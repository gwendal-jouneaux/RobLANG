package fr.gwendal_jouneaux.rob_lang.execute.thread;

import java.util.List;

import fr.gwendal_jouneaux.rob_lang.execute.robot.IAction;
import fr.gwendal_jouneaux.rob_lang.execute.robot.IRobot;

public class InterfaceThread extends Thread {

	IRobot rob;
	
	public InterfaceThread(IRobot robot) {
		this.rob = robot;
	}
	
	public void run() 
    { 
        List<IAction> actions = rob.getActions();
    } 

}
