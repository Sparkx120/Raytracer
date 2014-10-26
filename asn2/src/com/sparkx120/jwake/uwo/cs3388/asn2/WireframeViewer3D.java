package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Color;

public class WireframeViewer3D {
	private static PolyObject3D testObj;
	private static CamObject3D camera;
	private static int width;
	private static int height;
	private static World world;
	
	public static void main(String[] args){
		configTestObj();
		ObjectMaker.main(new String[]{"vase.txt", "dump.obj"});
		world = new World();
//		world.addPolyObject(testObj);
		world.addPolyObject(ObjectMaker.obj);
		width = 1024;
		height = 1024;
		configCam();
		
		world.addCameraObject(camera);
		
		Window3D viewer3D = new Window3D(camera, width, height);
		viewer3D.setTitle("Wireframe Viewer 3D");
		
		Renderer renderer = new WireframeRenderer(viewer3D, Color.BLACK, Color.WHITE);
		
		viewer3D.setRenderer(renderer);
		
		camera.renderFrame(renderer);
		
	}
	
	private static void configTestObj(){
		testObj = new PolyObject3D();
//		testObj.addPolygonByVertices(new Vertex(1F, 0F, 1F), new Vertex(0F, 0F, 0F), new Vertex(1F, 1F, 0F));
//		testObj.addPolygonByVertices(new Vertex(1F, 0F, 1F), new Vertex(0F, 0F, 0F), new Vertex(0F, 1F, 1F));
//		testObj.addPolygonByVertices(new Vertex(0F, 1F, 0F), new Vertex(0F, 0F, 0F), new Vertex(0F, 1F, 1F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(0F, 0F, 5F), new Vertex(5F, 0F, 0F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(5F, 0F, 0F), new Vertex(0F, 0F, -5F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(0F, 0F, -5F), new Vertex(-5F, 0F, 0F));
		testObj.addPolygonByVertices(new Vertex(0F, 5F, 0F), new Vertex(-5F, 0F, 0F), new Vertex(0F, 0F, 5F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(5F, 0F, 0F), new Vertex(0F, 0F, 5F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(0F, 0F, -5F), new Vertex(5F, 0F, 0F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(-5F, 0F, 0F), new Vertex(0F, 0F, -5F));
		testObj.addPolygonByVertices(new Vertex(0F, -5F, 0F), new Vertex(0F, 0F, 5F), new Vertex(-5F, 0F, 0F));
	}
	
	private static void configCam(){
		Point cameraPoint = new Point(0F, 35F, 35F);
		Point gazePoint = new Point(0F, 0F, 20F);
		camera = new CamObject3D(cameraPoint, gazePoint, width, height, 60F, world);
	}
}
