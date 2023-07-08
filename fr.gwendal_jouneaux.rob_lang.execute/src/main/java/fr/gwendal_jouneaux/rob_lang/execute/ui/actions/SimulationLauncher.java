package fr.gwendal_jouneaux.rob_lang.execute.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import fr.gwendal_jouneaux.rob_lang.RobLANGStandaloneSetupGenerated;
import fr.gwendal_jouneaux.rob_lang.execute.ui.Environment;
import fr.gwendal_jouneaux.rob_lang.interpreter.InterpretRobLANG;
import fr.gwendal_jouneaux.rob_lang.robLANG.Robot;

public class SimulationLauncher  implements ActionListener{
	
	private EnvironmentChooser envConf;
	private BehaviorChooser behavior;
	private Environment env;

	public SimulationLauncher(EnvironmentChooser ec, BehaviorChooser bc, Environment env) {
		this.envConf = ec;
		this.behavior = bc;
		this.env = env;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		env.init(envConf.getEnvironment());
		
		env.update(env.getGraphics());
		
		Injector injector = new RobLANGStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		Resource resource = resourceSet.getResource(URI.createFileURI(behavior.getBehavior().getAbsolutePath()), true);

		Robot theRobot = (Robot) resource.getContents().get(0);
		
		InterpretRobLANG interpreter = new InterpretRobLANG();
		interpreter.interpret(theRobot, env);
	}

}
