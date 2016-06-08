import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class KrogciPanel extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4496244535689647461L;
	private CopyOnWriteArrayList<Krogec> seznamKrogcev;

	public KrogciPanel() {
		super();
		setBackground(Color.black);
		seznamKrogcev = new CopyOnWriteArrayList<Krogec>();
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for (Krogec k: seznamKrogcev)
		{
			g.setColor(k.barva);
			g.fillOval(k.x, k.y, 2*k.r, 2*k.r);
		}	
	}
	
	public void dodajKrogec(Krogec k)
	{
		seznamKrogcev.add(k);
		repaint();
	}

	public CopyOnWriteArrayList<Krogec> getKrogci() 
	{
		return seznamKrogcev;
	}
	
	public void pobrisiSliko()
	{
		seznamKrogcev = new CopyOnWriteArrayList<Krogec>();
		repaint();
	}
	
	

}
