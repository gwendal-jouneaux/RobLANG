package fr.gwendal_jouneaux.rob_lang.execute;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.gwendal_jouneaux.rob_lang.execute.ui.Environment;
import fr.gwendal_jouneaux.rob_lang.execute.ui.StartButton;
import fr.gwendal_jouneaux.rob_lang.execute.ui.actions.BehaviorChooser;
import fr.gwendal_jouneaux.rob_lang.execute.ui.actions.EnvironmentChooser;
import fr.gwendal_jouneaux.rob_lang.execute.ui.actions.SimulationLauncher;

public class Application {

	public static void main(String[] args) {
		JFrame frame = new JFrame("RobLANG Simulation");
		
		Environment env = new Environment();
        
        JLabel envLabel = new JLabel("No environment selected");
        JLabel robLabel = new JLabel("No behavior selected");
        JLabel startLabel = new JLabel("Behavior and Environment Required");
        
        JButton envButton = new JButton("Choose environment");
        JButton robButton = new JButton("Choose behavior"); 
        StartButton start = new StartButton(startLabel);
        
        EnvironmentChooser chooseEnvironment = new EnvironmentChooser(start, envLabel);
        BehaviorChooser chooseBehavior = new BehaviorChooser(start, robLabel);
        envButton.addActionListener(chooseEnvironment); 
        robButton.addActionListener(chooseBehavior); 
        
        SimulationLauncher launcher = new SimulationLauncher(chooseEnvironment, chooseBehavior, env);
        start.addActionListener(launcher); 
        
        JPanel pEnv = new JPanel();
        pEnv.setLayout(new BoxLayout(pEnv, BoxLayout.Y_AXIS));
        pEnv.add(envLabel);
        pEnv.add(envButton);
        
        JPanel pRob = new JPanel();
        pRob.setLayout(new BoxLayout(pRob, BoxLayout.Y_AXIS));
        pRob.add(robLabel);
        pRob.add(robButton);
        
        JPanel pStart = new JPanel();
        pStart.setLayout(new BoxLayout(pStart, BoxLayout.Y_AXIS));
        pStart.add(startLabel);
        pStart.add(start);
        
        JPanel pControl = new JPanel();
        pControl.add(pEnv);
        pControl.add(pRob);
        pControl.add(pStart);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(pControl);
        mainPanel.add(env.getCanvas());
        
        
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
