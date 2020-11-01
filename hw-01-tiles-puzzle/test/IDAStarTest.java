import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDAStarTest {
    @Test
    public void test1() {
        int[][] initialState = buildBoardFromString("123456078");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 2;
        final String moves = new String("LL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test2() {
        int[][] initialState = buildBoardFromString("123745086");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 4;
        final String moves = new String("DLLU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test3() {
        int[][] initialState = buildBoardFromString("123480765");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 5;
        final String moves = new String("URDLU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test4() {
        int[][] initialState = buildBoardFromString("413726580");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 8;
        final String moves = new String("RRDDLUUL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
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