
public class Jeu {

	public static void main(String[] args) {
		boolean souhaiteRejouer = false;
		SoundPlayer s = new SoundPlayer();
		s.playMusique("Musique", true);
		do {
			
			// on affiche les menus
			MenuParametres menuParametres = new MenuParametres();
			if (menuParametres.display() == false) // l'utilisateur a quitte le menu, on ferme le programme
				return;
			s.playAudio("power_1", true);

			MenuCouleurs menuCouleurs = new MenuCouleurs(menuParametres.getNbJoueurs(), menuParametres.getNbIA());
			if (menuCouleurs.display() == false) // l'utilisateur a quitte le menu, on ferme le programme
				return;
			s.playAudio("power_1", true);

			// on cree une nouvelle partie
			Partie partie = new Partie(menuParametres.getParametres(), menuCouleurs.getCouleurs(),
					menuCouleurs.getNoms());

			// on joue la partie
			partie.jouer();
			
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
			f.setVisible(false);
			souhaiteRejouer = f.souhaiteRejouer();
		} while (souhaiteRejouer);
		System.exit(0);
	}
}
