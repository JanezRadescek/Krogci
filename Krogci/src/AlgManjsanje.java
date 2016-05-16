import java.awt.Color;
import java.util.ArrayList;


public class AlgManjsanje 
{
	OrgSlika orgSlika;
	NovaSlika novaSlika;
	
	int trenutniR;
	int visina;
	int sirina;
	int odstopanjeRGB;
	int odstopanjePix;
	Color povprecje;
	int xsidro;
	int ysidro;
	boolean mreza [][];
	
	public AlgManjsanje(String path, int zacetniR, int odstopanjeRGB, int odstopanjePix) 
	{
		orgSlika = new OrgSlika(path);
		novaSlika = new NovaSlika();
		
		trenutniR = zacetniR;
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
		this.odstopanjeRGB = odstopanjeRGB;
		this.odstopanjePix = odstopanjePix;
		xsidro = 0;
		ysidro = 0;
		mreza = new boolean[visina][sirina];
		
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
		//tu zmanšaj radij in pojdi od zaèetka razen èe je r<10, pravtako izriši kroge, ki smo jih našl do sedaj
	}
	*/
	
	void povprecje(int x1, int y1)
	{
		
		ArrayList<Integer> tabelaBarvInt = new ArrayList<Integer>();
		int x2 = x1+2*trenutniR;
		int y2 = y1+2*trenutniR;
		int xsredisce = x1 + trenutniR;
		int ysredisce = y1 + trenutniR;
		long sumr = 0, sumg = 0, sumb = 0;
		int n = 0;
		
		for(int x=x1; x<=x2; x++)
		{
			for(int y=y1; y<=y2; y++)
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
		
		povprecje = new Color(sumr/n, sumg/n, sumb/n);
		
	}
	
	boolean ustreza(int x, int y)
	{
		Color c = new Color(orgSlika.image.getRGB(x, y));
		int red = Math.abs(c.getRed() - povprecje.getRed());
		int green = Math.abs(c.getGreen() - povprecje.getGreen());
		int blue = Math.abs(c.getBlue() - povprecje.getBlue());
		return (red + green + blue < odstopanjeRGB);
		
	}
	
	void poskusiNarisati()
	{
		int i = 0;
		for(int x = xsidro; x < xsidro + 2*trenutniR; x++)
		{
			for (int y = ysidro; y < ysidro + 2*trenutniR; y++)
			{
				if(! ustreza(x,y))
					{
						i++;
						if(i==odstopanjePix) return;
					}
				
			}
		}
		narisiKrog();
	}
	
	void narisiKrog()
	{
		int xsredisce = xsidro+trenutniR;
		int ysredisce = ysidro+trenutniR;
		novaSlika.slika.add(new Krogec(xsidro,ysidro,trenutniR,povprecje));
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
