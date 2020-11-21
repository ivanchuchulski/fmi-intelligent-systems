import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Random random = new Random(System.currentTimeMillis());
    private static final PathLengthComparator pathLengthComparator = new PathLengthComparator();

    public static void main(String[] args) {
        int numberOfCities = inputNumberOfCities();
        int[][] travelPrices = generateRandomMatrix(numberOfCities);

        GeneticTSP geneticTSP = new GeneticTSP(numberOfCities, travelPrices);

        geneticTSP.evolve();
    }

    private static int inputNumberOfCities() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter number of cities (<= 100) :  ");
        int result = scanner.nextInt();

        if (result > 100) {
            throw new IllegalArgumentException("error : number of cities must be below 100");
        }

        scanner.close();

        return result;
    }

    private static int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; ++i) {
            matrix[i][i] = 0;
            for (int j = i + 1; j < size; ++j) {
//                matrix[i][j] = getRandomInt(2 * size);

                matrix[i][j] = getRandomIntInRange(0, 2 * size) + 1 ;

                matrix[j][i] = matrix[i][j];
            }
        }

//        for (int[] rows : matrix) {
//            System.out.println(Arrays.toString(rows));
//        }
//
//        System.exit(1);

        return matrix;
    }

    public static int getRandomInt(int bound) {
        if (bound <= 0) {
            return 0;
        }
//        random.setSeed(System.currentTimeMillis());
        return random.nextInt(bound) + 1;
    }

    // source : https://www.baeldung.com/java-generating-random-numbers-in-range
    public static int getRandomIntInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static List<Path> getBestFromPopulation(int numberOfCities, List<Path> population) {
        final double BEST_PATHS_PERCENT = 0.2;

        return population.stream()
                .sorted(pathLengthComparator)
                .limit((int) (numberOfCities * BEST_PATHS_PERCENT))
                .collect(Collectors.toList());
    }
}
