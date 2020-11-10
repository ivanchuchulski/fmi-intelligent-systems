import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyNQueens {
    private int boardSize;
    private int[] queens;

    private int[] queensInRow;
    private int[] queensInMainDiag;
    private int[] queensInSecDiag;

    private ArrayList<Integer> colWithMaxConflicts;
    private ArrayList<Integer> rowWithMinConflicts;

    private final Random random = new Random();

    public MyNQueens(int boardSize) {
        this.boardSize = boardSize;
        init();
    }

    private void init() {
        queens = new int[boardSize];
        queensInRow = new int[boardSize];
        queensInMainDiag = new int[2 * boardSize - 1];
        queensInSecDiag = new int[2 * boardSize - 1];

        colWithMaxConflicts = new ArrayList<>();
        rowWithMinConflicts = new ArrayList<>();


        List<Integer> shuffledColumns = new ArrayList<>(boardSize);
        int value = 0;

        for (int i = 0; i < boardSize; i++) {
            shuffledColumns.add(value++);
        }

        Collections.shuffle(shuffledColumns);

        for (int i = 0; i < shuffledColumns.size(); ++i) {
            int currentColumn = shuffledColumns.get(i);
            queens[i] = currentColumn;

            queensInRow[currentColumn]++;
            queensInMainDiag[this.boardSize - 1 - currentColumn + i]++;
            queensInSecDiag[currentColumn + i]++;
        }
    }

    boolean hasConflicts() {
        for (int col = 0; col < boardSize; col++) {
            if (getConflictsOfQueen(col) > 0) {
                return true;
            }
        }

        return false;
    }

    int getConflictsOfQueen(int col) {
        int row = queens[col];
        return queensInRow[row] +
                queensInMainDiag[getMainDiagonalIndexForCell(row, col)] + queensInSecDiag[getSecDiagonalIndexForCell(row, col)] - 3;
    }

    int getMainDiagonalIndexForCell(int row, int col) {
        if (col - row > 0) {
            return col - row;
        }
        else {
            return Math.abs(col - row) + boardSize - 1;
        }
    }
    int getSecDiagonalIndexForCell(int row, int col) {
        return row + col;
    }
}
