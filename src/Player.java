// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Player class controls the movement of our player


import com.jogamp.opengl.GL2;
import com.jogamp.common.nio.Buffers; // Since we can't use the next line
import java.nio.FloatBuffer;

public class Player {
	private static final float _90=(float)Math.toRadians(90);
	private static final float _maxPitch=(float)Math.toRadians(70);
	private float heading=15.5f;	// Left/right looking amount
	private float pitch=0.5f;		// Up/down looking amount

	private float cosa, sina;	// Helps determine up/dow looking
	private float cosb, sinb;	// Helps determine direction of strafe movement
	private float cosz, sinz;	// Helps determine direction of forward/backward
								// movement.

	// Helps determine left/right leaning, which won't change
	private float cosc=1.0f;	
	private float sinc=0.0f;
	private float x, y, z;		//player position

	private float[] mat={ 1,0,0,0,
			0,1,0,0,
			0,0,1,0,
			0,0,0,1};
	private FloatBuffer matrix;
	
	// Constructor for our player
	public Player()
	{
		// Since we can't use the commented line after this
		matrix = Buffers.newDirectFloatBuffer(mat.length);
		//matrix=BufferUtil.newFloatBuffer(mat.length);
		matrix.put(mat);
		
		// Starting location for our player
		x = -250f;
		z = 100f;
		
		//there is no floor collider so we set a static y value
		y=-1.0f;
	}
	
	// Controlling the horizontal looking direction
	public void setHeading(float amount){
		heading -= amount;
		cosb = (float)Math.toRadians(Math.cos(heading));
		sinb = (float)Math.toRadians(Math.sin(heading));
		cosz = (float)Math.toRadians(Math.cos(heading + _90));
		sinz = (float)Math.toRadians(Math.sin(heading + _90));
	}

	// Controlling pitch. We don't let our player look all the 
	// way around (360).
	public void setPitch(float amount){
		pitch-=amount;
		if(pitch>_maxPitch)pitch=_maxPitch;
		if(pitch<-_maxPitch)pitch=-_maxPitch;
		cosa=(float)Math.cos(pitch);
		sina=(float)Math.sin(pitch);
	}
	
	// Updating our Player's position
	public void moveForward(float amount)
	{
		x+=cosz*amount;
		z+=sinz*amount;
	}
	public void moveBackward(float amount)
	{
		x-=cosz*amount;
		z-=sinz*amount;
	}
	
	public void strafeLeft(float amount)
	{
		x+=cosb*amount;
		z+=sinb*amount;
	}
	
	public void strafeRight(float amount)
	{
		x-=cosb*amount;
		z-=sinb*amount;
	}
	
	// Matrix for each loop iteration is set here
	public void set()
	{
		matrix.put(0, cosc*cosb-sinc*sina*sinb);
		matrix.put(1, sinc*cosb+cosc*sina*sinb);
		matrix.put(2, -cosa*sinb);
		matrix.put(4, -sinc*cosa);
		matrix.put(5, cosc*cosa);
		matrix.put(6, sina);
		matrix.put(8, cosc*sinb+sinc*sina*cosb);
		matrix.put(9, sinc*sinb-cosc*sina*cosb);
		matrix.put(10, cosa*cosb);
		matrix.put(12, matrix.get(0)*x+matrix.get(4)*y+matrix.get(8)*z);
		matrix.put(13, matrix.get(1)*x+matrix.get(5)*y+matrix.get(9)*z);
		matrix.put(14, matrix.get(2)*x+matrix.get(6)*y+matrix.get(10)*z);
	}
	
	// The player's gun is drawn before the matrix is loaded for the 
	// environment rendering
	public void draw(GL2 gl)
	{
		float gunLevel = 0.5f;
		gl.glLoadIdentity();
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(7.0f, 5.0f, 3.0f);
		// Quad for our gun
		gl.glVertex3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3f(2.0f, 1.0f, 0.0f);
		gl.glVertex3f(2.0f, 1.0f, -2.0f);
		gl.glVertex3f(1.0f, 1.0f, -2.0f);
		gl.glEnd();
		matrix.rewind();
		gl.glLoadMatrixf(matrix);
	}
}