import java.util.*;

public class Main {
    static final int TRAP = 0;
    static final int FREE = 1;
    static final int VISITED = 6;
    static final int PATH = 7;

    public static void main(String[] args) {
        int mazeDimension;
        int numberOfEmpty;
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter dimension : ");
        mazeDimension = scanner.nextInt();

        int[][] matrix = creteMatrix(mazeDimension);

        inputMatrix(mazeDimension, scanner, matrix);

        printMatrix(mazeDimension, matrix);

        Stack<Point> path = bfs(matrix);

        System.out.println("path len: " + path.size());
        System.out.println("path : ");

        while (!path.isEmpty()) {
            Point point = path.pop();

            matrix[point.getX()][point.getY()] = PATH;
        }


        printMatrix(mazeDimension, matrix);

    }

    private static int[][] creteMatrix(int mazeDimension) {
        int[][] matrix = new int[mazeDimension][];
        for (int i = 0; i < mazeDimension; i++) {
            matrix[i] = new int[mazeDimension];
        }
        return matrix;
    }

    private static void inputMatrix(int mazeDimension, Scanner scanner, int[][] matrix) {
        System.out.println("enter matrix");

        for (int i = 0; i < mazeDimension; i++) {
            for (int j = 0; j < mazeDimension; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

    private static void printMatrix(int mazeDimension, int[][] matrix) {
        for (int i = 0; i < mazeDimension; i++) {
            for (int j = 0; j < mazeDimension; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static Stack<Point> bfs(int[][] matrix) {
        Point start = new Point(0, 0, null);
        Point end = new Point(matrix.length - 1, matrix.length - 1, null);


        if (isTrapPoint(matrix, start)) {
            System.out.println("can't enter the maze");
            return new Stack<>();
        }

        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
//                matrix[end.getX()][end.getY()] = VISITED;
                end.setParent(current);
                break;
            }

            visited.add(current);
//            matrix[current.getX()][current.getY()] = VISITED;

            ArrayList<Point> neighbours = getNeighbours(matrix, current);

            for (Point neighbour : neighbours) {
                if (!visited.contains(neighbour) && !isTrapPoint(matrix, neighbour)) {
                    queue.add(neighbour);
                }
            }
        }

        return recreateRoute(end);
    }

    private static Stack<Point> recreateRoute(Point current) {
        Stack<Point> path = new Stack<>();

        do {
            path.add(current);
            current = current.getParent();
        }
        while (current.getParent() != null);

        // add the starting node, his parent is null
        path.add(current);

        return path;
    }

    static ArrayList<Point> getNeighbours(int[][] matrix, Point point) {
        ArrayList<Point> neighbours = new ArrayList<>();

        Point left = new Point(point.getX(), point.getY() - 1, point);
        Point right = new Point(point.getX(), point.getY() + 1, point);
        Point upper = new Point(point.getX() - 1, point.getY(), point);
        Point lower = new Point(point.getX() + 1, point.getY(), point);

        if (isValidPoint(matrix, left)) {
            neighbours.add(left);
        }
        if (isValidPoint(matrix, right)) {
            neighbours.add(right);
        }
        if (isValidPoint(matrix, upper)) {
            neighbours.add(upper);
        }
        if (isValidPoint(matrix, lower)) {
            neighbours.add(lower);
        }

        return neighbours;
    }

    static boolean isValidPoint(int[][] matrix, Point point) {
        return point.getX() >= 0 && point.getX() < matrix.length && point.getY() >= 0 && point.getY() < matrix.length;
    }

    static boolean isTrapPoint(int[][] matrix, Point point) {
        return matrix[point.getX()][point.getY()] == TRAP;
    }

}

