package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;
import java.util.Scanner;

/**
 * A Light Emission Source
 * @author James Wake
 *
 */
public abstract class LightObject {
	
	private Point source;
	private float intensity;
	private Color color;
	
	/**
	 * Defines a simple Light Object
	 * @param source - The Source point
	 * @param intensity - The intensity (between 0 and 1)
	 * @param color - The color 
	 */
	public LightObject(Point source, float intensity, Color color){
		this.source = source;
		this.intensity = intensity;
		this.color = color;
	}
	
	public LightObject(String light) {
		decodeString(light);
	}
	
	/**
	 * Decode a String into an Object
	 * @param sphere
	 */
	private void decodeString(String obj){
		Scanner scan = new Scanner(obj);
		float x = scan.nextFloat();
		float y = scan.nextFloat();
		float z = scan.nextFloat();
		this.setSource(new Point(x, y, z));
		this.setIntensity(scan.nextFloat());
		float r = scan.nextFloat();
		float g = scan.nextFloat();
		float b = scan.nextFloat();
		this.setColor(new Color(r, g, b));
		scan.close();
	}
	
//	public abstract void rayIntersect(Ray ray);
	
	/**
	 * Generated Getters and Setters
	 */

	/**
	 * @return the source
	 */
	public Point getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Point source) {
		this.source = source;
	}

	/**
	 * @return the intensity
	 */
	public float getIntensity() {
		return intensity;
	}

	/**
	 * @param intensity the intensity to set
	 */
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
