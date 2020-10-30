import java.util.Arrays;
import java.util.Comparator;

public class Node {
    private int[][] state;
    private Node parent;
    private int totalCost;
    private String direction;
    private int path; // the path cost of reaching this node so far node

    public Node(int[][] state, Node parent, int totalCost, String direction, int path) {
        setState(state);
        setParent(parent);
        setTotalCost(totalCost);
        setDirection(direction);
        setPath(path);
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public int[][] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public String getDirection() {
        return direction;
    }

    public int getPath() {
        return path;
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

        if (!Arrays.deepEquals(this.state, other.state)) {
            return false;
        }

        return true;
    }

    public static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node left, Node right) {
            return left.getTotalCost() - right.getTotalCost();
        }

    }


}
