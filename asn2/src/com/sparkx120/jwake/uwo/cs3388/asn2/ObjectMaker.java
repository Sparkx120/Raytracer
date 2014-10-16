package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.util.ArrayList;

/**
 * ObjectMaker is part 1 of ASN2. It makes a Polygonal Object from
 * a set of points rotated along a specified axis.
 * 
 * @author James Wake
 * @version 1.0
 *
 */
public class ObjectMaker {
	private ArrayList<String> inputObjectData;
	private Object obj;
	
	/**
	 * The Main method for the Object Maker
	 * @param args - Args TBD
	 */
	public static void main(String[] args){
		//TODO Write the main logic
	}
	
	//Read Specified Data File for Asn2
	private void readFile(String fname){
		inputObjectData = FileIO.readFileAsStringList(fname);
	}
	
	private void generateInitVertexList(){
		
	}
	
	private void rotateXDegreesMakePolys(){
		
	}
	
	private void saveObjectToFile(){
		
	}
}
