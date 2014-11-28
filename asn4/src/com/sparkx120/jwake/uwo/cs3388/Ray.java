package com.sparkx120.jwake.uwo.cs3388;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	public Ray(int x, int y, CamObject3D camera, int superSampleRate){
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
		float bcoeff = (float) W*(((2F*x)/(camera.getWidth()*superSampleRate))-1);
		Vector b = Math3D.scalarMultiplyVector(u, bcoeff);
		float ccoeff = (float) H*(((2F*y)/(camera.getHeight()*superSampleRate))-1);
		Vector c = Math3D.scalarMultiplyVector(v, ccoeff);
		this.NO_INTERSECT = true;
		
		this.d = Math3D.vectorAdd(Math3D.vectorAdd(a, b), c);
//		System.out.println(d);
	}
	
	/**
	 * Define a Ray with two points
	 * @param objectPoint - Start Point
	 * @param targetPoint - The Target Point
	 */
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
	
	/**
	 * Directly Define a Ray giving it it's e Point and d Vector
	 * @param e - The Point e where the ray is firing from
	 * @param d - The Direction of the ray
	 */
	public Ray(Point e, Vector d){
		
		lowestTVal = 0;
		lowestTValObject = null;
		lowestTValIntersect = null;
		intersectedObjects = new ArrayList<ObjectIntersection<GenericObject>>();
		this.NO_INTERSECT = true;
		
		this.e = e;
		this.d = d;
	}
	
	public void addIntersectAt(float t, GenericObject obj){
		//t = Math.abs(t); //Temporary thing
		
		Vector dt = Math3D.scalarMultiplyVector(d, t);
		Point intersect = Math3D.addPoints(e, dt);
		
		//System.out.println(t + " " + dt + " " + intersect);
		if(NO_INTERSECT && t>0){
			lowestTVal = t;
			lowestTValObject = obj;
			lowestTValIntersect = intersect;
			NO_INTERSECT = false;
		}
		else
			if(t<lowestTVal && t>0){
				lowestTVal = t;
				lowestTValObject = obj;
				lowestTValIntersect = intersect;
			}
		
		intersectedObjects.add(new ObjectIntersection<GenericObject>(t, obj, intersect));
	}
	
	/**
	 * Adds a detected intersection At a TVal with an Obj and Sub Objects
	 * @param t - The TVal
	 * @param obj - The Object intersected
	 * @param sub - Sub Objects of the main Object (not used yet)
	 */
	public void addIntersectAt(float t, GenericObject obj, ArrayList<GenericObject> sub){
		int i = -1;
		Iterator<GenericObject> it = sub.iterator();
		boolean bestIntersection = true;
		while(it.hasNext()){
			GenericObject gen = it.next();
			if((i = intersectedObjects.indexOf(gen)) != -1){
				ObjectIntersection<GenericObject> intersect = intersectedObjects.get(i);
				if(intersect.getTVal() < lowestTVal){
					bestIntersection = false;
					break;
				}
			}
		}
		if(bestIntersection){
			addIntersectAt(t, obj);
		}
		
	}
	
	/**
	 * Computes the TVal of a point along the ray
	 * @param p - The Point
	 * @return - The TVal
	 */
	public float rayDetect(Point p){
		float t = 0F;
		if(d.getX() != 0){
			t = (p.getX() - e.getX())/d.getX();
		}
		else
			if(d.getY() != 0){
				t = (p.getY() - e.getY())/d.getY();
			}
			else
				if(d.getZ() != 0){
					t = (p.getZ() - e.getZ())/d.getZ();
				}
		return t;
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
