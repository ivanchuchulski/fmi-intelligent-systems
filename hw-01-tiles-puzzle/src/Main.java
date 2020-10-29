import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        int[][] initialState = inputBoard();

        printBoard(initialState);

        if (!checkIsBoardSolvable(initialState)) {
            System.out.println("board is unsolvable");
            return;
        }
        else {
            System.out.println("board is solvable");
        }


    }

    private static boolean checkIsBoardSolvable(int[][] initialState) {
        ArrayList<Integer> arr = new ArrayList<>();
        InversionsCounter inversionsCounter = new InversionsCounter();

        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState.length; j++) {
                if (initialState[i][j] == 0) {
                    continue;
                }

                arr.add(initialState[i][j]);
            }
        }

        int numberOfInversions = inversionsCounter.countInversionsSlow(arr);

        // when the board is nxn where n is odd number
        // the board is solvable if the number of inversions is even
        boolean evenInversions = numberOfInversions % 2 == 0;

        if (evenInversions) {
            return true;
        }
        else {
            return false;
        }

    }

    private static int[][] inputBoard() {
        int[][] board;
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter number of tiles (e.g. 8 for 3x3, 15 for 4x4, etc) : ");
        int boardSize = scanner.nextInt();

        boardSize = (int)Math.sqrt(boardSize + 1);

        board = new int[boardSize][boardSize];

        System.out.println("enter the board elements : ");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = scanner.nextInt();
            }
        }

        return board;
    }

    private static void printBoard(int[][] initialState) {
        System.out.println("printing board : ");

        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState.length; j++) {
                System.out.print(initialState[i][j] + " ");
            }

            System.out.println();
        }
    }

}
