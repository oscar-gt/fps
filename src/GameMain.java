// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Main class that runs our program. 
// See submitted design document for
// overall description/overview

// Note: example code provided in class and code
// snippets from online sources used. 

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;


public class GameMain implements GLEventListener{
	private Game game;
	private static boolean fullscreen=false;
	// Screen dimensions
	private int w=1024;
	private int h=768;
	private static Point center;
	private Point mousePos;
	
	// Keys used to move and shoot
	private int forward = KeyEvent.VK_W;
	private int backward = KeyEvent.VK_S;
	private int strafel = KeyEvent.VK_A;
	private int strafer = KeyEvent.VK_D;
	private int shoot = InputEvent.BUTTON1_MASK;
	private int use = InputEvent.BUTTON3_MASK;
	
	// Other keys
	private int startGame = KeyEvent.VK_ENTER;
	
	// Light positino
	float[] light_position = {0f, 10.0f, -50f, 1.0f};
		

	public static
	void main(String[] args) 
	{
		Frame frame = new Frame("JOGL Events");
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Cursor pointer = t.createCustomCursor(img, new Point(0,0), "none");
		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(new GameMain());
		canvas.setFocusable(true);
		canvas.requestFocus();
		frame.add(canvas);
		frame.setUndecorated(true);
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setCursor(pointer);
		frame.setVisible(true);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		if(fullscreen){
			ge.getDefaultScreenDevice().setFullScreenWindow(frame);
		}
		final com.jogamp.opengl.util.Animator animator = 
				new com.jogamp.opengl.util.Animator(canvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();
		Rectangle r=frame.getBounds();
		center=new Point(r.x+r.width/2, r.y+r.height/2);
	}

	public void init(GLAutoDrawable drawable) 
	{
		GL2 gl = (GL2) drawable.getGL();
		game=new Game(gl);
		game.setMouseCenter(center);
		mousePos=center;
		GLU glu=new GLU();
		gl.glViewport(0, 0, w, h);
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		glu.gluPerspective(45.0f, ((float)w/(float)h), 0.1f, 100.0f);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// *****************************************************
		// ------------- Lighting 	------------------
		float[] whiteMaterial = {0.2f, 0.2f, 0.8f, 1.0f};
 
		float[] lmodel_ambient = {0.1f, 0.1f, 0.1f, 1.0f};

		
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 0);

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, whiteMaterial, 0);

		// red diffuse light
		float[] light_diffuse = {0.9f, 0.15f, 0.15f, 0f};

		// on light0
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);

		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);



		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		// Capturing user input to move our player
		((Component) drawable).addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e){
				// Escape key kills our game/program
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					new Thread(new Runnable(){
						public void run(){
							System.exit(0);
						}
					}).start();
				}
				// Capturing movement keys
				// "W" for forward, "S" for backward
				// "A" for left strafe, "D" for right strafe
				if(e.getKeyCode() == forward)game.setForward(true);
				if(e.getKeyCode() == backward)game.setBackward(true);
				if(e.getKeyCode() == strafel)game.setStrafeLeft(true);
				if(e.getKeyCode() == strafer)game.setStrafeRight(true);
				
				// Pressing Enter key could do a few things depending on state
				if(e.getKeyCode() == startGame)
				{
					ViewState currentState = game.getGameState();
					if(currentState == ViewState.BOOT_MENU)
					{
						game.setGameState(ViewState.IN_GAME);
					}
					else if(currentState == ViewState.PAUSE_MENU)
					{
						game.setGameState(ViewState.IN_GAME);
					}
					else if(currentState == ViewState.IN_GAME)
					{
						game.setGameState(ViewState.PAUSE_MENU);
					}
				}
					
			}

			// Stopping movement when keys are released
			@Override
			public void keyReleased(KeyEvent e){
				if(e.getKeyCode()==forward)game.setForward(false);
				if(e.getKeyCode()==backward)game.setBackward(false);
				if(e.getKeyCode()==strafel)game.setStrafeLeft(false);
				if(e.getKeyCode()==strafer)game.setStrafeRight(false);
			}
		});

		// Mouse shooting functionality
		((Component) drawable).addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				if((e.getModifiers() & shoot)!=0)
					game.setShoot();
				if((e.getModifiers() & use)!=0)
					game.setUse();
			}
		});
	}

	public void display(GLAutoDrawable drawable) 
	{
		GL2 gl = (GL2) drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		// *******************************************************
		// -------------- Add lighting 	------------------
		
		 gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);
		
		gl.glLoadIdentity();

		

		game.playIteration();
		gl.glFlush();
	}
	public void reshape(GLAutoDrawable drawable, int x, int y, 
			int width, int height) 
	{
		GL2 gl = (GL2) drawable.getGL();
		GLU glu = new GLU();
		if (height <= 0) { height = 1; }
		final float h=(float)width/(float)height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// Variables for gluPerspective. 
		// gluPerspective() dictates the viewing cut off points.
		float FOVAngle = 90.0f;	// Field of view angle
		float aspect = h;		// Aspect
		double zNear = 0.05;	// Geometry < zNear not shown (discarded)
		double zFar = 20.0;		// Geometry > zFar not shown (discarded)
		glu.gluPerspective(FOVAngle, aspect, zNear, zFar);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		center=new Point(x+width/2, y+height/2);
		game.setMouseCenter(center);
	}
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, 
			boolean deviceChanged) {}
	@Override
	public void dispose(GLAutoDrawable drawable) 
	{


	}
}