import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	
	private HashMap<String, Clip> clips = new HashMap<String, Clip>();
	
	private boolean musique = true;
	private boolean effets = true;
	
	public SoundPlayer()
	{
		load("applaudissement");
		load("bad");
		load("button");
		load("champ");
		load("cheval");
		load("grenouille");
		load("musique");
		load("oiseau");
		load("pioche");
		load("power_1");
		load("power_2");
		load("power_3");
		load("sorciere");
		load("vagues");
		
	}
	
	public void load(String name)
	{
		try {
			Clip clip = AudioSystem.getClip();
			File file = new File("Son//" + name + ".wav");
			clip.open(AudioSystem.getAudioInputStream(file));
			clips.put(name, clip);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	
	public void playAudio(String sound) {
		
		if(sound.equals("musique")) //il s'agit de la musique du jeu
		{
			if(!musique) //desative
				return;
			
			clips.get(sound).loop(1000); //la musique est jouee en boucle
			clips.get(sound).start();
		}
		else //il s'agit d'un effet audio
		{
			if(!effets) //desactive
				return;
			
			clips.get(sound).setMicrosecondPosition(0); //le son reprend au debut
			clips.get(sound).start();
		}

	}
	
	public void set(boolean musique, boolean effets)
	{
		this.musique = musique;
		this.effets = effets;
		if(musique == false) //on arrete la musique
			clips.get("musique").stop();
		else
			clips.get("musique").start();
	}
	

	public void close()
	{
		for(Clip clip : clips.values())
		{
			clip.stop();
			clip.close();
		}
		clips.clear();
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