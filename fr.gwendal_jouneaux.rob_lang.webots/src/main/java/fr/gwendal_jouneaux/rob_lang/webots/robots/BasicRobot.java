package fr.gwendal_jouneaux.rob_lang.webots.robots;

import com.cyberbotics.webots.controller.DistanceSensor;
import com.cyberbotics.webots.controller.Motor;

public class BasicRobot extends AbstractWebotsRobot {
	
	private final double WHEEL_DIAMETER = 0.05;
	private final double WHEEL_CENTER_DIST = 0.045;
	
	private final double MOTOR_POWER = 14;
	
	public static void main(String[] args) {
		BasicRobot behavior = new BasicRobot(1);
		behavior.run();
	}

	public BasicRobot(int timeStep) {
		super(timeStep,"BasicRobot");
		
		Motor[] motors = new Motor[] {getMotor("left wheel motor"),getMotor("right wheel motor")};
	    motors[0].setPosition(Double.POSITIVE_INFINITY);
	    motors[1].setPosition(Double.POSITIVE_INFINITY);
	    motors[0].setVelocity(0.0);
	    motors[1].setVelocity(0.0);
	    setMotors(motors);
	    
	    DistanceSensor[] distanceSensors = new DistanceSensor[] {getDistanceSensor("ds0"),getDistanceSensor("ds1")};
	    for (int i=0; i<distanceSensors.length; i++) {
	      distanceSensors[i].enable(timeStep);
	    }
	    setDistanceSensors(distanceSensors);
	}

	@Override
	public double getMaxSpeed() {
		return 10;
	}

	@Override
	public void move(boolean forward) {
		updateBattery();
		Motor[] motors = getMotors();
		int factor = forward ? 1 : -1;
		motors[0].setVelocity(this.getSpeed() * factor);
	    motors[1].setVelocity(this.getSpeed() * factor);
	}

	@Override
	public void turn(boolean left) {
		updateBattery();
		Motor[] motors = getMotors();
		motors[left ? 0 : 1].setVelocity(-this.getSpeed());
	    motors[left ? 1 : 0].setVelocity(this.getSpeed());
	}

	@Override
	public void stop() {
		Motor[] motors = getMotors();
		motors[0].setVelocity(0);
	    motors[1].setVelocity(0);
	}
	
	private void updateBattery() {
		double percentage = this.getSpeed() / this.getMaxSpeed();
		double watt = Math.pow(percentage, 3) * MOTOR_POWER;
		battery -= (2 * watt) / 1000;
	}
}
