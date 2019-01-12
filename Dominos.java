import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dominos {

	// CONSTANTES (POUR PLUS DE CLARTE DANS LE CODE)

	// on definit la nature des cases comme des entiers
	public static final int MONTAGNE = 1;
	public static final int PLAINE = 2;
	public static final int RUINE = 3;
	public static final int FORET = 4;
	public static final int LAC = 5;
	public static final int CHAMP = 6;
	
	private static final String Fichier = "dominos.csv";
	private static BufferedReader br = null;
	private static String line = "";
	private static final String cvsSplitBy = ",";

	// ATTRIBUTS (PRIVES, ACCESSIBLES UNIQUEMENT DEPUIS CETTE CLASSE)

	// liste d'entiers qui stocke tous nos dominos
	private List<int[]> dominos = new ArrayList<>();

	// Retourne le nb de dominos au hasard et les supprime de la liste
	public int[][] GetAndDelete_Dominos(int nb) {

		int[][] choisis = new int[nb][5];
		for (int i = 0; i < nb; i++) {
			Random rand = new Random();
			int n = rand.nextInt(dominos.size());

			int[] Choisi = dominos.get(n);
			dominos.remove(n);

			choisis[i] = Choisi;
		}
		return choisis;
	}

	// CONSTRUCTEUR (prend nb en argument, ce qui correspond au nb de dominos a
	// jouer pour cette partie)
	public Dominos(int nb) {

		try {

			br = new BufferedReader(new FileReader(Fichier));
			while ((line = br.readLine()) != null) {

				String[] Elements = line.split(cvsSplitBy);

				if (!Elements[0].equals("NbCouronne1")) {

					int C1 = Integer.parseInt(Elements[0]);
					int C2 = getValueOfText(Elements[1]);
					int C3 = Integer.parseInt(Elements[2]);
					int C4 = getValueOfText(Elements[3]);
					int C5 = Integer.parseInt(Elements[4]);
					int[] D = { C1, C2, C3, C4, C5 };
					dominos.add(D);
				}

			}

		} catch (Exception e) {
			System.out.println("Erreur importation dominos csv");
		}

		Collections.shuffle(dominos);

		for (int i = 0; i < 48 - nb; i++) {
			dominos.remove(i);
		}

	}

	
	// METHODES 

	// Affichage des dominos
	public void print() {

		for (int i = 0; i < dominos.size(); i++) {
			System.out.print(dominos.get(i)[4] + "  : ");
			System.out.print(dominos.get(i)[0]);
			System.out.print(dominos.get(i)[1] + "  -  ");
			System.out.print(dominos.get(i)[2]);
			System.out.println(dominos.get(i)[3]);
		}
	}

	// Methode outil private
	private int getValueOfText(String Terrain) {

		switch (Terrain) {
		case "Mer":
			return 5;
		case "Montagne":
			return 1;
		case "Prairie":
			return 2;
		case "Champs":
			return 6;
		case "Mine":
			return 3;
		case "Foret":
			return 4;
		}

		return 0;
	}

}
