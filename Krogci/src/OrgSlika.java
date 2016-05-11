import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class OrgSlika 
{
	private BufferedImage image;
	
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
