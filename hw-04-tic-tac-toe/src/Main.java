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

//            botRandomMove(board);
            botBestMove(board, bot);
        }

//        test1(board);
//        test2(board);

        do {
            board.printBoard();

            System.out.println("your turn : ");

            bot.calculateBestMove(board, board.getPlayersTurn(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            System.out.println("best move is : " + bot.getBestMove());

            do {
                System.out.print("move : ");
                String[] userTurn = scanner.nextLine().split(" ");
                Move move = new Move(Integer.parseInt(userTurn[0]), Integer.parseInt(userTurn[1]));

                if (board.isMoveLegal(move)) {
                    board.makeMove(move);
                    break;
                }
            } while (true);

            botBestMove(board, bot);

        } while (!board.isGameOver());

        printGameResult(board);
    }

    private static void botRandomMove(Board board) {
        Random random = new Random();
        board.makeMove(random.nextInt(3), random.nextInt(3));
    }

    private static void botBestMove(Board board, TicTacToeBot bot) {
        System.out.println();
        System.out.println("bot move: ");

        bot.calculateBestMove(board, board.getPlayersTurn(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Move bestMove = bot.getBestMove();

        System.out.println("best move is : " + bestMove);
        board.makeMove(bestMove);
    }

    private static void printGameResult(Board board) {
        board.printBoard();

        PlayerSign winner = board.getWinner();

        System.out.println("game over");

        if (winner == PlayerSign.NONE) {
            System.out.println("it's a draw");
        } else {
            System.out.println(PlayerSign.getSymbolFromPlayerSign(winner) + " won");
        }
    }

    private static void test1(Board board) {
        board.makeMove(0, 0);
        board.makeMove(1, 1);

        board.makeMove(1, 0);
        board.makeMove(2, 0);

//        put on 0 1 and bot wins
//        best is 0 2 to block
    }

    private static void test2(Board board) {
        board.makeMove(0, 2);
        board.makeMove(0, 0);

        board.makeMove(1, 0);
        board.makeMove(1, 2);

        board.makeMove(1, 1);
        board.makeMove(2, 0);

//        put on 2 1 and bot should block on 0 1
    }
}
