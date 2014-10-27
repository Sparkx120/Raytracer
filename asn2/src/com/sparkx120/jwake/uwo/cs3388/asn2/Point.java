package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.io.Serializable;

/**
 * 3D Point/Vector Class
 * @author James Wake
 * @version 1.0
 *
 */
public class Point implements Serializable{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 3085957537125847385L;
	protected Float x, y, z, h;
	
	/**
	 * Empty Constructor
	 */
	public Point(){
		this.h = 1.0F; //Translate Points
	}
	
	/**
	 * Construct a Point as a copy of another Point
	 * @param p - A Point to copy
	 */
	public Point(Point p){
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
		this.h = 1.0F; //Translate Points
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
		this.h = 1.0F; //Translate Points
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
	 * Sets Coordinate h
	 * @param h - New H Value
	 */
	public void setH(Float h){
		this.h = h;
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
	
	/**
	 * Gets Coordinate H
	 * @return The H Coordinate
	 */
	public Float getH(){
		return h;
	}
	
	/**
	 * Equals used to see if this Point is equivalent to another
	 * @param p - Point to check
	 * @return - True or False
	 */
	public boolean equals(Point p){
		if(p.getX() == x && p.getY() == y && p.getZ() == z && p.getH() == h)
			return true;
		return false;
	}
	
	/**
	 * toString Override
	 */
	@Override
	public String toString(){
		return "x: " + this.getX() + " y: " + this.getY() + " z: " + this.getZ() + " h: " + this.getH();
	}
	
}
