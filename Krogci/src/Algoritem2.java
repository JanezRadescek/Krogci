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
		
	public Algoritem2(OrgSlika orgSlika, int maxR, int odstopanjeRGB, int odstopanjePix) 
	{
		this.orgSlika = orgSlika;
		novaSlika = new NovaSlika();
		visina = orgSlika.image.getHeight();
		sirina = orgSlika.image.getWidth();
		trenutniR = 50;
		minimalniR = 5;
		rdeca = new Color(255, 0, 0);
		modra = new Color(0,255,0);
		
		glavna();
	}
	
	public void glavna()
	{
		for(int i = 0; i < 10; i++){
			novoSidro();
			DodajKrog();
		}
	}

	private void novoSidro() {
		rn = new Random();
		sidrox = rn.nextInt(sirina - trenutniR) + trenutniR + 1;
		sidroy = rn.nextInt(visina - trenutniR) + trenutniR + 1;
		
	}

	private void DodajKrog() {
		
		novaSlika.slika.add(new Krogec(sidrox,sidroy,trenutniR,rdeca));		
			
	}
	
	public void narisi(Graphics g)
	{
		for (Krogec k: novaSlika.slika)
		{
			g.setColor(k.barva);
			g.fillOval(k.x - trenutniR, k.y - trenutniR, 2*k.r, 2*k.r);
		}
	}
	
}
