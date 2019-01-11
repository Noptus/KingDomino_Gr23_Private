import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

	public int NB;

	public void addComponentsToPane(Container pane, int[] score, int[] couleur, String[] nom,
			ArrayList<Integer> couleurs) {

		Object[][] infos = new Object[5][5];
		for (int i = 0; i < NB; i++) {
			infos[0][i] = score[i];
			infos[1][i] = couleur[i];
			infos[2][i] = nom[i];
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

		Border border = BorderFactory.createMatteBorder(20, 20, 20, 20, Color.BLACK);
		Font font = new Font("Book Antiqua", Font.BOLD, 45);
		Border thatBorder = new TitledBorder(border, " Briefing ", TitledBorder.CENTER, TitledBorder.TOP, font,
				Color.BLACK);
		briefing.setBorder(thatBorder);

		Border margin = new EmptyBorder(50, 30, 50, 30);
		briefing.setBorder(new CompoundBorder(thatBorder, margin));

		briefing.setVerticalAlignment(SwingConstants.CENTER);
		briefing.setHorizontalAlignment(SwingConstants.CENTER);
		pane.add(briefing, c);

		int Max = 0;
		for (int i = 0; i < NB; i++) {
			if (score[i] > Max)
				Max = score[i];
		}
		System.out.println("Nombre de joueurs : " + NB);

		int[] ScoreSafe = new int[4];
		for (int i = 0; i < score.length; i++) {
			ScoreSafe[i] = score[i];
		}

		System.out.println("max index for score :" + findMaxIndex(score));
		int first = 0;
		int second = 0;
		int third = 0;
		int fourth = 0;

		first = findMaxIndex(score);
		System.out.println(nom[first] + " est premier !");
		
		if (NB == 2) {

			first = findMaxIndex(score);
			if (first == 0) {
				second = 1;
			}
			addPodium(this.nbTotal, ScoreSafe[second], c, pane, nom[second], 0, couleur[second], Max,false);
			addPodium(this.nbTotal, ScoreSafe[first], c, pane, nom[first], 1, couleur[first], Max, true);

		}

		if (NB == 3) {

			first = findMaxIndex(score);
			score[first] = 0;
			second = findMaxIndex(score);
			score[second] = 0;
			third = findMaxIndex(score);

			addPodium(this.nbTotal, ScoreSafe[first], c, pane, nom[first], 1, couleur[first], Max, true);
			addPodium(this.nbTotal, ScoreSafe[second], c, pane, nom[second], 0, couleur[second], Max,false);
			addPodium(this.nbTotal, ScoreSafe[third], c, pane, nom[third], 2, couleur[third], Max,false);

		}

		if (NB == 4) {

			first = findMaxIndex(score);
			score[first] = 0;
			second = findMaxIndex(score);
			score[second] = 0;
			third = findMaxIndex(score);
			score[third] = 0;
			fourth = findMaxIndex(score);

			addPodium(this.nbTotal, ScoreSafe[first], c, pane, nom[first], 0, couleur[first], Max, true);
			addPodium(this.nbTotal, ScoreSafe[second], c, pane, nom[second], 1, couleur[second], Max,false);
			addPodium(this.nbTotal, ScoreSafe[third], c, pane, nom[third], 2, couleur[third], Max,false);
			addPodium(this.nbTotal, ScoreSafe[fourth], c, pane, nom[fourth], 3, couleur[fourth], Max,false);

		}

		System.out.println("ScoreSafe");
		System.out.println(ScoreSafe[first]);
		System.out.println(ScoreSafe[second]);
		System.out.println(ScoreSafe[third]);

		addMenuFinal(c, pane);

	}

	private int findMaxIndex(int[] arr) {
		int max = arr[0];
		int maxIdx = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
				maxIdx = i;
			}
		}
		return maxIdx;
	}

	private void addPodium(int N, int score, GridBagConstraints c, Container pane, String nom, int i, int couleur,
			int max, boolean premier) {

		c.fill = GridBagConstraints.BELOW_BASELINE;
		c.gridheight = 4;
		c.gridwidth = 1;

		c.gridx = i + 1;
		c.gridy = 0;
		
		String text2 = "";
		if( premier ) {
			text2 = "<html> Est le King ! <br>";
		}
		String text = "<html> <center>" + nom + "<br>" + text2+score + " points</center>";

		Color colorBorder = new Color(0, 0, 0);
		switch (couleur) {
		case 17:
			colorBorder = Color.BLUE;
			break;
		case 27:
			colorBorder = Color.YELLOW;
			break;
		case 37:
			colorBorder = Color.GREEN;
			break;
		case 47:
			colorBorder = Color.PINK;
			break;
		}

		addAPodium(text, pane, c, true, colorBorder, score, max);

	}

	private static void addAPodium(String text, Container container, GridBagConstraints c, boolean B, Color color,
			int score, int max) {

		JPanel podiumBas = new JPanel(new GridLayout(2, 2));

		JLabel button = new JLabel(text);

		double M = (double) 250 / max * score * 1.5;
		int M2 = (int) M;
		System.out.println("M :" + M2);

		Border border = BorderFactory.createMatteBorder(20, 20, 20, 20, color);
		button.setBorder(border);

		button.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
		button.setBackground(color);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setVerticalAlignment(SwingConstants.BOTTOM);

		Border margin = new EmptyBorder(30, 30, M2, 30);
		button.setBorder(new CompoundBorder(border, margin));

		// podiumBas.add(button, 1);
		container.add(button, c);

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
		NB = parametres.nbTotal;

		this.setSize(95 * x, 85 * y);
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
			// score[i] = score.get(i);
			// System.out.println(" Joueur " + (i + 1) + " :");
			// System.out.println(" couleur = " + score[i]);
			// System.out.println(" score = " + couleur[i]);
			// System.out.println(" nom = " + nom[i]);
		}

		FenetreFinale = new JFrame();
		FenetreFinale.setLayout(new GridBagLayout());
		FenetreFinale.setResizable(false);
		addComponentsToPane(this, score, couleur, nom, couleurs);

	}

	public static void updateGame(Graphics g) {

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

	public boolean souhaiteRejouer() {
		return rejouer;
	}

	public boolean hasDecided() {
		return hasDecided;
	}

}
