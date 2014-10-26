package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class Pane3D extends JPanel{

	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 7767506544058195953L;
	
	/**
	 * Image Buffer for Buffered Rendering
	 */
	private Image buffer;
	
	private int width;
	
	private int height;
	
	public Pane3D(int width, int height){
		super();
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        
        g.drawImage(buffer, 0, 0, width, height, null);
    }
	
	public void updateBuffer(Image newBuf){
		this.buffer = newBuf;
	}
}
