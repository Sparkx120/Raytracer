package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

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

	@Override
	public Color renderRayPixel(int x, int y, boolean debug) {
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
		float timeA = System.currentTimeMillis();
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
			float refractionFactor = obj.getRefractionFactor();
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
			float numberOfLights = world.getLightObjects().size();
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
				Point source = light.getSource();
				Iterator<GenericObject> it2 = genObjs.iterator();
				
				//Compute Vectors and Shadow Ray
				Vector s = new Vector(intersect, light.getSource());
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
					obj.rayIntersect(shadow);
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
					//DO NOthing
				}
			}
			//Compute Average DiffuseSpecular
			Iterator<Float> diffSpecR = diffuseSpecularIntensitiesR.iterator();
			Iterator<Float> diffSpecG = diffuseSpecularIntensitiesG.iterator();
			Iterator<Float> diffSpecB = diffuseSpecularIntensitiesB.iterator();
			while(diffSpecR.hasNext()){
				diffuseSpecularIntensityR += diffSpecR.next();
				diffuseSpecularIntensityG += diffSpecG.next();
				diffuseSpecularIntensityB += diffSpecB.next();
			}
//			if(lightIntersects > 0){
//				diffuseSpecularIntensityR /= lightIntersects;
//				diffuseSpecularIntensityG /= lightIntersects;
//				diffuseSpecularIntensityB /= lightIntersects;
////				System.out.println(lightIntersects);
//			}
			if(debug)
				System.out.println("lightIntersects: " + lightIntersects);
			
			//Compute Recursive Light
			float iDotn = Math3D.dotProduct(ray.getD(), normal);
			if(reflectionFactor > 0 && recursions < this.recursionFactor && iDotn < 0){
				recursions ++;
				Point e = intersect;
				float coeff = -2*(iDotn);
				Vector d = Math3D.scalarMultiplyVector(normal, coeff);
				d = Math3D.normalizeVector(d);
//				d = Math3D.scalarMultiplyVector(d, -1F);
				//System.out.println(Math3D.dotProduct(intersect, normal));
				if(debug){
					System.out.println("recursive true\niDotn: " + iDotn + "\ne: " + e + "\ncoeff" + coeff + "\nd: " + d + "\n\n");
				}
				Ray incident = new Ray(e, d);
				Color reflection = rayTrace(incident, recursions, x, y, obj, debug);
				reflectionIntensityR = reflectionFactor*reflection.getRed()/255F;
				reflectionIntensityG = reflectionFactor*reflection.getGreen()/255F;
				reflectionIntensityB = reflectionFactor*reflection.getBlue()/255F;
			}
			else{
				if(debug)
					System.out.println("recursive false, iDotn:" + iDotn);
			}
			
			//Compute Refracted Light
			
			
			float lightIntensityR = Math.min((ambiantIntensityR + diffuseSpecularIntensityR + reflectionIntensityR + refractionIntensityR), 1F);
			float lightIntensityG = Math.min((ambiantIntensityG + diffuseSpecularIntensityG + reflectionIntensityG + refractionIntensityG), 1F);
			float lightIntensityB = Math.min((ambiantIntensityB + diffuseSpecularIntensityB + reflectionIntensityB + refractionIntensityB), 1F);
//			System.out.println(lightIntensityR + " " + lightIntensityG + " " + lightIntensityB);
			Color output = new Color(lightIntensityR, lightIntensityG, lightIntensityB);
			
			float timeB = System.currentTimeMillis();
			frameComputeTime += timeB-timeA;
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
				if(backgroundImage.getWidth() <= x && backgroundImage.getHeight() <= y){
					pix = new Color(backgroundImage.getRGB(x, y));
				}
				else{
					if(backgroundC != null)
						pix = backgroundC;
					else
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
	
	public void renderToScreen(){
		if(this.getVisualDebug()){
			overlay.setColor(Color.WHITE);
			int x = 5;
			int y = 5;
			for (String line : debugInfo().split("\n"))
	            overlay.drawString(line, x, y += overlay.getFontMetrics().getHeight());
		}
		window.updateRender(this.buffer);
		
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		overlay = this.buffer.createGraphics();
		frameComputeTime = 0;
	}
	
	private String debugInfo(){
		String out = "";
		out += "Cam: x" + camera.gete().getX() + " y: " + camera.gete().getY() + " z: " + camera.gete().getZ() + "\n";
		out += "u: x:" + camera.getu().getX() + " y: " + camera.getu().getY() + " z:" + camera.getu().getX() + "\n";
		out += "v: x:" + camera.getv().getX() + " y: " + camera.getv().getY() + " z:" + camera.getv().getX() + "\n";
		out += "n: x:" + camera.getn().getX() + " y: " + camera.getn().getY() + " z:" + camera.getn().getX() + "\n";
		out += "frameTime: " + frameComputeTime;
		return out;
	}

}
