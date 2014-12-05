package com.sparkx120.jwake.graphics3d.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import com.sparkx120.jwake.graphics3d.base.CamObject3D;
import com.sparkx120.jwake.graphics3d.base.Point;
import com.sparkx120.jwake.graphics3d.base.Ray;
import com.sparkx120.jwake.graphics3d.base.Vector;
import com.sparkx120.jwake.graphics3d.base.World;
import com.sparkx120.jwake.graphics3d.gui.Window3D;
import com.sparkx120.jwake.graphics3d.lights.LightObject;
import com.sparkx120.jwake.graphics3d.lights.OmniDirectionalLight;
import com.sparkx120.jwake.graphics3d.lights.WorldLight;
import com.sparkx120.jwake.graphics3d.objects.GenericObject;
import com.sparkx120.jwake.graphics3d.objects.PolyObject3D;
import com.sparkx120.jwake.math.Math3D;

public class RaytraceRenderer extends Renderer{
	
	private int superSampling = 1;
	private boolean supersample = false;
	private int recursionFactor = 5;
	private Window3D window;
	private World world;
	private CamObject3D camera;
	private Color backgroundC;
	/**
	 * The local Image Buffer
	 */
	private BufferedImage buffer;
	private BufferedImage backgroundImage;
	private Graphics2D overlay;
	private float frameComputeTime;
	
	public RaytraceRenderer(Window3D window, World w, CamObject3D camera, Color background){
		super(RendererType.CAMERA_PIPE);
		this.window = window;
		this.world = w;
		this.camera = camera;
		backgroundC = background;
		frameComputeTime = 0.0F; 
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		overlay = this.buffer.createGraphics();
	}
	
	public RaytraceRenderer(Window3D window, World w, CamObject3D camera, BufferedImage background){
		super(RendererType.CAMERA_PIPE);
		this.window = window;
		this.world = w;
		this.camera = camera;
		backgroundImage = background;
		frameComputeTime = 0.0F; 
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		overlay = this.buffer.createGraphics();
	}

	@Override
	public void renderObjects(ArrayList<PolyObject3D> objs) {
		//Unimplemented as this is not an OBJECT_DATA renderer
	}
	
	public void renderWorld(){
		int[] black = new int[camera.getWidth()*camera.getHeight()];
		for(int i = 0; i<black.length; i++){
			black[i] = Color.BLACK.getRGB();
		}
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		buffer.setRGB(0, 0, camera.getWidth(), camera.getHeight(), black, 0, 0);
//		quickRender(64);
//		quickRender(128);
//		quickRender(256);
//		fullRender();
//		float timeA = System.currentTimeMillis();
		if(this.threading)
			quarterFullRenderThreaded();
		else
			quarterFullRender();
//		float timeB = System.currentTimeMillis();
//		frameComputeTime = (timeB-timeA)*1000;
		this.renderToScreen();
	}
	
	@SuppressWarnings("unused")
	private void quickRender(int quickFactor){
		for(int r=0; r<quickFactor; r++){
			for(int c=0; c<quickFactor; c++){
				int x = c*(camera.getWidth()/quickFactor);
				int y = r*(camera.getHeight()/quickFactor);
				int xStep = camera.getWidth()/quickFactor;
				int yStep = camera.getHeight()/quickFactor;
				
				Color pix = this.renderRayPixel(x, y, false, false);

				for(int i=x; i<x+xStep; i++){
					for(int j=y; j<y+yStep; j++){
						buffer.setRGB(i, j, pix.getRGB());
					}
				}
			}
			this.renderToScreen();
		}
	}
	
	private void quarterFullRender(){
		for(int r=0; r<(camera.getHeight()/2)-0; r++){
			for(int c=0; c<(camera.getWidth()/2)-0; c++){
				Color pix = this.renderRayPixel(c*2, r*2, false, false);
				buffer.setRGB(c*2, r*2, pix.getRGB());
			}
			this.renderToScreen();
		}
		for(int r=1; r<(camera.getHeight()/2)-1; r++){
			for(int c=0; c<(camera.getWidth()/2)-0; c++){
				Color pix = this.renderRayPixel(c*2, r*2+1, false, false);
				buffer.setRGB(c*2, r*2+1, pix.getRGB());
			}
			this.renderToScreen();
		}
		for(int r=1; r<(camera.getHeight()/2)-1; r++){
			for(int c=1; c<(camera.getWidth()/2)-1; c++){
				Color pix = this.renderRayPixel(c*2+1, r*2+1, false, false);
				buffer.setRGB(c*2+1, r*2+1, pix.getRGB());
			}
			this.renderToScreen();
		}
		for(int r=0; r<(camera.getHeight()/2)-0; r++){
			for(int c=1; c<(camera.getWidth()/2)-1; c++){
				Color pix = this.renderRayPixel(c*2+1, r*2, false, false);
				buffer.setRGB(c*2+1, r*2, pix.getRGB());
			}
			this.renderToScreen();
		}
	}
	
	private void quarterFullRenderThreaded(){
		Thread pixelA = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int r=0; r<(camera.getHeight()/2)-0; r++){
					for(int c=0; c<(camera.getWidth()/2)-0; c++){
						Color pix = renderRayPixel(c*2, r*2, false, false);
						buffer.setRGB(c*2, r*2, pix.getRGB());
					}
					renderToScreen();
				}
				
			}
			
		});
		Thread pixelB = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int r=1; r<(camera.getHeight()/2)-1; r++){
					for(int c=0; c<(camera.getWidth()/2)-0; c++){
						Color pix = renderRayPixel(c*2, r*2+1, false, false);
						buffer.setRGB(c*2, r*2+1, pix.getRGB());
					}
					renderToScreen();
				}
				
			}
			
		});
		Thread pixelC = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int r=1; r<(camera.getHeight()/2)-1; r++){
					for(int c=1; c<(camera.getWidth()/2)-1; c++){
						Color pix = renderRayPixel(c*2+1, r*2+1, false, false);
						buffer.setRGB(c*2+1, r*2+1, pix.getRGB());
					}
					renderToScreen();
				}
				
			}
			
		});
		Thread pixelD = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int r=0; r<(camera.getHeight()/2)-0; r++){
					for(int c=1; c<(camera.getWidth()/2)-1; c++){
						Color pix = renderRayPixel(c*2+1, r*2, false, false);
						buffer.setRGB(c*2+1, r*2, pix.getRGB());
					}
					renderToScreen();
				}
				
			}
			
		});
		pixelA.start();
		pixelB.start();
		pixelC.start();
		pixelD.start();
	}
	
	@SuppressWarnings("unused")
	private void fullRender(){
		for(int r=0; r<camera.getHeight(); r++){
			for(int c=0; c<camera.getWidth(); c++){
				Color pix = this.renderRayPixel(c, r, false, false);
				buffer.setRGB(c, r, pix.getRGB());
			}
			this.renderToScreen();
		}
	}

	@Override
	public Color renderRayPixel(int x, int y, boolean debug, boolean selfBuffer) {
		if(supersample){
			ArrayList<Color> toAverage = new ArrayList<Color>();
			x = x*superSampling;
			y = y*superSampling;
			for(int sx=0; sx<superSampling; sx++){
				for(int sy=0; sy<superSampling; sy++){
					Ray ray = new Ray(x-sx, y-sy, camera, superSampling);
					toAverage.add(rayTrace(ray, 0, x, y, null, debug));
				}
			}
			Iterator<Color> it = toAverage.iterator();
			int red = 0;
			int green = 0;
			int blue = 0;
			while(it.hasNext()){
				Color c = it.next();
				red += c.getRed();
				green += c.getGreen();
				blue += c.getBlue();
			}
			red /= toAverage.size();
			green /= toAverage.size();
			blue /= toAverage.size();
			Color pix = new Color(red, green, blue);
			this.buffer.setRGB(x/superSampling, y/superSampling, pix.getRGB());
			//System.out.println(pix);
			return pix;
		}
		else{
			Ray ray = new Ray(x, y, camera, 1);
			Color pix = rayTrace(ray, 0, x, y, null, debug);
			this.buffer.setRGB(x, y, pix.getRGB());
			return pix;
		}
	}
	
	private Color rayTrace(Ray ray, int recursions, int x, int y, GenericObject incidentObject, boolean debug){
		if(debug){
			System.out.println("Raytracing for x: " + x + " y: " + y);
			System.out.println("Recusion Level: " + recursions);
		}
//		float timeA = System.currentTimeMillis();
		ArrayList<GenericObject> genObjs = world.getRayRenderableObjects();
		Iterator<GenericObject> it = genObjs.iterator();
		while(it.hasNext()){
			GenericObject obj = it.next();
			if(obj != incidentObject)
				obj.rayIntersect(ray);
		}
		if(ray.didIntersect()){
			GenericObject obj = ray.getLowestTValObject();
			Point intersect = ray.getLowestTValIntersect();
			Vector normal = obj.getNormalAt(intersect);
			
			float reflectionFactor = obj.getReflectionFactor();
			float refractionIndex = obj.getRefractionIndex();
			
			//Color Channels
			float ambiantColorR = obj.getAmbiant_c().getRed()/255F;
			float ambiantColorG = obj.getAmbiant_c().getGreen()/255F;
			float ambiantColorB = obj.getAmbiant_c().getBlue()/255F;
			
			float diffuseColorR = obj.getDiffuse_c().getRed()/255F;
			float diffuseColorG = obj.getDiffuse_c().getGreen()/255F;
			float diffuseColorB = obj.getDiffuse_c().getBlue()/255F;
			
			float specularColorR = obj.getSpecular_c().getRed()/255F;
			float specularColorG = obj.getSpecular_c().getGreen()/255F;
			float specularColorB = obj.getSpecular_c().getBlue()/255F;
			
			
			//ITERATE LIGHT SOURCES and Compute Color Intensities
			
			//Ambient
			float ambiantIntensityR = obj.getAmbiantFactor()*ambiantColorR;
			float ambiantIntensityG = obj.getAmbiantFactor()*ambiantColorG;
			float ambiantIntensityB = obj.getAmbiantFactor()*ambiantColorB;
			
			float diffuseSpecularIntensityR = 0F;
			float diffuseSpecularIntensityG = 0F;
			float diffuseSpecularIntensityB = 0F;
			
			float reflectionIntensityR = 0F;
			float reflectionIntensityG = 0F;
			float reflectionIntensityB = 0F;
			
			float refractionIntensityR = 0F;
			float refractionIntensityG = 0F;
			float refractionIntensityB = 0F;
			
			Iterator<LightObject> lights = world.getLightObjects().iterator();
//			float numberOfLights = world.getLightObjects().size();
			float lightIntersects = 0;
			ArrayList<Float> diffuseSpecularIntensitiesR = new ArrayList<Float>();
			ArrayList<Float> diffuseSpecularIntensitiesG = new ArrayList<Float>();
			ArrayList<Float> diffuseSpecularIntensitiesB = new ArrayList<Float>();
			
			if(debug){
				System.out.println("\nObj: " + obj + "\nintersect: " + intersect + "\nnorm: " + normal);
			}
			
			while(lights.hasNext()){
				//Get Light Object and Define Factors
				LightObject light = lights.next();
				Point source = null;
				if(light instanceof OmniDirectionalLight){
					OmniDirectionalLight l = (OmniDirectionalLight) light;
					source = l.getSource();
				}
				if(light instanceof WorldLight){
					WorldLight l = (WorldLight) light;
					source = l.getSource(ray.getLowestTValIntersect());
				}
				Iterator<GenericObject> it2 = genObjs.iterator();
//				System.out.println((light instanceof WorldLight) + " " + source);
				//Compute Vectors and Shadow Ray
				Vector s = new Vector(intersect, source);
				Vector v = new Vector(intersect,ray.getE());
				Vector n = normal;
				if(debug){
					System.out.println("s: " + s + "\nv: " + v + "\nn:" + n);
				}
				Ray shadow = new Ray(intersect, source);
				while(it2.hasNext()){
					GenericObject sobj = it2.next();
					if(sobj != obj)
						sobj.rayIntersect(shadow);
				}
				if(shadow.didIntersect()){
//					if(obj instanceof GenericCylinder)
//					System.out.println(shadow.getLowestTValObject());
				}
				float tRay = shadow.rayDetect(source);
				float shadowDotNormal = Math3D.dotProduct(shadow.getD(), n);
				if(((!shadow.didIntersect()) || shadow.getLowestTVal() > tRay)  && shadowDotNormal > 0){ //   || (Math3D.dotProduct(shadow.getLowestTValIntersect(), shadow.getD()) < 0) // || Math3D.dotProduct(shadow.getLowestTValIntersect(), shadow.getD()) > 0A
					//obj.rayIntersect(shadow);
					//if((shadow.getLowestTValObject() == obj && shadow.getLowestTValIntersect() == intersect) || shadow.getLowestTValObject() != obj){
						float diffuseFactor = obj.getDiffuseFactor();
						float specularFactor  = obj.getSpecularFactor();
						lightIntersects ++;
						
						float ns = Math3D.dotProduct(n, s);
						float magN = Math3D.magnitudeOfVector(n);
						float coeff = 2*((ns)/(magN*magN));
						Vector r = Math3D.vectorAdd(Math3D.scalarMultiplyVector(s, -1F), Math3D.scalarMultiplyVector(n, coeff));
						float f = obj.getSpecularFalloff();
						
						
						//Compute Diffuse Intensity and Specular Intensity
						float diffuseIntensity = 0F;
						float nDots = ns/(Math3D.magnitudeOfVector(s)*Math3D.magnitudeOfVector(n));
						diffuseIntensity = diffuseFactor * Math.max(nDots, 0);
						float specularIntensity = 0F;
						double vDotr = Math3D.dotProduct(v, r)/(Math3D.magnitudeOfVector(v)*Math3D.magnitudeOfVector(r));
						if(vDotr > 0)
							specularIntensity = specularFactor * (float)Math.max(Math.pow(vDotr,f), 0);
						
						//Compute Light Source Intensity and Falloff
						//TODO Add Square Distance Falloff
						float magS = Math.abs(Math3D.magnitudeOfVector(s));
						float d = magS;
						float Ip = light.getIntensity(); ///numberOfLights
						
						//Combine Diffuse and Specular Light Intensities
						diffuseSpecularIntensityR = Math.min(Ip*(diffuseIntensity*diffuseColorR + specularIntensity*specularColorR), 1F);
						diffuseSpecularIntensityG = Math.min(Ip*(diffuseIntensity*diffuseColorG + specularIntensity*specularColorG), 1F);
						diffuseSpecularIntensityB = Math.min(Ip*(diffuseIntensity*diffuseColorB + specularIntensity*specularColorB), 1F);
						diffuseSpecularIntensitiesR.add(diffuseSpecularIntensityR);
						diffuseSpecularIntensitiesG.add(diffuseSpecularIntensityG);
						diffuseSpecularIntensitiesB.add(diffuseSpecularIntensityB);
						
						if(debug){
							System.out.println("light: " + light + "\ntRay: " + tRay);
							System.out.println("ns: " + ns + "\nmagN: " + magN + "\ncoeff: " + coeff + "\nr: " + r + "\nf: " + f);
							System.out.println("nDots: " + nDots + "\nvDotr: " + vDotr + "\ndiffIn: " + diffuseIntensity + "\nspecIn: " + specularIntensity);
							System.out.println("magS: " + magS + "\nd: " + d + "\nIp:" + Ip);
							System.out.println("diffuseSpecularR: " + diffuseSpecularIntensityR + "\ndiffuseSpecularG: " + diffuseSpecularIntensityG + "\ndiffuseSpecularB: " + diffuseSpecularIntensityB);
						}
					//}
				}
				else{
//					if(shadow.getLowestTValObject().getRefractionFactor() > 0){
//						//Handle Focused Light through refractions through objects	
//					}
				}
			}
			//Compute Average DiffuseSpecular
			diffuseSpecularIntensityR = 0F;
			diffuseSpecularIntensityG = 0F;
			diffuseSpecularIntensityB = 0F;
			Iterator<Float> diffSpecR = diffuseSpecularIntensitiesR.iterator();
			Iterator<Float> diffSpecG = diffuseSpecularIntensitiesG.iterator();
			Iterator<Float> diffSpecB = diffuseSpecularIntensitiesB.iterator();
			while(diffSpecR.hasNext()){
				diffuseSpecularIntensityR += diffSpecR.next();
				diffuseSpecularIntensityG += diffSpecG.next();
				diffuseSpecularIntensityB += diffSpecB.next();
			}
			//Normalize very bright to 1
//			diffuseSpecularIntensityR = Math.min(diffuseSpecularIntensityR, 1F);
//			diffuseSpecularIntensityG = Math.min(diffuseSpecularIntensityG, 1F);
//			diffuseSpecularIntensityB = Math.min(diffuseSpecularIntensityB, 1F);
//			if(lightIntersects > 0){
//				diffuseSpecularIntensityR /= lightIntersects;
//				diffuseSpecularIntensityG /= lightIntersects;
//				diffuseSpecularIntensityB /= lightIntersects;
////				System.out.println(lightIntersects);
//			}
			if(debug)
				System.out.println("lightIntersects: " + lightIntersects);
			
			//TODO Modularize the Shaders
			//Compute Recursive Light reflections and refraction
			float iDotn = Math3D.dotProduct(Math3D.normalizeVector(ray.getD()), Math3D.normalizeVector(normal));
			if((reflectionFactor > 0 || refractionIndex > 0) && recursions < this.recursionFactor && iDotn < 0){
				recursions ++;
				boolean criticalAngle = false;
				Point e = intersect;
				
				//Reflection Vector
				float coeff = -2*(iDotn);
				Vector reflectionD = Math3D.scalarMultiplyVector(normal, coeff);
				reflectionD = Math3D.normalizeVector(reflectionD);
				
				//Refraction Vector (assunming transitions with air)
				//Based on math from http://graphics.stanford.edu/courses/cs148-10-summer/docs/2006--degreve--reflection_refraction.pdf
				//Based on Derivation from http://www.starkeffects.com/snells-law-vector.shtml
				float nTheta = Math3D.dotProduct(Math3D.normalizeVector(Math3D.scalarMultiplyVector(ray.getD(), 1F)), Math3D.normalizeVector(normal));
				float cosNTheta = nTheta;//(float) Math.cos(nTheta);
				
				//float sint2 = refractionIndex*refractionIndex*(1-(cosNTheta*cosNTheta));
				Color refraction = Color.WHITE;
				if(debug){
					System.out.println("recursive true\niDotn: " + iDotn + "\ne: " + e + "\ncoeff" + coeff + "\nreflectionD: " + reflectionD + "\n\n");
					System.out.println("\nrefractionIndex: " + refractionIndex + "\nnTheta: " + nTheta + "\ntheta: " + Math.acos(nTheta));
				}
				if(refractionIndex > 0.0F){
					Vector nCrossD = Math3D.crossProdVectors(normal, ray.getD());
					Vector i = Math3D.crossProdVectors(normal, Math3D.crossProdVectors(Math3D.scalarMultiplyVector(normal, -1F), ray.getD()));
					i = Math3D.scalarMultiplyVector(i, refractionIndex);
					float nDotn = Math3D.dotProduct(nCrossD, nCrossD);
					coeff = (float) Math.sqrt(1-refractionIndex*refractionIndex*nDotn);
					Vector j = Math3D.scalarMultiplyVector(normal, coeff);
					Vector refractionD = Math3D.normalizeVector(Math3D.vectorSub(i, j));
					
//					i.setH(1F);
//					coeff = (float) (refractionIndex*cosNTheta - Math.sqrt(1-sint2));
//					Vector d = Math3D.scalarMultiplyVector(normal, coeff);
//					Vector refractionD = Math3D.vectorAdd(i, d);
//					refractionD = Math3D.normalizeVector(refractionD);
//					Point ePlus = Math3D.addPoints(e, Math3D.scalarMultiplyPoint(refractionD, 0.01F)); // Make sure that initial intersect does not count in ray
					Ray refracted = new Ray(e, refractionD);
					
					//Cheat Code
					obj.rayIntersect(refracted);
					if(refracted.getIntersectedObjects().size() > 1)
						refracted = new Ray(refracted.getIntersectedObjects().get(0).getIntersect(), ray.getD());
					
					refraction = rayTrace(refracted, recursions, x, y, obj, debug); //Detect object
					
				}
//				else
//					if(refractionIndex > 0 && sint2 > 1)
//						criticalAngle = true;
						
				Ray incident = new Ray(e, reflectionD);
				
				Color reflection = rayTrace(incident, recursions, x, y, obj, debug); //uses incident object detection aka this obj
				float refractionFactor = 0F;
				if(!criticalAngle && refractionIndex > 0)
					refractionFactor = -cosNTheta;
				reflectionIntensityR = reflectionFactor*reflection.getRed()/255F;
				reflectionIntensityG = reflectionFactor*reflection.getGreen()/255F;
				reflectionIntensityB = reflectionFactor*reflection.getBlue()/255F;
				
				refractionIntensityR = refractionFactor*refraction.getRed()/255F;
				refractionIntensityG = refractionFactor*refraction.getGreen()/255F;
				refractionIntensityB = refractionFactor*refraction.getBlue()/255F;
			}
			else{
				if(debug)
					System.out.println("recursive false, iDotn:" + iDotn);
			}
			
			float lightIntensityR = 0F;
			float lightIntensityG = 0F;
			float lightIntensityB = 0F;
			
			//UVMaps and Output
			Color UVPix = obj.getUVMapAt(intersect);
			if(UVPix != null){
				float pixR = UVPix.getRed()/255F;
				float pixG = UVPix.getGreen()/255F;
				float pixB = UVPix.getBlue()/255F;
				pixR *= ambiantIntensityR + diffuseSpecularIntensityR;
				pixG *= ambiantIntensityG + diffuseSpecularIntensityG;
				pixB *= ambiantIntensityB + diffuseSpecularIntensityB;
				lightIntensityR = Math.min((pixR + reflectionIntensityR + refractionIntensityR), 1F);
				lightIntensityG = Math.min((pixG + reflectionIntensityG + refractionIntensityG), 1F);
				lightIntensityB = Math.min((pixB + reflectionIntensityB + refractionIntensityB), 1F);
			}
			else{
				lightIntensityR = Math.min((ambiantIntensityR + diffuseSpecularIntensityR + reflectionIntensityR + refractionIntensityR), 1F);
				lightIntensityG = Math.min((ambiantIntensityG + diffuseSpecularIntensityG + reflectionIntensityG + refractionIntensityG), 1F);
				lightIntensityB = Math.min((ambiantIntensityB + diffuseSpecularIntensityB + reflectionIntensityB + refractionIntensityB), 1F);
			}
			
			
//			System.out.println(lightIntensityR + " " + lightIntensityG + " " + lightIntensityB);
			Color output = new Color(lightIntensityR, lightIntensityG, lightIntensityB);
			
//			float timeB = System.currentTimeMillis();
//			frameComputeTime += timeB-timeA;
			if(debug)
				System.out.println("Finished Ray Trace Recusion Level: " + recursions + "\n\n\n");
			return output;
		}
		else{
			if(debug){
				System.out.println("No Intersection");
			}
			if(backgroundImage != null){
				Color pix;
				int xPix = 0;
				int yPix = 0;
				
				if(x < backgroundImage.getWidth() && y < backgroundImage.getHeight()){
					xPix = x;
					yPix = y;
					pix = new Color(backgroundImage.getRGB(xPix, yPix));
				}
				else{
					pix = Color.BLACK;
				}
				
				if(debug)
					System.out.println("Finished Ray Trace Using Background Image Pixel\n\n\n");
				return pix;
			}
			if(debug)
				System.out.println("Finished Ray Trace using Background Color for Pixel\n\n\n");
			return backgroundC;
		}
	}
	
	//Put Modules Here figure out how I want to do that exactly
	@SuppressWarnings("unused")
	private Color reflect(){
		return null;
	}
	
	@SuppressWarnings("unused")
	private Color refract(){
		return null;
	}
	
	@SuppressWarnings("unused")
	private Color diffuseSpecular(){
		return null;
	}
	
	public void renderToScreen(){
		if(this.getVisualDebug()){
			overlay = (Graphics2D) buffer.getGraphics();
			overlay.setColor(Color.WHITE);
			int x = 5;
			int y = 5;
			for (String line : debugInfo().split("\n"))
	            overlay.drawString(line, x, y += overlay.getFontMetrics().getHeight());
		}
		window.updateRender(this.buffer);
	}
	
	public void renderToFile(String file){
		try{
			File outputfile = new File(file + ".png");
		    ImageIO.write(this.buffer, "png", outputfile);
		}catch(Exception e){e.printStackTrace();}
	}
	
	private String debugInfo(){
		String out = "";
		out += "James Wake's Ray Tracer v2\n";
		out += "CS3388 Assignment 4\n";
		out += "Cam: x" + camera.gete().getX() + " y: " + camera.gete().getY() + " z: " + camera.gete().getZ() + "\n";
		out += "u: x:" + camera.getu().getX() + " y: " + camera.getu().getY() + " z:" + camera.getu().getX() + "\n";
		out += "v: x:" + camera.getv().getX() + " y: " + camera.getv().getY() + " z:" + camera.getv().getX() + "\n";
		out += "n: x:" + camera.getn().getX() + " y: " + camera.getn().getY() + " z:" + camera.getn().getX() + "\n";
		out += "frameTime: " + frameComputeTime + "\n";
		if(threading)
			out += "Threading On";
		else
			out += "Threading Off";
		return out;
	}

}
