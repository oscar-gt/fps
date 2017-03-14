// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Block creates a rectangular block


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.glu.GLU;

public class Block implements Shape
{
	// --------- Default Values --------
	private float armXFactor; 
	private float armYFactor; 
	private float armZFactor; 

	private float size = 0.2f;

	private GL2 gl;

	private String textureName;
	private boolean useTexture;

	private double r = 0.5, g = 0.5, b = 0.5;

	public Block(GL2 gl, float x, float y, float z)
	{
		this.gl = gl;
		armXFactor = size * x;
		armYFactor = size * y;
		armZFactor = size * z;
		textureName = null;
		useTexture = false;

	}

	public Block(GL2 gl, float x, float y, float z, String textName)
	{
		this.gl = gl;
		armXFactor = size * x;
		armYFactor = size * y;
		armZFactor = size * z;
		textureName = textName;
		useTexture = true;

	}


	@Override
	public void draw() 
	{
		// Commenting next line and tring GLU glu = new GLU()
		//GLU glu = GLU.createGLU(gl);
		//GLU glu = new GLU();
		//		gl.glColor3d(r, g, b);

		// --------------------- Lighting -------------------
		boolean lighting = false;
		if(lighting)
		{
			gl.glEnable(GL2.GL_COLOR_MATERIAL);
			gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT);
			gl.glColor3d(0.2, 0.2, 0.2 );
			gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE);
			gl.glColor3d(0.1, 0.1, 0.1 );
//			gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR);
//			gl.glColor3d(0.3, 0.3, 0.3);

			gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 20f);
		}


		// ----------- End of lighting -----------------------


		// Drawing cube 


		//giving different colors to different sides
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
		gl.glColor3f((float)r, (float)g, (float)b);
		//gl.glColor3f(1f,0f,0f); //red color
		// ------------------ Front of arm --------------------
		// x, y, z
		gl.glVertex3f(armXFactor, armYFactor, -armZFactor); // Top Right Of The Quad (Top)
		gl.glVertex3f( -armXFactor,armYFactor, -armZFactor); // Top Left Of The Quad (Top)
		gl.glVertex3f( -armXFactor, armYFactor, armZFactor ); // Bottom Left Of The Quad (Top)
		gl.glVertex3f( armXFactor, armYFactor, armZFactor ); // Bottom Right Of The Quad (Top)

		// ------------- Back of arm ---------------------------

		// --------------------- Lighting -------------------
		if(lighting)
		{
			gl.glEnable(GL2.GL_COLOR_MATERIAL);
			gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT);
			gl.glColor3d(0.7, 0.7, 0.7 );
			gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE);
			gl.glColor3d(0.7, 0.7, 0.7 );
			gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR);
			gl.glColor3d(1, 1, 1);

			gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 20f);
		}


		// ----------- End of lighting -----------------------
		//gl.glColor3f(1f,1f,1f); //green color
		gl.glColor3f((float)r, (float)g, (float)b);
		gl.glVertex3f( armXFactor, -armYFactor, armZFactor ); // Top Right Of The Quad (Bottom of cube)
		gl.glVertex3f( -armXFactor, -armYFactor, armZFactor ); // Top Left Of The Quad
		gl.glVertex3f( -armXFactor, -armYFactor, -armZFactor ); // Bottom Left Of The Quad
		gl.glVertex3f( armXFactor, -armYFactor, -armZFactor ); // Bottom Right Of The Quad 

		//gl.glColor3f(0f,0f,1f); //blue color
		gl.glColor3f((float)r, (float)g, (float)b);
		gl.glVertex3f( armXFactor, armYFactor, armZFactor ); // Top Right Of The Quad (Front)
		gl.glVertex3f( -armXFactor, armYFactor, armZFactor ); // Top Left Of The Quad (Front)
		gl.glVertex3f( -armXFactor, -armYFactor, armZFactor ); // Bottom Left Of The Quad
		gl.glVertex3f( armXFactor, -armYFactor, armZFactor ); // Bottom Right Of The Quad 

		//gl.glColor3f(0f,0f,1f); //yellow (red + green)
		gl.glColor3f((float)r, (float)g, (float)b);
		gl.glVertex3f( armXFactor, -armYFactor, -armZFactor ); // Bottom Left Of The Quad
		gl.glVertex3f( -armXFactor, -armYFactor, -armZFactor ); // Bottom Right Of The Quad
		gl.glVertex3f( -armXFactor, armYFactor, -armZFactor ); // Top Right Of The Quad (Back)
		gl.glVertex3f( armXFactor, armYFactor, -armZFactor ); // Top Left Of The Quad (Back)

		//gl.glColor3f(1f,1f,1f); //purple (red + green)
		gl.glColor3f((float)r, (float)g, (float)b);
		gl.glVertex3f( -armXFactor, armYFactor, armZFactor ); // Top Right Of The Quad (Left)
		gl.glVertex3f( -armXFactor, armYFactor, -armZFactor ); // Top Left Of The Quad (Left)
		gl.glVertex3f( -armXFactor, -armYFactor, -armZFactor ); // Bottom Left Of The Quad
		gl.glVertex3f( -armXFactor, -armYFactor, armZFactor ); // Bottom Right Of The Quad 

		//gl.glColor3f(1f,1f,1f); //sky blue (blue +green)
		gl.glColor3f((float)r, (float)g, (float)b);
		gl.glVertex3f( armXFactor, armYFactor, -armZFactor ); // Top Right Of The Quad (Right)
		gl.glVertex3f( armXFactor, armYFactor, armZFactor ); // Top Left Of The Quad
		gl.glVertex3f( armXFactor, -armYFactor, armZFactor ); // Bottom Left Of The Quad
		gl.glVertex3f( armXFactor, -armYFactor, -armZFactor ); // Bottom Right Of The Quad

		gl.glEnd(); // Done Drawing The Quad
		
		// Drawing lines along the edges of our block
		drawBlockEdges();

		

	}
	
	public void drawBlockEdges()
	{
		gl.glBegin(GL2.GL_LINES);
		float gridLineR = 0f;
		float gridLineG = 1f;
		float gridLineB = 1f;
		gl.glColor3d(gridLineR, gridLineG, gridLineB);
		
		// Will only need to draw 12 lines: vertical edges and top quad edges
		// of block
		
		// 4 vertical edges
		gl.glVertex3f(armXFactor * -1, armYFactor * -1, armZFactor); // Bottom
		gl.glVertex3f(armXFactor * -1, armYFactor, armZFactor); // Top
		
		gl.glVertex3f(armXFactor, armYFactor * -1, armZFactor); // Bottom
		gl.glVertex3f(armXFactor, armYFactor, armZFactor); // Top
		
		gl.glVertex3f(armXFactor, armYFactor * -1, armZFactor * -1); // Bottom
		gl.glVertex3f(armXFactor, armYFactor, armZFactor * -1); // Top
		
		gl.glVertex3f(armXFactor * -1, armYFactor * -1, armZFactor * -1); // Bottom
		gl.glVertex3f(armXFactor * -1, armYFactor, armZFactor * -1); // Top
		
		// Edges of the quad top (roof)
		
		
		
		
		
		
		gl.glEnd();
	}

	@Override
	public void setColor(double r, double g, double b) 
	{
		this.r = r;
		this.g = g;
		this.b = b;

	}
	
	public float getXFactor()
	{
		return armXFactor;
	}
	
	public float getYFactor()
	{
		return armYFactor;
	}
	
	public float getZFactor()
	{
		return armZFactor;
	}

}
