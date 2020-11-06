import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfQueens = inputNumberOfQueens();

        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);
        nQueensMinConflicts.minConflicts();

    }

    private static int inputNumberOfQueens() {
        int result;
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter number of queens : ");

        result = scanner.nextInt();

        scanner.close();

        return result;
    }

}
