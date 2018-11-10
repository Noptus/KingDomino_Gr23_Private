import java.util.ArrayList;

public class Plateau 
{
	//on définit ces constantes qui correspondent à la nature des cases (pour plus de clarté dans le code)
	public static final int VIDE = 0;
	public static final int MONTAGNE = 1;
	public static final int PLAINE = 2;
	public static final int RUINE = 3;
	public static final int FORET = 4;
	public static final int LAC = 5;
	public static final int CHAMP = 6;
	
	private int[][] plateau;
	
	public Plateau()
	{
		//on crée un tableau de taille 9x9 (ou 13x13 pour le mode spécial, à rajouter plus tard en passant un paramètre lors de la création de la classe)
		plateau = new int[9][9];
		
		//plateau de test pour tester le calcul de score
		plateau[0][0] = 11; plateau[1][0] = 1; plateau[2][0] = 0; plateau[3][0] = 0; plateau[4][0] = 0; plateau[5][0] = 0; plateau[6][0] = 0; plateau[7][0] = 0; plateau[8][0] = 0;
		plateau[0][1] = 1; plateau[1][1] = 1; plateau[2][1] = 0; plateau[3][1] = 0; plateau[4][1] = 0; plateau[5][1] = 0; plateau[6][1] = 0; plateau[7][1] = 0; plateau[8][1] = 0;
		plateau[0][2] = 1; plateau[1][2] = 0; plateau[2][2] = 11; plateau[3][2] = 0; plateau[4][2] = 0; plateau[5][2] = 0; plateau[6][2] = 0; plateau[7][2] = 0; plateau[8][2] = 0;
		plateau[0][3] = 21; plateau[1][3] = 0; plateau[2][3] = 0; plateau[3][3] = 0; plateau[4][3] = 0; plateau[5][3] = 0; plateau[6][3] = 0; plateau[7][3] = 0; plateau[8][3] = 0;
		plateau[0][4] = 1; plateau[1][4] = 0; plateau[2][4] = 0; plateau[3][4] = 0; plateau[4][4] = 0; plateau[5][4] = 0; plateau[6][4] = 0; plateau[7][4] = 0; plateau[8][4] = 0;
		plateau[0][5] = 1; plateau[1][5] = 0; plateau[2][5] = 0; plateau[3][5] = 21; plateau[4][5] = 0; plateau[5][5] = 0; plateau[6][5] = 0; plateau[7][5] = 0; plateau[8][5] = 0;
		plateau[0][6] = 1; plateau[1][6] = 0; plateau[2][6] = 0; plateau[3][6] = 0; plateau[4][6] = 0; plateau[5][6] = 0; plateau[6][6] = 0; plateau[7][6] = 0; plateau[8][6] = 0;
		plateau[0][7] = 1; plateau[1][7] = 0; plateau[2][7] = 0; plateau[3][7] = 0; plateau[4][7] = 0; plateau[5][7] = 0; plateau[6][7] = 0; plateau[7][7] = 0; plateau[8][7] = 0;
		plateau[0][8] = 1; plateau[1][8] = 0; plateau[2][8] = 0; plateau[3][8] = 0; plateau[4][8] = 0; plateau[5][8] = 0; plateau[6][8] = 0; plateau[7][8] = 0; plateau[8][8] = 0;
	}
	
	public int getNature(int x, int y)
	{
		//la nature de la case correspond aux unités
		return plateau[x][y] % 10;
	}
	
	public int getCouronne(int x, int y)
	{
		//le nombre de couronne correpond aux dizaines
		return plateau[x][y] / 10;
	}
	
	public int getScore()
	{
		int score = 0;
		
		//liste qui stocke l'ensemble des cases qui ont été comptées (pour ne pas les recompter 2 fois quand on inspecte la case suivante)
		ArrayList<int[]> globallyCounted = new ArrayList<int[]>();
		
		//liste qui stocke les cases voisines qui sont semblables à celle inspectée actuellement
		ArrayList<int[]> currentlyCounted = new ArrayList<int[]>();
		
		//on cherche pour chaque case les cases voisines semblables
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				//on compte uniquement si la case n'est pas vide
				if(getNature(x, y) != VIDE)
				{
					//on vide l'historique des cases voisines à la précédente
					currentlyCounted.clear();
					
					//on compte les cases voisines semblables à celle à la position (x, y)
					count(globallyCounted, currentlyCounted, getNature(x, y), x, y);
					
					//on compte les couronnes en parcourant la liste des cases voisines semblables
					int couronnes = 0;
					for(int[] position : currentlyCounted)
					{
						couronnes += getCouronne(position[0], position[1]);
					}
					
					//les cases correspondent à la taille de la liste des cases voisines semblables
					int cases = currentlyCounted.size();
					
					//le score est calculé commme le produit des couronnes et des cases
					score += couronnes * cases;
				}
			}
		}
		
		return score;
	}
	
	//méthode privée (qui ne peut être appelé en dehors de la classe) qui sert au calcul du score
	private void count(ArrayList<int[]> globallyCounted, ArrayList<int[]> currentlyCounted, int nature, int x, int y)
	{
		//hors du plateau -> on s'arrête là
		if(x < 0 || y < 0 || x > 8 || y > 8)
			return;
		
		//pas la même nature -> on s'arrête là
		if(getNature(x, y) != nature)
			return;
		
		//on vérifie si la case a été comptée auparavant -> on s'arrête là
		int[] currentPos = {x, y};
		for(int[] pos : globallyCounted)
		{
			if(pos[0] == currentPos[0] && pos[1] == currentPos[1])
				return;
		}
		
		//on ajoute la case à l'historique des cases comptées 
		globallyCounted.add(currentPos);
		currentlyCounted.add(currentPos);
		
		//on inspecte les 4 cases voisines
		count(globallyCounted, currentlyCounted, nature, x - 1, y);
		count(globallyCounted, currentlyCounted, nature, x, y - 1); 
		count(globallyCounted, currentlyCounted, nature, x + 1, y);
		count(globallyCounted, currentlyCounted, nature, x, y + 1);
	}
}
