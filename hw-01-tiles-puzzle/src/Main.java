import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final int EMPTY_SLOT = 0;

    public static void main(String[] args) {
        System.out.println("hello");

        int[][] initialState = inputBoard();
        int[][] goalState = generateGoalState(initialState);

        printBoard(initialState);

        if (!checkIsBoardSolvable(initialState)) {
            System.out.println("board is unsolvable");
            return;
        }
        else {
            System.out.println("board is solvable");
        }


        Astar aStarSearch = new Astar(initialState, goalState);

        aStarSearch.findSolution();
        aStarSearch.printResult();
    }

    private static boolean checkIsBoardSolvable(int[][] initialState) {
        ArrayList<Integer> arr = new ArrayList<>();
        InversionsCounter inversionsCounter = new InversionsCounter();

        for (int i = 0; i < initialState.length; i++) {
            for (int j = 0; j < initialState.length; j++) {
                if (initialState[i][j] == EMPTY_SLOT) {
                    continue;
                }

                arr.add(initialState[i][j]);
            }
        }

        int numberOfInversions = inversionsCounter.countInversionsSlow(arr);

        // when the board is nxn where n is odd number
        // the board is solvable if the number of inversions is even
        boolean evenInversions = numberOfInversions % 2 == 0;

        return evenInversions;
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

    public static int[][] generateGoalState(int[][] initialBoard) {
        int[][] goalState = new int[initialBoard.length][initialBoard.length];
        int tilesNumber = 1;

        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard.length; j++) {
                if (i == initialBoard.length - 1 && j == initialBoard.length - 1) {
                    goalState[i][j] = EMPTY_SLOT;
                    break;
                }

                goalState[i][j] = tilesNumber++;
            }
        }

        return goalState;
    }

    public static void printBoard(int[][] board) {
        System.out.println("printing board : ");

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }
    }

}
