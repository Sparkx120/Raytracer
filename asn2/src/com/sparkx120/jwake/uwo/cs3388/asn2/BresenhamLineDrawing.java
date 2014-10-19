package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BresenhamLineDrawing {
	
	public static void drawLine(int x1, int x2, int y1, int y2, BufferedImage image, Color color){
		bresenhamPlot(x1, y1, x2, y2, image, color);
	}
	
	public static void drawLine(float x1f, float x2f, float y1f, float y2f, BufferedImage image, Color color){
		int x1 = (int) Math.abs(x1f);
		int x2 = (int) Math.abs(x2f);
		int y1 = (int) Math.abs(y1f);
		int y2 = (int) Math.abs(y2f);
		
		System.out.println(x1f + " " + y1f + " " + x2f + " " + y2f);
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(color);
		g.drawLine(x1, y1, x2, y2);
		//bresenhamPlot(x1, y1, x2, y2, image, color);
	}
	
	/**
	 * Implementation of the Bresenham Line Algorithm (Moved from Javascript and lambdas converted to methods)
	 * Added Line Clipping to Image
	 * @param x1 - First X Coordinate
	 * @param y1 - First Y Coordinate
	 * @param x2 - Second X Coordinate
	 * @param y2 - Second Y Coordinate
	 * @param image - An Image to draw on
	 * @param color - The color of the line
	 */
	private static void bresenhamPlot(int x1, int y1, int x2, int y2, BufferedImage image, Color color){
		//Get preliminary Data from LineObject
		int deltaX = x2 - x1;
		int deltaY = y2 - y1;

		//Initialize Variables for Algorithm
		int iInitial = 0;
		int loopInc = 0;
		int dX = deltaX;
		int dY = deltaY;
		int stepPixel = 0;
		int stepPixelInc = 0;
		int loopPixel = 0;
		int loopPixelInc = 0;
		
		//Compute Octant Dependant Variables for algorithm and setup said Variables
		int octant = computeOctant(deltaX, deltaY);
		switch(octant){
		case 0: iInitial = x1;
				loopInc = 1;
				dX = deltaX;
				dY = deltaY;
				stepPixel = y1;
				stepPixelInc = 1;
				loopPixel = x1;
				loopPixelInc = 1;
				break;
		case 1: iInitial = y1;
				loopInc = 1;
				dX = deltaY;
				dY = deltaX;
				stepPixel = x1;
				stepPixelInc = 1;
				loopPixel = y1;
				loopPixelInc = 1;
				break;
		case 2: iInitial = y1;
				loopInc = 1;
				dX = deltaY;
				dY = deltaX;
				stepPixel = x1;
				stepPixelInc = -1;
				loopPixel = y1;
				loopPixelInc = 1;
				break;
		case 3: iInitial = x1;
				loopInc = -1;
				dX = deltaX;
				dY = deltaY;
				stepPixel = y1;
				stepPixelInc = 1;
				loopPixel = x1;
				loopPixelInc = -1;
				break;
		case 4: iInitial = x1;
				loopInc = -1;
				dX = deltaX;
				dY = deltaY;
				stepPixel = y1;
				stepPixelInc = -1;
				loopPixel = x1;
				loopPixelInc = -1;
				break;
		case 5: iInitial = y1;
				loopInc = -1;
				dX = deltaY;
				dY = deltaX;
				stepPixel = x1;
				stepPixelInc = -1;
				loopPixel = y1;
				loopPixelInc = -1;
				break; 
		case 6: iInitial = y1;
				loopInc = -1;
				dX = deltaY;
				dY = deltaX;
				stepPixel = x1;
				stepPixelInc = 1;
				loopPixel = y1;
				loopPixelInc = -1;
				break;
		case 7: iInitial = x1;
				loopInc = 1;
				dX = deltaX;
				dY = deltaY;
				stepPixel = y1;
				stepPixelInc = -1;
				loopPixel = x1;
				loopPixelInc = 1;
				break;
		}
		
		//Bresenham Algorithm (Based on the algo provided)
		
		//Only Draw if Visible
		if(x1 > 0 && y1 > 0 && x1 < image.getWidth() && y1 < image.getHeight())
			image.setRGB(x1, y1, color.getRGB());
		
		int pi = 0;
		int z = 0;
		
		for(int i=iInitial; loopCondition(i, x1, x2, y1, y2, octant); i += loopInc){
			if(z==0){
				pi = 2*dY - dX;
				z++;
			}
			else{
				if(pi<0)
					pi = pi + 2*dY;
				else{
					pi = pi + 2*dY - 2*dX;
					stepPixel += stepPixelInc;
				}
			}
			loopPixel += loopPixelInc;
			int[] loopPix = loopPixelValue(stepPixel, loopPixel, octant);
			
			//Only draw if visible
			if(loopPix[0] > 0 && loopPix[1] > 0 && loopPix[0] < image.getWidth() && loopPix[1] < image.getHeight())
				image.setRGB(loopPix[0], loopPix[1], color.getRGB());
		}
	}
	
	private static boolean loopCondition(int i, int x1, int x2, int y1, int y2, int octant){
		switch(octant){
			case 0: case 7: return i<x2;
			case 1: case 2: return i<y2;
			case 3: case 4: return i>x2;
			case 5: case 6: return i>y2;
		}
		return false;
	}
	
	private static int[] loopPixelValue(int stepPixel, int loopPixel, int octant){
		int[] coord = new int[2]; //0 is x 1 is y
		switch(octant){
			case 0: case 3: case 4: case 7: coord[0] = loopPixel; coord[1] = stepPixel;
			case 1: case 2: case 5: case 6: coord[1] = loopPixel; coord[0] = stepPixel;;
		}
		return coord;
	}
	
	/**
	 * Computes the octant of a line using the Deltas of the line
	 * @param deltaX - The DeltaX of a line
	 * @param deltaY - The DeltaY of a line
	 * @return The Octant the line is in
	 */
	private static int computeOctant(int deltaX, int deltaY){
		int absDeltaX = Math.abs(deltaX);
		int absDeltaY = Math.abs(deltaY);

		if(deltaX >= 0){
			//Case for Octants 0 and 1
			if(deltaY >= 0){
				if(absDeltaX >= absDeltaY)
					return 0;
				else
					return 1;
			}
			//Case for Octants 7 and 6
			else{
				if(absDeltaX >= absDeltaY)
					return 7;
				else
					return 6;
			}
		}
		else{
			//Case for Ocatant 3 and 2
			if(deltaY >= 0){
				if(absDeltaX >= absDeltaY)
					return 3;
				else
					return 2;
			}
			//Case for Octant 4 and 5
			else{
				if(absDeltaX >= absDeltaY)
					return 4;
				else
					return 5;
			}
		}

	}
}
