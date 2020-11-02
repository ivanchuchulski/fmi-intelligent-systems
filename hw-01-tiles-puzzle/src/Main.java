import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final int EMPTY_TILE = 0;

    public static void main(String[] args) {
        int[][] initialState = inputBoard();
        int[][] goalState = generateGoalState(initialState);

        printBoard(initialState);

        try {
            checkIsBoardSolvable(initialState);

//            solveWithAStar(initialState, goalState);

            solveWithIDAStar(initialState, goalState);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void solveWithAStar(int[][] initialState, int[][] goalState) {
        AStar aStarSearch = new AStar(initialState, goalState);

        aStarSearch.findSolution();
        aStarSearch.printResult();
    }

    private static void solveWithIDAStar(int[][] initialState, int[][] goalState) {
        IDAStar idaStar = new IDAStar(initialState, goalState);

        idaStar.findSolution();
//        idaStar.getMoves();
        idaStar.printInfo();

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
        else if ((isEven(boardLength) && (isOdd(emptyPositionRow + numberOfInversions)))) {
            ;
        }
        else{
            throw new Exception("board is unsolvable");
        }
    }

    private static int[][] inputBoard() {
        int[][] board;
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter number of tiles (e.g. 8 for 3x3, 15 for 4x4, etc) : ");
        int boardSize = scanner.nextInt();

        boardSize = (int) Math.sqrt(boardSize + 1);

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
                    goalState[i][j] = EMPTY_TILE;
                    break;
                }

                goalState[i][j] = tilesNumber++;
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
        System.out.println("printing board : ");

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }
    }

}
