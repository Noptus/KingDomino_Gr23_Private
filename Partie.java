import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Partie 
{
	
	//ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	private int nbJoueurs;
	private List<String> couleurs;
	private List<Integer> ordre;
	private Dominos dominos;
	private Plateau[] plateaux;

	
	//CONSTRUCTEUR
	public Partie()
	{
		nbJoueurs = intro();
		
		couleurs = colorChoice();
		
		ordre = defOrderInit();
		
		dominos = new Dominos(12 * nbJoueurs);
		
		//on affiche les dominos pour tester
		dominos.print();
		
		plateaux = new Plateau[nbJoueurs];
		for(int i = 0; i < nbJoueurs; i++)
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
		//TANT QUE LA PARTIE N'EST PAS FINIE (dans la classe plateau: verifer plateau du joueur, si un est complet, c'est fini)
		
			//piocher 4 dominos (a faire dans la classe dominos: supprimer les dominos pioches et les retourner ici)
			
			//FOR SUR L'ORDRE DE JEU
		
				//chaque joueur recupere le domino choisit au tour precedent, le place sur son plateau (a faire dans la classe plateau), puis choisit son domino pour le prochain tour
			
			// FIN FOR
		
			//on actualise l'ordre de jeu pour le prochain tour
		
		//FIN TANT QUE
	}
	
	
	//METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE
	
	//methode pour connaitre le nombre de joueur
	private int intro() 
	{
		
		System.out.println(" [ Bienvenue dans Domi'Nations 1.0 ! ]");
		System.out.println("   Combien y aura t-il de joueurs ?");
		
		boolean isValid = false ;
		
		int nb = 0;
		while ( ! isValid) 
		{
			try 
			{
				Scanner reader = new Scanner(System.in);
				nb = reader.nextInt(); 
				
				if (nb < 2 || nb > 4) 
				{
					System.out.println(" Cette valeur n'est pas valide !");
					System.out.println(" Veuillez entrer un entier entre 2 et 4.");	
					isValid = false;
				}
				else 
				{	
					isValid = true; 
				}
			} 
			catch(Exception E) 
			{
				System.out.println(" Ceci n'est pas un entier !");
				System.out.println(" Veuillez entrer un entier entre 2 et 4.");
				isValid = false;
			}
		}
		
		System.out.println("Il y aura "+ nb +" joueurs");
		return nb;
	}
	
	// Methode pour répartir les couleurs
	private List<String> colorChoice () 
	{
		
		 List<String> couleursRestantes = new LinkedList<String>();
		 couleursRestantes.add("bleu");
		 couleursRestantes.add("jaune");
		 couleursRestantes.add("rose");
		 couleursRestantes.add("vert");
		 List<String> couleursChoisies = new LinkedList<String>();

		 
		 // pour chaque joueur
		 for(int i = 0; i < nbJoueurs; i++) 
		 {
			 
			 System.out.println("Joueur "+ (i+1) +", ces couleurs sont disponibles :");

			 // afficher toutes les couleurs encore disponibles
			 for(int j = 0; j < couleursRestantes.size(); j++) 
			 {
				 System.out.println((j+1) + " - " + couleursRestantes.get(j));
			 }
			 
			 boolean isValid = false;
			 while( ! isValid ) 
			 {
				 try 
				 {
					 Scanner reader = new Scanner(System.in);
					 int choixCouleur = reader.nextInt(); 
					 
					 if(choixCouleur < 1 || choixCouleur > couleursRestantes.size() ) 
					 {
						 System.out.println("Cette valeur n'est pas valide !");
						 System.out.println("Veuillez entrer un int valide.");
						 isValid = false;
						 
					 } 
					 else 
					 {
						 
						 couleursChoisies.add(  couleursRestantes.get( choixCouleur-1 )  );
						 couleursRestantes.remove(choixCouleur-1);
						 isValid = true;
					 }
					 
				 }
				 catch(Exception e) 
				 {
					 System.out.println("Ce n'est pas un int !");
					 System.out.println("Veuillez entrer un int valide.");
					 
				 } // Fin try catch
			 } // Fin while isValid	 
		 } // Fin for chaque joueur

		 System.out.println("Couleurs choisies :");
		 for(int j = 0; j < couleursChoisies.size(); j++) 
		 {
			 System.out.println("Joueur "+(j+1)+" : "+ couleursChoisies.get(j) );
		 }
		 
		 
		 return couleursChoisies;
	}
	
	
	// Methode pour savoir qui commence
	private List<Integer> defOrderInit() 
	{
		
		System.out.println("Ordre pour la première manche :");
		List<Integer> orderInit = new LinkedList<Integer>();
		
		if (nbJoueurs != 2 ) 
		{
			
			for(int i = 1; i <= nbJoueurs; i++) 
			{    
				orderInit.add(i);	
			}

			shuffleList(orderInit);
	
			for(int i = 0; i < nbJoueurs; i++) 
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

			shuffleList(orderInit);

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
