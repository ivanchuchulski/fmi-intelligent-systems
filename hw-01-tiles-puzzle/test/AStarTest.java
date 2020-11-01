import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {
    @Test
    public void test1() {
        int[][] initialState = {
                {1, 2, 3},
                {4, 5, 6},
                {0, 7, 8}};
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 2;
        final String moves = new String("LL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves, astar.getMoves());
    }

    @Test
    public void test2() {
        int[][] initialState = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}};
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 4;
        final String moves = new String("LULU");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves, astar.getMoves());
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
        final String moves2 = new String("ULDRULL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test4() {
        int[][] initialState = buildBoardFromString("413726580");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 8;
        final String moves2 = new String("RRDDLUUL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test5() {
        int[][] initialState = buildBoardFromString("162530478");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 9;
        final String moves2 = new String("RDLURRULL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test6() {
        int[][] initialState = buildBoardFromString("512630478");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 11;
        final String moves2 = new String("RRDLLURRULL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test7() {
        int[][] initialState = buildBoardFromString("126350478");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 13;
        final String moves2 = new String("DRURULLDRDLUU");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test8() {
        int[][] initialState = buildBoardFromString("436871052");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 18;
        final String moves2 = new String("DLLDRUULDRURDDLULU");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test9() {
        int[][] initialState = buildBoardFromString("503284671");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 23;
        final String moves2 = new String("RUULLDRRULLDRRULDDRUULL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test10() {
        int[][] initialState = buildBoardFromString("874320651");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 25;
        final String moves2 = new String("URDRDLLURURDDLLUURRDDLULU");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    public void test11() {
        int[][] initialState = buildBoardFromString("876543021");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 28;
        final String moves2 = new String("DDLURULDLURRDDLULDRUURDDLUUL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    @Test
    @Disabled
    public void test12() {
        int[][] initialState = buildBoardFromString("876543210");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 30;
        final String moves2 = new String("DRDRUULDDRUULDDLUURDDLURDRULUL");

        AStar astar = new AStar(initialState, goalState);
        astar.findSolution();

        assertEquals(steps, astar.getSteps());
        assertEquals(moves2, astar.getMoves());
    }

    private int[][] buildBoardFromString(String boardAsString) {
        int length = (int) Math.sqrt(boardAsString.length());
        int[][] board = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = Character.getNumericValue(boardAsString.charAt(i * length + j));
            }
        }

        return board;
    }


}