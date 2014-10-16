package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Polygon Class. Represents 3 Vertex Polygons with vertices a,b,c in
 * counter clockwise order for simple normal calculations
 * @author James Wake
 * @version 1.0
 *
 */
public class Polygon {
	private Vertex a;
	private Vertex b;
	private Vertex c;
	
	private Vector norm;
	
	/**
	 * Constructor for a Polygon
	 * Makes a polygon with a correct normal based on Vertex A to C in Counterclockwise order.
	 * @param a - Vertex A
	 * @param b - Vertex B
	 * @param c - Vertex C
	 */
	public Polygon(Vertex a, Vertex b, Vertex c){
		this.a = a;
		this.b = b;
		this.c = c;
		
		this.norm = calculatePolyNormal();
		
		a.addPoly(this);
		b.addPoly(this);
		c.addPoly(this);
	}
	
	/**
	 * Returns Vertex A
	 * @return Vertex A
	 */
	public Vertex getVertexA(){
		return a;
	}
	
	/**
	 * Returns Vertex B
	 * @return Vertex B
	 */
	public Vertex getVertexB(){
		return b;
	}

	/** 
	 * Returns Vertex C
	 * @return Vertex C
	 */
	public Vertex getVertexC(){
		return c;
	}
	
	public Vector getNorm(){
		return norm;
	}
	
	public void Destroy(){
		a.removePoly(this);
		b.removePoly(this);
		c.removePoly(this);
	}
	
	/**
	 * Computes the Normal Vector for this Polygon
	 * @return The Normal Vector
	 */
	private Vector calculatePolyNormal(){
		//TODO NOTE THIS NEEDS DOUBLE CHECK WHEN TESTING
		Vector A = new Vector(a.getPoint(), b.getPoint());
		Vector B = new Vector(b.getPoint(), c.getPoint());
		
		return Math3D.crossProdVectors(A, B);
	}
}
