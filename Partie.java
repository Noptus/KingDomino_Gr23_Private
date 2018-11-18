import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Partie 
{
	
	//ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	Parametres p;
	private List<String> couleurs;
	private List<Integer> ordre;
	private Dominos dominos;
	private Plateau[] plateaux;

	
	//CONSTRUCTEUR
	public Partie(Parametres p, List<String> couleurs)
	{		
		this.p = p;
		this.couleurs = couleurs;
		
		ordre = defOrderInit();
		
		dominos = new Dominos(12 * p.nbTotal);
		
		//on affiche les dominos pour tester
		dominos.print();
		
		plateaux = new Plateau[p.nbTotal];
		for(int i = 0; i <p.nbTotal; i++)
		{
			plateaux[i] = new Plateau();
			
			//on affiche le plateau pour tester
			plateaux[i].print();
		}
		
	}
	
	
	//METHODES PUBLIQUES
	
	
	//deroulement de la partie, la ou tout se passera
	public void jouer()
	{
		//TANT QUE LA PARTIE N'EST PAS FINIE
		
			//piocher 4 dominos (a faire dans la classe dominos: supprimer les dominos pioches et les retourner ici)
			
			//FOR SUR L'ORDRE DE JEU
		
				//chaque joueur recupere le domino choisit au tour precedent, le place sur son plateau (a faire dans la classe plateau), puis choisit son domino pour le prochain tour
			
			// FIN FOR
		
			//on actualise l'ordre de jeu pour le prochain tour
		
		//FIN TANT QUE
	}
	
	
	//METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE
	
	
	// Methode pour savoir qui commence
	private List<Integer> defOrderInit() 
	{
		
		System.out.println("Ordre pour la première manche :");
		List<Integer> orderInit = new LinkedList<Integer>();
		
		if (p.nbTotal != 2 ) 
		{
			
			for(int i = 1; i <= p.nbTotal; i++) 
			{    
				orderInit.add(i);	
			}
			Collections.shuffle(orderInit);
			//shuffleList(orderInit);
	
			for(int i = 0; i < p.nbTotal; i++) 
			{
				System.out.println("Joueur "+(orderInit.get(i))+" : Place = "+(i+1) );
			}
			
			return orderInit;
			
		} 
		else 
		{
			orderInit.add(1);
			orderInit.add(2);
			orderInit.add(2);
			orderInit.add(1);

			Collections.shuffle(orderInit);
			//shuffleList(orderInit);

			for(int i = 0; i < 4; i++) 
			{
				System.out.println("Joueur "+(orderInit.get(i))+" : Place = "+(i+1) );
			}
			return orderInit;
		}
	}
	
	//melange la liste
	private void shuffleList(List<Integer> a) 
	{
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) 
        {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }
	
	//echange la position de deux elements d'une liste
	private static void swap(List<Integer> a, int i, int change) 
	{
        int helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }	
}
