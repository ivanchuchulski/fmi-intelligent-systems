import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NaiveBayesClassifier {
    private int attributesCount;
    private final List<DatasetEntry> datasetEntries;
    private final List<DatasetEntry> testSet;
    private final List<DatasetEntry> validatingSet;

    public NaiveBayesClassifier() {
        datasetEntries = new ArrayList<>();
        testSet = new ArrayList<>();
        validatingSet = new ArrayList<>();
    }

    public void classify() {
        int validationTimes = 10;

        readData();

        for (int i = 0; i < validationTimes; i++) {
            buildTestAndValidatingSets(i);
            buildModel();
            testSet.clear();
            validatingSet.clear();
        }
    }

    private void readData() {
        final String datasetPath = "resources" + File.separator + "house-votes-84.data";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(datasetPath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedLine = line.split(",");
                attributesCount = splittedLine.length - 1;

                String className = splittedLine[0];
                String[] attributes = Arrays.copyOfRange(splittedLine, 1, splittedLine.length);

                DatasetEntry datasetEntry = new DatasetEntry(className, attributes);
                datasetEntries.add(datasetEntry);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // performing ten fold cross-validation
    private void buildTestAndValidatingSets(int validationTimes) {
        int oneTenthPart = datasetEntries.size() / 10;

        Set<DatasetEntry> addedToTest = new HashSet<>();

        for (int i = 0; i < oneTenthPart; i++) {
            int index = validationTimes * oneTenthPart;
            DatasetEntry datasetEntry = datasetEntries.get(index);

            testSet.add(datasetEntry);
            addedToTest.add(datasetEntry);
        }

        for (DatasetEntry datasetEntry : datasetEntries) {
            if (!addedToTest.contains(datasetEntry)) {
                validatingSet.add(datasetEntry);
            }
        }
    }

    private void buildModel() {

    }

}
