import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model {
    // {classname -> {list of attributes -> #votes} }
    private final Map<String, List<AttributeVote>> model;

    public Model() {
        model = new HashMap<>();
    }

    public void buildModel(Set<String> classNames, List<DatasetEntry> validatingSet, int attributesCount) {

        for (String className : classNames) {
            List<AttributeVote> attributeAttributeVotes = new ArrayList<>();

            // ?added the Laplace smoothing, each vote to be 1 initially
            // this way zero probability will be avoided
            for (int i = 0; i < attributesCount; i++) {
                attributeAttributeVotes.add(new AttributeVote(1, 1, 1));
            }

            model.put(className, attributeAttributeVotes);
        }

        for (DatasetEntry datasetEntry : validatingSet) {
            String className = datasetEntry.getClassName();
            String[] attributes = datasetEntry.getAttributes();
            List<AttributeVote> attributeVotes = model.get(className);

            for (int i = 0; i < attributes.length; i++) {
                String attribute = attributes[i];
                AttributeVote attributeVote = attributeVotes.get(i);

                attributeVote.incrementVote(attribute);
            }
        }
    }

    public void clearModel() {
        model.clear();
    }

    public Map<String, List<AttributeVote>> getModel() {
        return model;
    }
}
