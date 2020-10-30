import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstarTest {
    @Test
    public void test1() {
        int[][] initialState = {
                {1, 2, 3},
                {4, 5, 6},
                {0, 7, 8}};
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 2;
        final String[] moves = new String[]{Directions.left, Directions.left};

        Astar astar = new Astar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertArrayEquals(moves, astar.getMoves());
    }

    @Test
    public void test2() {
        int[][] initialState = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}};
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 4;
        final String[] moves = new String[]{Directions.left, Directions.up, Directions.left, Directions.up};

        Astar astar = new Astar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertArrayEquals(moves, astar.getMoves());
    }

    @Test
    public void test3() {
        // this board has more than one solution
        int[][] initialState = {
                {1, 2, 3},
                {0, 7, 6},
                {5, 4, 8}};
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 7;
        final String[] moves = new String[]{Directions.left,
                Directions.up,
                Directions.right,
                Directions.down,
                Directions.left,
                Directions.up,
                Directions.left
        };

        Astar astar = new Astar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertArrayEquals(moves, astar.getMoves());
    }


}