package fr.gwendal_jouneaux.rob_lang;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import fr.gwendal_jouneaux.rob_lang.interpreter.ContextRobLANG;

public class StandaloneContext extends ContextRobLANG {
	private Deque<Map<String, Object>> contexts;
	//private Map<String, Deque<Bounds>> constraints;
	private double battery;
	private double speed;
	private double angle;
	private double time;
	private double[] position;
	private double[] distance;
	private double nominalSpeed;
	
	private final double MOTOR_POWER = 14;
	
	public StandaloneContext() {
		this.contexts = new ArrayDeque<Map<String, Object>>();
		this.contexts.add(new HashMap<>());
		//this.constraints = new HashMap<>();
		this.angle = 0;
		this.battery = 1000000;
		this.speed = 10;
		this.angle = 0;
		this.time = 0;
		this.position = new double[] {0.0, 0.0};
		this.distance = new double[] {0.0, 0.0};
		this.nominalSpeed = 10;
	}

	// Context
	
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
	
	private void motorConsumption(double time) {
		double percentage = this.nominalSpeed / this.speed;
		double watt = Math.pow(percentage, 3) * MOTOR_POWER;
		battery -= (2 * watt) / 1000 * time;
		this.time += time;
		if(battery < 0) {
			System.err.println("Battery depletted after : " + time + " s simulated");
			System.exit(0);
		}
	}
	private void cpuConsumption() {
		battery -= 0.00029;
		this.time++;
		if(battery < 0) {
			System.err.println("Battery depletted after : " + time + " s simulated");
			System.exit(0);
		}
	}
	
	// Effectors

	@Override
	public void moveRobot(double dist) {
		this.position[0] += Math.cos(Math.toRadians(this.angle-90)) * dist;
		this.position[1] += Math.sin(Math.toRadians(this.angle-90)) * dist;
		double duration = dist / this.nominalSpeed * 1000;
		battery -= 0.00029 * duration;
		motorConsumption(duration);
	}

	@Override
	public void turnRobot(double angle) {
		this.angle += angle%360;
		if(this.angle < 0) this.angle += 360;
		if(this.angle > 360) this.angle -= 360;
		double duration = Math.toRadians(angle) / this.nominalSpeed * 1000;
		battery -= 0.00029 * duration;
		motorConsumption(duration);
	}
	
	@Override
	public void setNominalSpeed(double speed) {
		this.nominalSpeed = speed;
		cpuConsumption();
	}
	
	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
		cpuConsumption();
	}
	
	
	
	
	// Sensors

	@Override
	public double getBattery() {
		cpuConsumption();
		return this.battery;
	}

	@Override
	public double[] getPosition() {
		cpuConsumption();
		return this.position;
	}

	@Override
	public double getDistance(int index) {
		cpuConsumption();
		return this.distance[index];
	}

	@Override
	public double getCompass() {
		cpuConsumption();
		return this.angle;
	}
	
	@Override
	public double getSpeed() {
		cpuConsumption();
		return this.speed;
	}
	
	@Override
	public double getNominalSpeed() {
		cpuConsumption();
		return this.nominalSpeed;
	}
	
	@Override
	public double getTime() {
		cpuConsumption();
		return this.time;
	}
	
}
