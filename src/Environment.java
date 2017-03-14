// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Environment class draws the room that the Player roams
// in. 
import java.awt.Color;
import java.awt.Font;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;

public class Environment {

	GL2 gl;
	private double surfaceHeight = 0;
	private ViewState viewState = ViewState.BOOT_MENU;

	// Drawing our floor and walls
	public void drawRoom(GL2 gl)
	{
		// Surface level
		float smallVal = 0.0001f;
		float level = (float)surfaceHeight;
		float r = 0.1f;
		float g = 0.1f;
		float b = 0.1f;

		// Walls
		float verticalExtent = 5f;
		float leftWallX = 700f;
		float rightWallX = 0f;

		float backWallZ = -1000f;
		float frontWallZ = -2f;

		float extent = 1000f;	// Extent of our walls

		// For our grid lines
		float gridLineExtent = 100.0f;	// Extent of grid lines
		float gridInterval = 3.0f;

		float zShift = 500f;

		gl.glBegin(GL2.GL_QUADS);

		// ----------------------	Surface		------------------------
		gl.glColor3d((float)r, (float)g, (float)b);
		// Surface quad
		gl.glVertex3f(-1 * extent, level, -1 * extent + zShift);
		gl.glVertex3f(-1 * extent, level, extent + zShift);
		gl.glVertex3f(extent, 	   level, extent + zShift);
		gl.glVertex3f(extent,	   level, -1 * extent + zShift);
		gl.glEnd();

		// ---------------	Surface grid lines ------------------


		// -----------  Horizontal lines  ---------------
		int columns = 500;
		int rows = 500;
		// Drawing our grid lines
		// X distance from origin
		float x = (float)(columns / 2) * (gridInterval);
		// Z distance from origin
		float z = (float)(rows / 2) * (-1 * gridInterval);
		gl.glBegin(GL2.GL_LINES);
		float gridLineR = 1f;
		float gridLineG = (float)(105.0/255.0);
		float gridLineB = (float)(180.0/255.0);
		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		for(int i = 0; i <= rows; i++)
		{
			// Only z gets updated for the horiz lines
			z = z + (float)(gridInterval * i);
			// Left coord of line
			gl.glVertex3f(x * gridLineExtent * -1, level, z);
			// Right coord of line
			gl.glVertex3f(x * gridLineExtent, level, z);
		}
		// -------------  Vertical lines  -----------------
		float xVert = (columns / 2) * (-1 * gridInterval);
		float zVert = (rows / 2) * (gridInterval);
		for(int k = 0; k <= columns; k++)
		{
			// Only x will get changed/updated
			xVert = xVert + (float)(gridInterval * k);
			// Top coord of line
			gl.glVertex3f(xVert, level, zVert * gridLineExtent);
			// Bottom coord of line
			gl.glVertex3f(xVert, level, -1 * zVert * gridLineExtent);
		}
		gl.glEnd();
		// ---------------- End of surface grid lines -----------------

		// isVertical, level, extent, intervalLength
		//drawGridLines(false, (float)surfaceHeight, extent, gridInterval);



		// ******************************************************
		// ******************************************************
		// ---------------------	Left Wall ------------
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(r, g, b);
		gl.glVertex3f(leftWallX, (float)surfaceHeight, 	extent * -1  + zShift);
		gl.glVertex3f(leftWallX, (float)surfaceHeight,	extent);
		gl.glVertex3f(leftWallX, (float)surfaceHeight + verticalExtent, extent * -1 + zShift);
		gl.glVertex3f(leftWallX, (float)surfaceHeight + verticalExtent, extent + zShift);
		gl.glEnd();

		// --------------	Left wall grid lines	-------------

		// --------------------------////////////////////////----------------
		float y = (float)(columns / 2) * (gridInterval);
		// Z distance from origin
		z = (float)(rows / 2) * (-1 * gridInterval);
		gl.glBegin(GL2.GL_LINES);

		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		// -----------  Vertical lines  ---------------
		for(int i = 0; i <= rows; i++)
		{
			// Only z gets updated for the horiz lines
			z = z + (float)(gridInterval * i);
			// Left coord of line
			gl.glVertex3f(leftWallX, (float)surfaceHeight, z);
			// Right coord of line
			gl.glVertex3f(leftWallX, (float)surfaceHeight + verticalExtent, z);
		}
		// -------------  Horizontal lines  -----------------
		float yVert = (float)surfaceHeight;
		zVert = (rows / 2) * (gridInterval);
		for(int k = 0; k <= columns; k++)
		{

			yVert = yVert + (float)(gridInterval);
			if(yVert >= verticalExtent)
			{
				break;
			}
			// Top coord of line
			gl.glVertex3f(leftWallX, (float)surfaceHeight + yVert, zVert);
			// Bottom coord of line
			gl.glVertex3f(leftWallX, (float)surfaceHeight + yVert, -1 * zVert);
		}
		gl.glEnd();


		// -----------------	End of left wall grid lines  ------------------


		// ******************************************************
		// ******************************************************
		// --------------------	Right Wall 	--------------
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(r, g, 0.6);
		gl.glVertex3f(rightWallX, (float)surfaceHeight, 					extent * -1 + zShift);
		gl.glVertex3f(rightWallX, (float)surfaceHeight,						extent + zShift);
		gl.glVertex3f(rightWallX, (float)surfaceHeight + verticalExtent, 	extent * -1 + zShift);
		gl.glVertex3f(rightWallX, (float)surfaceHeight + verticalExtent, 	extent + zShift);
		gl.glEnd();

		// ------------------	Right wall grid lines	----------------
		y = (float)(columns / 2) * (gridInterval);
		// Z distance from origin
		z = (float)(rows / 2) * (-1 * gridInterval);
		gl.glBegin(GL2.GL_LINES);

		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		// -----------  Vertical lines  ---------------
		for(int i = 0; i <= rows; i++)
		{
			// Only z gets updated for the horiz lines
			z = z + (float)(gridInterval * i);
			// Left coord of line
			gl.glVertex3f(rightWallX, (float)surfaceHeight, z);
			// Right coord of line
			gl.glVertex3f(rightWallX, (float)surfaceHeight + verticalExtent, z);
		}
		// -------------  Horizontal lines  -----------------
		yVert = (float)surfaceHeight;
		zVert = (rows / 2) * (gridInterval);
		for(int k = 0; k <= columns; k++)
		{

			yVert = yVert + (float)(gridInterval);
			if(yVert >= verticalExtent)
			{
				break;
			}
			// Top coord of line
			gl.glVertex3f(rightWallX, (float)surfaceHeight + yVert, zVert);
			// Bottom coord of line
			gl.glVertex3f(rightWallX, (float)surfaceHeight + yVert, -1 * zVert);
		}
		gl.glEnd();
		// --------------	End of right wall grid lines	------------


		// ***********************************************************
		// ***********************************************************
		// --------------------		Front Wall 		-------------------
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(0.6, g, b);
		gl.glVertex3f(extent, 		(float)surfaceHeight, 					frontWallZ + zShift);
		gl.glVertex3f(extent * -1, 	(float)surfaceHeight,					frontWallZ + zShift);
		gl.glVertex3f(extent, 		(float)surfaceHeight + verticalExtent, 	frontWallZ + zShift);
		gl.glVertex3f(extent * -1, 	(float)surfaceHeight + verticalExtent, 	frontWallZ + zShift);
		gl.glEnd();

		// ---------------		Front wall grid lines	---------------
		y = (float)(columns / 2) * (gridInterval);
		// Z distance from origin
		x = (float)(rows / 2) * (-1 * gridInterval);
		gl.glBegin(GL2.GL_LINES);

		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		// -----------  Vertical lines  ---------------
		for(int i = 0; i <= rows; i++)
		{
			// Only z gets updated for the horiz lines
			x = x + (float)(gridInterval * i);
			// Left coord of line
			gl.glVertex3f(x, (float)surfaceHeight, frontWallZ + zShift);
			// Right coord of line
			gl.glVertex3f(x, (float)surfaceHeight + verticalExtent, frontWallZ + zShift);
		}
		// -------------  Horizontal lines  -----------------
		yVert = (float)surfaceHeight;
		zVert = (rows / 2) * (gridInterval);
		for(int k = 0; k <= columns; k++)
		{

			yVert = yVert + (float)(gridInterval);
			if(yVert >= verticalExtent)
			{
				break;
			}
			// Top coord of line
			gl.glVertex3f(extent * -1, (float)surfaceHeight + yVert, frontWallZ + zShift);
			// Bottom coord of line
			gl.glVertex3f(extent, (float)surfaceHeight + yVert, frontWallZ + zShift);
		}
		gl.glEnd();

		// ---------------		End of front wall grid lines	-------


		// ************************************************************
		// ************************************************************
		// --------------------		Back wall	-----------------------
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d(r, g, b);
		gl.glVertex3f(extent, 		(float)surfaceHeight, 					backWallZ + zShift);
		gl.glVertex3f(extent * -1, 	(float)surfaceHeight,					backWallZ + zShift);
		gl.glVertex3f(extent, 		(float)surfaceHeight + verticalExtent, 	backWallZ + zShift);
		gl.glVertex3f(extent * -1, 	(float)surfaceHeight + verticalExtent, 	backWallZ + zShift);
		gl.glEnd();

		// -------------	Back wall grid lines	-----------------
		y = (float)(columns / 2) * (gridInterval);
		// Z distance from origin
		x = (float)(rows / 2) * (-1 * gridInterval);
		gl.glBegin(GL2.GL_LINES);

		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		// -----------  Vertical lines  ---------------
		for(int i = 0; i <= rows; i++)
		{
			// Only z gets updated for the horiz lines
			x = x + (float)(gridInterval * i);
			// Left coord of line
			gl.glVertex3f(x, (float)surfaceHeight, backWallZ + zShift);
			// Right coord of line
			gl.glVertex3f(x, (float)surfaceHeight + verticalExtent, backWallZ + zShift);
		}
		// -------------  Horizontal lines  -----------------
		yVert = (float)surfaceHeight;
		zVert = (rows / 2) * (gridInterval);
		for(int k = 0; k <= columns; k++)
		{

			yVert = yVert + (float)(gridInterval);
			if(yVert >= verticalExtent)
			{
				break;
			}
			// Top coord of line
			gl.glVertex3f(extent * -1, (float)surfaceHeight + yVert, backWallZ + zShift);
			// Bottom coord of line
			gl.glVertex3f(extent, (float)surfaceHeight + yVert, backWallZ + zShift);
		}
		gl.glEnd();

		// -----------		End of back wall grid lines 	----------



		// ***********************************************************
		// ***********************************************************
		// ------------------  Sphere as a sky, with texture ---------

		GLU glu = GLU.createGLU(gl);
		// Sphere color
		float sphereR;
		float sphereG;
		float sphereB;
		//gl.glColor3d(r, g, b);

		// Stacks and slices for sphere
		int stacks = 20;
		int slices = 50;

		// other sphere stuff
		float scaleX = 1f;
		float scaleY = 0.02f; // was 0.7f first
		float scaleZ = 1f; // was 0.5f first
		double radius = 200;

		// Texture info
		String textureName = "stars2.png";
		boolean useTexture = false;
		// Might apply texture
		if(useTexture)
		{
			Texture texture = null;

			texture = TextureGenerator.createTexture(textureName);

			texture.bind(gl);
			texture.enable(gl);
		}

		GLUquadric quadric = glu.gluNewQuadric();
		glu.gluQuadricTexture(quadric, useTexture);

		// Texture modulating
		//gl.glTexGeni(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

		// Cylinder (quadric, baseRadius, topRadius, height, slices, stacks)
		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);


		gl.glScalef(scaleX, scaleY, scaleZ);
		gl.glTranslatef(200f, 2500f, 200f);
		gl.glColor3d(0f, 1f, 1f);
		glu.gluSphere(quadric, radius, slices, stacks);

	}

	public void draw(GL2 gl)
	{
		boolean drawRoom = true;
		if(drawRoom)
		{
			drawRoom(gl);
		}
		else
		{
			System.exit(0);
		}

		if(viewState == ViewState.BOOT_MENU)
		{
			// draw start up menu
			System.out.println("ViewState: " + viewState);
		}
		else if(viewState == ViewState.IN_GAME)
		{
			// draw in game stuff
			System.out.println("ViewState: " + viewState);
		}
		else if(viewState == ViewState.PAUSE_MENU)
		{
			// draw pause/start menu
			System.out.println("ViewState: " + viewState);
		}
		else if(viewState == ViewState.ENDING)
		{
			// Kill game
			System.out.println("ViewState: " + viewState);
			System.exit(0);
		}
		else if(viewState == ViewState.DEBUG)
		{
			System.out.println("ViewState: " + viewState);
			// debug mode. Render environment and rotate it, etc
		}
		else
		{
			System.out.println("Not in a valid ViewState.");
			System.out.println("... killing program.");
		}

		drawAxes(gl);
		this.gl = gl;

	}



	public void drawGridLines()
	{
		// -----------  Horizontal lines  ---------------
		float gridInterval = 2.0f;
		int columns = 500;
		int rows = 500;
		float gridLineExtent = 100.0f;	// Extent of grid lines
		float level = (float)surfaceHeight;
		// Drawing our grid lines
		// X distance from origin
		float x = (float)(columns / 2) * (gridInterval);
		// Z distance from origin
		float z = (float)(rows / 2) * (-1 * gridInterval);
		gl.glBegin(GL2.GL_LINES);
		float gridLineR = 1f;
		float gridLineG = (float)(105.0/255.0);
		float gridLineB = (float)(180.0/255.0);
		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		for(int i = 0; i <= rows; i++)
		{
			// Only z gets updated for the horiz lines
			z = z + (float)(gridInterval * i);
			// Left coord of line
			gl.glVertex3f(x * gridLineExtent * -1, level, z);
			// Right coord of line
			gl.glVertex3f(x * gridLineExtent, level, z);
		}
		// -------------  Vertical lines  -----------------
		float xVert = (columns / 2) * (-1 * gridInterval);
		float zVert = (rows / 2) * (gridInterval);
		for(int k = 0; k <= columns; k++)
		{
			// Only x will get changed/updated
			xVert = xVert + (float)(gridInterval * k);
			// Top coord of line
			gl.glVertex3f(xVert, level, zVert * gridLineExtent);
			// Bottom coord of line
			gl.glVertex3f(xVert, level, -1 * zVert * gridLineExtent);
		}
		gl.glEnd();
	}

	// -------------------------------------------------------------------
	// ---------------- Getting, setting view states ---------------------
	public ViewState getViewState()
	{
		return this.viewState;

	}

	public void setViewState(ViewState state)
	{
		this.viewState = state;
	}



	public void drawAxes(GL2 gl) {
		// drawing the axes
		float offset = 1.25f;
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(1.0, 0.0, 0.0);
		gl.glVertex3f(-offset, 0, 0);
		gl.glVertex3f(offset, 0, 0);
		gl.glColor3d(0.0, 1.0, 0.0);
		gl.glVertex3f(0, -offset, 0);
		gl.glVertex3f(0, offset, 0);
		gl.glColor3d(0.0, 0, 1.0);
		gl.glVertex3f(0, 0, -offset);
		gl.glVertex3f(0, 0, offset);
		gl.glEnd();

	}
	
	public void drawStartMenu(GL2 gl)
	{
		TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 50));
		textRenderer.beginRendering(900, 700);
		textRenderer.setColor(Color.BLUE);
		textRenderer.setSmoothing(true);

		int px = 50;
		int py = 300;
		textRenderer.draw("Welcome.", px, py);
		textRenderer.draw("Press Enter to start the game", px, py - 60);
		textRenderer.draw("or Escape to exit.", px, py - 120);
		textRenderer.endRendering();
		
	}
	
	public void drawPauseMenu(GL2 gl)
	{
		TextRenderer textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 50));
		textRenderer.beginRendering(900, 700);
		textRenderer.setColor(Color.BLUE);
		textRenderer.setSmoothing(true);

		int px = 50;
		int py = 300;
		textRenderer.draw("Game paused. Press enter", px, py);
		textRenderer.draw("to return to the game", px, py - 60);
		textRenderer.draw("or Escape to exit.", px, py - 120);
		textRenderer.endRendering();
	}

}
