package com.sparkx120.jwake.uwo.cs3388;

import java.util.ArrayList;

/**
 * A ray from a ray tracer. This contains all Objects that this ray has hit.
 * as well as the ray point e and vector d
 * @author James Wake
 *
 */
public class Ray {
	private ArrayList<ObjectIntersection<GenericObject>> intersectedObjects;
	public static final float NO_INTERSECTION = -1;
	private float lowestTVal;
	private GenericObject lowestTValObject;
	private Point lowestTValIntersect;
	private Point e;
	private Vector d;
	
	public Ray(int x, int y, CamObject3D camera){
		this.e = camera.gete();
		
		Vector n = camera.getn();
		Vector u = camera.getu();
		Vector v = camera.getv();
		float N = camera.getN();
		float W = camera.getr();
		float H = camera.gett();
		lowestTVal = NO_INTERSECTION;
		lowestTValObject = null;
		lowestTValIntersect = null;
		intersectedObjects = new ArrayList<ObjectIntersection<GenericObject>>(); 
		
		Vector a = Math3D.scalarMultiplyVector(n, -N);
		float bcoeff = (float) W*(((2F*x)/camera.getWidth())-1);
		Vector b = Math3D.scalarMultiplyVector(u, bcoeff);
		float ccoeff = (float) H*(((2F*y)/camera.getHeight())-1);
		Vector c = Math3D.scalarMultiplyVector(v, ccoeff);
		
		this.d = Math3D.vectorAdd(Math3D.vectorAdd(a, b), c);
	}
	
	public void addIntersectAt(float t, GenericObject obj){
		Vector mag = Math3D.scalarMultiplyVector(d, t);
		Point intersect = Math3D.addPoints(e, mag);
		
		if(lowestTVal == NO_INTERSECTION){
			lowestTVal = t;
			lowestTValObject = obj;
			lowestTValIntersect = intersect;
		}
		else
			if(t<lowestTVal){
				lowestTVal = t;
				lowestTValObject = obj;
				lowestTValIntersect = intersect;
			}
		
		intersectedObjects.add(new ObjectIntersection<GenericObject>(t, obj, intersect));
	}
	
	/**
	 * Generated Getter Setter Methods
	 */
	
	
	

	
	
	/**
	 * Generated Getters
	 */
	
	/**
	 * @return The List of Intersected Objects
	 */
	protected ArrayList<ObjectIntersection<GenericObject>> getIntersectedObjects() {
		return intersectedObjects;
	}
	
	/**
	 * @return the E Point
	 */
	protected Point getE() {
		return e;
	}

	/**
	 * @return the D Vector
	 */
	protected Vector getD() {
		return d;
	}
	
	/**
	 * @return the lowestTVal
	 */
	public float getLowestTVal() {
		return lowestTVal;
	}

	/**
	 * @return the lowestTValObject
	 */
	public GenericObject getLowestTValObject() {
		return lowestTValObject;
	}

	/**
	 * @return the lowestTValIntersect
	 */
	public Point getLowestTValIntersect() {
		return lowestTValIntersect;
	}
	
	
	
	
}
