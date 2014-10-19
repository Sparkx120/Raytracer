package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public int getNumberOfPolys(){
		return this.polygons.size();
	}
	
	public int getNumberOfVertices(){
		return this.vertices.size();
	}
}
