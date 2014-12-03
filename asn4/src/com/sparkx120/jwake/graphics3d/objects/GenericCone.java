package com.sparkx120.jwake.graphics3d.objects;

import java.awt.Color;

import com.sparkx120.jwake.graphics3d.base.Point;
import com.sparkx120.jwake.graphics3d.base.Ray;
import com.sparkx120.jwake.graphics3d.base.Vector;
import com.sparkx120.jwake.math.Math3D;
import com.sparkx120.jwake.math.Matrix3D;

public class GenericCone extends GenericObject{
	private GenericPlane bottomPlane;
	public GenericCone(Color base_color, Color ambiant_c, float ambiantFactor,
			Color diffuse_c, float diffuseFactor, Color specular_c,
			float specularFactor, float specularFalloff,
			float reflectionFactor,
			float refractionIndex, Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, reflectionFactor,
				refractionIndex, transform);
		Matrix3D bottomTransform = new Matrix3D(new float[][] {
				{1F,0,0,0},
				{0,1F,0,0},
				{0,0,1F,-1F},
				{0,0,0,1}
		});
		
		bottomTransform = this.getTransform().multiplyMatrixWithMatrix(bottomTransform);
		this.bottomPlane = new GenericPlane(this.getBase_color(), this.getAmbiant_c(), this.getAmbiantFactor(), this.getDiffuse_c(), this.getDiffuseFactor(),
				this.getSpecular_c(), this.getSpecularFactor(), this.getSpecularFalloff(), this.getReflectionFactor(),
				this.getRefractionIndex(), bottomTransform, true);
	}

	@Override
	public void rayIntersect(Ray ray) {
		//Ray computation
		Point eOriginal = ray.getE();
		Vector dOriginal = ray.getD();
		Point e = this.getTransformInverse().multiplyMatrixWithPoint(eOriginal);
		Vector d = this.getTransformInverse().multiplyMatrixWithVector(dOriginal);
		
		float a = d.getX()*d.getX() + d.getY()*d.getY() - 0.25F*d.getZ()*d.getZ();
		float b = e.getX()*d.getX() + e.getY()*d.getY() + 0.25F*d.getZ()*(1F-e.getZ());
		float c = e.getX()*e.getX() + e.getY()*e.getY() - 0.25F*(1F-e.getZ())*(1F-e.getZ());
		
		float det = (b*b) - (a*c); //Check 4
		
		// Intersection Calculations and Intersection Additions
		if(det == 0){
			float t = (float) ((b/(a)));
			float z = e.getZ() + d.getZ()*t;
			if(z <= 1 && z >= -1)
				ray.addIntersectAt(t, this);
			
		}
		
		if(det > 0){
			float t1 = (float) ((-b/(a)) + (Math.sqrt(det)/(a)));
			float t2 = (float) ((-b/(a)) - (Math.sqrt(det)/(a)));
			float z1 = e.getZ() + d.getZ()*t1;
			float z2 = e.getZ() + d.getZ()*t2;
			if(z1 <= 1 && z1 >= -1)
				ray.addIntersectAt(t1, this);
			if(z2 <= 1 && z2 >= -1)
				ray.addIntersectAt(t2, this);
		}
		this.bottomPlane.rayIntersect(ray);
	}

	@Override
	public Vector getNormalAt(Point p) {
		//TODO Handle Planes
		p = this.getTransformInverse().multiplyMatrixWithPoint(p);
		float x = 2*p.getX()*p.getX();
		float y = 2*p.getY()*p.getY();
		float z = ((1-p.getZ())/2);
		Vector norm = new Vector(x, y, z);
		float coeff = (float) Math.pow((x + y + z*z), -0.5);
		norm = Math3D.scalarMultiplyVector(norm, coeff);
		norm = this.getTransform().multiplyMatrixWithVector(norm);
		norm = Math3D.normalizeVector(norm);
		return norm;
	}

}
