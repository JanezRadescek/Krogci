import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class OrgSlika 
{
	protected BufferedImage image;
	
	public OrgSlika(String path) 
	{
		try 
	    {                
	       image = ImageIO.read(new File(path));
	    } 
		catch (IOException ex) 
		{
	           System.out.print("ne najde slike");
	    }
	}
	
	void novaSlika(String path)
	{
		try 
	    {                
	       image = ImageIO.read(new File(path));
	    } 
		catch (IOException ex) 
		{
	           System.out.print("ne najde slike");
	    }
	}
}
