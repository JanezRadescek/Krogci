import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class KrogciPanel extends JPanel {
	
	private ArrayList<Krogec> krogci;

	public KrogciPanel() {
		super();
		setBackground(Color.black);
		krogci = new ArrayList<Krogec>();
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Krogec k: krogci)
		{
			g.setColor(k.barva);
			g.fillOval(k.x, k.y, 2*k.r, 2*k.r);
		}
		
	}
	
	public void dodajKrogec(Krogec k)
	{
		krogci.add(k);
		repaint();
	}

}
