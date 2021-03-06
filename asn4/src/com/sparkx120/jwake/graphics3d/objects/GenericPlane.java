package com.sparkx120.jwake.graphics3d.objects;

import java.awt.Color;

import com.sparkx120.jwake.graphics3d.base.Point;
import com.sparkx120.jwake.graphics3d.base.Ray;
import com.sparkx120.jwake.graphics3d.base.Vector;
import com.sparkx120.jwake.math.Matrix3D;

/**
 * The Generic Plane at z=0 transformed with a Matrix 
 * If restriction is enabled then the plane will be computed with transforms
 * and everything not within a radius of 1 of the circular non transformed plane
 * will be ignored
 * 
 * @author James Wake
 */
public class GenericPlane extends GenericObject{
	private boolean restricted;
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
			float specularFactor, float specularFalloff, float reflectionFactor, float refractionIndex, Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, reflectionFactor, refractionIndex, transform);
		restricted = false;
	}
	
	
	
	/**
	 * Restricted Plane Constructor
	 * @param base_color - The Base color of the object
	 * @param ambiant_c - The Ambiant Color of the object
	 * @param ambiantFactor - The Ambiant Color factor of the Object
	 * @param diffuse_c - The Diffuse Color of the Object
	 * @param diffuseFactor - The Diffuse Factor of the Object
	 * @param specular_c - The Specular Color of the Object
	 * @param specularFactor - The Specuar Factor of the Object
	 * @param specularFalloff - The Specular Falloff Factor
	 * @param transform - The Transformation Matrix for this Object
	 * @param restricted - If the plane should be restricted in a Radial Fashion
	 */
	public GenericPlane(Color base_color, Color ambiant_c, float ambiantFactor,
			Color diffuse_c, float diffuseFactor, Color specular_c,
			float specularFactor, float specularFalloff, float reflectionFactor, float refractionIndex, Matrix3D transform, boolean restricted) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, reflectionFactor, refractionIndex, transform);
		this.restricted = restricted;
	}
	
	/**
	 * Definition Constructor
	 * @param substring
	 */
	public GenericPlane(String substring) {
		super(substring);
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
			float t = -(e.getZ()/d.getZ());
			if(t>0)
				if(!restricted)
					ray.addIntersectAt(t, this);
				else{
					float x = e.getX() + d.getX()*t;
					float y = e.getY() + d.getY()*t;
					if((float) (Math.sqrt(x*x + y*y)) <= 1)
						ray.addIntersectAt(t, this);
				}
		}
	}

	@Override
	public Vector getNormalAt(Point p) {
		Vector norm = new Vector(0F, 0F, 1F);
		norm = this.getTransform().multiplyMatrixWithVector(norm);
		return norm;
	}



	@Override
	public Color getUVMapAt(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
