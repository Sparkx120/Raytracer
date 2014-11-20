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
		
		Matrix3D sphereTransform1 = new Matrix3D(new float[][] {
									{1,0,0,0},
									{0,1,0,0},
									{0,0,1,1},
									{0,0,0,1}
		});
		Matrix3D sphereTransform2 = new Matrix3D(new float[][] {
				{1,0,0,-2},
				{0,1,0,2},
				{0,0,1,1},
				{0,0,0,1}
		});
		Matrix3D sphereTransform3 = new Matrix3D(new float[][] {
				{1,0,0,-2},
				{0,1,0,-2},
				{0,0,1,1},
				{0,0,0,1}
		});
		Matrix3D sphereTransform4 = new Matrix3D(new float[][] {
				{1,0,0,2},
				{0,1,0,2},
				{0,0,1,1},
				{0,0,0,1}
		});
		Matrix3D sphereTransform5 = new Matrix3D(new float[][] {
				{1,0,0,2},
				{0,1,0,-2},
				{0,0,1,1},
				{0,0,0,1}
		});
		Matrix3D planeTransform = new Matrix3D(new float[][] {
									{1,0,0,0},
									{0,1,0,0},
									{0,0,1,0},
									{0,0,0,1}
		});
		GenericSphere sphere1 = new GenericSphere(Color.BLUE, Color.BLUE, 0.2F, Color.BLUE, 0.6F,
												 Color.WHITE, 0.2F, 2.0F, sphereTransform1);
		GenericSphere sphere2 = new GenericSphere(Color.BLUE, Color.BLUE, 0.2F, Color.BLUE, 0.6F,
				 Color.WHITE, 0.2F, 2.0F, sphereTransform2);
		GenericSphere sphere3 = new GenericSphere(Color.BLUE, Color.BLUE, 0.2F, Color.BLUE, 0.6F,
				 Color.WHITE, 0.2F, 2.0F, sphereTransform3);
		GenericSphere sphere4 = new GenericSphere(Color.BLUE, Color.BLUE, 0.2F, Color.BLUE, 0.6F,
				 Color.WHITE, 0.2F, 2.0F, sphereTransform4);
		GenericSphere sphere5 = new GenericSphere(Color.BLUE, Color.BLUE, 0.2F, Color.BLUE, 0.6F,
				 Color.WHITE, 0.2F, 2.0F, sphereTransform5);
		GenericPlane plane = new GenericPlane(Color.BLACK, Color.BLACK, 0.6F, Color.GRAY, 0.4F,
												 Color.GRAY, 0.0F, 0.1F, planeTransform);
		
		OmniDirectionalLight light1 = new OmniDirectionalLight(new Point(3F, 3F, 1F), 2.0F, Color.WHITE);
		OmniDirectionalLight light2 = new OmniDirectionalLight(new Point(-3F, -3F, 1F), 2.0F, Color.WHITE);
		OmniDirectionalLight light3 = new OmniDirectionalLight(new Point(3F, -3F, 1F), 2.0F, Color.WHITE);
		OmniDirectionalLight light4 = new OmniDirectionalLight(new Point(-3F, 3F, 1F), 2.0F, Color.WHITE);
		OmniDirectionalLight light5 = new OmniDirectionalLight(new Point(0F, 0F, 3F), 2.0F, Color.WHITE);
		
		world.addGenericObject(sphere1);
		world.addGenericObject(sphere2);
		world.addGenericObject(sphere3);
		world.addGenericObject(sphere4);
		world.addGenericObject(sphere5);
		world.addGenericObject(plane);
		world.addLightObject(light1);
		world.addLightObject(light2);
		world.addLightObject(light3);
		world.addLightObject(light4);
		world.addLightObject(light5);
		
		//Configure the Camera
		width = 512;
		height = 512;
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
		Point cameraPoint = new Point(8F, 0F, 8.0F);
		Point gazePoint = new Point(0F, 0F, 0F);
		camera = new CamObject3D(cameraPoint, gazePoint, width, height, 45F, world);
	}
}
