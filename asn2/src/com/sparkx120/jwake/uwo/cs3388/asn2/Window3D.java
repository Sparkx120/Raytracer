package com.sparkx120.jwake.uwo.cs3388.asn2;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window3D extends JFrame{
	private int width = 512;
	private int height = 512;
	
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -550572534199899045L;
	
	public Window3D(){
		super();
		
		//Configure Window
		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Initialize the Window
		this.setVisible(true);
	}
}
