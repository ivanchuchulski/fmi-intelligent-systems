import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        int[][] initialState = inputBoard();

        printBoard(initialState);
    }

    private static int[][] inputBoard() {
        int[][] board;
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter board size (e.g. 3 for 3x3, 5 for 5x5, etc) : ");
        int boardSize = scanner.nextInt();

        board = new int[boardSize][boardSize];

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
