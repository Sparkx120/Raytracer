package com.sparkx120.jwake.uwo.cs3388;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Wireframe Renderer that extends Renderer
 * @author James Wake
 * @version 1.0
 *
 */
public class WireframeRenderer extends Renderer{
	/**
	 * The Window3D this Renderer is attached to
	 */
	private Window3D window;
	/**
	 * The colors to use
	 */
	private Color backgroundC;
	private Color lineC;
	/**
	 * Weather to remove backfaces
	 */
	private boolean removeBackFaces = true;
	/**
	 * The local Image Buffer
	 */
	private BufferedImage buffer;
	
	/**
	 * Default Constructor for the Wireframe Renderer
	 * @param window - A Window 3D Viewer Frame
	 * @param backgroundC - The Background Color to use
	 * @param lineC - The Line Color to use
	 */
	public WireframeRenderer(Window3D window, Color backgroundC, Color lineC){
		super(RendererType.OBJECT_DATA);
		this.window = window;
		this.backgroundC = backgroundC;
		this.lineC = lineC;
		this.buffer = new BufferedImage(window.getPaneWidth(), window.getPaneHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	/**
	 * Renders Objects (Will change to render Polygons and take a ploly list instead for futrue version)
	 */
	@Override
	public void renderObjects(ArrayList<PolyObject3D> objs) {
		Iterator<PolyObject3D> it = objs.iterator();
		
		//Generate a new Buffer and fill it with the Background Color
		this.buffer = new BufferedImage(window.getPaneWidth(), window.getPaneHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = buffer.createGraphics();
		g.setColor(backgroundC);
		g.fillRect(0, 0, window.getPaneWidth(), window.getPaneHeight());
//		for(int i=0; i<buffer.getWidth(); i++)
//			for(int j=0; j<buffer.getHeight(); j++)
//				buffer.setRGB(i, j, backgroundC.getRGB());
		
		//Iterate through all polygons and draw them
		while(it.hasNext()){
			Iterator<Polygon> polys = it.next().getFaces().iterator();
			
			while(polys.hasNext()){
				Polygon poly = polys.next();
				//Check if Visible with Normal Bresenham Handles OutOfBound Coords
				if(removeBackFaces){
					if(poly.getNorm().getZ()>0){
						drawPoly(poly);
					}
				}
				else{
					drawPoly(poly);
				}
			}
		}
		
		//Rerender Frame to the the Pane
		window.updateRender(buffer);
	}
	
	/**
	 * Switches whether backfaces are to be drawn
	 */
	public void switchBackfaceMode(){
		if(removeBackFaces)
			removeBackFaces = false;
		else
			removeBackFaces = true;
	}
	
	/**
	 * Draws a single Polygon to the buffer
	 * @param poly - The polygon to draw
	 */
	private void drawPoly(Polygon poly){
		//If Polygon is in front of the camera
		if((poly.getVertexA().getPoint().getZ() < 1 && poly.getVertexA().getPoint().getZ() > 0) &&
		   (poly.getVertexB().getPoint().getZ() < 1 && poly.getVertexB().getPoint().getZ() > 0) &&
		   (poly.getVertexC().getPoint().getZ() < 1 && poly.getVertexC().getPoint().getZ() > 0)){
			
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

	@Override
	public void renderRayPixel(int x, int y, RayData data) {
		//Unimplemented as this is not a Ray Tracer Type	
	}
}
