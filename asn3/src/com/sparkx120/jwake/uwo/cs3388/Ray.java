package com.sparkx120.jwake.uwo.cs3388;

import java.util.ArrayList;

public class Ray {
	ArrayList<GenericObject> intersectedObjects;
	Point e;
	Vector d;
	
	public Ray(Point e, int x, int y, CamObject3D camera){
		this.e = e;
		
		Vector n = camera.getn();
		Vector u = camera.getu();
		Vector v = camera.getv();
		float N = camera.getN();
		float W = camera.getr();
		float H = camera.gett();
		
		Vector a = Math3D.scalarMultiplyVector(n, -N);
		float bcoeff = W*(((2*x)/camera.getWidth())-1);
		Vector b = Math3D.scalarMultiplyVector(u, bcoeff);
		float ccoeff = H*(((2*y)/camera.getHeight())-1);
		Vector c = Math3D.scalarMultiplyVector(v, ccoeff);
		
		this.d = Math3D.vectorAdd(Math3D.vectorAdd(a, b), c);
	}
	
	/**
	 * Generated Getter Setter Methods
	 */
	
	
	protected ArrayList<GenericObject> getIntersectedObjects() {
		return intersectedObjects;
	}

	protected Point getE() {
		return e;
	}

	protected Vector getD() {
		return d;
	}
	
	
	
	
}
