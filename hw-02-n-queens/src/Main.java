import java.util.Calendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numberOfQueens = inputNumberOfQueens();

        solve(numberOfQueens);
    }

    private static void solve(int numberOfQueens) {
        long startTimestamp = Calendar.getInstance().getTimeInMillis();

        MyNQueens myNQueens = new MyNQueens(numberOfQueens);
        myNQueens.resolveConflicts();
//        myNQueens.printSolutionBoard();

        long finishTimestamp = Calendar.getInstance().getTimeInMillis();
        long overallTime = finishTimestamp - startTimestamp;

        System.out.printf("total time: %dms%n", overallTime);
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
