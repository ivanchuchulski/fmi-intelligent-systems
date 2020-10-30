import java.util.*;

/**
 * Implementation of A* algorithm for finding a solution
 *
 * @author ngadzheva
 */
public class Astar {
    private Node currentState;
    private final Node finalState;
    private final int boardSize;
    private int level;

    public Astar(int[][] initialState, int[][] goalState) {
        this.currentState = new Node(initialState, null, manhattanSum(initialState), "", level);
        this.finalState = new Node(goalState, null, -1, "", -1);
        this.boardSize = initialState.length;
        this.level = 0;
    }

    public void findSolution() {
        Set<Node> visited = new HashSet<Node>();
        PriorityQueue<Node> fringe = new PriorityQueue<>(new Node.NodeComparator());

        fringe.add(currentState);

        while (!fringe.isEmpty()) {
            currentState = fringe.remove();

            level = currentState.getPath();

            if (isGoalReached()) {
                break;
            }

            visited.add(currentState);

            for (Node state : getChildNodes()) {
                if (visited.contains(state)) {
                    continue;
                }

                if (!fringe.contains(state)) {
                    fringe.add(state);
                }
            }
        }
    }

    public void printResult() {
        System.out.println(currentState.getPath());

        Stack<String> result = new Stack<>();

        for (int i = 0; i < level; i++) {
            result.add(currentState.getDirection());
            currentState = currentState.getParent();
        }

        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

    public String[] getMoves() {
        Stack<String> result = new Stack<>();

        for (int i = 0; i < level; i++) {
            result.add(currentState.getDirection());
            currentState = currentState.getParent();
        }

        String[] moves = new String[result.size()];
        int i = 0;

        while (!result.isEmpty()) {
            moves[i++] = result.pop();
        }

        return moves;
    }

    public int getSteps() {
        return currentState.getPath();
    }

    private Node[] getChildNodes() {
        EmptyPositionLocation empty = getZeroPosition();

        ++level;

        Node up = moveTileUp(empty);
        Node down = moveTileDown(empty);
        Node left = moveTileLeft(empty);
        Node right = moveTileRight(empty);

        return new Node[]{up, down, left, right};
    }

    // move zero position down
    private Node moveTileUp(EmptyPositionLocation empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() < boardSize - 1) {
            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = Main.EMPTY_SLOT;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.up, level);
    }


    // move zero position up
    private Node moveTileDown(EmptyPositionLocation empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() > 0) {
            childBoard[row][col] = childBoard[row - 1][col];
            childBoard[row - 1][col] = Main.EMPTY_SLOT;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.down, level);
    }

    // move zero position right
    private Node moveTileLeft(EmptyPositionLocation empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col < boardSize - 1) {
            childBoard[row][col] = childBoard[row][col + 1];
            childBoard[row][col + 1] = 0;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.left, level);
    }

    // move zero position left
    private Node moveTileRight(EmptyPositionLocation empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col > 0) {
            childBoard[row][col] = childBoard[row][col - 1];
            childBoard[row][col - 1] = 0;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.right, level);
    }

    /**
     * Make copy of the current state
     *
     * @param current - the current state
     */
    private int[][] makeCopyState(int[][] current) {
        int[][] copyState = new int[boardSize][boardSize];

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                copyState[i][j] = current[i][j];
            }
        }

        return copyState;
    }

    /**
     * Find the coordinates of the empty position
     *
     * @return the coordinates of the empty position
     */
    private EmptyPositionLocation getZeroPosition() {
        EmptyPositionLocation emptyPositionLocation = new EmptyPositionLocation();

        int[][] current = currentState.getState();

        for (int row = 0; row < boardSize; ++row) {
            for (int column = 0; column < boardSize; ++column) {
                if (current[row][column] == 0) {
                    emptyPositionLocation.setRow(row);
                    emptyPositionLocation.setColumn(column);
                    break;
                }
            }
        }

        return emptyPositionLocation;
    }

    /**
     * Heuristic function evaluating the path to the current state
     * Implementation of Manhattan Distance
     * For every node of the current state calculate |Xg - Xc| + |Yg - Yc|
     * and sum the results, where
     * Xg is the row of the current node's i value in the final node
     * Xc is the row of the current node's i value
     * Yg is the column of the current node's i value in the final node
     * Yc is the column of the current node's i value
     *
     * @return the heuristic value
     */
    private int manhattanSum(int[][] state) {
        int sum = 0;

        for (int row = 0; row < boardSize; ++row) {
            for (int column = 0; column < boardSize; ++column) {
                int value = state[row][column];

                if (value == 0) {
                    sum += Math.abs(boardSize - 1 - row); // row difference
                    sum += Math.abs(boardSize - 1 - column); // column difference
                }
                else {
                    sum += Math.abs(((value - 1) / boardSize) - row); // row difference
                    sum += Math.abs(((value - 1) % boardSize) - column); // column difference
                }
            }
        }

        return sum;
    }

    private int calculateStateFullCost(int[][] state) {
        return level + manhattanSum(state);
    }

    private boolean isGoalReached() {
        int[][] current = currentState.getState();
        int[][] goal = finalState.getState();

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (current[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
