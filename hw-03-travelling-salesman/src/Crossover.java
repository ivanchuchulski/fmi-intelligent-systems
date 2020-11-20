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

        int left = Main.getRandomIntInRange(0, numberOfCities);
        int right = Main.getRandomIntInRange(left + 1, numberOfCities);

        for (int i = 0; i < numberOfCities; ++i) {
            if (i >= left && i <= right) {
                firstChild[i] = firstParentPath[i];
                secondChild[i] = secondParentPath[i];
            } else {
                firstChild[i] = -1;
                secondChild[i] = -1;
            }
        }

        Set<Integer> childOneGenes = new HashSet<>();
        Set<Integer> childTwoGenes = new HashSet<>();

        for (int gene : firstChild) {
            childOneGenes.add(gene);
        }

        for (int gene : secondChild) {
            childTwoGenes.add(gene);
        }

        // TODO not finished
        int indexForFirst = right;
        for (int i = right + 1; i < secondParentPath.length; i++) {
            int gene = secondParentPath[i];

            if (!childOneGenes.contains(gene)) {
                firstChild[right++] = gene;
                childOneGenes.add(gene);

                if (right >= numberOfCities) {
                    right = 0;
                }
            }
        }

        for (int i = 0; i < left; i++) {
            int gene = secondParentPath[i];

            if (!childOneGenes.contains(gene)) {
                firstChild[right++] = gene;
                childOneGenes.add(gene);

                if (right >= numberOfCities) {
                    right = 0;
                }
            }
        }

        // the same for the second child


        int[][] travelPrices = firstParent.getTravelPrices();
        List<Path> childrenPaths = new ArrayList<>();
        childrenPaths.add(new Path(travelPrices, firstChild));
        childrenPaths.add(new Path(travelPrices, secondChild));

        return childrenPaths;
    }

    List<Path> partiallyMappedCrossover() {
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
