package fr.gwendal_jouneaux.rob_lang.execute.ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class StartButton extends JButton {
	
	private boolean envReady = false;
	private boolean robReady = false;
	private JLabel label;

	public StartButton(JLabel label) {
		super("Start");
		this.label = label;
		update();
	}
	
	private void update() {
		this.setEnabled(envReady && robReady);
		if(envReady && robReady) {label.setText("Ready");}
		else {label.setText("Behavior and Environment Required");}
	}
	
	public void setEnvReady(boolean b) {envReady = b;update();}
	public void setRobReady(boolean b) {robReady = b;update();}

}
