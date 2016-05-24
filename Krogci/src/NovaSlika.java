import java.awt.Graphics;
import java.util.ArrayList;


public class NovaSlika 
{
	ArrayList<Krogec> slika = new ArrayList<Krogec>();
	
	void narisi(Graphics g)
	{
		for (Krogec k: slika)
		{
			g.setColor(k.barva);
			g.fillOval(k.x, k.y, 2*k.r, 2*k.r);
		}
	}
}
