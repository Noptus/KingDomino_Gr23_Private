import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Partie {

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	private Parametres p;
	private List<String> couleurs;
	private List<Integer> ordre;
	private Dominos dominos;
	private Plateau[] plateaux;

	private Pioche domino_manche;
	private Pioche domino_manche_plus_1;

	// CONSTRUCTEUR
	public Partie(Parametres p, List<String> couleurs) {
		
		// On initialise les paramètres, les couleurs, l'ordre pour commencer
		this.p = p;
		this.couleurs = couleurs;
		ordre = defOrderInit();

		// On mets de côté le nombre de dominos
		dominos = new Dominos(12 * p.nbTotal);
		// test
		dominos.print();

		
		// On crée le grand plateau
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
		
		domino_manche_plus_1 = new Pioche(dominos.GetAndDelete_Dominos(p.nbTotal));
		// Pour chacune des 12 manches
		for (int i = 1; i <= 12; i++) {
			
			domino_manche = domino_manche_plus_1;
			if(i != 12)
				domino_manche_plus_1 = new Pioche(dominos.GetAndDelete_Dominos(p.nbTotal));
				
			// domino_manche_plus_1 = PickDominos(p.nbTotal);
			System.out.println("manche " + i);
			domino_manche.print();
			if(i != 12)
				domino_manche_plus_1.print();
			
			for(int joueur : ordre)
			{
				plateaux[joueur].print();
				//afficher son premier domino
				
				//tester si possible de le placer
				//si possible, doit le placer
				//sinon, afficher message erreur et continue
				
				Scanner sc = new Scanner(System.in);
				System.out.println("joueur " + joueur);
				do
				{
					
					System.out.println("x? y? x2? y2?");
					
					
				}
				while(plateaux[joueur].placer(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), domino_manche.getDomino(joueur)) == false);
				//ou si le joueur peut placer le domino
				//le joueur choisit son domino
				//mettre a jour ordre tour suivant
			}
		}
		
	}
	
	
	//METHODES PUBLIQUE

	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// Methode pour savoir qui commence
	private List<Integer> defOrderInit() {

		System.out.println("Ordre pour la première manche :");
		List<Integer> orderInit = new LinkedList<Integer>();

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
