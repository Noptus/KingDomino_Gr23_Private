import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class MenuCouleurs extends JDialog {
	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)

	// ces attributs sont ceux qui nous permettent de recuperer les valeurs
	private ArrayList<JComboBox> couleurs = new ArrayList<JComboBox>();

	private ArrayList<JTextField> noms = new ArrayList<JTextField>();

	private boolean jouer = false;

	// stocke les fonds d'ecran pour mettre a jour l'image (quand la couleur change)
	private ArrayList<BackgroundPanel> backgroundColors = new ArrayList<BackgroundPanel>();

	// stocke les textures des chateaux pour les afficher a cote de la couleur
	// choisie par le joueur
	private HashMap<String, Image> chateaux = new HashMap<String, Image>();

	// correspondance entre les couleurs en chaine de caracteres et leur numero d'
	// images
	private HashMap<String, Integer> correspondanceCouleurs = new HashMap<String, Integer>();

	// CLASSE INTERNE

	// on creee une classe JPanel custom qui affiche une image qui peut etre mise a
	// jour
	class BackgroundPanel extends JPanel {
		private Image background;

		public BackgroundPanel(Image background) {
			this.background = background;
		}

		public void setBackgroundImage(Image background) {
			this.background = background;
			this.repaint(); // on demande a reafficher le panenl mis a jour
		}

		public void paint(Graphics g) {
			g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	// CONSTRUCTEUR
	public MenuCouleurs(int nbJoueurs, int nbIA) {
		// on intialise la fenetre
		super((JFrame) null, "KingDomino", true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screenSize.getWidth() / 100;
		int y = (int) screenSize.getHeight() / 100;
		this.setSize(31*x, 32*x);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		correspondanceCouleurs.put("Bleu", 17);
		correspondanceCouleurs.put("Jaune", 27);
		correspondanceCouleurs.put("Rose", 37);
		correspondanceCouleurs.put("Vert", 47);
		
		Font font = new Font("Book Antiqua", Font.PLAIN, 20);
		Font Mfont = new Font("Book Antiqua", Font.PLAIN, 35);
		Font Lfont = new Font("Book Antiqua", Font.BOLD, 25);
		
		// on charge les textures des chateaux
		for (String couleur : correspondanceCouleurs.keySet()) {
			try {
				chateaux.put(couleur,
						ImageIO.read(new File("images//" + correspondanceCouleurs.get(couleur) + ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// on remplit la fenetre

		// on cree un contenneur qui contiendra la liste de tous les joueurs
		JPanel content = new JPanel();
		content.setBackground(Color.white);

		for (int i = 0; i < nbJoueurs + nbIA; i++) // pour chaque joueur/IA
		{
			// on creee la liste deroulante avec le choix des couleurs
			JComboBox jcb = new JComboBox();
			jcb.setFont(font);
			couleurs.add(jcb);
			couleurs.get(i).addItem("Bleu");
			couleurs.get(i).addItem("Jaune");
			couleurs.get(i).addItem("Rose");
			couleurs.get(i).addItem("Vert");
			couleurs.get(i).setSelectedIndex(i); // couleur par defaut selectionne (differente pour chaque joueur)

			// on ajoute une action si une couleur est modifiee (il faut mettre a jour
			// l'image)
			couleurs.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < couleurs.size(); i++) {
						if (e.getSource() == couleurs.get(i)) // on cherche quelle liste a ete modifiee, et a quelle
																// image elle correspond
						{
							backgroundColors.get(i)
									.setBackgroundImage(chateaux.get(couleurs.get(i).getSelectedItem().toString()));
						}
					}
				}
			});

			// on cree l'image correspondante a la couleur
			backgroundColors.add(new BackgroundPanel(chateaux.get(couleurs.get(i).getSelectedItem().toString())));
			backgroundColors.get(i).setPreferredSize(new Dimension(5*x, 5*x));

			// on cree la legende
			JLabel labCouleur = new JLabel(" Couleur ");
			labCouleur.setFont(Lfont);
			// on cree le conteneur de ces 3 elements
			JPanel panCouleur = new JPanel();
			panCouleur.setBackground(Color.white);
			panCouleur.setPreferredSize(new Dimension(14*x, 13*x));

			String text;
			if (i < nbJoueurs)
				text = "Joueur " + (i + 1);
			else
				text = "IA " + (i + 1 - nbJoueurs);

			JLabel labNom = new JLabel(" Nom ");
			labNom.setFont(Lfont);
			JTextField jtf = new JTextField(text);
			jtf.setFont(font);
			noms.add(jtf);
			noms.get(i).setPreferredSize(new Dimension(7*x, 2*x));

			// on ajoute tous les elements au conteneur
			panCouleur.add(labNom);
			panCouleur.add(noms.get(i));
			panCouleur.add(labCouleur);
			panCouleur.add(couleurs.get(i));
			panCouleur.add(backgroundColors.get(i));
			Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK);

			TitledBorder tb = new TitledBorder(border, " "+text+" ", TitledBorder.LEFT, TitledBorder.TOP,
					Lfont, Color.BLACK);
			panCouleur.setBorder(tb);

			// ajout au conteneur qui contient tous les joueurs
			content.add(panCouleur);
		}

		// ajout du bouton pour lancer la partie
		JButton butJouer = new JButton("Lancer la partie");
		butJouer.setFont(Mfont);
		// ajout d'une action associee au bouton : on verifie les valeurs et on quitte
		// le menu
		butJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkValues()) {
					jouer = true;
					setVisible(false);
				}
			}
		});

		// on ajoute les conteneur au conteneur de la fenetre
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(butJouer, BorderLayout.SOUTH);
	}

	// METHODES PUBLIQUES

	// methode pour afficher le menu, qui retourne true si le joueur a lance la
	// partie ou false s'il a quitte le menu
	public boolean display() {
		this.setVisible(true);
		System.out.println("Salut");
		return jouer;
	}

	// methode pour recuperer les couleurs choisies par les joueurs
	public ArrayList<Integer> getCouleurs() {
		ArrayList<Integer> couleursChoisies = new ArrayList<Integer>();
		for (int i = 0; i < couleurs.size(); i++) {
			couleursChoisies.add(correspondanceCouleurs.get(couleurs.get(i).getSelectedItem().toString()));
		}
		return couleursChoisies;
	}

	public ArrayList<String> getNoms() {
		ArrayList<String> noms = new ArrayList<String>();
		for (int i = 0; i < this.noms.size(); i++) {
			noms.add(this.noms.get(i).getText());
		}
		return noms;
	}

	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// methode qui verifie que les valeurs entrees soient correctes avant de lancer
	// la partie
	private boolean checkValues() {
		HashSet<String> couleursChoisies = new HashSet<String>();
		for (int i = 0; i < couleurs.size(); i++) {
			// on ajoute les couleurs dans un set, si une couleur est deja presente, la
			// methode add retourne faux
			if (couleursChoisies.add(couleurs.get(i).getSelectedItem().toString()) == false) {
				JOptionPane message = new JOptionPane();
				// affiche un message d'erreur pour avertir l'utilisateur
				message.showMessageDialog(null, "Deux joueurs ne peuvent pas avoir la meme couleur",
						"Impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
				return false;
			} else if (noms.get(i).getText().length() > 20) {
				JOptionPane message = new JOptionPane();
				// affiche un message d'erreur pour avertir l'utilisateur
				message.showMessageDialog(null, "Les noms sont limites a 20 caracteres",
						"Impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
