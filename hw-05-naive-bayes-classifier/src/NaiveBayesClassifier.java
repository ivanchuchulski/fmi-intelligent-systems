import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NaiveBayesClassifier {
    private int attributesCount;
    private final List<DatasetEntry> datasetEntries;
    private final List<DatasetEntry> testSet;
    private final List<DatasetEntry> validatingSet;
    private final Set<String> classNames;

    // {classname -> {attribute -> #votes} }
    private final Map<String, Map<String, Integer>> model;

    public NaiveBayesClassifier() {
        datasetEntries = new ArrayList<>();
        testSet = new ArrayList<>();
        validatingSet = new ArrayList<>();
        classNames = new HashSet<>();
        model = new HashMap<>();
    }

    public void classify() {
        int validationTimes = 10;

        readData();
        double accuracySum = 0.0;

        for (int i = 0; i < validationTimes; i++) {
            buildTestAndValidatingSets(i);
            buildModel();

            double accuracy = train();

            System.out.printf("Accuracy: %.2f%s%n", 100 * accuracy, "%");
            accuracySum += accuracy;

            testSet.clear();
            validatingSet.clear();
            model.clear();
        }

        System.out.printf("Total: %.2f%s%n", 100 * (accuracySum / 10.0), "%");
    }

    private void readData() {
        final String datasetPath = "resources" + File.separator + "house-votes-84.data";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(datasetPath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splittedLine = line.split(",");

                String className = splittedLine[0];
                String[] attributes = Arrays.copyOfRange(splittedLine, 1, splittedLine.length);

                attributesCount = splittedLine.length - 1;
                classNames.add(className);

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
        for (String className : classNames) {
            model.put(className, new HashMap<>());
        }

        for (DatasetEntry datasetEntry : validatingSet) {
            String className = datasetEntry.getClassName();
            String[] attributes = datasetEntry.getAttributes();

            Map<String, Integer> votesForAttributes = model.get(className);

            for (String attribute : attributes) {
                int count = votesForAttributes.getOrDefault(attribute, 0);
                votesForAttributes.put(attribute, count + 1);
            }
        }

//        Set<String> classNames = model.keySet();
//        for (String className : classNames) {
//            Map<String, Integer> stringIntegerMap = model.get(className);
//            System.out.println(stringIntegerMap.size());
//        }
    }

    private double train() {
        int correctCount = 0;

        for (DatasetEntry datasetEntry : testSet) {
            String predictedClassName = findClassNameForEntry(datasetEntry);

            if (predictedClassName.equals(datasetEntry.getClassName())) {
                correctCount++;
            }
        }

        return correctCount * 1.0 / testSet.size();
    }

    private String findClassNameForEntry(DatasetEntry datasetEntry) {
        String bestClassName = "";
        double bestProbability = Double.MIN_VALUE;

        for (Map.Entry<String, Map<String, Integer>> classNameVotes : model.entrySet()) {
            Map<String, Integer> votesForAttributes = classNameVotes.getValue();

            double classNameProbability = calculateProbability(datasetEntry, votesForAttributes);

            if (classNameProbability > bestProbability) {
                bestProbability = classNameProbability;
                bestClassName = classNameVotes.getKey();
            }
        }

        return bestClassName;
    }

    private double calculateProbability(DatasetEntry datasetEntry, Map<String, Integer> votesForAttributes) {
        String[] attributes = datasetEntry.getAttributes();

        double probability = 1.0;

//        int total = 0;
//        for (String className : classNames) {
//            total += votesForAttributes.get(className);
//        }
//
//        for (String attribute : attributes) {
//            probability *= (1.0 * votesForAttributes.get(attribute)) / total;
//        }

        int total = votesForAttributes.get("y") + votesForAttributes.get("n") + votesForAttributes.get("?");
        for (int i = 0; i < attributes.length; i++) {
            probability *= (1.0 * votesForAttributes.get(attributes[i])) / total;
        }

        return probability;
    }

}
