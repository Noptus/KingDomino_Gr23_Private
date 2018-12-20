import java.util.ArrayList;
import java.util.Collections;

public class Pioche 
{

	private ArrayList<int[]> dominos; 
	private ArrayList<Integer> appartenances; //indique pour chaque domino a quel joueur il appartient (de base a 0 = a aucun joueur)
	
	
	public Pioche() //pioche vide pour le dernier tour
	{
		this.dominos = new ArrayList<int[]>();
		this.appartenances = new ArrayList<Integer>();
	}
	
	public Pioche(int[][] dominos)
	{
		this.dominos = new ArrayList<int[]>();
		
		this.appartenances = new ArrayList<Integer>();
		
		//on stocke les numeros des dominos dans une liste a part 
		// IMPORTANT : l'indice des elements de cette liste correspond a celle de la liste dominos 
		ArrayList<Integer> numeroDominos = new ArrayList<Integer>();
		
		for(int i = 0; i < dominos.length; i++)
		{
			numeroDominos.add(new Integer(dominos[i][4]));
			appartenances.add(0);
		}
		
		//on recuperer l'indice du numero du domino le plus petit, et on ajoute le dominos correspondant
		for(int i = 0; i < dominos.length; i++)
		{
			int indexMin = numeroDominos.indexOf(Collections.min(numeroDominos));
			this.dominos.add(dominos[indexMin]);
			numeroDominos.set(indexMin, new Integer(50)); //on lui attribue une valeur superieure a 48 pour qu'il ne soit plus le plus petit
		}

	}
		
		public Pioche(int[][] dominos, ArrayList<Integer> appartenances) //pioche pour le premier tour
		{
			this(dominos);
			this.appartenances = appartenances;
		}
		

	public int getSize()
	{
		return dominos.size();
	}
		
	//affiche tous les dominos de la pioche
	public void print()
	{
		for (int i = 0; i < dominos.size(); i++) 
		{
			System.out.println("Joueur " + appartenances.get(i) + " : " + (dominos.get(i)[0] * 10 + dominos.get(i)[1]) + "/" + (dominos.get(i)[2] * 10 + dominos.get(i)[3]) + " numero : " + dominos.get(i)[4]);
		}
	}
	
	//affiche le domino suivant a jouer du joueur
	public void printDomino(int joueur)
	{
		int indexJoueur = appartenances.indexOf(joueur);
		System.out.println("Domino a jouer : " + (dominos.get(indexJoueur)[0] * 10 + dominos.get(indexJoueur)[1]) + "/" + (dominos.get(indexJoueur)[2] * 10 + dominos.get(indexJoueur)[3]));
	}
	
	//retourne le domino suivant a jouer du joueur
	public int[] getDomino(int joueur)
	{
		int indexJoueur = appartenances.indexOf(joueur);
		return dominos.get(indexJoueur);
	}
	
	public int[] getDominoByIndex(int index)
	{
		return dominos.get(index);
	}
	
	public int getJoueurByIndex(int index)
	{
		return appartenances.get(index);
	}
	
	//supprime le domino suivant a jouer du joueur (appeler cette fonction une fois qu'il l'a place sur son terrain)
	public void deleteDomino(int joueur)
	{
		int indexJoueur = appartenances.indexOf(joueur);
		dominos.remove(indexJoueur);
		appartenances.remove(indexJoueur);
	}
	
	//reserve le domino numero indice de la pioche au joueur, si il est deja pris, retourne false
	public boolean choisir(int[] domino, int joueur)
	{

		if(appartenances.get(dominos.indexOf(domino)) == 0)
		{
			appartenances.set(dominos.indexOf(domino), joueur);
			return true;
		}
		else
			System.out.println("J'APPARTIENS DEJA A QUELQU'UN");
		return false;
	}
	
	//retourne l'ordre de jeu du tour suivant, qui correspond en fait a l'appartenance de chaque domino trie
	public ArrayList<Integer> getOrdre()
	{
		return (ArrayList<Integer>)appartenances.clone();
	}
	
}
