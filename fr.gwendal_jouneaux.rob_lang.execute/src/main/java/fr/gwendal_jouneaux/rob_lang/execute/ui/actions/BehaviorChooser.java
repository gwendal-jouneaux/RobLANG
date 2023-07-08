package fr.gwendal_jouneaux.rob_lang.execute.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

import fr.gwendal_jouneaux.rob_lang.execute.ui.StartButton;

public class BehaviorChooser implements ActionListener {
	
	public File getBehavior() {
		return behavior;
	}

	private File behavior;
	private StartButton sb;
	private JLabel label;

	public BehaviorChooser(StartButton sb, JLabel label) {
		behavior = null;
		this.sb = sb;
		this.label = label;
	}

	@Override
	public void actionPerformed(ActionEvent evt){ 
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Documents/Recherche/Rob-Lang/RobLANG-Test"); 
  
        // invoke the showsSaveDialog function to show the save dialog 
        int r = j.showSaveDialog(null); 
  
        // if the user selects a file 
        sb.setRobReady(r == JFileChooser.APPROVE_OPTION);
        if (r == JFileChooser.APPROVE_OPTION){ 
            // set the label to the path of the selected file 
            behavior = j.getSelectedFile(); 
            label.setText(behavior.getName());
        } 
        // if the user cancelled the operation 
        else {
        	behavior = null;
        	label.setText("No behavior selected");
        }
    } 

}
