import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JToolBar;

import java.awt.GridBagConstraints;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Okno extends JFrame {

	private JPanel contentPane;
	private JTextField path;
	private OrgSlika orgSlika;
	private Thread vlakno;
	JFileChooser chooser = new JFileChooser();
    

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
		contentPane.setBackground(Color.BLACK);
		
		KrogciPanel krogciPanel = new KrogciPanel();
		contentPane.add(krogciPanel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String text = path.getText();
				String pot = "C:\\Users\\MaliMsi\\Desktop\\projektSlike\\";
				pot = pot.concat(text);
				pot = pot.concat(".jpg");
				System.out.println(pot);
				orgSlika = new OrgSlika(pot);
				//Graphics g = panel.getGraphics();
				Dimension velikost = new Dimension(orgSlika.image.getWidth(), orgSlika.image.getHeight());
				krogciPanel.setPreferredSize(velikost);
				pack();
				
			}
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{	
				
				
				BufferedImage fileSlika = new BufferedImage(orgSlika.image.getWidth(), orgSlika.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			    try 
			    {
			    	System.out.println("shranjuje");
			    	ImageIO.write(fileSlika, "PNG", new File("yourImageName2.PNG"));
			    } 
			    catch (IOException e1)
			    {
			    	System.out.println("ne shrani");
			    	e1.printStackTrace();
			    }
			}
			
		});
		
		mnFile.add(mntmSave);
		
		JMenuItem mntmNewLoad = new JMenuItem("New Load");
		mntmNewLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "JPG & GIF Images", "jpg", "gif");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(contentPane);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
				    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
				    String path = chooser.getSelectedFile().getAbsolutePath();
				    orgSlika = new OrgSlika(path);
				    Dimension velikost = new Dimension(orgSlika.image.getWidth(), orgSlika.image.getHeight());
					krogciPanel.setPreferredSize(velikost);
					pack();
				}
			   
			}
		});
		mnFile.add(mntmNewLoad);
		
		JMenuItem mntmNewSafe = new JMenuItem("New Safe");
		mntmNewSafe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				
				 int retrival = chooser.showSaveDialog(null);
				 if (retrival == JFileChooser.APPROVE_OPTION) {
				    try 
				    {
				    	
				    	BufferedImage fileSlika = new BufferedImage(orgSlika.image.getWidth(), orgSlika.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
					    Graphics g = fileSlika.getGraphics();
				    	try 
					    {
					    	System.out.println("shranjuje");
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
					    	e1.printStackTrace();
					    }
				    	
				        
				    } 
				    catch (Exception ex) 
				    {
				        ex.printStackTrace();
				    }
				}
				
				
				
			}
		});
		mnFile.add(mntmNewSafe);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				System.out.println(path.getText());
				//orgSlika = new OrgSlika("C:\\Users\\MaliMsi\\Desktop\\Kid_krillin_peace.jpg");
				//Dimension velikost = new Dimension(orgSlika.image.getWidth(), orgSlika.image.getHeight());
				
				//panel.setSize(orgSlika.image.getHeight(), orgSlika.image.getWidth());
				//panel.setPreferredSize(velikost);
				//orgSlika.narisi(g);
				
				if (vlakno == null)
				{
					vlakno = new Thread(new Algoritem2(orgSlika, 30, 10, 10, krogciPanel));
					vlakno.start();
				}
				//novaSlika.narisi(g);
				//algoritem.narisi(g);
				
				
			}
		});
		menuBar.add(btnStart);
		
		path = new JTextField();
		menuBar.add(path);
		path.setColumns(10);
		
		
	}
	
	
	

}
