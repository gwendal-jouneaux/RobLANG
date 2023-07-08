package fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions;

import com.cyberbotics.webots.controller.Emitter;

import fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.RobotModel;

public class ModelUpdate extends AAction {
	
	private RobotModel model;
	
	public ModelUpdate(RobotModel model) {
		this.model = model;
	}

	@Override
	protected void perform(Emitter emitter) {
		String messageToSend = model.getRobotID() + " update";
		byte[] formated = messageToSend.getBytes();
        emitter.send(formated);
	}
	
	public void update(double battery, double angle, double time,
			double[] position, double[] distance) {
		model.setBattery(battery);
		model.setAngle(angle);
		model.setTime(time);
		model.setPosition(position);
		model.setDistance(distance);
	}
	
	public String toString() {
		return "Model update";
	}
}
