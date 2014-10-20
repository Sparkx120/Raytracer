package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Defines a 3D Matrix in Homogeneous Coordinates.
 * The Matrix is defined as a 4x4 Matrix with mx and my defining internal
 * matrix coordinates
 * Ranges:
 * mx 0-3
 * my 0-3
 * 
 * Values:
 * All values are floats
 * 
 * Example:
 * value of 1.0F at
 * mx = 1 and my = 2
 * |0 0 0 0|
 * |0 0 0 0|
 * |0 1 0 0|
 * |0 0 0 0|
 * * Decimals not added to make reading structure easier
 * 
 * I did not make a MatrixN Class as I have no need in this course for one and
 * do not have time to Abstract this that much.
 * 
 * @author James Wake
 * @version 1.0
 *
 */
public class Matrix3D {
	private float[][] matrix;
	
	public final static Matrix3D identity = new Matrix3D(new float[][]{
			{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}
	});
	
	/**
	 * Create a new 3D Matrix and set it to the 0 Matrix
	 */
	public Matrix3D(){
		matrix = new float[4][4];
		for(float[] i:matrix)
			for(float j:i)
				j = 0;
	}
	
	/**
	 * Create a Matrix with a predefined float[][]. MUST BE 4x4 or else things will get hairy.
	 * No Error Checking is implemented right now.
	 * @param data - The Data in a float[][]
	 */
	public Matrix3D(float[][] data){
		this.matrix = data;
	}
	
	/**
	 * Insert a Value at Matrix Coordinate X,Y
	 * @param mx - X Coordinate
	 * @param my - Y Coordinate
	 * @param value - Value
	 */
	public void insertValAt(int mx, int my, float value){
		matrix[my][mx] = value;
	}
	
	/**
	 * Get a Value at Matrix Coordinate X,Y
	 * @param mx - X Coordinate
	 * @param my - Y Coordinate
	 * @return The value
	 */
	public float getValAt(int mx, int my){
		return matrix[my][mx];
	}
	
	/**
	 * Sets the matrix data directly with a float[][]
	 * @param matrix - The new Matrix Data
	 */
	public void setMatrix(float[][] matrix){
		this.matrix = matrix;
	}
	
	/**
	 * Gets the matrix data directly ie it's float[][]
	 * @return The matrix data
	 */
	public float[][] getMatrix(){
		return this.matrix;
	}
	
	/**
	 * Multiplies This Matrix to another of the same type NxN
	 * This * Matrix b
	 * @param b - The Matrix to Multiply this matrix by
	 * @return The Resultant Matrix3D
	 */
	public Matrix3D multiplyMatrixWithMatrix(Matrix3D b){
		float[][] result = new float[4][4];
		float[][] matrixb = b.getMatrix();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				float value = 0.0F;
				for(int k=0;k<4;k++)
					value += this.matrix[i][k]*matrixb[k][j];
				result[i][j] = value;
			}
		}
		Matrix3D out = new Matrix3D(result);
		
		return out;
	}
	
	public Point multiplyMatrixWithPoint(Point v){
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		float h = v.getH();
		
		float newx, newy, newz;
		
		newx = x*matrix[0][0] + y*matrix[0][1] + z*matrix[0][2] + h*matrix[0][3];
		newy = x*matrix[1][0] + y*matrix[1][1] + z*matrix[1][2] + h*matrix[1][3];
		newz = x*matrix[2][0] + y*matrix[2][1] + z*matrix[2][2] + h*matrix[2][3];
		
		Point out = new Point(newx, newy, newz);
		//out.setH(h);
		
		return out;
	}
	
	public Vector multiplyMatrixWithVector(Vector v){
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		float h = v.getH();
		
		float newx, newy, newz;
		
		newx = x*matrix[0][0] + y*matrix[0][1] + z*matrix[0][2] + h*matrix[0][3];
		newy = x*matrix[1][0] + y*matrix[1][1] + z*matrix[1][2] + h*matrix[1][3];
		newz = x*matrix[2][0] + y*matrix[2][1] + z*matrix[2][2] + h*matrix[2][3];
		
		Vector out = new Vector(newx, newy, newz);
		//out.setH(h);
		
		return out;
	}
	
	/**
	 * toString Override
	 */
	@Override
	public String toString(){
		String out = "";
		for(int i=0;i<4;i++){
			out += "|";
			for(int j=0;j<4;j++){
				out += matrix[i][j] + " | ";
			}
			out += "\n";
		}
		return out;
	}
}
