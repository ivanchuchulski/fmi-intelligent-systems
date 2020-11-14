import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class NQueensTest {
    @Test
    public void tests_queens_4() {
        final int numberOfQueens = 4;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_8() {
        final int numberOfQueens = 8;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_64() {
        final int numberOfQueens = 64;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_256() {
        final int numberOfQueens = 256;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_1024() {
        final int numberOfQueens = 1024;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_4096() {
        final int numberOfQueens = 4096;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_8000() {
        final int numberOfQueens = 8000;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    public void tests_queens_10000() {
        final int numberOfQueens = 10000;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    @Disabled
    public void tests_queens_15000() {
        final int numberOfQueens = 15000;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeout(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }

    @Test
    @Disabled
    public void tests_queens_17500() {
        final int numberOfQueens = 17500;
        NQueens nQueens = new NQueens(numberOfQueens);

        assertTimeout(Duration.ofMillis(1000), nQueens::resolveConflicts);
    }
}