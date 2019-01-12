import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Partie {

	public static final int PLACER_DOMINO = 1;
	public static final int CHOISIR_DOMINO = 2;
	public static final int IMPOSSIBLE_PLACER_DOMINO = 3;

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	private Parametres p;
	private ArrayList<Integer> couleurs;
	private ArrayList<String> noms;
	private ArrayList<Integer> ordre;
	private Dominos dominos;
	private Plateau[] plateaux;
	private Fenetre fenetre;

	private Pioche domino_manche;
	private Pioche domino_manche_plus_1;
	
	private IA chevre;
	
	private long startTime;
	
	private SoundPlayer s;

	// CONSTRUCTEUR
	public Partie(Parametres p, ArrayList<Integer> couleurs, ArrayList<String> noms, SoundPlayer s) {

		this.s = s;
		// On initialise les parametres, les couleurs, l'ordre pour commencer
		this.p = p;
		this.couleurs = couleurs;
		this.noms = noms;
		ordre = defOrderInit();

		// On cree les dominos
		dominos = new Dominos(p.nbDominosPartie);

		// On cree le grand plateau
		plateaux = new Plateau[p.nbTotal];

		for (int i = 0; i < p.nbTotal; i++) {
			// On le remplit
			plateaux[i] = new Plateau(couleurs.get(i), p);
		}

		fenetre = null;
		
		chevre = new IA(p);
	}

	// METHODES PUBLIQUES

	// deroulement de la partie, la ou tout se passera
	public void jouer() {

		// on cree la pioche du premier tour et on indique l'appartenance des dominos
		// suivant l'odre des joueurs genere aleatoirement
		domino_manche_plus_1 = new Pioche(dominos.GetAndDelete_Dominos(p.nbDominosManche),
				(ArrayList<Integer>) ordre.clone());
		fenetre = new Fenetre(plateaux, domino_manche_plus_1, couleurs, noms, s);
		fenetre.setVisible(true);

		// On definit le nombre de manches du jeu selon le nb de joueurs
		int nb_manches;
		if (p.nbTotal == 2 && p.modeGrandDuel == false)
			nb_manches = 6;
		else
			nb_manches = 12;

		startTime = System.currentTimeMillis();

		// boucle de jeu
		for (int manche = 1; manche <= nb_manches; manche++) {

			// on intervertit les pioches
			domino_manche = domino_manche_plus_1;

			// on cree la pioche du tour suivant
			if (manche != nb_manches)
				domino_manche_plus_1 = new Pioche(dominos.GetAndDelete_Dominos(p.nbDominosManche));
			else
				domino_manche_plus_1 = new Pioche();

			fenetre.updatePioche(domino_manche_plus_1);
			fenetre.updateManche(manche);

			// on fait jouer les joueurs suivant l'ordre de jeu
			for (int joueur : ordre) {
				fenetre.setJoueur(joueur - 1);
				fenetre.setHighlight(domino_manche.getDomino(joueur));
				fenetre.updateAction(PLACER_DOMINO);
				fenetre.updateScore(plateaux[joueur - 1].getScore(false));
				//l'ia prend ses decisions
				if(joueur > p.nbJoueurs)
				{
					chevre.think(plateaux[joueur-1], domino_manche, domino_manche_plus_1, joueur, manche);
				}

				// tester si possible de le placer
				if (!plateaux[joueur - 1].isPossible(domino_manche.getDomino(joueur))) {
					s.playAudio("sorciere");
					if(joueur <= p.nbJoueurs) //le message ne s'affiche que pour les joueurs
						fenetre.updateAction(IMPOSSIBLE_PLACER_DOMINO);
				} else {
					int positions[] = new int[2];
					if(joueur <= p.nbJoueurs) //c'est un jouueur qui joue
					{
						// le joueur place son domino sur son terrain
						int T = 0; //nombre de tentatives de placement
						
						//tant que le joueur n'a pas reussi a placer son domino
						do {
							if (T != 0) { //le joueur a mal place son domino
								s.playAudio("bad");
							}
								
							// on attend que l'utilisateur ait place son domino
							while (fenetre.hasPlacedDomino() == false) {
								// /!\ on verifie toutes les 10 ms, autrement ca ne fonctionne pas
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
	
							// on recupere les cases sur lesquelles il a clique
							positions = fenetre.getPositions();
							T = T + 1;
	
						} // verifie que le placement est valide, sinon on lui redemande a nouveau
						while (plateaux[joueur - 1].placementValide(positions[0], positions[1], positions[2], positions[3],domino_manche.getDomino(joueur)) == false);
					}
					else //c'est une ia
					{
						positions = chevre.getPos();
						//on ralentit l'IA sinon elle joue trop rapidement
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					//on place le domino
					plateaux[joueur - 1].placer(positions[0], positions[1], positions[2], positions[3], domino_manche.getDomino(joueur));	

					// on met a jour les textures du plateau
					fenetre.setDomino(positions, domino_manche.getDomino(joueur));
					s.playDomino(domino_manche.getDomino(joueur));

					// on met a jour le score
					fenetre.updateScore(plateaux[joueur - 1].getScore(false));
				}
				// on supprimer son domino de la pioche du tour actuel
				domino_manche.deleteDomino(joueur);

				if (manche != nb_manches) // si ce n'est pas la derniere manche
				{
					// le joueur choisit son domino dans la pioche du tour suivant
					fenetre.updateAction(CHOISIR_DOMINO);

					
					int[] domino = new int[5];
					if(joueur <= p.nbJoueurs)//c'est un joueur qui joue
					{
						while (fenetre.hasSelectedDomino() == false) {
							// /!\ on verifie toutes les 10 ms, autrement ca ne fonctionne pas
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
	
						domino = fenetre.getDomino();
					}
					else //c'est une ia
					{
						domino = chevre.getDomino();
						//on ralentit l'IA sinon elle joue trop rapidement
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace(); 
						}
					}
					domino_manche_plus_1.choisir(domino, joueur);
					
					//on met a jour l'affichage
					fenetre.setCrossed(domino);
					fenetre.setCouleur(domino, couleurs.get(joueur - 1));
				}

			}

			// mettre a jour ordre tour suivant
			ordre = domino_manche_plus_1.getOrdre();
		}

		// on affiche les scores de fin de partie
		for (int i = 0; i < p.nbTotal; i++) {
			System.out.println(noms.get(i) + " a termine avec un score de : " + plateaux[i].getScore(true));
		}

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace(); 
		}
		
		fenetre.setVisible(false);
		fenetre.dispose();
	}

	// METHODES PUBLIQUE
	public int[] getScores()
	{
		int[] scores = new int[p.nbTotal];
		for(int i = 0; i < p.nbTotal; i++)
		{
			scores[i] = plateaux[i].getScore(true);
		}
		return scores;
	}
	
	public long getElapsedTime()
	{
		return System.currentTimeMillis() - startTime;
	}
	
	
	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// Methode pour savoir qui commence
	private ArrayList<Integer> defOrderInit() {

		ArrayList<Integer> orderInit = new ArrayList<Integer>();

		if (p.nbTotal != 2) {

			for (int i = 1; i <= p.nbTotal; i++) {
				orderInit.add(i);
			}
			Collections.shuffle(orderInit);
			// shuffleList(orderInit);


			return orderInit;

		} else {
			orderInit.add(1);
			orderInit.add(2);
			orderInit.add(2);
			orderInit.add(1);

			Collections.shuffle(orderInit);
			// shuffleList(orderInit);

			return orderInit;
		}
	}
}
