import java.util.*;

public class IDAStar {
    private Node initialNode;
    private Node finalNode;
    private final int boardSize;
    private int level;

    private int solutionSteps;
    private String solutionMoves;

    private Map<Integer, Position> goalStatePositions;

    public IDAStar(int[][] initialState, int[][] goalState) {
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

    public String getMoves() {
//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (int i = 0; i < level; i++) {
//            stringBuilder.append(finalNode.getDirection());
//            finalNode = finalNode.getParent();
//        }
//
//        return stringBuilder.reverse().toString();

        return solutionMoves;
    }

    public int getSteps() {
        return solutionSteps;
    }

    public void printInfo() {
        String moves = getMoves();

        System.out.println("moves : " + moves);
        System.out.println("length " + moves.length());
        System.out.println("length " + level);
    }

    public void findSolution() {
        int currentLimit = manhattanSum(initialNode.getState());
        int totalCostToCurrentNode = 0;
        final int FOUND = 0;

        while (true) {
            int smallestLimitOverCurrent = recursiveSearch(initialNode, 0, currentLimit);

            if (smallestLimitOverCurrent == FOUND) {
                break;
            }
            if (smallestLimitOverCurrent == Integer.MAX_VALUE) {
                System.out.println("unreachable goal");
                return;
            }

            currentLimit = smallestLimitOverCurrent;
            level = 0;

//            System.out.println(currentLimit);
        }


        StringBuilder stringBuilder = new StringBuilder();

//        for (int i = 0; i < level; i++) {
//            stringBuilder.append(finalNode.getDirection());
//            finalNode = finalNode.getParent();
//        }

        while (true) {
            stringBuilder.append(finalNode.getDirection());
            finalNode = finalNode.getParent();

            if (finalNode == null) {
                break;

            }
        }

        solutionMoves = stringBuilder.reverse().toString();
        solutionSteps = solutionMoves.length();
    }

    private int recursiveSearch(Node node, int stepsToNodeG, int currentFLimit) {
        final int FOUND = 0;
        int f = stepsToNodeG + manhattanSum(node.getState());

//        if (node.getTotalCost() > currentLimit) {
//            return node.getTotalCost();
//        }

        if (f > currentFLimit) {
            return f;
        }

        if (isGoalReached(node)) {
            finalNode = node;
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Node child : getChildNodes(node)) {
//            int temp = recursiveSearch(child, totalCostToCurrentNode + manhattanSum(child.getState()), currentLimit);
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

    private ArrayList<Node> getChildNodes(Node node) {
        Position empty = getZeroPosition(node);
        ++level;
        Node up = moveTileUp(empty, node);
        Node down = moveTileDown(empty, node);
        Node left = moveTileLeft(empty, node);
        Node right = moveTileRight(empty, node);

        // maybe add checks for null
        ArrayList<Node> children = new ArrayList<>();
        if (up != null) {
            children.add(up);
        }

        if (down != null) {
            children.add(down);
        }

        if (left != null) {
            children.add(left);
        }
        if (right != null) {
            children.add(right);
        }

        return children;
    }

    // move zero position down
    private Node moveTileUp(Position empty, Node node) {
        int[][] childBoard = makeCopyState(node.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() < boardSize - 1) {
            childBoard[row][col] = childBoard[row + 1][col];
            childBoard[row + 1][col] = Main.EMPTY_TILE;

            return new Node(childBoard, node, Directions.up, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + manhattanSum(childBoard));
        }
        else {
            return null;
        }
    }


    // move zero position up
    private Node moveTileDown(Position empty, Node node) {
        int[][] childBoard = makeCopyState(node.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (empty.getRow() > 0) {
            childBoard[row][col] = childBoard[row - 1][col];
            childBoard[row - 1][col] = Main.EMPTY_TILE;
            return new Node(childBoard, node, Directions.down, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + manhattanSum(childBoard));
        }
        else {
            return null;
        }

    }

    // move zero position right
    private Node moveTileLeft(Position empty, Node node) {
        int[][] childBoard = makeCopyState(node.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col < boardSize - 1) {
            childBoard[row][col] = childBoard[row][col + 1];
            childBoard[row][col + 1] = 0;
            return new Node(childBoard, node, Directions.left, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + manhattanSum(childBoard));
        }
        else {
            return null;
        }

    }

    // move zero position left
    private Node moveTileRight(Position empty, Node node) {
        int[][] childBoard = makeCopyState(node.getState());
        int row = empty.getRow();
        int col = empty.getColumn();

        if (col > 0) {
            childBoard[row][col] = childBoard[row][col - 1];
            childBoard[row][col - 1] = 0;
            return new Node(childBoard, node, Directions.right, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + manhattanSum(childBoard));
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

    private Position getZeroPosition(Node node) {
        Position position = new Position();

        int[][] current = node.getState();

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