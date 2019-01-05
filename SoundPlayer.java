import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	
	//stocker les clips pour pouvoir les fermer plus tard
	private ArrayList<Clip> clips = new ArrayList<Clip>();
	private ArrayList<String> sounds = new ArrayList<String>();
	
	private boolean musique = true;
	private boolean effets = true;
	
	public void playAudio(String sound) {
		
		if(sound.equals("musique")) //il s'agit de la musique du jeu
		{
			if(!musique) //desative
				return;
		}
		else //il s'agit d'un effet audio
		{
			if(!effets) //desactive
				return;
		}

		try {
			File Clap = new File("Son//" + sound + ".wav");
			clips.add(AudioSystem.getClip());
			sounds.add(sound);
			clips.get(clips.size()-1).open(AudioSystem.getAudioInputStream(Clap));
			clips.get(clips.size()-1).start();
		} catch (Exception e) {
			e.printStackTrace(); 
		}

	}
	
	public void set(boolean musique, boolean effets)
	{
		this.musique = musique;
		this.effets = effets;
		if(musique == false) //on arrete la musique
			stop("musique");
	}
	
	//ferme tous les sons en cours
	public void close()
	{
		for(Clip clip : clips)
		{
			clip.stop();
			clip.close();
		}
		clips.clear();
		sounds.clear();
	}
	
	//arrete de jouer le son en question s'il existe (et autant de fois qu'il existe)
	public void stop(String sound)
	{
		while(sounds.indexOf(sound) != -1)
		{
			int index = sounds.indexOf(sound);
			//on ferme le fichier audio
			clips.get(index).stop();
			clips.get(index).close();
			//On le supprime des deux listes
			clips.remove(index);
			sounds.remove(index);
		}
	}

	public void playDomino(int[] Domino) {
		int C1 = Domino[0];
		int C2 = Domino[2];
		int N1 = Domino[1];
		int N2 = Domino[3];

		switch (C1) {
		case 1:
			playAudio("power_1");
			break;
		case 2:
			playAudio("power_2");
			break;
		case 3:
			playAudio("power_3");
			break;
		default:
			break;
		}
		switch (N1) {
		case 1:
			playAudio("pioche");
			break;
		case 2:
			playAudio("cheval");
			break;
		case 3:
			playAudio("grenouille");
			break;
		case 4:
			playAudio("oiseau");
			break;
		case 5:
			playAudio("vagues");
			break;
		case 6:
			playAudio("champ");
			break;

		}
		switch (C2) {
		case 1:
			playAudio("power_1");
			break;
		case 2:
			playAudio("power_2");
			break;
		case 3:
			playAudio("power_3");
			break;
		default:
			break;
		}
		switch (N2) {
		case 1:
			playAudio("pioche");
			break;
		case 2:
			playAudio("cheval");
			break;
		case 3:
			playAudio("grenouille");
			break;
		case 4:
			playAudio("oiseau");
			break;
		case 5:
			playAudio("vagues");
			break;
		case 6:
			playAudio("champ");
			break;

		}

	}

}