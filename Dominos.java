import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	public List<int[]> dominos = new ArrayList<>();
	
	// Retourne le nb de dominos au hasard et les supprime de la liste
	public int[][] GetAndDelete_Dominos(int nb) {
		
		int [][] choisis = new int[nb][5];
		for(int i = 0; i < nb; i++)
		{
			Random rand = new Random();
			int n = rand.nextInt( dominos.size() ) ;
			
			int[] Choisi = dominos.get(n);
			dominos.remove(n);
			
			choisis[i] = Choisi;
		}
		return choisis;
	}
	
	//CONSTRUCTEUR (prend nb en argument, ce qui correspond au nb de dominos a jouer pour cette partie)
	public Dominos(int nb)
	{
	
				   int[] D1 = { 0 , CHAMP , 0 , CHAMP , 1 };  dominos.add(D1);
				   int[] D2 = { 0 , CHAMP , 0 , CHAMP , 2 };  dominos.add(D2);
				   int[] D3 = { 0 , FORET , 0 , FORET , 3 };  dominos.add(D3);
				   int[] D4 = { 0 , FORET , 0 , FORET , 4 };  dominos.add(D4);
				   int[] D5 = { 0 , FORET , 0 , FORET , 5 };  dominos.add(D5);
				   int[] D6 = { 0 , FORET , 0 , FORET , 6 };  dominos.add(D6);
				   int[] D7 = { 0 , LAC , 0 , LAC , 7 };      dominos.add(D7);
				   int[] D8 = { 0 , LAC , 0 , LAC , 8 };      dominos.add(D8);
				   int[] D9 = { 0 , LAC , 0 , LAC , 9 };      dominos.add(D9);
				   int[] D10= { 0 , PLAINE , 0 , PLAINE , 10 };  dominos.add(D10);
				   int[] D11= { 0 , PLAINE , 0 , PLAINE , 11 };  dominos.add(D11);
				   int[] D12= { 0 , RUINE , 0 , RUINE , 12 };  dominos.add(D12);
				  
				   int[] D13 = { 0 , CHAMP , 0 , FORET , 13 };  dominos.add(D13);
				   int[] D14 = { 0 , CHAMP , 0 , LAC , 14 };  dominos.add(D14);
				   int[] D15 = { 0 , CHAMP , 0 , PLAINE , 15 };  dominos.add(D15);
				   int[] D16 = { 0 , CHAMP , 0 , RUINE , 16 };  dominos.add(D16);
				   int[] D17 = { 0 , FORET , 0 , LAC , 17 };  dominos.add(D17);
				   int[] D18 = { 0 , FORET , 0 , PLAINE , 18 };  dominos.add(D18);
				   int[] D19 = { 1 , CHAMP , 0 , FORET , 19 };  dominos.add(D19);
				   int[] D20 = { 1 , CHAMP , 0 , LAC , 20 };  dominos.add(D20);
				   int[] D21 = { 1 , CHAMP , 0 , PLAINE , 21 };  dominos.add(D21);
				   int[] D22 = { 1 , CHAMP , 0 , RUINE , 22 };  dominos.add(D22);
				   int[] D23 = { 1 , CHAMP , 0 , MONTAGNE , 23 };  dominos.add(D23);
				   int[] D24 = { 1 , FORET , 0 , CHAMP , 24 };  dominos.add(D24);
				  
				   int[] D25= { 1 , FORET , 0 , CHAMP , 25 };  dominos.add(D25);
				   int[] D26= { 1 , FORET , 0 , CHAMP , 26 };  dominos.add(D26);
				   int[] D27= { 1 , FORET , 0 , CHAMP , 27 };  dominos.add(D27);
				   int[] D28= { 1 , FORET , 0 , LAC , 28 };  dominos.add(D28);
				   int[] D29= { 1 , FORET , 0 , PLAINE , 29 };  dominos.add(D29);
				   int[] D30= { 1 , LAC , 0 , CHAMP , 30 };  dominos.add(D30);
				   int[] D31= { 1 , LAC , 0 , CHAMP , 31 };  dominos.add(D31);
				   int[] D32= { 1 , LAC , 0 , FORET , 32 };  dominos.add(D32);
				   int[] D33= { 1 , LAC , 0 , FORET , 33 };  dominos.add(D33);
				   int[] D34= { 1 , LAC , 0 , FORET , 34 };  dominos.add(D34);
				   int[] D35= { 1 , LAC , 0 , FORET , 35 };  dominos.add(D35);
				   int[] D36= { 0 , CHAMP , 1 , PLAINE , 36 };  dominos.add(D36);
				  
				   int[] D37= { 0 , LAC , 1 , PLAINE , 37 };  dominos.add(D37);
				   int[] D38= { 0 , CHAMP , 1 , RUINE , 38 };  dominos.add(D38);
				   int[] D39= { 0 , PLAINE , 1 , RUINE , 39 };  dominos.add(D39);
				   int[] D40= { 1 , MONTAGNE , 0 , CHAMP , 40 };  dominos.add(D40);
				   int[] D41= { 0 , CHAMP , 2 , PLAINE , 41 };  dominos.add(D41);
				   int[] D42= { 0 , LAC , 2 , PLAINE , 42 };  dominos.add(D42);
				   int[] D43= { 0 , CHAMP , 2 , RUINE , 43 };  dominos.add(D43);
				   int[] D44= { 0 , PLAINE , 2 , RUINE , 44 };  dominos.add(D44);
				   int[] D45= { 2 , MONTAGNE , 0 , CHAMP , 45 };  dominos.add(D45);
				   int[] D46= { 0 , RUINE , 2 , MONTAGNE , 46 };  dominos.add(D46);
				   int[] D47= { 0 , RUINE , 2 , MONTAGNE , 47 };  dominos.add(D47);
				   int[] D48= { 0 , CHAMP , 3 , MONTAGNE , 48 };  dominos.add(D48);
				  
				  
				  Collections.shuffle(dominos);

			      for(int i=0 ; i<48-nb ; i++){
			    	  dominos.remove(i);
			      }

				
	}
	
	//METHODES PUBLIQUES
	
	// Affichage des dominos
	public void print() 
	{
		
        for(int i = 0; i < dominos.size(); i++)
        {
        	System.out.print(dominos.get(i)[4] + "  : " );
        	System.out.print(dominos.get(i)[0]);
        	System.out.print(dominos.get(i)[1] + "  -  ");
        	System.out.print(dominos.get(i)[2]);
        	System.out.println(dominos.get(i)[3]);
        }	
	}
	
		
	
}
