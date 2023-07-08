package fr.gwendal_jouneaux.rob_lang.execute;

public class Vector {
	private double x;
	private double y;
	private double scaleX;
	private double scaleY;
	private double signX;
	private double signY;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
		this.scaleX = 1.0 / x;
		this.scaleY = 1.0 / y;
		this.signX = x/Math.abs(x);
		this.signY = y/Math.abs(y);
	}
	
	public Vector(double norm, int angle) {
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));
		double x = sin * norm;
		double y = cos * norm;
		this.x = x;
		this.y = y;
		this.scaleX = 1.0 / x;
		this.scaleY = 1.0 / y;
		this.signX = x/Math.abs(x);
		this.signY = y/Math.abs(y);
	}
	
	public Vector add(Vector other) {
		return new Vector(this.x+other.x, this.y+other.y);
	}
	
	public double norm() {
		return Math.sqrt(x*x+y*y);
	}
	
	public Vector inverse() {
		return new Vector(-this.x, -this.y);
	}
	
	public Vector normalize() {
		double norm = this.norm();
		return new Vector(this.x/norm, this.y/norm);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getSignX() {
		return signX;
	}

	public void setSignX(double signX) {
		this.signX = signX;
	}

	public double getSignY() {
		return signY;
	}

	public void setSignY(double signY) {
		this.signY = signY;
	}
	
	
}