package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Matrix Math for 3D Transforms Etc. //TO BE PHASED INTO Math3D as static
 * @author James Wake
 * @version 1.0
 *
 */
public class Matrices3D {
	
	/**
	 * Identity Matrix
	 */
	public final static Matrix3D I = new Matrix3D(new float[][]{
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	});
	
	/**
	 * Perform a Rotation on X Axis
	 * @param deg - Input Rotation in Degrees
	 * @return Output Matrix
	 */
	public static Matrix3D affineTransformRx(Float deg){
		float cos = (float) Math.cos((Math.PI/180)*deg);
		float sin = (float) Math.sin((Math.PI/180)*deg);
		
		float[][] data = new float[4][4];
		
		//Setup Matrix
		data[0][0] = 1;		data[0][1] = 0;		data[0][2] = 0;		data[0][3] = 0;
		data[1][0] = 0;		data[1][1] = cos;	data[1][2] = -sin;	data[1][3] = 0;
		data[2][0] = 0;		data[2][1] = sin;	data[2][2] = cos;	data[2][3] = 0;
		data[3][0] = 0;		data[3][1] = 0;		data[3][2] = 0;		data[3][3] = 1;
		
		//Output
		Matrix3D matrix = new Matrix3D(data);

		return matrix;
	}
	
	/**
	 * Perform a Rotation on Y Axis
	 * @param deg - Input Rotation in Degrees
	 * @return Output Matrix
	 */
	public static Matrix3D affineTransformRy(Float deg){
		float cos = (float) Math.cos((Math.PI/180)*deg);
		float sin = (float) Math.sin((Math.PI/180)*deg);

		float[][] data = new float[4][4];
		
		//Setup Matrix
		data[0][0] = cos;	data[0][1] = 0;		data[0][2] = sin;	data[0][3] = 0;
		data[1][0] = 0;		data[1][1] = 1;		data[1][2] = 0;		data[1][3] = 0;
		data[2][0] = -sin;	data[2][1] = 0;		data[2][2] = cos;	data[2][3] = 0;
		data[3][0] = 0;		data[3][1] = 0;		data[3][2] = 0;		data[3][3] = 1;
		
		//Output
		Matrix3D matrix = new Matrix3D(data);

		return matrix;
	}
	
	/**
	 * Perform a Rotation on Z Axis
	 * @param deg - Input Rotation in Degrees
	 * @return Output Matrix
	 */
	public static Matrix3D affineTransformRz(Float deg){
		float cos = (float) Math.cos((Math.PI/180)*deg);
		float sin = (float) Math.sin((Math.PI/180)*deg);
		
		float[][] data = new float[4][4];
		
		//Setup Matrix
		data[0][0] = cos;	data[0][1] = -sin;	data[0][2] = 0;		data[0][3] = 0;
		data[1][0] = sin;	data[1][1] = cos;	data[1][2] = 0;		data[1][3] = 0;
		data[2][0] = 0;		data[2][1] = 0;		data[2][2] = 1;		data[2][3] = 0;
		data[3][0] = 0;		data[3][1] = 0;		data[3][2] = 0;		data[3][3] = 1;
		
		//Output
		Matrix3D matrix = new Matrix3D(data);

		return matrix;
	}
	
	/**
	 * Rotation matrix around an arbitrary vector by some number of degrees
	 * @param deg -The number of degrees
	 * @param axis - The Arbitrary Axis Vector
	 * @return
	 */
	public static Matrix3D rotateOnArbitrary(Float deg, Vector axis){
		//Preconfig
		float cos = (float) Math.cos((Math.PI/180)*deg);
		float sin = (float) Math.sin((Math.PI/180)*deg);
		Vector v = Math3D.normalizeVector(axis);
		
		float[][] data = new float[4][4];
		
		//Setup Jv Matrix
		data[0][0] = 0;			data[0][1] = -v.getZ();	data[0][2] = v.getY();	data[0][3] = 0;
		data[1][0] = v.getZ();	data[1][1] = 0;			data[1][2] = -v.getX();	data[1][3] = 0;
		data[2][0] = -v.getY();	data[2][1] = v.getX();	data[2][2] = 0;			data[2][3] = 0;
		data[3][0] = 0;			data[3][1] = 0;			data[3][2] = 0;			data[3][3] = 1;
		
		Matrix3D Jv = new Matrix3D(data);
		Matrix3D R = Matrices3D.I.addMatrix(Jv.scalarMultiplyMatrix(sin)).addMatrix(Jv.multiplyMatrixWithMatrix(Jv).scalarMultiplyMatrix(1-cos));
		
		return R;
	}
	
	/**
	 * Perform a Translation on Vector Direction X Y Z
	 * @param x - Input Magnitude of X
	 * @param y - Input Magnitude of Y
	 * @param z - Input Magnitude of Z
	 * @return Output Matrix
	 */
	public static Matrix3D affineTransformX(float x, float y, float z){		
		float[][] data = new float[4][4];
		
		//Setup Matrix
		data[0][0] = 1;		data[0][1] = 0;		data[0][2] = 0;		data[0][3] = x;
		data[1][0] = 0;		data[1][1] = 1;		data[1][2] = 0;		data[1][3] = y;
		data[2][0] = 0;		data[2][1] = 0;		data[2][2] = 1;		data[2][3] = z;
		data[3][0] = 0;		data[3][1] = 0;		data[3][2] = 0;		data[3][3] = 1;
		
		//Output
		Matrix3D matrix = new Matrix3D(data);

		return matrix;
	}
	
	/**
	 * Perform a Scaling on Vector Components X Y Z
	 * @param x - Input Magnitude of X
	 * @param y - Input Magnitude of Y
	 * @param z - Input Magnitude of Z
	 * @return Output Matrix
	 */
	public static Matrix3D affineScaleX(float x, float y, float z){		
		float[][] data = new float[4][4];
		
		//Setup Matrix
		data[0][0] = x;		data[0][1] = 0;		data[0][2] = 0;		data[0][3] = 0;
		data[1][0] = 0;		data[1][1] = y;		data[1][2] = 0;		data[1][3] = 0;
		data[2][0] = 0;		data[2][1] = 0;		data[2][2] = z;		data[2][3] = 0;
		data[3][0] = 0;		data[3][1] = 0;		data[3][2] = 0;		data[3][3] = 1;
		
		//Output
		Matrix3D matrix = new Matrix3D(data);

		return matrix;
	}
}
