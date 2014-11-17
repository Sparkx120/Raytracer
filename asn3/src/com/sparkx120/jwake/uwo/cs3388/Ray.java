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
	boolean NO_INTERSECT;
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
		lowestTVal = 0;
		lowestTValObject = null;
		lowestTValIntersect = null;
		intersectedObjects = new ArrayList<ObjectIntersection<GenericObject>>(); 
		
		Vector a = Math3D.scalarMultiplyVector(n, -N);
		float bcoeff = (float) W*(((2F*x)/camera.getWidth())-1);
		Vector b = Math3D.scalarMultiplyVector(u, bcoeff);
		float ccoeff = (float) H*(((2F*y)/camera.getHeight())-1);
		Vector c = Math3D.scalarMultiplyVector(v, ccoeff);
		this.NO_INTERSECT = true;
		
		this.d = Math3D.vectorAdd(Math3D.vectorAdd(a, b), c);
//		System.out.println(d);
	}
	
	public Ray(Point objectPoint, Point targetPoint){
		
		lowestTVal = 0;
		lowestTValObject = null;
		lowestTValIntersect = null;
		intersectedObjects = new ArrayList<ObjectIntersection<GenericObject>>();
		this.NO_INTERSECT = true;
		
		this.e = objectPoint;
		this.d = new Vector(objectPoint, targetPoint);
		this.d = Math3D.scalarMultiplyVector(this.d, 1/Math3D.magnitudeOfVector(this.d));
	}
	
	public void addIntersectAt(float t, GenericObject obj){
		//t = Math.abs(t); //Temporary thing
		
		Vector dt = Math3D.scalarMultiplyVector(d, t);
		Point intersect = Math3D.addPoints(e, dt);
		
		//System.out.println(t + " " + dt + " " + intersect);
		if(NO_INTERSECT){
			lowestTVal = t;
			lowestTValObject = obj;
			lowestTValIntersect = intersect;
			NO_INTERSECT = false;
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
	 * @return - If the ray has intersected an Object
	 */
	public boolean didIntersect(){
		return !NO_INTERSECT;
	}
	
	/**
	 * Generated Getter Setter Methods
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
