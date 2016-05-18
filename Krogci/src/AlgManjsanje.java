import java.awt.Color;
import java.util.ArrayList;


public class AlgManjsanje 
{
	OrgSlika orgSlika;
	NovaSlika novaSlika;
	
	int trenutniR;
	int minR;
	int visina;
	int sirina;
	int odstopanjeRGB; //koliko se lahko pixel razlikuje da je še vredu
	int odstopanjeProcent; //procenti dovoljenega  števila napaènih pixlov
	Color povprecnaBarva;
	int xsidro;
	int ysidro;
	boolean mreza [][];
	boolean konec;
	
	public AlgManjsanje(OrgSlika orgSlika, int zacetniR, int odstopanjeRGB, int odstopanjePix) 
	{
		this.orgSlika = orgSlika;
		novaSlika = new NovaSlika();
		
		trenutniR = zacetniR;
		minR = 5;
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
		this.odstopanjeRGB = odstopanjeRGB;
		this.odstopanjeProcent = odstopanjePix;
		xsidro = 0;
		ysidro = 0;
		mreza = new boolean[visina][sirina];
		konec = false;
		
	}
	
	void glavna()
	{
		//novoSidro();
		zanka();
		
		
	}
	
	
	void zanka()
	{
		for (;trenutniR > minR; trenutniR -= 5)
		{
			povprecje();
			if (poskusiNarediti())
			{
				narediKrog();
				return;
			}
		}
		
	}
	
	
	
	/*
	void novoSidro()
	{
		for (int i = 0; i<visina; i++)
		{
			for(int j=0; j<sirina; j++)
			{
				if (mreza[i][j] == true) continue;
				else
				{
					
				}
			}
				
		}
		
		konec = true;
	}
	*/
	
	void povprecje()
	{
		
		int x2 = xsidro+2*trenutniR;
		int y2 = ysidro+2*trenutniR;
		int xsredisce = xsidro + trenutniR;
		int ysredisce = ysidro + trenutniR;
		long sumr = 0, sumg = 0, sumb = 0;
		int n = 0;
		
		for(int x=xsidro; x<=x2; x++)
		{
			for(int y=ysidro; y<=y2; y++)
			{
				if (2 * Metrika.razdalja(x, y, xsredisce, ysredisce) <= 2*trenutniR )
				{
					Color c = new Color(orgSlika.image.getRGB(x, y));
					sumr += c.getRed();
		            sumg += c.getGreen();
		            sumb += c.getBlue();
		            n += 1;
					
				}
			}
		}
		
		povprecnaBarva = new Color(sumr/n, sumg/n, sumb/n);
		
	}
	
	boolean ustreza(int x, int y)
	{
		Color c = new Color(orgSlika.image.getRGB(x, y));
		int red = Math.abs(c.getRed() - povprecnaBarva.getRed());
		int green = Math.abs(c.getGreen() - povprecnaBarva.getGreen());
		int blue = Math.abs(c.getBlue() - povprecnaBarva.getBlue());
		return (red + green + blue < odstopanjeRGB);
		
	}
	
	boolean poskusiNarediti()
	{
		int i = 0;
		int odstopanjeStevilo = (int)((odstopanjeProcent/100) 
				* (4/3.14) * trenutniR);
		for(int x = xsidro; x < xsidro + 2*trenutniR; x++)
		{
			for (int y = ysidro; y < ysidro + 2*trenutniR; y++)
			{
				if(! ustreza(x,y))
					{
						i++;
						if(i==odstopanjeStevilo) return false;
					}
				
			}
		}
		return true;
	}
	
	void narediKrog()
	{
		int xsredisce = xsidro+trenutniR;
		int ysredisce = ysidro+trenutniR;
		novaSlika.slika.add(new Krogec(xsidro,ysidro,trenutniR,povprecnaBarva));
		for (int x=xsidro; x < xsidro + 2*trenutniR; x++)
		{
			for(int y=ysidro; y < ysidro + 2*trenutniR; y++)
			{
				if(Metrika.razdalja(x, y, xsredisce, ysredisce) <= trenutniR)
				{
					mreza[x][y] = true;
				}
			}
		}
	}
	
}
