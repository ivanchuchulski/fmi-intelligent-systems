import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneticTSP {
    private static final Random random = new Random(System.currentTimeMillis());
    private static final PathLengthComparator pathLengthComparator = new PathLengthComparator();

    private final int populationSize = 100;
    private final int maxSteps = populationSize * 100;

    private final double BEST_PATHS_PERCENT = 0.2;

    private final double mutationPercent = 0.05;

    private final int numberOfCities;
    private final int[][] travelPrices;
    private int numberOfMutations = 0;

    private List<Path> bestsAtStart;
    private List<Path> bestPathsAtEnd;

    public GeneticTSP(int numberOfCities, int[][] travelPrices) {
        this.numberOfCities = numberOfCities;
        this.travelPrices = travelPrices;
    }

    public void evolve() {
        int currentSteps = 0;
        int numberOfChildren = 0;

        Set<Path> population = new HashSet<>();

        while (population.size() < numberOfCities) {
            population.add(new Path(travelPrices));
        }

        bestsAtStart = getBestFromPopulation(numberOfCities, population);

        System.out.println("best paths at start");
        bestsAtStart.forEach(Path::printPath);

        do {
            int counter = 0;

            printProgression(currentSteps, getBestFromPopulation(numberOfCities, population));

            do {
                List<Path> bestPaths = getBestFromPopulation(numberOfCities, population);

//                printProgression(currentSteps, bestPaths);

                Path firstParent = bestPaths.get(Main.getRandomIntInRange(0, bestPaths.size()));
                Path secondParent = bestPaths.get(Main.getRandomIntInRange(0, bestPaths.size()));

                if (firstParent == secondParent) {
                    continue;
                }

                Crossover crossover = new Crossover(firstParent, secondParent);
                List<Path> children = crossover.twoPointCrossover();
                numberOfChildren += 2;

                mutateChildren(children);

                population.addAll(children);

            } while (counter++ < 0.2 * populationSize);

            population = population.stream()
                    .sorted(pathLengthComparator)
                    .limit(numberOfCities)
                    .collect(Collectors.toSet());

        } while (currentSteps++ < maxSteps);



        bestPathsAtEnd = getBestFromPopulation(numberOfCities, population);

        System.out.println("best paths at end");
        bestPathsAtEnd.forEach(Path::printPath);

        System.out.println("total number of steps : " + getMaxSteps());
        System.out.println("number of children : " + numberOfChildren);
        System.out.println("number of mutations : " + getNumberOfMutations());
    }

    private static void printStepInfo(List<Path> bestPaths, Path firstParent, Path secondParent, List<Path> children) {
        System.out.println("best paths");
        bestPaths.forEach(Path::printPath);

        System.out.println("parents");
        firstParent.printPath();
        secondParent.printPath();

        System.out.println("children");
        for (Path child : children) {
            child.printPath();
        }
    }

    private List<Path> getBestFromPopulation(int numberOfCities, Set<Path> population) {
        return population.stream()
                .sorted(pathLengthComparator)
                .limit((int) (numberOfCities * BEST_PATHS_PERCENT))
                .collect(Collectors.toList());
    }

    private void mutateChildren(List<Path> children) {
        if (random.nextFloat() <= mutationPercent) {
            numberOfMutations += 2;
            for (Path child : children) {
                child.mutatePath();
            }
        }
    }

    public void printProgression(int currentSteps, List<Path> bestPaths) {
        if (currentSteps == 10) {
            System.out.println("best paths after 10th step");
            bestPaths.forEach(Path::printPath);
        }

        if (currentSteps == maxSteps / 3) {
            System.out.println("best paths after 1/3 of the steps");
            bestPaths.forEach(Path::printPath);
        }

        if (currentSteps == 2 * maxSteps / 3) {
            System.out.println("best paths after 2/3 of the steps");
            bestPaths.forEach(Path::printPath);
        }
    }

    public int getNumberOfMutations() {
        return numberOfMutations;
    }

    public List<Path> getBestsAtStart() {
        return bestsAtStart;
    }

    public List<Path> getBestPathsAtEnd() {
        return bestPathsAtEnd;
    }

    public int getMaxSteps() {
        return maxSteps;
    }
}
