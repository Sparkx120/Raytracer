package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

public class GenericSphere extends GenericObject{

	public GenericSphere(Color base_color, Color ambiant_c,
			float ambiantFactor, Color diffuse_c, float diffuseFactor,
			Color specular_c, float specularFactor, float specularFalloff,
			Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, transform);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void rayIntersect(Ray ray) {
		//Ray computation
		Point eOriginal = ray.getE();
		Vector dOriginal = ray.getD();
		Point e = this.getTransformInverse().multiplyMatrixWithPoint(eOriginal);
		Vector d = this.getTransformInverse().multiplyMatrixWithVector(dOriginal);
		
//		System.out.println(d + " " + dOriginal);
		
		//define Spherical Geometry Intersection here
		float magD = Math3D.magnitudeOfVector(d);
		float magE = Math3D.magnitudeOfVector(new Vector(Math3D.origin, e));
		
		//fun quadratics
		float a = (magD * magD);
		float b = Math3D.dotProduct(e, d);
		float c = (magE * magE) - 1;
		
		float det = (b*b) - (a*c); //Check 4

//		System.out.println(det);
		
		if(det == 0){
			float t = (float) ((b/a) + (Math.sqrt(det)/a));
			if(t<=0)
				ray.addIntersectAt(t, this);
			
		}
		
		if(det > 0){
			float t1 = (float) ((b/a) + (Math.sqrt(det)/a));
			float t2 = (float) ((b/a) - (Math.sqrt(det)/a));
			if(t1 <= 0)
				ray.addIntersectAt(t1, this);
			if(t2 <= 0)
				ray.addIntersectAt(t2, this);
		}
	}

	@Override
	public Vector getNormalAt(Point p) {
		Vector norm = new Vector(p.getX(), p.getY(), p.getZ());
		norm = this.getTransform().multiplyMatrixWithVector(norm);
		return norm;
	}
	

}
