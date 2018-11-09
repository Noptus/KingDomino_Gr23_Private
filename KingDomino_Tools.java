import java.util.List;
import java.util.Random;

public class KingDomino_Tools {

	
	public static void shuffleList(List<Integer> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(List<Integer> a, int i, int change) {
        int helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }	

    private static void swap(String[][] a, int i, int change) {
        String[] helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
    
}
