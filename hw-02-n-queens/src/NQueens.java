import java.util.*;

public class NQueens {
    public static final Random random = new Random();

    private final int size;
    private int[] queens;

    private int[] columnConflicts;
    private int[] rowConflicts;
    private int[] mainDiagonalConflicts;
    private int[] secondaryDiagonalConflicts;

    private List<Conflict> minConflictQueens = new ArrayList<>();
    private List<Conflict> maxConflictQueens = new ArrayList<>();

    public NQueens(int size) {
        this.size = size;
        init();
    }

    public void getQueensPlace() {
        boolean conflictsResolved = false;
        int currentIterations = 0;
        final int MAX_ITERATIONS = size * 2;

        while (currentIterations++ < MAX_ITERATIONS) {
            int row = getRowWithMaxConflicts();

            if (row == -1) {
                conflictsResolved = true;
                break;
            }

            int previousColumn = queens[row];
            int nextColumn = getColumnWithMinConflicts(row);

            queens[row] = nextColumn;

            columnConflicts[previousColumn]--;
            columnConflicts[nextColumn]++;

            mainDiagonalConflicts[size - 1 - previousColumn + row]--;
            mainDiagonalConflicts[size - 1 - nextColumn + row]++;

            secondaryDiagonalConflicts[previousColumn + row]--;
            secondaryDiagonalConflicts[nextColumn + row]++;
        }

        if (!conflictsResolved) {
            init();
            getQueensPlace();
        }


//        print();
    }

    private void init() {
        queens = new int[size];
        columnConflicts = new int[size];
        rowConflicts = new int[size];

        mainDiagonalConflicts = new int[2 * size - 1];
        secondaryDiagonalConflicts = new int[2 * size - 1];

        List<Integer> shuffledRows = new ArrayList<>(size);
        int value = 0;

        for (int i = 0; i < size; i++) {
            shuffledRows.add(value++);
        }

        Collections.shuffle(shuffledRows);

        for (int col = 0; col < size; col++) {
            int currentRow = shuffledRows.get(col);

            queens[col] = currentRow;

            columnConflicts[currentRow]++;
            rowConflicts[currentRow]++;
            mainDiagonalConflicts[getMainDiagonalIndexForCell(currentRow, col)]++;
            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(currentRow, col)]++;
        }
    }

    private int getRowWithMaxConflicts() {
        maxConflictQueens.clear();

        for (int row = 0; row < size; ++row) {
            int column = queens[row];
            int candidate =
                    columnConflicts[column] +
                    mainDiagonalConflicts[size - 1 - column + row] +
                    secondaryDiagonalConflicts[row + column];

            if (maxConflictQueens.isEmpty() || maxConflictQueens.get(0).value == candidate) {
                maxConflictQueens.add(new Conflict(row, candidate));
            }
            else {
                if (maxConflictQueens.get(0).value < candidate) {
                    maxConflictQueens.clear();
                    maxConflictQueens.add(new Conflict(row, candidate));
                }
            }
        }

        if (maxConflictQueens.get(0).value == 0) {
            return -1;
        }


        return maxConflictQueens.get(random.nextInt(maxConflictQueens.size())).place;
    }

    private int getColumnWithMinConflicts(int row) {
        minConflictQueens.clear();

        for (int column = 0; column < size; ++column) {
            int candidate =
                    columnConflicts[column] +
                    mainDiagonalConflicts[size - 1 - column + row] +
                    secondaryDiagonalConflicts[row + column];

            if (minConflictQueens.isEmpty() || minConflictQueens.get(0).value == candidate) {
                minConflictQueens.add(new Conflict(column, candidate));
            }
            else {
                if (minConflictQueens.get(0).value > candidate) {
                    minConflictQueens.clear();
                    minConflictQueens.add(new Conflict(column, candidate));
                }
            }
        }

        return minConflictQueens.get(random.nextInt(minConflictQueens.size())).place;
    }

//    private int getColWithMaxConflicts() {
//        ArrayList<Integer> candidates = new ArrayList<>();
//
//        for (int col = 0; col < size; col++) {
//
//        }
//
//    }

    private int getMainDiagonalIndexForCell(int row, int col) {
//        if (col - row > 0) {
//            return col - row;
//        }
//        else {
//            return Math.abs(col - row) + this.size - 1;
//        }

        return col - row + this.size - 1;
    }

    private int getSecDiagonalIndexForCell(int row, int col) {
        return row + col;
    }

    public void print() {
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
