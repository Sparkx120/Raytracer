package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Defines a Synthetic Camera in World Coordinates
 * Currently the Camera Computes all Objects Coordinates including those out of Frame
 * In the Future 3D Clipping will be used to minimize Computations.
 * 
 * @author James Wake
 * @version 1.0
 */
public class CamObject3D extends Object3D{
	/**
	 * Camera Position Point
	 */
	private Point e;
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
	private Point g;
	/**
	 * Edge variables of Camera
	 */
	private float l; //Left Edge Distance
	private float r; //Right Edge Distance
	private float t; //Top Edge Distance
	private float b; //Bottom Edge Distance
	private float N; //Distance to Near Plane
	private float F; //Distance to Far Plane
	private float theta; //The Cameras Viewing Angle
	private float aspect; //The Cameras Aspect
	private int width; // The Cameras Pixel Width
	private int height; // The Cameras Pixel Height
	
	/**
	 * Camera Transform Matrices
	 */
	Matrix3D M; //M Matrix
	Matrix3D Mv; //Mv Matrix
	Matrix3D S1T1Mp; //S1T1Mp Matrix
	Matrix3D WS2T2; //WS2T2 Matrix
	Matrix3D matrixPipe; //Matrix Pipe (Now unused)
	
	/**
	 * The World this Camera is in
	 */
	private World world;
	
	/**
	 * Output Debugging
	 */
	private boolean debug = false;
	private boolean debugPoints = false;
	
	/**
	 * Discontinued Constructor
	 */
//	/**
//	 * Constructs a new Synthetic Camera at Point P with "Gaze" Vector n and "Up" Vector u
//	 * @param p - The Point where the camera should be
//	 * @param n - The Normal Vector
//	 * @param u - The Up Gaze Vector
//	 * @param width - The Pixel Width of the Camera
//	 * @param height - The Pixel Height of the Camera
//	 * @param viewingAngle - The Viewing angle of the Camera in Degrees
//	 *  
//	 * @param w - The World this camera is in
//	 */
//	public CamObject3D(Point p, Vector n, Vector u,int width, int height, float viewingAngle, World w){
//		//Set Vectors
//		this.e = p;
//		this.n = n;
//		this.u = u;
//		this.v = Math3D.crossProdVectors(n, u);
//		
//		//Set Params
//		this.width = width;
//		this.height = height;
//		this.theta = viewingAngle;
//		this.world = w;
//		
//		//Set Near and Far Plane Distance
//		this.N = 1.0F;
//		this.F = 100F;
//		
//		//Compute matrixPipe
//		computeAndSetMatrixPipe();
//	}
	
	/**
	 * Default Camera Contructor
	 * @param p - The Point in world coordinates where the camera should be
	 * @param g - The Point where the gaze vector should go through
	 * @param width - The width of the output window
	 * @param height - The height of the output window
	 * @param viewingAngle - The vieweing angle of this camera
	 * @param w - The World this camera is in
	 */
	public CamObject3D(Point p, Point g, int width, int height, float viewingAngle, World w){
		//Set Params
		this.e = p;
		this.g = g;
		this.width = width;
		this.height = height;
		this.theta = viewingAngle;
		this.world = w;
		this.g = g;
		
		//Set Near and Far Plane Distance 
		//Add ability to set these in the next assignment
		this.N = 0.5F;
		this.F = 100F;
		
		//Compute Vector n
		Vector nP = new Vector(g, e);
		float nPmag = 1/Math3D.magnitudeOfVector(nP);
		this.n = Math3D.scalarMultiplyVector(nP, nPmag); //Maybe need to invert Direction
		
		//Compute Vector u (+y axis = up for now) 
		Vector pP = new Vector(0F,1F,0F);
		this.u = Math3D.crossProdVectors(pP, n);
		this.u = Math3D.scalarMultiplyVector(u, -1F);
		
		//Compute Vector v
		this.v = Math3D.crossProdVectors(n, u);
		
		//Compute all Matrices
		computeAndSetMatrixPipe();
		
		
	}
	
	/**
	 * Sets the Matrix Pipe up for Computations using current Variables
	 */
	private void computeAndSetMatrixPipe(){
		
		//Compute WS2T2 Matrix
		this.WS2T2 = WS2T2(width, height);
		if(debug)
			System.out.println("WS2T2: \n" + WS2T2.toString());
		
		//Compute S1T1Mp Matrix
		this.S1T1Mp = S1T1Mp();
		if(debug)
			System.out.println("S1T1Mp: \n" + S1T1Mp.toString());
		
		//Compute Mv Matrix
		this.Mv = Mv();
		if(debug)
			System.out.println("Mv: \n" + Mv.toString());
		
		//Compute M Matrix (Not in Pipe)
		this.M = M();
		
		//Compute First Multiplication
//		Matrix3D mid = WS2T2.multiplyMatrixWithMatrix(S1T1Mp);
//		if(debug)
//			System.out.println("MidMatrix: " + mid.toString());
		
		//Compute Final Multiplication and Pipe
//		this.matrixPipe = mid.multiplyMatrixWithMatrix(Mv);
//		if(debug)
//			System.out.println("Matrix Pipe: " + matrixPipe.toString());
		
		//this.matrixPipe = WS2T2.multiplyMatrixWithMatrix(S1T1Mp).multiplyMatrixWithMatrix(Mv);
	}
	
	/**
	 * Updates the matrix pipe (Mv Is the only component that needs updating
	 */
	private void updateMatrixPipe(){
		//Compute Mv Matrix
		this.Mv = Mv();
	}
	
	/**
	 * Passes the current scene to the Set Renderer for Rendering
	 * @param renderer - The Renderer
	 */
	public void renderFrame(Renderer renderer){
		//Do Matrix Stuff Here
		ArrayList<PolyObject3D> objs = world.getRenderableObjects();
		
		//Create Objects with Camera Coordinates and put them in an ArrayList
		ArrayList<PolyObject3D> toRender = new ArrayList<PolyObject3D>();
		Iterator<PolyObject3D> it = objs.iterator();
		while(it.hasNext()){
			//Iterate Polygons
			PolyObject3D obj = it.next();
			PolyObject3D rendObj = new PolyObject3D();
			ArrayList<Polygon> polys = obj.getFaces();
			Iterator<Polygon> it2 = polys.iterator();
			while(it2.hasNext()){
				//Convert Vertices
				Polygon poly = it2.next();
				Point a = poly.getVertexA().getPoint();
				Point b = poly.getVertexB().getPoint();
				Point c = poly.getVertexC().getPoint();
				Vertex A = new Vertex(pipeCalc(a));
				Vertex B = new Vertex(pipeCalc(b));
				Vertex C = new Vertex(pipeCalc(c));
				rendObj.addPolygonByVertices(A, B, C);
			}
			//Send add to render object
			toRender.add(rendObj);
		}
		
		//Send to Renderer to Draw on Screen
		if(debug)
			System.out.println("Sending Objects to Renderer");
		renderer.renderObjects(toRender);
	}
	
	/**
	 * Convert Point through the Matrix Pipe.
	 * @param in - The Point in
	 * @return The Converted Point
	 */
	private Point pipeCalc(Point in){
		Point out;
		
		//Pipe computation is no longer used
//		out = matrixPipe.multiplyMatrixWithPointOrVector(in);
		
		//Pass World Point through pipe
		out = Mv.multiplyMatrixWithPoint(in);
		float Z = out.getZ();
		if(debugPoints)
			System.out.println("Mv Calc" + out);
		out = S1T1Mp.multiplyMatrixWithPoint(out);
		if(debugPoints)
			System.out.println("S1T1Mp: " + out + " inZ " + Z);
		out = Math3D.scalarMultiplyPoint(out, (-1/Z));
		out = WS2T2.multiplyMatrixWithPoint(out);
		if(debugPoints)
			System.out.println("Final: " + out);
		
		return out;
	}
	
	/**
	 * Camera Movement Methods
	 */
	
	/**
	 * Movement Left and Right (Assigned to A and D)
	 * @param mag - The Magnitude in which to move +/-
	 */
	public void translateCameraV(float mag){
		Vector worldMotion = Math3D.scalarMultiplyVector(u, mag);
		Point newE = Math3D.addPoints(worldMotion, e);
		e = newE;
		
		updateMatrixPipe();
	}
	
	/**
	 * Movement Forwards and Backward (Assigned to W and S)
	 * @param mag - The Magnitude in which to move +/-
	 */
	public void translateCameraN(float mag){
		Vector worldMotion = Math3D.scalarMultiplyVector(n, mag);
		Point newE = Math3D.addPoints(worldMotion, e);
		e = newE;
		
		updateMatrixPipe();
	}
	
	/**
	 * Movement Up and Down (Assigned to R and F)
	 * @param mag - The Magnitude in which to move +/-
	 */
	public void translateCameraU(float mag){
		Vector worldMotion = Math3D.scalarMultiplyVector(v, mag);
		Point newE = Math3D.addPoints(worldMotion, e);
		e = newE;
		
		updateMatrixPipe();
	}
	
	/**
	 * Rotate camera around it's u axis (Assigned to Mouse X)
	 * @param mag - The Magnitude in which to rotate +/-
	 */
	public void rotateCameraU(float mag){
		Matrix3D rotate = Matrices3D.rotateOnArbitrary(mag, u);
		this.v = rotate.multiplyMatrixWithVector(v);
		this.n = rotate.multiplyMatrixWithVector(n);
		
		updateMatrixPipe();
	}
	
	//Y Mouse
	/**
	 * Rotate camera around it's v axis (Assigned to Mouse Y)
	 * @param mag - The Magnitude in which to rotate +/-
	 */
	public void rotateCameraV(float mag){
		Matrix3D rotate = Matrices3D.rotateOnArbitrary(mag, v);
		this.u = rotate.multiplyMatrixWithVector(u);
		this.n = rotate.multiplyMatrixWithVector(n);
		
		updateMatrixPipe();
	}

	/**
	 * Rotate camera around it's n axis (Assigned to 1 and 3
	 * @param mag - The Magnitude in which to rotate +/-
	 */
	public void rotateCameraN(float mag){
		Matrix3D rotate = Matrices3D.rotateOnArbitrary(mag, n);
		this.u = rotate.multiplyMatrixWithVector(u);
		this.v = rotate.multiplyMatrixWithVector(v);
		
		updateMatrixPipe();
	}
	
	/**
	 * Camera Perspective from World Coords Conversion Matrices
	 */
	
	/**
	 * Generates the M Matrix from the Notes 
	 * @return The Matrix3D Representation of Mv
	 */
	private Matrix3D M(){
		float[][] data = new float[4][4];
		
		//Setup matrix array
		data[0][0] = u.getX();	data[0][1] = v.getX();	data[0][2] = n.getX();	data[0][3] = e.getX();
		data[1][0] = u.getY();	data[1][1] = v.getY();	data[1][2] = n.getY();	data[1][3] = e.getY();
		data[2][0] = u.getZ();	data[2][1] = v.getZ();	data[2][2] = n.getZ();	data[2][3] = e.getZ();
		data[3][0] = 0;			data[3][1] = 0;			data[3][2] = 0;			data[3][3] = 1;
		
		//Output
		Matrix3D Mv = new Matrix3D(data);
		
		return Mv;
	}
	
	/**
	 * Generates the Mv Matrix from the Notes 
	 * @return The Matrix3D Representation of Mv
	 */
	private Matrix3D Mv(){
		float[][] data = new float[4][4];
		
		//Setup matrix array
		data[0][0] = u.getX();	data[0][1] = u.getY();	data[0][2] = u.getZ();	data[0][3] = -Math3D.dotProduct(e, u);
		data[1][0] = v.getX();	data[1][1] = v.getY();	data[1][2] = v.getZ();	data[1][3] = -Math3D.dotProduct(e, v);
		data[2][0] = n.getX();	data[2][1] = n.getY();	data[2][2] = n.getZ();	data[2][3] = -Math3D.dotProduct(e, n);
		data[3][0] = 0;			data[3][1] = 0;			data[3][2] = 0;			data[3][3] = 1;
		
		//Output
		Matrix3D Mv = new Matrix3D(data);
		
		return Mv;
	}
	
	/**
	 * Generates the S1T1Mp Matrix from the Notes and Sets t,b,r,l,
	 * @param theta - The Viewing Angle
	 * @param aspect - The Aspect Ratio
	 * @return The Matrix3D Representation of S1T1Mp
	 */
	private Matrix3D S1T1Mp(){
		float[][] data = new float[4][4];
		
		//Set Camera Variables
		this.t = (float) (N*Math.tan((Math.PI/180)*(theta/2)));
		this.b = -t;
		this.r = aspect*t;
		this.l = -r;
		
		//Setup matrix array
		data[0][0] = ((2*N)/(r-1)); data[0][1] = 0;				data[0][2] = ((r+l)/(r-l));		data[0][3] = 0;
		data[1][0] = 0;				data[1][1] = ((2*N)/(t-b)); data[1][2] = ((t+b)/(t-b));		data[1][3] = 0;
		data[2][0] = 0;				data[2][1] = 0;				data[2][2] = ((-(F+N))/(F-N));	data[2][3] = ((-2*F*N)/(F-N));
		data[3][0] = 0;				data[3][1] = 0;				data[3][2] = -1;				data[3][3] = 0;
		
		//Output
		Matrix3D out = new Matrix3D(data);
		
		return out;
	}
	
	/**
	 * Generates the WS2T2 Matrix from the Notes and sets width and height and aspect
	 * @param width - The width
	 * @param height - The height
	 * @return The Matrix3D Representation of WS2T2
	 */
	private Matrix3D WS2T2(int width, int height){
		float[][] data = new float[4][4];
		
		//Set Camera Variables
		this.width = width;
		this.height = height;
		this.aspect = width/height;
		
		//Setup matrix array
		data[0][0] = width/2;	data[0][1] = 0;			data[0][2] = 0;	data[0][3] = width/2;
		data[1][0] = 0;			data[1][1] = -height/2;	data[1][2] = 0; data[1][3] = ((-height/2) + height);
		data[2][0] = 0;			data[2][1] = 0;			data[2][2] = 1;	data[2][3] = 0;
		data[3][0] = 0;			data[3][1] = 0;			data[3][2] = 0; data[3][3] = 1;
		
		//Output
		Matrix3D out = new Matrix3D(data);
		
		return out;
	}
}

