import java.util.ArrayList;

public class InversionsCounter {
    int countInversionsSlow(ArrayList<Integer> arr) {
        int numberOfInversions = 0;

        for (int i = 0; i < arr.size(); i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i) > arr.get(j)) {
                    numberOfInversions++;
                }
            }
        }

        return numberOfInversions;
    }
}
