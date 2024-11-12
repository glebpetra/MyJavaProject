import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

public class Read_HTML {
    public static String readHTML(String filename) {
    	String expression = "";
    	Map<String, Double> values = new HashMap<>();
        try {
            File input = new File(filename);
            Document doc = Jsoup.parse(input, "UTF-8");
            expression = doc.select("expression").text();
            
            Element elem = doc.selectFirst("values");
            for (Element var : elem.children()) {
            	String variable = var.tagName();
            	Double value = Double.parseDouble(var.text());
            	values.put(variable, value);
            }
            
            for (Map.Entry<String, Double> entry : values.entrySet()) {
            	expression = expression.replace(entry.getKey(), String.valueOf(entry.getValue()));
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expression;
    }
    
    public static String simpleHTML(String filename) {
        String expression = "";
        Map<String, Double> variables = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean inValues = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("<expression>")) {
                    expression = line.replace("<expression>", "").replace("</expression>", "").trim();
                }
                else if (line.startsWith("<values>")) {
                    inValues = true;
                }
                else if (line.startsWith("</values>")) {
                    inValues = false;
                }
                else if (inValues) {
                    String[] parts = line.split(">");
                    if (parts.length > 1) {
                        String tagName = parts[0].substring(1);
                        String valueStr = parts[1].substring(0, parts[1].indexOf("<")).trim();
                        double value = Double.parseDouble(valueStr);
                        variables.put(tagName, value);
                    }
                }
            }

            for (Map.Entry<String, Double> entry : variables.entrySet()) {
                expression = expression.replace(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expression;
    }
}
