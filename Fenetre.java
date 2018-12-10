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
import java.util.ArrayList;
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
		private Image halfDomino;
		private int x;
		private int y;
		
		public Case(Image texture, int x, int y)
		{
			this.halfDomino = texture;
			this.x = x;
			this.y = y;
			if(isFull() == true)
				this.setBorderPainted(false);
		}
		
		public boolean isFull()
		{
			return halfDomino != textures.get(0);
		}
		
		public int[] get()
		{
			return new int[] {this.x, this.y};
		}
		
		
		public void set(Image texture)
		{
			this.halfDomino = texture;
			this.setBorderPainted(false); 
			this.repaint(); //on demande a reafficher le panel mis a jour
		}
		
		public void paintComponent(Graphics g)
		{
			g.drawImage(halfDomino, 0, 0, this.getWidth(), this.getHeight(), null);
		} 
	}
	
	//CLASSE INTERNE qui represente un domino de notre pioche
	class Domino extends JButton
	{
		private Image halfDomino1;
		private Image halfDomino2;
		private Image pionJoueur;

		private int[] domino;
		private boolean highlight = false;
		private boolean crossed = false;
		
		public Domino(int[] domino, int couleur)
		{
			this.domino = domino;
			this.halfDomino1 = textures.get(domino[0]*10 + domino[1]);
			this.halfDomino2 = textures.get(domino[2]*10 + domino[3]);
			if(couleur != 0)
			{
				this.setBorderPainted(false);
				this.pionJoueur = textures.get(couleur + 1);
			}
			else
				this.pionJoueur = null;
		
		}
		
		public int[] get()
		{
			return domino;
		}
		
		private boolean nobodyOwns()
		{
			return pionJoueur == null;
		}
				
		public void setCouleur(int couleur) //inque l'appartenance du domino (pour la pioche du tour suivant)
		{
			this.setBorderPainted(false);
			this.pionJoueur = textures.get(couleur + 1);;
		}
		
		public void setHighlight(boolean highlight) //insique si le domino doit etre encadre en rouge (domino a jouer)
		{
			this.highlight = highlight;
		}
		
		public void setCrossed(boolean crossed)//insique si le domino doit etre barre en rouge (domino deja choisie)
		{
			this.crossed = crossed;
		}
		
		public void paintComponent(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			int thickness = 6;
			Stroke save = g2d.getStroke(); //on sauvegarde l'ancienne methode de rendu pour ne pas influer sur les autres affichages du programme (tous les traits auraient une epaisseur de 6 sinon)
			g2d.setStroke(new BasicStroke(thickness));

			g2d.drawImage(halfDomino1, 0, 0, this.getWidth()/2, this.getHeight(), null); //premiere partie du domino
			g2d.drawImage(halfDomino2, this.getWidth()/2, 0, this.getWidth()/2, this.getHeight(), null); //deuxieme partie du domino
			
			
			if(highlight)
			{
				g2d.setColor(Color.RED);
				g2d.drawRect(thickness/2, thickness/2, this.getWidth() - thickness, this.getHeight() - thickness);
			}
			else if(crossed)
			{
				g2d.setColor(Color.RED);
				g2d.drawLine(10, 10, this.getWidth() - 10, this.getHeight() - 10);
			}
			
			if(nobodyOwns() == false)
			{
				g2d.drawImage(pionJoueur, this.getWidth() - 60, 10, 50, 50, null);
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
	
	private Case[][][] cases;
	private Domino[] domino_manche;
	private Domino[] domino_manche_plus_1;
	
	private int joueur = 1;
	private String nom;
	
	private JPanel[] pan_plateau;
	private JPanel pan_pioche_manche;
	private JPanel pan_pioche_manche_plus_1;
	private Infos pan_infos;
	
	private JLabel lab_action;
	private JLabel lab_manche;
	
	private int actionEnCours = 0;
	
	
	//CONSTRUCTEUR
    public Fenetre(Plateau[] plateaux, Pioche pioche_initiale, ArrayList<Integer> couleurs)
	{

    	for(int i = 0; i <= 48; i++)
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
	    		
	    //plateaux de tous les joueurs
	    pan_plateau = new JPanel[plateaux.length];
	    cases = new Case[plateaux.length][9][9];
	    for(int i = 0; i < pan_plateau.length; i++)
	    {
	    	pan_plateau[i] = new JPanel();
	    	pan_plateau[i].setLayout(new GridLayout(9, 9, 5, 5));
	    	for(int x = 0; x < 9; x++)
		    {
		    	for(int y = 0; y < 9; y++)
		    	{
		    		cases[i][x][y] = new Case(textures.get(plateaux[i].getCouronne(x, y)*10 + plateaux[i].getNature(x, y)), x, y);
		    		
		    		//on ajoute une action aux cases: celle de retourner l'indice correspondant
		    		cases[i][x][y].addActionListener(new ActionListener()
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
		    		pan_plateau[i].add(cases[i][x][y]);
		    	}
		    }
	    }
	    
	    //meme s'il est vide, il faut l'initialiser
	    pan_pioche_manche = new JPanel();
	    
	    //PIOCHE INTIALE (qu'on definit comme etant celle de la manche plus 1 car elle sera intervertit lors de l'update des pioche)
	    pan_pioche_manche_plus_1 = new JPanel();
	    pan_pioche_manche_plus_1.setLayout(new GridLayout(pioche_initiale.getSize(), 1, 20, 20));
	    domino_manche_plus_1 = new Domino[pioche_initiale.getSize()];
	    for(int i = 0; i < pioche_initiale.getSize(); i++)
	    {
	    	this.domino_manche_plus_1[i] = new Domino(pioche_initiale.getDominoByIndex(i), couleurs.get(pioche_initiale.getJoueurByIndex(i)-1));
	    	pan_pioche_manche_plus_1.add(domino_manche_plus_1[i]);
	    }
	    
	    //INFOS
	    Color couleur = new Color(15, 67, 113);
	    pan_infos = new Infos(new GridLayout(2, 1));
	    lab_manche = new JLabel();
	    lab_manche.setFont(new Font("Book Antiqua", Font.PLAIN, 50));
	    lab_manche.setForeground(couleur);
	    lab_manche.setHorizontalAlignment(JLabel.CENTER);
	    pan_infos.add(lab_manche);
	    
	    lab_action = new JLabel();
	    lab_action.setFont(new Font("Book Antiqua", Font.PLAIN, 50));
	    lab_action.setForeground(couleur);
	    lab_action.setHorizontalAlignment(JLabel.CENTER);
	    pan_infos.add(lab_action);
	    
	    //on ajoute uniquement les infos a la fenetre (le reste sera ajoute ensuite)
	    this.getContentPane().setLayout((new GridBagLayout()));
	    GridBagConstraints c = new GridBagConstraints();
	    
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		this.getContentPane().add(pan_infos, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.5;
		this.getContentPane().add(pan_pioche_manche, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.5;
		this.getContentPane().add(pan_pioche_manche_plus_1, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 0.5;
		c.weighty = 1;
		this.getContentPane().add(pan_plateau[joueur-1], c);
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
    
    public void setDomino(int[] pos, int[] domino)
    {
    	cases[joueur-1][pos[0]][pos[1]].set(textures.get(domino[0]*10 + domino[1]));
    	cases[joueur-1][pos[2]][pos[3]].set(textures.get(domino[2]*10 + domino[3]));
    }
    
    public void setCouleur(int[] domino, int couleur)
    {
    	for(int i = 0; i < domino_manche_plus_1.length; i++)
    	{
    		if(domino_manche_plus_1[i].get() == domino)
    			domino_manche_plus_1[i].setCouleur(couleur);
    	}    
    }
    
    public void setHighlight(int[] domino)
    {
    	for(int i = 0; i < domino_manche.length; i++)
    	{
    		if(domino_manche[i].get() == domino)
    			domino_manche[i].setHighlight(true);
    		else
    			domino_manche[i].setHighlight(false);
    	}    	
    }
    
    public void setCrossed(int[] domino)
    {
    	for(int i = 0; i < domino_manche_plus_1.length; i++)
    	{
    		if(domino_manche_plus_1[i].get() == domino)
    			domino_manche_plus_1[i].setCrossed(true);
    	}    
    }
    
    
    public void setJoueur(int joueur, String nom)
    {
    	if(joueur != this.joueur) //si c'est toujours le meme joueur, rien a faire
    	{
    		//on supprime le plateau de l'affichage
			this.getContentPane().remove(pan_plateau[this.joueur-1]);
			
			//on ajoute le bon plateau a l'affichage
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 2;
			c.gridy = 0;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.weightx = 0.5;
			c.weighty = 1;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10, 10, 10, 10);
			this.getContentPane().add(pan_plateau[joueur-1], c);
			this.getContentPane().validate();
			this.getContentPane().repaint();
			
			//on met a jour ces attributs
	    	this.joueur = joueur;
	    	this.nom = nom;
    	}
    }
    
    public void updateManche(int manche)
    {
    	lab_manche.setText("Manche " + manche);
    }
    
    public void updateAction(int action)
    {
    	actionEnCours = action;
    	if(action == Partie.PLACER_DOMINO)
    		lab_action.setText(nom + " place son domino");
    	else if(action == Partie.CHOISIR_DOMINO)
    		lab_action.setText(nom + " choisit son domino");
    	else if(action == Partie.IMPOSSIBLE_PLACER_DOMINO)
    	{
    		JOptionPane message = new JOptionPane();
			// affiche un message d'erreur pour avertir l'utilisateur
			message.showMessageDialog(null, "le domino ne peut pas etre place sur le plateau", "action impossible", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    public void updatePioche(Pioche pioche_manche_plus_1)
    {
    	GridBagConstraints c = new GridBagConstraints();
    	
    	//on supprime les 2 pioches de l'affichage
    	this.getContentPane().remove(pan_pioche_manche);
    	this.getContentPane().remove(pan_pioche_manche_plus_1);
    	
    	//la pioche de la manche plus 1 devient celle du tour actuel (pas besoin de la creer)
    	pan_pioche_manche = pan_pioche_manche_plus_1;
    	domino_manche = domino_manche_plus_1;
    	for(int i = 0; i < domino_manche.length; i++) //les dominos choisis ne sont plus barres
    	{
    		domino_manche[i].setCrossed(false);
    	}
    	
    	//on l'ajoute a l'affichage
    	c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		this.getContentPane().add(pan_pioche_manche, c);
    	
    	//on cree la pioche de la manche plus 1
	    pan_pioche_manche_plus_1 = new JPanel();
	    pan_pioche_manche_plus_1.setLayout(new GridLayout(pioche_manche_plus_1.getSize(), 1, 20, 20));
	    domino_manche_plus_1 = new Domino[pioche_manche_plus_1.getSize()];
	    for(int i = 0; i < pioche_manche_plus_1.getSize(); i++)
	    {
	    	domino_manche_plus_1[i] = new Domino(pioche_manche_plus_1.getDominoByIndex(i), 0);
	    	domino_manche_plus_1[i].addActionListener(new ActionListener()
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
	    	pan_pioche_manche_plus_1.add(domino_manche_plus_1[i]);
	    }
	    
	    //on l'ajoute a l'affichage
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.25;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		this.getContentPane().add(pan_pioche_manche_plus_1, c);
		this.getContentPane().validate();
		this.getContentPane().repaint();
    }
    
}