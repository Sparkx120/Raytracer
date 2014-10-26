package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window3D extends JFrame implements MouseListener, MouseMotionListener, KeyListener{
	private int width;
	private int height;
	private Pane3D drawPane;
	private CamObject3D camera;
	private Renderer renderer;
	
	private boolean keyIsPressed;
	private boolean mouseLeftIsPressed;
	private int sampleRate;
	private int currentSample;
	
	private boolean mouseControl;
	
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -550572534199899045L;
	
	public Window3D(CamObject3D camera, int width, int height){
		super();
		
		//Configure Hieght and Width
		this.width = width;
		this.height = height;
		
		//Configure Window
		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.camera = camera;
		
		//Configure the Draw Pane3D
		this.drawPane = new Pane3D(width, height);
		this.add(drawPane);
		
		//Set Up listeners
		this.mouseControl = false;
		this.keyIsPressed = false;
		this.mouseLeftIsPressed = false;
		this.sampleRate = 50;
		this.currentSample = 0;
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		
		//Initialize the Window
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void updateRender(Image buffer){
		this.drawPane.updateBuffer(buffer);
		this.drawPane.repaint();
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
	
	/**
	 * Sets the cursor invisible
	 * Acquired from: http://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
	 * Date: 2014/10/26
	 */
	private void setCursorInvisible(){
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new java.awt.Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		this.getContentPane().setCursor(blankCursor);
	}
	
	/**
	 * Sets the cursor visible
	 * Acquired from: http://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
	 * Date: 2014/10/26
	 */
	private void setCursorVisible(){
		// Sets the JPanel's cursor to the system default.
		this.getContentPane().setCursor(Cursor.getDefaultCursor());
	}
	
	/**
	 * Move the mouse in Java using Global screen coordinates.
	 * Acquired from: http://stackoverflow.com/questions/2941324/how-do-i-set-the-position-of-the-mouse-in-java
	 * Date: 2014/10/26
	 * 
	 * @param p - The Point to move to
	 */
	private void setMousePosition(java.awt.Point p) {
	    GraphicsEnvironment ge = 
	        GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();

	    // Search the devices for the one that draws the specified point.
	    for (GraphicsDevice device: gs) { 
	        GraphicsConfiguration[] configurations =
	            device.getConfigurations();
	        for (GraphicsConfiguration config: configurations) {
	            Rectangle bounds = config.getBounds();
	            if(bounds.contains(p)) {
	                // Set point to screen coordinates.
	                java.awt.Point b = bounds.getLocation(); 
	                java.awt.Point s = new java.awt.Point(p.x - b.x, p.y - b.y);

	                try {
	                    Robot r = new Robot(device);
	                    r.mouseMove(s.x, s.y);
	                } catch (AWTException e) {
	                    e.printStackTrace();
	                }

	                return;
	            }
	        }
	    }
	    // Couldn't move to the point, it may be off screen.
	    return;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Do Nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()){
			case 'd': camera.translateCameraV(-0.1F); break;
			case 'a': camera.translateCameraV(0.1F); break;
			case 'w': camera.translateCameraN(-0.1F); break;
			case 's': camera.translateCameraN(0.1F); break;
			case 'r': camera.translateCameraU(0.1F); break;
			case 'f': camera.translateCameraU(-0.1F); break;
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
		if(e.getButton() == MouseEvent.BUTTON1){
			if(mouseControl){
				setCursorVisible();
				mouseControl = false;
			}
			else{
				setCursorInvisible();
				setMousePosition(new java.awt.Point((int)this.getLocationOnScreen().getX() + width/2, (int)this.getLocationOnScreen().getY() + height/2));
				mouseControl = true;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(e.getButton()){
			case MouseEvent.BUTTON3: if(renderer instanceof WireframeRenderer){((WireframeRenderer) renderer).switchBackfaceMode(); camera.renderFrame(renderer);} break;
			default: break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Do Nothing
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//Do Nothing
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(mouseControl){

			double xDelta = (this.getLocationOnScreen().getX()+(width/2) - e.getXOnScreen());
			double yDelta = (this.getLocationOnScreen().getY()+(height/2) - e.getYOnScreen());
			System.out.println(xDelta + " " + yDelta);
			camera.rotateCameraU((int)-yDelta);
			camera.rotateCameraV((int)xDelta);
			setMousePosition(new java.awt.Point((int)this.getLocationOnScreen().getX() + width/2, (int)this.getLocationOnScreen().getY() + height/2));
			camera.renderFrame(renderer);
		}
		else{
			//Do Nothing
		}
	}
}
