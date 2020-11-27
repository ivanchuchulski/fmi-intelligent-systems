public class BoardPositionMapper {
    private static int boardSize;

    public BoardPositionMapper(int boardSize) {
        BoardPositionMapper.boardSize = boardSize;
    }

    public static int getRowFromIndex(int move) {
        return move / boardSize;
    }

    public static int getColFromIndex(int move) {
        return move % boardSize;
    }
}