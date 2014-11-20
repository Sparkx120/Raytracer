package com.sparkx120.jwake.uwo.cs3388;

import java.util.Scanner;

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
	
	/**
	 * Create a new 3D Matrix and set it to the 0 Matrix
	 */
	public Matrix3D(){
		matrix = new float[4][4];
		for(float[] i:matrix)
			for(int j=0;j<4;j++)
				i[j] = 0;
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
	 * Decode a Matrix from a String
	 * @param matrixS
	 */
	public Matrix3D(String matrixS) {
		readMatrixFromString(matrixS);
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
	 * Add a matrix to this matrix and return a new Matrix3D
	 * @param b - The Matrix to add
	 * @return - The Result
	 */
	public Matrix3D addMatrix(Matrix3D b){
		float[][] in = b.getMatrix();
		float[][] result = new float[4][4];
		
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				result[i][j] = in[i][j] + matrix[i][j];
			}
		}
		
		return new Matrix3D(result);
	}
	
	/**
	 * Scalar Multiply a matrix to this matrix and return a new Matrix3D
	 * @param val - The value to multiply by
	 * @return - The Result
	 */
	public Matrix3D scalarMultiplyMatrix(float val){
		float[][] result = new float[4][4];
		
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				result[i][j] = matrix[i][j]*val;
			}
		}
		
		return new Matrix3D(result);
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

		return new Matrix3D(result);
	}
	
	/**
	 * Multiply this matrix with a Point
	 * @param v - The Point
	 * @return - The Point returned from the Multiplication
	 */
	public Point multiplyMatrixWithPoint(Point v){
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		float h = v.getH();
		
		float newx, newy, newz, newh;
		
		newx = x*matrix[0][0] + y*matrix[0][1] + z*matrix[0][2] + h*matrix[0][3];
		newy = x*matrix[1][0] + y*matrix[1][1] + z*matrix[1][2] + h*matrix[1][3];
		newz = x*matrix[2][0] + y*matrix[2][1] + z*matrix[2][2] + h*matrix[2][3];
		newh = x*matrix[3][0] + y*matrix[3][1] + z*matrix[3][2] + h*matrix[3][3];
		
		Point out = new Point(newx, newy, newz);
		out.setH(newh);
		
		return out;
	}
	
	/**
	 * Multiply this matrix with a Vector
	 * @param v - The Vector
	 * @return - The Vector having been multiplied with this Matrix
	 */
	public Vector multiplyMatrixWithVector(Vector v){
		float x = v.getX();
		float y = v.getY();
		float z = v.getZ();
		float h = v.getH();
		
		float newx, newy, newz, newh;
		
		newx = x*matrix[0][0] + y*matrix[0][1] + z*matrix[0][2] + h*matrix[0][3];
		newy = x*matrix[1][0] + y*matrix[1][1] + z*matrix[1][2] + h*matrix[1][3];
		newz = x*matrix[2][0] + y*matrix[2][1] + z*matrix[2][2] + h*matrix[2][3];
		newh = x*matrix[3][0] + y*matrix[3][1] + z*matrix[3][2] + h*matrix[3][3];
		
		Vector out = new Vector(newx, newy, newz);
		out.setH(newh);
		
		return out;
	}
	
	/**
	 * Generate a cofactorMatrix for determinate computations
	 * @param matrix - The Matrix to sub
	 * @param row - The row to remove
	 * @param col - The col to remove
	 * @return - The cofactorMatrix
	 */
	private float[][] cofactor(float[][] matrix, int row, int col){
		float[][] result = new float[matrix.length-1][matrix.length-1];
		int rowPointer = 0;
		int colPointer = 0;
		for(int r=0; r<result.length && rowPointer < matrix.length; r++){
			if(r == row)
				rowPointer = r + 1;
			for(int c=0; c<result.length && colPointer < matrix.length; c++){
				if(c == col)
					colPointer = c + 1;
				result[r][c] = matrix[rowPointer][colPointer];
//				System.out.print(result[r][c]);
				colPointer ++;
			}
//			System.out.print("   " + r + " " + rowPointer);
			colPointer = 0;
			rowPointer ++;
//			System.out.println();
		}
		return result;
	}
	
	/**
	 * Compute the Matrix Determinate using a Laplace Cofactor Expansion
	 * @param matrix - The Matrix to find the Determinate of
	 * @return - The Determiante
	 */
	private float determinate(float[][] matrix){
		if(matrix.length == 2){
			return (matrix[0][0]*matrix[1][1]) - (matrix[0][1]*matrix[1][0]);
		}
		//Use first row
		float determinate = 0;
		for(int c=0; c<matrix.length; c++){
			int coeffPM = (int) Math.pow(-1D, c);
			determinate += coeffPM * matrix[0][c] * determinate(cofactor(matrix, 0, c));
		}
		
		return determinate;
	}
	
	/**
	 * Compute the transpose of a matrix
	 * @param matrix - the Matrix
	 * @return - its Transpose
	 */
	private float[][] pTranspose(float[][] matrix){
		float[][] result = new float[matrix[0].length][matrix.length];
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j<matrix[0].length; j++){
				result[j][i] = matrix[i][j];
			}
		}
		return result;
	}
	
	/**
	 * Compute the Adjugate Matrix of a Matrix
	 * @param matrix - The Matrix
	 * @return - The Adjugate Matrix
	 */
	private float[][] adjugateMatrix(float[][] matrix){
		if(matrix.length < 3){
			return null;
		}
		
		float[][] result = new float[matrix.length][matrix.length];
		
		for(int r=0; r<matrix.length; r++){
			for(int c=0; c<matrix.length; c++){
				int coeffPM = (int) Math.pow(-1D, r+c);
				result[r][c] = coeffPM * determinate(cofactor(matrix, r, c));
			}
		}
		
		return pTranspose(result);
	}
	
	/**
	 * Compute the inverse of this Matrix using Crammer's Rule and the Determinate 
	 * calculated by a Laplace Cofactor Expansion
	 * @return - The inverse
	 */
	public Matrix3D inverseMatrix(){		
		float det = determinate(this.matrix);
//		System.out.println("det: " + det);
		Matrix3D adj = new Matrix3D(adjugateMatrix(this.matrix));
//		System.out.println("adj: \n" + adj);
		Matrix3D result = adj.scalarMultiplyMatrix(1/det);
		
		return result;
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
	
	/**
	 * Encode a Matrix3D to a String for storage
	 * NOT IMPLEMENTED RETURNS NULL
	 * @return - The String Representation
	 */
	public String encodeMatrixToDataString(){
		return null;
	}
	
	/**
	 * Reads a String in and sets this Matrix to the encoded Matrix;
	 */
	public void readMatrixFromString(String in){
		float[][] input = new float[4][4];
		String [] matrix = in.substring(in.indexOf('{'), in.indexOf('}')).split("\n");
		
		int i = 0;
		for(String p: matrix){
			Scanner scan = new Scanner(p);
			float c1 = scan.nextFloat();
			float c2 = scan.nextFloat();
			float c3 = scan.nextFloat();
			float c4 = scan.nextFloat();
			input[i][0] = c1;
			input[i][1] = c2;
			input[i][2] = c3;
			input[i][3] = c4;
			scan.close();
			i++;
		}
	}
	
	/**
	 * Tests for Matrix Inversion
	 * @param args - NONE
	 */
	public static void main(String[] args){
		Matrix3D I = Matrices3D.I;
		Matrix3D test = Matrices3D.testMatrixA;
//		I.cofactor(I.getMatrix(), 1,1);
		System.out.println(test);
		test = test.inverseMatrix();
		System.out.println(test);
	}
}
