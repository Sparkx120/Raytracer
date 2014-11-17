package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

public abstract class GenericObject {
	private Color base_color;
	private Color ambiant_c;
	private Color diffuse_c;
	private Color specular_c;
	private float ambiantFactor;
	private float diffuseFactor;
	private float specularFactor;
	private float specularFalloff;
	private Matrix3D transform;
	private Matrix3D transformInverse;

	public GenericObject(Color base_color, Color ambiant_c, float ambiantFactor, Color diffuse_c, float diffuseFactor, Color specular_c, float specularFactor, float specularFalloff, Matrix3D transform){
		this.base_color = base_color;
		this.ambiant_c = ambiant_c;
		this.diffuse_c = diffuse_c;
		this.specular_c = specular_c;
		this.ambiantFactor = ambiantFactor;
		this.diffuseFactor = diffuseFactor;
		this.specularFactor = specularFactor;
		this.specularFalloff = specularFalloff;
		
		setTransform(transform);
	}
	
	public void setTransform(Matrix3D transform){
		this.transform = transform;
		this.transformInverse = transform.inverseMatrix();
	}
	
	public Matrix3D getTransform(){
		return this.transform;
	}
	
	public Matrix3D getTransformInverse(){
		return this.transformInverse;
	}
	
	public abstract void rayIntersect(Ray ray);
	
	public abstract Vector getNormalAt(Point p);
	
	/**
	 * Generated Getters and Setters
	 */
	public Color getBase_color() {
		return base_color;
	}

	public void setBase_color(Color base_color) {
		this.base_color = base_color;
	}

	public Color getAmbiant_c() {
		return ambiant_c;
	}

	public void setAmbiant_c(Color ambiant_c) {
		this.ambiant_c = ambiant_c;
	}

	public Color getDiffuse_c() {
		return diffuse_c;
	}

	public void setDiffuse_c(Color diffuse_c) {
		this.diffuse_c = diffuse_c;
	}

	public Color getSpecular_c() {
		return specular_c;
	}

	public void setSpecular_c(Color specular_c) {
		this.specular_c = specular_c;
	}

	public float getAmbiantFactor() {
		return ambiantFactor;
	}

	public void setAmbiantFactor(float ambiantFactor) {
		this.ambiantFactor = ambiantFactor;
	}

	public float getDiffuseFactor() {
		return diffuseFactor;
	}

	public void setDiffuseFactor(float diffuseFactor) {
		this.diffuseFactor = diffuseFactor;
	}

	public float getSpecularFactor() {
		return specularFactor;
	}

	public void setSpecularFactor(float specularFactor) {
		this.specularFactor = specularFactor;
	}

	/**
	 * @return the specularFalloff
	 */
	public float getSpecularFalloff() {
		return specularFalloff;
	}

	/**
	 * @param specularFalloff the specularFalloff to set
	 */
	public void setSpecularFalloff(float specularFalloff) {
		this.specularFalloff = specularFalloff;
	}
}
