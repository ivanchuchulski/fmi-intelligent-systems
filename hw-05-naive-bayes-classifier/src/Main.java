public class Main {
    public static void main(String[] args) {
        NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier();

        naiveBayesClassifier.classify();

        naiveBayesClassifier.printResults();
    }
}
