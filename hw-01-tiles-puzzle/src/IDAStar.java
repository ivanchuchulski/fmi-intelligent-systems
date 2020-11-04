import java.util.*;

public class IDAStar {
    private final Node initialNode;
    private Node finalNode;

    private final int boardSize;

    private int solutionSteps;
    private String solutionMoves;

    private final Map<Integer, Position> goalStatePositions;

    final int FOUND = 0;

    public IDAStar(int[][] initialState, int[][] goalState) {
        this.boardSize = initialState.length;
        goalStatePositions = new HashMap<>();

        for (int row = 0; row < initialState.length; row++) {
            for (int col = 0; col < initialState.length; col++) {
                if (goalState[row][col] == Main.EMPTY_TILE) {
                    continue;
                }
                goalStatePositions.put(goalState[row][col], new Position(row, col));
            }
        }

        int initialManh = manhattanSum(initialState);
        Position initialEmpty = findEmptyPosition(initialState);

        this.initialNode = new Node(initialState, null, "", 0, initialManh, initialEmpty);
        this.finalNode = new Node(goalState, null, "", -1, -1, new Position(-1, -1));
    }

    public String getMoves() {
        return solutionMoves;
    }

    public int getSteps() {
        return solutionSteps;
    }

    public void printInfo() {
        String moves = getMoves();

        System.out.println("#steps : " + getSteps());
        System.out.println("moves : " + getMoves());

        long aftermem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("\nMemory used: " + (aftermem / 1024) + "kb");
    }

    public void findSolution() {
        int currentLimit = manhattanSum(initialNode.getState());

        while (true) {
            int smallestLimitOverCurrent = recursiveSearch(initialNode, 0, currentLimit);

            if (smallestLimitOverCurrent == FOUND) {
                break;
            }
            if (smallestLimitOverCurrent == Integer.MAX_VALUE) {
                throw new RuntimeException("unreachable goal");
            }

            currentLimit = smallestLimitOverCurrent;
        }

        solutionSteps = finalNode.getStepsFromStartG();

        StringBuilder stringBuilder = new StringBuilder();

        do {
            stringBuilder.append(finalNode.getDirection());
            finalNode = finalNode.getParent();
        }
        while (finalNode != null);

        solutionMoves = stringBuilder.reverse().toString();
    }

    private int recursiveSearch(Node node, int stepsToNodeG, int currentFLimit) {
        if (node.getTotalCostF() > currentFLimit) {
            return node.getTotalCostF();
        }

        if (isGoalReached(node)) {
            finalNode = node;
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Node child : getChildNodes(node)) {
            if (child == null) {
                continue;
            }

            if (node.getParent() != null && node.getParent().equals(child)) {
                continue;
            }

            int temp = recursiveSearch(child, stepsToNodeG + 1, currentFLimit);

            if (temp == FOUND) {
                return FOUND;
            }
            if (temp < minF) {
                minF = temp;
            }
        }

        return minF;
    }

    private boolean isGoalReached(Node node) {
        int[][] current = node.getState();
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

    private Node[] getChildNodes(Node node) {
        Node up = moveTileUp(node);
        Node down = moveTileDown(node);
        Node left = moveTileLeft(node);
        Node right = moveTileRight(node);

        return new Node[]{up, right, left, down};
    }

    // move zero position down
    private Node moveTileUp(Node node) {
        int row = node.getEmpty().getRow();
        int col = node.getEmpty().getColumn();

        if (row < boardSize - 1) {
            int[][] childBoard = makeCopyState(node.getState());

            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = 0;

            Position emptyInChild = new Position(row + 1, col);

            int movedTile = childBoard[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row + 1, col);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(childBoard, node, Directions.up, node.getStepsFromStartG() + 1, childManh, emptyInChild);
        }
        else {
            return null;
        }
    }

    // move zero position up
    private Node moveTileDown(Node node) {
        int row = node.getEmpty().getRow();
        int col = node.getEmpty().getColumn();

        if (row > 0) {
            int[][] childBoard = makeCopyState(node.getState());

            childBoard[row][col] = childBoard[row - 1][col];
            childBoard[row - 1][col] = 0;

            Position emptyInChild = new Position(row - 1, col);

            int movedTile = childBoard[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row - 1, col);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(childBoard, node, Directions.down, node.getStepsFromStartG() + 1, childManh, emptyInChild);
        }
        else {
            return null;
        }
    }

    // move zero position right
    private Node moveTileLeft(Node node) {
        int row = node.getEmpty().getRow();
        int col = node.getEmpty().getColumn();

        if (col < boardSize - 1) {
            int[][] childBoard = makeCopyState(node.getState());

            childBoard[row][col] = childBoard[row][col + 1];
            childBoard[row][col + 1] = 0;

            Position emptyInChild = new Position(row, col + 1);

            int movedTile = childBoard[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row, col + 1);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(childBoard, node, Directions.left, node.getStepsFromStartG() + 1, childManh, emptyInChild);
        }
        else {
            return null;
        }
    }

    // move zero position left
    private Node moveTileRight(Node node) {
        int row = node.getEmpty().getRow();
        int col = node.getEmpty().getColumn();

        if (col > 0) {
            int[][] childBoard = makeCopyState(node.getState());

            childBoard[row][col] = childBoard[row][col - 1];
            childBoard[row][col - 1] = 0;

            Position emptyInChild = new Position(row, col - 1);

            int movedTile = childBoard[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row, col - 1);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(childBoard, node, Directions.right, node.getStepsFromStartG() + 1, childManh, emptyInChild);
        }
        else {
            return null;
        }
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

    private Position findEmptyPosition(int[][] board) {
        Position position = new Position();

        for (int row = 0; row < board.length; ++row) {
            for (int col = 0; col < board.length; ++col) {
                if (board[row][col] == 0) {
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

        for (int row = 0; row < state.length; ++row) {
            for (int col = 0; col < state.length; ++col) {
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

}
