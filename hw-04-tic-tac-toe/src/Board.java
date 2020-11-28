import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board {
    private static final int boardSize = 3;
    private PlayerSign[][] board;

    private PlayerSign playersTurn;
    private PlayerSign winner;

    private Set<Integer> movesAvailable;

    private int movesCount;
    private boolean isGameOver;

    public Board() {
        playersTurn = PlayerSign.NONE;
        winner = PlayerSign.NONE;

        movesCount = 0;
        isGameOver = false;

        movesAvailable = new HashSet<>();

        initializeBoard();
    }

    /**
     * Map move
     *
     * @param index - position on the board [0-8]
     * @return the mapped row and column on the board
     */
    public boolean move(int index) {
        return makeMove(index / boardSize, index % boardSize);
    }

    /**
     * Make player's move
     *
     * @param row - the index of the row to make a move
     * @param col - the index of the column to make a move
     * @return true if a move is made
     * else return false
     */
    public boolean makeMove(int row, int col) {
        board[row][col] = playersTurn;
        ++movesCount;
        movesAvailable.remove(row * boardSize + col);

        isGameOver = isWinner();

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;

        return true;
    }

    public boolean isMoveLegal(int row, int col) {
        if (row < 0 || row > 3 || col < 0 || col > 3) {
            System.out.println("error : moves is out of the board");
            return false;
        }

        if (board[row][col] != PlayerSign.NONE) {
            System.out.println("error : this tile is not empty");

            return false;
        }

        return true;
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(PlayerSign.getSymbolFromPlayerSign(board[i][j]) + " ");
            }

            System.out.println();
        }

        System.out.println();
    }


    public PlayerSign getPlayersTurn() {
        return playersTurn;
    }

    public void setPlayersTurn(PlayerSign playersTurn) {
        this.playersTurn = playersTurn;
    }

    public Set<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    public PlayerSign getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Board copyBoard() {
        Board copy = new Board();

        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(this.board[i], 0, copy.board[i], 0, boardSize);
        }

        copy.playersTurn = this.playersTurn;
        copy.winner = this.winner;

        copy.movesAvailable = new HashSet<>();
        copy.movesAvailable.addAll(this.movesAvailable);

        copy.movesCount = this.movesCount;
        copy.isGameOver = this.isGameOver;

        return copy;
    }



    private void initializeBoard() {
        board = new PlayerSign[boardSize][boardSize];
        for (PlayerSign[] playerSigns : board) {
            Arrays.fill(playerSigns, PlayerSign.NONE);
        }

        movesAvailable.clear();

        for (int square = 0; square < boardSize * boardSize; square++) {
            movesAvailable.add(square);
        }
    }

    /**
     * Find whether there is a winner
     *
     * @return true if there is a winner
     * else return false
     */
    private boolean isWinner() {
        for (int i = 0; i < boardSize; ++i) {
            /**
             * Column winner
             */
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != PlayerSign.NONE) {
                winner = board[0][i];
                return true;
            }

            /**
             * Row winner
             */
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != PlayerSign.NONE) {
                winner = board[i][0];
                return true;
            }
        }

        /**
         * Main diagonal winner
         */
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != PlayerSign.NONE) {
            winner = board[0][0];
            return true;
        }

        /**
         * Second diagonal winner
         */
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != PlayerSign.NONE) {
            winner = board[0][2];
            return true;
        }

        /**
         * Draw
         */
        if (movesCount == boardSize * boardSize && winner == PlayerSign.NONE) {
            return true;
        }

        /**
         * None
         */
        return false;
    }


}
