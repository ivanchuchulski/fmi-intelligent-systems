import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class NQueensMinConflictsTest {
    @Test
    public void tests_queens_4() {
        final int numberOfQueens = 4;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeout(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }

    @Test
    public void tests_queens_17500() {
        final int numberOfQueens = 17_500;
        NQueensMinConflicts nQueensMinConflicts = new NQueensMinConflicts(numberOfQueens);

        assertTimeout(Duration.ofMillis(1000), nQueensMinConflicts::minConflicts);
    }
}