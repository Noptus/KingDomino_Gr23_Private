import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

	public void playAudio(String sound, boolean B) {

		try {
			File Clap = new File("Son//" + sound + ".wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Clap));

			if (B) {
				clip.start();
				System.out.println(sound + " plays");
			} else if (!B) {
				clip.stop();
				System.out.println(sound + " stops");
			}

		} catch (Exception e) {
			System.out.println("error playing " + sound);
		}

	}

	public void playMusique(String sound, boolean B) {

		try {
			File Clap = new File("Son//" + sound + ".wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Clap));

			if (B) {
				clip.start();
				System.out.println(sound + " plays");
			} else if (!B) {
				clip.stop();
				System.out.println(sound + " stops");
			}

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
			playAudio("power_1", true);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 2:
			playAudio("power_2", true);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 3:
			playAudio("power_3", true);
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
			playAudio("pioche", true);
			break;
		case 2:
			playAudio("cheval", true);
			break;
		case 3:
			playAudio("grenouille", true);
			break;
		case 4:
			playAudio("oiseau", true);
			break;
		case 5:
			playAudio("vagues", true);
			break;
		case 6:
			playAudio("champ", true);
			break;

		}
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		}
		switch (C2) {
		case 1:
			playAudio("power_1", true);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 2:
			playAudio("power_2", true);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
			break;
		case 3:
			playAudio("power_3", true);
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
			playAudio("pioche", true);
			break;
		case 2:
			playAudio("cheval", true);
			break;
		case 3:
			playAudio("grenouille", true);
			break;
		case 4:
			playAudio("oiseau", true);
			break;
		case 5:
			playAudio("vagues", true);
			break;
		case 6:
			playAudio("champ", true);
			break;

		}

	}

}