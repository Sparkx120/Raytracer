package com.sparkx120.jwake.graphics3d.lights;

import java.awt.Color;

import com.sparkx120.jwake.graphics3d.base.Point;

/**
 * Omni Directional Light Source
 * @author James Wake
 *
 */
public class OmniDirectionalLight extends LightObject{
	private Point source;
	/**
	 * Use Abstraction for OmniDirectional
	 * @param source - The Point where the light is
	 * @param intensity - The Intensity of the light
	 * @param color - The Color of the Light
	 */
	public OmniDirectionalLight(Point source, float intensity, Color color) {
		super(intensity, color);
		this.source = source;
	}
	
	/**
	 * Definition Constructor
	 * @param substring
	 */
	public OmniDirectionalLight(String substring) {
		super(substring);
		// TODO Auto-generated constructor stub
	}

	public void readLightFromEncodedString(){
		
	}
	
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

}
