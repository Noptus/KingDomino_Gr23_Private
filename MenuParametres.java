import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class MenuParametres extends JDialog {
	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)

	// ces attributs sont ceux qui nous permettent de recuperer les valeurs
	private JComboBox nbJoueurs, nbIA;
	private JCheckBox dynastie, empireMilieu, harmonie, grandDuel;
	private boolean jouer = false;
	
	// CONSTRUCTEUR
	public MenuParametres() {
		// on intialise la fenetre
		super((JFrame) null, " KingDomino", true); // appel du constructeur de la classe mere
		this.setSize(400, 400); // taille de la fentre
		this.setLocationRelativeTo(null); // centre au milieu de l'ecrann
		this.setResizable(false); // pas redimmensionnable
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // quand on clique sur la croix, quitte la fenetre

		// on remplit la fenetre

		// logo du jeu
		JLabel labImage = new JLabel(new ImageIcon("images//kingdomino.jpg"));

		// liste deroutante pour choisir nb de joueurs
		JPanel panNbJoueurs = new JPanel();
		panNbJoueurs.setBackground(Color.white);
		panNbJoueurs.setPreferredSize(new Dimension(150, 70));
		panNbJoueurs.setBorder(BorderFactory.createTitledBorder("Joueurs"));
		nbJoueurs = new JComboBox();
		nbJoueurs.addItem("0");
		nbJoueurs.addItem("1");
		nbJoueurs.addItem("2");
		nbJoueurs.addItem("3");
		nbJoueurs.addItem("4");
		nbJoueurs.setSelectedIndex(0);
		JLabel labNbJoueurs = new JLabel("nombre : ");
		panNbJoueurs.add(labNbJoueurs);
		panNbJoueurs.add(nbJoueurs);

		// liste deroulante pour choisir le nb d'IA
		JPanel panNbIA = new JPanel();
		panNbIA.setBackground(Color.white);
		panNbIA.setPreferredSize(new Dimension(150, 70));
		panNbIA.setBorder(BorderFactory.createTitledBorder("IA"));
		nbIA = new JComboBox();
		nbIA.addItem("0");
		nbIA.addItem("1");
		nbIA.addItem("2");
		nbIA.addItem("3");
		nbIA.addItem("4");
		nbIA.setSelectedIndex(2);
		JLabel labNbIA = new JLabel("nombre : ");
		panNbIA.add(labNbIA);
		panNbIA.add(nbIA);

		// caces a cocher pour activer des regles additionnelles
		JPanel panReglesAdditionnelles = new JPanel();
		panReglesAdditionnelles.setBackground(Color.white);
		panReglesAdditionnelles.setPreferredSize(new Dimension(300, 90));
		panReglesAdditionnelles.setBorder(BorderFactory.createTitledBorder("Regles additionnelles"));
		dynastie = new JCheckBox("dynastie");
		empireMilieu = new JCheckBox("empire du milieu");
		harmonie = new JCheckBox("harmonie");
		grandDuel = new JCheckBox("grand duel");
		JLabel labReglesAdditionnelles = new JLabel("activer : ");
		panReglesAdditionnelles.add(labReglesAdditionnelles);
		panReglesAdditionnelles.add(dynastie);
		panReglesAdditionnelles.add(empireMilieu);
		panReglesAdditionnelles.add(harmonie);
		panReglesAdditionnelles.add(grandDuel);

		// on ajoute les 3 elements au conteneur central (pour la mise en page)
		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNbJoueurs);
		content.add(panNbIA);
		content.add(panReglesAdditionnelles);

		// creation du bouton pour lancer la partie
		JButton butJouer = new JButton("Lancer la partie");
		
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
		return jouer; // quand on quitte la fenetre, on retourne la valeur du booléen jouer
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
			message.showMessageDialog(null, "le nombre de joueurs doit etre compris entre 2 et 4",
					"impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if(total != 2 && getModeDynastie() == true){
			JOptionPane message = new JOptionPane();
			// affiche un message d'erreur pour avertir l'utilisateur
			message.showMessageDialog(null, "le mode dynastie ne peut etre jouer qu'a 2",
					"impossible de lancer la partie", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

}
