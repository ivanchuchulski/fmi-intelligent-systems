import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        AlphaBetaPruning bot = new AlphaBetaPruning();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Select player (X or O): ");
        String player = scanner.nextLine();

        PlayerSign human = player.equals("X") ? PlayerSign.X_PLAYER : PlayerSign.O_PLAYER;
        board.setPlayersTurn(human);

        System.out.println("");

        System.out.print("Select who is first (X or O): ");
        String first = scanner.nextLine();

        int currentDepth = 0;

        if (!first.equals(player)) {
            board.setPlayersTurn(human == PlayerSign.X_PLAYER ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER);

            // this could be changed to be the optimal move
            board.makeMove(random.nextInt(3), random.nextInt(3));

            currentDepth++;
        }

        do {
            boolean moveMade = false;

            while (!moveMade) {
                board.printBoard();

                System.out.print("Your turn: ");
                String[] userTurn = scanner.nextLine().split(" ");

                moveMade = board.makeMove(Integer.parseInt(userTurn[0]), Integer.parseInt(userTurn[1]));
            }

            currentDepth++;
            System.out.println("Opponent turn: ");
            bot.start(board, board.getPlayersTurn(), currentDepth);

        } while (!board.isGameOver());

        board.printBoard();

        if (board.getPlayerSymbol(board.getWinner()) == PlayerSign.getNone()) {
            System.out.println("It's a draw!");
        } else {
            System.out.printf("%s is the winner! %n", board.getPlayerSymbol(board.getWinner()));
        }
    }

}
