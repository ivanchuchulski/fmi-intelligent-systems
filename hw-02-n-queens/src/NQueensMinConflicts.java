import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NQueensMinConflicts {
    private final int numberOfQueens;
    private int[] queens;

    private int[] colConflicts;
    private int[] rowConflicts;
    private int[] diagConflicts;

    private ArrayList<Integer> colWithMaxConflicts;
    private ArrayList<Integer> rowWithMinConflicts;

    private final Random random;

    private int maxConflicts;
    private final int MAX_ITERATIONS;

    public NQueensMinConflicts(int numberOfQueens) {
        this.numberOfQueens = numberOfQueens;

        colWithMaxConflicts = new ArrayList<>();
        rowWithMinConflicts = new ArrayList<>();

        random = new Random();

        maxConflicts = 0;
        MAX_ITERATIONS = 2 * numberOfQueens;

        initializeBoard();
    }

    /**
     * Implementation of Min Conflicts algorithm
     */
    public void minConflicts() {
        int currentIterations = 0;

        /**
         * While current iteration is less than the max iteration
         * Get the queen with max conflicts (the col with max conflicts)
         * Find the row with min conflicts and move the queen there
         * If the max conflicts queen is with 0 conflicts, print the result
         * If the current iteration is greater than the max iteration
         * and there is still no solution, restart
         */
        while (currentIterations++ < MAX_ITERATIONS) {
            conflicts();

            int col = getColWithMaxConflicts();

            if (maxConflicts == 0) {
                //    print();
                break;
            }

            int row = getRowWithMinConflicts(col);

            // move queen
            queens[col] = row;

            --colConflicts[col];
            ++rowConflicts[row];

            if (col == row || col + row == numberOfQueens - 1) {

            }
        }

        if (maxConflicts != 0) {
            maxConflicts = 0;
            initializeBoard();
            minConflicts();
        }
    }

    /**
     * Make initial state with randomly placed queens to the rows
     * The index i is the number of the column.
     * The value queens[i] is the row number in which the queen is placed
     */
    private void initializeBoard() {
        queens = new int[numberOfQueens];

        colConflicts = new int[numberOfQueens];
        rowConflicts = new int[numberOfQueens];
        diagConflicts = new int[2 * numberOfQueens];

        for (int i = 0; i < numberOfQueens; ++i) {
            colConflicts[i] = -1;
            rowConflicts[i] = -1;
            diagConflicts[i] = -1;
        }

        for (int col = 0; col < numberOfQueens; ++col) {
            int row = random.nextInt(numberOfQueens);
            queens[col] = row;
            ++colConflicts[col];
            ++rowConflicts[row];

            if (col == row && diagConflicts[col] == -1) {
                for (int i = 0; i < numberOfQueens; ++i) {
                    ++diagConflicts[i];
                }
            }

            if (col + row == numberOfQueens - 1 && diagConflicts[col + numberOfQueens] == -1) {
                for (int i = numberOfQueens; i < 2 * numberOfQueens; ++i) {
                    ++diagConflicts[i];
                }
            }
        }
    }

    private void conflicts() {
        int minConfl = (queens[0] != 0) ? (colConflicts[0] + rowConflicts[0] + diagConflicts[0] - 1) : (colConflicts[0] + rowConflicts[0] - 1);
        int maxConfl = minConfl;

        rowWithMinConflicts.add(0);
        colWithMaxConflicts.add(0);

        for (int row = 0; row < numberOfQueens; ++row) {
            for (int col = 0; col < numberOfQueens; col++) {
                if (row == 0 && row == col) {
                    continue;
                }

                int confl = colConflicts[col] + rowConflicts[row];

                if (queens[col] != row) {
                    if (col == row) {
                        confl += diagConflicts[col] - 1;
                    }

                    if (col + row == numberOfQueens - 1) {
                        confl += diagConflicts[col + numberOfQueens] - 1;
                    }
                }
                else {
                    confl -= 1;
                }


                if (confl < minConfl) {
                    minConfl = confl;
                    rowWithMinConflicts.clear();
                    rowWithMinConflicts.add(row);
                }

                if (maxConfl < confl) {
                    colWithMaxConflicts.clear();
                    maxConfl = confl;
                    colWithMaxConflicts.add(col);
                }

                if (minConfl == confl) {
                    rowWithMinConflicts.add(row);
                }

                if (maxConfl == confl) {
                    colWithMaxConflicts.add(col);
                }
            }
        }
    }

    /**
     * Return the number of queens that are in conflict with the queen on the
     * current position (row, col)
     *
     * @param queenRow - the row index of the current queen
     * @param queenCol - the col index of the current queen
     * @return the number of conflicts
     */
    private int conflicts(int queenRow, int queenCol) {
        int conflictsCount = 0;

        for (int col = 0; col < numberOfQueens; ++col) {
            if (col == queenCol) {
                continue;
            }

            int row = queens[col];

            if (row == queenRow ||
                    Math.abs(row - queenRow) == Math.abs(col - queenCol)) {
                ++conflictsCount;
            }
        }

        return conflictsCount;
    }

    /**
     * Get the index of the column with max conflicts
     *
     * @return maxConflictQueenIndex
     */
    private int getColWithMaxConflicts() {
        ArrayList<Integer> maxConflictsQueenIndex = new ArrayList<>();
        maxConflicts = 0;

        for (int col = 0; col < numberOfQueens; ++col) {
            int conflictsCount = conflicts(queens[col], col);

            if (conflictsCount == maxConflicts) {
                maxConflictsQueenIndex.add(col);
            }
            else {
                if (conflictsCount > maxConflicts) {
                    maxConflictsQueenIndex.clear();
                    maxConflictsQueenIndex.add(col);
                    maxConflicts = conflictsCount;
                }
            }
        }

        return maxConflictsQueenIndex.get(random.nextInt(maxConflictsQueenIndex.size()));
    }

    /**
     * Get the row, in which the queen is in min conflicts with other queens
     *
     * @param queenCol - the column index of the current queen
     * @return the index of the row with min conflicts
     */
    private int getRowWithMinConflicts(int queenCol) {
        ArrayList<Integer> minConflictsQueenRow = new ArrayList<>();
        int minConflicts = numberOfQueens;

        for (int row = 0; row < numberOfQueens; ++row) {
            int conflictsCount = conflicts(row, queenCol);
            if (conflictsCount == minConflicts) {
                minConflictsQueenRow.add(row);
            }
            else {
                if (conflictsCount < minConflicts) {
                    minConflicts = conflictsCount;
                    minConflictsQueenRow.clear();
                    minConflictsQueenRow.add(row);
                }
            }
        }

        if (!minConflictsQueenRow.isEmpty()) {
            return minConflictsQueenRow.get(random.nextInt(minConflictsQueenRow.size()));
        }

        return 0;
    }

    /**
     * Print the result
     */
    private void print() {
        System.out.println("finished : all conflicts resolved");

        for (int row = 0; row < numberOfQueens; row++) {
            for (int col = 0; col < numberOfQueens; col++) {
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
