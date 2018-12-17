import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class FinDePartie extends JFrame {

	private JPanel page;

	public void addComponentsToPane(Container pane, int[] score, int[] couleur, String[] nom) {

		int[][] infos = new int[3][4];
		for (int i = 0; i < score.length; i++) {
			infos[0][i] = score[i];
			infos[1][i] = couleur[i];
			// infos[2][i] = nom[i];
		}
		// test
		infos[0][1] = 10;

		System.out.println("start");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.println(infos[i][j]);
			}
		}

		for (int i = 0; i < infos.length; i++) {
			Arrays.sort(infos[i]);
		}

		System.out.println("start 2");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.println(infos[i][j]);
			}
		}

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 6;
		c.weightx = 0.2;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 10, 10);

		String Regles = "";
		if (this.modeDynastie)
			Regles = Regles + "Dynastie<br>";
		if (this.modeHarmonie)
			Regles = Regles + "Harmonie<br>";
		if (this.modeGrandDuel)
			Regles = Regles + "Grand Duel<br>";
		if (this.modeEmpireMilieu)
			Regles = Regles + "Empire du Milieu<br>";
		if (!(this.modeDynastie && this.modeHarmonie && this.modeGrandDuel && this.modeEmpireMilieu))
			Regles = Regles + "Aucune<br>";

		long durationInMillis = this.Time;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long seconds = (durationInMillis - (1000 * 60) * minute) / 1000;
		String TempsDeJeu = Long.toString(minute) + " min " + Long.toString(seconds) + " sec";

		String text = "<html> <u>Participants</u>" + "<br> " + String.valueOf(this.nbJoueurs) + " Joueurs" + "<br> "
				+ String.valueOf(this.nbIA) + " IA<br>" + "<br> <u>Regles actives</u>" + "<br>" + Regles
				+ "<br> <u>Temps de Jeu </u>" + "<br>" + TempsDeJeu;

		JLabel briefing = new JLabel("<html><div style='text-align: center;'>" + text);

		briefing.setFont(new Font("Book Antiqua", Font.BOLD, 35));
		briefing.setLayout(new BorderLayout());

		Border border = BorderFactory.createMatteBorder(20, 20, 20, 20, Color.BLUE);
		Font font = new Font("Book Antiqua", Font.BOLD, 45);
		Border thatBorder = new TitledBorder(border, " Briefing ", TitledBorder.CENTER, TitledBorder.TOP, font,
				Color.BLUE);
		briefing.setBorder(thatBorder);

		Border margin = new EmptyBorder(30, 30, 30, 30);
		briefing.setBorder(new CompoundBorder(thatBorder, margin));

		briefing.setVerticalAlignment(SwingConstants.CENTER);
		briefing.setHorizontalAlignment(SwingConstants.CENTER);
		pane.add(briefing, c);

		addPodium(this.nbTotal, score, c, pane);
		addMenuFinal(c, pane);

	}

	private void addPodium(int N, int[] score, GridBagConstraints c, Container pane) {

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridheight = 4;
		c.gridwidth = 1;

		for (int i = 0; i < N; i++) {

			c.gridx = i + 1;
			c.gridy = 0;
			String text = "<html> Joueur " + Integer.toString(i + 1) + "<br>Score : " + score[i];
			addAPodium(text, pane, c, true, Color.GREEN);

		}

		c.gridwidth = 2;
		c.weightx = 0.4;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.BOTH;

	}

	private void addMenuFinal(GridBagConstraints c, Container pane) {

		// Bouton rejouer
		c.gridx = 1;
		c.gridy = 4;
		JButton Rejouer = new JButton("Rejouer");
		Rejouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("replay");
				rejouer = true;
				hasDecided = true;
			}
		});
		Rejouer.setBorderPainted(false);
		Rejouer.setFocusPainted(false);
		Rejouer.setFont(new Font("Book Antiqua", Font.PLAIN, 35));
		Rejouer.setBackground(new Color(127, 255, 0));
		pane.add(Rejouer, c);

		// Bouton quitter
		c.gridx = 3;
		c.gridy = 4;
		JButton Quitter = new JButton("Quitter");
		Quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("");
				System.out.println("quit");
				rejouer = false;
				hasDecided = true;
			}
		});
		Quitter.setBorderPainted(false);
		Quitter.setFocusPainted(false);
		Quitter.setFont(new Font("Book Antiqua", Font.PLAIN, 35));
		Quitter.setBackground(new Color(255, 99, 71));
		pane.add(Quitter, c);
	}

	private static void addAPodium(String text, Container container, GridBagConstraints c, boolean B, Color color) {

		JLabel button = new JLabel(text);
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
		button.setBackground(color);
		button.setVerticalAlignment(SwingConstants.CENTER);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(button, c);

	}

	// ATTRIBUTS PRIVES
	private int nbJoueurs, nbIA, nbTotal;
	private boolean modeDynastie, modeEmpireMilieu, modeHarmonie, modeGrandDuel;
	private long Time;
	
	private boolean rejouer;
	private boolean hasDecided = false;
	
	JFrame FenetreFinale;
	JButton Quitter, Recommencer;

	// CONSTRUCTEUR
	public FinDePartie(Parametres parametres, ArrayList<Integer> couleurs, int[] score, long Time,
			ArrayList<String> noms2) {

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Game is finished !");
		this.setVisible(true);
		this.setResizable(false);
		this.Time = Time;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screenSize.getWidth() / 100;
		int y = (int) screenSize.getHeight() / 100;

		this.setSize(70 * x, 85 * y);
		this.setLocationRelativeTo(null);

		this.nbJoueurs = parametres.nbJoueurs;
		this.nbIA = parametres.nbIA;
		this.nbTotal = parametres.nbTotal;
		this.modeDynastie = parametres.modeDynastie;
		this.modeEmpireMilieu = parametres.modeEmpireMilieu;
		this.modeHarmonie = parametres.modeHarmonie;
		this.modeGrandDuel = parametres.modeGrandDuel;

		String[] nom = new String[4];
		int[] couleur = new int[4];

		for (int i = 0; i < this.nbTotal; i++) {

			couleur[i] = couleurs.get(i);
			nom[i] = noms2.get(i);

			System.out.println("   Joueur " + (i + 1) + " :");
			System.out.println("   couleur = " + score[i]);
			System.out.println("   score = " + couleur[i]);
			System.out.println("   nom = " + nom[i]);
		}

		FenetreFinale = new JFrame();
		FenetreFinale.setLayout(new GridBagLayout());
		FenetreFinale.setResizable(false);
		addComponentsToPane(this, score, couleur, nom);

	}

	public int getIndexOfLargest(int[] array) {
		if (array == null || array.length == 0)
			return -1;

		int largest = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > array[largest])
				largest = i;
		}
		return largest;
	}
	
	public boolean souhaiteRejouer()
	{
		return rejouer;
	}
	
	public boolean hasDecided()
	{
		return hasDecided;
	}

}