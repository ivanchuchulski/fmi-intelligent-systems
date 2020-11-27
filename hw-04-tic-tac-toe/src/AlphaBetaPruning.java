public class AlphaBetaPruning {
    private final int NEGATIVE_INFINITY;
    private final int POSITIVE_INFINITY;
    private final BoardPositionMapper boardPositionMapper = new BoardPositionMapper(3);

    public AlphaBetaPruning() {
        NEGATIVE_INFINITY = Integer.MIN_VALUE;
        POSITIVE_INFINITY = Integer.MAX_VALUE;
    }


    /**
     * Alpha beta pruning algorithm implementation
     *
     * @param board        - board field
     * @param playerSign   - player's turn
     * @param currentDepth - current depth of tree search
     * @return the score of the winner
     */
    public int start(Board board, PlayerSign playerSign, int currentDepth) {
        if (board.isGameOver()) {
            return score(board, playerSign, currentDepth);
        }

        if (playerSign == board.getPlayersTurn()) {
            return getMax(board, playerSign, NEGATIVE_INFINITY, POSITIVE_INFINITY, currentDepth);
        } else {
            return getMin(board, playerSign, NEGATIVE_INFINITY, POSITIVE_INFINITY, currentDepth);
        }
    }

    /**
     * @param board        - board field
     * @param playerSign   - player's turn
     * @param alpha        - maximizing value
     * @param beta         - minimizing value
     * @param currentDepth - current depth of the search tree
     * @return the the value of alpha
     */
    private int getMax(Board board, PlayerSign playerSign, int alpha, int beta, int currentDepth) {
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.copy();
            modifiedBoard.makeMove(BoardPositionMapper.getRowFromIndex(move),
                    BoardPositionMapper.getColFromIndex(move));

            int score = start(modifiedBoard, playerSign, currentDepth + 1);

            // undo board move optimization?

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = move;
            }

            if (alpha >= beta) {
                break;
            }

        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return alpha;
    }

    /**
     * @param board        - board field
     * @param playerSign   - player's turn
     * @param alpha        - maximizing value
     * @param beta         - minimizing value
     * @param currentDepth - current depth of the search tree
     * @return beta value
     */
    private int getMin(Board board, PlayerSign playerSign, int alpha, int beta, int currentDepth) {
        int indexOfBestMove = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.copy();
            modifiedBoard.move(move);
            int score = start(modifiedBoard, playerSign, currentDepth + 1);

            if (score < beta) {
                beta = score;
                indexOfBestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return beta;
    }

    /**
     * Get the score of the winner
     *
     * @param board        - board field
     * @param playerSign   - player's turn
     * @param currentDepth - current depth of the search tree
     * @return the score of the winner
     */
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
