package fr.gwendal_jouneaux.rob_lang.webots.supervisor.model.actions;

import com.cyberbotics.webots.controller.Emitter;

public abstract class AAction {
	private boolean running = false;
	
	public void doPerform(Emitter emitter) {
		this.perform(emitter);
		running = true;
	}
	
	public final boolean isRunning() {
		return running;
	}
	
	protected abstract void perform(Emitter emitter);
}
