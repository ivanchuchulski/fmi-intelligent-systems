public enum Directions {
    NONE(""),
    UP("Up"),
    DOWN("Down"),
    RIGHT("Right"),
    LEFT("Left");

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
