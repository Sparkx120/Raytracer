package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class to define a Vertex. 
 * @author James Wake
 * @version 1.0
 *
 */
public class Vertex implements Serializable{

	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 5727695601010804607L;

	/**
	 * Point that this Vertex is at
	 */
	private Point p;
	
	/**
	 * Normal of this Vertex (Based on Polys) (Ignore Magnitude Direction only)
	 */
	private Vector norm;
	
	/**
	 * List of pointers to Polygons using this Vertex
	 */
	private ArrayList<Polygon> polys;
	
	
	/**
	 * Empty Constructor
	 */
	public Vertex(){
		polys = new ArrayList<Polygon>();
	}
	
	/**
	 * Default Constructor Providing the Vertex Point
	 * @param p - The Point defining the Vertex
	 */
	public Vertex(Point p){
		this.p = p;
		polys = new ArrayList<Polygon>();
	}
	
	/**
	 * Create a Vertex from 3 coordinates X Y Z
	 * @param x - X Coordinate
	 * @param y - Y Coordinate
	 * @param z - Z Coordinate
	 */
	public Vertex(Float x, Float y, Float z) {
		//Use Default Constructor
		this(new Point(x, y, z));
	}

	/**
	 * Set the vertex point
	 * @param p - The new point
	 */
	public void setPoint(Point p){
		this.p = p;
	}
	
	/**
	 * Set the normal of this vertex
	 * @param n - The vector of the normal of this vertex
	 */
	protected void setNormal(Vector n){
		this.norm = n;
	}
	
	//TODO Made setNormal Method that can compute from an ArrayList of Polygons
	
	/**
	 * Get the point representation of this Vertex
	 * @return The Point
	 */
	public Point getPoint(){
		return p;
	}
	
	/**
	 * Get the Normal Vector for this vertex (returns null if not set)
	 * @return - The Normal Vector
	 */
	public Vector getNormal(){
		return norm;
	}
	
	/**
	 * Add a polygon reference to this vertex and update normal
	 * @param p - The Polygon
	 */
	protected void addPoly(Polygon p){
		polys.add(p);
		
		if(norm == null)
			norm = new Vector(0.0F, 0.0F, 0.0F); 
		
		norm = Math3D.vectorAdd(norm, p.getNorm());
	}
	
	/**
	 * Remove a polygon reference to this vertex and update normal
	 * @param p
	 */
	protected void removePoly(Polygon p){
		polys.remove(p);
		
		norm = Math3D.vectorSub(norm, p.getNorm());
	}
	
	
	/**
	 * Override Equals to check if Vertex is Equal to another
	 * @param v - Vertex to Check
	 * @return Result of check
	 */
	public boolean equals(Vertex v){
		if(v.getPoint().equals(p))
			return true;
		return false;
	}
}
