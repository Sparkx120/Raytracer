package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Matrix Math for 3D Transforms Etc.
 * @author James Wake
 * @version 1.0
 *
 */
public class Matrices3D {
	
	/**
	 * Perform a Rotation on X Axis (ignores Homogeneous Coords)
	 * @param in - Input Point
	 * @param deg - Input Rotation in Degrees
	 * @return Output Point
	 */
	public static Point affineTransformRx(Point in, Float deg){
		Float x = in.getX();
		Float y = (float) ((float) in.getY()*Math.cos(deg) - in.getZ()*Math.sin(deg));
		Float z = (float) ((float) in.getY()*Math.sin(deg) + in.getZ()*Math.cos(deg));
		
		return new Vector(x,y,z);
	}
	
	/**
	 * Perform a Rotation on Y Axis (ignores Homogeneous Coords)
	 * @param in - Input Point
	 * @param deg - Input Rotation in Degrees
	 * @return Output Point
	 */
	public static Point affineTransformRy(Point in, Float deg){
		Float x = (float) ((float) in.getX()*Math.cos(deg) + in.getZ()*Math.sin(deg));
		Float y = in.getY();
		Float z = (float) ((float) in.getZ()*Math.cos(deg) - in.getX()*Math.sin(deg));
		
		return new Vector(x,y,z);
	}
	
	/**
	 * Perform a Rotation on Z Axis (ignores Homogeneous Coords)
	 * @param in - Input Point
	 * @param deg - Input Rotation in Degrees
	 * @return Output Point
	 */
	public static Point affineTransformRz(Point in, Float deg){
		Float x = (float) ((float) in.getX()*Math.cos(deg) - in.getY()*Math.sin(deg));
		Float y = (float) ((float) in.getX()*Math.sin(deg) + in.getY()*Math.cos(deg));;
		Float z = in.getZ();
		
		return new Vector(x,y,z);
	}
}
