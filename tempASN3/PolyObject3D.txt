package com.sparkx120.jwake.uwo.cs3388;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 3D Object Class. Notice that I do not use a Normal List as Normals are integrated
 * into Polygons and Vertices implicitly by my Object Design and therefore would be
 * redundant
 * 
 * Implements Serializable So that Object can be saved to disk easily after init.
 * 
 * @author James Wake
 * @version 1.0
 *
 */
public class PolyObject3D extends Object3D implements Serializable{
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -715169348465892563L;
	
	/**
	 * Vertex List of this Object
	 */
	private VertexList vertices;
	
	/**
	 * Polygon / Face List of this Object
	 */
	private ArrayList<Polygon> polygons;
	
	/**
	 * Default Constructor
	 */
	public PolyObject3D(){
		vertices = new VertexList();
		polygons = new ArrayList<Polygon>();
	}
	
	/**
	 * Construct from a dataFile String
	 */
	public PolyObject3D(String data){
		vertices = new VertexList();
		polygons = new ArrayList<Polygon>();
		decodeStorageFileString(data);
	}
	
	/**
	 * Get the Vertex List for this Object
	 * @return The Vertex List
	 */
	public ArrayList<Vertex> getVertices(){
		return vertices;
	}
	
	/**
	 * Get the Polygon Face List for this Object
	 * @return The Polygon List
	 */
	public ArrayList<Polygon> getFaces(){
		return polygons;
	}
	
	/**
	 * Add a polygon by Vertices in Counterclockwise Order for Normal A -> B -> C
	 * @param a - Vertex A
	 * @param b - Vertex B
	 * @param c - Vertex C
	 */
	public void addPolygonByVertices(Vertex a, Vertex b, Vertex c){
		//Try to add vertices to the list if they already exist get the existing Vertex instead.
		if(!vertices.add(a)){
			a = vertices.get(vertices.indexOf(a));
		}
		if(!vertices.add(b)){
			b = vertices.get(vertices.indexOf(b));
		}
		if(!vertices.add(c)){
			c = vertices.get(vertices.indexOf(c));
		}
		
		//Create Polygon using Vertices (Vertex Normals Update automatically)
		Polygon poly = new Polygon(a, b, c);
		polygons.add(poly);	
	}
	
	/**
	 * Returns the Number of Polygon Faces this Object has
	 * @return The Number
	 */
	public int getNumberOfPolys(){
		return this.polygons.size();
	}
	
	/**
	 * Returns the Number of Vertices this object has
	 * @return The Number
	 */
	public int getNumberOfVertices(){
		return this.vertices.size();
	}
	
	/**
	 * toString Override
	 */
	@Override
	public String toString(){
		String out = "Vertices: " + this.getNumberOfVertices() + "\n";
		out += "Faces: " + this.getNumberOfPolys() + "\n";
		out += vertices.toString();
		return out;
	}
	
	public String encodeStorageFileString(){
		String data = "PolyObject3D v1.0\n";
		
		//Encode Vertices
		data += "vertices: \n";
		Iterator<Vertex> vertices = this.vertices.iterator();
		while(vertices.hasNext()){
			Vertex v = vertices.next();
			Point p = v.getPoint();
			data += p.getX() + " " + p.getY() + " " + p.getZ() + "\n";
		}
		data += "END VERTEX\n";
		
		//Encode Polygons
		data += "polys: \n";
		Iterator<Polygon> polys = this.polygons.iterator();
		while(polys.hasNext()){
			Polygon poly = polys.next();
			Vertex a = poly.getVertexA();
			Vertex b = poly.getVertexB();
			Vertex c = poly.getVertexC();
			int aIndex = this.vertices.indexOf(a);
			int bIndex = this.vertices.indexOf(b);
			int cIndex = this.vertices.indexOf(c);
			data += aIndex + " " + bIndex + " " + cIndex + "\n";
		}
		data += "END POLYS\n";
		
		return data;
	}
	
	private void decodeStorageFileString(String data){
		String vertexList = data.substring(data.indexOf("vertices: ")+11, data.indexOf("END VERTEX"));
//		vertexList = vertexList.substring(1, vertexList.length());
		String polyList = data.substring(data.indexOf("polys: ")+8, data.indexOf("END POLYS"));
		
		//Init the Vertex List
		String[] verticesToAdd = vertexList.split("[\\r\\n]+");
		for(String v: verticesToAdd){
			Scanner scan = new Scanner(v);
			Float x = scan.nextFloat();
			Float y = scan.nextFloat();
			Float z = scan.nextFloat();
			scan.close();
			
			vertices.add(new Vertex(x, y, z));
		}
		
		//System.out.println(vertices.size());
		
		String[] polysToAdd = polyList.split("[\\r\\n]+");
		for(String p: polysToAdd){
			Scanner scan = new Scanner(p);
			int aIndex = scan.nextInt();
			int bIndex = scan.nextInt();
			int cIndex = scan.nextInt();
			scan.close();
			
			addPolygonByVertices(vertices.get(aIndex), vertices.get(bIndex), vertices.get(cIndex));
		}
		
		System.out.println("Decoded " + polygons.size() + " polygons.");
	}
}
