

import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;


/*
 * Node for a SceneGraph tree.
 * Simplified, in that transformations are included with geometry.
 * Assumes rotation before translation.
 * Appearance and geometry are within draw method.
 */
public class TreeNode {
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
	private TreeNode sibling = null;
	private Vec3D translateVec = new Vec3D();
	private Vec3D rotateVec = new Vec3D();
	private double rotateAngle = 0;
	private Shape shape;

	private String name;

	public TreeNode(Shape shape)
	{
		this.shape = shape;
	}

	public Vec3D getTranslateVec() {
		return translateVec;
	}

	public void setTranslateVec(Vec3D translateVec) {
		this.translateVec = translateVec;
	}

	public Vec3D getRotateVec() {
		return rotateVec;
	}

	public void setRotateVec(Vec3D rotateVec) {
		this.rotateVec = rotateVec;
	}

	public void setRotateAngle(double angle)
	{
		rotateAngle = angle;
	}
	
	public double getRotateAngle()
	{
		return rotateAngle;
	}


	/*
	 * Pre-order traversal, drawing children last
	 */
	// Modify so that it also draws siblings!!!!!!!!!!!!!!!!!!!!!
	public void draw()
	{
		shape.draw();
	}
	public void draw(GL2 gl)
	{
		
		shape.draw();

	}

}
