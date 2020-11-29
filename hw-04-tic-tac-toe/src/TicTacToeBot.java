import java.util.ArrayList;
import java.util.List;

public class TicTacToeBot {
    private final int NEGATIVE_INFINITY;
    private final int POSITIVE_INFINITY;
    private final int NOT_SET = -1;

    private List<NodeEvaluation> rootChildrenScore = new ArrayList<>();

    public TicTacToeBot() {
        NEGATIVE_INFINITY = Integer.MIN_VALUE;
        POSITIVE_INFINITY = Integer.MAX_VALUE;
    }

    public int botMove(Board board, PlayerSign playerSign, int currentDepth, int alpha, int beta) {
        if (board.isGameOver()) {
            return score(board, playerSign, currentDepth);
        }

        int indexOfBestMove = NOT_SET;
        if (playerSign == board.getPlayersTurn()) {

            for (Integer move : board.getAvailableMoves()) {
                Board modifiedBoard = board.copyBoard();

                int row = move / 3;
                int col = move % 3;

                modifiedBoard.makeMove(row, col);

                int score = botMove(modifiedBoard, playerSign, currentDepth + 1, alpha, beta);

                if (score > alpha) {
                    alpha = score;
                    indexOfBestMove = move;
                }

                if (alpha >= beta) {
                    break;
                }
            }

            if (indexOfBestMove != NOT_SET) {
                int bestRow = indexOfBestMove / 3;
                int bestCol = indexOfBestMove % 3;

                board.makeMove(bestRow, bestCol);
            }

            return alpha;
        } else {

            for (Integer move : board.getAvailableMoves()) {
                Board modifiedBoard = board.copyBoard();

                int row = move / 3;
                int col = move % 3;

                modifiedBoard.makeMove(row, col);

                int score = botMove(modifiedBoard, playerSign, currentDepth + 1, alpha, beta);

                if (score < beta) {
                    beta = score;
                    indexOfBestMove = move;
                }

                if (alpha >= beta) {
                    break;
                }
            }

            if (indexOfBestMove != NOT_SET) {
                int bestRow = indexOfBestMove / 3;
                int bestCol = indexOfBestMove % 3;

                board.makeMove(bestRow, bestCol);
            }

            return beta;
        }
    }


    private int score(Board board, PlayerSign playerSign, int currentDepth) {
        PlayerSign opponent = (playerSign == PlayerSign.X_PLAYER) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;

        if (board.getWinner() == playerSign) {
            return 10 - currentDepth;
        } else if (board.getWinner() == opponent) {
            return -10 + currentDepth;
        } else {
            return 0;
        }
    }
}
