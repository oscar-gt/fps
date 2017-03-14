// Oscar Garcia-Telles
// CSS 451 Final Project
// 14 March 2017

// Static class that returns a Texture

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.print.DocFlavor.URL;

import org.omg.CORBA.portable.InputStream;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureGenerator {

	public static Texture createTexture(String fileName)
	{
		
		// To read our image 
		FileInputStream stream = null;
		// to create texture
		TextureData data = null;
		// Resultant texture
		Texture result = null;
		// Getting the extension of our texture image file
		String suffix = getSuffix(fileName);
		// Loading the image to use for our texture
		
		try 
		{
			stream = new FileInputStream(new File(fileName));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		// Creating texture data
		try 
		{
			data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, null);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		result = TextureIO.newTexture(data);
		return result;
	}

	// Checks the extension of the fileName.
	// For our application, should be an image 
	// extension: jpg, png, gif, tiff, or bmp
	private static String getSuffix(String fileName)
	{
		// Substring that will be the extension
		String sub = "";
		int length = fileName.length();
		if(length < 5)
		{
			return "";
		}
		sub = fileName.substring(length - 3);

		return sub;
	}

}
