import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        TicTacToeBot bot = new TicTacToeBot();

        Scanner scanner = new Scanner(System.in);

        System.out.print("select do you want to be first (yes or no) : ");
        boolean humanPlaysFirst = scanner.nextLine().equals("yes");

        System.out.print("select your sign, X or O : ");
        PlayerSign humanSign = scanner.nextLine().equals("X") ? PlayerSign.X_PLAYER : PlayerSign.O_PLAYER;

        if (humanPlaysFirst) {
            board.setPlayersTurn(humanSign);
        } else {
            board.setPlayersTurn(humanSign == PlayerSign.X_PLAYER ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER);

            // the bot makes random first move
//            Random random = new Random();
//            board.makeMove(random.nextInt(3), random.nextInt(3));

            // the optimal move?
            bot.aiMove(board, board.getPlayersTurn(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("best move is : " + bot.getBestMove());
            board.makeMove(bot.getBestMove());
        }


//        test1(board);
//        test2(board);
//        test3(board);

        do {
            board.printBoard();

            bot.aiMove(board, board.getPlayersTurn(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("best move is : " + bot.getBestMove());

            System.out.print("your turn : ");
            String[] userTurn = scanner.nextLine().split(" ");

            int row = Integer.parseInt(userTurn[0]);
            int col = Integer.parseInt(userTurn[1]);

            if (!board.isMoveLegal(row, col)) {
                continue;
            }

            board.makeMove(row, col);

            System.out.println("bot move: ");
            bot.aiMove(board, board.getPlayersTurn(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.makeMove(bot.getBestMove());

        } while (!board.isGameOver());

        printGameResult(board);
    }

    private static void printGameResult(Board board) {
        board.printBoard();

        PlayerSign winner = board.getWinner();
        char symbolFromPlayerSign = PlayerSign.getSymbolFromPlayerSign(winner);

        System.out.println("game over");

        if (symbolFromPlayerSign == PlayerSign.getNone()) {
            System.out.println("it's a draw");
        } else {
            System.out.println(symbolFromPlayerSign + " won");
        }
    }

    private static void test1(Board board) {
        board.makeMove(0, 0);
        board.makeMove(0, 1);

        board.makeMove(0, 2);
        board.makeMove(1, 0);

        board.makeMove(1, 1);
        board.makeMove(1, 2);
    }

    private static void test2(Board board) {
        board.makeMove(0, 0);
        board.makeMove(0, 1);

        board.makeMove(0, 2);
        board.makeMove(1, 0);

        board.makeMove(2, 0);
        board.makeMove(1, 2);
        // put on 2 1
    }

    private static void test3(Board board) {
        board.makeMove(0, 2);
        board.makeMove(0, 0);

        board.makeMove(1, 0);
        board.makeMove(1, 2);

        board.makeMove(1, 1);
        board.makeMove(2, 0);
//         put on 2 1
    }
}
