package com.sparkx120.jwake.uwo.cs3388;

import java.util.ArrayList;

/**
 * Abstraction of a renderer
 * @author James Wake
 * @version 1.0
 *
 */
public abstract class Renderer {
	
	private RendererType type;
	
	public Renderer(RendererType type){
		this.type = type;
	}
	
	/**
	 * A Renderer must be able to render Object Lists if it is an Object Type renderer
	 * @param objs - An ArrayList of PolyObject3D's
	 */
	public abstract void renderObjects(ArrayList<PolyObject3D> objs);
	
	/**
	 * Renders a pixel in a rayTrace
	 * @param x - The X coordinate of the trace
	 * @param y - The Y coordinate of the trace
	 * @param data - The ray data from reflections etc.
	 */
	public abstract void renderRayPixel(int x, int y);
	
	/**
	 * Returns the Renderer's Type
	 * @return
	 */
	public RendererType getRendererType(){
		return type;
	}
}
