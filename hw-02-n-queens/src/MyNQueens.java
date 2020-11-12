import java.util.*;

public class MyNQueens {
    public Random random;

    private final int size;
    private final int[] queens;

    private final int[] rowConflicts;
    private final int[] mainDiagonalConflicts;
    private final int[] secondaryDiagonalConflicts;

    private final List<Integer> colsWithMaxConflicts;
    private final List<Integer> rowsWithMinConflicts;

    private final int NO_CONFLICTS_FOUND = -1;

    public MyNQueens(int size) {
        this.size = size;

        queens = new int[size];

        rowConflicts = new int[size];
        mainDiagonalConflicts = new int[2 * size - 1];
        secondaryDiagonalConflicts = new int[2 * size - 1];

        colsWithMaxConflicts = new ArrayList<>();
        rowsWithMinConflicts = new ArrayList<>();

        for (int col = 0; col < size; col++) {
            queens[col] = col;
        }

        init();
    }

    public void resolveConflicts() {
        boolean conflictsResolved = false;
        int currentIterations = 0;
        final int MAX_ITERATIONS = size * 2;

        init();

        while (currentIterations++ < MAX_ITERATIONS) {
            int columnWithMaxConflicts = getColumnWithMaxConflicts();

            if (columnWithMaxConflicts == NO_CONFLICTS_FOUND) {
                conflictsResolved = true;
                break;
            }

            int prevRow = queens[columnWithMaxConflicts];
            int nextRow = getRowWithMinConflicts(columnWithMaxConflicts);

            moveQueen(columnWithMaxConflicts, prevRow, nextRow);
        }

        if (!conflictsResolved) {
            resolveConflicts();
        }
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

    private int getColumnWithMaxConflicts() {
        colsWithMaxConflicts.clear();
        int maxConflictCount = Integer.MIN_VALUE;

        for (int col = 0; col < size; ++col) {
            int row = queens[col];

            int currentConflicts = getConflictsForQueen(row, col) - 3;

            if (colsWithMaxConflicts.isEmpty() || currentConflicts == maxConflictCount) {
                maxConflictCount = currentConflicts;
                colsWithMaxConflicts.add(col);
            }
            else {
                if (currentConflicts > maxConflictCount) {
                    maxConflictCount = currentConflicts;

                    colsWithMaxConflicts.clear();

                    colsWithMaxConflicts.add(col);
                }
            }
        }

        if (maxConflictCount == 0) {
            return NO_CONFLICTS_FOUND;
        }

        return colsWithMaxConflicts.get(random.nextInt(colsWithMaxConflicts.size()));
    }

    private int getRowWithMinConflicts(int colWithMaxConfl) {
        rowsWithMinConflicts.clear();
        int minConflictCount = Integer.MAX_VALUE;

        for (int row = 0; row < size; ++row) {
            int currentConflicts = getConflictsForQueen(row, colWithMaxConfl);

            if (row == queens[colWithMaxConfl]) {
                currentConflicts -= 3;
            }

            if (rowsWithMinConflicts.isEmpty() || currentConflicts == minConflictCount) {
                minConflictCount = currentConflicts;
                rowsWithMinConflicts.add(row);
            }
            else {
                if (currentConflicts < minConflictCount) {
                    minConflictCount = currentConflicts;

                    rowsWithMinConflicts.clear();

                    rowsWithMinConflicts.add(row);
                }
            }
        }

        return rowsWithMinConflicts.get(random.nextInt(rowsWithMinConflicts.size()));
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

    private int getConflictsForQueen(int row, int col) {
        return rowConflicts[row] +
                mainDiagonalConflicts[getMainDiagonalIndexForCell(row, col)] +
                secondaryDiagonalConflicts[getSecDiagonalIndexForCell(row, col)];
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
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
}
