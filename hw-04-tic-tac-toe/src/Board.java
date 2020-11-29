import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final int sizeOfBoard = 3;
    private final PlayerSign[][] board;

    private PlayerSign playersTurn;
    private PlayerSign winner;

    private final Map<Move, Boolean> tilesAvailable;

    private boolean isGameOver;

    public Board() {
        playersTurn = PlayerSign.NONE;
        winner = PlayerSign.NONE;

        isGameOver = false;

        tilesAvailable = new HashMap<>();

        board = new PlayerSign[sizeOfBoard][sizeOfBoard];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = PlayerSign.NONE;
                tilesAvailable.put(new Move(i, j), true);
            }
        }
    }

    public boolean isMoveLegal(Move move) {
        if (move.getRow() < 0 || move.getRow() > 3 || move.getCol() < 0 || move.getCol() > 3) {
            System.out.println("error : move is out of the board");
            return false;
        }

        if (board[move.getRow()][move.getCol()] != PlayerSign.NONE) {
            System.out.println("error : this tile is not empty");
            return false;
        }

        return true;
    }

    public void makeMove(int row, int col) {
        makeMove(new Move(row, col));
    }

    public void makeMove(Move move) {
        board[move.getRow()][move.getCol()] = playersTurn;
        tilesAvailable.put(move, false);

        isGameOver = winnerFound();

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;
    }

    public void undoMove(Move move) {
        board[move.getRow()][move.getCol()] = PlayerSign.NONE;
        tilesAvailable.put(move, true);

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;
    }

    public void printBoard() {
        for (int i = 0; i < sizeOfBoard; i++) {
            for (int j = 0; j < sizeOfBoard; j++) {
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

    public Map<Move, Boolean> getTilesAvailable() {
        return tilesAvailable;
    }

    public PlayerSign getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean winnerFound() {
        // column winner
        for (int col = 0; col < sizeOfBoard; col++) {
            if (board[0][col] == board[1][col] && board[0][col] == board[2][col] && board[0][col] != PlayerSign.NONE) {
                winner = board[0][col];
                return true;
            }
        }

        // row winner
        for (int row = 0; row < sizeOfBoard; row++) {
            if (board[row][0] == board[row][1] && board[row][0] == board[row][2] && board[row][0] != PlayerSign.NONE) {
                winner = board[row][0];
                return true;
            }
        }

        // main diagonal winner
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != PlayerSign.NONE) {
            winner = board[0][0];
            return true;
        }

        // second diagonal winner
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != PlayerSign.NONE) {
            winner = board[0][2];
            return true;
        }

        for (Move move : tilesAvailable.keySet()) {
            Boolean tileAvailable = tilesAvailable.get(move);
            if (tileAvailable) {
                // there are still empty spaces so no winner yet
                return false;
            }
        }

        // no empty spaces, so game is draw
        winner = PlayerSign.NONE;
        return true;
    }
}
