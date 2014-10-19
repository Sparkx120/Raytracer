package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;

public class WireframeRenderer extends Renderer{
	private Window3D window;
	private Color backgroundC;
	private Color lineC;
	private boolean removeBackFaces = false;
	private BufferedImage buffer;
	
	/**
	 * Default Constructor for the Wireframe Renderer
	 * @param window - A Window 3D Viewer Frame
	 * @param backgroundC - The Background Color to use
	 * @param lineC - The Line Color to use
	 */
	public WireframeRenderer(Window3D window, Color backgroundC, Color lineC){
		this.window = window;
		this.backgroundC = backgroundC;
		this.lineC = lineC;
		this.buffer = new BufferedImage(window.getPaneWidth(), window.getPaneHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	@Override
	public void renderObjects(ArrayList<PolyObject3D> objs) {
		Iterator<PolyObject3D> it = objs.iterator();
		
		this.buffer = new BufferedImage(window.getPaneWidth(), window.getPaneHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for(int i=0; i<buffer.getWidth(); i++)
			for(int j=0; j<buffer.getHeight(); j++)
				buffer.setRGB(i, j, backgroundC.getRGB());
		System.out.println("Drew Background Now Drawing Lines");
		while(it.hasNext()){
			Iterator<Polygon> polys = it.next().getFaces().iterator();
			
			while(polys.hasNext()){
				Polygon poly = polys.next();
				//Check if Visible with Normal Bresenham Handles OutOfBound Coords
				if(poly.getNorm().getZ()>0 && removeBackFaces){
					//Draw Line A B
					float x1 = poly.getVertexA().getPoint().getX();
					float x2 = poly.getVertexB().getPoint().getX();
					float y1 = poly.getVertexA().getPoint().getY();
					float y2 = poly.getVertexB().getPoint().getY();
					BresenhamLineDrawing.drawLine(x1, x2, y1, y2, buffer, lineC);
					
					//Draw Line B C
					x1 = poly.getVertexB().getPoint().getX();
					x2 = poly.getVertexC().getPoint().getX();
					y1 = poly.getVertexB().getPoint().getY();
					y2 = poly.getVertexC().getPoint().getY();
					BresenhamLineDrawing.drawLine(x1, x2, y1, y2, buffer, lineC);
					
					//Draw Line C A
					x1 = poly.getVertexC().getPoint().getX();
					x2 = poly.getVertexA().getPoint().getX();
					y1 = poly.getVertexC().getPoint().getY();
					y2 = poly.getVertexA().getPoint().getY();
					BresenhamLineDrawing.drawLine(x1, x2, y1, y2, buffer, lineC);
				}
			}
		}
		System.out.println("Finished Drawing Line");
		window.updateRender(buffer);
	}
	
}
