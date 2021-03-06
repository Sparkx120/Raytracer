package com.sparkx120.jwake.graphics3d.objects;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sparkx120.jwake.graphics3d.base.Point;
import com.sparkx120.jwake.graphics3d.base.Ray;
import com.sparkx120.jwake.graphics3d.base.Vector;
import com.sparkx120.jwake.math.Math3D;
import com.sparkx120.jwake.math.Matrix3D;

public class GenericCylinder extends GenericObject{
	
	private GenericPlane topPlane;
	private GenericPlane bottomPlane;
	private boolean enableTop;
	private boolean enableBottom;

	public GenericCylinder(Color base_color, Color ambiant_c,
			float ambiantFactor, Color diffuse_c, float diffuseFactor,
			Color specular_c, float specularFactor, float specularFalloff,
			float reflectionFactor,
			float refractionIndex, Matrix3D transform) {
		super(base_color, ambiant_c, ambiantFactor, diffuse_c, diffuseFactor,
				specular_c, specularFactor, specularFalloff, reflectionFactor,
				refractionIndex, transform);
		
		setUpCylinderVars();
	}
	
	public GenericCylinder(Color base_color, float ambiantFactor, float diffuseFactor,
			float specularFactor, Matrix3D transform) {
		super(base_color, ambiantFactor, diffuseFactor, specularFactor, transform);
		
		setUpCylinderVars();
	}
	
	public GenericCylinder(Color base_color, float ambiantFactor, float diffuseFactor,
			float specularFactor, BufferedImage UVMap, Matrix3D transform) {
		super(base_color, ambiantFactor, diffuseFactor, specularFactor, UVMap, transform);
		
		setUpCylinderVars();
	}
	
	
	/**
	 * Sets up Cylinder Specific Variables
	 */
	private void setUpCylinderVars(){
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
				this.getRefractionIndex(), topTransform, true);
		
		this.bottomPlane = new GenericPlane(this.getBase_color(), this.getAmbiant_c(), this.getAmbiantFactor(), this.getDiffuse_c(), this.getDiffuseFactor(),
				this.getSpecular_c(), this.getSpecularFactor(), this.getSpecularFalloff(), this.getReflectionFactor(),
				this.getRefractionIndex(), bottomTransform, true);
		this.enableTop = true;
		this.enableBottom = true;
		
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
		
		if(enableTop)
			topPlane.rayIntersect(ray);
		if(enableBottom)
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
	
	public void enableTopPlane(boolean top){
		this.enableTop = top;
	}
	
	public void enableBottomPlane(boolean bottom){
		this.enableBottom = bottom;
	}
	
	public void setBottomPlaneColor(Color color){
		this.bottomPlane.setBase_color(color);
		this.bottomPlane.setAmbiant_c(color);
		this.bottomPlane.setDiffuse_c(color);
	}
	
	public void setTopPlaneColor(Color color){
		this.topPlane.setBase_color(color);
		this.topPlane.setAmbiant_c(color);
		this.topPlane.setDiffuse_c(color);
	}

	@Override
	public Color getUVMapAt(Point p) {
		if(this.getUVMap() == null)
			return null;
		BufferedImage UVMap = this.getUVMap();
		p = this.getTransformInverse().multiplyMatrixWithPoint(p);
		float x = p.getX();
		float y = p.getY();
		float z = p.getZ()+1;
		float theta = (float) (Math.atan(y/x) + (Math.PI/2));
		int xPix = (int) ((float) (Math.round((UVMap.getWidth()*theta)/(2*Math.PI))));
		int yPix = (int) ((float) (Math.round((UVMap.getHeight()*z)/(2))));
		if(xPix >= UVMap.getWidth())
			xPix = UVMap.getWidth() - 1;
		if(yPix >= UVMap.getHeight())
			yPix = UVMap.getHeight() - 1;
		return new Color(UVMap.getRGB(xPix, yPix));
	}


}
