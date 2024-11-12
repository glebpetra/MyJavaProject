import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reading {
	public static double readPlainText(String filename) {
	String line ="";
	String expression = "";
    Map<String, String> variables = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        expression = reader.readLine().trim();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                variables.put(parts[0].trim(), parts[1].trim());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    for (Map.Entry<String, String> entry : variables.entrySet()) {
        expression = expression.replace(entry.getKey(), entry.getValue());
    }

    String opz = Counting.create_OPZ(expression);
    double result = Counting.calculate_OPZ(opz);
    
    try (FileWriter writer = new FileWriter("Output.txt")) {
        writer.write("Результат: " + result);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
}
