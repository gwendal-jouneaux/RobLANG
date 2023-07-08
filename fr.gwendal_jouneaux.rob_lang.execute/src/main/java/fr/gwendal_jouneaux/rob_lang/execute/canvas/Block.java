package fr.gwendal_jouneaux.rob_lang.execute.canvas;

import java.awt.Polygon;

import fr.gwendal_jouneaux.rob_lang.execute.Vector;
import fr.gwendal_jouneaux.rob_lang.execute.robot.IRobot;

public class Block extends Rectangle implements SolidElement {

	public Block(String color, int x, int y, int width, int height) {
		super(color, x, y, width, height);
	}

	@Override
	public double collide(Vector pos, Vector ray) {
		double nearTimeX = (this.getX()+this.getWidth()/2 - 
							ray.getSignX() * (this.getWidth()/2) - 
							pos.getX()
							) * ray.getScaleX();
		double nearTimeY = (this.getY()+this.getHeight()/2 - 
							ray.getSignY() * (this.getHeight()/2) - 
							pos.getY()
							) * ray.getScaleY();
		double farTimeX = (this.getX()+this.getWidth()/2 + 
							ray.getSignX() * (this.getWidth()/2) - 
							pos.getX()
							) * ray.getScaleX();
		double farTimeY = (this.getY()+this.getHeight()/2 + 
							ray.getSignY() * (this.getHeight()/2) - 
							pos.getY()
							) * ray.getScaleY();
		
		// No collision
		if (nearTimeX > farTimeY || nearTimeY > farTimeX) {
			return -1;
		}
		
		double nearTime = nearTimeX > nearTimeY ? nearTimeX : nearTimeY;
		double farTime = farTimeX < farTimeY ? farTimeX : farTimeY;
		
		if (nearTime >= 1 || farTime <= 0) {
	      return -1;
	    }
		
	    double dx = (1.0 - nearTime) * -ray.getX();
	    double dy = (1.0 - nearTime) * -ray.getY();
	    //hit.pos.x = pos.x + delta.x * hit.time;
	    //hit.pos.y = pos.y + delta.y * hit.time;
		
		return nearTime;
	}

}
