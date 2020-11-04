import java.util.Arrays;
import java.util.Comparator;

public class Node {
    private int[][] state;
    private Node parent;
    private String direction;
    private int stepsFromStartG; // the path cost of reaching this node so far node
    private int manhattanH;
    private int totalCostF;
    private Position empty;

    public Node(int[][] state, Node parent, String direction, int stepsFromStartG, int totalCostF, int manhattanH, Position empty) {
        setState(state);
        setParent(parent);
        setDirection(direction);
        setStepsFromStartG(stepsFromStartG);
        setTotalCostF(totalCostF);
        this.manhattanH = manhattanH;
        this.empty = empty;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setTotalCostF(int totalCostF) {
        this.totalCostF = totalCostF;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setStepsFromStartG(int stepsFromStartG) {
        this.stepsFromStartG = stepsFromStartG;
    }

    public int[][] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public int getTotalCostF() {
        return totalCostF;
    }

    public String getDirection() {
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

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;

        return Arrays.deepEquals(this.state, other.state);
    }

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node left, Node right) {
            return left.getTotalCostF() - right.getTotalCostF();
        }

    }

}
