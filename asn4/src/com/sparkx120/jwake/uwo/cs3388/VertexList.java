package com.sparkx120.jwake.uwo.cs3388;

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
	 * The Equivalence Factor used to decide if a vertex is the same as another
	 * set this to zero for 100% perfect matching (TURNED OFF)
	 */
	private float granularityFactor = 0.0F;
	
	/**
	 * Default Constructor
	 */
	public VertexList(){
		super();
	}
	
	public void setVertexEquivelenceFactor(float factor){
		granularityFactor = factor;
	}
	
	/**
	 * Does a check for a point with a certain error factor
	 * @param item - The Item to check for
	 * @return - If it is found
	 */
	public boolean containsRough(Vertex item){
		if(item == null)
			return false;
		Iterator<Vertex> it = this.iterator();
		while(it.hasNext()){
			Vertex pos = it.next();
			if(pos.equalsRough(item, granularityFactor))
				return true;
		}
		return false;
		
	}
	
	/**
	 * Make sure all Vertices are Unique on Add Ignore Vertices already in the list
	 * @param item - The item to add
	 * @return - If the addition was successful (returns false if already in the list)
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
	 * Returns the index of a vertex using the Vertex Equivalence Factor of this list
	 * @param item - The item to search for
	 * @return - The index if it exists. -1 if it does not
	 */
	public int indexOf(Vertex item){
		if(item == null)
			return -1;
		for(int i=0; i<this.size();i++){
			Vertex pos = this.get(i);
			if(pos.equalsRough(item, granularityFactor))
				return i;
		}
		return -1;
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
