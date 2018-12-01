import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Fenetre extends JFrame
{
	//CLASSE INTERNE qui represente une case de notre damier
	class Case extends JButton
	{
		private Image texture = null;
		private int x;
		private int y;
		
		public Case(Image texture, int x, int y)
		{
			this.texture = texture;
			this.x = x;
			this.y = y;
		}
		
		public boolean isFull()
		{
			return texture != textures.get(0);
		}
		
		public int[] get()
		{
			return new int[] {this.x, this.y};
		}
		
		
		public void set(Image texture)
		{
			this.texture = texture;
			this.repaint(); //on demande a reafficher le panel mis a jour
			this.setBorderPainted(false);
		}
		
		public void paintComponent(Graphics g)
		{
			g.drawImage(texture, 0, 0, this.getWidth(), this.getHeight(), null);
		} 
	}
	
	
	//ATTRIBUTS PRIVES
	private HashMap<Integer, Image> textures = new HashMap<Integer, Image>();
	private int clicks[] = new int[] {-1, -1, -1, -1};
	private Case[][] cases = new Case[9][9];
	JPanel damier;
	
	//CONSTRUCTEUR
    public Fenetre(Plateau plateau)
	{
    	for(int i = 0; i < 48; i++)
    	{
	    	try
			{
				textures.put(i, ImageIO.read(new File("images//" + i + ".png")));
			}
			catch(IOException e)
			{
			}
    	}
    	
    	//on cree notre fenetre
	    this.setTitle("KingDomino");
	    this.setSize(800, 600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
	    
	    //on creee un conteneur qui contient toutes noses cases
	    damier = new JPanel();
	    damier.setLayout(new GridLayout(9, 9, 5, 5));
	    for(int x = 0; x < 9; x++)
	    {
	    	for(int y = 0; y < 9; y++)
	    	{
	    		cases[x][y] = new Case(textures.get(plateau.getCouronne(x, y)*10 + plateau.getNature(x, y)), x, y);
	    		
	    		//on ajoute une action aux cases: celle de retourner l'indice correspondant
	    		cases[x][y].addActionListener(new ActionListener()
				{
	    			public void actionPerformed(ActionEvent e)
	    			{
	    				//on recupere la case qui a ete selectionnee
	    				Case source = (Case) e.getSource();
	    				
	    				//si la case est deja remplie, on ne peut pas la selectionner
	    				if(!source.isFull())
	    				{
		    				int x = source.get()[0];
		    				int y = source.get()[1];
		    				
		    				//on stocke l'indice des cases dans le tableau
		    				if(clicks[0] != -1)
		    				{
		    					clicks[2] = x;
		    					clicks[3] = y;
		    				}
		    				else
		    				{
		    					clicks[0] = x;
		    					clicks[1] = y;
		    				}
	    				}
	    			}
				});
	    		//on ajoute les cases au conteneur
	    		damier.add(cases[x][y]);
	    	}
	    }
	    //on ajoute le conteneur a la fenetre
	    this.setContentPane(damier);
	}
    
    
    
    public boolean hasClickedTwice()
    {
    	return clicks[0] != -1 && clicks[1] != -1 && clicks[2] != -1 && clicks[3] != -1;
    }
    
    public int[] getClicks()
    {
    	int[] results = new int[4];
    	for(int i = 0; i < 4; i++)
    	{
    		results[i] = clicks[i];
    		clicks[i] = -1;
    	}
    	return results;
    }
    
    public void setTextures(int[] pos, int[] domino)
    {
    	cases[pos[0]][pos[1]].set(textures.get(domino[0]*10 + domino[1]));
    	cases[pos[2]][pos[3]].set(textures.get(domino[2]*10 + domino[3]));
    }
}