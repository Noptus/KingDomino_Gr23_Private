import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
	
	class Domino extends JButton
	{
		private Image texture1;
		private Image texture2;
		private int indice;
		private int[] domino;
		private int joueur;
		private boolean highlight;
		private boolean crossed;
		
		public Domino(int[] domino, int indice, int joueur)
		{
			this.domino = domino;
			this.texture1 = textures.get(domino[0]*10 + domino[1]);
			this.texture2 = textures.get(domino[2]*10 + domino[3]);
			this.indice = indice;
			this.joueur = joueur;
			if(nobodyOwns() == false)
				this.setBorderPainted(false);
		
		}
		
		public int[] get()
		{
			return domino;
		}
		
		private boolean nobodyOwns()
		{
			return joueur == 0;
		}
				
		public void setJoueur(int joueur) //inque l'appartenance du domino (pour la pioche du tour suivant)
		{
			this.joueur = joueur;
		}
		
		public void setHighlight(boolean highlight) //insique si le domino doit etre encadre en rouge (domino a jouer)
		{
			this.highlight = highlight;
		}
		
		public void setCrossed(boolean crossed) //indique si le domino doit etre barre (domino deja pioche)
		{
			this.crossed = crossed;
		}
		
		public void paintComponent(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			int thickness = 6;
			Stroke save = g2d.getStroke();
			g2d.setStroke(new BasicStroke(thickness));

			g2d.drawImage(texture1, 0, 0, this.getWidth()/2, this.getHeight(), null);
			g2d.drawImage(texture2, this.getWidth()/2, 0, this.getWidth()/2, this.getHeight(), null);
			if(highlight)
			{
				g2d.setColor(Color.RED);
				g2d.drawRect(thickness/2, thickness/2, this.getWidth() - thickness, this.getHeight() - thickness);
			}
			if(crossed)
			{
				g2d.setColor(Color.RED);
				g2d.drawLine(10, 10, this.getWidth() - 10, this.getHeight() - 10);
			}
			g2d.setStroke(save);
		} 
	}
	
	
	//ATTRIBUTS PRIVES
	private HashMap<Integer, Image> textures = new HashMap<Integer, Image>();
	
	private int[] pos = new int[] {-1, -1, -1, -1}; //2 clics pour placer domino
	private int[] domino = null; //1 clic pour choisir domino
	
	private Case[][] cases = new Case[9][9];
	private Domino[][] dominos = new Domino[4][2];
	private int joueur;
	JPanel page;
	JPanel damier;
	JPanel domino_manche_Pan, domino_manche_plus_1_Pan;
	
	
	//CONSTRUCTEUR
    public Fenetre(Plateau plateau, Pioche domino_manche, Pioche domino_manche_plus_1, int joueur)
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
	    
	    //DAMIER
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
		    				if(pos[0] != -1)
		    				{
		    					pos[2] = x;
		    					pos[3] = y;
		    				}
		    				else
		    				{
		    					pos[0] = x;
		    					pos[1] = y;
		    				}
	    				}
	    			}
				});
	    		//on ajoute les cases au conteneur
	    		damier.add(cases[x][y]);
	    	}
	    }
	    
	    //PIOCHE MANCHE
	    domino_manche_Pan = new JPanel();
	    domino_manche_Pan.setLayout(new GridLayout(domino_manche.getSize(), 1, 20, 20));
	    for(int i = 0; i < domino_manche.getSize(); i++)
	    {
	    	dominos[i][0] = new Domino(domino_manche.getDominoByIndex(i), i, domino_manche.getJoueurByIndex(i));
	    	domino_manche_Pan.add(dominos[i][0]);
	    }
	    
	    //DOMINO MANCHE PLUS 1
	    domino_manche_plus_1_Pan = new JPanel();
	    domino_manche_plus_1_Pan.setLayout(new GridLayout(domino_manche_plus_1.getSize(), 1, 20, 20));
	    for(int i = 0; i < domino_manche_plus_1.getSize(); i++)
	    {
	    	dominos[i][1] = new Domino(domino_manche_plus_1.getDominoByIndex(i), i, domino_manche_plus_1.getJoueurByIndex(i));
	    	dominos[i][1].addActionListener(new ActionListener()
			{
    			public void actionPerformed(ActionEvent e)
    			{
    				//on recupere la case qui a ete selectionnee
    				Domino source = (Domino) e.getSource();
    				
    				//si le domino appartient deja a quelqu'un, on ne peut pas le choisir
    				if(source.nobodyOwns())
    				{
    					domino = source.get();
    				}
    			}
			});
	    	domino_manche_plus_1_Pan.add(dominos[i][1]);
	    }
	    
	    
	    
	    //on ajoute le conteneur a la fenetre
	    page = new JPanel(new GridLayout(1, 3, 20, 20));
	    page.add(domino_manche_Pan);
	    page.add(domino_manche_plus_1_Pan);
	    page.add(damier);
	    this.setContentPane(page);
	}
    
    
    
    public boolean hasPlacedDomino()
    {
    	return pos[0] != -1 && pos[1] != -1 && pos[2] != -1 && pos[3] != -1;
    }
    
    public boolean hasSelectedDomino()
    {
    	return domino != null;
    }
    
    public int[] getPositions()
    {
    	int[] results = new int[4];
    	for(int i = 0; i < 4; i++)
    	{
    		results[i] = pos[i];
    		pos[i] = -1;
    	}
    	return results;
    }
    
    public int[] getDomino()
    {
    	int[] result = domino;
    	domino = null;
    	return result;
    }
    
    public void setTextures(int[] pos, int[] domino)
    {
    	cases[pos[0]][pos[1]].set(textures.get(domino[0]*10 + domino[1]));
    	cases[pos[2]][pos[3]].set(textures.get(domino[2]*10 + domino[3]));
    }
    
    public void setJoueur(int[] domino, int joueur)
    {
    	for(int i = 0; i < dominos.length; i++)
    	{
    		if(dominos[i][0].get() == domino)
    			dominos[i][0].setJoueur(joueur);
    	}    
    }
    
    public void setHighlight(int[] domino)
    {
    	for(int i = 0; i < dominos.length; i++)
    	{
    		if(dominos[i][0].get() == domino)
    			dominos[i][0].setHighlight(true);
    		else
    			dominos[i][0].setHighlight(false);
    	}    	
    }
    
    public void setCrossed(int[] domino)
    {
    	for(int i = 0; i < dominos.length; i++)
    	{
    		if(dominos[i][1].get() == domino)
    			dominos[i][1].setCrossed(true);
    	}    
    }
    
}