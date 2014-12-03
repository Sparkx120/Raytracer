package com.sparkx120.jwake.graphics3d.base;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import com.sparkx120.jwake.graphics3d.lights.LightObject;
import com.sparkx120.jwake.graphics3d.objects.GenericObject;
import com.sparkx120.jwake.graphics3d.objects.PolyObject3D;

/**
 * Defines the World and its objects
 * @author James Wake
 * @version 1.0
 *
 */
public class World {
	private ArrayList<PolyObject3D> polyObjs;
	private ArrayList<GenericObject> genObjs;
	private ArrayList<LightObject> lightObjs;
	private ArrayList<CamObject3D> camObjs;
	private float ambiantLight;
	private Color ambiantLightColor;
	
	//Future Variables
	//private float ambientLightFactor;
	
	/**
	 * Default Constructor for a World
	 */
	public World(){
		polyObjs = new ArrayList<PolyObject3D>();
		camObjs = new ArrayList<CamObject3D>();
		genObjs = new ArrayList<GenericObject>();
		lightObjs = new ArrayList<LightObject>();
		ambiantLight = 1.0F;
		ambiantLightColor = Color.WHITE;
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
	 * Add a Generic Object to this world
	 * @param genObj - The GenericObject
	 */
	public void addGenericObject(GenericObject genObj){
		genObjs.add(genObj);
	}
	
	/**
	 * Add a Light Object to this world
	 * @param lightObj - The LightObject
	 */
	public void addLightObject(LightObject lightObj){
		lightObjs.add(lightObj);
	}
	
	/**
	 * Gets All Renderable Objects
	 * @return All renderable Objects
	 */
	public ArrayList<PolyObject3D> getRenderableObjects(){
		return polyObjs;
	}
	
	/**
	 * Gets All Ray Renderable Objects (AKA GenericObjects)
	 * @return - All Ray Renderable Objects
	 */
	public ArrayList<GenericObject> getRayRenderableObjects(){
		return genObjs;
	}
	
	/**
	 * Gets All Light Objects
	 * @return - All Light Objects
	 */
	public ArrayList<LightObject> getLightObjects(){
		return lightObjs;
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

	/**
	 * @return the ambiantLight
	 */
	public float getAmbiantLight() {
		return ambiantLight;
	}

	/**
	 * @param ambiantLight the ambiantLight to set
	 */
	public void setAmbiantLight(float ambiantLight) {
		this.ambiantLight = ambiantLight;
	}

	/**
	 * @return the ambiantLightColor
	 */
	public Color getAmbiantLightColor() {
		return ambiantLightColor;
	}

	/**
	 * @param ambiantLightColor the ambiantLightColor to set
	 */
	public void setAmbiantLightColor(Color ambiantLightColor) {
		this.ambiantLightColor = ambiantLightColor;
	}
}
