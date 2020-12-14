import java.util.Arrays;
import java.util.Objects;

public class DatasetEntry {
    private String className;
    private String[] attributes;

    public DatasetEntry(String className, String[] attributes) {
        this.className = className;
        this.attributes = attributes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatasetEntry that = (DatasetEntry) o;
        return Objects.equals(className, that.className) &&
                Arrays.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(className);
        result = 31 * result + Arrays.hashCode(attributes);
        return result;
    }
}
