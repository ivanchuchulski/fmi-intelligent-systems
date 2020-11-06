import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final int EMPTY_TILE = 0;
    public static int emptyTileIndexInSolution = -1;

    public static void main(String[] args) {
        try {
            int[][] initialState = inputBoard();
            int[][] goalState = generateGoalState(initialState);

            System.out.println("printing initial state");
            printBoard(initialState);

            checkIsBoardSolvable(initialState);

            System.out.println("printing goal state");
            printBoard(goalState);

            System.out.println("solving...");
            solveWithIDAStar(initialState, goalState);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void solveWithIDAStar(int[][] initialState, int[][] goalState) {
        IDAStar idaStar = new IDAStar(initialState, goalState);

        idaStar.findSolution();
        idaStar.printSolutionInformation();
    }

    private static void checkIsBoardSolvable(int[][] initialState) throws Exception {
        int boardLength = initialState.length;
        int emptyPositionRow = 0;

        ArrayList<Integer> arr = new ArrayList<>();
        InversionsCounter inversionsCounter = new InversionsCounter();

        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (initialState[i][j] == EMPTY_TILE) {
                    emptyPositionRow = i;
                    continue;
                }

                arr.add(initialState[i][j]);
            }
        }

        int numberOfInversions = inversionsCounter.countInversionsSlow(arr);

        // when the board is nxn where n is odd number
        // the board is solvable if the number of inversions is even
        // That is, when n is even, an n-by-n board is solvable if and only if the number of inversions plus the row of the blank
        //square is odd.

        if (isOdd(boardLength) && isEven(numberOfInversions)) {
            ;
        }
        else {
            if ((isEven(boardLength) && (isOdd(emptyPositionRow + numberOfInversions)))) {
                ;
            }
            else {
                throw new Exception("board is unsolvable");
            }
        }
    }

    private static int[][] inputBoard() {
        int[][] board;
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter number of tiles (e.g. 8 for 3x3, 15 for 4x4, etc) : ");
        int numberOfTiles = scanner.nextInt();

        System.out.print("enter index of empty element in the solution [0, #tiles - 1] or -1 for the lowest right : ");
        int emptyIndexInSolution = scanner.nextInt();

        int boardSize = (int) Math.sqrt(numberOfTiles + 1);

        board = new int[boardSize][boardSize];

        if (emptyIndexInSolution == -1) {
            Main.emptyTileIndexInSolution = numberOfTiles;
        }
        else {
            if (emptyIndexInSolution < 0 || emptyIndexInSolution > numberOfTiles) {
                throw new RuntimeException("error : wrong empty tile index, it must be within [0, " + numberOfTiles + "] but was " + emptyIndexInSolution);
            }
            else {
                Main.emptyTileIndexInSolution = emptyIndexInSolution;
            }
        }

        System.out.println("enter the board elements : ");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        return board;
    }

    public static int[][] generateGoalState(int[][] initialBoard) {
        int[][] goalState = new int[initialBoard.length][initialBoard.length];
        int tilesNumber = 1;

        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard.length; j++) {
                if (i * initialBoard.length + j == Main.emptyTileIndexInSolution) {
                    goalState[i][j] = EMPTY_TILE;
                }
                else {
                    goalState[i][j] = tilesNumber++;
                }
            }
        }

        return goalState;
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    private static boolean isOdd(int number) {
        return number % 2 == 1;
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }
    }

}
