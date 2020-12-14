import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// ten fold cross-validation
// replace the ? with the most-common value for the class
public class Main {
    public static void main(String[] args) {
        final String datasetPath = "resourses" + File.separator + "house-votes-84.data";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(datasetPath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
