package com.sparkx120.jwake.uwo.cs3388;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
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

/**
 * An extension of JFrame that implements Mouse Listeners and a Key Listener to display 3D Graphics
 * @author James Wake
 * @version 1.0
 *
 */
public class Window3D extends JFrame implements MouseListener, MouseMotionListener, KeyListener{
	/**
	 * The Width and Height
	 */
	private int width;
	private int height;
	/**
	 * The Pane3D and Camera and Renderer
	 */
	private Pane3D drawPane;
	private CamObject3D camera;
	private Renderer renderer;
	
	private boolean mouseControl;
	
	/**
	 * Generated Serial UID
	 */
	private static final long serialVersionUID = -550572534199899045L;
	
	/**
	 * Default Constructor for a Window3D JFrame
	 * @param camera - The Camera for this window
	 * @param width - The height of the Window
	 * @param height - The Width of the Window
	 */
	public Window3D(CamObject3D camera, int width, int height){
		super();
		
		//Configure Height and Width
		this.width = width;
		this.height = height;
		
		//Set Camera
		this.camera = camera;
		
		//Configure the Draw Pane3D
		this.drawPane = new Pane3D(width, height);
		this.add(drawPane);
		Insets insets = this.getInsets();
		this.setSize(new Dimension(insets.left + insets.right + width,
											insets.top + insets.bottom + height));
		
		//Configure Window
//		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//Set Up listeners
		this.mouseControl = false;
		this.drawPane.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		
		//Initialize the Window
		//this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}
	
	/**
	 * Updates the Pane3D's Image Buffer
	 * @param buffer
	 */
	public void updateRender(Image buffer){
		this.drawPane.updateBuffer(buffer);
		this.drawPane.repaint();
	}
	
	public void setMouseEnabled(boolean mouseEnabled){
		if(mouseEnabled != mouseControl){
			if(mouseEnabled){
				mouseControl = true;
				this.addMouseListener(this);
			}
			else{
				mouseControl = false;
				this.removeMouseListener(this);
			}
		}
	}
	
	/**
	 * Sets the renderer that this Window3D will use
	 * @param renderer
	 */
	public void setRenderer(Renderer renderer){
		this.renderer = renderer;
	}
	
	/**
	 * Gets the width of this Window3D
	 * @return - The width
	 */
	public int getPaneWidth(){
		return this.width;
	}
	
	/**
	 * Gets the height of this Window3D
	 * @return - The height
	 */
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

	/**
	 * Listener Overrides
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		//Do Nothing
	}

	/**
	 * Handles Keyboard Events
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()){
			case 'd': camera.translateCameraV(-0.5F); break;
			case 'a': camera.translateCameraV(0.5F); break;
			case 'w': camera.translateCameraN(-0.2F); break;
			case 's': camera.translateCameraN(0.2F); break;
			case 'r': camera.translateCameraU(0.1F); break;
			case 'f': camera.translateCameraU(-0.1F); break;
			case 'q': camera.rotateCameraU(-1); break;
			case 'e': camera.rotateCameraU(1); break;
			case 'z': camera.rotateCameraV(-1); break;
			case 'c': camera.rotateCameraV(1); break;
			case '1': camera.rotateCameraN(-1); break;
			case '3': camera.rotateCameraN(1); break;
			case 'v': renderer.setVisualDebug(!renderer.getVisualDebug());
			case 'p': renderer.renderToFile("output");
		}
		camera.renderFrame(renderer);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//End KeyPressed
	}

	/**
	 * Handles Left Mouse Button
	 * Locks the mouse into 3D rotation X/Y
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			if(renderer.getType() == RendererType.OBJECT_DATA){
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
			if(renderer.getType() == RendererType.CAMERA_PIPE){
				renderer.renderRayPixel(e.getX(), e.getY(), true);
			}
		}
	}
	
	/**
	 * Handles all mouse buttons
	 * Right Click switches Wireframe rendering mode
	 */
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
	
	/**
	 * Handles Mouse movement when locked in window by clicking
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if(mouseControl){

			double xDelta = (this.getLocationOnScreen().getX()+(width/2) - e.getXOnScreen());
			double yDelta = (this.getLocationOnScreen().getY()+(height/2) - e.getYOnScreen());
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
