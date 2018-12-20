import java.util.ArrayList;
import java.util.Collections;

public class Plateau {

	// CONSTANTES (POUR PLUS DE CLARTE DANS LE CODE)

	// on dÃ©finit une case vide, sans dominos dessus
	public static final int VIDE = 0;
	public static final int CHATEAU = 7;

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)

	// matrice d'entiers qui stockera les cases du plateau avec son contenu
	private int[][] plateau;
	private Parametres p;
	private int XC;
	private int YC;
	
	// CONSTRUCTEUR
	public Plateau(int ch, Parametres p) {
		
		this.p = p;
		if ( p.modeGrandDuel ) {
			plateau = new int[13][13];
			plateau[6][6] = ch;
			XC = 6;
			YC = 6;
		} else {
			plateau = new int[9][9];
			plateau[4][4] = ch;
			XC = 4;
			YC = 4;
		}
	
	}

	// METHODES PUBLIQUES

	public int[] getDimensions() {

		int min_X = 20;
		int max_X = -10;
		int min_Y = 20;
		int max_Y = -10;

		// On avance en augmentant les valeurs, pour trouver les mins
		for (int i = 0; i < sizeX(); i++) {
			for (int j = 0; j < sizeY(); j++) {
				if (getNature(i, j) != 0 && i < min_X) {
					min_X = i;
				}
				if (getNature(i, j) != 0 && j < min_Y) {
					min_Y = j;
				}
			}
		}

		// On fait diminuer les valeurs, pour trouver les max
		for (int i = sizeX() - 1; i >= 0; i--) {
			for (int j = sizeY() - 1; j >= 0; j--) {
				if (getNature(i, j) != 0 && i > max_X) {
					max_X = i;
				}
				if (getNature(i, j) != 0 && j > max_Y) {
					max_Y = j;
				}
			}
		}

		int[] Dimensions = new int[4];
		Dimensions[0] = min_X;
		Dimensions[1] = max_X;
		Dimensions[2] = min_Y;
		Dimensions[3] = max_Y;

		return Dimensions;

	}
	
	// retourne la nature de la case
	public int getNature(int x, int y) {
		// la nature de la case correspond aux unites
		try {
			return plateau[x][y] % 10;
		} catch (Exception e) {
			return -1;
		}

	}

	// retourne true si il y a possibilitÃ© de poser le domino dans le plateau
	public boolean isPossible(int[] Domino) {

		// On a les positions maximales et minimales de X et Y
		int[] Dimensions = getPossibleDimensions();

		for (int x = Dimensions[0]; x <= Dimensions[1]; x++) {
			for (int y = Dimensions[2]; y <= Dimensions[3]; y++) {
				if (getPossibilityHere(x, y, Domino) == true) {
					System.out.println("Possible : "+x+"-"+y);
					return true;
				}
			}

		} // fin du if case vide
		// Si aucun true n'a Ã©tÃ© sortie
		return false;
	}

	
	
	
	public boolean getPossibilityHere(int x, int y, int[] Domino) {

		ArrayList<Integer> gains = new ArrayList<Integer>();
		gains.add(tester(x-1, y, x, y, Domino));
		gains.add(tester(x+1, y, x, y, Domino));
		gains.add(tester(x, y-1, x, y, Domino));
		gains.add(tester(x, y+1, x, y, Domino));
		return Collections.max(gains) != -1;
		
	}
	
	
	// Chateau au centre > 10 points
	private boolean isEmpire() {
		if(p.modeEmpireMilieu == false) //le mode n'est pas active
			return false;
		int[] Dimensions = getDimensions();
		
		//distance du centre par rapport au bord de son plateau
		int distance;
		if(p.modeGrandDuel == true)
			distance = 3;
		else
			distance = 2;
		
		int Center_Y = Dimensions[3]-distance;
		int Center_X = Dimensions[1]-distance;
		System.out.println("center is : "+getNature(Center_X, Center_Y));
		//on verifie que le chateau est au centre de son plateau
		if (getNature(Center_X, Center_Y) == 7) {
			return true;
		} else {
			return false;
		}

	}

	// 5*5 cases > 5 points
	private boolean isHarmony() {
		if(p.modeHarmonie == false) //le mode n'est pas active
			return false;
		int[] Dimensions = getDimensions();
		int min_X = Dimensions[0];
		int min_Y = Dimensions[1];
		int max_X = Dimensions[2];
		int max_Y = Dimensions[3];
		
		//on verifie que chaque case est bien remplie
		for ( int x = min_X ; x < max_X + 1 ; x++) {
			for ( int y = min_Y ; y < max_Y + 1 ; y++) {
				if (getNature(x,y) == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	//4 4
	public boolean getRainbowPossibility() {
		if (       getNature(XC, YC+1) == 0 && getNature(XC-1, YC+1) == 0 || getNature(XC, YC+1) == 0 && getNature(XC, YC+2) == 0
				|| getNature(XC, YC+1) == 0 && getNature(XC+1, YC+1) == 0

				|| getNature(XC-1, YC) == 0 && getNature(XC-1, YC-1) == 0 || getNature(XC-1, YC) == 0 && getNature(XC-2, YC) == 0
				|| getNature(XC-1, YC) == 0 && getNature(XC-1, YC+1) == 0

				|| getNature(XC+1, YC) == 0 && getNature(XC+1, YC+1) == 0 || getNature(XC+1, YC) == 0 && getNature(XC+1, YC-1) == 0
				|| getNature(XC+1, YC) == 0 && getNature(XC+1, YC+2) == 0

				|| getNature(XC, YC-1) == 0 && getNature(XC-1, YC-1) == 0 || getNature(XC, YC-1) == 0 && getNature(XC, YC-1) == 0
				|| getNature(XC, YC-1) == 0 && getNature(XC+1, YC-1) == 0) {
			return true;
		}
		return false;
	}

	public int[] getPossibleDimensions() {

		int[] Dim_Actuelles = getDimensions();
		int min_X = Dim_Actuelles[0];
		int max_X = Dim_Actuelles[1];
		int min_Y = Dim_Actuelles[2];
		int max_Y = Dim_Actuelles[3];

		
		int dim_autorisee;
		if(p.modeGrandDuel == true)
			dim_autorisee = 7;
		else
			dim_autorisee = 5;
		
		//de combien on peut se decaler sur les cotes en respectant les dimensions autorisees
		int Marge_X = dim_autorisee - (max_X - min_X + 1);
		int Marge_Y = dim_autorisee - (max_Y - min_Y + 1);

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
	public int getScore(boolean partieFinie) {
		int score = 0;
		
		if(partieFinie) {
		if ( isHarmony() ) {
			score = score +5;
		}
		if ( isEmpire() ) {
			score = score +10;
		}
		}
		
		// liste qui stocke l'ensemble des cases qui ont ete comptees (pour ne pas les
		// recompter 2 fois quand on inspecte la case suivante)
		ArrayList<int[]> globallyCounted = new ArrayList<int[]>();

		// liste qui stocke les cases voisines qui sont semblables a celle inspectee
		// actuellement
		ArrayList<int[]> currentlyCounted = new ArrayList<int[]>();

		// on cherche pour chaque case les cases voisines semblables
		for (int x = 0; x < sizeX(); x++) {
			for (int y = 0; y < sizeY(); y++) {
				// on compte uniquement si la case n'est pas vide ou n'est pas un chateau
				if (getNature(x, y) != VIDE && getNature(x, y) != CHATEAU) {
					// on vide l'historique des cases voisines a la precedente
					currentlyCounted.clear();

					// on compte les cases voisines semblables aÂ  celle a la position (x, y)
					count(globallyCounted, currentlyCounted, getNature(x, y), x, y);

					// on compte les couronnes en parcourant la liste des cases voisines semblables
					int couronnes = 0;
					for (int[] position : currentlyCounted) {
						couronnes += getCouronne(position[0], position[1]);
					}

					// les cases correspondent aÂ  la taille de la liste des cases voisines
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
		for (int y = 0; y < sizeX(); y++) {
			System.out.print(y + " |");
			for (int x = 0; x < sizeY(); x++) {
					System.out.print(getCouronne(x, y));
					System.out.print(getNature(x, y));
					System.out.print("|");
				}
			System.out.println("");
		}
	}

	// METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE

	// sert au calcul du score
	private void count(ArrayList<int[]> globallyCounted, ArrayList<int[]> currentlyCounted, int nature, int x, int y) {
		// hors du plateau -> on s'arrete laÂ 
		if (x < 0 || y < 0 || x >= sizeX() || y >= sizeY())
			return;

		// pas la meme nature -> on s'arrete la
		if (getNature(x, y) != nature)
			return;

		// on verifie si la case a ete comptee auparavant -> on s'arrete laÂ 
		int[] currentPos = { x, y };
		for (int[] pos : globallyCounted) {
			if (pos[0] == currentPos[0] && pos[1] == currentPos[1])
				return;
		}

		// on ajoute la case aÂ  l'historique des cases comptees
		globallyCounted.add(currentPos);
		currentlyCounted.add(currentPos);

		// on inspecte les 4 cases voisines
		count(globallyCounted, currentlyCounted, nature, x - 1, y);
		count(globallyCounted, currentlyCounted, nature, x, y - 1);
		count(globallyCounted, currentlyCounted, nature, x + 1, y);
		count(globallyCounted, currentlyCounted, nature, x, y + 1);
	}

	//retourne true si le domino a ete place, false si le placement etait invalide
	public boolean placer(int x, int y, int x2, int y2, int[] domino) {
		
		if(tester(x, y, x2, y2, domino) != -1)
		{
			plateau[x][y] = domino[0] * 10 + domino[1];
			plateau[x2][y2] = domino[2] * 10 + domino[3];
			return true;
		}
		return false;

	}
	
	
	//retourne le score du nouveau plateau, ou -1 si le placement est invalide
	public int tester(int x, int y, int x2, int y2, int[] domino) {
		
		if(Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2)) != 1) //les deux morceaux sont separes de plus d'une case ou sont superposes
			return -1;
		if (isAvailable(x, y) && isAvailable(x2, y2)
				&& (isCompatible(x, y, domino[1]) || isCompatible(x2, y2, domino[3]))) { //les cases sont libres et les dominos compatibles
			
			//on place le domino
			plateau[x][y] = domino[0] * 10 + domino[1];
			plateau[x2][y2] = domino[2] * 10 + domino[3];
			
			int[] dimensions = getDimensions();
			int dim_autorisee;
			if(p.modeGrandDuel == true)
				dim_autorisee = 7;
			else
				dim_autorisee = 5;
			//on verifie que le nouveau plateau ne depasse pas les dimensions autorisees
			if(Math.abs(dimensions[0] - dimensions[1]) + 1 > dim_autorisee || Math.abs(dimensions[2] - dimensions[3]) + 1 > dim_autorisee) 
			{
				plateau[x][y] = VIDE; 
				plateau[x2][y2] = VIDE;
				return -1;
			}
			
			//on calcule le score de ce nouveau plateau
			int Score = getScore(false);
			
			plateau[x][y] = VIDE;
			plateau[x2][y2] = VIDE;
			return Score;
		}
		return -1;
	}





	private boolean isAvailable(int x, int y) {
		return getNature(x, y) == VIDE;
	}

	private boolean isCompatible(int x, int y, int nature) {
		boolean isCompatible = false;
		if ((x == XC - 1 && y == YC) || (x == XC + 1 && y == YC) || (x == XC && y == YC - 1) || (x == XC && y == YC + 1)) //rainbow
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
	
	public int sizeX()
	{
		return plateau.length;
	}
	
	public int sizeY()
	{
		return plateau[0].length;
	}

}
