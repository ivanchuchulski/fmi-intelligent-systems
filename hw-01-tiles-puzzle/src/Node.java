public class Node {
    private final Node parent;
    private final Directions direction;
    private final int stepsFromStartG; // the path cost of reaching this node so far node
    private final int manhattanH;
    private final int totalCostF;
    private final Position empty;

    public Node(Node parent, Directions direction, int stepsFromStartG, int manhattanH, Position empty) {
        this.parent = parent;
        this.direction = direction;

        this.stepsFromStartG = stepsFromStartG;
        this.manhattanH = manhattanH;

        this.totalCostF = stepsFromStartG + manhattanH;
        this.empty = empty;
    }

    public Node getParent() {
        return parent;
    }

    public int getTotalCostF() {
        return totalCostF;
    }

    public Directions getDirection() {
        return direction;
    }

    public int getStepsFromStartG() {
        return stepsFromStartG;
    }

    public int getManhattanH() {
        return manhattanH;
    }

    public Position getEmpty() {
        return empty;
    }
}
