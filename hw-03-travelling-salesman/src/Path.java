import java.util.Arrays;
import java.util.Random;

public class Path {
    private final static Random random = new Random(System.currentTimeMillis());
    private final int[][] travelPrices;
    private final int[] path;
    private int fitness;

    public Path(int[][] travelPrices) {
        this.travelPrices = travelPrices;
        this.path = new int[travelPrices.length];

        int city = 0;
        for (int i = 0; i < path.length; ++i) {
            this.path[i] = city++;
        }

        shuffleArray(this.path);

        calculatePathFitness();
    }

    public Path(int[][] travelPrices, int[] path, int fitness) {
        this.travelPrices = travelPrices;
        this.path = path;
        this.fitness = 0;

        calculatePathFitness();
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getPath() {
        return path;
    }

    void mutatePath() {
        int first = Main.getRandomInt(path.length);
        int second = Main.getRandomInt(path.length);

        // swap mutation
        int temp = path[first];
        path[first] = path[second];
        path[second] = temp;

        calculatePathFitness();
    }

    void printPath() {
        System.out.println(Arrays.toString(path)  + " " + getFitness());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Path path1 = (Path) o;
        return Arrays.equals(path, path1.path);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(path);
    }

    //    source : https://stackoverflow.com/a/1520212/9127495
    private void shuffleArray(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

    private void calculatePathFitness() {
        for (int i = 1 ; i < path.length ; ++i) {
            int fromCity = path[i - 1];
            int toCity = path[i];
            this.fitness += travelPrices[fromCity][toCity];
        }
    }
}
