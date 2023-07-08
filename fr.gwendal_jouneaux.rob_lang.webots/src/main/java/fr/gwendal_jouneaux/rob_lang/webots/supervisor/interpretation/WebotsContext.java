package fr.gwendal_jouneaux.rob_lang.webots.supervisor.interpretation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import fr.gwendal_jouneaux.rob_lang.interpreter.ContextRobLANG;
import fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.RobotModel;
import fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions.AAction;

public class WebotsContext extends ContextRobLANG {
	private Deque<Map<String, Object>> contexts;
	private RobotModel robotModel;
	private boolean backFlag = true;
	
	public WebotsContext(RobotModel robotModel, int timeStep) {
		this.contexts = new ArrayDeque<Map<String, Object>>();
		this.contexts.add(new HashMap<>());
		this.robotModel = robotModel;
	}

	
	
	// Context
	
	@Override
	public void put(String varName, Object value) {
		contexts.getFirst().put(varName, value);
	}

	@Override
	public Object get(String varName) {
		return contexts.getFirst().get(varName);
	}

	@Override
	public void addAll(Map<String, Object> vars) {
		contexts.getFirst().putAll(vars);
	}

	@Override
	public void pushContext(Map<String, Object> vars) {
		contexts.push(vars);
	}
	
	@Override
	public void pushContext() {
		contexts.push(new HashMap<>());
	}
	
	@Override
	public void popContext() {
		contexts.pop();
	}
	
	
	
	// Effectors

	@Override
	public void moveRobot(double dist) {
		if(dist<0) {
			if (backFlag) {
				robotModel.moveRobot(dist);
			}
			backFlag = !backFlag;
		} else {
			backFlag = true;
			robotModel.moveRobot(dist);
		}
	}

	@Override
	public void turnRobot(double angle) {
		robotModel.turnRobot(angle);
	}
	
	@Override
	public void setNominalSpeed(double speed) {
		System.out.println("Set Nominal speed :" + speed);
		robotModel.setNominalSpeed(speed);
	}
	
	@Override
	public void setSpeed(double speed) {
		System.out.println("Set speed :" + speed);
		robotModel.setSpeed(speed);
	}
	
	
	
	
	// Sensors

	@Override
	public double getBattery() {
		return robotModel.getBattery();
	}

	@Override
	public double[] getPosition() {
		return robotModel.getPosition();
	}

	@Override
	public double getDistance(int index) {
		return robotModel.getDistance(index);
	}

	@Override
	public double getCompass() {
		return robotModel.getCompass();
	}
	
	@Override
	public double getSpeed() {
		return robotModel.getSpeed();
	}
	
	@Override
	public double getNominalSpeed() {
		return robotModel.getNominalSpeed();
	}
	
	@Override
	public double getTime() {
		return robotModel.getTime();
	}



	public Deque<AAction> getActions() {
		return robotModel.getActions();
	}
	
}
