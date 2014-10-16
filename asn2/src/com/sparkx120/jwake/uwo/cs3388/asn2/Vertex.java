package com.sparkx120.jwake.uwo.cs3388.asn2;

public class Vertex {
	private Point p;
	private Vector norm;
	
	/**
	 * Empty Constructor
	 */
	public Vertex(){
		
	}
	
	/**
	 * Default Constructor Providing the Vertex Point
	 * @param p - The Point defining the Vertex
	 */
	public Vertex(Point p){
		this.p = p;
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
	public void setNormal(Vector n){
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
}
