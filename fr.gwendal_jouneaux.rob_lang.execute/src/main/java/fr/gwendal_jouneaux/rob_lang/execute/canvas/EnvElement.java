package fr.gwendal_jouneaux.rob_lang.execute.canvas;

import java.awt.Color;
import java.awt.Graphics;

public interface EnvElement {
	public void paint(Graphics g);
	public Color getColor();
}
