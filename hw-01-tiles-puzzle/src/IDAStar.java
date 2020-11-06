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
    Directions[] directions = {Directions.UP, Directions.RIGHT, Directions.LEFT, Directions.DOWN};

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

        this.initialNode = new Node(initialState, null, Directions.NONE, 0, initialManh, initialEmpty);
        this.finalNode = new Node(goalState, null, Directions.NONE, -1, -1, new Position(-1, -1));

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

        if (isGoalReached(node)) {
            finalNode = node;
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Directions direction : directions) {
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

    private void undoDirection(Directions direction, Node child) {
        Position empty = child.getEmpty();
        int row = empty.getRow();
        int col = empty.getColumn();

        switch (direction) {
            case UP -> {
                workState[row][col] = workState[row - 1][col];
                workState[row - 1][col] = 0;
            }
            case DOWN -> {
                workState[row][col] = workState[row + 1][col];
                workState[row + 1][col] = 0;
            }
            case RIGHT -> {
                workState[row][col] = workState[row][col + 1];
                workState[row][col + 1] = 0;
            }
            case LEFT -> {
                workState[row][col] = workState[row][col - 1];
                workState[row][col - 1] = 0;
            }
        }
    }

    private Node goDirection(Directions direction, Node node) {
        Node result = null;

        switch (direction) {
            case UP -> {
                result = moveTileUp(node);
            }
            case DOWN -> {
                result = moveTileDown(node);
            }
            case RIGHT -> {
                result = moveTileRight(node);
            }
            case LEFT -> {
                result = moveTileLeft(node);
            }
        }
        ;

        return result;
    }

    private boolean isGoalReached(Node node) {
        int[][] current = workState;
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
            workState[row][col] = workState[row + 1][col];
            workState[row + 1][col] = 0;

            Position emptyInChild = new Position(row + 1, col);

            int movedTile = workState[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row + 1, col);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(null, node, Directions.UP, node.getStepsFromStartG() + 1, childManh, emptyInChild);
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
            workState[row][col] = workState[row - 1][col];
            workState[row - 1][col] = 0;

            Position emptyInChild = new Position(row - 1, col);

            int movedTile = workState[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row - 1, col);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(null, node, Directions.DOWN, node.getStepsFromStartG() + 1, childManh, emptyInChild);
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
            workState[row][col] = workState[row][col + 1];
            workState[row][col + 1] = 0;

            Position emptyInChild = new Position(row, col + 1);

            int movedTile = workState[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row, col + 1);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(null, node, Directions.LEFT, node.getStepsFromStartG() + 1, childManh, emptyInChild);
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
            workState[row][col] = workState[row][col - 1];
            workState[row][col - 1] = 0;

            Position emptyInChild = new Position(row, col - 1);

            int movedTile = workState[row][col];
            Position goalPos = goalStatePositions.get(movedTile);
            Position currPos = new Position(row, col);
            Position prevPos = new Position(row, col - 1);

            int prevMahn = Math.abs(goalPos.getRow() - prevPos.getRow()) + Math.abs(goalPos.getColumn() - prevPos.getColumn());
            int currManh = Math.abs(goalPos.getRow() - currPos.getRow()) + Math.abs(goalPos.getColumn() - currPos.getColumn());

            int childManh = node.getManhattanH() - prevMahn + currManh;

            return new Node(null, node, Directions.RIGHT, node.getStepsFromStartG() + 1, childManh, emptyInChild);
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
