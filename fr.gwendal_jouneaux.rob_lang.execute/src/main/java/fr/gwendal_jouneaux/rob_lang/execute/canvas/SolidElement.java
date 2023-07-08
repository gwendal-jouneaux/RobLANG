package fr.gwendal_jouneaux.rob_lang.execute.canvas;

import java.awt.Polygon;

import fr.gwendal_jouneaux.rob_lang.execute.Vector;

public interface SolidElement {
	public double collide(Vector pos, Vector ray);
}
