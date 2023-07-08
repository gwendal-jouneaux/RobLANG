package fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions;

import com.cyberbotics.webots.controller.Emitter;

public class Turn extends AAction {

	private String simulationRobotID;
	private double angle;
	private boolean left;
	
	public Turn(String simulationRobotID, double angle) {
		this.simulationRobotID = simulationRobotID;
		this.angle = Math.abs(angle);
		this.left = angle > 0;
	}

	@Override
	protected void perform(Emitter emitter) {
		String messageToSend = simulationRobotID + " turn " + (left ? "left " : "right ") + angle;
		byte[] formated = messageToSend.getBytes();
        emitter.send(formated);
	}

	public String toString() {
		return "Turn -> rotation angle : " + angle;
	}
}
