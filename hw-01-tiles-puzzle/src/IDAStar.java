import java.util.*;

public class IDAStar {
    private Node initialNode;
    private Node finalNode;

    private final int boardSize;
    private int level;

    private int solutionSteps;
    private String solutionMoves;

    private Map<Integer, Position> goalStatePositions;

    final int FOUND = 0;

    public IDAStar(int[][] initialState, int[][] goalState) {
        this.level = 0;
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

        this.initialNode = new Node(initialState, null, "", 0, manhattanSum(initialState));
        this.finalNode = new Node(goalState, null, "", -1, -1);
    }

    public String getMoves() {
        return solutionMoves;
    }

    public int getSteps() {
        return solutionSteps;
    }

    public void printInfo() {
        String moves = getMoves();

        System.out.println("moves : " + moves);
        System.out.println("length " + moves.length());

        long aftermem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("\nMemory used: " + (aftermem / 1024) + "kb");
    }

    public void findSolution_orig() {
        int currentLimit = manhattanSum(initialNode.getState());
        final int FOUND = 0;

        while (true) {
            int smallestLimitOverCurrent = recursiveSearch_orig(initialNode, 0, currentLimit);

            if (smallestLimitOverCurrent == FOUND) {
                break;
            }
            if (smallestLimitOverCurrent == Integer.MAX_VALUE) {
                throw new RuntimeException("unreachable goal");
            }

            currentLimit = smallestLimitOverCurrent;
            level = 0;
        }


        StringBuilder stringBuilder = new StringBuilder();

        solutionSteps = finalNode.getStepsFromStartG();

        while (true) {
            stringBuilder.append(finalNode.getDirection());
            finalNode = finalNode.getParent();

            if (finalNode == null) {
                break;
            }
        }

        solutionMoves = stringBuilder.reverse().toString();

//        solutionSteps = solutionMoves.length();
    }

    private int recursiveSearch_orig(Node node, int stepsToNodeG, int currentFLimit) {
        final int FOUND = 0;
        // slower
//        int f = stepsToNodeG + manhattanSum(node.getState());
//           if (f > currentFLimit) {
//            return f;
//        }
        if (node.getTotalCostF() > currentFLimit) {
            return node.getTotalCostF();
        }

        if (isGoalReached(node)) {
            finalNode = node;
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Node child : getChildNodes(node)) {
            if (node.getParent() != null && node.getParent().equals(child)) {
                continue;
            }

            int temp = recursiveSearch_orig(child, stepsToNodeG + 1, currentFLimit);

            if (temp == FOUND) {
                return FOUND;
            }
            if (temp < minF) {
                minF = temp;
            }
        }

        return minF;
    }

    public void findSolution() {
        int currentLimit = manhattanSum(initialNode.getState());
        ArrayList<Node> path = new ArrayList<>();

        path.add(0, initialNode);

        while (true) {
            int smallestLimitOverCurrent = recursiveSearch(path, 0, currentLimit);

            if (smallestLimitOverCurrent == FOUND) {
                break;
            }
            if (smallestLimitOverCurrent == Integer.MAX_VALUE) {
                throw new RuntimeException("unreachable goal");
            }

            currentLimit = smallestLimitOverCurrent;
            level = 0;
        }


        StringBuilder stringBuilder = new StringBuilder();

        solutionSteps = finalNode.getStepsFromStartG();

        while (true) {
            stringBuilder.append(finalNode.getDirection());
            finalNode = finalNode.getParent();

            if (finalNode == null) {
                break;
            }
        }

        solutionMoves = stringBuilder.reverse().toString();

//        solutionSteps = solutionMoves.length();
    }

    private int recursiveSearch(ArrayList<Node> path, int stepsToNodeG, int currentFLimit) {
        Node node = path.get(path.size() - 1);

        if (node.getTotalCostF() > currentFLimit) {
            return node.getTotalCostF();
        }

        if (isGoalReached(node)) {
            finalNode = node;
            return FOUND;
        }

        int minF = Integer.MAX_VALUE;

        for (Node child : getChildNodes(node)) {
            if (!path.contains(child)) {
                path.add(child);

                int temp = recursiveSearch(path, stepsToNodeG + 1, currentFLimit);

                if (temp == FOUND) {
                    return FOUND;
                }
                if (temp < minF) {
                    minF = temp;
                }

                path.remove(path.size() - 1);
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

        Node up = moveTileUp(empty, node);
        Node down = moveTileDown(empty, node);
        Node left = moveTileLeft(empty, node);
        Node right = moveTileRight(empty, node);

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

            return new Node(childBoard, node, Directions.up, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + 1 + manhattanSum(childBoard));
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
            return new Node(childBoard, node, Directions.down, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + 1 + manhattanSum(childBoard));
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
            return new Node(childBoard, node, Directions.left, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + 1 + manhattanSum(childBoard));
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
            return new Node(childBoard, node, Directions.right, node.getStepsFromStartG() + 1, node.getStepsFromStartG() + 1 + manhattanSum(childBoard));
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
