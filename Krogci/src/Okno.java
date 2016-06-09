import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Okno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103785570254760021L;
	private OrgSlika orgSlika;
	private Thread vlakno;
	private Algoritem2 algoritem;
	JFileChooser chooser = new JFileChooser();
    /**
	 * Create the frame.
	 */
	public Okno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		KrogciPanel krogciPanel = new KrogciPanel();
		setContentPane(krogciPanel);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (vlakno == null) 
				{
					algoritem = new Algoritem2(orgSlika, 30, 10, 10, krogciPanel);
					vlakno = new Thread(algoritem);
					vlakno.start();
				}
								
			}
		});
		
		JButton btnNewLoad = new JButton("Load");
		btnNewLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "JPG & GIF Images", "jpg", "gif");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(krogciPanel);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					
					if (vlakno != null)
					{
						algoritem.koncaj();
						try {
							vlakno.join();
							vlakno = null;
						} catch (InterruptedException e1) {
							
						}
							
						
					}
					
					krogciPanel.pobrisiSliko();
					krogciPanel.repaint();
					
				    String path = chooser.getSelectedFile().getAbsolutePath();
				    orgSlika = new OrgSlika(path);
				    Dimension velikost = new Dimension(orgSlika.image.getWidth(), orgSlika.image.getHeight());
					krogciPanel.setPreferredSize(velikost);
					pack();
				}
			}
		});
		menuBar.add(btnNewLoad);
		
		JButton btnNewSafe = new JButton("Save");
		btnNewSafe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int retrival = chooser.showSaveDialog(null);
				 if (retrival == JFileChooser.APPROVE_OPTION) {
				    try 
				    {
				    	
				    	BufferedImage fileSlika = new BufferedImage(orgSlika.image.getWidth(), orgSlika.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
					    Graphics g = fileSlika.getGraphics();
				    	try 
					    {
					    	g.setColor(new Color(0,0,0));
					    	g.fillRect(0, 0, orgSlika.image.getWidth(), orgSlika.image.getWidth());
					    	for (Krogec k: krogciPanel.getKrogci())
							{
								g.setColor(k.barva);
								g.fillOval(k.x, k.y, 2*k.r, 2*k.r);
							}
					    						    	
					    	ImageIO.write(fileSlika, "PNG", chooser.getSelectedFile());
					    } 
					    catch (IOException e1)
					    {
					    	System.out.println("ne shrani");
					    }
				    	
				        
				    } 
				    catch (Exception ex) 
				    {
				    	
				    }
				}
			}
		});
		menuBar.add(btnNewSafe);
		menuBar.add(btnStart);
		
		
	}
	

}
