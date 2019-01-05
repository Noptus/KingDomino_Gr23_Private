import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


public class IA {

	public class Move
	{
		public int[] pos;
		public int[] domino;
		public boolean impossible;

		public Move(int[] pos, int[] domino)
		{
			this.pos = pos;
			this.domino = domino;
			this.impossible = false;
		}
		public Move(int[] domino)
		{
			this.domino = domino;
			this.impossible = true;
		}
	}
	private ArrayList<Move> next_moves;

	private Parametres p;
	private Pioche pioche_actuelle;
	private Pioche pioche_suivante;
	private int joueur;

	private int nb_moves;
	private boolean derniere_manche;

	ArrayList<ArrayList<Move>> all_moves = new ArrayList<ArrayList<Move>>();

	public IA(Parametres p)
	{
		this.p = p;
	}

	public int[] getPos()
	{
		return next_moves.get(0).pos;
	}

	public int[] getDomino()
	{
		if(nb_moves == 2)
			return next_moves.get(1).domino;
		return next_moves.get(2).domino;
	}

	public ArrayList<Move> getMoves()
	{
		return next_moves;
	}
	
	public void think(Plateau plateau, Pioche pioche_actuelle, Pioche pioche_suivante, int joueur)
	{
		all_moves.clear();

		System.out.println("plateau 1 :");
		plateau.print();

		this.pioche_actuelle = pioche_actuelle;
		this.pioche_suivante = pioche_suivante;
		this.joueur = joueur;

		if(pioche_suivante.getSize() == 0)
			this.derniere_manche = true;
		else
			this.derniere_manche = false;

		if(pioche_actuelle.getDomino(joueur) != pioche_actuelle.getSecondDomino(joueur))
			this.nb_moves = 3;
		else
			this.nb_moves = 2;

		searchMoves(plateau, new ArrayList<Move>());

		System.out.println("plateau 2 :");
		plateau.print();

		ArrayList<Float> gains = new ArrayList<Float>();
		for(ArrayList<Move> moves: all_moves)
		{
			gains.add(getGain(plateau, moves));
		}

		System.out.println("plateau 3 :");
		plateau.print();

		float best_gain = Collections.max(gains);

		ArrayList<ArrayList<Move>> all_best_moves = new ArrayList<ArrayList<Move>>();
		while(gains.indexOf(best_gain) != -1)
		{
				all_best_moves.add(all_moves.get(gains.indexOf(best_gain)));
				all_moves.remove(gains.indexOf(best_gain));
				gains.remove(gains.indexOf(best_gain));
		}

		Random random = new Random();
		next_moves =  all_best_moves.get(random.nextInt(all_best_moves.size()));

		System.out.println("IA Joueur " + joueur);
		System.out.println("nombre de moves ignores : " + all_moves.size());
		System.out.println("nombre de moves conserves : " + all_best_moves.size());
		System.out.println("moves selectionnes :");
		printMoves(next_moves);
		System.out.println("gains : " + best_gain);
	}

	public void printMoves(ArrayList<Move> moves)
	{
		for(Move move : moves)
		{
			System.out.println("domino : " + (move.domino[0]*10 + move.domino[1]) + "-" + (move.domino[2]*10 + move.domino[3]));
			if(move.impossible)
				System.out.println("-> placement impossible");
			else
				System.out.println("-> aux coordonnees : " + move.pos[0] + "-" + move.pos[1] + " et " + move.pos[2] + "-" + move.pos[3]);
		}
	}

	public void searchMoves(Plateau plateau, ArrayList<Move> current_moves)
	{
		switch(current_moves.size())
		{
		case 0: //premier domino a placer
		{
			iterateMoves(new Plateau(plateau), new ArrayList<Move>(current_moves), pioche_actuelle.getDomino(joueur));
			break;
		}
		case 1: //deuxieme domino a placer ou premier domino a piocher
		{
			if(nb_moves == 2) //premier domino a piocher
			{
				for(int i = 0; i < pioche_suivante.getSize(); i++)
				{
					if(pioche_suivante.getJoueurByIndex(i) != 0) //le domino n'est pas disponible
						continue;
					iterateMoves(new Plateau(plateau), new ArrayList<Move>(current_moves), pioche_suivante.getDominoByIndex(i));
				}
			}
			else //deuxieme domino a placer
			{
				iterateMoves(new Plateau(plateau), new ArrayList<Move>(current_moves), pioche_actuelle.getSecondDomino(joueur));
			}
			break;
		}
		case 2: //premier domino a piocher
		{
			for(int i = 0; i < pioche_suivante.getSize(); i++)
			{
				if(pioche_suivante.getJoueurByIndex(i) != 0) //le domino n'est pas disponible
					continue;
				iterateMoves(new Plateau(plateau), new ArrayList<Move>(current_moves), pioche_suivante.getDominoByIndex(i));
			}
			break;
		}
		case 3: //deuxieme domino a piocher
		{
			for(int i = 0; i < pioche_suivante.getSize(); i++)
			{
				if(pioche_suivante.getJoueurByIndex(i) != 0 || pioche_suivante.getDominoByIndex(i) == current_moves.get(2).domino) //le domino n'est pas disponible ou a deja ete choisi au move d'avant
					continue;
				iterateMoves(new Plateau(plateau), new ArrayList<Move>(current_moves), pioche_suivante.getDominoByIndex(i));
			}
			break;
		}
	}

	}

	public void iterateMoves(Plateau plateau, ArrayList<Move> current_moves, int domino[])
	{
		ArrayList<Move> moves = getMoves(plateau, domino);
		for(Move move : moves)
		{
			ArrayList<Move> current_moves_evolved = new ArrayList<Move>(current_moves);
			current_moves_evolved.add(move);

			if(current_moves_evolved.size() == nb_moves / (derniere_manche ? 2 : 1)) //ce move est complet -> on l'ajoute et on arrete la
			{
				all_moves.add(current_moves_evolved); 
			}
			else
			{
				if(!move.impossible)
					plateau.placer(move.pos[0], move.pos[1], move.pos[2], move.pos[3], move.domino);
				searchMoves(plateau, current_moves_evolved);
			}
		}
	}

	//retourne tous les moves possibles du domino en question
	public ArrayList<Move> getMoves(Plateau plateau, int[] domino) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		int[] pos = new int[4];

		///on essaye chaque case contenue dans les dimensions possibles (= qui respectent la taille autorisee du plateau)
		int[] PossibleDimensions = plateau.getPossibleDimensions();
		for (int i = PossibleDimensions[0]; i <= PossibleDimensions[1]; i++) {
			for (int j = PossibleDimensions[2]; j <= PossibleDimensions[3]; j++) {
				for(int k = 0; k < 4; k++){ //on teste les 4 orientations du domino possibles
					switch(k) 
					{
					case 0:
						pos[0] = i - 1;
						pos[1] = j;
						pos[2] = i;
						pos[3] = j;
						break;
					case 1:
						pos[0] = i + 1;
						pos[1] = j;
						pos[2] = i;
						pos[3] = j;
						break;
					case 2:
						pos[0] = i;
						pos[1] = j - 1;
						pos[2] = i;
						pos[3] = j;
						break;
					case 3:
						pos[0] = i;
						pos[1] = j + 1;
						pos[2] = i;
						pos[3] = j;
						break;
					}
				
					if(plateau.placementValide(pos[0], pos[1], pos[2], pos[3], domino)) //si le placement est valide, on le sauvegarde
						moves.add(new Move(pos.clone(), domino));
				}
			}
		}
		if(moves.size() == 0) //on verifie que le domino peut etre place -> sinon on ajoute un move impossible
			moves.add(new Move(domino));

		return moves;
	}

	//retourne le gain de l'ensemble des moves 
	public float getGain(Plateau plateau, ArrayList<Move> moves)
	{
		//ETAPE 1 : on calcule le score avant de jouer les moves
		int S = plateau.getScore(false);
		
		//ETAPE 2 : on place les moves temporairement
		for(Move move : moves)
		{
			if(move.impossible)
				continue;

			plateau.placer(move.pos[0], move.pos[1], move.pos[2], move.pos[3], move.domino);
		}

		//ETAPE 3 : on calcule le score apres les moves et on calcule la difference
		S = plateau.getScore(false) - S; //on calcule le score apres les moves et on retire le score avant pour calculer la difference
		
		/*
		//ETAPE 4 : on regarde combien de cases identiques sont a proximites des cases posees
		ArrayList<int[]> globallyCounted = new ArrayList<int[]>();
		int nb_cases_identiques = 0;
		for(Move move : moves) //on regarde pour chaque move
		{
			if(move.impossible)
				continue;

			for(int i = 0; i < 3; i += 2) //on regarde pour chaque demi domino (2 iterations)
			{
				nb_cases_identiques += plateau.count(globallyCounted, null, plateau.getNature(move.pos[i], move.pos[i+1]), move.pos[i], move.pos[i+1]);
			}
		}*/

		//ETAPE 5 : on regarde si les moves permettent toujours d'avoir les bonus de fin de partie
		boolean canHarmony = plateau.canHarmony();
		boolean canEmpire = plateau.canEmpire();

		//ETAPE 6 : on calcule le gain
		//permet de determiner le meilleur move  /!\ G >= 0  obligatoirement /!\ 
		//ajuster cette formule permet de modifier le comportement de l'IA : faire des tests pour ajuster
		float G = 0;
		switch(joueur)
		{
			case 1:
				G = S + (p.modeHarmonie && canHarmony ? 5 : 0) + (p.modeEmpireMilieu && canEmpire ? 10 : 0);
				break;
			case 2:
				G = S + (p.modeHarmonie && canHarmony ? 5 : 0) + (p.modeEmpireMilieu && canEmpire ? 10 : 0);
				break;
			case 3:
				G = S + (p.modeHarmonie && canHarmony ? 5 : 0) + (p.modeEmpireMilieu && canEmpire ? 10 : 0);
				break;
			case 4:
				G = S + (p.modeHarmonie && canHarmony ? 5 : 0) + (p.modeEmpireMilieu && canEmpire ? 10 : 0);
				break;
		}

		//ETAPE 7 : on supprime les moves places temporairement
		for(Move move : moves)
		{
			if(move.impossible)
				continue;

			plateau.remove(move.pos[0], move.pos[1], move.pos[2], move.pos[3]);
		}

		//ETAPE 8 : on retourne le gain
		return G;
	}
}
