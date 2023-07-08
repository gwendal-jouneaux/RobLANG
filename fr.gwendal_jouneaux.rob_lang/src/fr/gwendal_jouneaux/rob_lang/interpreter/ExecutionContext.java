package fr.gwendal_jouneaux.rob_lang.interpreter;

import java.util.Map;

public interface ExecutionContext {
	public void put(String varName, Object value);
	public Object get(String varName);
	public void addAll(Map<String, Object> vars);
	public void pushContext(Map<String, Object> vars);
	public void pushContext();
	public void popContext();
	
	// sensors
	public double getBattery();
	public double[] getPosition();
	public double getDistance(int index);
	public double getCompass();
	public double getSpeed();
	public double getNominalSpeed();
	public double getTime();
	
	// effectors
	public void moveRobot(double dist);
	public void turnRobot(double angle);
	public void setNominalSpeed(double speed);
	public void setSpeed(double speed);
	
}
