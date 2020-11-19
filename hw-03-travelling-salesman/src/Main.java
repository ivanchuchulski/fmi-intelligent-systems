import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
possible crossovers
    1 point, 2 point
    partially mapped
    cycle

 */

/*
possible mutations
    swap
    insertion
    reverse
 */
public class Main {
    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        int numberOfCities = inputNumberOfCities();
        int maxSteps;
        int mutationStep;
        int[][] travelPrices = generateRandomMatrix(numberOfCities);
        List<Path> population = new ArrayList<>();

        while (population.size() < numberOfCities) {
            population.add(new Path(travelPrices));
        }

//        System.out.println(Arrays.deepToString(travelPrices));

        population.forEach(Path::printPath);

        List<Path> bestPaths = population.stream()
                .sorted(new PathLengthComparator())
                .limit((int) (numberOfCities * 0.3))
                .collect(Collectors.toList());

        System.out.println("best paths");
        bestPaths.forEach(Path::printPath);
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

        for (int i = 0 ; i < size ; ++i) {
            matrix[i][i] = 0;
            for (int j = i + 1 ; j < size ; ++j) {
                matrix[i][j] = getRandomInt(2 * size);
                matrix[j][i] = matrix[i][j];
            }
        }
        return matrix;
    }

    public static int getRandomInt(int bound) {
        if (bound <= 0) {
            return 0;
        }

//        random.setSeed(System.currentTimeMillis());

        return random.nextInt(bound) + 1;
    }
}
