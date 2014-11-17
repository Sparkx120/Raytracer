package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;
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
	
	public RaytraceRenderer(Window3D window, World w, CamObject3D camera, Color background){
		super(RendererType.CAMERA_PIPE);
		width = window.getWidth();
		height = window.getHeight();
		this.window = window;
		this.world = w;
		this.camera = camera;
		backgroundC = background;
		this.buffer = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
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
		if(ray.getLowestTVal() != Ray.NO_INTERSECTION){
			GenericObject obj = ray.getLowestTValObject();
			Point intersect = ray.getLowestTValIntersect();
			Vector normal = obj.getNormalAt(intersect);

			//ITERATE LIGHT SOURCES
			float ambiantIntensityR = obj.getAmbiantFactor()*obj.getAmbiant_c().getRed()/256;
			float ambiantIntensityG = obj.getAmbiantFactor()*obj.getAmbiant_c().getGreen()/256;
			float ambiantIntensityB = obj.getAmbiantFactor()*obj.getAmbiant_c().getBlue()/256;
			
			float diffuseSpecularIntensityR = 0F;
			float diffuseSpecularIntensityG = 0F;
			float diffuseSpecularIntensityB = 0F;
			
			Iterator<LightObject> lights = world.getLightObjects().iterator();
			//float numberOfLights = world.getLightObjects().size();
			while(lights.hasNext()){
				LightObject light = lights.next();
				//TODO This needs revision
				float diffuseFactor = obj.getDiffuseFactor();
				float specularFactor  = obj.getSpecularFactor();
				Vector s = new Vector(light.getSource(), intersect);
				Vector v = new Vector(ray.getE(), intersect);
				Vector n = normal;
				float magN = Math3D.magnitudeOfVector(n);
				float coeff = 2*((Math3D.dotProduct(s, n))/(magN*magN));
				Vector r = Math3D.vectorAdd(Math3D.scalarMultiplyVector(s, -1F), Math3D.scalarMultiplyVector(n, coeff));
				float f = obj.getSpecularFalloff();
//				System.out.println(diffuseFactor + " " + specularFactor);
				float diffuseIntensity = diffuseFactor * Math3D.dotProduct(n, s);
//				System.out.println(Math3D.dotProduct(n, s));
				float specularIntensity = specularFactor * (float) (Math.min(Math.max(Math.pow(Math3D.dotProduct(v, r), f), 0F), 1F));
				System.out.println((float) (Math.min(Math.max(Math.pow(Math3D.dotProduct(v, r), f), 0F), 1F)) + " " + specularFactor + " " + Math3D.dotProduct(n, s));
				//TODO Add Square Distance Falloff
				float magS = Math.abs(Math3D.magnitudeOfVector(s));
				float d = magS;
				float Ip = light.getIntensity()/(d*d);
				System.out.println(diffuseIntensity + " " + specularIntensity + " " + Ip + " " + d + " " + light.getIntensity());
				diffuseSpecularIntensityR = Ip*(diffuseIntensity*obj.getDiffuse_c().getRed()/256 + specularIntensity*obj.getSpecular_c().getRed()/256);
				diffuseSpecularIntensityG = Ip*(diffuseIntensity*obj.getDiffuse_c().getGreen()/256 + specularIntensity*obj.getSpecular_c().getGreen()/256);
				diffuseSpecularIntensityB = Ip*(diffuseIntensity*obj.getDiffuse_c().getBlue()/256 + specularIntensity*obj.getSpecular_c().getBlue()/256);
				System.out.println(diffuseSpecularIntensityR + " " + diffuseSpecularIntensityG + " " + diffuseSpecularIntensityB);
			}
			float lightIntensityR = (ambiantIntensityR + diffuseSpecularIntensityR);
			float lightIntensityG = (ambiantIntensityG + diffuseSpecularIntensityG);
			float lightIntensityB = (ambiantIntensityB + diffuseSpecularIntensityB);
//			System.out.println(lightIntensityR + " " + lightIntensityG + " " + lightIntensityB + " " + ambiantIntensityB + " " +diffuseSpecularIntensityB);
			Color output = new Color(lightIntensityR, lightIntensityG, lightIntensityB);
			
			this.buffer.setRGB(x, y, output.getRGB());
		}
		else{
			this.buffer.setRGB(x, y, backgroundC.getRGB());
		}
		
	}
	
	public void renderToScreen(){
		window.updateRender(this.buffer);
	}

}
