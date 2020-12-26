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

    private final Map<String, Integer> classNamesCount;

    private final List<DatasetEntry> validatingSet;
    private final List<DatasetEntry> testSet;

    private final Model model;

    private final List<Double> roundAccuracies;
    private double finalAccuracy;
    private final int numberOfValidationRounds = 10;

    public NaiveBayesClassifier() {
        datasetEntries = new ArrayList<>();
        classNamesCount = new HashMap<>();

        validatingSet = new ArrayList<>();
        testSet = new ArrayList<>();

        model = new Model();

        roundAccuracies = new ArrayList<>();
    }

    public void classify() {
        readData();

        for (int round = 0; round < numberOfValidationRounds; round++) {
//            buildTestAndValidatingSetsFixed(round);
            buildTestAndValidatingSetsOnRandom();

            buildModel();

            roundAccuracies.add(calculateAccuracyPercentForTestSet());

            resetModel();
        }

        double accuracySum = 0.0;
        for (Double roundAccuracy : roundAccuracies) {
            accuracySum += roundAccuracy;
        }

        finalAccuracy = accuracySum / numberOfValidationRounds;
    }

    public void printResults() {
        System.out.println("after performing ten fold cross-validation : ");

        for (int time = 0; time < numberOfValidationRounds; time++) {
            System.out.printf("accuracy on round %s: %.3f%%%n", time + 1, roundAccuracies.get(time));
        }

        System.out.printf("final accuracy: %.3f%%%n", finalAccuracy);
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

    private void buildTestAndValidatingSetsFixed(int round) {
        int oneTenthPart = datasetEntries.size() / 10;

        Set<DatasetEntry> addedToTest = new HashSet<>();

        int counter = 0;
        int index = round * oneTenthPart;
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

    private double calculateAccuracyPercentForTestSet() {
        double correctPredictionsCount = 0.0;

        for (DatasetEntry datasetEntry : testSet) {
            String predictedClassName = findClassNameForEntry(datasetEntry);

            if (predictedClassName.equals(datasetEntry.getClassName())) {
                correctPredictionsCount++;
            }
        }

        return 100 * correctPredictionsCount / testSet.size();
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

            if (Double.compare(classNameProbability, bestProbability) > 0) {
                bestProbability = classNameProbability;
                bestClassName = className;
            }
        }

        return bestClassName;
    }

    private double calculateProbabilityForClass(DatasetEntry datasetEntry, String currrentClassname,
                                                List<AttributeVote> votesForAttributes) {
        double probability = 1.0;
        double probabilitySumInLog = 0.0;

        double countForClass = 1.0;
        double countForOtherClass = 1.0;

        for (String className : classNamesCount.keySet()) {
            if (className.equals(currrentClassname)) {
                countForClass = classNamesCount.get(className);
            } else {
                countForOtherClass = classNamesCount.get(className);
            }
        }

        double classProbability = countForClass / (countForClass + countForOtherClass);

        probability *= classProbability;
//        probabilitySumInLog += Math.log(classProbability);

        for (int i = 0; i < attributesCount; i++) {
            String attribute = datasetEntry.getAttributes()[i];
            AttributeVote attributeVote = votesForAttributes.get(i);

            int voteCount = attributeVote.getVoteCount(attribute);
            int allVotesNumber = attributeVote.getAllVotesNumber();

            double voteConditionalProbability = (1.0 * voteCount) / allVotesNumber;

            probability *= voteConditionalProbability;
//            probabilitySumInLog += Math.log(classProbability);
        }

        return probability;
    }
}
