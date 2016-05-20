import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;


public class AlgManjsanje 
{
	public OrgSlika orgSlika;
	public NovaSlika novaSlika;
	
	private int maxR;
	private int trenutniR;
	private int minR;
	private int visina;
	private int sirina;
	private int odstopanjeRGB; //koliko se lahko pixel razlikuje da je še vredu
	private int odstopanjePix; //procenti dovoljenega  števila napaènih pixlov
	private Color povprecnaBarva;
	private int xsidro;
	private int ysidro;
	private boolean mreza [][];
	private boolean konec;
	private int xkandidat;
	private int ymin;
	private ArrayList<Integer> yNasledniMin;
	private boolean konecSlike;
	private boolean smoNarisali;
	
	
	public AlgManjsanje(OrgSlika orgSlika, int maxR, int odstopanjeRGB, int odstopanjePix) 
	{
		this.orgSlika = orgSlika;
		novaSlika = new NovaSlika();
		
		
		trenutniR = maxR;
		minR = 5;
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
		this.maxR = Math.min(maxR, Math.min(visina, sirina)/8);
		this.odstopanjeRGB = odstopanjeRGB;
		this.odstopanjePix = odstopanjePix;
		xsidro = 0;
		ysidro = 0;
		mreza = new boolean[sirina][visina];
		konec = false;
		xkandidat = 0;
		ymin = 0;
		yNasledniMin = new ArrayList<Integer>();
		konecSlike = false;
		smoNarisali = false;
		
		glavna();
		
	}
	
	private void glavna()
	{
		while(! konec)
		{
			novoSidro();
			if (konecSlike)
			{
				if (smoNarisali)
				{
					konecSlike = false;
					xkandidat = 0;
				}
				else 
				{
					konec = true;
					System.out.println("konec");
				}
				
			}
			else zanka(); //riše krog kakršnega bo paè lahko narisala
		}
	}
	
	private void zanka()
	{
		for (;trenutniR > minR; trenutniR -= 5)
		{
			try
			{
				povprecje();
			}
			catch(java.lang.ArrayIndexOutOfBoundsException a)
			{
				
			}
			
			if (poskusiNarediti())
			{
				narediKrog();
				return;
			}
		}
		
		trenutniR = minR;
		
		povprecje();
		narediKrog();
		
	}
	
	private void novoSidro()
	{
		while(xkandidat < sirina - minR)
		{
			for (int y = ymin; y<visina ; y++)
			{
				if (! mreza[xkandidat][y])
				{
					//èe najdemo lepga bo vredu èe je pa tak da se bo na desni zaletu bo pa krogec mobu premaknt kandidata
					if (poskusiMin(y)) 
					{
						xsidro = xkandidat;
						ysidro = y;
						
						smoNarisali = true;
						return;
					}
					  
				}
					
			}
			xkandidat += minR;
		}
		
		konecSlike = true;		
		
	}
	
	private void povprecje()
	{
		
		int xsredisce = xsidro + trenutniR;
		int ysredisce = ysidro + trenutniR;
		long sumr = 0, sumg = 0, sumb = 0;
		int n = 0;
		
		for(int x=xsidro; x<=xsidro+2*trenutniR; x++)
		{
			for(int y=ysidro; y<=ysidro+2*trenutniR; y++)
			{
				if ( Metrika.razdalja(x, y, xsredisce, ysredisce) <= trenutniR )
				{
					Color c = new Color(orgSlika.image.getRGB(x, y));
					sumr += c.getRed();
		            sumg += c.getGreen();
		            sumb += c.getBlue();
		            n += 1;
					
				}
			}
		}
		
		povprecnaBarva = new Color(sumr/n/255, sumg/n/255, sumb/n/255);
		
	}
	
	private boolean ustreza(int x, int y)
	{
		if ((x>=sirina) || (y>=visina)) return false;
		
		Color c = new Color(orgSlika.image.getRGB(x, y));
		int red = Math.abs(c.getRed() - povprecnaBarva.getRed());
		int green = Math.abs(c.getGreen() - povprecnaBarva.getGreen());
		int blue = Math.abs(c.getBlue() - povprecnaBarva.getBlue());
		return (red + green + blue < (3*255*odstopanjeRGB/100));
		
	}
	
	private boolean poskusiMin(int yt)
	{
		
		for (int x = xkandidat; x <= xkandidat + 2*minR; x++)
		{
			for (int y = yt; y< yt + 2*minR; y++)
			{
				if ((x>sirina-1)  || (y>visina-1))
				{
					return false;
				}
				if ((mreza[x][y]) && (Metrika.razdalja(x, y, xkandidat+minR, yt+minR) <= minR) )
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean poskusiNarediti()
	{
		int i = 0;
		int odstopanjeStevilo = (int)((odstopanjePix/100) 
				* (3.14/4) * trenutniR * trenutniR);
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
	
	private void narediKrog()
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
					if ((x<sirina) && (y<visina)) mreza[x][y] = true;
					
				}
			}
		}
		
		yNasledniMin.add(ysidro + 2*trenutniR);
		trenutniR = maxR;
		xkandidat += 2*trenutniR;
		if (xkandidat >= sirina - minR)
		{
			xkandidat = 0;
			ymin = Collections.min(yNasledniMin);
		}
	}
	
}
