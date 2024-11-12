import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class Read_YAML {
    public static String readYAML(String filename) {
        Yaml yaml = new Yaml();
        String expression = "";
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            Map<String, Object> data = yaml.load(inputStream);
            expression = (String) data.get("expression");
            Map<String, Double> values = (Map<String, Double>) data.get("values");

            for (Map.Entry<String, Double> entry : values.entrySet()) {
                expression = expression.replace(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expression;
    }
    
    public static String simpleYAML(String filename) {
        String expression = "";
        Map<String, Double> values = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("expression:")) {
                    expression = line.substring("expression:".length()).trim();
                } else if (line.startsWith("values:")) {
                    String valuesLine = line.substring("values:".length()).trim();
                    valuesLine = valuesLine.replace("{", "").replace("}", "").trim();
                    String[] pairs = valuesLine.split(",");
                    for (String pair : pairs) {
                        String[] value = pair.split(":");
                        values.put(value[0].trim(), Double.parseDouble(value[1].trim()));
                    }
                }
            }
            
            for (Map.Entry<String, Double> entry : values.entrySet()) {
                expression = expression.replace(entry.getKey(), entry.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expression;
    }
    
}
