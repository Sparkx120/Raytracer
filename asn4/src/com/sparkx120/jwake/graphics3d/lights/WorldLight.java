package com.sparkx120.jwake.graphics3d.lights;

import java.awt.Color;

import com.sparkx120.jwake.graphics3d.base.Point;
import com.sparkx120.jwake.graphics3d.base.Vector;

public class WorldLight extends LightObject{
	Vector direction;
	float falloff;
	
	public WorldLight(Vector lightDirection, float intensity, Color color) {
		super(intensity, color);
		this.direction = lightDirection;
		this.falloff = 10;
	}
	
	public Point getSource(Point worldPoint){
		Point out = new Point();
		out.setX(worldPoint.getX() + (direction.getX()*falloff/this.getIntensity()));
		out.setX(worldPoint.getY() + (direction.getY()*falloff/this.getIntensity()));
		out.setX(worldPoint.getZ() + (direction.getZ()*falloff/this.getIntensity()));
		return out;
	}
	
	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	/**
	 * @return the falloff
	 */
	public float getFalloff() {
		return falloff;
	}

	/**
	 * @param falloff the falloff to set
	 */
	public void setFalloff(float falloff) {
		this.falloff = falloff;
	}

}
