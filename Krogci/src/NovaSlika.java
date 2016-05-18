import java.awt.Graphics;
import java.util.ArrayList;


public class NovaSlika 
{
	ArrayList<Krogec> slika = new ArrayList<Krogec>();
	
	void narisi(Graphics g)
	{
		for (Krogec k: slika)
		{
			g.fillOval(k.x, k.y, k.x + 2*k.r, k.y + 2*k.r);
		}
	}
}
