package fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions;

import com.cyberbotics.webots.controller.Emitter;

public class ChangeSpeed extends AAction {
	
	String simulationRobotID;
	double speed;
	
	public ChangeSpeed(String simulationRobotID, double speed) {
		this.simulationRobotID = simulationRobotID;
		this.speed = speed;
	}

	@Override
	protected void perform(Emitter emitter) {
		String messageToSend = simulationRobotID + " speed " + this.speed;
		byte[] formated = messageToSend.getBytes();
        emitter.send(formated);
	}
	
	public String toString() {
		return "Change speed -> speed : " + speed;
	}

}
