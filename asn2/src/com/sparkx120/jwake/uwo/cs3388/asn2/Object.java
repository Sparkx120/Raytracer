package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.util.ArrayList;

/**
 * 3D Object Class. Notice that I do not use a Normal List as Normals are integrated
 * into Polygons and Vertices implicitly by my Object Design and therefore would be
 * redundant
 * @author James Wake
 * @version 1.0
 *
 */
public class Object {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
}
