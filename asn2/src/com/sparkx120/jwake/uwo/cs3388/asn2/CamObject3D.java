package com.sparkx120.jwake.uwo.cs3388.asn2;

/**
 * Defines a Synthetic Camera in World Coordinates
 * @author James Wake
 * @version
 */
public class CamObject3D extends Object3D{
	/**
	 * Camera Position Point
	 */
	private Point p;
	/**
	 * Camera Up Vector
	 */
	private Vector u;
	/**
	 * Camera L/R Vector
	 */
	private Vector v;
	/**
	 * Camera Gaze Vector
	 */
	private Vector n;
	/**
	 * Near Edge Restrictons of Camera (Computed by Default to be a Square)
	 */
	private float l;
	private float r;
	private float t;
	private float b;
	/**
	 * Camera Near Plane [0] (l, t,-N) [1] (r, t, -N) [2] (r, b, -N) [3] (l, b, -N)
	 * These are P1 P2 P3 P4 respectively from the notes
	 */
	private Point[] nearPlane;
	/**
	 * The World this Camera is in
	 */
	private World world;
	/**
	 * Constructs a new Synthetic Camera at Point P with "Gaze" Vector n and "Up" Vector u
	 * @param p - The Point where the camera should be
	 * @param n - The Normal Vector
	 * @param u - The Up Gaze Vector
	 * @param w - The World this camera is in
	 */
	public CamObject3D(Point p, Vector n, Vector u, World w){
		this.p = p;
		this.n = n;
		this.u = u;
		this.v = Math3D.crossProdVectors(n, u);
		this.world = w;
	}
	
	public void computeFrame(Renderer renderer){
		//Do Matrix Stuff Here
		
		//Get Renderer Size and Send renderer Object List with transformed points
	}
	
	/**
	 * Camera Movement Methods
	 */
	
	public void translateCameraX(){
		
	}
	
	public void translateCameraY(){
		
	}

	public void translateCameraZ(){
		
	}
	
	public void rotateCameraX(){
		
	}
	
	public void rotateCameraY(){
		
	}

	public void rotateCameraZ(){
		
	}
	
	/**
	 * Camera Transformation Matrices
	 */
	
	private Point Mv(Point in){
		return null;
	}
	
	private Point Mp(Point in){
		return null;
	}
	
	private Point S1T1Mp(Point in){
		return null;
	}
	
	private Point WS2T2(Point in){
		return null;
	}
}

