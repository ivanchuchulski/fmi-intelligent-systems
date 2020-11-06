import java.util.*;

public class IDAStar {
    private final Node initialNode;
    private Node finalNode;

    private final int boardSize;

    private int numberOfStepsToSolution;
    private String solutionMoves;

    private final Map<Integer, Position> goalStatePositions;

    final int FOUND = 0;

    int[][] workState;
    int[][] goalState;
    Directions[] DIRECTIONS = {Directions.UP, Directions.RIGHT, Directions.LEFT, Directions.DOWN};

    public IDAStar(int[][] initialState, int[][] goalState) {
        this.boardSize = initialState.length;
        goalStatePositions = new HashMap<>();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (goalState[row][col] == Main.EMPTY_TILE) {
                    continue;
                }
                goalStatePositions.put(goalState[row][col], new Position(row, col));
            }
        }

        int initialManh = manhattanSum(initialState);
        Position initialEmpty = findEmptyPosition(initialState);

        this.initialNode = new Node(null, Directions.NONE, 0, initialManh, initialEmpty);
        // this.finalNode = new Node(goalState, null, Directions.NONE, -1, -1, new Position(-1, -1));

        this.goalState = goalState;

        workState = makeCopyState(initialState);
    }

    public String getMoves() {
        return solutionMoves;
    }

    public int getNumberOfStepsToSolution() {
        return numberOfStepsToSolution;
    }

    public void printInfo() {
        String moves = getMoves();

        System.out.println("#steps : " + getNumberOfStepsToSolution());
        System.out.println("moves : " + getMoves());
    }

    public void findSolution() {
        int currentLimit = initialNode.getManhattanH();

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

        numberOfStepsToSolution = finalNode.getStepsFromStartG();

        buildSolutionMoves();
    }

    private int recursiveSearch(Node node, int stepsToNodeG, int currentFLimit) {
        if (node.getTotalCostF() > currentFLimit) {
            return node.getTotalCostF();
        }

        if (isGoalReached()) {
            finalNode = node;
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Directions direction : DIRECTIONS) {
            Node child = goDirection(direction, node);

            if (child == null) {
                continue;
            }

            if (node.getParent() != null) {
                Directions parentDir = node.getDirection();
                if (parentDir.equals(Directions.UP) && direction.equals(Directions.DOWN)) {
                    undoDirection(direction, child);
                    continue;
                }
                if (parentDir.equals(Directions.DOWN) && direction.equals(Directions.UP)) {
                    undoDirection(direction, child);
                    continue;
                }
                if (parentDir.equals(Directions.RIGHT) && direction.equals(Directions.LEFT)) {
                    undoDirection(direction, child);
                    continue;
                }
                if (parentDir.equals(Directions.LEFT) && direction.equals(Directions.RIGHT)) {
                    undoDirection(direction, child);
                    continue;
                }
            }

            int temp = recursiveSearch(child, stepsToNodeG + 1, currentFLimit);

            if (temp == FOUND) {
                return FOUND;
            }
            if (temp < minF) {
                minF = temp;
            }

            undoDirection(direction, child);
        }

        return minF;
    }

    private Node goDirection(Directions direction, Node parent) {
        Node result = null;

        switch (direction) {
            case UP -> result = moveTileUp(parent);
            case DOWN -> result = moveTileDown(parent);
            case RIGHT -> result = moveTileRight(parent);
            case LEFT -> result = moveTileLeft(parent);
        }

        return result;
    }

    private void undoDirection(Directions direction, Node child) {
        Position empty = child.getEmpty();

        switch (direction) {
            case UP -> downTransition(empty);
            case DOWN -> upTransition(empty);
            case RIGHT -> leftTransition(empty);
            case LEFT -> rightTransition(empty);
        }
    }

    private boolean isGoalReached() {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (workState[i][j] != goalState[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // move zero position down
    private Node moveTileUp(Node parent) {
        Position parentEmptyTile = parent.getEmpty();
        int row = parentEmptyTile.getRow();
        int col = parentEmptyTile.getColumn();

        if (row < boardSize - 1) {
            upTransition(parent.getEmpty());

            int movedTile = workState[row][col];
            Position childEmptyTile = new Position(row + 1, col);

            int childManhattan = calculateManhattanDifference(parent.getManhattanH(), goalStatePositions.get(movedTile), childEmptyTile, parentEmptyTile);

            return new Node(parent, Directions.UP, parent.getStepsFromStartG() + 1, childManhattan, childEmptyTile);
        }
        else {
            return null;
        }
    }

    // move zero position up
    private Node moveTileDown(Node parent) {
        Position parentEmptyTile = parent.getEmpty();
        int row = parentEmptyTile.getRow();
        int col = parentEmptyTile.getColumn();

        if (row > 0) {
            downTransition(parent.getEmpty());

            int movedTile = workState[row][col];
            Position childEmptyTile = new Position(row - 1, col);

            int childManhattan = calculateManhattanDifference(parent.getManhattanH(), goalStatePositions.get(movedTile), childEmptyTile, parentEmptyTile);

            return new Node(parent, Directions.DOWN, parent.getStepsFromStartG() + 1, childManhattan, childEmptyTile);
        }
        else {
            return null;
        }
    }

    // move zero position right
    private Node moveTileLeft(Node parent) {
        Position parentEmptyTile = parent.getEmpty();
        int row = parentEmptyTile.getRow();
        int col = parentEmptyTile.getColumn();

        if (col < boardSize - 1) {
            leftTransition(parent.getEmpty());

            int movedTile = workState[row][col];
            Position childEmptyTile = new Position(row, col + 1);

            int childManhattan = calculateManhattanDifference(parent.getManhattanH(), goalStatePositions.get(movedTile), childEmptyTile, parentEmptyTile);

            return new Node(parent, Directions.LEFT, parent.getStepsFromStartG() + 1, childManhattan, childEmptyTile);
        }
        else {
            return null;
        }
    }

    // move zero position left
    private Node moveTileRight(Node parent) {
        Position parentEmptyTile = parent.getEmpty();
        int row = parentEmptyTile.getRow();
        int col = parentEmptyTile.getColumn();

        if (col > 0) {
            rightTransition(parentEmptyTile);

            int movedTile = workState[row][col];
            Position childEmptyTile = new Position(row, col - 1);

            int childManhattan = calculateManhattanDifference(parent.getManhattanH(), goalStatePositions.get(movedTile), childEmptyTile, parentEmptyTile);

            return new Node(parent, Directions.RIGHT, parent.getStepsFromStartG() + 1, childManhattan, childEmptyTile);
        }
        else {
            return null;
        }
    }

    private int[][] makeCopyState(int[][] state) {
        int[][] copyState = new int[boardSize][boardSize];

        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                copyState[i][j] = state[i][j];
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

    private void upTransition(Position empty) {
        int row = empty.getRow();
        int col = empty.getColumn();

        workState[row][col] = workState[row + 1][col];
        workState[row + 1][col] = 0;
    }

    private void rightTransition(Position empty) {
        int row = empty.getRow();
        int col = empty.getColumn();

        workState[row][col] = workState[row][col - 1];
        workState[row][col - 1] = 0;
    }

    private void leftTransition(Position empty) {
        int row = empty.getRow();
        int col = empty.getColumn();

        workState[row][col] = workState[row][col + 1];
        workState[row][col + 1] = 0;
    }

    private void downTransition(Position empty) {
        int row = empty.getRow();
        int col = empty.getColumn();

        workState[row][col] = workState[row - 1][col];
        workState[row - 1][col] = 0;
    }

    private int calculateManhattanDifference(int manhattan, Position goalPos, Position oldPositionOfTile, Position newPositionOfTile) {
        int oldManhattan = Math.abs(goalPos.getRow() - oldPositionOfTile.getRow()) + Math.abs(goalPos.getColumn() - oldPositionOfTile.getColumn());
        int currentManhattanDistance = Math.abs(goalPos.getRow() - newPositionOfTile.getRow()) + Math.abs(goalPos.getColumn() - newPositionOfTile.getColumn());

        return manhattan - oldManhattan + currentManhattanDistance;
    }

    private void buildSolutionMoves() {
        StringBuilder stringBuilder = new StringBuilder();

        do {
            stringBuilder.append(finalNode.getDirection().getLetter());

            finalNode = finalNode.getParent();
        }
        while (finalNode != null);

        solutionMoves = stringBuilder.reverse().toString();
    }
}
