// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Grid class creates a rectangular surface
// with grid lines

import com.jogamp.opengl.GL2;

// Grid will act as our ground level surface for our city

public class Grid implements Shape
{
	// Dimensions and color of Grid
	private int rows;
	private int columns;	
	private static float intervalLength = 2.0f;
	private double r, g, b;	// Color
	private int gridHeight = 0; // y coord of our plane/grid
	private boolean isHorizontal; // true for horizontal grid, false for vert
	private double gridLevel;

	private GL2 gl;

	// Default values
	private static int defaultCols = 50;
	private static int defaultRows = 50;
	private static double defaultRed = 0.5;
	private static double defaultGreen = 0.5;
	private static double defaultBlue = 0.5;
	private static double defaultGridLevel = 0;

	// Creates a default grid. Relatively small.
	public Grid(GL2 gl)
	{
		// A default grid that is horizontal (i.e., y values constant at 0)
		this(gl, defaultRows, defaultCols, true, defaultGridLevel);
	}

	// Can specify the size of the grid
	public Grid(GL2 gl, int rows, int cols, boolean isHoriz, double gridLev)
	{
		this(gl, rows, cols, defaultRed, defaultGreen, defaultBlue, isHoriz, gridLev);
	}

	// Private constructor
	private Grid(GL2 gl, int rows, int cols, double red, double green, double blue, boolean horiz, double gridLev)
	{
		this.gl = gl;
		this.rows = rows;
		this.columns = cols;
		this.r = red;
		this.g = green;
		this.b = blue;
		this.isHorizontal = horiz;
		this.gridLevel = gridLev;
	}

	// Our grid is simply a quad
	@Override
	public void draw() {
		if(isHorizontal)
		{
			drawHorizontalGrid();
		}
		else
		{
			drawVerticalGrid();
		}

	}

	// --------------- Draws a grid with constant y values ---------------
	public void drawHorizontalGrid()
	{
		float smallVal = 0.0001f;
		float level = (float)gridLevel;
		float extent = 5f;	// Extent of our surface
		float gridLineExtent = 10.0f;	// Extent of grid lines
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3d((float)r, (float)g, (float)b);
		//gl.glColor3d(1.0, 0.5, 0.6);
		gl.glVertex4f(-1 * extent, level, -1 * extent, smallVal);
		gl.glVertex4f(-1 * extent, level, extent, 	   smallVal);
		gl.glVertex4f(extent, 	   level, extent, 	   smallVal);
		gl.glVertex4f(extent,	   level, -1 * extent, smallVal);
		gl.glEnd();
		
		
		

		boolean drawGrid = true;
		if(drawGrid)
		{
			// -----------  Horizontal lines  ---------------
			// Drawing our grid lines
			// X distance from origin
			float x = (float)(this.columns / 2) * (intervalLength);
			// Z distance from origin
			float z = (float)(this.rows / 2) * (-1 * intervalLength);
			gl.glBegin(GL2.GL_LINES);
			float gridLineR = 1f;
			float gridLineG = (float)(105.0/255.0);
			float gridLineB = (float)(180.0/255.0);
			gl.glColor3d(gridLineR, gridLineG, gridLineB);
			for(int i = 0; i <= this.rows; i++)
			{
				// Only z gets updated for the horiz lines
				z = z + (float)(intervalLength * i);
				// Left coord of line
				gl.glVertex3f(x * gridLineExtent * -1, level, z);
				// Right coord of line
				gl.glVertex3f(x * gridLineExtent, level, z);
			}
			// -------------  Vertical lines  -----------------
			float xVert = (this.columns / 2) * (-1 * intervalLength);
			float zVert = (this.rows / 2) * (intervalLength);
			for(int k = 0; k <= this.columns; k++)
			{
				// Only x will get changed/updated
				xVert = xVert + (float)(intervalLength * k);
				// Top coord of line
				gl.glVertex3f(xVert, level, zVert * gridLineExtent);
				// Bottom coord of line
				gl.glVertex3f(xVert, level, -1 * zVert * gridLineExtent);
			}
			gl.glEnd();
		}


	}

	// -------------- Draws grid with constant  x values ----------------
	public void drawVerticalGrid()
	{

		float smallVal = 0.0001f;
		float level = (float)gridLevel;
		float extent = 5f;
		gl.glBegin(GL2.GL_QUADS);
		//gl.glColor3d((float)r, (float)g, (float)b);
		gl.glColor3d(1.0, 0.5, 0.6);

		gl.glVertex3f(level, extent * -1, 	extent);
		gl.glVertex3f(level, extent,		extent);
		gl.glVertex3f(level, extent * -1, 	extent * -1);
		gl.glVertex3f(level, extent, 		extent * -1);


		//		gl.glVertex4f(level, extent * -1, 	extent, 	 smallVal);
		//		gl.glVertex4f(level, extent,		extent, 	 smallVal);
		//		gl.glVertex4f(level, extent * -1, 	extent * -1, smallVal);
		//		gl.glVertex4f(level, extent, 		extent * -1, smallVal);
		gl.glEnd();

		// Cutting out code for debugging, vertical walls don't render, only
		// the grid lines do. 
		boolean draw1 = false;
		if(draw1)
		{
			// Drawing our grid surface
			// Y distance from origin
			float y = (float)(this.columns / 2) * (intervalLength);
			// Z distance from origin
			float z = (float)(this.rows / 2) * (-1 * intervalLength);
			gl.glBegin(GL2.GL_LINES);
			float gridLineR = 1f;
			float gridLineG = (float)(105.0/255.0);
			float gridLineB = (float)(180.0/255.0);
			gl.glColor3d(gridLineR, gridLineG, gridLineB);
			// -----------  Vertical lines  ---------------
			for(int i = 0; i <= this.rows; i++)
			{
				// Only z gets updated for the horiz lines
				z = z + (float)(intervalLength * i);
				// Left coord of line
				gl.glVertex3f(level, -1 * y, z);
				// Right coord of line
				gl.glVertex3f(level, y, z);
			}
			// -------------  Horizontal lines  -----------------
			float yVert = (this.columns / 2) * (-1 * intervalLength);
			float zVert = (this.rows / 2) * (intervalLength);
			for(int k = 0; k <= this.columns; k++)
			{

				yVert = yVert + (float)(intervalLength * k);
				// Top coord of line
				gl.glVertex3f(level, yVert, zVert);
				// Bottom coord of line
				gl.glVertex3f(level, yVert, -1 * zVert);
			}
			gl.glEnd();


		}

	}

	@Override
	public void setColor(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void setGridLevel(int gridLev)
	{
		this.gridLevel = gridLev;
	}

}
