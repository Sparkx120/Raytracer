package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class RaytraceRenderer extends Renderer{
	
	private int supersampling = 1;
	private int width;
	private int height;
	private Window3D window;
	private World world;
	private CamObject3D camera;
	private Color backgroundC;
	/**
	 * The local Image Buffer
	 */
	private BufferedImage buffer;
	private Graphics2D overlay;
	private boolean visualDebug = true;
	
	public RaytraceRenderer(Window3D window, World w, CamObject3D camera, Color background){
		super(RendererType.CAMERA_PIPE);
		width = window.getWidth();
		height = window.getHeight();
		this.window = window;
		this.world = w;
		this.camera = camera;
		backgroundC = background;
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		overlay = this.buffer.createGraphics();
	}

	@Override
	public void renderObjects(ArrayList<PolyObject3D> objs) {
		//Unimplemented as this is not an OBJECT_DATA renderer
	}

	@Override
	public void renderRayPixel(int x, int y) {
		Ray ray = new Ray(x, y, camera);
		ArrayList<GenericObject> genObjs = world.getRayRenderableObjects();
		Iterator<GenericObject> it = genObjs.iterator();
		while(it.hasNext()){
			GenericObject obj = it.next();
			obj.rayIntersect(ray);
		}
		if(ray.didIntersect()){
			GenericObject obj = ray.getLowestTValObject();
			Point intersect = ray.getLowestTValIntersect();
			Vector normal = obj.getNormalAt(intersect);
//			System.out.println(intersect + "\n" + normal + "\n");
			
			//Color Channels
			float ambiantColorR = obj.getAmbiant_c().getRed()/256F;
			float ambiantColorG = obj.getAmbiant_c().getGreen()/256F;
			float ambiantColorB = obj.getAmbiant_c().getBlue()/256F;
			
			float diffuseColorR = obj.getDiffuse_c().getRed()/256F;
			float diffuseColorG = obj.getDiffuse_c().getGreen()/256F;
			float diffuseColorB = obj.getDiffuse_c().getBlue()/256F;
			
			float specularColorR = obj.getSpecular_c().getRed()/256F;
			float specularColorG = obj.getSpecular_c().getGreen()/256F;
			float specularColorB = obj.getSpecular_c().getBlue()/256F;
			
			
			//ITERATE LIGHT SOURCES and Compute Color Intensities
			float ambiantIntensityR = obj.getAmbiantFactor()*ambiantColorR;
			float ambiantIntensityG = obj.getAmbiantFactor()*ambiantColorG;
			float ambiantIntensityB = obj.getAmbiantFactor()*ambiantColorB;
			
			float diffuseSpecularIntensityR = 0F;
			float diffuseSpecularIntensityG = 0F;
			float diffuseSpecularIntensityB = 0F;
			
			Iterator<LightObject> lights = world.getLightObjects().iterator();
			//float numberOfLights = world.getLightObjects().size();
			
			while(lights.hasNext()){
				//Get Light Object and Define Factors
				LightObject light = lights.next();
				Point source = light.getSource();
				Iterator<GenericObject> it2 = genObjs.iterator();
				Ray shadow = new Ray(intersect, source);
				while(it2.hasNext()){
					GenericObject sobj = it2.next();
					if(sobj != obj)
						sobj.rayIntersect(shadow);
				}
				if(!shadow.didIntersect() || obj instanceof GenericSphere){
					//TODO This needs revision
					float diffuseFactor = obj.getDiffuseFactor();
					float specularFactor  = obj.getSpecularFactor();
					
					//Compute Vectors and Shadow Ray
					Vector s = new Vector(intersect, light.getSource());
					Vector v = new Vector(intersect,ray.getE());
					Vector n = normal;
					float ns = Math3D.dotProduct(n, s);
					float magN = Math3D.magnitudeOfVector(n);
					float coeff = 2*((ns)/(magN*magN));
					Vector r = Math3D.vectorAdd(Math3D.scalarMultiplyVector(s, -1F), Math3D.scalarMultiplyVector(n, coeff));
					float f = obj.getSpecularFalloff();
////					System.out.println(diffuseFactor + " " + specularFactor);
					
					//Compute Diffuse Intensity and Specular Intensity
					float nDots = ns/(Math3D.magnitudeOfVector(s)*Math3D.magnitudeOfVector(n)); ///
//					System.out.println(n + "\n" + s + "\n " + nDots + "\n");
					float diffuseIntensity = diffuseFactor * Math.max(nDots, 0F);
////					System.out.println(Math3D.dotProduct(n, s));
					float specularIntensity = 0F;
					double vDotr = Math3D.dotProduct(v, r)/(Math3D.magnitudeOfVector(v)*Math3D.magnitudeOfVector(r));
//					System.out.println(vDotr);
					specularIntensity = specularFactor * (float)Math.max(vDotr, 0D);
//					System.out.println((float) (Math.min(Math.max(Math.pow(Math3D.dotProduct(v, r), f), 0F), 1F)) + " " + specularFactor + " " + Math3D.dotProduct(n, s));
					
					//Compute Light Source Intensity and Falloff
					//TODO Add Square Distance Falloff
					float magS = Math.abs(Math3D.magnitudeOfVector(s));
					float d = magS;
					float Ip = light.getIntensity(); ///d*d
					
					//Combine Diffuse and Specular Light Intensities
//					System.out.println(diffuseIntensity + " " + specularIntensity + " " + Ip + " " + d + " " + light.getIntensity());
					diffuseSpecularIntensityR = Ip*(diffuseIntensity*diffuseColorR + specularIntensity*specularColorR);
					diffuseSpecularIntensityG = Ip*(diffuseIntensity*diffuseColorG + specularIntensity*specularColorG);
					diffuseSpecularIntensityB = Ip*(diffuseIntensity*diffuseColorB + specularIntensity*specularColorB);
//					System.out.println(diffuseSpecularIntensityR + " " + diffuseSpecularIntensityG + " " + diffuseSpecularIntensityB + " " + diffuseIntensity);
				}
			}
			float lightIntensityR = (ambiantIntensityR + diffuseSpecularIntensityR);
			float lightIntensityG = (ambiantIntensityG + diffuseSpecularIntensityG);
			float lightIntensityB = (ambiantIntensityB + diffuseSpecularIntensityB);
//			System.out.println(lightIntensityR + " " + lightIntensityG + " " + lightIntensityB + " " + ambiantIntensityB + " " +diffuseSpecularIntensityB + "\n\n");
			Color output = new Color(lightIntensityR, lightIntensityG, lightIntensityB);
			
			this.buffer.setRGB(x, y, output.getRGB());
		}
		else{
			this.buffer.setRGB(x, y, backgroundC.getRGB());
		}
		
	}
	
	public void renderToScreen(){
		if(visualDebug){
			overlay.setColor(Color.WHITE);
			int x = 5;
			int y = 5;
			for (String line : debugInfo().split("\n"))
	            overlay.drawString(line, x, y += overlay.getFontMetrics().getHeight());
		}
		window.updateRender(this.buffer);
		
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		overlay = this.buffer.createGraphics();
	}
	
	private String debugInfo(){
		String out = "";
		out += "Cam: x" + camera.gete().getX() + " y: " + camera.gete().getY() + " z: " + camera.gete().getZ() + "\n";
		out += "u: x:" + camera.getu().getX() + " y: " + camera.getu().getY() + " z:" + camera.getu().getX() + "\n";
		out += "v: x:" + camera.getv().getX() + " y: " + camera.getv().getY() + " z:" + camera.getv().getX() + "\n";
		out += "n: x:" + camera.getn().getX() + " y: " + camera.getn().getY() + " z:" + camera.getn().getX() + "\n";
		return out;
	}

}
