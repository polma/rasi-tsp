import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CrossoverOperators {

    private Random rnd;
    public static final int OX = 0;
    public static final int CX = 1;
    private int currentOperator;


    public CrossoverOperators(int operatorId) {
        rnd = new Random();
        currentOperator = operatorId;
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

    private int[][] ox(int[] perm1, int[] perm2) {
        if (perm1.length == 1) {
            int[][] result = new int[2][];
            result[0] = perm2;
            result[1] = perm1;
            return result;
        }
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

    private int[][] cx(int[] perm1, int[] perm2) {
        if (perm1.length == 1) {
            int[][] result = new int[2][];
            result[0] = perm2;
            result[1] = perm1;
            return result;
        }

        final int length = perm1.length;
        boolean[] visited = new boolean[length];
        int visitedCount = 0;
        int idx = 0;
        int cycleCount = 1;
        int[][] result = new int[2][];

        result[0] = Arrays.copyOf(perm2, length);
        result[1] = Arrays.copyOf(perm1, length);

        List<Integer> currCycle = new ArrayList<Integer>();

        while (visitedCount < length) {
            currCycle.add(idx);
            ++visitedCount;
            visited[idx] = true;
            int corresponding = perm2[idx];

            idx = findIndex(perm1, corresponding);

            while (idx != currCycle.get(0)) {
                currCycle.add(idx);
                ++visitedCount;
                visited[idx] = true;
                corresponding = perm2[idx];
                idx = findIndex(perm1, corresponding);
            }

            if (cycleCount++ % 2 != 0) {
                for (int i : currCycle) {
                    int temp = result[0][i];
                    result[0][i] = result[1][i];
                    result[1][i] = temp;
                }
            }

            idx = (currCycle.get(0) + 1) % length;
            while (visited[idx] && visitedCount < length) {
                ++idx;
                idx = idx >= length ? 0 : idx;
            }

            currCycle.clear();
        }

        return result;
    }

    private int findIndex(int[] array, int element) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == element) return i;
        }
        return -1;
    }

    public int[][] mate(int[] perm1, int[] perm2) {
        if (currentOperator == OX)
            return ox(perm1, perm2);
        if (currentOperator == CX)
            return cx(perm1, perm2);
        return cx(perm1, perm2); //default
    }

    public void setCurrentOperator(int opId) {
        currentOperator = opId;
    }

    public static void main(String[] args) {
        int[] perm1 = {7, 8, 3, 6, 4, 5};
        int[] perm2 = {8, 7, 5, 6, 3, 4};

        CrossoverOperators cops = new CrossoverOperators(CrossoverOperators.CX);

        int[][] res = cops.mate(perm1, perm2);

        for (int i : res[0]) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for (int i : res[1]) {
            System.out.print(i);
            System.out.print(" ");
        }
    }
}
