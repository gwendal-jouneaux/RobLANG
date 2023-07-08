package fr.gwendal_jouneaux.rob_lang;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import fr.gwendal_jouneaux.rob_lang.interpreter.InterpretRobLANG;
import fr.gwendal_jouneaux.rob_lang.robLANG.Robot;

public class App {
	
	private static String resultPath;
	private static StandaloneContext context;
	private static Robot theRobot;
	
	
	public static void main(String[] args) {
		
		String filePath = args[0];
		resultPath = "";

		boolean isBench = args[0].startsWith("-");
		
		if (isBench) {
			resultPath = args[1];
			filePath = args[2];
		}
		
		Injector injector = new RobLANGStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		
		URI uri = URI.createFileURI(filePath);
		Resource resource = resourceSet.getResource(uri, true);
		
		context = new StandaloneContext();

		theRobot = (Robot) resource.getContents().get(0);
		
		if (isBench) {
			benchmark();
		} else {
			run();
		}
	}
	
	public static Object benchmark() {
		long start = System.currentTimeMillis();
		Object out = InterpretRobLANG.INSTANCE().interpret(theRobot, context);
		long end = System.currentTimeMillis();
		
		long duration = end - start;
		String resultFilname = resultPath.substring(resultPath.lastIndexOf("/")+1);
		String filenamesplit = resultFilname.substring(0,resultFilname.lastIndexOf("."));
		String[] benchInfo = filenamesplit.split("_");
		
		
		try {
			File resultFile = new File(resultPath);
			resultFile.createNewFile();
			FileWriter fw = new FileWriter(resultFile, true);
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(benchInfo[1]+","+benchInfo[0]+","+benchInfo[2]+","+duration);
		    bw.newLine();
		    bw.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		return out;
	}

	public static Object run() {
		Object out = InterpretRobLANG.INSTANCE().interpret(theRobot, context);
		return out;
	}
}
