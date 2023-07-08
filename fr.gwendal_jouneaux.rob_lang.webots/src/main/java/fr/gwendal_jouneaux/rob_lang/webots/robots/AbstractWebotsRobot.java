package fr.gwendal_jouneaux.rob_lang.webots.robots;

import java.util.ArrayList;
import java.util.List;

import com.cyberbotics.webots.controller.Compass;
import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.Emitter;
import com.cyberbotics.webots.controller.GPS;
import com.cyberbotics.webots.controller.Motor;
import com.cyberbotics.webots.controller.Receiver;
import com.cyberbotics.webots.controller.Robot;


public abstract class AbstractWebotsRobot extends Robot {
	private int timeStep;
	private String robotID;

	// EFFECTOR
	private Motor[] motors;
	private double speed;

	// SENSOR
	private DistanceSensor[] distanceSensors;
	private Receiver receiver;
	private Emitter emitter;
	private Compass compass;
	private GPS gps;
	
	protected double battery = 10000;

	private enum Mode {
		INIT, STOP, MOVE, TURN, SPEED, UPDATE
	}

	public AbstractWebotsRobot(int timeStep, String robotID) {
		receiver = getReceiver("receiver");
		emitter = getEmitter("emitter");
		compass = getCompass("compass");
		gps = getGPS("gps");
		receiver.enable(timeStep);
		compass.enable(timeStep);
		gps.enable(timeStep);
		
		this.speed = getMaxSpeed();

		this.timeStep = timeStep;
		this.robotID = robotID;
	}

	public final void run() {
		Mode mode = Mode.STOP;
		
		boolean moving = false;
		boolean turning = false;
		boolean delaying = false;
		
		double[] moveObjective = new double[] {0, 0};
		double   turnObjective = 0;
		double  delayObjective = 0;
		
		double previousDist = Double.MAX_VALUE;
		double previousAngle = 361;
		
		boolean movingOrientation = true;
		double effectorValue = 0;
		
		while (step(timeStep) != -1) {
			battery -= 0.00029; // estimated J/ms consumption of an Arduino uno
			if(battery < 0) break;
			
			if(moving) {
				double[] pos = gps.getValues();
				double dist = Math.sqrt(Math.pow(moveObjective[0] - pos[0], 2) + Math.pow(moveObjective[1] - pos[2], 2));
				System.out.println("Move Dist : " + dist);
				double delta = previousDist - dist;
				System.out.println("Move delta : " + delta);
				if(dist < 0.005 || delta < -0.00001) {
					moving = false;
					String endAction = robotID + " end";
					emitter.send(endAction.getBytes());
					previousDist = Double.MAX_VALUE;
					continue;
				}
				previousDist = dist;
			} else if(turning) {
				double diff = Math.abs(angleDiff(turnObjective, getOrientation()));
				System.out.println("turn diff : " + diff);
				if(diff < 0.15) {
					turning = false;
					String endAction = robotID + " end";
					emitter.send(endAction.getBytes());
					previousAngle = 361;
					continue;
				}
				previousAngle = diff;
			} else if (delaying) {
				System.out.println("Delay : " + delayObjective);
				if(delayObjective < 0) {
					delaying = false;
					String endAction = robotID + " end";
					emitter.send(endAction.getBytes());
					continue;
				}
				delayObjective--;
			} else {
				// When no action running STOP is the nominal action
				mode = Mode.STOP;
				// Read the order of the supervisor
				if (receiver.getQueueLength() > 0) {
					String message = new String(receiver.getData());
					receiver.nextPacket();
					
					System.out.println("Robot : " + message);
	
					if(message.equals("init")) {
						mode = Mode.INIT;
					} else if (message.startsWith(robotID)) {
						String[] cmd = message.split(" ");
						switch (cmd[1]) {
						case "move":
							moving = true;
							mode = Mode.MOVE;
							movingOrientation = cmd[2].equals("forward");
							effectorValue = Double.parseDouble(cmd[3]);
							double orientation = getOrientation();
							System.out.println("Orientation : " + orientation);
							double y = Math.cos(Math.toRadians(orientation)) * effectorValue;
							double x = - Math.sin(Math.toRadians(orientation)) * effectorValue;
							if(movingOrientation) {
								y = -y;
								x = -x;
							}
							double[] pos = gps.getValues();
							moveObjective[0] = pos[0] + x;
							moveObjective[1] = pos[2] + y;
							System.out.println("Move Target : " + moveObjective[0] + " ; " + moveObjective[1]);
							break;
						case "turn":
							turning = true;
							mode = Mode.TURN;
							movingOrientation = cmd[2].equals("left");
							effectorValue = Double.parseDouble(cmd[3]) - 2.5;
							double angle = getOrientation();
							double target = angle + (movingOrientation ? -1 : 1) * effectorValue;
							// if target ends in being negative here the modulo will not change the sign
							target = target % 360; 
							target = target < 0 ? 360 + target : target;  // We need to do that for negative target
							turnObjective = target;
							System.out.println("Turn Target : " + target);
							break;
						case "delay":
							delaying = true;
							mode = Mode.STOP;
							delayObjective = Integer.parseInt(cmd[2]);
							System.out.println("Delay Target : " + delayObjective);
							break;
						case "speed":
							mode = Mode.SPEED;
							effectorValue = Double.parseDouble(cmd[2]);
							break;
						case "update":
							mode = Mode.UPDATE;
							break;
						default:
							mode = Mode.STOP;
						}
					}
				}
			}

			switch (mode) {
			case MOVE:
				this.move(movingOrientation);
				break;
			case TURN:
				this.turn(movingOrientation);
				break;
			case STOP:
				this.stop();
				break;
			case SPEED:
				this.setSpeed(effectorValue);
				String endAction = robotID + " end";
				endAction += createStateString();
				emitter.send(endAction.getBytes());
				break;
			case UPDATE:
				String messageToSend = robotID + " update\n";
				messageToSend += createStateString();
				emitter.send(messageToSend.getBytes());
				break;
			case INIT:
				String initMessage = robotID + " init\n";
				initMessage += createStateString() + "\n";
				initMessage += getMaxSpeed();
				emitter.send(initMessage.getBytes());
				break;
			}
		}
	}
	
	private String createStateString() {
		String currentState = "";
		currentState += getBattery() + "\n";
		currentState += getOrientation() + "\n";
		currentState += getTime() + "\n";

		List<String> values = new ArrayList<>();
		for(DistanceSensor sensor : distanceSensors) {
			values.add(sensor.getValue()+"");
		}
		currentState += String.join(" ", values) + "\n";
		
		double[] pos = gps.getValues();
		currentState += pos[0] + " " + pos[2] + " " + pos[1];
		
		return currentState;
	}

	public Motor[] getMotors() {
		return motors;
	}

	public void setMotors(Motor[] motors) {
		this.motors = motors;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if(speed > 0) {
			this.speed = Math.min(speed, getMaxSpeed());
		} else {
			this.speed = Math.max(speed, -getMaxSpeed());
		}
	}

	public DistanceSensor[] getDistanceSensors() {
		return distanceSensors;
	}

	public void setDistanceSensors(DistanceSensor[] distanceSensors) {
		this.distanceSensors = distanceSensors;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Emitter getEmitter() {
		return emitter;
	}

	public void setEmitter(Emitter emitter) {
		this.emitter = emitter;
	}

	public Compass getCompass() {
		return compass;
	}

	public void setCompass(Compass compass) {
		this.compass = compass;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public int getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(int timeStep) {
		this.timeStep = timeStep;
	}

	public abstract double getMaxSpeed();

	public abstract void move(boolean forward);

	public abstract void turn(boolean left);

	public abstract void stop();

	public double getBattery() {
		return battery;
	}

	public double getOrientation() {
		double[] north = compass.getValues();
		double rad = Math.atan2(north[0], north[2]);
		double bearing = (rad - 1.5708) / Math.PI * 180.0;
		if (bearing < 0.0)
			bearing = bearing + 360.0;
		return bearing;
	}
	
	private double angleDiff(double a, double b) {
		double d = Math.abs(a - b) % 360.0; 
        double r = d > 180.0 ? 360.0 - d : d;
           
        //calculate sign 
        int sign = (a - b >= 0 && a - b <= 180) || (a - b <=-180 && a- b>= -360) ? 1 : -1; 
        r *= sign;
        return r;
	}
}
