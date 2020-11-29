import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicTacToeBot {
    private final int NOT_SET = -1;

    private List<NodeEvaluation> rootChildrenScore = new ArrayList<>();

    public TicTacToeBot() {
    }

    public Move getBestMove() {
        int maxEval = Integer.MIN_VALUE;
        Move result = null;
        int bestIndex = -1;

        for (NodeEvaluation nodeEvaluation : rootChildrenScore) {
            if (nodeEvaluation.getEvaluation() > maxEval) {
                maxEval = nodeEvaluation.getEvaluation();
                result = nodeEvaluation.getMove();
            }
        }

        return result;
    }

    public int botMove(Board board, PlayerSign playerSign, int currentDepth, int alpha, int beta) {
        if (board.isGameOver()) {
            return evaluatePosition(board, playerSign, currentDepth);
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

    public int aiMove(Board board, PlayerSign playerSign, int currentDepth, int alpha, int beta) {
        if (board.isWinner()) {
            return evaluatePosition(board, playerSign, currentDepth);
        }

        if (currentDepth == 0) {
            rootChildrenScore.clear();
        }

        if (playerSign == board.getPlayersTurn()) {
            int score = Integer.MIN_VALUE;

            for (Move move : board.getTilesAvailable().keySet()) {
                if (!board.getTilesAvailable().get(move)) {
                    continue;
                }

                board.makeMove(move);

                score = Math.max(score, aiMove(board, playerSign, currentDepth + 1, alpha, beta));
                alpha = Math.max(alpha, score);

                board.undoMove(move);

                if (currentDepth == 0) {
                    rootChildrenScore.add(new NodeEvaluation(move, score));
                }

                if (alpha >= beta) {
                    break;
                }
            }
            return score;

        } else {
            int score = Integer.MAX_VALUE;
            for (Move move : board.getTilesAvailable().keySet()) {
                if (!board.getTilesAvailable().get(move)) {
                    continue;
                }

                board.makeMove(move);

                score = Math.min(score, aiMove(board, playerSign, currentDepth + 1, alpha, beta));
                beta = Math.min(beta, score);

                board.undoMove(move);

                if (alpha >= beta) {
                    break;
                }
            }

            return score;
        }
    }

    private int evaluatePosition(Board board, PlayerSign playerSign, int currentDepth) {
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
