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
	private final int odstopanjePix; //procenti dovoljenega  števila napaènih pixlov
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

	private boolean lahkoMin() {
		// TODO Auto-generated method stub
		return false;
	}

	private void manjsajKrog() 
	{
		while(trenutniR >= minR)
		{
			if(niObdelano())
			{
				izracunajPovprecnoBarvo(ysidro);
				if(barvnoUjemanje())
				{
					narediKrog();
					return;
				}
			}
			trenutniR -= minR;
		}
		
		izracunajPovprecnoBarvo(ysidro);
		narediKrog();
		
	}

	private void narediKrog() 
	{
		novaSlika.slika.add(new Krogec(xsidro, ysidro, trenutniR, povprecnaBarva));
	}

	private boolean barvnoUjemanje() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean niObdelano() {
		// TODO Auto-generated method stub
		return false;
	}

	private void izracunajPovprecnoBarvo(int ysid) 
	{
		int red = 0, green = 0, blue = 0;
		int n = 0;
		for(int x = xkandidat; x <= xkandidat + 2*trenutniR; x++)
		{
			for(int y = ysid; y<= ysid + 2*trenutniR; y++)
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
