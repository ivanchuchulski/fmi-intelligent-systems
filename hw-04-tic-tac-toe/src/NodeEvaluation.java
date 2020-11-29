import java.util.Objects;

public class NodeEvaluation {
    private Move move;
    private int evaluation;

    public NodeEvaluation(Move move, int evaluation) {
        this.move = move;
        this.evaluation = evaluation;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeEvaluation that = (NodeEvaluation) o;
        return evaluation == that.evaluation &&
                Objects.equals(move, that.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move, evaluation);
    }
}
