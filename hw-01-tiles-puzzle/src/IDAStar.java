import java.util.*;

public class IDAStar {
    private Node currentNode;
    private final Node finalNode;
    private final int boardSize;
    private int level;

    private boolean goalFound = false;

    private Map<Integer, Position> goalStatePositions;

    public IDAStar(int[][] initialState, int[][] goalState) {
        this.currentNode = new Node(initialState, null, manhattanSum(initialState), "", level);
        this.finalNode = new Node(goalState, null, -1, "", -1);
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
        int limit = currentNode.getTotalCost();

        Stack<Node> solutionPath = new Stack<>();
        solutionPath.add(currentNode);

        while (!goalFound) {
            recursiveSearch(currentNode, limit);

        }

    }

    private void recursiveSearch(Node node, int limit) {
        Set<Node> visited = new HashSet<Node>();

        if (isGoalReached()) {
            goalFound = true;
            return;
        }

        visited.add(currentNode);

        for (Node child : getChildNodes()) {
            int childCost = child.getTotalCost();

            if (childCost > limit) {

            }

        }
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
        int[][] current = currentNode.getState();
        int[][] goal = finalNode.getState();

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (current[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }

        return true;
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
        int[][] childBoard = makeCopyState(currentNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() < boardSize - 1) {
            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, currentNode, calculateStateFullCost(childBoard), Directions.up, level);
    }


    // move zero position up
    private Node moveTileDown(Position empty) {
        int[][] childBoard = makeCopyState(currentNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() > 0) {
            childBoard[row][col] = childBoard[row - 1][col];
            childBoard[row - 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, currentNode, calculateStateFullCost(childBoard), Directions.down, level);
    }

    // move zero position right
    private Node moveTileLeft(Position empty) {
        int[][] childBoard = makeCopyState(currentNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col < boardSize - 1) {
            childBoard[row][col] = childBoard[row][col + 1];
            childBoard[row][col + 1] = 0;
        }

        return new Node(childBoard, currentNode, calculateStateFullCost(childBoard), Directions.left, level);
    }

    // move zero position left
    private Node moveTileRight(Position empty) {
        int[][] childBoard = makeCopyState(currentNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col > 0) {
            childBoard[row][col] = childBoard[row][col - 1];
            childBoard[row][col - 1] = 0;
        }

        return new Node(childBoard, currentNode, calculateStateFullCost(childBoard), Directions.right, level);
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

        int[][] current = currentNode.getState();

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
}
