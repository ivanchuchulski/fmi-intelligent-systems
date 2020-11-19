import java.util.Comparator;

public class PathLengthComparator implements Comparator<Path> {

    @Override
    public int compare(Path o1, Path o2) {
        return Integer.compare(o1.getFitness(), o2.getFitness());
    }
}
