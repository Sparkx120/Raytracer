package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * ObjectMaker is part 1 of ASN2. It makes a Polygonal Object from
 * a set of points rotated along a specified axis.
 * 
 * @author James Wake
 * @version 1.0
 *
 */
public class ObjectMaker {
	private static ArrayList<String> inputObjectData;
	private static Float degreeRotation;
	private static int rotations;
	private static VertexList list;
	private static VertexList[] lists; 
	private static char axis;
	private static PolyObject3D obj;
	
	/**
	 * The Main method for the Object Maker
	 * @param args - Args TBD
	 */
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Not Enough Arguments: input.file output.file");
			System.exit(1);
		}
		//TODO Write the main logic
		readFile(args[0]);
		degreeRotation = 60.0F;//Float.parseFloat(inputObjectData.get(1));
		axis = inputObjectData.get(0).charAt(0);
		rotations = 0;
		list = generateInitVertexList();
		lists = new VertexList[(int) (360.0/degreeRotation) + 1];
		while(360+degreeRotation > degreeRotation*rotations){
			rotateXDegrees();
		}
		obj = new PolyObject3D();
		makePolys();
		System.out.println("Vertices Generated: " + obj.getVertices().size());
		System.out.println("Faces Generated: " + obj.getFaces().size());
		for(int i=0; i<obj.getVertices().size(); i++){
			Vertex v = obj.getVertices().get(i);
			Point p = v.getPoint();
			System.out.println("x: " + p.getX() + " y: " + p.getY() + " z: " + p.getZ());
		}
		//saveObjectToFile(args[1]);
	}
	
	//Read Specified Data File for Asn2
	private static void readFile(String fname){
		inputObjectData = FileIO.readFileAsStringList(fname);
	}
	
	private static VertexList generateInitVertexList(){
		VertexList vList = new VertexList();
		Iterator<String> it = inputObjectData.iterator();
		it.next(); it.next(); //Get rid of non vertex data
		while(it.hasNext()){
			String vertexToBe = it.next();
			Scanner scan = new Scanner(vertexToBe);
			Float x = scan.nextFloat();
			Float y = scan.nextFloat();
			Float z = scan.nextFloat();
			scan.close();
			Vertex v = new Vertex(x, y, z);
			vList.add(v);
		}
		return vList;
	}
	
	private static void rotateXDegrees(){
		Iterator<Vertex> vertices = list.iterator();
		
		VertexList currList = new VertexList();
		while(vertices.hasNext()){
			Vertex curr = vertices.next();
			Point rotatedPoint = new Point();
			switch(axis){
				case 'x': rotatedPoint = Matrices3D.affineTransformRx(curr.getPoint(), degreeRotation*rotations);
						  break;
				case 'y': rotatedPoint = Matrices3D.affineTransformRy(curr.getPoint(), degreeRotation*rotations);
						  break;
				case 'z': rotatedPoint = Matrices3D.affineTransformRz(curr.getPoint(), degreeRotation*rotations);
						  break;
			}
			currList.add(new Vertex(rotatedPoint));
		}
		
		System.out.println(rotations);
		lists[rotations] = currList;
		rotations ++;
	}
	
	/**
	 * Connects the vertices into polygons like this
	 * 
	 * c--a
	 * |\ | 
	 * | \|
	 * d--b
	 */
	private static void makePolys(){
		for(int i=0; i<lists.length-1; i++){
			for(int j=0; j<lists[0].size()-1; j++){
				Vertex a = lists[i].get(j);
				Vertex b = lists[i].get(j+1);
				Vertex c = lists[i+1].get(j);
				Vertex d = lists[i+1].get(j+1);
				obj.addPolygonByVertices(a, c, b);
				obj.addPolygonByVertices(c, d, b);
			}
		}
	}
	
	private static void saveObjectToFile(String fname){
		FileIO.writeObjectToFile(obj.getFaces().get(0), fname);
	}
}
