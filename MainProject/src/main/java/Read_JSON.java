import org.json.JSONObject;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Read_JSON {
    public static String readJSON(String filename) {
    	String newFilename = "Input.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
            String expression = jsonObject.getString("expression");
            JSONObject values = jsonObject.getJSONObject("values");

            Map<String, Double> variables = new HashMap<>();
            for (String key : values.keySet()) {
            	double value = values.getDouble(key);
            	variables.put(key, value);
            }

            System.out.println("Expression: " + expression);
            System.out.println("Values :");
            for (Map.Entry<String, Double> entry : variables.entrySet()) {
            	System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            
            try (FileWriter writer = new FileWriter(newFilename)) {
                writer.write(expression + '\n');
                for (Map.Entry<String, Double> entry : variables.entrySet()) {
                	writer.write(entry.getKey() + " = " + entry.getValue() + '\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Reading of the file is wrong: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Parsing JSON error: " + e.getMessage());
        }
        return newFilename;
    }
}
