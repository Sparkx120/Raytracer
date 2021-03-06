package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * An extension of JPanel for Drawing 3D Buffered Graphics
 * @author James Wake
 * @version 1.0
 *
 */
public class Pane3D extends JPanel{

	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = 7767506544058195953L;
	
	/**
	 * Image Buffer for Buffered Rendering
	 */
	private Image buffer;
	
	/**
	 * The width and height
	 */
	private int width;
	private int height;
	
	/**
	 * Default Constructor for Pane3D
	 * @param width - The width to set the Pane3D
	 * @param height - The height to set the Pane3D
	 */
	public Pane3D(int width, int height){
		super();
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Graphics override for drawing the buffered graphics
	 */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;
        
        g.drawImage(buffer, 0, 0, width, height, null);
    }
	
	/**
	 * Update the buffered image reference
	 * @param newBuf - The New Buffered Image to use
	 */
	public void updateBuffer(Image newBuf){
		this.buffer = newBuf;
	}
}
