package fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions;

import com.cyberbotics.webots.controller.Emitter;

public class Move extends AAction {
	
	private String simulationRobotID;
	private double dist;
	private boolean forward;
	
	public Move(String simulationRobotID, double dist) {
		this.simulationRobotID = simulationRobotID;
		this.dist = Math.abs(dist);
		this.forward = dist > 0;
	}

	@Override
	protected void perform(Emitter emitter) {
		String messageToSend = simulationRobotID + " move " + (forward ? "forward " : "backward ") + dist;
		byte[] formated = messageToSend.getBytes();
        emitter.send(formated);
	}

	public String toString() {
		return "Move -> distance : " + dist;
	}
}
