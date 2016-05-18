import java.awt.Graphics;
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
	           System.out.println("ne najde slike");
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
	
	void narisi(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}
}
