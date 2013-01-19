import java.util.ArrayList;
import java.util.Random;

public class CrossoverOperators {

    private Random rnd;

    public CrossoverOperators() {
        rnd = new Random();
    }

    private boolean in(int[] t, int e, int left, int right) {
        for (int i = left; i < right; ++i)
            if (t[i] == e)
                return true;
        return false;
    }

    private int[] selectCrossoverPoints(int length) {
        int[] result = new int[2];
        result[0] = result[1] = 1;
        while (result[0] >= result[1]) {
            result[0] = rnd.nextInt(length);
            result[1] = rnd.nextInt(length);
        }
        return result;
    }

    public int[][] ox(int[] perm1, int[] perm2) {
        int[][] result = new int[2][perm1.length];

        int[] crossoverPoints = selectCrossoverPoints(perm1.length);

        ArrayList<Integer> elements1 = new ArrayList<Integer>();
        ArrayList<Integer> elements2 = new ArrayList<Integer>();

        for (int i : perm1) {
            if (!in(perm2, i, crossoverPoints[0], crossoverPoints[1] + 1))
                elements1.add(i);
        }
        for (int i : perm2) {
            if (!in(perm1, i, crossoverPoints[0], crossoverPoints[1] + 1))
                elements2.add(i);
        }


        for (int i = 0; i < crossoverPoints[0]; ++i) {
            result[0][i] = elements2.get(i);
            result[1][i] = elements1.get(i);
        }

        for (int i = crossoverPoints[0]; i < crossoverPoints[1] + 1; ++i) {
            result[0][i] = perm1[i];
            result[1][i] = perm2[i];
        }

        int j = crossoverPoints[1] + 1;
        for (int i = crossoverPoints[0]; i < elements1.size(); ++i) {
            result[0][j] = elements2.get(i);
            result[1][j++] = elements1.get(i);
        }

        return result;
    }
    public static void main(String[] args) {
        int [] perm1 = {1,2,3,6,4,5};
        int [] perm2 = {2,6,5,3,1,4};

        CrossoverOperators cops = new CrossoverOperators();

        int [][] res = cops.ox(perm1, perm2);

        for(int i: res[0]){
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for(int i: res[1]){
            System.out.print(i);
            System.out.print(" ");
        }
    }
}
