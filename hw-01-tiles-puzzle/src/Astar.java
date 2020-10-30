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

    private Map<Integer, Position> currentStatePositions;
    private Map<Integer, Position> goalStatePositions;

    public Astar(int[][] initialState, int[][] goalState) {
        this.currentState = new Node(initialState, null, manhattanSum(initialState), "", level);
        this.finalState = new Node(goalState, null, -1, "", -1);
        this.boardSize = initialState.length;
        this.level = 0;
        goalStatePositions = new HashMap<>();

        for (int row = 0; row < goalState.length; row++) {
            for (int col = 0; col < goalState.length; col++) {
                if (goalState[row][col] == Main.EMPTY_TILE) {
                    continue;
                }
                goalStatePositions.put(goalState[row][col], new Position(row, col));
            }
        }
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

    public String getMoves() {
        Stack<String> result = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < level; i++) {
//            result.add(currentState.getDirection());

            stringBuilder.append(currentState.getDirection());
            currentState = currentState.getParent();
        }

        return stringBuilder.reverse().toString();
    }

    public int getSteps() {
        return currentState.getPath();
    }

    private Node[] getChildNodes() {
        Position empty = getZeroPosition();

        ++level;

        Node up = moveTileUp(empty);
        Node down = moveTileDown(empty);
        Node left = moveTileLeft(empty);
        Node right = moveTileRight(empty);

        return new Node[]{up, down, left, right};
    }

    // move zero position down
    private Node moveTileUp(Position empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() < boardSize - 1) {
            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.up, level);
    }


    // move zero position up
    private Node moveTileDown(Position empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() > 0) {
            childBoard[row][col] = childBoard[row - 1][col];
            childBoard[row - 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.down, level);
    }

    // move zero position right
    private Node moveTileLeft(Position empty) {
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
    private Node moveTileRight(Position empty) {
        int[][] childBoard = makeCopyState(currentState.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col > 0) {
            childBoard[row][col] = childBoard[row][col - 1];
            childBoard[row][col - 1] = 0;
        }

        return new Node(childBoard, currentState, calculateStateFullCost(childBoard), Directions.right, level);
    }

    private int[][] makeCopyState(int[][] current) {
        int[][] copyState = new int[boardSize][boardSize];

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                copyState[i][j] = current[i][j];
            }
        }

        return copyState;
    }

    private Position getZeroPosition() {
        Position position = new Position();

        int[][] current = currentState.getState();

        for (int row = 0; row < boardSize; ++row) {
            for (int col = 0; col < boardSize; ++col) {
                if (current[row][col] == 0) {
                    position.setRow(row);
                    position.setColumn(col);
                    break;
                }
            }
        }

        return position;
    }

    private int manhattanSum(int[][] state) {
        int manhattanSum = 0;

        for (int row = 0; row < boardSize; ++row) {
            for (int col = 0; col < boardSize; ++col) {
                int tile = state[row][col];

                if (tile == Main.EMPTY_TILE) {
                    continue;
                }

//                int rowGoal = Math.abs(((tile - 1) / boardSize));
//                int colGoal = Math.abs(((tile - 1) % boardSize));

                Position goalPosition = goalStatePositions.get(tile);

                manhattanSum += goalPosition.getRow() - row;
                manhattanSum += goalPosition.getColumn() - col;
            }
        }

        return manhattanSum;
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
