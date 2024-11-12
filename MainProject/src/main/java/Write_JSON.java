import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Write_JSON {
	public static void simpleJSON(double result, String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
	        writer.write("{" + "\n");
	        writer.write("\"result\" : " + String.valueOf(result) + "\n");
	        writer.write("}" + "\n");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void writeJSON(double result, String filename) throws JsonProcessingException {
		Result res = new Result();
    	res.setResult(result);

    	ObjectMapper mapper = new ObjectMapper();
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	String jsonResult = mapper.writeValueAsString(res);
    	try (FileWriter writer = new FileWriter(filename)) {
	        writer.write(jsonResult);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
