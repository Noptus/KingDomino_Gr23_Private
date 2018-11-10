import java.util.Random;

public class Dominos 
{
	
	//CONSTANTES (POUR PLUS DE CLARTE DANS LE CODE)

	//on definit la nature des cases comme des entiers
	public static final int MONTAGNE = 1;
	public static final int PLAINE   = 2;
	public static final int RUINE    = 3;
	public static final int FORET    = 4;
	public static final int LAC      = 5;
	public static final int CHAMP    = 6;
		
	
	//ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)
	
	//liste d'entiers qui stocke tous nos dominos
	private int[][] dominos;
	
	
	//CONSTRUCTEUR (prend nb en argument, ce qui correspond au nb de dominos a jouer pour cette partie)
	public Dominos(int nb)
	{
		dominos = new int[][]
				{	
				  {0,CHAMP,0,CHAMP,1}, // 1
				  {0,CHAMP,0,CHAMP,2}, // 2
				  {0,FORET,0,FORET,3}, // 3
				  {0,FORET,0,FORET,4}, // 4
				  {0,FORET,0,FORET,5}, // 5
				  {0,FORET,0,FORET,6}, // 6
				  {0,LAC,0,LAC,7}, // 7
				  {0,LAC,0,LAC,8}, // 8
				  {0,LAC,0,LAC,9}, // 9
				  {0,PLAINE,0,PLAINE,10}, // 0
				  {0,PLAINE,0,PLAINE,11}, // 11
				  {0,RUINE,0,RUINE,12}, // 12
				  
				  {0,CHAMP,0,FORET,13}, // 13
				  {0,CHAMP,0,LAC,14}, // 14
				  {0,CHAMP,0,PLAINE,15}, // 15
				  {0,CHAMP,0,RUINE,16}, // 16
				  {0,FORET,0,LAC,17}, // 17
				  {0,FORET,0,PLAINE,18}, // 18
				  {1,CHAMP,0,FORET,19}, // 19
				  {1,CHAMP,0,LAC,20}, // 20
				  {1,CHAMP,0,PLAINE,21}, // 21
				  {1,CHAMP,0,RUINE,22}, // 22
				  {1,CHAMP,0,MONTAGNE,23}, // 23
				  {1,FORET,0,CHAMP,24}, // 24
				  
				  {1,FORET,0,CHAMP,25}, // 25
				  {1,FORET,0,CHAMP,26}, // 26
				  {1,FORET,0,CHAMP,27}, // 27
				  {1,FORET,0,LAC,28}, // 28
				  {1,FORET,0,PLAINE,29}, // 29
				  {1,LAC,0,CHAMP,30}, // 30
				  {1,LAC,0,CHAMP,31}, // 31
				  {1,LAC,0,FORET,32}, // 32
				  {1,LAC,0,FORET,33}, // 33
				  {1,LAC,0,FORET,34}, // 34
				  {1,LAC,0,FORET,35}, // 35
				  {0,CHAMP,1,PLAINE,36}, // 36
				  
				  {0,LAC,1,PLAINE,37}, // 37
				  {0,CHAMP,1,RUINE,38}, // 38
				  {0,PLAINE,1,RUINE,39}, // 39
				  {1,MONTAGNE,0,CHAMP,40}, // 40
				  {0,CHAMP,2,PLAINE,41}, // 41
				  {0,LAC,2,PLAINE,42}, // 42
				  {0,CHAMP,2,RUINE,43}, // 43
				  {0,PLAINE,2,RUINE,44}, // 44
				  {2,MONTAGNE,0,CHAMP,45}, // 45
				  {0,RUINE,2,MONTAGNE,46}, // 46
				  {0,RUINE,2,MONTAGNE,47}, // 47
				  {0,CHAMP,3,MONTAGNE,48}, // 48
				};
		dominos = shuffleCut(nb);
	}
	
	//METHODES PUBLIQUES
	
	// Affichage des dominos
	public void print() 
	{
		
        for(int i = 0; i < dominos.length; i++)
        {
        	System.out.print(dominos[i][4] + " : ");
        	System.out.print(dominos[i][0]);
        	System.out.print(dominos[i][1] + " - ");
        	System.out.print(dominos[i][2]);
        	System.out.println(dominos[i][3]);
        }	
	}
	
	
	//METHODES PRIVEES, QUI SERVENT UNIQUEMENT A D'AUTRES METHODES DE CETTE CLASSE
	
	//melange et retourne uniquement un certain nb de dominos
	private int[][] shuffleCut(int nb) 
	{
		
		// On melange tous les dominos 
        int l = dominos.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < l; i++) 
        {
            int element = i + random.nextInt(l - i);
            swap(dominos, i, element);
        }
        
        
        // On en garde nb
        int[][] dominosBonneTaille = new int[nb][5];
        for (int i = 0; i < nb; i++)
        {
	        for (int j = 0; j < 5; j++)
	        {
	        	dominosBonneTaille[i][j] = dominos[i][j];
	        }
        }
        
        // On ressort la liste melangee et raccourcie
        System.out.println((nb) +" Dominos ont été mélangés ...");
        return dominosBonneTaille;
     }
	
	//echange de place deux dominos dans la liste
	private void swap(int[][] a, int i, int change) 
    {
        int[] helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
}


