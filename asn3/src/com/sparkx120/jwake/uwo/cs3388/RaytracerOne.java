package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;

public class RaytracerOne{
	
	private static CamObject3D camera;
	private static int width;
	private static int height;
	private static World world;
	private static Window3D window;
	
	public static void main(String[] args){
		world = new World();
		
		Matrix3D sphereTransform = new Matrix3D(new float[][] {
									{1,0,0,0},
									{0,1,0,0},
									{0,0,1,1},
									{0,0,0,1}
		});
		Matrix3D planeTransform = new Matrix3D(new float[][] {
									{1,0,0,0},
									{0,1,0,0},
									{0,0,1,0},
									{0,0,0,1}
		});
		GenericSphere sphere = new GenericSphere(Color.BLUE, Color.BLUE, 0.2F, Color.BLUE, 0.6F,
												 Color.WHITE, 0.2F, 1.5F, sphereTransform);
		
		GenericPlane plane = new GenericPlane(Color.BLACK, Color.BLACK, 0.6F, Color.GRAY, 0.4F,
												 Color.GRAY, 0.0F, 0.1F, planeTransform);
		
		OmniDirectionalLight light = new OmniDirectionalLight(new Point(3F, 3F, 3F), 1.0F, Color.WHITE);
		
		world.addGenericObject(sphere);
		world.addGenericObject(plane);
		world.addLightObject(light);
		
		//Configure the Camera
		width = 1000;
		height = 1000;
		configCam();
		
		//Add Camera to the World
		world.addCameraObject(camera);
		
		//Set up a Window3D Viewer
		Window3D viewer3D = new Window3D(camera, width, height);
		viewer3D.setTitle("Raytracer Viewer");
		viewer3D.setMouseEnabled(false);
		
		Renderer raytracer = new RaytraceRenderer(viewer3D, world, camera, Color.BLACK);
		viewer3D.setRenderer(raytracer);
		
		camera.rotateCameraN(90);
		camera.renderFrame(raytracer);		
	}
	
	/**
	 * Configure the camera using the global height and width and a hard coded position
	 */
	private static void configCam(){
		Point cameraPoint = new Point(10F, 0F, 2F);
		Point gazePoint = new Point(0F, 0F, 0F);
		camera = new CamObject3D(cameraPoint, gazePoint, width, height, 60F, world);
	}
}
