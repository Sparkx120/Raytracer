package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

/**
 * Omni Directional Light Source
 * @author James Wake
 *
 */
public class OmniDirectionalLight extends LightObject{

	/**
	 * Use Abstraction for OmniDirectional
	 * @param source - The Point where the light is
	 * @param intensity - The Intensity of the light
	 * @param color - The Color of the Light
	 */
	public OmniDirectionalLight(Point source, float intensity, Color color) {
		super(source, intensity, color);
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

}
