import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
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
			if(isFull() == true)
				this.setBorderPainted(false);
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
			this.setBorderPainted(false); 
			this.repaint(); //on demande a reafficher le panel mis a jour
		}
		
		public void paintComponent(Graphics g)
		{
			g.drawImage(texture, 0, 0, this.getWidth(), this.getHeight(), null);
		} 
	}
	
	//CLASSE INTERNE qui represente un domino de notre pioche
	class Domino extends JButton
	{
		private Image texture1;
		private Image texture2;
		private int[] domino;
		private int joueur;
		private boolean highlight;
		private boolean crossed;
		
		public Domino(int[] domino, int joueur)
		{
			this.domino = domino;
			this.texture1 = textures.get(domino[0]*10 + domino[1]);
			this.texture2 = textures.get(domino[2]*10 + domino[3]);
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
			this.setBorderPainted(false);
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
			Stroke save = g2d.getStroke(); //on sauvegarde l'ancienne methode de rendu pour ne pas influer sur les autres affichages du programme (tous les traits auraient une epaisseur de 6 sinon)
			g2d.setStroke(new BasicStroke(thickness));

			g2d.drawImage(texture1, 0, 0, this.getWidth()/2, this.getHeight(), null); //premiere partie du domino
			g2d.drawImage(texture2, this.getWidth()/2, 0, this.getWidth()/2, this.getHeight(), null); //deuxieme partie du domino
			
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
			g2d.setStroke(save); //on restaure l'ancienne methode de rendu
		} 
	}
	
	class Infos extends JPanel
	{
		private Image texture;
		
		public Infos(GridLayout g)
		{
			super(g);
			try
			{
				texture =  ImageIO.read(new File("images//ingame.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void paintComponent(Graphics g)
		{
			g.drawImage(texture, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
	
	
	//ATTRIBUTS PRIVES
	private HashMap<Integer, Image> textures = new HashMap<Integer, Image>();
	
	private int[] pos = new int[] {-1, -1, -1, -1}; //2 clics pour placer domino
	private int[] domino = null; //1 clic pour choisir domino
	
	private Case[][] cases = new Case[9][9];
	private Domino[][] dominos = new Domino[4][2];
	private JPanel page;
	
	private Infos infos;
	private JPanel damier;
	private JPanel domino_manche_Pan;
	private JPanel domino_manche_plus_1_Pan;
	
	private JLabel joueur;
	private JLabel manche;
	
	private String nom;
	private int actionEnCours = 0;
	
	
	//CONSTRUCTEUR
    public Fenetre(Plateau plateau, Pioche domino_manche, Pioche domino_manche_plus_1, String nom, int manche)
	{
    	for(int i = 0; i < 48; i++)
    	{
	    	try
			{
				textures.put(i, ImageIO.read(new File("images//" + i + ".png")));
			}
			catch(IOException e)
			{}
    	}
    	
    	//on cree notre fenetre
	    this.setTitle("KingDomino");
	    this.setSize(800, 600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); //affichage plein ecran du jeu, mais redimmensionnable par la suite
	    
		this.nom = nom;
		
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
	    				if(actionEnCours != 1) //le programme ne retient pas les cases si ce n'est pas l'action a effectuer
	    					return;
	    				
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
	    	dominos[i][0] = new Domino(domino_manche.getDominoByIndex(i), domino_manche.getJoueurByIndex(i));
	    	domino_manche_Pan.add(dominos[i][0]);
	    }
	    
	    //DOMINO MANCHE PLUS 1
	    domino_manche_plus_1_Pan = new JPanel();
	    domino_manche_plus_1_Pan.setLayout(new GridLayout(domino_manche_plus_1.getSize(), 1, 20, 20));
	    for(int i = 0; i < domino_manche_plus_1.getSize(); i++)
	    {
	    	dominos[i][1] = new Domino(domino_manche_plus_1.getDominoByIndex(i), domino_manche_plus_1.getJoueurByIndex(i));
	    	dominos[i][1].addActionListener(new ActionListener()
			{
    			public void actionPerformed(ActionEvent e)
    			{
    				if(actionEnCours != 2) //le programme ne retient pas le domino si ce n'est pas l'action a effectuer
    					return;
    				
    				//on le domino qui a ete selectionnee
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
	    
	    //INFOS
	    
	    Color couleur = new Color(15, 67, 113);
	    infos = new Infos(new GridLayout(2, 1));
	    this.manche = new JLabel("Manche " + manche);
	    this.manche.setFont(new Font("Book Antiqua", Font.PLAIN, 50));
	    this.manche.setForeground(couleur);
	    this.manche.setHorizontalAlignment(JLabel.CENTER);
	    
	    infos.add(this.manche);
	    this.joueur = new JLabel(nom);
	    this.joueur.setFont(new Font("Book Antiqua", Font.PLAIN, 50));
	    this.joueur.setForeground(couleur);
	    this.joueur.setHorizontalAlignment(JLabel.CENTER);
	    
	    infos.add(this.joueur);
	    
	    //on ajoute le conteneur a la fenetre
	    page = new JPanel(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		page.add(infos, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.45;
		
		page.add(domino_manche_Pan, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.45;
		page.add(domino_manche_plus_1_Pan, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 0.5;
		c.weighty = 1;
		page.add(damier, c);
		

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
    		if(dominos[i][1].get() == domino)
    			dominos[i][1].setJoueur(joueur);
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
    
    public void setActionEnCours(int action)
    {
    	actionEnCours = action;
    	if(action == Partie.PLACER_DOMINO)
    		joueur.setText(nom + " place son domino");
    	else if(action == Partie.CHOISIR_DOMINO)
    		joueur.setText(nom + " choisit son domino");
    	else if(action == Partie.IMPOSSIBLE_PLACER_DOMINO)
    	{
    		JOptionPane message = new JOptionPane();
			// affiche un message d'erreur pour avertir l'utilisateur
			message.showMessageDialog(null, "le domino ne peut pas etre place sur le plateau", "action impossible", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
}