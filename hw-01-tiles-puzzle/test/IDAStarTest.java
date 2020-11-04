import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDAStarTest {
    @Test
    public void test1() {
        int[][] initialState = buildBoardFromString("123456078");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 2;
        final String moves = "LL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test2() {
        int[][] initialState = buildBoardFromString("123745086");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 4;
        final String moves = "DLLU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test3() {
        int[][] initialState = buildBoardFromString("123480765");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 5;
        final String moves = "URDLU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test4() {
        int[][] initialState = buildBoardFromString("413726580");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 8;
        final String moves = "RRDDLUUL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test5() {
        int[][] initialState = buildBoardFromString("162530478");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 9;
        final String moves = "RDLURRULL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test6() {
        int[][] initialState = buildBoardFromString("512630478");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 11;
        final String moves = "RRDLLURRULL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test7() {
        int[][] initialState = buildBoardFromString("126350478");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 13;
        final String moves = "DRURULLDRDLUU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test8() {
        int[][] initialState = buildBoardFromString("436871052");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 18;
        final String moves = "DLLDRUULDRURDDLULU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test9() {
        int[][] initialState = buildBoardFromString("503284671");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 23;
        final String moves = "RUULLDRRULLDRRULDDRUULL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test10() {
        int[][] initialState = buildBoardFromString("874320651");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 25;
        final String moves = "URDRDLLURURDDLLUURRDDLULU";
        final String moves2 = "URDRDLULDRUURDDLULURRDLLU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        assertEquals(moves2, idaStar.getMoves());
    }

    @Test
    public void test11() {
        // has 2 solutions
        int[][] initialState = buildBoardFromString("876543021");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 28;
        final String moves1 = "DDLURULDLURRDDLULDRUURDDLUUL";
        final String moves = "DDLULURRDDLULDRUULDRURDDLULU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test12() {
        // has 2 solutions
        int[][] initialState = buildBoardFromString("876543210");
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 30;
        final String moves = "DRDRUULDDRUULDDLUURDDLURDRULUL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    // 4x4 tests

    @Test
    public void test13() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 0, 9, 10, 11, 8, 13, 14, 15, 12});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 2;
        final String moves = "UU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test14() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 9, 6, 11, 12, 13, 10, 14, 15});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 4;
        final String moves = "UULL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test15() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 6, 2, 4, 5, 0, 3, 8, 9, 10, 7, 11, 13, 14, 15, 12});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 6;
        final String moves = "DLUULU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test16() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 4, 12, 5, 6, 3, 0, 9, 10, 8, 7, 13, 14, 11, 15});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 10;
        final String moves = "DRUULDRUUL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test17() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 2, 8, 3, 5, 11, 6, 4, 0, 10, 7, 12, 9, 13, 14, 15});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 14;
        final String moves = "ULDDLDLURURULL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test18() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 5, 3, 4, 1, 7, 11, 8, 9, 6, 0, 12, 13, 14, 15, 10});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 18;
        final String moves = "DLUURDLDRRDRULULUL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test19() {
        int[][] initialState = buildBoardFromArray(new int[]{1, 4, 8, 3, 7, 2, 10, 11, 5, 6, 0, 15, 9, 13, 14, 12});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 22;
        final String moves = "LDDRRURUULLDDLDRURULLU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test20() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 5, 4, 7, 9, 1, 3, 8, 11, 10, 0, 6, 14, 13, 15, 12});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 28;
        final String moves = "LDDRUURRULDRDLDRULLLUURRDLUL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test21() {
        int[][] initialState = buildBoardFromArray(new int[]{9, 5, 8, 3, 6, 0, 10, 11, 2, 1, 14, 7, 13, 15, 12, 4});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 34;
        final String moves = "ULDLUURRDRDDLUURDDLUULDLURDDLURULU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test22() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 6, 4, 8, 1, 10, 7, 3, 5, 13, 11, 15, 12, 14, 9, 0});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 38;
        final String moves = "DDRRDRUULULDRURDLLDLUURRDLULDRDLDRUUUL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    public void test23() {
        int[][] initialState = buildBoardFromArray(new int[]{14, 5, 11, 6, 9, 0, 4, 3, 15, 1, 7, 12, 8, 2, 13, 10});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 44;
        final String moves = "UULLDRRRULDRDDLURDLLLURURDDLURDLLUURURDLDLUU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    @Disabled
    public void test24() {
        int[][] initialState = buildBoardFromArray(new int[]{13, 11, 9, 3, 14, 7, 1, 4, 0, 5, 10, 12, 15, 2, 6, 8});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 46;
        final String moves = "DDLUUURDDLLDRUULURRDDDLULULURRDDRULDLDLUURRULL";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
    }

    @Test
    @Disabled
    public void test25() {
        int[][] initialState = buildBoardFromArray(new int[]{2, 9, 3, 5, 8, 11, 12, 7, 15, 4, 0, 13, 6, 1, 10, 14});
        int[][] goalState = generateDefualtGoalState(initialState);

        final int steps = 50;
        final String moves = "LDRRUULDLDRRRULULLDDRRRUULDDDLLURDRRULLURRDLUULDLU";

        IDAStar idaStar = new IDAStar(initialState, goalState);
        idaStar.findSolution();

        assertEquals(steps, idaStar.getSteps());
        // assertEquals(moves, idaStar.getMoves());
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

    public int[][] generateDefualtGoalState(int[][] initialBoard) {
        int[][] goalState = new int[initialBoard.length][initialBoard.length];
        int tilesNumber = 1;

        for (int i = 0; i < initialBoard.length; i++) {
            for (int j = 0; j < initialBoard.length; j++) {
                if (i == initialBoard.length - 1 && j == initialBoard.length - 1) {
                    goalState[i][j] = 0;
                }
                else {
                    goalState[i][j] = tilesNumber++;
                }
            }
        }

        return goalState;
    }

}