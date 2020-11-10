import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class NQueensMinConflictsTest {
    @Test
    public void tests_queens_4() {
        final int numberOfQueens = 4;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_8() {
        final int numberOfQueens = 8;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_64() {
        final int numberOfQueens = 64;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_256() {
        final int numberOfQueens = 256;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_1024() {
        final int numberOfQueens = 1024;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_4096() {
        final int numberOfQueens = 4096;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_8000() {
        final int numberOfQueens = 8000;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_10000() {
        final int numberOfQueens = 10000;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_16000() {
        final int numberOfQueens = 16000;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    @Disabled
    public void tests_queens_17500() {
        final int numberOfQueens = 17500;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeoutPreemptively(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }
}