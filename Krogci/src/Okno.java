import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JToolBar;
import java.awt.GridBagConstraints;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Okno extends JFrame {

	private JPanel contentPane;
	private JTextField path;
	private OrgSlika orgSlika;
	private NovaSlika novaSlika;
	private Algoritem2 algoritem;

	/**
	 * Create the frame.
	 */
	public Okno() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(path.getText());
				//orgSlika = new OrgSlika("C:\\Users\\MaliMsi\\Desktop\\Kid_krillin_peace.jpg");
				Graphics g = panel.getGraphics();
				
				//Dimension velikost = new Dimension(orgSlika.image.getWidth(), orgSlika.image.getHeight());
				
				//panel.setSize(orgSlika.image.getHeight(), orgSlika.image.getWidth());
				//panel.setPreferredSize(velikost);
				//orgSlika.narisi(g);
				pack();
				algoritem = new Algoritem2(orgSlika, 30, 10, 10, g);
				novaSlika = algoritem.novaSlika;
				novaSlika.narisi(g);
				//algoritem.narisi(g);
			}
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				BufferedImage fileSlika = new BufferedImage(orgSlika.image.getWidth(), orgSlika.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			    Graphics gg = fileSlika.getGraphics();
			    novaSlika.narisi(gg);
			    try 
			    {
			    	System.out.println("shranjuje");
			    	ImageIO.write(fileSlika, "PNG", new File("yourImageName.PNG"));
			    } 
			    catch (IOException e1)
			    {
			    	System.out.println("ne shrani");
			    	e1.printStackTrace();
			    }
			}
			
		});
		
		mnFile.add(mntmSave);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				String text = path.getText();
				String pot = "C:\\Users\\MaliMsi\\Desktop\\projektslike\\";
				pot = pot.concat(text);
				pot = pot.concat(".jpg");
				System.out.println(pot);
				orgSlika = new OrgSlika(pot);
				//Graphics g = panel.getGraphics();
				Dimension velikost = new Dimension(orgSlika.image.getWidth(), orgSlika.image.getHeight());
				panel.setPreferredSize(velikost);
				pack();
				
				
			}
		});
		menuBar.add(btnStart);
		
		path = new JTextField();
		menuBar.add(path);
		path.setColumns(10);
		
		
	}
	
	
	

}
