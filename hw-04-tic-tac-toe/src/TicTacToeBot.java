import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicTacToeBot {
    private final List<NodeEvaluation> rootChildrenScore;

    public TicTacToeBot() {
        rootChildrenScore = new ArrayList<>();
    }

    public int calculateBestMove(Board board, PlayerSign playerSign, int currentDepth, int alpha, int beta) {
        if (board.winnerFound()) {
            return evaluatePositionByDepth(board, playerSign, currentDepth);
//            return evaluateByEmptySpaces(board, playerSign);
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

                score = Math.max(score, calculateBestMove(board, playerSign, currentDepth + 1, alpha, beta));
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

                score = Math.min(score, calculateBestMove(board, playerSign, currentDepth + 1, alpha, beta));
                beta = Math.min(beta, score);

                board.undoMove(move);

                if (alpha >= beta) {
                    break;
                }
            }

            return score;
        }
    }

    public Move getBestMove() {
        int maxEval = Integer.MIN_VALUE;
        Move result = null;

        for (NodeEvaluation nodeEvaluation : rootChildrenScore) {
            if (nodeEvaluation.getEvaluation() > maxEval) {
                maxEval = nodeEvaluation.getEvaluation();
                result = nodeEvaluation.getMove();
            }
        }

        return result;
    }

    private int evaluatePositionByDepth(Board board, PlayerSign playerSign, int currentDepth) {
        PlayerSign opponent = (playerSign == PlayerSign.X_PLAYER) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;
        PlayerSign winner = board.getWinner();

        if (winner == playerSign) {
            return 10 - currentDepth;
        } else if (winner == opponent) {
            return -10 + currentDepth;
        } else {
            return 0;
        }
    }

    private int evaluateByEmptySpaces(Board board, PlayerSign playerSign) {
        PlayerSign opponent = (playerSign == PlayerSign.X_PLAYER) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;
        PlayerSign winner = board.getWinner();
        int numberOfEmptyTiles = 0;

        Map<Move, Boolean> tilesAvailable = board.getTilesAvailable();

        for (Map.Entry<Move, Boolean> moveBooleanEntry : tilesAvailable.entrySet()) {
            if (moveBooleanEntry.getValue()) {
                numberOfEmptyTiles++;
            }
        }

        if (winner == playerSign) {
            return 1 + numberOfEmptyTiles;
        } else if (winner == opponent) {
            return -1 - numberOfEmptyTiles;
        } else {
            return 0;
        }
    }
}
