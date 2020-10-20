import java.util.Objects;

public class Point {
    private int x;
    private int y;
    private Point parent;

    public Point(int x, int y, Point par) {
        this.x = x;
        this.y = y;
        parent = par;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setParent(Point parent) {
        this.parent = parent;
    }

    public Point getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
