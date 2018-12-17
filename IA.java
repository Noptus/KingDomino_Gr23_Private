import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class IA {

	private Plateau p;

	public int[] getChoice(Plateau plateau, Pioche pioche) {

		int L = pioche.getSize();
		int max = 0;
		int[] MeilleurDomino = new int[5];

		for (int i = 0; i < L; i++) {
			if (pioche.getJoueurByIndex(i) == 0 && plateau.isPossible(pioche.getDominoByIndex(i))) {
				int Points = getMove(plateau, pioche.getDominoByIndex(i))[4];
				if (Points >= max) {
					MeilleurDomino = pioche.getDominoByIndex(i);
					max = Points;
				}

			}
		}

		return MeilleurDomino;
	}



	public int[] getMove(Plateau p, int[] Domino) {

		ArrayList<int[]> Moves = new ArrayList<int[]>();
		int[] Move = new int[5];

		System.out.println("");
		System.out.println("Domino pour moves : ");

		int Smax = 0;

		for (int i = 0; i < p.sizeX(); i++) {
			for (int j = 0; j < p.sizeY(); j++) {

				int S1 = p.tester(i - 1, j, i, j, Domino) - p.getScore(false);
				int S2 = p.tester(i + 1, j, i, j, Domino) - p.getScore(false);
				int S3 = p.tester(i, j - 1, i, j, Domino) - p.getScore(false);
				int S4 = p.tester(i, j + 1, i, j, Domino) - p.getScore(false);

				if (S1 >= Smax) {
					Smax = S1;
					Move[0] = i - 1;
					Move[1] = j;
					Move[2] = i;
					Move[3] = j;
					Move[4] = S1;
					Moves.add(Move.clone());
					System.out.println("Nouveau meilleur move : " + (i - 1) + "-" + (j) + " + " + (i) + "-" + (j));
					System.out.println(S1 + " points");
				}

				if (S2 >= Smax) {
					Smax = S2;
					Move[0] = i + 1;
					Move[1] = j;
					Move[2] = i;
					Move[3] = j;
					Move[4] = S2;
					Moves.add(Move.clone());
					System.out.println("Nouveau meilleur move : " + (i + 1) + "-" + (j) + " + " + (i) + "-" + (j));
					System.out.println(S2 + " points");
				}

				if (S3 >= Smax) {
					Smax = S3;
					Move[0] = i;
					Move[1] = j - 1;
					Move[2] = i;
					Move[3] = j;
					Move[4] = S3;
					Moves.add(Move.clone());
					System.out.println("Nouveau meilleur move : " + (i) + "-" + (j - 1) + " + " + (i) + "-" + (j));
					System.out.println(S3 + " points");
				}

				if (S4 >= Smax) {
					Smax = S4;
					Move[0] = i;
					Move[1] = j + 1;
					Move[2] = i;
					Move[3] = j;
					Move[4] = S4;
					Moves.add(Move.clone());
					System.out.println("Nouveau meilleur move : " + (i) + "-" + (j + 1) + " + " + (i) + "-" + (j));
					System.out.println(S4 + " points");
				}

			}
		}

		int max = Moves.get(Moves.size() - 1)[4];

		for (int i = Moves.size() - 1; i >= 0; i--) {
			if (Moves.get(i)[4] < max) {
				Moves.remove(i);
			}
		}

		Random rn = new Random();
		int R = rn.nextInt(Moves.size());
		
		System.out.println(R);

		System.out.println(Moves.size());

		System.out.println("Move choisi : " + (Moves.get(R)[0]) + "-" + (Moves.get(R)[1]) + " + " + (Moves.get(R)[2])
				+ "-" + (Moves.get(R)[3]));
		System.out.println(Moves.get(R)[4] + " points");
		
		return Moves.get(R);

	}

}
