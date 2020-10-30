import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstarTest {
    @Test
    public void test1() {
        int[][] initialState = {{1, 2, 3}, {4, 5, 6}, {Main.EMPTY_SLOT, 7, 8}};
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 2;
        final String[] moves = new String[]{Directions.left, Directions.left};

        Astar astar = new Astar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertArrayEquals(moves, astar.getMoves());
    }


}