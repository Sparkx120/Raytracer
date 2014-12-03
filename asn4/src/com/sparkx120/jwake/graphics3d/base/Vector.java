package com.sparkx120.jwake.graphics3d.base;

import java.io.Serializable;

import com.sparkx120.jwake.math.Math3D;

/**
 * Vector Class to represent Vectors
 * @author James Wake
 * @version 1.0
 */
public class Vector extends Point implements Serializable{
	
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -3728382632473220303L;

	/**
	 * Empty Constructor
	 */
	public Vector(){
		super();
		this.h = 0.0F; //Don't Translate Vector
	}
	
	/**
	 * Make a Vector defined by points from origin
	 * @param x - X Coordinate
	 * @param y - Y Coordinate
	 * @param z - Z Coordinate
	 */
	public Vector(Float x, Float y, Float z) {
		super(x, y, z);
		this.h = 0.0F; //Don't Translate Vector
	}
	
	/**
	 * Make a vector defined by two points. Vector will point from
	 * A to B aka B - A
	 * @param a - First Point
	 * @param b - Second Point
	 */
	public Vector(Point a, Point b){
		super(Math3D.subPoints(b, a));
		this.h = 0.0F; //Don't Translate Vector
	}
	//A Vector is much like a point except that it needs to be handled
	//differently. This class is here to differentiate the two.
}
