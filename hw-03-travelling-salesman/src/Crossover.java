import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crossover {
    private final Path firstParent;
    private final Path secondParent;

    public Crossover(Path firstParent, Path secondParent) {
        this.firstParent = firstParent;
        this.secondParent = secondParent;
    }

    List<Path> twoPointCrossover() {
        int numberOfCities = firstParent.getPath().length;
        int[] firstParentPath = firstParent.getPath();
        int[] secondParentPath = secondParent.getPath();

        int[] firstChild = new int[numberOfCities];
        int[] secondChild = new int[numberOfCities];

        int left = Main.getRandomIntInRange(0, numberOfCities - 1);
        int right = Main.getRandomIntInRange(left + 1, numberOfCities - 1);

        for (int i = 0; i < numberOfCities; ++i) {
            if (i >= left && i <= right) {
                firstChild[i] = firstParentPath[i];
                secondChild[i] = secondParentPath[i];
            } else {
                firstChild[i] = -1;
                secondChild[i] = -1;
            }
        }

        Set<Integer> childTwoGenes = new HashSet<>();

        Set<Integer> childOneGenes = new HashSet<>();

        for (int gene : firstChild) {
            if (gene != -1) {
                childOneGenes.add(gene);
            }
        }

        for (int gene : secondChild) {
            if (gene != -1) {
                childTwoGenes.add(gene);
            }
        }

        // TODO not finished
        // the first child
        fillChild(numberOfCities, secondParentPath, firstChild, childOneGenes, left, right);

        // the same for the second child
        fillChild(numberOfCities, firstParentPath, secondChild, childTwoGenes, left, right);

        int[][] travelPrices = firstParent.getTravelPrices();
        List<Path> childrenPaths = new ArrayList<>();
        childrenPaths.add(new Path(travelPrices, firstChild));
        childrenPaths.add(new Path(travelPrices, secondChild));

        return childrenPaths;
    }

    private void fillChild(int numberOfCities, int[] otherParent, int[] child, Set<Integer> childGenes, int left,
                           int right) {
        int indexForChild = right + 1;
        for (int i = right + 1; i < otherParent.length; i++) {
            int gene = otherParent[i];

            if (!childGenes.contains(gene)) {
                child[indexForChild++] = gene;
                childGenes.add(gene);

                if (indexForChild >= numberOfCities) {
                    indexForChild = 0;
                }
            }
        }

        if (indexForChild == numberOfCities) {
            indexForChild = 0;
        }

        for (int i = 0; i < otherParent.length; i++) {
            int gene = otherParent[i];

            if (childGenes.size() == numberOfCities) {
                break;
            }

            if (!childGenes.contains(gene)) {
                child[indexForChild++] = gene;
                childGenes.add(gene);

                if (indexForChild >= numberOfCities) {
                    indexForChild = 0;
                }
            }
        }
    }

    // source : https://stackabuse.com/traveling-salesman-problem-with-genetic-algorithms-in-java/
    /*
    the fixed part is after the breakpoint
     */
    List<Path> partiallyMappedCrossoverWithOneFixedPoint() {
        int[][] travelPrices = firstParent.getTravelPrices();
        int numberOfCities = firstParent.getPath().length;

        int[] firstParentPath = firstParent.getPath();
        int[] secondParentPath = secondParent.getPath();

        int[] firstChild = Arrays.copyOf(firstParentPath, firstParentPath.length);
        int[] secondChild = Arrays.copyOf(secondParentPath, secondParentPath.length);

        int breakpoint = Main.getRandomIntInRange(0, numberOfCities);
        for (int i = 0; i < breakpoint; i++) {
            int value = secondParentPath[i];
            swapElements(firstChild, getElementIndex(firstParentPath, value), i);
        }

        for (int i = 0; i < breakpoint; i++) {
            int value = firstParentPath[i];
            swapElements(secondChild, getElementIndex(secondChild, value), i);
        }


        List<Path> childrenPaths = new ArrayList<>();
        childrenPaths.add(new Path(travelPrices, firstChild));
        childrenPaths.add(new Path(travelPrices, secondChild));

        return childrenPaths;
    }

    private void swapElements(int[] arr, int oldIndex, int newIndex) {
        int temp = arr[oldIndex];
        arr[oldIndex] = arr[newIndex];
        arr[newIndex] = temp;
    }

    private int getElementIndex(int[] arr, int value) {
        int index = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                index = i;
            }
        }

        return index;
    }
}
