package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;
import java.util.Scanner;

/**
 * Abstract of a Generic Object for Ray Tracing etc
 * @author James Wake
 *
 */
public abstract class GenericObject {
	private Color base_color;
	private Color ambiant_c;
	private Color diffuse_c;
	private Color specular_c;
	private float ambiantFactor;
	private float diffuseFactor;
	private float specularFactor;
	private float specularFalloff;
	private float reflectionFactor;
	private float refractionFactor;
	private float refractionIndex;
	private Matrix3D transform;
	private Matrix3D transformInverse;

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
	public GenericObject(Color base_color, Color ambiant_c, float ambiantFactor, Color diffuse_c, float diffuseFactor, Color specular_c, float specularFactor, float specularFalloff, float reflectionFactor, float refractionIndex, Matrix3D transform){
		this.base_color = base_color;
		this.ambiant_c = ambiant_c;
		this.diffuse_c = diffuse_c;
		this.specular_c = specular_c;
		this.ambiantFactor = ambiantFactor;
		this.diffuseFactor = diffuseFactor;
		this.specularFactor = specularFactor;
		this.specularFalloff = specularFalloff;
		this.reflectionFactor = reflectionFactor;
//		this.refractionFactor = refractionFactor;
		this.refractionIndex = refractionIndex;
		
		setTransform(transform);
	}
	
	public GenericObject(String obj) {
		decodeString(obj);
	}

	/**
	 * Sets the transformation matrix for this Object
	 * @param transform - The Matrix
	 */
	public void setTransform(Matrix3D transform){
		this.transform = transform;
		this.transformInverse = transform.inverseMatrix();
	}
	
	/**
	 * @return The Transformation Matrix of this Object
	 */
	public Matrix3D getTransform(){
		return this.transform;
	}
	
	/**
	 * @return The Inverse of the Transformation Matrix of this Object
	 */
	public Matrix3D getTransformInverse(){
		return this.transformInverse;
	}
	
	/**
	 * Runs a Ray Intersection on a Ray. Adds intersection info to the Ray
	 * @param ray - The Ray
	 */
	public abstract void rayIntersect(Ray ray);
	
	/**
	 * Returns the Normal at a Point p. Assumes that P is on the surface of the Object
	 * Undefined behaviour if the point is not on the surface
	 * @param p - The point to compute the normal for
	 * @return - The Normal Vector
	 */
	public abstract Vector getNormalAt(Point p);
	
	/**
	 * Decode a String into an Object
	 * @param sphere
	 */
	private void decodeString(String obj){
		//String[] params = sphere.split("\n");
		Scanner scan = new Scanner(obj);
		float r = scan.nextFloat();
		float g = scan.nextFloat();
		float b = scan.nextFloat();
		this.setBase_color(new Color(r, g, b));
		r = scan.nextFloat();
		g = scan.nextFloat();
		b = scan.nextFloat();
		this.setAmbiant_c(new Color(r, g, b));
		this.setAmbiantFactor(scan.nextFloat());
		r = scan.nextFloat();
		g = scan.nextFloat();
		b = scan.nextFloat();
		this.setDiffuse_c(new Color(r, g, b));
		this.setDiffuseFactor(scan.nextFloat());
		r = scan.nextFloat();
		g = scan.nextFloat();
		b = scan.nextFloat();
		this.setSpecular_c(new Color(r, g, b));
		this.setSpecularFactor(scan.nextFloat());
		this.setSpecularFalloff(scan.nextFloat());
		String matrixS = obj.substring(obj.indexOf("matrix") + 6);
		Matrix3D matrix = new Matrix3D(matrixS);
		this.setTransform(matrix);
		scan.close();
	}
	
	
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

	/**
	 * @return the reflectionFactor
	 */
	public float getReflectionFactor() {
		return reflectionFactor;
	}

	/**
	 * @param reflectionFactor the reflectionFactor to set
	 */
	public void setReflectionFactor(float reflectionFactor) {
		this.reflectionFactor = reflectionFactor;
	}

	/**
	 * @return the refractionFactor
	 */
	public float getRefractionFactor() {
		return refractionFactor;
	}

	/**
	 * @param refractionFactor the refractionFactor to set
	 */
	public void setRefractionFactor(float refractionFactor) {
		this.refractionFactor = refractionFactor;
	}

	/**
	 * @return the refractionIndex
	 */
	public float getRefractionIndex() {
		return refractionIndex;
	}

	/**
	 * @param refractionIndex the refractionIndex to set
	 */
	public void setRefractionIndex(float refractionIndex) {
		this.refractionIndex = refractionIndex;
	}
}
