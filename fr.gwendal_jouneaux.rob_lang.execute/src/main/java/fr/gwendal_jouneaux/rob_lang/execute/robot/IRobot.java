package fr.gwendal_jouneaux.rob_lang.execute.robot;

import java.awt.Polygon;
import java.util.List;

import fr.gwendal_jouneaux.rob_lang.execute.Vector;

public interface IRobot {
	
	public List<IAction> getActions();
	public Vector getRay();
	public int getHeight();
	
	// sensors
	public double getBattery();
	public double[] getPosition();
	public double getCompass();
	
	// effectors
	public void moveRobot(int dist);
	public void turnRobot(int angle);
}
