import java.util.ArrayList;

public class Plateau {

	// CONSTANTES (POUR PLUS DE CLARTE DANS LE CODE)

	// on définit une case vide, sans dominos dessus
	public static final int VIDE = 0;

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)

	// matrice d'entiers qui stockera les cases du plateau avec son contenu
	private int[][] plateau;

	// CONSTRUCTEUR
	public Plateau() {
		// on creee un tableau de taille 9x9
		plateau = new int[9][9];

		// plateau de test pour tester le calcul de score
		plateau[0][0] = 00;  plateau[1][0] = 00; plateau[2][0] = 00;  plateau[3][0] = 00;  plateau[4][0] = 00; plateau[5][0] = 00; plateau[6][0] = 00; plateau[7][0] = 00; plateau[8][0] = 00;
		plateau[0][1] = 00;  plateau[1][1] = 00; plateau[2][1] = 00;  plateau[3][1] = 00;  plateau[4][1] = 00; plateau[5][1] = 00; plateau[6][1] = 00; plateau[7][1] = 00; plateau[8][1] = 00;
		plateau[0][2] = 00;  plateau[1][2] = 00; plateau[2][2] = 00;  plateau[3][2] = 00;  plateau[4][2] = 00; plateau[5][2] = 00; plateau[6][2] = 00; plateau[7][2] = 00; plateau[8][2] = 00;
		plateau[0][3] = 00;  plateau[1][3] = 00; plateau[2][3] = 00;  plateau[3][3] = 00;  plateau[4][3] = 00; plateau[5][3] = 00; plateau[6][3] = 00; plateau[7][3] = 00; plateau[8][3] = 00;
		plateau[0][4] = 00;  plateau[1][4] = 00; plateau[2][4] = 00;  plateau[3][4] = 00;  plateau[4][4] = 07; plateau[5][4] = 00; plateau[6][4] = 00; plateau[7][4] = 00; plateau[8][4] = 00;
		plateau[0][5] = 00;  plateau[1][5] = 00; plateau[2][5] = 00;  plateau[3][5] = 00;  plateau[4][5] = 00; plateau[5][5] = 00; plateau[6][5] = 00; plateau[7][5] = 00; plateau[8][5] = 00;
		plateau[0][6] = 00;  plateau[1][6] = 00; plateau[2][6] = 00;  plateau[3][6] = 00;  plateau[4][6] = 00; plateau[5][6] = 00; plateau[6][6] = 00; plateau[7][6] = 00; plateau[8][6] = 00;
		plateau[0][7] = 00;  plateau[1][7] = 00; plateau[2][7] = 00;  plateau[3][7] = 00;  plateau[4][7] = 00; plateau[5][7] = 00; plateau[6][7] = 00; plateau[7][7] = 00; plateau[8][7] = 00;
		plateau[0][8] = 00;  plateau[1][8] = 00; plateau[2][8] = 00;  plateau[3][8] = 00;  plateau[4][8] = 00; plateau[5][8] = 00; plateau[6][8] = 00; plateau[7][8] = 00; plateau[8][8] = 00;
	}

	// METHODES PUBLIQUES

	// retourne la nature de la case
	public int getNature(int x, int y) {
		// la nature de la case correspond aux unites
		try {
			return plateau[x][y] % 10;
		} catch (Exception e) {
			return -1;
		}

	}

	// retourne true si il y a possibilité de poser le domino dans le plateau
	public boolean isPossible(int[] Domino) {

		// On a les positions maximales et minimales de X et Y
		int[] Dimensions = getPossibleDimensions();

		// On teste les cases multicolores
		if (getRainbowPossibility() == true) {
			System.out.println("Possible rainbow");
			return true;
		}

		// Sinon, on navigue dans le carré où l'on peut poser
		for (int x = Dimensions[0]; x <= Dimensions[1]; x++) {
			for (int y = Dimensions[2]; y <= Dimensions[3]; y++) {


				if (getNature(x, y) == 0) {
					boolean possible = getPossibilityHere(x, y, Domino);
					if (possible == true) {
						System.out.println("Possible : "+x+"-"+y);
						return true;
					}

				} // fin du if case vide

			}
		}

		// Si aucun true n'a été sortie
		return false;
	}

	
	
	
	public boolean getPossibilityHere(int x, int y, int[] Domino) {

		int[] Dimensions = getPossibleDimensions();
		
		// case a gauche match
		if( getNature(x-1,y) == Domino[1] || getNature(x-1,y) == Domino[3] ) {
			if( getNature(x-2,y)==0 || getNature(x-1,y-1)==0 || getNature(x-1,y+1)==0 ) {
				return true;
			}
		}
		// case a droite match
		if( getNature(x+1,y) == Domino[1] || getNature(x+1,y) == Domino[3] ) {
			if( getNature(x+2,y)==0 || getNature(x+1,y-1)==0 || getNature(x+1,y+1)==0 ) {
				return true;
			}
		}
		// case en dessous match
		if( getNature(x,y+1) == Domino[1] || getNature(x,y+1) == Domino[3] ) {
			if( getNature(x-1,y+1)==0 || getNature(x,y+2)==0 || getNature(x+1,y+1)==0 ) {
				return true;
			}
		}
		// case au dessus match
		if( getNature(x,y-1) == Domino[1] || getNature(x,y-1) == Domino[3] ) {
			if( getNature(x-1,y-1)==0 || getNature(x,y-2)==0 || getNature(x+1,y-1)==0 ) {
				return true;
			}
		}
		
		// aucun match valide
		return false;
	}
	
	
	
	

	public boolean getRainbowPossibility() {
		if (       getNature(4, 5) == 0 && getNature(3, 5) == 0 || getNature(4, 5) == 0 && getNature(4, 6) == 0
				|| getNature(4, 5) == 0 && getNature(5, 5) == 0

				|| getNature(3, 4) == 0 && getNature(3, 3) == 0 || getNature(3, 4) == 0 && getNature(2, 4) == 0
				|| getNature(3, 4) == 0 && getNature(3, 5) == 0

				|| getNature(5, 4) == 0 && getNature(5, 5) == 0 || getNature(5, 4) == 0 && getNature(5, 3) == 0
				|| getNature(5, 4) == 0 && getNature(5, 6) == 0

				|| getNature(4, 3) == 0 && getNature(3, 3) == 0 || getNature(4, 3) == 0 && getNature(4, 2) == 0
				|| getNature(4, 3) == 0 && getNature(5, 3) == 0) {
			return true;
		}
		return false;
	}

	public int[] getPossibleDimensions() {

		int min_X = 20;
		int max_X = -10;
		int min_Y = 20;
		int max_Y = -10;

		// On avance en augmentant les valeurs, pour trouver les mins
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				if (getNature(i, j) != 0 && i < min_X) {
					min_X = i;
				}
				if (getNature(i, j) != 0 && j < min_Y) {
					min_Y = j;
				}
			}
		}

		// On fait diminuer les valeurs, pour trouver les max
		for (int i = 8; i >= 0; i--) {
			for (int j = 8; j >= 0; j--) {
				if (getNature(i, j) != 0 && i > max_X) {
					max_X = i;
				}
				if (getNature(i, j) != 0 && j > max_Y) {
					max_Y = j;
				}
			}
		}

		int Marge_X = 5 - (max_X - min_X + 1);
		int Marge_Y = 5 - (max_Y - min_Y + 1);

		int Minimum_Ever_X = min_X - Marge_X;
		if (Minimum_Ever_X < 0)
			Minimum_Ever_X = 0;
		int Minimum_Ever_Y = min_Y - Marge_Y;
		if (Minimum_Ever_Y < 0)
			Minimum_Ever_Y = 0;
		int Maximum_Ever_X = max_X + Marge_X;
		if (Maximum_Ever_X > 8)
			Maximum_Ever_X = 8;
		int Maximum_Ever_Y = max_Y + Marge_Y;
		if (Maximum_Ever_Y > 8)
			Maximum_Ever_Y = 8;

		int[] Dimensions = new int[4];
		Dimensions[0] = Minimum_Ever_X;
		Dimensions[1] = Maximum_Ever_X;
		Dimensions[2] = Minimum_Ever_Y;
		Dimensions[3] = Maximum_Ever_Y;

		return Dimensions;
	}

	// retourne le nb de couronnes sur cette case
	public int getCouronne(int x, int y) {
		try {
			// le nombre de couronne correpond aux dizaines
			return plateau[x][y] / 10;
		}catch(Exception e) { 
			return 0;
		}
	}

	// retourne le score du plateau
	public int getScore() {
		int score = 0;

		// liste qui stocke l'ensemble des cases qui ont ete comptees (pour ne pas les
		// recompter 2 fois quand on inspecte la case suivante)
		ArrayList<int[]> globallyCounted = new ArrayList<int[]>();

		// liste qui stocke les cases voisines qui sont semblables a celle inspectee
		// actuellement
		ArrayList<int[]> currentlyCounted = new ArrayList<int[]>();

		// on cherche pour chaque case les cases voisines semblables
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				// on compte uniquement si la case n'est pas vide
				if (getNature(x, y) != VIDE) {
					// on vide l'historique des cases voisines a la precedente
					currentlyCounted.clear();

					// on compte les cases voisines semblables a  celle a la position (x, y)
					count(globallyCounted, currentlyCounted, getNature(x, y), x, y);

					// on compte les couronnes en parcourant la liste des cases voisines semblables
					int couronnes = 0;
					for (int[] position : currentlyCounted) {
						couronnes += getCouronne(position[0], position[1]);
					}

					// les cases correspondent a  la taille de la liste des cases voisines
					// semblables
					int cases = currentlyCounted.size();

					// le score est calcule commme le produit des couronnes et des cases
					score += couronnes * cases;
				}
			}
		}

		return score;
	}

	// affiche le plateau
	public void print() {
		System.out.println("  |0 |1 |2 |3 |4 |5 |6 | 7| 8");
		for (int y = 0; y <= 8; y++) {
			System.out.print(y + " |");
			for (int x = 0; x <= 8; x++) {
					System.out.print(getCouronne(x, y));
					System.out.print(getNature(x, y));
					System.out.print("|");
				}
			System.out.println("");
		}
		System.out.println("Score: " + getScore());
	}

	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// sert au calcul du score
	private void count(ArrayList<int[]> globallyCounted, ArrayList<int[]> currentlyCounted, int nature, int x, int y) {
		// hors du plateau -> on s'arrete la 
		if (x < 0 || y < 0 || x > 8 || y > 8)
			return;

		// pas la meme nature -> on s'arrete la
		if (getNature(x, y) != nature)
			return;

		// on verifie si la case a ete comptee auparavant -> on s'arrete la 
		int[] currentPos = { x, y };
		for (int[] pos : globallyCounted) {
			if (pos[0] == currentPos[0] && pos[1] == currentPos[1])
				return;
		}

		// on ajoute la case a  l'historique des cases comptees
		globallyCounted.add(currentPos);
		currentlyCounted.add(currentPos);

		// on inspecte les 4 cases voisines
		count(globallyCounted, currentlyCounted, nature, x - 1, y);
		count(globallyCounted, currentlyCounted, nature, x, y - 1);
		count(globallyCounted, currentlyCounted, nature, x + 1, y);
		count(globallyCounted, currentlyCounted, nature, x, y + 1);
	}

	public boolean placer(int x, int y, int x2, int y2, int[] domino) {
		// + est contenu dans le plateau + ne depasse les 5x5
		if (isAvailable(x, y) && isAvailable(x2, y2)
				&& (isCompatible(x, y, domino[1]) || isCompatible(x2, y2, domino[3]))) {
			plateau[x][y] = domino[0] * 10 + domino[1];
			plateau[x2][y2] = domino[2] * 10 + domino[3];
			return true;
		}
		return false;

	}

	private boolean isAvailable(int x, int y) {
		return getNature(x, y) == VIDE;
	}

	private boolean isCompatible(int x, int y, int nature) {
		boolean isCompatible = false;
		if ((x == 3 && y == 4) || (x == 5 && y == 4) || (x == 4 && y == 5) || (x == 4 && y == 3))
			isCompatible = true;
		if (getNature(x - 1, y) == nature)
			isCompatible = true;
		if (getNature(x + 1, y) == nature)
			isCompatible = true;
		if (getNature(x, y - 1) == nature)
			isCompatible = true;
		if (getNature(x, y + 1) == nature)
			isCompatible = true;
		return isCompatible;
	}

}
