import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    private static final int sizeOfBoard = 3;
    private PlayerSign[][] board;

    private PlayerSign playersTurn;
    private PlayerSign winner;

    private Set<Integer> movesAvailable;
    private Map<Move, Boolean> tilesAvailable;

    private int movesCount;
    private boolean isGameOver;

    public Board() {
        playersTurn = PlayerSign.NONE;
        winner = PlayerSign.NONE;

        movesCount = 0;
        isGameOver = false;

        movesAvailable = new HashSet<>();
        tilesAvailable = new HashMap<>();

        initializeBoard();
    }

    public boolean makeMove(int row, int col) {
        board[row][col] = playersTurn;
        ++movesCount;

        movesAvailable.remove(row * sizeOfBoard + col);
        tilesAvailable.put(new Move(row, col), false);

        isGameOver = isWinner();

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;

        return true;
    }


    public void makeMove(Move move) {
        board[move.getRow()][move.getCol()] = playersTurn;
        ++movesCount;
        tilesAvailable.put(move, false);

        isGameOver = isWinner();

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;
    }

    public void undoMove(Move move) {
        board[move.getRow()][move.getCol()] = PlayerSign.NONE;
        --movesCount;
        tilesAvailable.put(move, true);

        playersTurn = (PlayerSign.X_PLAYER == playersTurn) ? PlayerSign.O_PLAYER : PlayerSign.X_PLAYER;
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

    public Set<Integer> getAvailableMoves() {
        return movesAvailable;
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

    public Board copyBoard() {
        Board copy = new Board();

        for (int i = 0; i < sizeOfBoard; i++) {
            System.arraycopy(this.board[i], 0, copy.board[i], 0, sizeOfBoard);
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
        board = new PlayerSign[sizeOfBoard][sizeOfBoard];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = PlayerSign.NONE;
                tilesAvailable.put(new Move(i, j), true);
            }
        }

        movesAvailable.clear();

        for (int square = 0; square < sizeOfBoard * sizeOfBoard; square++) {
            movesAvailable.add(square);
        }
    }

    public boolean isWinner() {
        for (int i = 0; i < sizeOfBoard; i++) {
            // column winner
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != PlayerSign.NONE) {
                winner = board[0][i];
                return true;
            }

            // row winner
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != PlayerSign.NONE) {
                winner = board[i][0];
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
            Boolean moveAvailable = tilesAvailable.get(move);
            if (moveAvailable) {
                return false;
            }
        }

        winner = PlayerSign.NONE;
        return true;

//        // draw
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board.length; j++) {
//                if (board[i][j] == PlayerSign.NONE) {
//                    // no winner yet
//                    return false;
//                }
//            }
//        }
//
//        // the result is draw
//        return true;

//        if (movesCount == sizeOfBoard * sizeOfBoard && winner == PlayerSign.NONE) {
//            return true;
//        }
//
//        // no winner yet
//        return false;
    }


}
