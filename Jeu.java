
public class Jeu {

	public static void main(String[] args) {
		boolean souhaiteRejouer = false;
		SoundPlayer s = new SoundPlayer();
		s.playAudio("musique");
		do {
			
			// on affiche les menus
			MenuParametres menuParametres = new MenuParametres();
			if (menuParametres.display() == false) // l'utilisateur a quitte le menu, on ferme le programme
			{
				menuParametres.dispose();
				break;
			}
			s.set(menuParametres.getMusique(), menuParametres.getEffets());
			s.playAudio("button");
			menuParametres.dispose();

			MenuCouleurs menuCouleurs = new MenuCouleurs(menuParametres.getNbJoueurs(), menuParametres.getNbIA());
			if (menuCouleurs.display() == false) // l'utilisateur a quitte le menu, on ferme le programme
			{
				menuCouleurs.dispose();
				break;
			}
			s.playAudio("button");
			menuCouleurs.dispose();

			// on cree une nouvelle partie
			Partie partie = new Partie(menuParametres.getParametres(), menuCouleurs.getCouleurs(),
					menuCouleurs.getNoms(), s);

			// on joue la partie
			partie.jouer();
			
			s.playAudio("applaudissement");
			//on affiche l'ecran de fin de partie
			FinDePartie f = new FinDePartie(menuParametres.getParametres(), menuCouleurs.getCouleurs(), partie.getScores(), partie.getElapsedTime(), menuCouleurs.getNoms());
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
