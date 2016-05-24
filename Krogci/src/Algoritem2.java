import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Algoritem2 {
	
	public OrgSlika orgSlika;
	public NovaSlika novaSlika;
	public int visina;
	public int sirina;
	
	private int trenutniR;
	private int minimalniR;
	private int sidrox;
	private int sidroy;
	
	private Random rn;
	
	public Color rdeca;
	public Color modra;
	public Color povprecnaB;
	
	private boolean mreza [][];
		
	public Algoritem2(OrgSlika orgSlika, int maxR, int odstopanjeRGB, int odstopanjePix) 
	{
		this.orgSlika = orgSlika;
		novaSlika = new NovaSlika();
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
		
		trenutniR = 70;
		minimalniR = 2;
		
		rdeca = new Color(255, 0, 0);
		modra = new Color(0,255,0);
		
		mreza = new boolean[sirina][visina]; //v mrezo vpisemo vse vrednosti true, kar pomeni, da imamo prosto mesto za risanje krogca
		for(int i = 0; i < sirina; i++)
		{
			for(int j = 0; j < visina; j++)
			{
				mreza[i][j] = true;
			}
			
		}
		
		glavna();
	}
	
	public void glavna()
	{
		for(;trenutniR >= minimalniR; trenutniR -= 4)
		{
			for(int i = 0; i < (51 - trenutniR)*1000; i++)
			{
				novoSidro();
				if (preveriSidro())
				{
					povprecnaBarva();
					if (preveriOdstopanje())
					{
						DodajKrog();
					}
				}
				
			}
		}
	}

	private void novoSidro() {
		rn = new Random();
		sidrox = rn.nextInt(sirina - trenutniR*2 - 2) + 1;
		sidroy = rn.nextInt(visina - trenutniR*2 - 2) + 1;
		
	}

	private void DodajKrog() {
		
		novaSlika.slika.add(new Krogec(sidrox,sidroy,trenutniR,povprecnaB)); //doda krog v seznam krogov, ki se bodo kasneje narisali
		
		//v mrezo vpisemo katera polja so ze zasedena
		for (int i = sidrox; i < sidrox + trenutniR*2; i++)
		{
			for(int j = sidroy; j < sidroy + trenutniR*2; j++)
			{
				if ((i - (sidrox + trenutniR))*(i - (sidrox + trenutniR)) + (j - (sidroy + trenutniR))*(j - (sidroy + trenutniR)) <= trenutniR*trenutniR)
				{
					mreza[i][j] = false;
				}
			}
		}
			
	}
	
	
	
	public boolean preveriSidro()
	{
		for (int i = sidrox; i < sidrox + trenutniR*2; i++)
		{
			for(int j = sidroy; j < sidroy + trenutniR*2; j++)
			{
				if ((i - (sidrox + trenutniR))*(i - (sidrox + trenutniR)) + (j - (sidroy + trenutniR))*(j - (sidroy + trenutniR)) <= trenutniR*trenutniR)
				{
					if(mreza[i][j] == false)
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void povprecnaBarva()
	{
		int povpR = 0;
		int povpZ = 0;
		int povpM = 0;
		int povpA = 0;
		int stevec = 0;
		
		for (int i = sidrox; i < sidrox + trenutniR*2; i++)
		{
			for(int j = sidroy; j < sidroy + trenutniR*2; j++)
			{
				if ((i - (sidrox + trenutniR))*(i - (sidrox + trenutniR)) + (j - (sidroy + trenutniR))*(j - (sidroy + trenutniR)) <= trenutniR*trenutniR)
				{
					int colour = orgSlika.image.getRGB(i, j);
					
					int alpha = (colour>>24) & 0xff;
					int  red = (colour & 0x00ff0000) >> 16;
					int  green = (colour & 0x0000ff00) >> 8;
					int  blue = colour & 0x000000ff;
					
					povpR += red;
					povpZ += green;
					povpM += blue;
					povpA += alpha;
					stevec += 1;
					
				}
			}
		}
		Color barva = new Color(povpR/stevec, povpZ/stevec, povpM/stevec, povpA/stevec);
		povprecnaB = barva;
	}
	
	private boolean preveriOdstopanje()
	{
		int odstop = 0;
		int stevec = 0;
		int stevecOdstopanj = 0;
		
		for (int i = sidrox; i < sidrox + trenutniR*2; i++)
		{
			for(int j = sidroy; j < sidroy + trenutniR*2; j++)
			{
				if ((i - (sidrox + trenutniR))*(i - (sidrox + trenutniR)) + (j - (sidroy + trenutniR))*(j - (sidroy + trenutniR)) <= trenutniR*trenutniR)
				{
					int colour = orgSlika.image.getRGB(i, j);
					
					int alpha = (colour>>24) & 0xff;
					int  red = (colour & 0x00ff0000) >> 16;
					int  green = (colour & 0x0000ff00) >> 8;
					int  blue = colour & 0x000000ff;
					
					int pR = povprecnaB.getRed();
					int pM = povprecnaB.getBlue();
					int pZ = povprecnaB.getGreen();
					
					odstop += Math.abs((red - povprecnaB.getRed()));
					odstop += Math.abs(green - povprecnaB.getGreen());
					odstop += Math.abs(blue - povprecnaB.getBlue());
					
					
					if (odstop > 10)
					{
						stevecOdstopanj += 1;
					}
					odstop = 0;
					stevec += 1;
					
				}
			}
		}
		System.out.println(stevec);
		System.out.println(stevecOdstopanj);
		double procentOdstopa = (stevecOdstopanj / (double) stevec);
		System.out.println(procentOdstopa);
		
		if (procentOdstopa < 0.05)
		{
			return true;
		}
		return false;
	}
	
	public void narisi(Graphics g)
	{
		for (Krogec k: novaSlika.slika)
		{
			g.setColor(k.barva);
			g.fillOval(k.x, k.y, 2*k.r, 2*k.r);
		}
	}
	
}
