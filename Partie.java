import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Partie {

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	private Parametres p;
	private ArrayList<String> couleurs;
	private ArrayList<Integer> ordre;
	private Dominos dominos;
	private Plateau[] plateaux;

	private Pioche domino_manche;
	private Pioche domino_manche_plus_1;

	// CONSTRUCTEUR
	public Partie(Parametres p, ArrayList<String> couleurs) {
		
		// On initialise les parametres, les couleurs, l'ordre pour commencer
		this.p = p;
		this.couleurs = couleurs;
		ordre = defOrderInit();

		// On cree les dominos
		dominos = new Dominos(12 * p.nbTotal);
		// test
		dominos.print();

		
		// On cree le grand plateau
		plateaux = new Plateau[p.nbTotal];
		for (int i = 0; i < p.nbTotal; i++) {
			// On le remplit
			plateaux[i] = new Plateau();
			// test
			System.out.println();
			plateaux[i].print();
		}

	}

	// METHODES PUBLIQUES

	// deroulement de la partie, la ou tout se passera
	public void jouer() {
		
		//on cree la pioche du premier tour et on indique l'appartenance des dominos suivant l'odre des joueurs genere precedemment
		domino_manche_plus_1 = new Pioche(dominos.GetAndDelete_Dominos(p.nbTotal*p.nbDominoParJoueur), (ArrayList<Integer>)ordre.clone());
		
		// Pour chacune des 12 manches
		for (int i = 1; i <= 12; i++) {
			
			//on intervertit les pioches
			domino_manche = domino_manche_plus_1;
			if(i != 12) //on cree la pioche du tour suivant
				domino_manche_plus_1 = new Pioche(dominos.GetAndDelete_Dominos(p.nbTotal*p.nbDominoParJoueur));
				
			System.out.println("manche " + i);
			
			//on affiche les 2 pioches (pour le debug)
			System.out.println("pioche tour actuel : ");
			domino_manche.print();
			if(i != 12)
			{
				System.out.println("pioche tour suivant : ");
				domino_manche_plus_1.print();	
			}
			
			//on fait jouer les joueurs suivant l'ordre de jeu
			for(int joueur : ordre)
			{
				//indique quel joueur joue
				System.out.println("joueur " + joueur);

				//afficher le plateau du joueur
				System.out.println("plateau du joueur : ");
				plateaux[joueur-1].print();
				
				//afficher le domino que le joueur doit placer
				domino_manche.printDomino(joueur);
				
				Scanner sc = new Scanner(System.in);
				
				//tester si possible de le placer
				if(!plateaux[joueur-1].isPossible(domino_manche.getDomino(joueur)))
				{
					System.out.println("Tu ne peux pas jouer ce domino !");
				}
				else
				{			
					//le joueur place son domino sur son terrain
					do
					{
						System.out.println("x? y? x2? y2?");
					}
					while(plateaux[joueur-1].placer(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), domino_manche.getDomino(joueur)) == false);
					
					//on supprimer son domino de la pioche du tour actuel
					domino_manche.deleteDomino(joueur);
				}
				
				//le joueur choisit son domino dans la pioche du tour suivant
				System.out.println("pioche tour suivant :");
				domino_manche_plus_1.print();
				do
				{
					System.out.println("indice du domino a choisir ?");
				}
				while(domino_manche_plus_1.choisir(sc.nextInt(), joueur) == false);
				
			}
			
			//mettre a jour ordre tour suivant
			ordre = domino_manche_plus_1.getOrdre();
		}
		
	}
	
	
	//METHODES PUBLIQUE

	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// Methode pour savoir qui commence
	private ArrayList<Integer> defOrderInit() {

		System.out.println("Ordre pour la première manche :");
		ArrayList<Integer> orderInit = new ArrayList<Integer>();

		if (p.nbTotal != 2) {

			for (int i = 1; i <= p.nbTotal; i++) {
				orderInit.add(i);
			}
			Collections.shuffle(orderInit);
			// shuffleList(orderInit);

			for (int i = 0; i < p.nbTotal; i++) {
				System.out.println("Joueur " + (orderInit.get(i)) + " : Place = " + (i + 1));
			}

			return orderInit;

		} else {
			orderInit.add(1);
			orderInit.add(2);
			orderInit.add(2);
			orderInit.add(1);

			Collections.shuffle(orderInit);
			// shuffleList(orderInit);

			for (int i = 0; i < 4; i++) {
				System.out.println("Joueur " + (orderInit.get(i)) + " : Place = " + (i + 1));
			}
			return orderInit;
		}
	}

}
