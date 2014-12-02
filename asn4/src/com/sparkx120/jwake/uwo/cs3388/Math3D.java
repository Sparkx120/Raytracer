package com.sparkx120.jwake.uwo.cs3388;

/**
 * Generic 3D Math Library
 * @author James Wake
 * @version 1.0
 */
public class Math3D{
	
	public static final Point origin = new Point(0F,0F,0F);
	
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
	 * Subtracts two points A - B
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
		Float h = v.getH()*s;
		
		Vector out = new Vector(x, y, z);
		out.setH(h);
		
		return out;
	}
	
	/**
	 * Scalar Multiply Point P
	 * @param v - Point P
	 * @param s - Scalar
	 * @return Result of Multiplication
	 */
	public static Point scalarMultiplyPoint(Point v, Float s){
		Float x = v.getX()*s;
		Float y = v.getY()*s;
		Float z = v.getZ()*s;
		Float h = v.getH()*s;
		
		Point out = new Point(x, y, z);
		out.setH(h);
		
		return out;
		
	}
	
	/**
	 * Computes the magnitude of a Vector
	 * @param v - The Vector
	 * @return It's Magnitude
	 */
	public static float magnitudeOfVector(Vector v){
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		float h = v.getH();
		
		float out = (float) Math.sqrt((x*x) + (y*y) + (z*z) + (h*h));
		return out;
	}
	
	/**
	 * Computes the Dot Product of Vectors and Points
	 * @param a - Point a
	 * @param b - Point b
	 * @return - The Result of the Dot Product
	 */
	public static float dotProduct(Point a, Point b){ 
		return (a.getX()*b.getX()) + (a.getY()*b.getY()) + (a.getZ()*b.getZ()) + (a.getH()*b.getH());
	}
	
	/**
	 * Normalizes a Vector to unit length
	 * @param v - The vector to normalize
	 * @return - The Normalized vector
	 */
	public static Vector normalizeVector(Vector v) {
		Vector out;
		float mag = Math3D.magnitudeOfVector(v);
		out = Math3D.scalarMultiplyVector(v, (1/mag));
		
		return out;
	}
}
