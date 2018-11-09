import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Test_Chamber extends KingDomino_Tools{
		
		static Scanner reader = new Scanner(System.in);

		
		public static void main(String args[]){

			// Message d'intro, choix nb joueurs
			int NJ = Intro();
			
			// Generation de tous les dominos
			String[][] DominosDisponibles = CreateDominos(NJ);
			// Mélange et coupe des dominos
	        String[][] DominosShuffledCut = ShuffleCut(DominosDisponibles,NJ);
	        // Affichage pour check
			  //PrintDominos(DominosShuffledCut);
	        
	        
	        // Choix des couleurs pour les joueurs 
			List CouleursJoueurs = ColorChoice(NJ);
			
			// Savoir l'ordre initial
			List Ordre = DefOrderInit(NJ);
		}

		
		
				// Methodes avant le début de la partie
		
		// accueil, demande du nb de joueurs
		private static int Intro() {
			
			System.out.println(" [ Bienvenue dans Domi'Nations 1.0 ! ]");
			System.out.println("   Combien y aura t-il de joueurs ?");
			
			boolean isValid = false ;
			int NJ = 0;
			
			while ( ! isValid) {
				try {
					Scanner reader = new Scanner(System.in);
					NJ = reader.nextInt(); 
					
					if (NJ < 2 || NJ > 4) {
						System.out.println(" Cette valeur n'est pas valide !");
						System.out.println(" Veuillez entrer un entier entre 2 et 4.");	
						isValid = false;
					}
					else {	
						isValid = true; 
					}
				} catch(Exception E) {
					System.out.println(" Ceci n'est pas un entier !");
					System.out.println(" Veuillez entrer un entier entre 2 et 4.");
					isValid = false;
				}
			}
			
			System.out.println("Il y aura "+ NJ +" joueurs");
			return NJ;

			
		}
		
		// création de la liste complète et ordonnée
		private static String[][] CreateDominos(int Nbjoueurs) {
			
			String [][] DominosDisponibles = {
					
					/*
					 * Champs  	= C
					 * Plaines	= P
					 * Lacs		= L
					 * Forêts	= F
					 * Ruines	= R
					 * Montagne	= M
					 */
					
					  {"0","C","0","C","01"}, // 1
					  {"0","C","0","C","02"}, // 2
					  {"0","F","0","F","03"}, // 3
					  {"0","F","0","F","04"}, // 4
					  {"0","F","0","F","05"}, // 5
					  {"0","F","0","F","06"}, // 6
					  {"0","L","0","L","07"}, // 7
					  {"0","L","0","L","08"}, // 8
					  {"0","L","0","L","09"}, // 9
					  {"0","P","0","P","10"}, // 0
					  {"0","P","0","P","11"}, // 11
					  {"0","R","0","R","12"}, // 12
					  
					  {"0","C","0","F","13"}, // 13
					  {"0","C","0","L","14"}, // 14
					  {"0","C","0","P","15"}, // 15
					  {"0","C","0","R","16"}, // 16
					  {"0","F","0","L","17"}, // 17
					  {"0","F","0","P","18"}, // 18
					  {"1","C","0","F","19"}, // 19
					  {"1","C","0","L","20"}, // 20
					  {"1","C","0","P","21"}, // 21
					  {"1","C","0","R","22"}, // 22
					  {"1","C","0","M","23"}, // 23
					  {"1","F","0","D","24"}, // 24
					  
					  {"1","F","0","C","25"}, // 25
					  {"1","F","0","C","26"}, // 26
					  {"1","F","0","C","27"}, // 27
					  {"1","F","0","L","28"}, // 28
					  {"1","F","0","P","29"}, // 29
					  {"1","L","0","C","30"}, // 30
					  {"1","L","0","C","31"}, // 31
					  {"1","L","0","F","32"}, // 32
					  {"1","L","0","F","33"}, // 33
					  {"1","L","0","F","34"}, // 34
					  {"1","L","0","F","35"}, // 35
					  {"0","C","1","P","36"}, // 36
					  
					  {"0","L","1","P","37"}, // 37
					  {"0","C","1","R","38"}, // 38
					  {"0","P","1","R","39"}, // 39
					  {"1","M","0","C","40"}, // 40
					  {"0","C","2","P","41"}, // 41
					  {"0","L","2","P","42"}, // 42
					  {"0","C","2","R","43"}, // 43
					  {"0","P","2","R","44"}, // 44
					  {"2","M","0","C","45"}, // 45
					  {"0","R","2","M","46"}, // 46
					  {"0","R","2","M","47"}, // 47
					  {"0","C","3","M","48"}, // 48
					  
					};
			
			return DominosDisponibles;
		}

		// Affichage de la liste que l'on veut
		private static void PrintDominos(String[][] Dominos) {
			
	        for(int i=0; i<Dominos.length; i++){
            	System.out.print (Dominos[i][4] +" : ");
            	System.out.print (Dominos[i][0] +"/");
            	System.out.print (Dominos[i][1] +" - ");
            	System.out.print (Dominos[i][2] +"/");
            	System.out.println (Dominos[i][3] );
	        }
				
		}
		
		// Mélange des dominos et ajustement nombre
		public static String[][] ShuffleCut(String[][] Dominos, int N) {
			
				// On mélange tous les dominos 
		        int L = Dominos.length;
		        Random random = new Random();
		        random.nextInt();
		        for (int i = 0; i < L; i++) {
		            int element = i + random.nextInt(L - i);
		            swap(Dominos, i, element);
		        }
		        
		        // On en garde 12*le nombre de joueurs
		        String[][] Dominos_Bonne_Taille = new String[N*12][5];
		        for (int i = 0; i < N*12; i++){
			        for (int j = 0; j < 5; j++){
			        	Dominos_Bonne_Taille[i][j] = Dominos[i][j];
			        }
		        }
		        
		        // On ressort la liste mélangée et raccourcie
		        System.out.println( (12*N) +" Dominos ont été mélangés ...");
		        return Dominos_Bonne_Taille;
		    }


		
		
				// Methodes de debut de partie
		
		// Methode pour répartir les couleurs, savoir qui commence
		private static List ColorChoice (int NJ) {
			
			 List Couleurs = new LinkedList();
			 Couleurs.add("Bleu");
			 Couleurs.add("Jaune");
			 Couleurs.add("Rose");
			 Couleurs.add("Vert");
			 List CouleursFinales = new LinkedList();

			 
			 // pour chaque joueur
			 for(int i = 0; i < NJ; i++) {
				 
				 System.out.println("Joueur "+(i+1)+", ces couleurs sont disponibles :");

				 // afficher toutes les couleurs encore disponibles
				 for(int j = 0; j < Couleurs.size(); j++) {
					 System.out.println((j+1) + " - " + Couleurs.get(j));
				 }
				 
				 boolean isValid = false;
				 while( ! isValid ) {
					 try {
						 Scanner reader = new Scanner(System.in);
						 int ChoixCouleur = reader.nextInt(); 
						 
						 if(ChoixCouleur < 1 || ChoixCouleur > Couleurs.size() ) {
							 System.out.println("Cette valeur n'est pas valide !");
							 System.out.println("Veuillez entrer un int valide.");
							 isValid = false;
							 
						 } else {
							 
							 CouleursFinales.add(  Couleurs.get( ChoixCouleur-1 )  );
							 Couleurs.remove(ChoixCouleur-1);
							 isValid = true;
						 }
						 
					 }catch(Exception e) {
						 System.out.println("Ce n'est pas un int !");
						 System.out.println("Veuillez entrer un int valide.");
						 
					 } // Fin try catch
				 } // Fin while isValid	 
			 } // Fin for chaque joueur

			 System.out.println("Couleurs choisies :");
			 for(int j = 0; j < CouleursFinales.size(); j++) {
				 System.out.println("Joueur "+(j+1)+" : "+CouleursFinales.get(j) );
			 }
			 
			 
			 return CouleursFinales;
		}
		
		// Methode pour savoir qui commence
		private static List DefOrderInit(int NJ) {
			
			System.out.println("Ordre pour la première manche :");
			
			if ( NJ != 2 ) {
				
				List Players = new LinkedList();
				for(int i = 1; i <= NJ; i++) {    Players.add(i);	}

				shuffleList(Players);

				for(int i = 0; i < NJ; i++) {
					System.out.println("Joueur "+(Players.get(i))+" : Place = "+(i+1) );
				}
			
				return Players;
				
			} else {
				List Players = new LinkedList();
				Players.add(1);
				Players.add(2);
				Players.add(2);
				Players.add(1);

				shuffleList(Players);

				for(int i = 0; i < 4; i++) {
					System.out.println("Joueur "+(Players.get(i))+" : Place = "+(i+1) );
				}
			
				return Players;
			}
		}
		
		


				// TOOLS
		

		public static void shuffleList(List<Integer> a) {
	        int n = a.size();
	        Random random = new Random();
	        random.nextInt();
	        for (int i = 0; i < n; i++) {
	            int change = i + random.nextInt(n - i);
	            swap(a, i, change);
	        }
	    }

	    private static void swap(List<Integer> a, int i, int change) {
	        int helper = a.get(i);
	        a.set(i, a.get(change));
	        a.set(change, helper);
	    }	

	    private static void swap(String[][] a, int i, int change) {
	        String[] helper = a[i];
	        a[i] = a[change];
	        a[change] = helper;
	    }
	    
		
		
		



}