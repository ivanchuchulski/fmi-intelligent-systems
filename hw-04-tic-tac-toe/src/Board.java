import java.util.Arrays;
import java.util.HashSet;

public class Board {
    private static final int BOARD_SIZE = 3;
    private PlayerSign[][] board;

    private PlayerSign playersTurn;
    private PlayerSign winner;

    private HashSet<Integer> movesAvailable;

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
     * Get player's turn
     *
     * @return playersTurn
     */
    public PlayerSign getPlayersTurn() {
        return playersTurn;
    }

    /**
     * Set player's turn
     *
     * @param playersTurn
     */
    public void setPlayersTurn(PlayerSign playersTurn) {
        this.playersTurn = playersTurn;
    }

    /**
     * Get available moves
     *
     * @return set with available moves
     */
    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    /**
     * Get the winner player
     *
     * @return winner
     */
    public PlayerSign getWinner() {
        return winner;
    }

    /**
     * Make a copy of the board
     *
     * @return the copy of the board
     */
    public Board copy() {
        Board board = new Board();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board.board[i][j] = this.board[i][j];
            }
        }

        board.playersTurn = this.playersTurn;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.movesCount = this.movesCount;
        board.isGameOver = this.isGameOver;

        return board;
    }

    /**
     * Initialize the board with empty spaces
     */
    private void initializeBoard() {
        board = new PlayerSign[BOARD_SIZE][BOARD_SIZE];
        for (PlayerSign[] playerSigns : board) {
            Arrays.fill(playerSigns, PlayerSign.NONE);
        }

        movesAvailable.clear();

        int size = BOARD_SIZE * BOARD_SIZE;

        for (int i = 0; i < size; i++) {
            movesAvailable.add(i);
        }
    }

    /**
     * Map move
     *
     * @param index - position on the board [0-8]
     * @return the mapped row and column on the board
     */
    public boolean move(int index) {
        return makeMove(index / BOARD_SIZE, index % BOARD_SIZE);
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
        if (board[row][col] != PlayerSign.NONE) {
            System.out.println("This place is not empty!");

            return false;
        }

        board[row][col] = playersTurn;
        ++movesCount;
        movesAvailable.remove(row * BOARD_SIZE + col);

        isGameOver = isWinner();

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;

        return true;
    }

    /**
     * Check whether the game is over
     *
     * @return true if there is a winner
     * else return false
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Find whether there is a winner
     *
     * @return true if there is a winner
     * else return false
     */
    private boolean isWinner() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
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
        if (movesCount == BOARD_SIZE * BOARD_SIZE && winner == PlayerSign.NONE) {
            return true;
        }

        /**
         * None
         */
        return false;
    }

    /**
     * Get the symbol of the winner player
     *
     * @param playerSign - the player, who is the winner
     * @return the symbol of the winner player
     */
    public char getPlayerSymbol(PlayerSign playerSign) {
        char result;

        switch (playerSign) {
            case X_PLAYER -> {
                result = PlayerSign.getX();
            }
            case O_PLAYER -> {
                result = PlayerSign.getO();
            }
            case NONE -> {
                result = PlayerSign.getNone();
            }
            default -> throw new IllegalStateException("Unexpected value: " + playerSign);
        }

        return result;
    }

    /**
     * Print the board
     */
    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(getPlayerSymbol(board[i][j]) + " ");
            }

            System.out.println("");
        }

        System.out.println("");
    }
}
