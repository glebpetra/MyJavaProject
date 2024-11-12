import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Read_XML {
    public static String readXML(String filename) {
        String newFilename = "Input.txt";
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            doc.getDocumentElement().normalize();
            String expression = doc.getElementsByTagName("expression").item(0).getTextContent();
            NodeList valuesList = doc.getElementsByTagName("values");
            Element valuesElement = (Element) valuesList.item(0);

            Map<String, Double> variables = new HashMap<>();

            NodeList variableNodes = valuesElement.getChildNodes();
            for (int i = 0; i < variableNodes.getLength(); i++) {
                Node node = variableNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String varName = node.getNodeName();
                    double varValue = Double.parseDouble(node.getTextContent());
                    variables.put(varName, varValue);
                }
            }

            System.out.println("Expression: " + expression);
            System.out.println("Values:");
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
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return newFilename;
    }
}
