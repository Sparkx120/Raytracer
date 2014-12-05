package com.sparkx120.jwake.graphics2d.mandlebrot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sparkx120.jwake.graphics3d.gui.BufferedGraphicsPane;
import com.sparkx120.jwake.graphics3d.gui.Window3D;

public class Mandlebrot {
	private int iterationsMax;
	private int width;
	private int height;
	private BufferedGraphicsPane buffered;
	private boolean rePaint = false;
	
	public static void main(String[] args){
		int iterationsMax = 1000;
		if(args.length == 1){
			iterationsMax = Integer.parseInt(args[0]);
		}
		//TODO Make a test run app
//		Window3D window = new Window3D();
//		window.setTitle("Mandlebrot");
		
	}
	
	public Mandlebrot(int iterations, int width, int height){
		this.iterationsMax = iterations;
		this.width = width;
		this.height = height;
	}
	
	public BufferedImage generateMandlebrot(){
		BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		System.out.println("Computing Mandlebrot");
		for(int xPix=0; xPix<width; xPix++){
			for(int yPix=0; yPix<height; yPix++){
				float x0 = ((3.5F*xPix)/width) - 2.5F;
				float y0 = ((2F*yPix)/height) - 1F;
				float x = 0.0F;
				float y = 0.0F;
				int localIterations = 0;
				while(x*x + y*y < 2*2 && localIterations < iterationsMax){
					float xtemp =x*x - y*y + x0;
					y = 2*x*y + y0;
					x = xtemp;
					localIterations ++;
				}
				Color pix = new Color(0F,0F,((localIterations%255)/255F));
//				System.out.println((localIterations%255)/255F);
				out.setRGB(xPix, yPix, pix.getRGB());
			}
		}
		System.out.println("Done Computing Mandlebrot");
		return out;
	}
	
	public void generateMandlebrotToFrame(){
		BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) out.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		System.out.println("Computing Mandlebrot");
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	for(int xPix=0; xPix<width; xPix++){
					for(int yPix=0; yPix<height; yPix++){
						float x0 = ((3.5F*xPix)/width) - 2.5F;
						float y0 = ((2F*yPix)/height) - 1F;
						float x = 0.0F;
						float y = 0.0F;
						int localIterations = 0;
						while(x*x + y*y < 2*2 && localIterations < iterationsMax){
							float xtemp =x*x - y*y + x0;
							y = 2*x*y + y0;
							x = xtemp;
							localIterations ++;
						}
						Color pix = new Color(0F,0F,((localIterations%255)/255F));
//						System.out.println((localIterations%255)/255F);
						out.setRGB(xPix, yPix, pix.getRGB());
					}
					buffered.updateBuffer(out);
					if(xPix%(width/100) == 0) 
						buffered.paintImmediately(0, 0, width, height);
				}
		    }
		});
		
		
		System.out.println("Done Computing Mandlebrot");
	}
	
	public void launchMandlebrotFrame(){
		JFrame m = new JFrame("Mandlebrot");
		m.setSize(width, height);
		buffered = new BufferedGraphicsPane(width, height);
		m.add(buffered);
		m.setVisible(true);
		
		Thread ready = new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					if(rePaint){
						System.out.println("repaiting");
						rePaint = false;
						buffered.repaint();
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		ready.setPriority(Thread.MAX_PRIORITY);
		ready.start();
	}
}
