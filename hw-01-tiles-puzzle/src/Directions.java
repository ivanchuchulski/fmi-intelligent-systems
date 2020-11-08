public enum Directions {
    NONE(""),
    UP("up"),
    DOWN("down"),
    RIGHT("right"),
    LEFT("left");

    private final String letter;

    Directions(String letter) {
        this.letter = letter;
    }

    String getFullDirectionText() {
        return this.letter;
    }

    char getLetter() {
        return this.letter.charAt(0);
    }
}
