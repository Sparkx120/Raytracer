package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * 3D Point/Vector Class
 * @author James Wake
 * @version 1.0
 *
 */
public class Point {
	private Float x, y, z;
	
	/**
	 * Empty Constructor
	 */
	public Point(){
		
	}
	
	/**
	 * Construct a Point as a copy of another Point
	 * @param p - A Point to copy
	 */
	public Point(Point p){
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}
	
	/**
	 * Default Constructor
	 * @param x - The X Coordinate
	 * @param y - The Y Coordinate
	 * @param z - The Z Coordinate
	 */
	public Point(Float x, Float y, Float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Sets Coordinate X
	 * @param x - New X Value
	 */
	public void setX(Float x){
		this.x = x;
	}
	
	/**
	 * Sets Coordinate Y
	 * @param y - New Y Value
	 */
	public void setY(Float y){
		this.y = y;
	}
	
	/**
	 * Sets Coordinate Z
	 * @param z - New Z Value
	 */
	public void setZ(Float z){
		this.z = z;
	}
	
	/**
	 * Get Coordinate X
	 * @return The X Coordinate
	 */
	public Float getX(){
		return x;
	}
	
	/**
	 * Get Coordinate Y
	 * @return The Y Coordinate
	 */
	public Float getY(){
		return y;
	}
	
	/**
	 * Get Coordinate Z
	 * @return The Z Coordinate
	 */
	public Float getZ(){
		return z;
	}
	
	public boolean equals(Point p){
		if(p.getX() == x && p.getY() == y && p.getZ() == z)
			return true;
		return false;
	}
	
}
