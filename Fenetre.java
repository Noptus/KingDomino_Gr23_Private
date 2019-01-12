import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.Timer;
import java.awt.AlphaComposite;

public class Fenetre extends JFrame {

	// CLASSE INTERNE qui represente une case de notre damier
	class Case extends JButton {
		private Image halfDomino;
		private int x;
		private int y;
		boolean temporary = false;

		public Case(Image texture, int x, int y) {
			this.halfDomino = texture;
			this.x = x;
			this.y = y;
			if (isFull() == true)
				this.setBorderPainted(false);
			this.setOpaque(false);
		}

		public boolean isFull() {
			return halfDomino != textures.get(0);
		}

		public int[] get() {
			return new int[] { this.x, this.y };
		}

		public void set(Image texture, boolean temporary) {
			this.halfDomino = texture;
			this.temporary = temporary;
			this.setBorderPainted(false);
			this.repaint(); // on demande a reafficher le panel mis a jour
		}

		public void reset() {
			this.halfDomino = textures.get(0);
			this.temporary = false;
			this.setBorderPainted(true);
			this.repaint(); // on demande a reafficher le panel mis a jour
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, temporary ? 0.5f : 1f));
			g.drawImage(halfDomino, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	// CLASSE INTERNE qui represente un domino de notre pioche
	class Domino extends JButton {
		private Image halfDomino1;
		private Image halfDomino2;
		private Image pionJoueur;

		private int[] domino;
		private boolean highlight = false;
		private boolean crossed = false;

		public Domino(int[] domino, int couleur) {
			this.domino = domino;
			this.halfDomino1 = textures.get(domino[0] * 10 + domino[1]);
			this.halfDomino2 = textures.get(domino[2] * 10 + domino[3]);
			if (couleur != 0) {
				this.setBorderPainted(false);
				this.pionJoueur = textures.get(couleur + 1);
			} else
				this.pionJoueur = null;

		}

		public int[] get() {
			return domino;
		}

		private boolean nobodyOwns() {
			return pionJoueur == null;
		}

		public void setCouleur(int couleur) // inque l'appartenance du domino (pour la pioche du tour suivant)
		{
			this.setBorderPainted(false);
			this.pionJoueur = textures.get(couleur + 1);
			;
		}

		public void setHighlight(boolean highlight) // insique si le domino doit etre encadre en rouge (domino a jouer)
		{
			this.highlight = highlight;
		}

		public void setCrossed(boolean crossed)// insique si le domino doit etre barre en rouge (domino deja choisie)
		{
			this.crossed = crossed;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			int thickness = 6;
			Stroke save = g2d.getStroke(); // on sauvegarde l'ancienne methode de rendu pour ne pas influer sur les
											// autres affichages du programme (tous les traits auraient une epaisseur de
											// 6 sinon)
			g2d.setStroke(new BasicStroke(thickness));

			g2d.drawImage(halfDomino1, 0, 0, this.getWidth() / 2, this.getHeight(), null); // premiere partie du domino
			g2d.drawImage(halfDomino2, this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight(), null); // deuxieme
																												// partie
																												// du
																												// domino

			if (highlight) {
				g2d.setColor(Color.RED);
				g2d.drawRect(thickness / 2, thickness / 2, this.getWidth() - thickness, this.getHeight() - thickness);
			} else if (crossed) {
				g2d.setColor(Color.RED);
				g2d.drawLine(10, 10, this.getWidth() - 10, this.getHeight() - 10);
			}

			if (nobodyOwns() == false) {
				g2d.drawImage(pionJoueur, this.getWidth() - 60, 10, 50, 50, null);
			}
			g2d.setStroke(save); // on restaure l'ancienne methode de rendu
		}
	}

	class Infos extends JPanel implements ActionListener {

		private Image[] textures = new Image[29];
		private int displayed = 0;
		private Timer timer = new Timer(50, this);
		private long last_frame = System.currentTimeMillis();
		private int animation = 0;

		public Infos(GridLayout g) {
			super(g);
			for (int i = 0; i <= 28; i++) {
				try {
					textures[i] = ImageIO.read(new File("images//frame_" + String.valueOf(i) + "_delay-0.05s.gif"));
				} catch (Exception e) {
				}
			}

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(textures[displayed], 0, 0, this.getWidth(), this.getHeight(), null);
		}

		public void actionPerformed(ActionEvent e) {
			switch (animation) {
			case 0:
				timer.stop();
				break;
			case 1:
				if (displayed == 28)
					animation = 0;
				else if (System.currentTimeMillis() - last_frame >= 50)
					displayed += 1;
				break;
			case 2:
				if (displayed == 14)
					animation = 0;
				else if (System.currentTimeMillis() - last_frame >= 50)
					displayed += 1;
				break;
			case 3:
				if (displayed == 28)
					animation = 0;
				else if (System.currentTimeMillis() - last_frame >= 50)
					displayed += 1;
				break;
			}
			if (System.currentTimeMillis() - last_frame >= 50)
				last_frame = System.currentTimeMillis();
			repaint();
		}

		public void setAnimation(int animation) {
			switch (animation) {
			case 1:
				displayed = 14;
				s.playAudio("Scroll");
				break;
			case 2:
				displayed = 0;
				break;
			case 3:
				displayed = 0;
				s.playAudio("Scroll2");
				break;
			}
			this.animation = animation;
			timer.start();
		}
	}

	// ATTRIBUTS PRIVES
	private HashMap<Integer, Image> textures = new HashMap<Integer, Image>();

	private int[] pos = new int[] { -1, -1, -1, -1 }; // 2 clics pour placer domino
	private int[] domino = null; // 1 clic pour choisir domino

	private Case[][][] cases;
	private Domino[] domino_manche;
	private Domino[] domino_manche_plus_1;

	private int joueur = 0;
	private ArrayList<String> noms;

	private JPanel[] pan_plateau;
	private JPanel pan_pioche_manche;
	private JPanel pan_pioche_manche_plus_1;
	private Infos pan_infos;
	private JPanel pan_legende_pioche;

	private Color couleur = new Color(75, 63, 60);
	private Color background_couleur = new Color(255, 197, 110);
	private Font police = new Font("Book Antiqua", Font.PLAIN, 40);

	private JLabel lab_action;
	private JLabel lab_manche;
	private JLabel lab_score;
	private JLabel[] lab_nom_score;

	private JButton but_vue;

	private boolean vueEnsemble = false;
	private int actionEnCours = 0;

	private Timer timer;

	private SoundPlayer s;

	// CONSTRUCTEUR
	public Fenetre(Plateau[] plateaux, Pioche pioche_initiale, ArrayList<Integer> couleurs, ArrayList<String> noms,
			SoundPlayer s) {

		this.s = s;
		this.noms = noms;

		for (int i = 0; i <= 48; i++) {
			try {
				textures.put(i, ImageIO.read(new File("images//" + i + ".png")));
			} catch (IOException e) {
			}
		}

		// on cree notre fenetre
		this.setTitle("KingDomino");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // affichage plein ecran du jeu, mais redimmensionnable par la
														// suite
		// initialise le label des noms et scores des joueurs pour la vue d'ensemble
		lab_nom_score = new JLabel[plateaux.length];
		for (int i = 0; i < lab_nom_score.length; i++) {
			lab_nom_score[i] = new JLabel(noms.get(i) + " : 0 points");
			lab_nom_score[i].setFont(police);
			lab_nom_score[i].setForeground(couleur);
			lab_nom_score[i].setHorizontalAlignment(JLabel.CENTER);
		}

		// plateaux de tous les joueurs
		pan_plateau = new JPanel[plateaux.length];
		cases = new Case[plateaux.length][plateaux[0].sizeX()][plateaux[0].sizeY()];
		for (int i = 0; i < pan_plateau.length; i++) {
			pan_plateau[i] = new JPanel();
			pan_plateau[i].setLayout(new GridLayout(plateaux[0].sizeX(), plateaux[0].sizeY(), 5, 5));
			for (int x = 0; x < plateaux[0].sizeX(); x++) {
				for (int y = 0; y < plateaux[0].sizeY(); y++) {
					cases[i][x][y] = new Case(
							textures.get(plateaux[i].getCouronne(x, y) * 10 + plateaux[i].getNature(x, y)), x, y);

					// on ajoute une action aux cases: celle de retourner l'indice correspondant
					cases[i][x][y].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (actionEnCours != 1) // le programme ne retient pas les cases si ce n'est pas l'action a
													// effectuer
								return;

							// on recupere la case qui a ete selectionnee
							Case source = (Case) e.getSource();

							// si la case est deja remplie, on ne peut pas la selectionner
							if (!source.isFull()) {
								int x = source.get()[0];
								int y = source.get()[1];

								// on stocke l'indice des cases dans le tableau
								if (pos[0] != -1) {
									pos[2] = x;
									pos[3] = y;
								} else {
									pos[0] = x;
									pos[1] = y;
								}
							}
						}
					});
					// on ajoute les cases au conteneur
					pan_plateau[i].add(cases[i][x][y]);
				}
			}
			pan_plateau[i].setBackground(background_couleur);
		}

		// legendes des pioches
		pan_legende_pioche = new JPanel(new GridLayout(1, 2));
		pan_legende_pioche.setBackground(background_couleur);
		JLabel lab_pioche_manche = new JLabel("Manche actuelle");
		lab_pioche_manche.setFont(police);
		lab_pioche_manche.setForeground(couleur);
		lab_pioche_manche.setHorizontalAlignment(JLabel.CENTER);
		pan_legende_pioche.add(lab_pioche_manche);

		JLabel lab_pioche_manche_plus_1 = new JLabel("Manche suivante");
		lab_pioche_manche_plus_1.setFont(police);
		lab_pioche_manche_plus_1.setForeground(couleur);
		lab_pioche_manche_plus_1.setHorizontalAlignment(JLabel.CENTER);
		pan_legende_pioche.add(lab_pioche_manche_plus_1);

		// meme s'il est vide, il faut l'initialiser
		pan_pioche_manche = new JPanel();
		pan_pioche_manche.setBackground(background_couleur);

		// PIOCHE INTIALE (qu'on definit comme etant celle de la manche plus 1 car elle
		// sera intervertit lors de l'update des pioche)
		pan_pioche_manche_plus_1 = new JPanel();
		pan_pioche_manche_plus_1.setBackground(background_couleur);
		pan_pioche_manche_plus_1.setLayout(new GridLayout(pioche_initiale.getSize(), 1, 20, 20));

		domino_manche_plus_1 = new Domino[pioche_initiale.getSize()];
		for (int i = 0; i < pioche_initiale.getSize(); i++) {
			this.domino_manche_plus_1[i] = new Domino(pioche_initiale.getDominoByIndex(i),
					couleurs.get(pioche_initiale.getJoueurByIndex(i) - 1));
			pan_pioche_manche_plus_1.add(domino_manche_plus_1[i]);
		}

		// INFOS
		pan_infos = new Infos(new GridLayout(3, 1));
		pan_infos.setBackground(background_couleur);
		pan_infos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		lab_manche = new JLabel();
		lab_manche.setFont(police);
		lab_manche.setForeground(couleur);
		lab_manche.setHorizontalAlignment(JLabel.CENTER);
		pan_infos.add(lab_manche);

		lab_action = new JLabel();
		lab_action.setFont(police);
		lab_action.setForeground(couleur);
		lab_action.setHorizontalAlignment(JLabel.CENTER);
		pan_infos.add(lab_action);

		lab_score = new JLabel();
		lab_score.setFont(police);
		lab_score.setForeground(couleur);
		lab_score.setHorizontalAlignment(JLabel.CENTER);
		pan_infos.add(lab_score);

		but_vue = new JButton("Vue d'ensemble");
		but_vue.setContentAreaFilled(false);
		but_vue.setFont(police);
		but_vue.setBorderPainted(false);
		but_vue.setFocusPainted(false);
		but_vue.setForeground(new Color(75, 63, 60));
		but_vue.setRolloverIcon(new ImageIcon("images//Bouton2.png"));
		but_vue.setIcon(new ImageIcon("images//Bouton.png"));
		but_vue.setPressedIcon(new ImageIcon("images//Bouton3.png"));
		but_vue.setHorizontalTextPosition(JButton.CENTER);
		but_vue.setVerticalTextPosition(JButton.CENTER);
		but_vue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				s.playAudio("button");
				if (source.getText().equals("Vue d'ensemble")) {
					vueEnsemble = true;
					displayVueEnsemble();
				} else {
					vueEnsemble = false;
					displayVueJoueur();
				}

			}
		});

		// on ajoute uniquement les infos a la fenetre (le reste sera ajoute ensuite)
		this.getContentPane().setLayout((new GridBagLayout()));
		this.getContentPane().add(pan_infos, GBC_infos());
		this.getContentPane().add(but_vue, GBC_button());
		this.getContentPane().add(pan_legende_pioche, GBC_legende_pioche());
		this.getContentPane().add(pan_pioche_manche, GBC_pioche_manche());
		this.getContentPane().add(pan_pioche_manche_plus_1, GBC_pioche_manche_plus_1());
		this.getContentPane().add(pan_plateau[joueur], GBC_plateau());
		this.getContentPane().setBackground(background_couleur);
	}

	public boolean hasPlacedDomino() {
		return pos[0] != -1 && pos[1] != -1 && pos[2] != -1 && pos[3] != -1;
	}

	public boolean hasSelectedDomino() {
		return domino != null;
	}

	public int[] getPositions() {
		int[] results = new int[4];
		for (int i = 0; i < 4; i++) {
			results[i] = pos[i];
			pos[i] = -1;
		}
		return results;
	}

	public int[] getDomino() {
		int[] result = domino;
		domino = null;
		return result;
	}

	public void setDomino(int[] pos, int[] domino) {
		cases[joueur][pos[0]][pos[1]].set(textures.get(domino[0] * 10 + domino[1]), false);
		cases[joueur][pos[2]][pos[3]].set(textures.get(domino[2] * 10 + domino[3]), false);
	}

	public void setCouleur(int[] domino, int couleur) {
		for (int i = 0; i < domino_manche_plus_1.length; i++) {
			if (domino_manche_plus_1[i].get() == domino)
				domino_manche_plus_1[i].setCouleur(couleur);
		}
	}

	public void setHighlight(int[] domino) {
		for (int i = 0; i < domino_manche.length; i++) {
			if (domino_manche[i].get() == domino)
				domino_manche[i].setHighlight(true);
			else
				domino_manche[i].setHighlight(false);
		}
	}

	public void setCrossed(int[] domino) {
		for (int i = 0; i < domino_manche_plus_1.length; i++) {
			if (domino_manche_plus_1[i].get() == domino)
				domino_manche_plus_1[i].setCrossed(true);
		}
	}

	public void setJoueur(int joueur) {
		if (joueur != this.joueur) // si c'est toujours le meme joueur, rien a faire
		{
			if (vueEnsemble == false) {
				// on supprime le plateau de l'affichage
				this.getContentPane().remove(pan_plateau[this.joueur]);

				// on ajoute le bon plateau a l'affichage
				this.getContentPane().add(pan_plateau[joueur], GBC_plateau());
				this.getContentPane().validate();
				this.getContentPane().repaint();
			}
			this.joueur = joueur;
		}
	}

	public void updateManche(int manche) {
		lab_manche.setText("Manche " + manche);
		if (manche == 1)
			pan_infos.setAnimation(1);
		else
			pan_infos.setAnimation(3);
	}

	public void updateScore(int score) {
		lab_score.setText("Score : " + score);
		lab_nom_score[joueur].setText(noms.get(joueur) + " : " + score + " points");

	}

	public void updateAction(int action) {
		actionEnCours = action;
		if (action == Partie.PLACER_DOMINO)
			lab_action.setText(noms.get(joueur) + " place son domino");
		else if (action == Partie.CHOISIR_DOMINO) {
			lab_action.setText(noms.get(joueur) + " choisit son domino");
		} else if (action == Partie.IMPOSSIBLE_PLACER_DOMINO) {
			JOptionPane message = new JOptionPane();
			// affiche un message d'erreur pour avertir l'utilisateur
			message.showMessageDialog(null, "le domino ne peut pas etre place sur le plateau", "action impossible",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void updatePioche(Pioche pioche_manche_plus_1) {

		// on supprime les 2 pioches de l'affichage
		if (vueEnsemble == false) {
			this.getContentPane().remove(pan_pioche_manche);
			this.getContentPane().remove(pan_pioche_manche_plus_1);
		}

		// la pioche de la manche plus 1 devient celle du tour actuel (pas besoin de la
		// creer)
		pan_pioche_manche = pan_pioche_manche_plus_1;
		domino_manche = domino_manche_plus_1;
		for (int i = 0; i < domino_manche.length; i++) // les dominos choisis ne sont plus barres
		{
			domino_manche[i].setCrossed(false);
		}

		// on l'ajoute a l'affichage
		if (vueEnsemble == false)
			this.getContentPane().add(pan_pioche_manche, GBC_pioche_manche());

		// on cree la pioche de la manche plus 1
		pan_pioche_manche_plus_1 = new JPanel();
		pan_pioche_manche_plus_1.setBackground(background_couleur);
		pan_pioche_manche_plus_1.setLayout(new GridLayout(pioche_manche_plus_1.getSize(), 1, 20, 20));

		domino_manche_plus_1 = new Domino[pioche_manche_plus_1.getSize()];
		for (int i = 0; i < pioche_manche_plus_1.getSize(); i++) {
			domino_manche_plus_1[i] = new Domino(pioche_manche_plus_1.getDominoByIndex(i), 0);
			domino_manche_plus_1[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (actionEnCours != 2) // le programme ne retient pas le domino si ce n'est pas l'action a
											// effectuer
						return;

					// on recupere le domino qui a ete selectionnee
					Domino source = (Domino) e.getSource();
					s.playAudio("button");
					// si le domino appartient deja a quelqu'un, on ne peut pas le choisir
					if (source.nobodyOwns()) {
						domino = source.get();
					}
				}
			});
			pan_pioche_manche_plus_1.add(domino_manche_plus_1[i]);
		}

		// on l'ajoute a l'affichage
		if (vueEnsemble == false) {
			this.getContentPane().add(pan_pioche_manche_plus_1, GBC_pioche_manche_plus_1());
			this.getContentPane().validate();
			this.getContentPane().repaint();
		}

	}

	private void displayVueJoueur() {
		while (this.getContentPane().getComponentCount() > 0) {
			this.getContentPane().remove(0);
		}
		but_vue.setText("Vue d'ensemble");
		this.getContentPane().add(pan_infos, GBC_infos());
		this.getContentPane().add(but_vue, GBC_button());
		this.getContentPane().add(pan_legende_pioche, GBC_legende_pioche());
		this.getContentPane().add(pan_pioche_manche, GBC_pioche_manche());
		this.getContentPane().add(pan_pioche_manche_plus_1, GBC_pioche_manche_plus_1());
		this.getContentPane().add(pan_plateau[joueur], GBC_plateau());
		this.getContentPane().validate();
		this.getContentPane().repaint();

	}

	private void displayVueEnsemble() {
		while (this.getContentPane().getComponentCount() > 0) {
			this.getContentPane().remove(0);
		}
		but_vue.setText("Vue joueur");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = pan_plateau.length;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		this.getContentPane().add(but_vue, c);

		for (int i = 0; i < pan_plateau.length; i++) {
			c.gridx = i;
			c.gridy = 1;
			c.gridwidth = 1;
			c.gridheight = 1;
			c.weightx = 1.0 / pan_plateau.length;
			c.weighty = 0.05;
			c.fill = GridBagConstraints.BOTH;
			this.getContentPane().add(lab_nom_score[i], c);

			c.weighty = 9.0 / 16.0 * 1.0 / pan_plateau.length;
			c.gridy = 2;
			this.getContentPane().add(pan_plateau[i], c);
		}
		this.getContentPane().validate();
		this.getContentPane().repaint();

	}

	private GridBagConstraints GBC_infos() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.4;
		c.weighty = 0.15;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}

	private GridBagConstraints GBC_legende_pioche() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.2;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}

	private GridBagConstraints GBC_pioche_manche() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.2;
		c.weighty = 0.75;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}

	private GridBagConstraints GBC_pioche_manche_plus_1() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.2;
		c.weighty = 0.75;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}

	private GridBagConstraints GBC_plateau() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 4;
		c.weightx = 0.6;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}

	private GridBagConstraints GBC_button() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.4;
		c.weighty = 0.05;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}

	public void showMoves(ArrayList<IA.Move> moves) {
		for (IA.Move move : moves) {
			if (move.impossible)
				continue;

			cases[joueur][move.pos[0]][move.pos[1]].set(textures.get(move.domino[0] * 10 + move.domino[1]), true);
			cases[joueur][move.pos[2]][move.pos[3]].set(textures.get(move.domino[2] * 10 + move.domino[3]), true);
		}
	}

	public void hideMoves(ArrayList<IA.Move> moves) {
		for (IA.Move move : moves) {
			if (move.impossible)
				continue;

			cases[joueur][move.pos[0]][move.pos[1]].reset();
			cases[joueur][move.pos[2]][move.pos[3]].reset();
		}
	}
}
