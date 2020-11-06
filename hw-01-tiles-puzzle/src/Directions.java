public enum Directions {
    NONE(""),
    UP("U"),
    DOWN("D"),
    RIGHT("R"),
    LEFT("L");

    private final String letter;

    Directions(String letter) {
        this.letter = letter;
    }

    String getLetter() {
        return this.letter;
    }
}
