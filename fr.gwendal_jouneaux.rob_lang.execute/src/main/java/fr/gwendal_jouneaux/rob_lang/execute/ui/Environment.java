package fr.gwendal_jouneaux.rob_lang.execute.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gwendal_jouneaux.rob_lang.execute.Utils;
import fr.gwendal_jouneaux.rob_lang.execute.Vector;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.BasicRobot;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.Block;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.EnvElement;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.Rectangle;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.SolidElement;
import fr.gwendal_jouneaux.rob_lang.interpreter.AdaptationContext;
import fr.gwendal_jouneaux.rob_lang.interpreter.ContextRobLANG;

public class Environment extends ContextRobLANG{
	Deque<Map<String, Object>> contexts;
	List<EnvElement> elements;
	BasicRobot rob;
	Canvas canvas;
	
	public Environment() {
		canvas = new Canvas();
		canvas.setBackground(new Color(0));
		canvas.setSize(1080, 720);
		
		contexts = new ArrayDeque<Map<String, Object>>();
		contexts.push(new HashMap<>());
		
		elements = new ArrayList();
	}
	
	public void init(File conf) {
		contexts = new ArrayDeque<Map<String, Object>>();
		contexts.push(new HashMap<>());
		
		elements = new ArrayList();
		
		String content = Utils.fileToString(conf);
		String[] lines = content.split("\n");
		String[] parts = lines[0].split(" ");
		
		int width = Integer.parseInt(parts[0]);
		int height = Integer.parseInt(parts[1]);
		int startX = Integer.parseInt(parts[2]);
		int startY = Integer.parseInt(parts[3]);
		
		for(int i =1; i < lines.length; i++) {
			parts = lines[i].split(" ");
			switch(parts[0]) {
				case "B":
					elements.add(new Block(parts[1],
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							Integer.parseInt(parts[4]),
							Integer.parseInt(parts[5])));
					break;
				case "R":
					elements.add(new Rectangle(parts[1],
							Integer.parseInt(parts[2]),
							Integer.parseInt(parts[3]),
							Integer.parseInt(parts[4]),
							Integer.parseInt(parts[5])));
					break;
			}
		}
		
		rob = new BasicRobot(startX, startY);
		canvas.setBackground(new Color(0));
		canvas.setSize(width, height);
	}
	
	public void paint(Graphics g) {
		paintEnvironment(g);
		if(rob != null) rob.paint(g);
    }
	
	private void paintEnvironment(Graphics g) {
		for(EnvElement elem : elements) {
			elem.paint(g);
		}
	}

	@Override
	public void put(String varName, Object value) {
		contexts.getFirst().put(varName, value);
	}

	@Override
	public Object get(String varName) {
		return contexts.getFirst().get(varName);
	}
	
	@Override
	public void addAll(Map<String, Object> vars) {
		contexts.getFirst().putAll(vars);
	}

	@Override
	public void pushContext(Map<String, Object> vars) {
		contexts.push(vars);
	}
	
	@Override
	public void pushContext() {
		contexts.push(new HashMap<>());
	}
	
	@Override
	public void popContext() {
		contexts.pop();
	}

	@Override
	public double getBattery() {
		return rob.getBattery();
	}

	@Override
	public double[] getPosition() {
		return rob.getPosition();
	}

	@Override
	public double getDistance(int ID) {
		SolidElement elem = getNearest();
		double dist = elem.collide(new Vector((double)rob.getPosition()[0],(double)rob.getPosition()[1]), rob.getRay());

		return (rob.getRay().norm() * dist) - rob.getHeight()/2;
	}

	@Override
	public double getCompass() {
		return rob.getCompass();
	}

	@Override
	public void moveRobot(int dist) {
		rob.moveRobot(-dist);
		canvas.update(canvas.getGraphics());
	}

	@Override
	public void turnRobot(int angle) {
		rob.turnRobot(angle);
		canvas.update(canvas.getGraphics());
	}
	
	private SolidElement getNearest() {
		Vector ray = rob.getRay();
		SolidElement collided = null;
		double dist = 1;
		for (EnvElement elem : elements) {
			if(elem instanceof SolidElement) {
				double tempDist = ((SolidElement) elem).collide(new Vector((double)rob.getPosition()[0],(double)rob.getPosition()[1]), ray);
				if(tempDist != -1 && tempDist < dist) {
					dist = tempDist;
					collided = (SolidElement) elem;
				}
			}
		}
		return collided;
	}
	
	public Graphics getGraphics() {
		return canvas.getGraphics();
	}

	@Override
	public void setSpeed(double speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void update(Graphics graphics) {
		paint(graphics);
		canvas.update(graphics);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
