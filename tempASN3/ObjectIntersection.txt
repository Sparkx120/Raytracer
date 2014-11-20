package com.sparkx120.jwake.uwo.cs3388;

/**
 * A representation of an Object Intersection at parameter value t
 * @author James Wake
 *
 * @param <O> - The Type of object that was intersect for instance a GenericObject
 */
public class ObjectIntersection<O>{
	/**
	 * The Object that is being intersected
	 */
	private O obj;
	/**
	 * The parameter value that it was intersected at
	 */
	private float tval;
	/**
	 * The Point of Intersection
	 */
	private Point intersect;
	
	/**
	 * Default constructor to set values
	 * @param tval - The T value that the object was intersected at
	 * @param obj - The Object that was intersected
	 */
	public ObjectIntersection(float tval, O obj, Point intersect){
		this.obj = obj;
		this.tval = tval;
		this.intersect = intersect;
	}
	
	/**
	 * Return the intersected object
	 * @return - The intersected object
	 */
	public O getObject(){
		return obj;
	}
	
	/**
	 * Returns the t valye at intersection with the object
	 * @return - The t value
	 */
	public float getTVal(){
		return tval;
	}
	
	/**
	 * Returns the Point of Intersection
	 * @return - The Point of intersection
	 */
	public Point getIntersect(){
		return intersect;
	}
}
