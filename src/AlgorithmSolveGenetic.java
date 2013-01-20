import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: pdr
 * Date: 1/19/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlgorithmSolveGenetic implements Algorithm_solve {

    private int iterationsCount;
    private int populationSize;
    private CrossoverOperators crossoverOperators;

    public AlgorithmSolveGenetic() {
        this.iterationsCount = 500;
        this.populationSize = 50;
        this.crossoverOperators = new CrossoverOperators();
    }

    public AlgorithmSolveGenetic(int iterationsCount, int populationSize) {
        this.iterationsCount = iterationsCount;
        this.populationSize = populationSize;
        this.crossoverOperators = new CrossoverOperators();
    }

    @Override
    public int[] run(Problem_instance pi, int[] list) {
        populationSize = populationSize % 2 == 0 ? populationSize : populationSize + 1;
        int[][] population = generateRandomPermutations(list);
        int halfPopulation = populationSize / 2;

        Random rnd = new Random();
        for (int i = 0; i < iterationsCount; ++i) {
            //pick 50% of population and combine pairs
            ArrayList<Integer> parentIndexes = new ArrayList<Integer>();
            boolean [] chosenParents = new boolean[populationSize];

            while (parentIndexes.size() < halfPopulation) {
                int random = rnd.nextInt(populationSize);
                if (!chosenParents[random]){
                    chosenParents[random] = true;
                    parentIndexes.add(random);
                }
            }
            boolean [] alreadySelected = new boolean[populationSize];
            for (int j = 0; j < halfPopulation; j += 2) {
                int getIndex = rnd.nextInt(halfPopulation);
                while (alreadySelected[getIndex])
                    getIndex = rnd.nextInt(halfPopulation);
                alreadySelected[getIndex] = true;
                int index1 = parentIndexes.get(getIndex);
                while (alreadySelected[getIndex])
                    getIndex = rnd.nextInt(halfPopulation);
                int index2 = parentIndexes.get(getIndex);
                alreadySelected[getIndex] = true;

                int[][] offspring = crossoverOperators.ox(population[index1], population[index2]);
                population[index1] = offspring[1];
                population[index2] = offspring[2];
            }

            //apply 2-opt on half of the remaining set
            int quarterPopulation = halfPopulation / 2;
            for (int j = 0; j < quarterPopulation; ++j) {
                int tourIndex = rnd.nextInt();
                while (chosenParents[tourIndex])
                    tourIndex = rnd.nextInt(populationSize);
                for (int k = 0; k < 10; ++k) {
                    //pick two pairs of adjacent cities
                    int i1 = 1, i2 = 1;
                    while (i1 == i2) {
                        i1 = rnd.nextInt(list.length - 1);
                        i2 = rnd.nextInt(list.length - 1);
                        if (Math.abs(i1 - i2) <= 1)
                            i1 = i2;
                    }

                    //check the 2opt condition
                    //reorder the cities if condition satisfied
                    int distance1 = pi.min_m[population[tourIndex][i1]][population[tourIndex][i1 + 1]] +
                            pi.min_m[population[tourIndex][i2]][population[tourIndex][i2 + 1]];
                    int distance2 = pi.min_m[population[tourIndex][i1]][population[tourIndex][i2]] +
                            pi.min_m[population[tourIndex][i1 + 1]][population[tourIndex][i2 + 1]];
                    if (distance1 > distance2) {
                        int temp = population[tourIndex][i1 + 1];
                        population[tourIndex][i1 + 1] = population[tourIndex][i2];
                        population[tourIndex][i2] = temp;
                    }

                    //we may wish to apply or-opt here
                }
            }
        }
        //choose the best tour and return it
        int minTourIndex = 0;
        int minTourWeight = computeTourWeight(pi, population[0]);

        for(int i=1; i<populationSize; ++i){
            int currentMin = computeTourWeight(pi, population[i]);
            if(currentMin < minTourWeight){
                minTourIndex = i;
                minTourWeight = currentMin;
            }
        }
        return population[minTourIndex];
    }

    private int computeTourWeight(Problem_instance pi, int[] tour){
        int total = 0;
        assert(tour.length > 1);
        for(int i=1; i<tour.length; ++i){
            total += pi.min_m[tour[i]][tour[i-1]];
        }
        return total;
    }

    private int[][] generateRandomPermutations(int[] list) {
        int[][] population = new int[populationSize][list.length];

        Random rnd = new Random();

        for (int i = 0; i < populationSize; ++i) {
            population[i] = Arrays.copyOf(list, list.length);
            for (int j = 0; j < list.length; ++j) {
                //swap values
                int temp = population[i][j];
                int rndIndex = rnd.nextInt(list.length);
                population[i][j] = population[i][rndIndex];
                population[i][rndIndex] = temp;
            }
        }

        return population;
    }
}
