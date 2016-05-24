import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class AlgManjsanje2 
{
	public OrgSlika orgSlika;
	public NovaSlika novaSlika;
	
	private final int maxR;
	private int trenutniR;
	private final int minR;
	private final int visina;
	private final int sirina;
	private final int odstopanjeRGB; //koliko se lahko pixel razlikuje da je še vredu
	private final int odstopanjePix; //procenti dovoljenega  števila napačnih pixlov
	private Color povprecnaBarva;
	private int xsidro;
	private int ysidro;
	private boolean mreza [][];
	private boolean konec;
	private int xkandidat;
	private int ymin;
	private ArrayList<Integer> yZeObdelani;
	private boolean konecVrstice;
	private boolean smoNarisali;
	
	public AlgManjsanje2(OrgSlika orgSlika, int maxR, int odstopanjeRGB, int odstopanjePix) 
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
		yZeObdelani = new ArrayList<Integer>();
		konecVrstice = false;
		smoNarisali = false;
		
		System.out.println("pred glavno");
				
		glavna();
		
		System.out.println("za glavno");
	}

	//išče nove krogce in jih nariše dokler ne pride do konca
	private void glavna() 
	{
		while(! konec)
		{
			novoSidro();
			if(smoNarisali)
			{
				konec = true;
			}
			else
			{
				manjsajKrog();
			}
		}
	}

	// poišče x,y kjer bomo lahko zagotovo naredili vsaj min krog
	private void novoSidro() 
	{
		while(xkandidat < sirina - minR)
		{
			for(int y = ymin; y < visina; y+= (int)(minR/2))
			{
				if(lahkoMin())
				{
					xsidro = xkandidat;
					ysidro = y;
					
					smoNarisali = true;
					System.out.println("najdeno sidro" + xsidro + " : " + ysidro);
					
					return;
				}
			}
			
			xkandidat += (int)(minR/2);
		}
		
		konecVrstice();
		
	}
	
	//ko pridemo do konca vrstice je lahko konec ali pa so kašni predeli ostali
	private void konecVrstice() 
	{
		if(smoNarisali)
		{
			smoNarisali = false;
			ymin = Collections.min(yZeObdelani); 
			xkandidat = 0;
			novoSidro();
		}
		else
		{
			konec = true;
		}
		
	}

	//sidro ni znano
	//preveri če je prostora vsaj za min krog
	private boolean lahkoMin() {
		int treR = trenutniR;
		trenutniR = minR;
		boolean r = niObdelano();
		trenutniR = treR;
		return r;
	}

	//sidro je znano
	//zmanjšuje radij dokler ne najde pravega, če ni drugega min vseeno nariše.
	private void manjsajKrog() 
	{
		while(trenutniR >= minR)
		{
			if(niObdelano())
			{
				if(barvniPogoji())
				{
					narediKrog();
					return;
				}
			}
			trenutniR -= minR;
		}
		
		izracunajPovprecnoBarvo();
		narediKrog();
		
	}

	//sidro je znano
	//naredi Krog :D
	private void narediKrog() 
	{
		novaSlika.slika.add(new Krogec(xsidro, ysidro, trenutniR, povprecnaBarva));
	}

	//sidro je znano
	// če če trenutni krog zadostuje barvnim pogojem
	/*
	
	private boolean barvniPogoji() 
	{
		izracunajPovprecnoBarvo();
		int maxPix = (int)((3.14/4)*trenutniR*trenutniR*odstopanjePix/100);
		int n = 0;
		int red = 0, green = 0, blue = 0, alpha = 0;
				
		for(int x = xsidro; x<= xsidro + 2*trenutniR; x++)
		{
			for(int y = ysidro; y<= ysidro + 2*trenutniR; y++)
			{
				if(Metrika.razdalja(x, y, x+trenutniR, y+trenutniR) <= trenutniR)
				{
					long rgb = orgSlika.image.getRGB(x, y);
					alpha = (int)((rgb >> 24) & 0x000000FF);
					red = (int)((rgb >> 16) & 0x000000FF);
					green = (int)((rgb >>8 ) & 0x000000FF);
					blue = (int)((rgb) & 0x000000FF);
					
					
				}
			}
				
		}
		
		return false;
	}

	*/
	
	//vidova
	//sidro znano
	private boolean barvniPogoji()
	{
		int odstop = 0;
		int stevec = 0;
		int stevecOdstopanj = 0;
		
		for (int i = xsidro; i < xsidro + trenutniR*2; i++)
		{
			for(int j = ysidro; j < ysidro + trenutniR*2; j++)
			{
				if ((i - (xsidro + trenutniR))*(i - (xsidro + trenutniR)) + (j - (ysidro + trenutniR))*(j - (ysidro + trenutniR)) <= trenutniR*trenutniR)
				{
					int colour = orgSlika.image.getRGB(i, j);
					
					int alpha = (colour>>24) & 0xff;
					int  red = (colour & 0x00ff0000) >> 16;
					int  green = (colour & 0x0000ff00) >> 8;
					int  blue = colour & 0x000000ff;
					
					int pR = povprecnaBarva.getRed();
					int pM = povprecnaBarva.getBlue();
					int pZ = povprecnaBarva.getGreen();
					int pA = povprecnaBarva.getAlpha();
					
					odstop += Math.abs(red - pR);
					odstop += Math.abs(green - pZ);
					odstop += Math.abs(blue - pM);
					odstop += Math.abs(alpha - pA);
					
					
					if (odstop > 20)
					{
						stevecOdstopanj += 1;
					}
					odstop = 0;
					stevec += 1;
					
				}
			}
		}
		//System.out.println(stevec);
		//System.out.println(stevecOdstopanj);
		double procentOdstopa = (stevecOdstopanj / (double) stevec);
		//System.out.println(procentOdstopa);
		
		if (procentOdstopa < 0.3)
		{
			return true;
		}
		return false;
	}
	
	
	//sidro je znano
	//preveri če trenutni krog ne leži na kakšnem že narejenm krogu
	private boolean niObdelano() {
		
		for(int x = xsidro; x<= xsidro + 2*trenutniR; x++)
		{
			for(int y = ysidro; x<=ysidro + 2*trenutniR; y++)
			{
				if(Metrika.razdalja(x, y, xsidro+trenutniR, ysidro+trenutniR) <= trenutniR)
				{
					if((x>=sirina) || (y>= visina))
					{
						return false;
					}
					if(mreza[x][y]) 
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}

	//sidro je znano
	//povprečna barva trenutnega kroga
	private void izracunajPovprecnoBarvo() 
	{
		int red = 0, green = 0, blue = 0, alpha = 0;
		int n = 0;
		for(int x = xsidro; x <= xsidro + 2*trenutniR; x++)
		{
			for(int y = ysidro; y<= ysidro + 2*trenutniR; y++)
			{
				if (Metrika.razdalja(x, y, x+trenutniR, y+trenutniR) <= trenutniR)
				{
					long rgb = orgSlika.image.getRGB(x, y);
					alpha += (rgb >> 24) & 0x000000FF;
					red += (rgb >> 16) & 0x000000FF;
					green += (rgb >>8 ) & 0x000000FF;
					blue += (rgb) & 0x000000FF;
					n++;
				}
			}
		}
		int povRed = (int)(red/n), povGreen = (int)(green/n), povBlue = (int)(blue/n), povAlpha = (int)(alpha/n);
		povprecnaBarva = new Color(povRed, povGreen, povBlue, povAlpha);
		
	}

}
