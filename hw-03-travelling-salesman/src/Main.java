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
    private static final Random random = new Random(System.currentTimeMillis());
    private static final PathLengthComparator pathLengthComparator = new PathLengthComparator();

    public static void main(String[] args) {
        int numberOfCities = inputNumberOfCities();
        int[][] travelPrices = generateRandomMatrix(numberOfCities);


        List<Path> population = new ArrayList<>();

        while (population.size() < numberOfCities) {
            population.add(new Path(travelPrices));
        }

        population.forEach(Path::printPath);

        List<Path> bestsAtStart = getBestFromPopulation(numberOfCities, population);

        int maxSteps = 5;
        int currentSteps = 0;
        double mutationPercent = 0.05;

        do {
            List<Path> bestPaths = getBestFromPopulation(numberOfCities, population);

            System.out.println("best paths");
            bestPaths.forEach(Path::printPath);

            Path firstParent = bestPaths.get(getRandomIntInRange(0, bestPaths.size()));
            Path secondParent = bestPaths.get(getRandomIntInRange(0, bestPaths.size()));

            if (firstParent == secondParent) {
                continue;
            }

            Crossover crossover = new Crossover(firstParent, secondParent);
            List<Path> children = crossover.partiallyMappedCrossover();

            System.out.println("parents");
            firstParent.printPath();
            secondParent.printPath();

            System.out.println("children");
            for (Path child : children) {
                child.printPath();
            }

            // mutation here

            population.addAll(children);

            population = population.stream()
                    .sorted(pathLengthComparator)
                    .limit(numberOfCities)
                    .collect(Collectors.toList());

        } while (currentSteps++ < maxSteps);

        List<Path> bestPaths = getBestFromPopulation(numberOfCities, population);

        System.out.println("best paths at start");
        bestsAtStart.forEach(Path::printPath);

        System.out.println("best paths at end");
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

//        System.out.println(Arrays.deepToString(matrix));

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
