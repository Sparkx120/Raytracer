package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window3D extends JFrame{
	private int width = 512;
	private int height = 512;
	private Pane3D drawPane;
	
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -550572534199899045L;
	
	public Window3D(){
		super();
		
		//Configure Window
		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.drawPane = new Pane3D(width, height);
		this.add(drawPane);
		
		//Initialize the Window
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void updateRender(Image buffer){
		this.drawPane.updateBuffer(buffer);
		this.drawPane.repaint();
		System.out.println("Updated and repainted Pane");
	}
	
	public int getPaneWidth(){
		return this.width;
	}
	
	public int getPaneHeight(){
		return this.height;
	}
}
