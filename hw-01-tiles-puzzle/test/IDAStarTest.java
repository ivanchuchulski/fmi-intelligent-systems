import org.junit.jupiter.api.Disabled;
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

    @Test
    public void test5() {
        int[][] initialState = buildBoardFromString("162530478");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 9;
        final String moves = new String("RDLURRULL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test6() {
        int[][] initialState = buildBoardFromString("512630478");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 11;
        final String moves = new String("RRDLLURRULL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test7() {
        int[][] initialState = buildBoardFromString("126350478");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 13;
        final String moves = new String("DRURULLDRDLUU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test8() {
        int[][] initialState = buildBoardFromString("436871052");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 18;
        final String moves = new String("DLLDRUULDRURDDLULU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test9() {
        int[][] initialState = buildBoardFromString("503284671");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 23;
        final String moves = new String("RUULLDRRULLDRRULDDRUULL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test10() {
        int[][] initialState = buildBoardFromString("874320651");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 25;
        final String moves = new String("URDRDLLURURDDLLUURRDDLULU");
        final String moves2 = new String("URDRDLULDRUURDDLULURRDLLU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves2, idaStar.getMoves());
    }

    @Test
    public void test11() {
        // has 2 solutions
        int[][] initialState = buildBoardFromString("876543021");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 28;
        final String moves1 = new String("DDLURULDLURRDDLULDRUURDDLUUL");
        final String moves = new String("DDLULURRDDLULDRUULDRURDDLULU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    @Disabled
    public void test12() {
        // has 2 solutions
        int[][] initialState = buildBoardFromString("876543210");
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 30;
        final String moves = new String("DRDRUULDDRUULDDLUURDDLURDRULUL");

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