
public class Jeu {

	public static void main(String[] args) {

		boolean souhaiteRejouer = false;
		SoundPlayer s = new SoundPlayer();
		s.playAudio("musique");
		do {
			
			// on affiche les menus
			MenuParametres menuParametres = new MenuParametres();
			if (menuParametres.display() == false) // l'utilisateur a quitte le menu, on ferme le programme
				break;
			
			s.set(menuParametres.getMusique(), menuParametres.getEffets());
			s.playAudio("button");
			menuParametres.dispose();

			MenuCouleurs menuCouleurs = new MenuCouleurs(menuParametres.getNbJoueurs(), menuParametres.getNbIA());
			if (menuCouleurs.display() == false) // l'utilisateur a quitte le menu, on ferme le programme
				break;
			
			s.playAudio("button");
			menuCouleurs.dispose();
			
			//variables necessaires pour le mode dynastie
			int[] score_cumule = new int[menuParametres.getNbJoueurs() + menuParametres.getNbIA()];
			long temps_cumule = 0;
			int nb_parties;
			if(menuParametres.getModeDynastie())
				nb_parties = 3;
			else
				nb_parties = 1;
			
			Parametres p = menuParametres.getParametres();
			for(int i = 0; i < nb_parties; i++)
			{
				// on cree une nouvelle partie
				Partie partie = new Partie(p, menuCouleurs.getCouleurs(),
						menuCouleurs.getNoms(), s);
				
				// on joue la partie
				partie.jouer();
				
				//on recupere le score et le temps ecoule
				int[] score = partie.getScores();
				for(int j = 0; j < score.length; j++)
				{
					score_cumule[j] += score[j];
				}
				temps_cumule += partie.getElapsedTime();
			}
			
			s.playAudio("applaudissement");
			//on affiche l'ecran de fin de partie
			FinDePartie f = new FinDePartie(menuParametres.getParametres(), menuCouleurs.getCouleurs(), score_cumule, temps_cumule, menuCouleurs.getNoms());
			f.setVisible(true);
			while (f.hasDecided() == false) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			souhaiteRejouer = f.souhaiteRejouer();
			f.setVisible(false);
			f.dispose();
		} while (souhaiteRejouer);
		s.close();
	}
}
