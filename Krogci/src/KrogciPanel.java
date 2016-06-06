import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JPanel;

public class KrogciPanel extends JPanel {
	
	private ArrayList<Krogec> krogci;
	private ConcurrentLinkedQueue<Krogec> vrstaKrogcev;

	public KrogciPanel() {
		super();
		setBackground(Color.black);
		krogci = new ArrayList<Krogec>();
		
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		while(!vrstaKrogcev.isEmpty())
		{
			krogci.add(vrstaKrogcev.poll());
		}
		//krogci.addAll(vrstaKrogcev.toArray());
		
		for (Krogec k: krogci)
		{
			g.setColor(k.barva);
			g.fillOval(k.x, k.y, 2*k.r, 2*k.r);
		}
		
	}
	
	public void dodajKrogec(Krogec k)
	{
		vrstaKrogcev.offer(k);
		//krogci.add(vrstaKrogcev.poll());
		repaint();
	}

	public ArrayList<Krogec> getKrogci() 
	{
		return krogci;
	}
	
	public void pobrisiSliko()
	{
		krogci = new ArrayList<Krogec>();
		System.out.print("slika pobrisana");
	}
	
	

}
