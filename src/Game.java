// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Game class contains our Player and
// Environment that the Player roams in.
// We make use of the Java Robot class
// to use the mouse to look around

import java.awt.AWTException;
import java.awt.MouseInfo;

import com.jogamp.opengl.GL2;

import java.awt.Point;
import java.awt.Robot;

public class Game {
	private Player player;		// Player that moves around
	private Environment environment;	// Environment where player plays
	private GL2 gl;
	
	//modify mouse movement distances to tolerable limits
	private final float headSensitivity=0.02f;
	private final float pitchSensitivity=0.01f;
	private Point mouseCenter;
	
	// Java Robots used to take input from mouse and keyboard
	private Robot robot;
	// Tracking mouse X, Y movement
	private float dx, dy;
	
	//we need a timer to adjudicate player movements
	private long lastTime;
	
	// Movement boolean flags
	private boolean forward = false;
	private boolean backward = false;
	private boolean strafeLeft = false;
	private boolean strafeRight = false;
	private int shoot = 0;
	private int use = 0;
	
	// State of our game
	private ViewState gameState = ViewState.BOOT_MENU;
	
	// Constructor for our Game
	public Game(GL2 gl){
		this.gl = gl;
		environment = new Environment();
		player = new Player();
		Robot rob = null;
		try{
			rob = new Robot();
		}catch(final AWTException e){System.out.println("Trouble stating Robot");}
		this.robot = rob;
		if(robot == null)System.out.println("Error setting up robot");
	}
	
	// Events that are controlled with booleans
	private void pollEvents()
	{
		// Using timer to move our player
		long now = System.nanoTime();
		float period = (float)((now-lastTime) * 0.000005);
		lastTime=now;
		
		// Mouse movement for looking
		dx=MouseInfo.getPointerInfo().getLocation().x;
		dy=MouseInfo.getPointerInfo().getLocation().y;
		// Heading, i.e., x direction of players point of view
		float heading = mouseCenter.x - dx;
		// Pitch, vertical direction of player's point of view. 
		float pitch = mouseCenter.y - dy;

		// At each loop pass, the matrix will be updated to determine where
		// the player is and where he/she is heading
		if(heading != 0) player.setHeading(heading * headSensitivity);
		if(pitch != 0) player.setPitch(pitch * pitchSensitivity);
		if(forward) player.moveForward((float)period);
		if(backward) player.moveBackward((float)period);
		if(strafeLeft) player.strafeLeft((float)period);
		if(strafeRight) player.strafeRight((float)period);
		if(shoot>0){System.out.println("Shot fired");}
		if(use>0){System.out.println("Using an item");}
		//Once in place this method builds the matrix
		player.set();
	}
	
	// ----------------------------------------------------------------------
	// -------------------	Loop Iteration for our Game ---------------------
	// ----------------------------------------------------------------------
	public void playIteration()
	{
		
		// First drawing anything that relates to the player so that the 
		// items won't be affected when drawing the environment.
		if(gameState == ViewState.BOOT_MENU)
		{
			environment.drawStartMenu(gl);
		}
		else if(gameState == ViewState.IN_GAME)
		{
			pollEvents();
			if(robot != null)
			{
				robot.mouseMove(mouseCenter.x, mouseCenter.y);
			}
				
			shoot=0;
			use=0;
			
			player.draw(gl);
			environment.drawRoom(gl);
		}
		else if(gameState == ViewState.ENDING)
		{
			System.exit(0);
		}
		else if(gameState == ViewState.PAUSE_MENU)
		{
			environment.drawPauseMenu(gl);
		}
		
	}
	
	// Controller for our player
	public void setMouseCenter(Point center)
	{
		this.mouseCenter=center;
	}
	
	// When movement keys pressed, will set appropriate booleans
	public void setForward(boolean flag)
	{
		forward=flag;
	}
	
	public void setBackward(boolean flag)
	{
		backward=flag;
	}
	
	public void setStrafeLeft(boolean flag)
	{
		strafeLeft=flag;
	}
	
	public void setStrafeRight(boolean flag)
	{
		strafeRight=flag;
	}
	
	public void setShoot()
	{
		shoot++;
	}
	
	public void setUse()
	{
		use++;
	}
	
	public void setGameState(ViewState newState)
	{
		gameState = newState;
	}
	
	public ViewState getGameState()
	{
		return this.gameState;
	}
}