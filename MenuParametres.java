import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class MenuParametres extends JDialog {
	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)

	// ces attributs sont ceux qui nous permettent de recuperer les valeurs
	private JComboBox nbJoueurs, nbIA;
	private JCheckBox dynastie, empireMilieu, harmonie, grandDuel;
	private JCheckBox musique, effets;
	private boolean jouer = false;

	// CONSTRUCTEUR
	public MenuParametres() {

		// on intialise la fenetre
		super((JFrame) null, " ", true); // appel du constructeur de la classe mere

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screenSize.getWidth() / 100;
		int y = (int) screenSize.getHeight() / 100;
		this.setSize(31 * x, 28 * x); // taille de la fentre
		this.setLocationRelativeTo(null); // centre au milieu de l'ecrann
		this.setResizable(false); // pas redimmensionnable
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // quand on clique sur la croix, quitte la fenetre

		// on remplit la fenetre

		// logo du jeu
		JLabel labImage = new JLabel(new ImageIcon("images//kingdomino.png"));
		Font font = new Font("Book Antiqua", Font.PLAIN, 20);
		Font Mfont = new Font("Book Antiqua", Font.PLAIN, 35);
		Font Lfont = new Font("Book Antiqua", Font.BOLD, 25);


		Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK);
		Border blueborder = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE);
		Border redborder = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED);

		// liste deroutante pour choisir nb de joueurs
		JPanel panNbJoueurs = new JPanel();
		panNbJoueurs.setBackground(Color.white);
		panNbJoueurs.setFont(font);
		panNbJoueurs.setPreferredSize(new Dimension(14*x, 5*x));
		panNbJoueurs.setBorder(BorderFactory.createTitledBorder(blueborder, " Joueurs ", TitledBorder.LEFT, TitledBorder.TOP,
				Lfont, Color.BLUE));
		nbJoueurs = new JComboBox();
		nbJoueurs.setFont(font);
		nbJoueurs.addItem("0");
		nbJoueurs.addItem("1");
		nbJoueurs.addItem("2");
		nbJoueurs.addItem("3");
		nbJoueurs.addItem("4");
		nbJoueurs.setSelectedIndex(0);
		JLabel labNbJoueurs = new JLabel(" Effectif : ");
		labNbJoueurs.setFont(font);
		panNbJoueurs.add(labNbJoueurs);
		panNbJoueurs.add(nbJoueurs);

		// liste deroulante pour choisir le nb d'IA
		JPanel panNbIA = new JPanel();
		panNbIA.setBackground(Color.white);
		panNbIA.setPreferredSize(new Dimension(14*x, 5*x));
		panNbIA.setBorder(BorderFactory.createTitledBorder(redborder, " IA ", TitledBorder.LEFT, TitledBorder.TOP, Lfont,
				Color.RED));
		panNbIA.setFont(font);
		nbIA = new JComboBox();
		nbIA.setFont(font);
		nbIA.addItem("0");
		nbIA.addItem("1");
		nbIA.addItem("2");
		nbIA.addItem("3");
		nbIA.addItem("4");
		nbIA.setSelectedIndex(4);
		JLabel labNbIA = new JLabel("nombre : ");
		labNbIA.setFont(font);
		panNbIA.add(labNbIA);
		panNbIA.add(nbIA);

		// caces a cocher pour activer des regles additionnelles
		JPanel panReglesAdditionnelles = new JPanel();
		panReglesAdditionnelles.setBackground(Color.white);
		panReglesAdditionnelles.setPreferredSize(new Dimension(25*x, 7*x));
		panReglesAdditionnelles.setFont(font);
		panReglesAdditionnelles.setBorder(BorderFactory.createTitledBorder(border, " Regles aditionelles ", TitledBorder.CENTER, TitledBorder.TOP, Lfont,
				Color.BLACK));
		dynastie = new JCheckBox(" Dynastie ");
		dynastie.setFont(font);
		empireMilieu = new JCheckBox(" Empire du milieu ");
		empireMilieu.setFont(font);
		harmonie = new JCheckBox(" Harmonie ");
		harmonie.setFont(font);
		grandDuel = new JCheckBox(" Grand duel ");
		grandDuel.setFont(font);
		JLabel labReglesAdditionnelles = new JLabel(" Activer : ");
		labReglesAdditionnelles.setFont(font);
		panReglesAdditionnelles.add(labReglesAdditionnelles);
		panReglesAdditionnelles.add(dynastie);
		panReglesAdditionnelles.add(empireMilieu);
		panReglesAdditionnelles.add(harmonie);
		panReglesAdditionnelles.add(grandDuel);

		JPanel panAudio = new JPanel();
		panAudio.setBackground(Color.WHITE);
		panAudio.setPreferredSize(new Dimension(300, 60));
		panAudio.setBorder(BorderFactory.createTitledBorder("Audio"));
		musique = new JCheckBox("musique");
		//musique.setSelected(true);
		effets = new JCheckBox("effets");
		//effets.setSelected(true);
		JLabel labAudio = new JLabel("activer :");

		panAudio.setPreferredSize(new Dimension(25*x, 5*x));
		panAudio.setBorder(BorderFactory.createTitledBorder(border, " Audio ", TitledBorder.CENTER, TitledBorder.TOP, Lfont,
				Color.BLACK));
		musique = new JCheckBox(" Musique ");
		musique.setSelected(true);
		musique.setFont(font);
		effets = new JCheckBox(" Effets sonores ");
		effets.setSelected(true);
		effets.setFont(font);
		labAudio.setFont(font);

		panAudio.add(labAudio);
		panAudio.add(musique);
		panAudio.add(effets);

		// on ajoute les 4 elements au conteneur central (pour la mise en page)
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNbJoueurs);
		content.add(panNbIA);
		content.add(panReglesAdditionnelles);
		content.add(panAudio);

		// creation du bouton pour lancer la partie
		JButton butJouer = new JButton("Lancer la partie");
		butJouer.setFont(Mfont);
		
		// ajout d'une action associee au bouton : on verifie les valeurs et on quitte
		// le menu
		butJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkValues()) // si les valeurs sont conformes, on peut lancer le jeu
				{
					jouer = true;
					setVisible(false); // quitter la fenetre
				}
			}
		});

		// on ajoute l'image, le conteneur central et le bouton au conteneur de la
		// fenetre
		this.getContentPane().setBackground(Color.white);
		this.getContentPane().add(labImage, BorderLayout.NORTH);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(butJouer, BorderLayout.SOUTH);
	}

	// METHODES PUBLIQUES

	// methode pour afficher le menu, qui retourne true si le joueur a lance la
	// partie ou false s'il a quitte le menu
	public boolean display() {
		this.setVisible(true); // afficher la fenetre
		return jouer; // quand on quitte la fenetre, on retourne la valeur du boolÃ©en jouer
	}

	// methodes pour recuperer les valeurs entrees par l'utilisateur
	public int getNbJoueurs() {
		return Integer.valueOf(nbJoueurs.getSelectedItem().toString());
	}

	public int getNbIA() {
		return Integer.valueOf(nbIA.getSelectedItem().toString());
	}

	public boolean getModeDynastie() {
		return dynastie.isSelected();
	}

	public boolean getModeEmpireMilieu() {
		return empireMilieu.isSelected();
	}

	public boolean getModeHarmonie() {
		return harmonie.isSelected();
	}

	public boolean getModeGrandDuel() {
		return grandDuel.isSelected();
	}

	public boolean getMusique() {
		return musique.isSelected();
	}

	public boolean getEffets() {
		return effets.isSelected();
	}

	// retoure toutes les valeurs dans la structure Parametres
	public Parametres getParametres() {
		return new Parametres(getNbJoueurs(), getNbIA(), getModeDynastie(), getModeEmpireMilieu(), getModeHarmonie(),
				getModeGrandDuel());
	}

	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// methode qui verifie que les valeurs entrees soient correctes avant de lancer
	// la partie
	private boolean checkValues() {
		int total = getNbJoueurs() + getNbIA();
		if (total < 2 || total > 4) {
			JOptionPane message = new JOptionPane();
			// affiche un message d'erreur pour avertir l'utilisateur
			message.showMessageDialog(null, "Le nombre de joueurs doit etre compris entre 2 et 4",
					"Impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (total != 2 && getModeGrandDuel() == true) {
			JOptionPane message = new JOptionPane();
			// affiche un message d'erreur pour avertir l'utilisateur
			message.showMessageDialog(null, "Le mode grand duel ne peut etre jouer qu'a 2",
					"Impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

}
