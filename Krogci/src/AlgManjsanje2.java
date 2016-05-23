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
	private final int odstopanjeRGB; //koliko se lahko pixel razlikuje da je �e vredu
	private final int odstopanjePix; //procenti dovoljenega  �tevila napa�nih pixlov
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
				
		glavna();
		
		System.out.println("za glavno");
	}

	//i��e nove krogce in jih nari�e dokler ne pride do konca
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

	// poi��e x,y kjer bomo lahko zagotovo naredili vsaj min krog
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
					
					return;
				}
			}
			
			xkandidat += (int)(minR/2);
		}
		
		konecVrstice();
		
	}
	
	//ko pridemo do konca vrstice je lahko konec ali pa so ka�ni predeli ostali
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
	//preveri �e je prostora vsaj za min krog
	private boolean lahkoMin() {
		// TODO Auto-generated method stub
		return false;
	}

	//sidro je znano
	//zmanj�uje radij dokler ne najde pravega, �e ni drugega min vseeno nari�e.
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
	// �e �e trenutni krog zadostuje barvnim pogojem
	private boolean barvniPogoji() 
	{
		izracunajPovprecnoBarvo();
		
		for(int x = xsidro; x<= xsidro + 2*trenutniR; x++)
		{
			for(int y = ysidro; y<= ysidro + 2*trenutniR; y++)
			{
				
			}
				
		}
		
		return false;
	}

	//sidro je znano
	//preveri �e trenutni krog ne le�i na kak�nem �e narejenm krogu
	private boolean niObdelano() {
		// TODO Auto-generated method stub
		return false;
	}

	//sidro je znano
	//povpre�na barva trenutnega kroga
	private void izracunajPovprecnoBarvo() 
	{
		int red = 0, green = 0, blue = 0;
		int n = 0;
		for(int x = xsidro; x <= xsidro + 2*trenutniR; x++)
		{
			for(int y = ysidro; y<= ysidro + 2*trenutniR; y++)
			{
				if (Metrika.razdalja(x, y, x+trenutniR, y+trenutniR) <= trenutniR)
				{
					long rgb = orgSlika.image.getRGB(x, y);
					red += (rgb >> 16) & 0x000000FF;
					green += (rgb >>8 ) & 0x000000FF;
					blue += (rgb) & 0x000000FF;
					n++;
				}
			}
		}
		int povRed = (int)(red/n), povGreen = (int)(green/n), povBlue = (int)(blue/n);
		povprecnaBarva = new Color(povRed, povGreen, povBlue);
		
	}
}
