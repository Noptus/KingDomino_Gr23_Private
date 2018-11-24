
//classe pour stocker les parametres de la partie
public class Parametres 
{
	
	//tous les attributs sont publics pour y acceder partout
	public int nbJoueurs, nbIA, nbTotal;
	public boolean modeDynastie, modeEmpireMilieu, modeHarmonie, modeGrandDuel; 
	
	public Parametres()
	{}
	
	public Parametres(int nbJoueurs, int nbIA, boolean modeDynastie, boolean modeEmpireMilieu, boolean modeHarmonie, boolean modeGrandDuel)
	{
		
		this.nbJoueurs = nbJoueurs;
		this.nbIA = nbIA;
		this.nbTotal = nbJoueurs + nbIA;
		this.modeDynastie = modeDynastie;
		this.modeEmpireMilieu = modeEmpireMilieu;
		this.modeHarmonie = modeHarmonie;
		this.modeGrandDuel = modeGrandDuel;
	}
}
