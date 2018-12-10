import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

	public void playAudio(String sound) {

		try {
			File Clap = new File(sound + ".wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Clap));
			clip.start();
			System.out.println(sound + " plays");

		} catch (Exception e) {
			System.out.println("error playing " + sound);
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
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 2:
			playAudio("power_2");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 3:
			playAudio("power_3");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
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
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		switch (C2) {
		case 1:
			playAudio("power_1");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 2:
			playAudio("power_2");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 3:
			playAudio("power_3");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
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