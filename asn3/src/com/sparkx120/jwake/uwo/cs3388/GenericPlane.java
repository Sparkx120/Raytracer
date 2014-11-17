package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

/**
 * The Generic Plane at z=0 transformed with a Matrix 
 * @author James Wake
 */
public class GenericPlane extends GenericObject{
	
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
	public GenericPlane(Color base_color, Color ambiant_c, float ambiantFactor,
			Color diffuse_c, float diffuseFactor, Color specular_c,
			float specularFactor, float specularFalloff, Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, transform);
	}

	@Override
	public void rayIntersect(Ray ray) {
		//Ray computation
		Point eOriginal = ray.getE();
		Vector dOriginal = ray.getD();
		Point e = this.getTransformInverse().multiplyMatrixWithPoint(eOriginal);
		Vector d = this.getTransformInverse().multiplyMatrixWithVector(dOriginal);
		
		//Intersection Commputation and Additions
		if(d.getZ() != 0){
			float t = -e.getZ()/d.getZ();
			ray.addIntersectAt(t, this);
		}
	}

	@Override
	public Vector getNormalAt(Point p) {
		Vector norm = new Vector(0F, 0F, 1F);
		norm = this.getTransform().multiplyMatrixWithVector(norm);
		return norm;
	}

}
