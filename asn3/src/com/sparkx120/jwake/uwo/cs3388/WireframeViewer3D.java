package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

/**
 * Driver class for the Wireframe Viewer part of this Assignment
 * @author James Wake
 * @version 1.0
 *
 */
public class WireframeViewer3D {
	/**
	 * Test Poly Object
	 */
	private static PolyObject3D testObj;
	/**
	 * Camera Object for the viewer
	 */
	private static CamObject3D camera;
	/**
	 * Height and Width of the viewer
	 */
	private static int width;
	private static int height;
	/**
	 * The world to user
	 */
	private static World world;
	
	/**
	 * The executable main function for Wireframe Viewer 3D
	 * @param args - Command Line Arguments
	 */
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Must suppy an Object File, Usage: object.obj");
			System.exit(1);
		}
		//Configure an Object and World for Viewing
//		configTestObj();
//		ObjectMaker.main(new String[]{"vase.txt", "dump.obj"});
		String objData = FileIO.readStringFromFile(args[0]);
		testObj = new PolyObject3D(objData);
		world = new World();
		world.addPolyObject(testObj);
//		world.addPolyObject(ObjectMaker.obj);
		
		//Configure the Camera
		width = 1280;
		height = 1024;
		configCam();
		
		//Add Camera to the World
		world.addCameraObject(camera);
		
		//Set up a Window3D Viewer
		Window3D viewer3D = new Window3D(camera, width, height);
		viewer3D.setTitle("Wireframe Viewer 3D");
		
		//Set up a renderer for the Window3D
		Renderer renderer = new WireframeRenderer(viewer3D, Color.BLACK, Color.WHITE);
		viewer3D.setRenderer(renderer);
		
		//Render the first frame
		camera.renderFrame(renderer);
	}
	
	/**
	 * Configures the TestObject with a simple Octahedron
	 */
	@SuppressWarnings("unused")
	private static void configTestObj(){
		testObj = new PolyObject3D();
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(0F, 0F, 5F), new Vertex(5F, 0F, 0F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(5F, 0F, 0F), new Vertex(0F, 0F, -5F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(0F, 0F, -5F), new Vertex(-5F, 0F, 0F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(-5F, 0F, 0F), new Vertex(0F, 0F, 5F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(5F, 0F, 0F), new Vertex(0F, 0F, 5F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(0F, 0F, -5F), new Vertex(5F, 0F, 0F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(-5F, 0F, 0F), new Vertex(0F, 0F, -5F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(0F, 0F, 5F), new Vertex(-5F, 0F, 0F));
	}
	
	/**
	 * Configure the camera using the global height and width and a hard coded position
	 */
	private static void configCam(){
		Point cameraPoint = new Point(0F, 35F, 35F);
		Point gazePoint = new Point(0F, 0F, 20F);
		camera = new CamObject3D(cameraPoint, gazePoint, width, height, 60F, world);
	}
}
