import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuCouleurs extends JDialog
{
	//ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	
	//ces attributs sont ceux qui nous permettent de recuperer les valeurs
	private ArrayList<JComboBox> couleurs = new ArrayList<JComboBox>();
	
	private boolean jouer = false;
	
	//stocke les fonds d'ecran pour mettre a jour l'image (quand la couleur change)
	private ArrayList<BackgroundPanel> backgroundColors = new ArrayList<BackgroundPanel>();

	//stocke les textures des chateaux pour les afficher a cote de la couleur choisie par le joueur
	private HashMap<String, Image> chateaux = new HashMap<String, Image>();
	private String[] choixCouleurs = {"bleu", "jaune", "rose", "vert"};
	
	
	//CLASSE INTERNE
	
	//on creee une classe JPanel custom qui affiche une image qui peut etre mise a jour
	class BackgroundPanel extends JPanel
	{
		private Image background;
		
		public BackgroundPanel(Image background)
		{
			this.background = background;
		}
		
		public void setBackgroundImage(Image background)
		{
			this.background = background;
			this.repaint(); //on demande a reafficher le panenl mis a jour
		}
		public void paint(Graphics g)
		{
			g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
	
	
	//CONSTRUCTEUR
	public MenuCouleurs(int nbJoueurs, int nbIA)
	{
		//on intialise la fenetre
		super((JFrame)null, "KingDomino", true);
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//on charge les textures des chateaux
		for(String couleur : choixCouleurs)
		{
			try
			{
				chateaux.put(couleur, ImageIO.read(new File(couleur + ".png")));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		//on remplit la fenetre
		
		//on cree un contenneur qui contiendra la liste de tous les joueurs
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		
		for(int i = 0; i < nbJoueurs + nbIA; i++) //pour chaque joueur/IA
		{
			//on creee la liste deroulante avec le choix des couleurs
			couleurs.add(new JComboBox());
			couleurs.get(i).addItem("bleu");
			couleurs.get(i).addItem("jaune");
			couleurs.get(i).addItem("rose");
			couleurs.get(i).addItem("vert");
			couleurs.get(i).setSelectedIndex(i); //couleur par defaut selectionne (differente pour chaque joueur)
			
			//on ajoute une action si une couleur est modifiee (il faut mettre a jour l'image)
			couleurs.get(i).addActionListener(new ActionListener() 
			{				
				public void actionPerformed(ActionEvent e)
				{
					for(int i = 0; i < couleurs.size(); i++) 
					{
						if(e.getSource() == couleurs.get(i)) //on cherche quelle liste a ete modifiee, et a quelle image elle correspond
						{
							backgroundColors.get(i).setBackgroundImage(chateaux.get(couleurs.get(i).getSelectedItem().toString()));	
						}
					}
				}
			});
			
			//on cree l'image correspondante a la couleur
			backgroundColors.add(new BackgroundPanel(chateaux.get(couleurs.get(i).getSelectedItem().toString())));
			backgroundColors.get(i).setPreferredSize(new Dimension(90, 90));
			
			//on cree la legende
			JLabel lab = new JLabel("couleur : ");
			
			//on cree le conteneur de ces 3 elements
			JPanel panCouleur = new JPanel();
			panCouleur.setBackground(Color.white);
			panCouleur.setPreferredSize(new Dimension(160, 160));
			
			//on ajoute tous les elements au conteneur
			panCouleur.add(lab);
			panCouleur.add(couleurs.get(i));
			panCouleur.add(backgroundColors.get(i));
			
			if(i < nbJoueurs) //il s'agit d'un joueur
				panCouleur.setBorder(BorderFactory.createTitledBorder("Joueur " + (i + 1)));
			else //il s'agit d'une IA
				panCouleur.setBorder(BorderFactory.createTitledBorder("IA " + (i + 1 - nbJoueurs)));
			
			//ajout au conteneur qui contient tous les joueurs
			content.add(panCouleur);
		}

		
		//ajout du bouton pour lancer la partie
		JButton butJouer = new JButton("Lancer la partie");
		
		//ajout d'une action associee au bouton : on verifie les valeurs et on quitte le menu
		butJouer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if(checkValues())
				{
					jouer = true;
					setVisible(false);
				}
			}
		});
		
		//on ajoute les conteneur au conteneur de la fenetre
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(butJouer, BorderLayout.SOUTH);
	}
	
	
	//METHODES PUBLIQUES
	
	//methode pour afficher le menu, qui retourne true si le joueur a lance la partie ou false s'il a quitte le menu
	public boolean display()
	{
		this.setVisible(true);
		return jouer;
	}
	
	//methode pour recuperer les couleurs choisies par les joueurs
	public ArrayList<String> getCouleurs()
	{
		ArrayList<String> couleursChoisies = new ArrayList<String>();
		for(int i = 0; i < couleurs.size(); i++)
		{
			couleursChoisies.add(couleurs.get(i).getSelectedItem().toString());
		}
		return couleursChoisies;
	}
	
	
	//METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE
	
	//methode qui verifie que les valeurs entrees soient correctes avant de lancer la partie
	private boolean checkValues()
	{
		HashSet<String> couleursChoisies = new HashSet<String>();
		for(int i = 0; i < couleurs.size(); i++)
		{
			//on ajoute les couleurs dans un set, si une couleur est deja presente, la methode add retourne faux
			if(couleursChoisies.add(couleurs.get(i).getSelectedItem().toString()) == false)
			{
				JOptionPane message = new JOptionPane();
				//affiche un message d'erreur pour avertir l'utilisateur
				message.showMessageDialog(null, "Deux joueurs ne peuvent pas avoir la meme couleur", "impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}
}