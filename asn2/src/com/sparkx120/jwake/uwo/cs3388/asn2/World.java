package com.sparkx120.jwake.uwo.cs3388.asn2;

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
	
	public void addPolyObject(PolyObject3D poly){
		polyObjs.add(poly);
	}
	
	public void addCameraObject(CamObject3D cam){
		camObjs.add(cam);
	}
	
	public ArrayList<PolyObject3D> getRenderableObjects(){
		return polyObjs;
	}
	
	public ArrayList<CamObject3D> getCameraObjects(){
		return camObjs;
	}
	
	public int sizeOfWorldPolys(){
		Iterator<PolyObject3D> it = polyObjs.iterator();
		int size = 0;
		while(it.hasNext()){
			size += it.next().getNumberOfPolys();
		}
		return size;
	}
	
	public int sizeOfWorldObjects(){
		return polyObjs.size() + camObjs.size();
	}
}
