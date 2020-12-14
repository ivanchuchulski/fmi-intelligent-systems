import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private Map<ClassName, Map<String, Vote>> model;

    public Model() {
        model = new HashMap<>();
    }
}
