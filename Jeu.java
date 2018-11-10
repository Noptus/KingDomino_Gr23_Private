import java.util.Scanner;

public class Jeu 
{

	public static void main(String[] args) 
	{
		do
		{
			//on cree une nouvelle partie
			Partie partie = new Partie();
			
			//on joue la partie
			partie.jouer();
		}
		while(souhaiteRejouer());
	}
	
	//propose au joueur de rejouer
	public static boolean souhaiteRejouer()
	{
		Scanner reader = new Scanner(System.in);
		String reponse = "";
		do
		{
			System.out.println("Voulez-vous rejouer ? (y/n)");
			try
			{
				reponse = reader.nextLine();
			}
			catch(Exception e)
			{}
			if(!reponse.equals("y") && !reponse.equals("n"))
				System.out.println("veuillez repondre par y ou n");
		}
		while(!reponse.equals("y") && !reponse.equals("n"));
		
		if(reponse.equals("y"))
			return true;
		else
			return false;
	}

}
