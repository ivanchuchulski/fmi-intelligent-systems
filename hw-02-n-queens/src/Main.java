import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfQueens = inputNumberOfQueens();

//        nqueens1(numberOfQueens);
        nqueens2(numberOfQueens);
    }

    private static void nqueens2(int numberOfQueens) {
        NQueens nQueens = new NQueens(numberOfQueens);
        nQueens.getQueensPlace();
    }

    private static void nqueens1(int numberOfQueens) {
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
