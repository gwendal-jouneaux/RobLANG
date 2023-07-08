package fr.gwendal_jouneaux.rob_lang.execute.canvas;

import java.awt.Color;
import java.awt.Graphics;

import fr.gwendal_jouneaux.rob_lang.execute.Utils;

public class Rectangle implements EnvElement {
	
	private Color color;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Rectangle(String color, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = Utils.colorFromString(color);
	}

	@Override
	public void paint(Graphics g) {
		Color prev = g.getColor();
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(prev);
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
