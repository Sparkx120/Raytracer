package com.sparkx120.jwake.graphics3d.objects;

import java.awt.Color;
import java.util.Scanner;

import com.sparkx120.jwake.graphics3d.base.Point;
import com.sparkx120.jwake.graphics3d.base.Ray;
import com.sparkx120.jwake.graphics3d.base.Vector;
import com.sparkx120.jwake.math.Math3D;
import com.sparkx120.jwake.math.Matrix3D;

/**
 * Generic Sphere at origin with radius 1 transformed with a Matrix
 * @author James Wake
 *
 */
public class GenericSphere extends GenericObject{
	
	/**
	 * Default Constructor
	 * @param base_color - The Base color of the object
	 * @param ambiant_c - The Ambiant Color of the object
	 * @param ambiantFactor - The Ambiant Color factor of the Object
	 * @param diffuse_c - The Diffuse Color of the Object
	 * @param diffuseFactor - The Diffuse Factor of the Object
	 * @param specular_c - The Specular Color of the Object
	 * @param specularFactor - The Specuar Factor of the Object
	 * @param specularFalloff - The Specular Falloff Factor
	 * @param transform - The Transformation Matrix for this Object
	 */
	public GenericSphere(Color base_color, Color ambiant_c,
			float ambiantFactor, Color diffuse_c, float diffuseFactor,
			Color specular_c, float specularFactor, float specularFalloff, float reflectionFactor, float refractionIndex,
			Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, reflectionFactor, refractionIndex, transform);
	}
	
	/**
	 * Read String Def from file
	 * @param substring
	 */
	public GenericSphere(String substring) {
		super(substring);
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
		float magE = Math3D.magnitudeOfVector(new Vector(Math3D.origin,e));
		
		//fun quadratics
		float a = (magD * magD);
		float b = Math3D.dotProduct(e, d);
		float c = (magE * magE) - 1;
		
		float det = (b*b) - (a*c); //Check 4

//		System.out.println(det);
		
		// Intersection Calculations and Intersection Additions
		if(det == 0){
			float t = (float) ((b/(a)));
//			if(t>=0)
				ray.addIntersectAt(t, this);
			
		}
		
		if(det > 0){
			float t1 = (float) ((-b/(a)) + (Math.sqrt(det)/(a)));
			float t2 = (float) ((-b/(a)) - (Math.sqrt(det)/(a)));
			ray.addIntersectAt(t1, this);
			ray.addIntersectAt(t2, this);
		}
	}

	@Override
	public Vector getNormalAt(Point p) {
		p = this.getTransformInverse().multiplyMatrixWithPoint(p);
		Vector norm = new Vector(p.getX(), p.getY(), p.getZ());
		norm = Math3D.normalizeVector(norm);
		norm = this.getTransform().multiplyMatrixWithVector(norm);
		return norm;
	}
}
