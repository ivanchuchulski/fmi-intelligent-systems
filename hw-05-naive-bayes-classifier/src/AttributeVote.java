public class AttributeVote {
    private int yesVotes;
    private int noVotes;
    private int questionVotes;

    public AttributeVote(int yesVotes, int noVotes, int questionVotes) {
        this.yesVotes = yesVotes;
        this.noVotes = noVotes;
        this.questionVotes = questionVotes;
    }

    public void incrementVote(String vote) {
        switch (vote) {
            case "y" -> yesVotes++;
            case "n" -> noVotes++;
            case "?" -> questionVotes++;
            default -> throw new IllegalStateException("Unexpected value: " + vote);
        }
    }

    public int getVoteCount(String vote) {
        return switch (vote) {
            case "y" -> yesVotes;
            case "n" -> noVotes;
            case "?" -> questionVotes;
            default -> throw new IllegalStateException("Unexpected value: " + vote);
        };
    }

    public int getAllVotesNumber() {
        return yesVotes + noVotes + questionVotes;
    }
}
