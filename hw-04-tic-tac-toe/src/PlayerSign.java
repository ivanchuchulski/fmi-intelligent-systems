public enum PlayerSign {
    X_PLAYER('X'),
    O_PLAYER('O'),
    NONE('_');

    private final char symbol;

    PlayerSign(char symbol) {
        this.symbol = symbol;
    }

    public static char getX(){
        return 'X';
    }

    public static char getO(){
        return 'O';
    }

    public static char getNone(){
        return '_';
    }

    public char getSymbol() {
        return this.symbol;
    }

    public static char getSymbolFromPlayerSign(PlayerSign playerSign) {
        return playerSign.getSymbol();
    }
}
