package com.sparkx120.jwake.uwo.cs3388;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Defines the World and its objects
 * @author James Wake
 * @version 1.0
 *
 */
public class World {
	ArrayList<PolyObject3D> polyObjs;
	ArrayList<CamObject3D> camObjs;
	
	//Future Variables
	//private float ambientLightFactor;
	
	/**
	 * Default Constructor for a World
	 */
	public World(){
		polyObjs = new ArrayList<PolyObject3D>();
		camObjs = new ArrayList<CamObject3D>();
	}
	
	/**
	 * Add a PolyObject3D to this world
	 * @param poly - The PolyObject3D
	 */
	public void addPolyObject(PolyObject3D poly){
		polyObjs.add(poly);
	}
	
	/**
	 * Add a Camera Object to this world
	 * @param cam - The Camera
	 */
	public void addCameraObject(CamObject3D cam){
		camObjs.add(cam);
	}
	
	/**
	 * Gets All Renderable Objects
	 * @return All renderable Objects
	 */
	public ArrayList<PolyObject3D> getRenderableObjects(){
		return polyObjs;
	}
	
	/**
	 * Gets all Camera Objects
	 * @return The Camera Object
	 */
	public ArrayList<CamObject3D> getCameraObjects(){
		return camObjs;
	}
	
	/**
	 * Returns the size of the world in the number of Polygons in it.
	 * @return - The total number of polygons
	 */
	public int sizeOfWorldPolys(){
		Iterator<PolyObject3D> it = polyObjs.iterator();
		int size = 0;
		while(it.hasNext()){
			size += it.next().getNumberOfPolys();
		}
		return size;
	}
	
	/**
	 * Returns the size of the World in Objects
	 * @return - The number of Objects
	 */
	public int sizeOfWorldObjects(){
		return polyObjs.size() + camObjs.size();
	}
}
