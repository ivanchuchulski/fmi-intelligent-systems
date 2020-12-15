import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private final Map<String, Integer> classNamesCount;
    private final Model model;

    // {classname -> {attribute -> #votes} }
//    private final Map<String, Map<String, Integer>> model;

    public NaiveBayesClassifier() {
        datasetEntries = new ArrayList<>();
        testSet = new ArrayList<>();
        validatingSet = new ArrayList<>();
        classNamesCount = new HashMap<>();
        model = new Model();
    }

    // performing ten fold cross-validation
    public void classify() {
        int validationTimes = 10;

        readData();
        double accuracySum = 0.0;

        for (int time = 0; time < validationTimes; time++) {
//            buildTestAndValidatingSetsFixed(time);
            buildTestAndValidatingSetsOnRandom();

            buildModel();

            double accuracy = makePrediction();

            accuracySum += accuracy;

            System.out.printf("accuracy on round %s: %.2f%s%n", time, 100 * accuracy, "%");

            resetModel();
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
                int count = classNamesCount.getOrDefault(className, 0);
                classNamesCount.put(className, count + 1);

                DatasetEntry datasetEntry = new DatasetEntry(className, attributes);
                datasetEntries.add(datasetEntry);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void buildTestAndValidatingSetsFixed(int validationTimes) {
        int oneTenthPart = datasetEntries.size() / 10;

        Set<DatasetEntry> addedToTest = new HashSet<>();

        int counter = 0;
        int index = validationTimes * oneTenthPart;
        while (counter < oneTenthPart) {
            DatasetEntry datasetEntry = datasetEntries.get(index);

            testSet.add(datasetEntry);
            addedToTest.add(datasetEntry);
            index++;
            counter++;
        }

        for (DatasetEntry datasetEntry : datasetEntries) {
            if (!addedToTest.contains(datasetEntry)) {
                validatingSet.add(datasetEntry);
            }
        }
    }

    private void buildTestAndValidatingSetsOnRandom() {
        Collections.shuffle(datasetEntries);
        int oneTenthPart = datasetEntries.size() / 10;

        Set<DatasetEntry> addedToTest = new HashSet<>();

        int counter = 0;
        while (counter < oneTenthPart) {
            DatasetEntry datasetEntry = datasetEntries.get(counter);

            testSet.add(datasetEntry);
            addedToTest.add(datasetEntry);
            counter++;
        }

        for (DatasetEntry datasetEntry : datasetEntries) {
            if (!addedToTest.contains(datasetEntry)) {
                validatingSet.add(datasetEntry);
            }
        }
    }

    private void buildModel() {
        model.buildModel(classNamesCount.keySet(), validatingSet, attributesCount);
    }

    private double makePrediction() {
        double correctPredictionsCount = 0.0;

        for (DatasetEntry datasetEntry : testSet) {
            String predictedClassName = findClassNameForEntry(datasetEntry);

            if (predictedClassName.equals(datasetEntry.getClassName())) {
                correctPredictionsCount++;
            }
        }

        return correctPredictionsCount / testSet.size();
    }

    private void resetModel() {
        testSet.clear();
        validatingSet.clear();
        model.clearModel();
    }

    private String findClassNameForEntry(DatasetEntry datasetEntry) {
        String bestClassName = "";
        double bestProbability = Double.MIN_VALUE;

        for (String className : model.getModel().keySet()) {
            List<AttributeVote> attributeVotes = model.getModel().get(className);

            double classNameProbability = calculateProbabilityForClass(datasetEntry, className, attributeVotes);

            if (classNameProbability > bestProbability) {
                bestProbability = classNameProbability;
                bestClassName = className;
            }
        }

        return bestClassName;
    }

    private double calculateProbabilityForClass(DatasetEntry datasetEntry, String currrentClassname,
                                                List<AttributeVote> votesForAttributes) {
        String[] attributes = datasetEntry.getAttributes();

        double probability = 1.0;

        double countForClass = 1.0;
        double countForOtherClass = 1.0;

        for (String className : classNamesCount.keySet()) {
            if (className.equals(currrentClassname)) {
                countForClass = classNamesCount.get(className);
            } else {
                countForOtherClass = classNamesCount.get(className);
            }
        }

        probability *= countForClass / (countForClass + countForOtherClass);

        for (int i = 0; i < attributesCount; i++) {
            String attribute = attributes[i];
            AttributeVote attributeVote = votesForAttributes.get(i);

            int voteCount = attributeVote.getVoteCount(attribute);
            int allVotesNumber = attributeVote.getAllVotesNumber();

            probability *= (1.0 * voteCount) / allVotesNumber;
        }

        return probability;
    }

}
