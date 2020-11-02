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

    // 4x4 tests

    @Test
    public void test13() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 0, 9, 10, 11, 8, 13, 14, 15, 12});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 2;
        final String moves = new String("UU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test14() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 9, 6, 11, 12, 13, 10, 14, 15});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 4;
        final String moves = new String("UULL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test15() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 6, 2, 4, 5, 0, 3, 8, 9, 10, 7, 11, 13, 14, 15, 12});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 6;
        final String moves = new String("DLUULU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test16() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 4, 12, 5, 6, 3, 0, 9, 10, 8, 7, 13, 14, 11, 15});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 10;
        final String moves = new String("DRUULDRUUL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test17() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 8, 3, 5, 11, 6, 4, 0, 10, 7, 12, 9, 13, 14, 15});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 14;
        final String moves = new String("ULDDLDLURURULL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test18() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 5, 3, 4, 1, 7, 11, 8, 9, 6, 0, 12, 13, 14, 15, 10});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 18;
        final String moves = new String("DLUURDLDRRDRULULUL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test19() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 4, 8, 3, 7, 2, 10, 11, 5, 6, 0, 15, 9, 13, 14, 12});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 22;
        final String moves = new String("LDDRRURUULLDDLDRURULLU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test20() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 5, 4, 7, 9, 1, 3, 8, 11, 10, 0, 6, 14, 13, 15, 12});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 28;
        final String moves = new String("LDDRUURRULDRDLDRULLLUURRDLUL");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test21() {
        int[][] initialState = buildBoardFromArray(new int[]{9, 5, 8, 3, 6, 0, 10, 11, 2, 1, 14, 7, 13, 15, 12, 4});
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 34;
        final String moves = new String("ULDLUURRDRDDLUURDDLUULDLURDDLURULU");

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves, idaStar.getMoves());
    }

    @Test
    @Disabled
    public void test22() {
        int[][] initialState = buildBoardFromArray(new int[]{3, 14, 2, 4, 9, 1, 7, 8, 0, 12, 6, 10, 13, 5, 11, 15
        });
        int[][] goalState = Main.generateGoalState(initialState);

        final int steps = 38;
        final String moves = new String("ULDLUURRDRDDLUURDDLUULDLURDDLURULU");

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

    private int[][] buildBoardFromArray(int[] arr) {
        int length = (int) Math.sqrt(arr.length);
        int[][] board = new int[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                board[i][j] = arr[i * length + j];
            }
        }

        return board;
    }

}