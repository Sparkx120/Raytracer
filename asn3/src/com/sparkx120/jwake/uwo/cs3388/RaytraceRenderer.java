package com.sparkx120.jwake.uwo.cs3388;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RaytraceRenderer extends Renderer{
	
	private int supersampling = 1;
	private int width;
	private int height;
	private Window3D window;
	private World world;
	private CamObject3D camera;
	/**
	 * The local Image Buffer
	 */
	private BufferedImage buffer;
	
	public RaytraceRenderer(Window3D window, World w, CamObject3D camera){
		super(RendererType.CAMERA_PIPE);
		width = window.getWidth();
		height = window.getHeight();
		this.window = window;
		this.world = w;
		this.camera = camera;
	}

	@Override
	public void renderObjects(ArrayList<PolyObject3D> objs) {
		//Unimplemented as this is not an OBJECT_DATA renderer
	}

	@Override
	public void renderRayPixel(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
