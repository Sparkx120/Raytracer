package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

public class GenericCylinder extends GenericObject{
	
	private GenericPlane topPlane;
	private GenericPlane bottomPlane;

	public GenericCylinder(Color base_color, Color ambiant_c,
			float ambiantFactor, Color diffuse_c, float diffuseFactor,
			Color specular_c, float specularFactor, float specularFalloff,
			float reflectionFactor, float refractionFactor,
			float refractionIndex, Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, reflectionFactor,
				refractionFactor, refractionIndex, transform);
		
		Matrix3D topTransform = new Matrix3D(new float[][] {
				{1F,0,0,0},
				{0,1F,0,0},
				{0,0,1F,1F},
				{0,0,0,1}
		});
		
		Matrix3D bottomTransform = new Matrix3D(new float[][] {
				{1F,0,0,0},
				{0,1F,0,0},
				{0,0,1F,-1F},
				{0,0,0,1}
		});
		topTransform = this.getTransform().multiplyMatrixWithMatrix(topTransform);
		bottomTransform = this.getTransform().multiplyMatrixWithMatrix(bottomTransform);
		
		this.topPlane = new GenericPlane(this.getBase_color(), this.getAmbiant_c(), this.getAmbiantFactor(), this.getDiffuse_c(), this.getDiffuseFactor(),
				this.getSpecular_c(), this.getSpecularFactor(), this.getSpecularFalloff(), this.getReflectionFactor(),
				this.getRefractionFactor(), this.getRefractionIndex(), topTransform, true);
		
		this.bottomPlane = new GenericPlane(this.getBase_color(), this.getAmbiant_c(), this.getAmbiantFactor(), this.getDiffuse_c(), this.getDiffuseFactor(),
				this.getSpecular_c(), this.getSpecularFactor(), this.getSpecularFalloff(), this.getReflectionFactor(),
				this.getRefractionFactor(), this.getRefractionIndex(), bottomTransform, true);
	}

	@Override
	public void rayIntersect(Ray ray) {
		//Ray computation
		Point eOriginal = ray.getE();
		Vector dOriginal = ray.getD();
		Point e = this.getTransformInverse().multiplyMatrixWithPoint(eOriginal);
		Vector d = this.getTransformInverse().multiplyMatrixWithVector(dOriginal);
		
		float a = d.getX()*d.getX() + d.getY()*d.getY();
		float b = e.getX()*d.getX() + e.getY()*d.getY();
		float c = e.getX()*e.getX() + e.getY()*e.getY() - 1;
		
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
		
		topPlane.rayIntersect(ray);
		bottomPlane.rayIntersect(ray);
	}

	@Override
	public Vector getNormalAt(Point p) {
		//TODO Handle Planes
		p = this.getTransformInverse().multiplyMatrixWithPoint(p);
		Vector norm = new Vector(p.getX(), p.getY(), 0F);
		float coeff = (float) (1/(Math.sqrt(p.getX()*p.getX() + p.getY()*p.getY())));
		norm = Math3D.scalarMultiplyVector(norm, coeff);
		norm = this.getTransform().multiplyMatrixWithVector(norm);
		norm = Math3D.normalizeVector(norm);
		return norm;
	}

}
