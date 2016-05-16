import java.awt.Color;
import java.util.ArrayList;


public class AlgManjsanje 
{
	OrgSlika orgSlika;
	NovaSlika novaSlika;
	
	int zacetniR;
	int visina;
	int sirina;
	int odstopanje;
	Color povprecje;
	int xsidro;
	int ysidro;
	
	public AlgManjsanje(String path, int R) 
	{
		orgSlika = new OrgSlika(path);
		novaSlika = new NovaSlika();
		zacetniR = R;
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
	}
	
	
	
	void povprecje(int x1, int y1, int a)
	{
		
		ArrayList<Integer> tabelaBarvInt = new ArrayList<Integer>();
		int x2 = x1+a;
		int y2 = y1+a;
		int xsredisce = (int)((x1+x2)/2);
		int ysredisce = (int)((y1+y2)/2);
		long sumr = 0, sumg = 0, sumb = 0;
		int n = 0;
		
		for(int x=x1; x<=x2; x++)
		{
			for(int y=y1; y<=y2; y++)
			{
				if (2 * Metrika.razdalja(x, y, xsredisce, ysredisce) <= a )
				{
					Color c = new Color(orgSlika.image.getRGB(x, y));
					sumr += c.getRed();
		            sumg += c.getGreen();
		            sumb += c.getBlue();
		            n += 1;
					
				}
			}
		}
		
		povprecje = new Color(sumr/n, sumg/n, sumb/n);
		
	}
	
	boolean ustreza(int x, int y)
	{
		Color c = new Color(orgSlika.image.getRGB(x, y));
		int red = Math.abs(c.getRed() - povprecje.getRed());
		int green = Math.abs(c.getGreen() - povprecje.getGreen());
		int blue = Math.abs(c.getBlue() - povprecje.getBlue());
		return (red + green + blue < odstopanje);
		
	}
	
	
	
}
