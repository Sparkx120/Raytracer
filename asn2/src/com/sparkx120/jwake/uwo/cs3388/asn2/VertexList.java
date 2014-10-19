package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Vertex List that prevents adding multiple copies of the same vertex
 * @author James Wake
 * @version 1.0 
 */
public class VertexList extends ArrayList<Vertex> implements Serializable{

	/**
	 * Default serial version uid
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default Constructor
	 */
	public VertexList(){
		super();
	}
	
	/**
	 * Make sure all Vertices are Unique on Add Ignore Vertices already in the list
	 */
	public boolean add(Vertex item){
		if(this.contains(item))
			return false;
		else{
			super.add(item);
			return true;
		}
	}
	
	/**
	 * String Override
	 */
	@Override
	public String toString(){
		Iterator<Vertex> it = this.iterator();
		String out = "";
		while(it.hasNext()){
			Vertex v = it.next();
			out += "x: " + v.getPoint().getX() + " y: " + v.getPoint().getY() + " z: " + v.getPoint().getZ() + " h: " + v.getPoint().getH() + "\n";
		}
		return out;
	}
}
