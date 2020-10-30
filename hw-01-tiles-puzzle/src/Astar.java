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

            if (isGoal()) {
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
        EmptyPositionLocation zeroPos = getZeroPosition();

        int row = zeroPos.getRow();
        int col = zeroPos.getColumn();
        int[][] current = currentState.getState();

        ++level;

        Node up = moveUp(makeCopyState(current), row, col);
        Node down = moveDown(makeCopyState(current), row, col);
        Node left = moveLeft(makeCopyState(current), row, col);
        Node right = moveRight(makeCopyState(current), row, col);

        Node[] childStates = {up, down, left, right};

        Node[] children = {myMoveUp(zeroPos), down, left, right};

        return childStates;
    }

    /**
     * Move zero position down
     *
     * @param current - current state
     * @param row     - row of the zero position
     * @param col     - col of the zero position
     */
    private Node moveUp(int[][] current, int row, int col) {
        if (row < boardSize - 1) {
            current[row][col] = current[row + 1][col];
            current[row + 1][col] = 0;
        }

        Node up = new Node(current, currentState, calculateFullCost(current), "up", level);

        return up;
    }

    private Node myMoveUp(EmptyPositionLocation empty) {
        int[][] childBoard = makeCopyState(currentState.getState());

        if (empty.getRow() < boardSize - 1) {
            childBoard[empty.getRow()][empty.getRow()] = childBoard[empty.getRow() + 1][empty.getColumn()];
            childBoard[empty.getRow() + 1][empty.getColumn()] = Main.EMPTY_SLOT;
        }

        return new Node(childBoard, currentState, calculateFullCost(childBoard), Directions.up, level);
    }

    /**
     * Move zero position up
     *
     * @param current - current state
     * @param row     - row of the zero position
     * @param col     - col of the zero position
     */
    private Node moveDown(int[][] current, int row, int col) {
        if (row > 0) {
            current[row][col] = current[row - 1][col];
            current[row - 1][col] = 0;
        }

        Node down = new Node(current, currentState, calculateFullCost(current), "down", level);

        return down;
    }

    /**
     * Move zero position to the right
     *
     * @param current - current state
     * @param row     - row of the zero position
     * @param col     - col of the zero position
     */
    private Node moveLeft(int[][] current, int row, int col) {
        if (col < boardSize - 1) {
            current[row][col] = current[row][col + 1];
            current[row][col + 1] = 0;
        }

        Node left = new Node(current, currentState, calculateFullCost(current), "left", level);

        return left;
    }

    /**
     * Move zero position to the left
     *
     * @param current - current position
     * @param row     - row of the current position
     * @param col     - col of the current position
     */
    private Node moveRight(int[][] current, int row, int col) {
        if (col > 0) {
            current[row][col] = current[row][col - 1];
            current[row][col - 1] = 0;
        }

        Node right = new Node(current, currentState, calculateFullCost(current), "right", level);

        return right;
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

    private int calculateFullCost(int[][] state) {
        int cost = level + manhattanSum(state);

        return cost;
    }

    private boolean isGoal() {
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
