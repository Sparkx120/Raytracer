package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Matrix Math for 3D Transforms Etc. //TO BE PHASED INTO Math3D as static
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
		float cos = (float) Math.cos(deg);
		float sin = (float) Math.sin(deg);
		Float x = in.getX();
		Float y = in.getY()*cos - in.getZ()*sin;
		Float z = in.getY()*sin + in.getZ()*cos;
		Float h = in.getH();
		
		//Do this to make sure Vectors are Handled Correctly
		Point out = new Point(x,y,z);
		out.setH(h);
		
		return out;
	}
	
	/**
	 * Perform a Rotation on Y Axis (ignores Homogeneous Coords)
	 * @param in - Input Point
	 * @param deg - Input Rotation in Degrees
	 * @return Output Point
	 */
	public static Point affineTransformRy(Point in, Float deg){
		float cos = (float) Math.cos(deg);
		float sin = (float) Math.sin(deg);
		Float x = in.getX()*cos + in.getZ()*sin;
		Float y = in.getY();
		Float z = in.getZ()*cos - in.getX()*sin;
		Float h = in.getH();
		
		//Do this to make sure Vectors are Handled Correctly
		Point out = new Point(x,y,z);
		out.setH(h);
		
		return out;
	}
	
	/**
	 * Perform a Rotation on Z Axis (ignores Homogeneous Coords)
	 * @param in - Input Point
	 * @param deg - Input Rotation in Degrees
	 * @return Output Point
	 */
	public static Point affineTransformRz(Point in, Float deg){
		float cos = (float) Math.cos(deg);
		float sin = (float) Math.sin(deg);
		Float x = in.getX()*cos - in.getY()*sin;
		Float y = in.getX()*sin + in.getY()*cos;
		Float z = in.getZ();
		Float h = in.getH();
		
		//Do this to make sure Vectors are Handled Correctly
		Point out = new Point(x,y,z);
		out.setH(h);
		
		return out;
	}
}
