package com.sparkx120.jwake.graphics3d.renderers;

import java.awt.Color;
import java.util.ArrayList;

import com.sparkx120.jwake.graphics3d.objects.PolyObject3D;

/**
 * Abstraction of a renderer
 * @author James Wake
 * @version 1.0
 *
 */
public abstract class Renderer {
	
	private RendererType type;
	private boolean visualDebug;
	
	public Renderer(RendererType type){
		this.type = type;
		this.visualDebug = false;
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
	public abstract Color renderRayPixel(int x, int y, boolean debug);
	
	public abstract void renderToScreen();
	
	public abstract void renderToFile(String file);
	
	public void setVisualDebug(boolean debug){
		this.visualDebug = debug;
	}
	
	public boolean getVisualDebug(){
		return this.visualDebug;
	}
	
	/**
	 * Returns the Renderer's Type
	 * @return
	 */
	public RendererType getType(){
		return type;
	}
}
