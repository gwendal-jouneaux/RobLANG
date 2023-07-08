package fr.gwendal_jouneaux.rob_lang.webots.supervisor.interpretation;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import fr.gwendal_jouneaux.rob_lang.RobLANGStandaloneSetupGenerated;
import fr.gwendal_jouneaux.rob_lang.interpreter.InterpretRobLANG;
import fr.gwendal_jouneaux.rob_lang.robLANG.Robot;

public class InterpretationThread extends Thread {
	
	private WebotsContext context;
	
	public InterpretationThread(WebotsContext context) {
		this.context = context;
	}
	
	public void run() {
		Injector injector = new RobLANGStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		
		String filePath = System.getenv("BEHAVIOR_PATH");
		URI uri = URI.createFileURI(filePath);
		Resource resource = resourceSet.getResource(uri, true);
		
		//FeedbackLoop.INSTANCE().setContext(context);
		//FeedbackLoop.INSTANCE().registerModule(new BatteryOptimization());

		Robot theRobot = (Robot) resource.getContents().get(0);
		InterpretRobLANG interpreter = InterpretRobLANG.INSTANCE();
		System.out.println("Program Started");
		interpreter.interpret(theRobot, context);
	}
}
