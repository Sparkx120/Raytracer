package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window3D extends JFrame implements MouseListener, KeyListener{
	private int width = 1024;
	private int height = 1024;
	private Pane3D drawPane;
	private CamObject3D camera;
	private Renderer renderer;
	
	private boolean keyIsPressed;
	private boolean mouseLeftIsPressed;
	private int sampleRate;
	private int currentSample;
	
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -550572534199899045L;
	
	public Window3D(CamObject3D camera){
		super();
		
		//Configure Window
		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.camera = camera;
		
		this.drawPane = new Pane3D(width, height);
		this.add(drawPane);
		
		this.keyIsPressed = false;
		this.mouseLeftIsPressed = false;
		this.sampleRate = 50;
		this.currentSample = 0;
		this.addMouseListener(this);
		this.addKeyListener(this);
		
		//Initialize the Window
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void updateRender(Image buffer){
		this.drawPane.updateBuffer(buffer);
		this.drawPane.repaint();
		System.out.println("Updated and repainted Pane");
	}
	
	public void setRenderer(Renderer renderer){
		this.renderer = renderer;
	}
	
	public int getPaneWidth(){
		return this.width;
	}
	
	public int getPaneHeight(){
		return this.height;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Do Nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()){
			case 'a': camera.translateCameraV(-0.1F); break;
			case 'd': camera.translateCameraV(0.1F); break;
			case 'w': camera.translateCameraN(-0.1F); break;
			case 's': camera.translateCameraN(0.1F); break;
			case 'q': camera.rotateCameraU(-1); break;
			case 'e': camera.rotateCameraU(1); break;
			case 'z': camera.rotateCameraV(-1); break;
			case 'c': camera.rotateCameraV(1); break;
		}
		camera.renderFrame(renderer);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//End KeyPressed
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Start Mouse Pressed and send Point
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//End Mouse Pressed Parse Last Var
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//End Mouse Pressed
	}
}
