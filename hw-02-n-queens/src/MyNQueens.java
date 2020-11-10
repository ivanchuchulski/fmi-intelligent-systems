import java.util.*;

public class MyNQueens {
    public static Random random;

    private final int size;
    private final int[] queens;

    private final int[] rowConflicts;
    private final int[] mainDiagonalConflicts;
    private final int[] secondaryDiagonalConflicts;

    private final List<Conflict> rowsWithMinConflicts;
    private final List<Conflict> colsWithMaxConflicts;

    public MyNQueens(int size) {
        this.size = size;

        queens = new int[size];

        rowConflicts = new int[size];
        mainDiagonalConflicts = new int[2 * size - 1];
        secondaryDiagonalConflicts = new int[2 * size - 1];

        rowsWithMinConflicts = new ArrayList<>();
        colsWithMaxConflicts = new ArrayList<>();

        int value = 0;
        for (int i = 0; i < size; i++) {
            queens[i] = value++;
        }

        init();
    }

    public void resolveConflicts() {
        boolean conflictsResolved = false;
        int currentIterations = 0;
        final int MAX_ITERATIONS = size * 2;

        init();

        while (currentIterations++ < MAX_ITERATIONS) {
            int colWithMaxConfl = getColwWithMaxConflicts();

            if (colWithMaxConfl == -1) {
                conflictsResolved = true;
                break;
            }

            int prevRow = queens[colWithMaxConfl];
            int nextRow = getRowWithMinConflicts(colWithMaxConfl);

            moveQueen(colWithMaxConfl, prevRow, nextRow);
        }

        if (!conflictsResolved) {
            resolveConflicts();
        }

//        printSolutionBoard();
    }


    private void init() {
        random = new Random();
        shuffleArray(queens);

        Arrays.fill(rowConflicts, 0);
        Arrays.fill(mainDiagonalConflicts, 0);
        Arrays.fill(secondaryDiagonalConflicts, 0);

        for (int col = 0; col < size; col++) {
            int currentRow = queens[col];

            rowConflicts[currentRow]++;
            mainDiagonalConflicts[getMainDiagonalIndexForCell(currentRow, col)]++;
            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(currentRow, col)]++;
        }
    }

    private int getColwWithMaxConflicts() {
        colsWithMaxConflicts.clear();

        for (int col = 0; col < size; ++col) {
            int row = queens[col];

            int candidate =
                    rowConflicts[row] +
                            mainDiagonalConflicts[getMainDiagonalIndexForCell(row, col)] +
                            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(row, col)] - 3;

            if (colsWithMaxConflicts.isEmpty() || colsWithMaxConflicts.get(0).value == candidate) {
                colsWithMaxConflicts.add(new Conflict(col, candidate));
            }
            else {
                if (colsWithMaxConflicts.get(0).value < candidate) {
                    colsWithMaxConflicts.clear();
                    colsWithMaxConflicts.add(new Conflict(col, candidate));
                }
            }
        }

        if (colsWithMaxConflicts.get(0).value == 0) {
            return -1;
        }


        return colsWithMaxConflicts.get(random.nextInt(colsWithMaxConflicts.size())).place;
    }

    private int getRowWithMinConflicts(int colWithMaxConfl) {
        rowsWithMinConflicts.clear();

        for (int row = 0; row < size; ++row) {
            int candidate =
                    rowConflicts[row] +
                            mainDiagonalConflicts[getMainDiagonalIndexForCell(row, colWithMaxConfl)] +
                            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(row, colWithMaxConfl)];

            if (row == queens[colWithMaxConfl]) {
                candidate -= 3;
            }

            if (rowsWithMinConflicts.isEmpty() || rowsWithMinConflicts.get(0).value == candidate) {
                rowsWithMinConflicts.add(new Conflict(row, candidate));
            }
            else {
                if (rowsWithMinConflicts.get(0).value > candidate) {
                    rowsWithMinConflicts.clear();
                    rowsWithMinConflicts.add(new Conflict(row, candidate));
                }
            }
        }

        return rowsWithMinConflicts.get(random.nextInt(rowsWithMinConflicts.size())).place;
    }

    private void moveQueen(int colWithMaxConfl, int prevRow, int nextRow) {
        queens[colWithMaxConfl] = nextRow;

        rowConflicts[prevRow]--;
        mainDiagonalConflicts[getMainDiagonalIndexForCell(prevRow, colWithMaxConfl)]--;
        secondaryDiagonalConflicts[getSecDiagonalIndexForCell(prevRow, colWithMaxConfl)]--;

        rowConflicts[nextRow]++;
        mainDiagonalConflicts[getMainDiagonalIndexForCell(nextRow, colWithMaxConfl)]++;
        secondaryDiagonalConflicts[getSecDiagonalIndexForCell(nextRow, colWithMaxConfl)]++;
    }

    private int getMainDiagonalIndexForCell(int row, int col) {
        return col - row + this.size - 1;
    }

    private int getSecDiagonalIndexForCell(int row, int col) {
        return row + col;
    }

//     source : https://stackoverflow.com/a/1520212/9127495
    private void shuffleArray(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

    public void printSolutionBoard() {
        System.out.println("finished : all conflicts resolved");

        System.out.println(Arrays.toString(queens));

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (queens[col] == row) {
                    System.out.print("* ");
                }
                else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }
}