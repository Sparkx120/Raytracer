package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

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
