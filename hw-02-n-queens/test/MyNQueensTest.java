import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MyNQueensTest {
    @Test
    public void tests_queens_4() {
        final int numberOfQueens = 4;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_8() {
        final int numberOfQueens = 8;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_64() {
        final int numberOfQueens = 64;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_256() {
        final int numberOfQueens = 256;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_1024() {
        final int numberOfQueens = 1024;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_4096() {
        final int numberOfQueens = 4096;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_8000() {
        final int numberOfQueens = 8000;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    public void tests_queens_10000() {
        final int numberOfQueens = 10000;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    @Disabled
    public void tests_queens_15000() {
        final int numberOfQueens = 15000;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeout(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }

    @Test
    @Disabled
    public void tests_queens_17500() {
        final int numberOfQueens = 17500;
        MyNQueens mynqueens = new MyNQueens(numberOfQueens);

        assertTimeout(Duration.ofMillis(1000), mynqueens::resolveConflicts);
    }
}