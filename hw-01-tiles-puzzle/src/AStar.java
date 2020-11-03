import java.util.*;

public class AStar {
    private Node initialNode;
    private Node finalNode;

    private final int boardSize;
    private int level;

    private int solutionSteps;
    private String solutionMoves;

    private Map<Integer, Position> goalStatePositions;

    public AStar(int[][] initialState, int[][] goalState) {
        this.level = 0;
        this.boardSize = initialState.length;
        goalStatePositions = new HashMap<>();

        for (int row = 0; row < goalState.length; row++) {
            for (int col = 0; col < goalState.length; col++) {
                if (goalState[row][col] == Main.EMPTY_TILE) {
                    continue;
                }
                goalStatePositions.put(goalState[row][col], new Position(row, col));
            }
        }

        this.initialNode = new Node(initialState, null, "", 0, manhattanSum(initialState));
        this.finalNode = new Node(goalState, null, "", -1, -1);
    }

    public void findSolution() {
        Set<Node> visited = new HashSet<Node>();
        PriorityQueue<Node> fringe = new PriorityQueue<>(new Node.NodeComparator());

        fringe.add(initialNode);

        while (!fringe.isEmpty()) {
            initialNode = fringe.remove();

            level = initialNode.getStepsFromStartG();

            if (isGoalReached()) {
                break;
            }

            visited.add(initialNode);

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
        System.out.println(initialNode.getStepsFromStartG());

        Stack<String> result = new Stack<>();

        for (int i = 0; i < level; i++) {
            result.add(initialNode.getDirection());
            initialNode = initialNode.getParent();
        }

        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

    public String getMoves() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < level; i++) {
            stringBuilder.append(initialNode.getDirection());
            initialNode = initialNode.getParent();
        }

        return stringBuilder.reverse().toString();
    }

    public int getSteps() {
        return initialNode.getStepsFromStartG();
    }

    private Node[] getChildNodes() {
        Position emptyTile = getEmptyPosition();

        ++level;

        Node up = moveTileUp(emptyTile);
        Node down = moveTileDown(emptyTile);
        Node left = moveTileLeft(emptyTile);
        Node right = moveTileRight(emptyTile);

        return new Node[]{up, down, left, right};
    }

    // move zero position down
    private Node moveTileUp(Position empty) {
        int[][] childBoard = makeCopyState(initialNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() < boardSize - 1) {
            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, initialNode, Directions.up, level, calculateStateFullCost(childBoard));
    }


    // move zero position up
    private Node moveTileDown(Position empty) {
        int[][] childBoard = makeCopyState(initialNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() > 0) {
            childBoard[row][col] = childBoard[row - 1][col];
            childBoard[row - 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, initialNode, Directions.down, level, calculateStateFullCost(childBoard));
    }

    // move zero position right
    private Node moveTileLeft(Position empty) {
        int[][] childBoard = makeCopyState(initialNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col < boardSize - 1) {
            childBoard[row][col] = childBoard[row][col + 1];
            childBoard[row][col + 1] = 0;
        }

        return new Node(childBoard, initialNode, Directions.left, level, calculateStateFullCost(childBoard));
    }

    // move zero position left
    private Node moveTileRight(Position empty) {
        int[][] childBoard = makeCopyState(initialNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col > 0) {
            childBoard[row][col] = childBoard[row][col - 1];
            childBoard[row][col - 1] = 0;
        }

        return new Node(childBoard, initialNode, Directions.right, level, calculateStateFullCost(childBoard));
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

    private Position getEmptyPosition() {
        Position position = new Position();

        int[][] current = initialNode.getState();

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

                Position goalPosition = goalStatePositions.get(tile);

                manhattanSum += Math.abs(goalPosition.getRow() - row);
                manhattanSum += Math.abs(goalPosition.getColumn() - col);
            }
        }

        return manhattanSum;
    }

    private int calculateStateFullCost(int[][] state) {
        return level + manhattanSum(state);
    }

    private boolean isGoalReached() {
        int[][] current = initialNode.getState();
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
}
