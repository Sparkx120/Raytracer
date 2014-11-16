package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

public class GenericSphere extends GenericObject{

	public GenericSphere(Color base_color, Color ambiant_c,
			float ambiantFactor, Color diffuse_c, float diffuseFactor,
			Color specular_c, float specularFactor) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor);
		
	}

	@Override
	public void rayIntersect(Ray ray) {
		//define Spherical Geometry Intersection here
		Vector d = ray.getD();
		Point e = ray.getE();
		float magD = Math3D.magnitudeOfVector(d);
		float magE = Math3D.magnitudeOfVector(new Vector(Math3D.origin, e));
		
		float a = (magD * magD);
		float b = Math3D.dotProduct(e, d);
		float c = (magE * magE) - 1;
		
		float det = (b*b) - (a*c);
		
		
		if(det == 0){
			float t = (float) ((b/a) + (Math.sqrt(det)/a));
			ray.addIntersectAt(t, this);
		}
		
		if(det > 0){
			float t1 = (float) ((b/a) + (Math.sqrt(det)/a));
			float t2 = (float) ((b/a) - (Math.sqrt(det)/a));
			ray.addIntersectAt(t1, this);
			ray.addIntersectAt(t2, this);
		}
	}
	

}
