public enum Vote {
    YES("y"),
    NO("n"),
    QUESTION("?");

    private final String voteSign;

    Vote(String voteSign) {
        this.voteSign = voteSign;
    }

    public String getVoteSign() {
        return voteSign;
    }
}