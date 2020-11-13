import java.util.*;

public class IDAStar {
    final int FOUND = 0;

    private final Node initialNode;
    private Node finalNode;

    private final int boardSize;

    private int numberOfStepsToSolution;
    private String solutionMoves;
    private final StringBuilder movesBuilder;
    private final Map<Integer, Position> goalStatePositions;

    private final int[][] workState;
    private final int[][] goalState;
    private final Directions[] DIRECTIONS = {Directions.UP, Directions.LEFT, Directions.RIGHT, Directions.DOWN};

    private final ArrayList<String> moves;

    public IDAStar(int[][] initialState, int[][] goalState) {
        this.boardSize = initialState.length;
        this.goalStatePositions = new HashMap<>();
        precomputeGoalStatePositions(goalState);

        this.initialNode = new Node(null, Directions.NONE, 0, manhattanSum(initialState), findEmptyPosition(initialState));
        this.workState = initialState;
        this.goalState = goalState;
        this.movesBuilder = new StringBuilder();
        this.moves = new ArrayList<>();
    }

    public String getMovesAsLetters() {
        return solutionMoves;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public int getNumberOfStepsToSolution() {
        return numberOfStepsToSolution;
    }

    public void printSolutionInformation() {
//        System.out.println("solution found : ");
//        System.out.print("#steps : ");
        System.out.println(getNumberOfStepsToSolution());

//        System.out.println("moves : ");
        for (String move : moves) {
            System.out.println(move);
        }

//        System.out.println("moves : " + getMovesAsLetters());
    }

    public void findSolution() {
        int currentLimit = initialNode.getManhattanH();

        while (true) {
            int smallestLimitOverCurrent = recursiveSearch(initialNode, currentLimit);

            if (smallestLimitOverCurrent == FOUND) {
                break;
            }
            if (smallestLimitOverCurrent == Integer.MAX_VALUE) {
                throw new RuntimeException("unreachable goal");
            }

            currentLimit = smallestLimitOverCurrent;
        }

        solutionMoves = movesBuilder.reverse().toString();
        Collections.reverse(moves);
    }

    private int recursiveSearch(Node node, int currentFLimit) {
        if (node.getTotalCostF() > currentFLimit) {
            return node.getTotalCostF();
        }

        if (node.getManhattanH() == 0) {
            numberOfStepsToSolution = node.getStepsFromStartG();
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Directions direction : DIRECTIONS) {
            if (checkIfTransitionWillGoToGrandparent(node, direction)) {
                continue;
            }

            Node child = goToDirection(direction, node);

            if (child == null) {
                continue;
            }

            int temp = recursiveSearch(child, currentFLimit);

            if (temp == FOUND) {
                // task requirement
                moves.add(direction.getFullDirectionText());

                // for tests
                movesBuilder.append(Character.toUpperCase(direction.getLetter()));

                return FOUND;
            }
            if (temp < minF) {
                minF = temp;
            }

            undoDirection(direction, child);
        }

        return minF;
    }

    private boolean checkIfTransitionWillGoToGrandparent(Node parent, Directions directionToGo) {
        if (parent.getParent() != null) {
            Directions parentDirection = parent.getDirection();

            if (parentDirection.equals(Directions.UP) && directionToGo.equals(Directions.DOWN)) {
                return true;
            }
            if (parentDirection.equals(Directions.DOWN) && directionToGo.equals(Directions.UP)) {
                return true;
            }
            if (parentDirection.equals(Directions.RIGHT) && directionToGo.equals(Directions.LEFT)) {
                return true;
            }
            if (parentDirection.equals(Directions.LEFT) && directionToGo.equals(Directions.RIGHT)) {
                return true;
            }
        }
        return false;
    }

    private Node goToDirection(Directions direction, Node parent) {
        Node result = null;

        switch (direction) {
            case UP:
                result = moveTileUp(parent);
                break;
            case DOWN:
                result = moveTileDown(parent);
                break;
            case RIGHT:
                result = moveTileRight(parent);
                break;
            case LEFT:
                result = moveTileLeft(parent);
                break;
        }

        return result;
    }

    private void undoDirection(Directions direction, Node child) {
        Position empty = child.getEmpty();

        switch (direction) {
            case UP:
                downTransition(empty);
                break;
            case DOWN:
                upTransition(empty);
                break;
            case RIGHT:
                leftTransition(empty);
                break;
            case LEFT:
                rightTransition(empty);
                break;
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

    private void precomputeGoalStatePositions(int[][] goalState) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (goalState[row][col] == Main.EMPTY_TILE) {
                    continue;
                }
                goalStatePositions.put(goalState[row][col], new Position(row, col));
            }
        }
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
            stringBuilder.append(finalNode.getDirection().getFullDirectionText());

            finalNode = finalNode.getParent();
        }
        while (finalNode != null);

        solutionMoves = stringBuilder.reverse().toString();
    }
}
