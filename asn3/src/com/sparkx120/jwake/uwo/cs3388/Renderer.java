package com.sparkx120.jwake.uwo.cs3388;

import java.util.ArrayList;

/**
 * Abstraction of a renderer
 * @author James Wake
 * @version 1.0
 *
 */
public abstract class Renderer {
	
	/**
	 * A Renderer must be able to render Object Lists
	 * @param objs - An ArrayList of PolyObject3D's
	 */
	public abstract void renderObjects(ArrayList<PolyObject3D> objs);
}
