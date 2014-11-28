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
		
		if(args.length == 0){
			configureWorld();
		}
		else{
			readWorldFile(args[0]);
		}
		
		
		//Configure the Camera
		width = 400;
		height = 400;
		configCam();
		
		//Add Camera to the World
		world.addCameraObject(camera);
		
		//Set up a Window3D Viewer
		Window3D viewer3D = new Window3D(camera, width, height);
		viewer3D.setTitle("Raytracer Viewer");
		//viewer3D.setMouseEnabled(false);
		
		Renderer raytracer = new RaytraceRenderer(viewer3D, world, camera, Color.BLACK);
		viewer3D.setRenderer(raytracer);
		
		camera.rotateCameraN(90);
		camera.renderFrame(raytracer);		
	}
	
	/**
	 * Configure the camera using the global height and width and a hard coded position
	 */
	private static void configCam(){
		Point cameraPoint = new Point(10F, 0F, 26F);
		Point gazePoint = new Point(-27F, 0F, 0F);
		camera = new CamObject3D(cameraPoint, gazePoint, width, height, 45F, world);
	}
	
	/**
	 * Configure the Default Test World
	 */
	private static void configureWorldTest(){
		Matrix3D sphereTransform1 = new Matrix3D(new float[][] {
				{1F,0,0,0},
				{0,1F,0,0},
				{0,0,1F,4F},
				{0,0,0,1}
		});
		Matrix3D sphereTransform2 = new Matrix3D(new float[][] {
				{1,0,0,-2},
				{0,1,0,0},
				{0,0,1,1},
				{0,0,0,1}
				});
		Matrix3D sphereTransform3 = new Matrix3D(new float[][] {
				{1,0,0,-2},
				{0,1,0,-1},
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
		
		Matrix3D cylindarTransform = new Matrix3D(new float[][] {
				{10,0,0,0},
				{0,10,0,0},
				{0,0,10,0},
				{0,0,0,1}
		});
		Matrix3D coneTransform = new Matrix3D(new float[][] {
				{1F,0,0,0},
				{0,1F,0,0},
				{0,0,1F,1},
				{0,0,0,1}
		});
		
		GenericSphere sphere1 = new GenericSphere(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.2F,
		Color.WHITE, 1.0F, 10.0F, 0.75F, 0.0F, 0.0F,  sphereTransform1);
		GenericSphere sphere2 = new GenericSphere(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.2F,
		Color.WHITE, 1.0F, 10.0F, 0.75F, 0.0F, 0.0F,  sphereTransform2);
		GenericSphere sphere3 = new GenericSphere(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.2F,
		Color.WHITE, 1.0F, 10.0F, 0.75F, 0.0F, 0.0F,  sphereTransform3);
		GenericSphere sphere4 = new GenericSphere(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.2F,
		Color.WHITE, 1.0F, 10.0F, 0.75F, 0.0F, 0.0F,  sphereTransform4);
		GenericSphere sphere5 = new GenericSphere(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.2F,
		Color.WHITE, 1.0F, 10.0F, 0.75F, 0.0F, 0.0F,  sphereTransform5);
		GenericPlane plane = new GenericPlane(Color.BLACK, Color.BLACK, 0.6F, Color.GRAY, 0.4F,
									 Color.WHITE, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F,  planeTransform);
		GenericCylinder cyl = new GenericCylinder(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.6F,
				Color.WHITE, 0.3F, 10.0F, 0.0F, 0.0F, 0.0F,  cylindarTransform);
		GenericCone cone = new GenericCone(Color.BLUE, Color.BLUE, 0.1F, Color.BLUE, 0.2F,
				Color.WHITE, 1.0F, 10.0F, 0.75F, 0.0F, 0.0F,  coneTransform);
		
		OmniDirectionalLight light1 = new OmniDirectionalLight(new Point(5F, 5F, 2F), 1.0F, Color.WHITE);
		OmniDirectionalLight light2 = new OmniDirectionalLight(new Point(-5F, -5F, 2F), 1.0F, Color.WHITE);
		OmniDirectionalLight light3 = new OmniDirectionalLight(new Point(0F, 0F, 20F), 1.0F, Color.WHITE);
		OmniDirectionalLight light4 = new OmniDirectionalLight(new Point(0F, 0F, 6F), 1.0F, Color.WHITE);
		OmniDirectionalLight light5 = new OmniDirectionalLight(new Point(0F, 0F, 2.9F), 1.0F, Color.WHITE);
		
//		world.addGenericObject(sphere1);
		world.addGenericObject(sphere2);
//		world.addGenericObject(sphere3);
		world.addGenericObject(sphere4);
		world.addGenericObject(sphere5);
		world.addGenericObject(cyl);
		world.addGenericObject(cone);
		world.addGenericObject(plane);
		//world.addLightObject(light1);
		//world.addLightObject(light2);
//		world.addLightObject(light3);
//		world.addLightObject(light4);
		world.addLightObject(light5);
	}
	
	/**
	 * Configure the Default Test World
	 */
	private static void configureWorld(){
		Matrix3D cylinderTransform1 = new Matrix3D(new float[][] {
				{1F,0,0,0},
				{0,1F,0,0},
				{0,0,8F,10F},
				{0,0,0,1}
		});
		Matrix3D sphereTransform1 = new Matrix3D(new float[][] {
				{1,0,0,0},
				{0,1,0,0},
				{0,0,3,18F},
				{0,0,0,1}
				});
		Matrix3D coneTransform1 = new Matrix3D(new float[][] {
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,2},
				{0,0,0,1}
				});

		Matrix3D planeTransform1 = Matrices3D.affineTransformRx(90F);
		Matrix3D planeTransform2 = Matrices3D.affineTransformRx(90F);
		Matrix3D planeTransform3 = Matrices3D.affineTransformRy(90F);
		Matrix3D planeTransform4 = Matrices3D.affineTransformRy(90F);
		planeTransform1 = Matrices3D.affineTransformTranslation(-1F, 0F, 2F).multiplyMatrixWithMatrix(planeTransform1);
		planeTransform2 = Matrices3D.affineTransformTranslation(1F, 0F, 2F).multiplyMatrixWithMatrix(planeTransform2);
		planeTransform3 = Matrices3D.affineTransformTranslation(0F, -1F, 2F).multiplyMatrixWithMatrix(planeTransform3);
		planeTransform4 = Matrices3D.affineTransformTranslation(0F, 1F, 2F).multiplyMatrixWithMatrix(planeTransform4);
		planeTransform1 = Matrices3D.affineTransformScale(1F, 1F, 2F).multiplyMatrixWithMatrix(planeTransform1);
		planeTransform2 = Matrices3D.affineTransformScale(1F, 1F, 2F).multiplyMatrixWithMatrix(planeTransform2);
		planeTransform3 = Matrices3D.affineTransformScale(1F, 1F, 2F).multiplyMatrixWithMatrix(planeTransform3);
		planeTransform4 = Matrices3D.affineTransformScale(1F, 1F, 2F).multiplyMatrixWithMatrix(planeTransform4);
		
		Matrix3D rocketTransform = Matrices3D.affineTransformTranslation(-10F, 0, 0).multiplyMatrixWithMatrix(Matrices3D.affineTransformRz(45F));
		
		cylinderTransform1 = rocketTransform.multiplyMatrixWithMatrix(cylinderTransform1);
		coneTransform1 = rocketTransform.multiplyMatrixWithMatrix(coneTransform1);
		sphereTransform1 = rocketTransform.multiplyMatrixWithMatrix(sphereTransform1);
		planeTransform1 = rocketTransform.multiplyMatrixWithMatrix(planeTransform1);
		planeTransform2 = rocketTransform.multiplyMatrixWithMatrix(planeTransform2);
		planeTransform3 = rocketTransform.multiplyMatrixWithMatrix(planeTransform3);
		planeTransform4 = rocketTransform.multiplyMatrixWithMatrix(planeTransform4);
		
		Matrix3D planeTransform = new Matrix3D(new float[][] {
				{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1}
		});
		
		GenericCylinder rocketBody = new GenericCylinder(Color.WHITE, Color.WHITE, 0.05F, Color.WHITE, 0.6F,
				Color.WHITE, 0.35F, 10.0F, 0.1F, 0.0F, 0.0F,  cylinderTransform1);
		rocketBody.enableTopPlane(false);
		GenericSphere noseCone = new GenericSphere(Color.WHITE, Color.WHITE, 0.05F, Color.WHITE, 0.6F,
				Color.WHITE, 0.35F, 10.0F, 0.1F, 0.0F, 0.0F,  sphereTransform1);
		GenericCone engineCone = new GenericCone(Color.GRAY, Color.GRAY, 0.05F, Color.GRAY, 0.85F,
				Color.WHITE, 0.1F, 10.0F, 0.1F, 0.0F, 0.0F,  coneTransform1);
		GenericPlane finA = new GenericPlane(Color.GRAY, Color.GRAY, 0.05F, Color.GRAY, 0.85F,
				Color.WHITE, 0.1F, 10.0F, 0.1F, 0.0F, 0.0F,  planeTransform1, true);
		GenericPlane finB = new GenericPlane(Color.GRAY, Color.GRAY, 0.05F, Color.GRAY, 0.85F,
				Color.WHITE, 0.1F, 10.0F, 0.1F, 0.0F, 0.0F,  planeTransform2, true);
		GenericPlane finC = new GenericPlane(Color.GRAY, Color.GRAY, 0.05F, Color.GRAY, 0.85F,
				Color.WHITE, 0.1F, 10.0F, 0.1F, 0.0F, 0.0F,  planeTransform3, true);
		GenericPlane finD = new GenericPlane(Color.GRAY, Color.GRAY, 0.05F, Color.GRAY, 0.85F,
				Color.WHITE, 0.1F, 10.0F, 0.1F, 0.0F, 0.0F,  planeTransform4, true);
		
		GenericPlane plane = new GenericPlane(Color.BLACK, Color.BLACK, 0.6F, Color.GRAY, 0.4F,
				 Color.WHITE, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F,  planeTransform);
		
		OmniDirectionalLight light1 = new OmniDirectionalLight(new Point(-7F, 30F, 20F), 1.0F, Color.WHITE);
		OmniDirectionalLight light2 = new OmniDirectionalLight(new Point(-14F, -30F, 20F), 1.0F, Color.WHITE);
		OmniDirectionalLight light3 = new OmniDirectionalLight(new Point(-10F, 0F, 30F), 1.0F, Color.WHITE);
		
		world.addGenericObject(rocketBody);
		world.addGenericObject(noseCone);
		world.addGenericObject(engineCone);
		world.addGenericObject(finA);
		world.addGenericObject(finB);
		world.addGenericObject(finC);
		world.addGenericObject(finD);
		world.addGenericObject(plane);
		world.addLightObject(light1);
		world.addLightObject(light2);
//		world.addLightObject(light3);
	}
	
	/**
	 * Reads a World Definition File
	 * @param fname - The File
	 */
	private static void readWorldFile(String fname){
		String file = FileIO.readStringFromFile(fname);
		int index = getNextDefIndex(file);
		while(index != -1){
			GenericObjectType type = getObjectType(file);
			switch(type){
				case SPHERE: world.addGenericObject(new GenericSphere(file.substring(file.indexOf("{") + 1, file.indexOf("}") + 2)));
							index = file.indexOf("}") + 2;
							break;
				case PLANE: world.addGenericObject(new GenericPlane(file.substring(file.indexOf("{") + 1, file.indexOf("}"))));
							index = file.indexOf("}");
							break;
				case OMNI_LIGHT: world.addLightObject(new OmniDirectionalLight(file.substring(file.indexOf("{") + 1, file.indexOf("}"))));
							index = file.indexOf("}");
							break;
			}
			index = getNextDefIndex(file.substring(index));
			file = file.substring(index);
		}
		
		
	}
	
	private static GenericObjectType getObjectType(String file){
		String line = file.substring(0, file.indexOf("{"));
		return GenericObjectType.getTypeFromString(line);
	}
	
	/**
	 * Returns Next Definition Index in file
	 * @param file - The File String
	 * @return - The next Definition Index
	 */
	private static int getNextDefIndex(String file){
		int sphereDef = file.indexOf("sphere");
		int planeDef = file.indexOf("plane");
		int lightDef = file.indexOf("light");
		
		if(sphereDef < planeDef && sphereDef < lightDef){
			return sphereDef;
		}
		
		if(planeDef < sphereDef && planeDef < lightDef){
			return planeDef;
		}
		if(lightDef != -1){
			return lightDef;
		}
		return -1;
	}
}
