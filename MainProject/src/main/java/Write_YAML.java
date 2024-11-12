import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class Write_YAML {
	public static void writeYAML(double result, String filename) {
		Map<String, Double> data = new HashMap<>();
		data.put("result", result);
		// Result res = new Result();
		// res.setResult(result);
		Yaml yaml = new Yaml();
		String yamlStr = yaml.dump(data);
		try (FileWriter writer = new FileWriter(filename)) {
	        writer.write(yamlStr);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
