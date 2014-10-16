package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Generic 3D Math Library
 * @author James Wake
 * @version 1.0
 */
public class Math {
	
	/**
	 * Adds two points A + B
	 * @param a - Point A
	 * @param b - Point B
	 * @return The Result
	 */
	public static Point addPoints(Point a, Point b){
		Float x = a.getX() + b.getX();
		Float y = a.getY() + b.getY();
		Float z = a.getZ() + b.getZ();
		
		return new Point(x, y, z);
	}
	
	/**
	 * Subtracts two points A + B
	 * @param a - Point A
	 * @param b - Point B
	 * @return The Result
	 */	
	public static Point subPoints(Point a, Point b){
		Float x = a.getX() - b.getX();
		Float y = a.getY() - b.getY();
		Float z = a.getZ() - b.getZ();
		
		return new Point(x, y, z);
	}
	
	/**
	 * Computes the cross Product of A and B
	 * @param a - Point A
	 * @param b - Point B
	 * @return The Result
	 */
	public static Vector crossProdVectors(Vector a, Vector b){
		Float x = (a.getY()*b.getZ()) - (a.getZ()*b.getY());
		Float y = (a.getZ()*b.getX()) - (a.getX()*b.getZ());
		Float z = (a.getX()*b.getY()) - (a.getY()*b.getX());
		
		return new Vector(x, y, z);
	}
	
}
