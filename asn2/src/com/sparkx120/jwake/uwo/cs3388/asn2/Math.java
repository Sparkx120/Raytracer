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
	
	/**
	 * Add two Vectors A + B
	 * @param a - Vector A
	 * @param b - Vector B
	 * @return Result of Addition
	 */
	public static Vector vectorAdd(Vector a, Vector b){
		Float x = a.getX() + b.getX();
		Float y = a.getY() + b.getY();
		Float z = a.getZ() + b.getZ();
		
		return new Vector(x, y, z);
	}
	
	/**
	 * Subtract two Vectors A - B
	 * @param a - Vector A
	 * @param b - Vector B
	 * @return Result of Subtraction
	 */
	public static Vector vectorSub(Vector a, Vector b){
		Float x = a.getX() - b.getX();
		Float y = a.getY() - b.getY();
		Float z = a.getZ() - b.getZ();
		
		return new Vector(x, y, z);
	}
	
	/**
	 * Scalar Multiply Vector V
	 * @param v - Vector V
	 * @param s - Scalar
	 * @return Result of Multiplication
	 */
	public static Vector scalarMultiplyVector(Vector v, Float s){
		Float x = v.getX()*s;
		Float y = v.getY()*s;
		Float z = v.getZ()*s;
		
		return new Vector(x, y, z);
	}
	
}
