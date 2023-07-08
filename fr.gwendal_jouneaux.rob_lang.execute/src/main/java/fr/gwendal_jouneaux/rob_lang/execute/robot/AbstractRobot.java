package fr.gwendal_jouneaux.rob_lang.execute.robot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.List;

import fr.gwendal_jouneaux.rob_lang.execute.Vector;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.EnvElement;
import fr.gwendal_jouneaux.rob_lang.execute.canvas.SolidElement;

public abstract class AbstractRobot implements IRobot, EnvElement{

	protected int x;
	protected int y;
	protected int speed;
	protected int battery;
	protected int angle;
	protected final int WIDTH = 30;
	protected final int HEIGHT = 50;

	public AbstractRobot(int x, int y, int speed, int battery, int angle) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.battery = battery;
		this.angle = angle;
	}

	
	// @IRobot implementation for normal robot behavior
	
	@Override
	public double getBattery() {
		return this.battery;
	}

	@Override
	public double[] getPosition() {
		return new double[] {this.x , this.y};
	}

	@Override
	public double getCompass() {
		return angle;
	}

	@Override
	public void moveRobot(int dist) {
		delay(100);
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));
		double dx = sin * dist;
		double dy = cos * dist;
		this.x += dx;
		this.y += dy;
	}

	@Override
	public void turnRobot(int angle) {
		delay(100);
		this.angle = (this.angle+angle)%360;
	}
	
	private void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<IAction> getActions() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	
	
	
	// @EnvElement implementation

	@Override
	public void paint(Graphics g) {
		Color prev = g.getColor();
		g.setColor(new Color(255, 255, 255));
		
		Point[] outline = getOutline();
		Point[] toDraw = getOutline();
		rotatePointMatrix(outline, this.angle, toDraw);
		Polygon shape = polygonize(toDraw);
		shape.translate(x, y);
		g.fillPolygon(shape);
		
		g.drawLine(x, y, (int) (x+getRay().getX()), (int) (y+getRay().getY()));
		
		g.setColor(prev);
	}
	
	@Override
	public Color getColor() {
		return new Color(255, 255, 255);
	}
	
	
	
	
	public Vector getRay() {
		double cos = Math.cos(Math.toRadians(angle));
		double sin = Math.sin(Math.toRadians(angle));
		double coeff = Math.abs(sin) < 0.00001 ? 0 : cos/sin;
		double X = 0;
		double Y = 0;
		if(angle<180) {
			X = angle == 0 ? x : 0;
			Y = angle == 0 ? 0 : coeff*(X-x) + y;
		} else if (angle > 180) {
			X = 1080;
			Y = coeff*(X-x) + y;
		} else {
			X = x;
			Y = 720;
		}
		return new Vector(X-x, Y-y);
	}
	
	protected Point[] getOutline() {
		return new Point[] {new Point(-(WIDTH/2), (HEIGHT/2)),
				 new Point(-(WIDTH/2), 0),
                 new Point(0, -(HEIGHT/2)),
                 new Point((WIDTH/2), 0),
                 new Point((WIDTH/2), (HEIGHT/2))};
	}
	
	protected void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo){
        AffineTransform.getRotateInstance
        (Math.toRadians(-angle), 0, 0)
                .transform(origPoints,0,storeTo,0,5);


    }
	
	protected Polygon polygonize(Point[] polyPoints){

        //a simple method that makes a new polygon out of the rotated points
        Polygon tempPoly = new Polygon();

         for(int  i=0; i < polyPoints.length; i++){
             tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
        }

        return tempPoly;

    }

}
