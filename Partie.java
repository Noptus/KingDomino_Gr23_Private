import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Partie {

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	Parametres p;
	private List<String> couleurs;
	private List<Integer> ordre;
	private Dominos dominos;
	private Plateau[] plateaux;

	public int[][] domino_manche;
	public int[][] domino_manche_plus_1;

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

		// Pour chacune des 12 manches
				for (int i = 1; i <= 12; i++) {
					
					domino_manche = domino_manche_plus_1;
					domino_manche_plus_1 = PickDominos(p.nbTotal);
					
					System.out.println("manche "+i);
					System.out.println(domino_manche);
					System.out.println("");
					System.out.println(domino_manche_plus_1);
					System.out.println();
				}
				
	}

	private int[][] PickDominos(int n) {

		switch (n) {
		case 2:
			domino_manche[0] = dominos.GetAndDelete_Domino();
			domino_manche[1] = dominos.GetAndDelete_Domino();
			domino_manche[2] = dominos.GetAndDelete_Domino();
			domino_manche[3] = dominos.GetAndDelete_Domino();
			break;
		case 3:
			domino_manche[0] = dominos.GetAndDelete_Domino();
			domino_manche[1] = dominos.GetAndDelete_Domino();
			domino_manche[2] = dominos.GetAndDelete_Domino();
			break;
		default:
			domino_manche[0] = dominos.GetAndDelete_Domino();
			domino_manche[1] = dominos.GetAndDelete_Domino();
			domino_manche[2] = dominos.GetAndDelete_Domino();
			domino_manche[3] = dominos.GetAndDelete_Domino();
			break;
		}
		return domino_manche;

	}
	
	private void printPickedDominos(int[][] D) {
		
		for(int i =0; i<=D.length;i++) {
			System.out.println("Domino "+i+" : "+D[i]);
		}
		
	}
	
	

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
