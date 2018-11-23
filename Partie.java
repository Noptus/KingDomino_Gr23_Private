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
		// test
		dominos.print();
		
		plateaux = new Plateau[p.nbTotal];
		for(int i = 0; i <p.nbTotal; i++)
		{
			plateaux[i] = new Plateau();
			System.out.println("");
			//on affiche le plateau pour tester
			plateaux[i].print();
		}
		
		
		// Pour chacune des 12 manches
        for(int i=1 ; i < 12 ; i++){
        	
        	Manche manche = new Manche();
        	
        }		
        
		
		
		

		
	}
	
	
	//METHODES PUBLIQUES
	
	
	//deroulement de la partie, la ou tout se passera
	public void jouer()
	{
		
		
        
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

	
}
