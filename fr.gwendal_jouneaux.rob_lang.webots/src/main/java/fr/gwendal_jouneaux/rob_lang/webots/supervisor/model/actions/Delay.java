package fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions;

import com.cyberbotics.webots.controller.Emitter;

public class Delay extends AAction {
	
	private String simulationRobotID;
	private int time;

	public Delay(String simulationRobotID, int time) {
		this.simulationRobotID = simulationRobotID;
		this.time = time;
	}

	@Override
	protected void perform(Emitter emitter) {
		String messageToSend = simulationRobotID + " delay " + time;
		byte[] formated = messageToSend.getBytes();
        emitter.send(formated);
	}

	public String toString() {
		return "Delay -> time : " + time;
	}

}
