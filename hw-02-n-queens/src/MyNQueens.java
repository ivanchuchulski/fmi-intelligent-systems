import java.util.*;

public class MyNQueens {
    public static final Random random = new Random();

    private final int size;
    private int[] queens;

    private int[] rowConfl;
    private int[] mainDiagonalConflicts;
    private int[] secondaryDiagonalConflicts;

    private List<Conflict> minConflictQueens = new ArrayList<>();
    private List<Conflict> maxConflictQueens = new ArrayList<>();

    public MyNQueens(int size) {
        this.size = size;
        init();
    }

    public void resolveConflicts() {
        boolean conflictsResolved = false;
        int currentIterations = 0;
        final int MAX_ITERATIONS = size * 2;

        while (currentIterations++ < MAX_ITERATIONS) {
            int colWithMaxConfl = getColwWithMaxConflicts();

            if (colWithMaxConfl == -1) {
                conflictsResolved = true;
                break;
            }

            int prevRow = queens[colWithMaxConfl];
            int nextRow = getRowWithMinConflicts(colWithMaxConfl);

            queens[colWithMaxConfl] = nextRow;

            rowConfl[prevRow]--;
            mainDiagonalConflicts[getMainDiagonalIndexForCell(prevRow, colWithMaxConfl)]--;
            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(prevRow, colWithMaxConfl)]--;

            rowConfl[nextRow]++;
            mainDiagonalConflicts[getMainDiagonalIndexForCell(nextRow, colWithMaxConfl)]++;
            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(nextRow, colWithMaxConfl)]++;
        }

        if (!conflictsResolved) {
            init();
            resolveConflicts();
        }


//        print();
    }

    private void init() {
        queens = new int[size];
        rowConfl = new int[size];

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

            rowConfl[currentRow]++;
            mainDiagonalConflicts[getMainDiagonalIndexForCell(currentRow, col)]++;
            secondaryDiagonalConflicts[getSecDiagonalIndexForCell(currentRow, col)]++;
        }
    }

    private int getColwWithMaxConflicts() {
        maxConflictQueens.clear();

        for (int col = 0; col < size; ++col) {
            int row = queens[col];

            int candidate =
                    rowConfl[row] +
                            mainDiagonalConflicts[size - 1 - row + col] +
                            secondaryDiagonalConflicts[col + row] - 3;

            if (maxConflictQueens.isEmpty() || maxConflictQueens.get(0).value == candidate) {
                maxConflictQueens.add(new Conflict(col, candidate));
            }
            else {
                if (maxConflictQueens.get(0).value < candidate) {
                    maxConflictQueens.clear();
                    maxConflictQueens.add(new Conflict(col, candidate));
                }
            }
        }

        if (maxConflictQueens.get(0).value == 0) {
            return -1;
        }


        return maxConflictQueens.get(random.nextInt(maxConflictQueens.size())).place;
    }

    private int getRowWithMinConflicts(int colWithMaxConfl) {
        minConflictQueens.clear();

        for (int row = 0; row < size; ++row) {
            // works with -3 or without it
            int candidate =
                    rowConfl[row] +
                    mainDiagonalConflicts[getMainDiagonalIndexForCell(row, colWithMaxConfl)] +
                    secondaryDiagonalConflicts[getSecDiagonalIndexForCell(row, colWithMaxConfl)];

            if (row == queens[colWithMaxConfl]) {
                candidate -= 3;
            }

            if (minConflictQueens.isEmpty() || minConflictQueens.get(0).value == candidate) {
                minConflictQueens.add(new Conflict(row, candidate));
            }
            else {
                if (minConflictQueens.get(0).value > candidate) {
                    minConflictQueens.clear();
                    minConflictQueens.add(new Conflict(row, candidate));
                }
            }
        }

        return minConflictQueens.get(random.nextInt(minConflictQueens.size())).place;
    }


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
