import java.util.*;

public class AStar {
    private Node currentNode;
    private final Node finalNode;
    private final int boardSize;
    private int level;
    private Map<Integer, Position> goalStatePositions;

    public AStar(int[][] initialState, int[][] goalState) {
        this.currentNode = new Node(initialState, null, "", level, manhattanSum(initialState));
        this.finalNode = new Node(goalState, null, "", -1, -1);
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

        fringe.add(currentNode);

        while (!fringe.isEmpty()) {
            currentNode = fringe.remove();

            level = currentNode.getStepsFromStartG();

            if (isGoalReached()) {
                break;
            }

            visited.add(currentNode);

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
        System.out.println(currentNode.getStepsFromStartG());

        Stack<String> result = new Stack<>();

        for (int i = 0; i < level; i++) {
            result.add(currentNode.getDirection());
            currentNode = currentNode.getParent();
        }

        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

    public String getMoves() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < level; i++) {
            stringBuilder.append(currentNode.getDirection());
            currentNode = currentNode.getParent();
        }

        return stringBuilder.reverse().toString();
    }

    public int getSteps() {
        return currentNode.getStepsFromStartG();
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
        int[][] childBoard = makeCopyState(currentNode.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() < boardSize - 1) {
            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = Main.EMPTY_TILE;
        }

        return new Node(childBoard, currentNode, Directions.up, level, calculateStateFullCost(childBoard));
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

        return new Node(childBoard, currentNode, Directions.down, level, calculateStateFullCost(childBoard));
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

        return new Node(childBoard, currentNode, Directions.left, level, calculateStateFullCost(childBoard));
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

        return new Node(childBoard, currentNode, Directions.right, level, calculateStateFullCost(childBoard));
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
}
