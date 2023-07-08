package fr.gwendal_jouneaux.rob_lang.execute.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

import fr.gwendal_jouneaux.rob_lang.execute.ui.StartButton;

public class EnvironmentChooser implements ActionListener {

	public File getEnvironment() {
		return environment;
	}

	private File environment;
	private StartButton sb;
	private JLabel label;
	
	public EnvironmentChooser(StartButton sb, JLabel label) {
		environment = null;
		this.sb = sb;
		this.label = label;
	}

	@Override
	public void actionPerformed(ActionEvent evt){ 
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Documents/Recherche/Rob-Lang/RobLANG-Test"); 
  
        // invoke the showsOpenDialog function to show the dialog 
        int r = j.showOpenDialog(null); 
  
        // if the user selects a file 
        sb.setEnvReady(r == JFileChooser.APPROVE_OPTION);
        if (r == JFileChooser.APPROVE_OPTION){ 
            // set the label to the path of the selected file 
        	environment = j.getSelectedFile(); 
        	label.setText(environment.getName());
        } 
        // if the user cancelled the operation 
        else {
        	environment = null;
        	label.setText("No environment selected");
        }
    }

}
