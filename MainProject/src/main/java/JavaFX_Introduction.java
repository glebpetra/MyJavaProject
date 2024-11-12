import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.collections.*;

public class JavaFX_Introduction extends Application {
	private StringBuilder tag = new StringBuilder();
	public CheckBox arch;
	public CheckBox encr;
	private String filenameValue;
	// private String passwordValue;
	
	public static void main(String[] args) {
		launch(args);
	}
	
    @Override
    public void start(Stage stage) {
        Label enterName = new Label("Enter the name of your file");
        Label currentName = new Label();
        Label result = new Label();
        Label enterPassword = new Label("Enter your password if it necessary");
        TextField filename = new TextField();
        PasswordField password = new PasswordField();
        filename.setPrefColumnCount(15);
        Button but = new Button("OK");
        
        ObservableList<String> format = FXCollections.observableArrayList("txt", "json", "xml", "yaml", "html");
        ComboBox<String> formatComboBox = new ComboBox<String>(format);
        // formatComboBox.setValue("txt");
        
        arch = new CheckBox("Archived");
        arch.setSelected(false);
        encr = new CheckBox("Encoded");
        encr.setSelected(false);
        
        formatComboBox.setOnAction(event -> tag.append(formatComboBox.getValue()));
        but.setOnAction(event -> {
			try {
				currentName.setText("You have entered: " + tag);
				String pass = password.getText();
				filenameValue = filename.getText();
				IsArchiving();
				// String name_dearch = Dearchiving.dearchiving(name);
				IsEncoding(pass);
				// String name_dearch_decr = Decryption.decryption(pass, name_dearch);
				switch(tag.toString()) {
				case "txt":
					result.setText("Result is: " + String.valueOf(Reading.readPlainText(filenameValue)));
					break;
				case "json":
					filenameValue = Read_JSON.readJSON(filenameValue);
					result.setText("Result is: " + String.valueOf(Reading.readPlainText(filenameValue)));
					Write_JSON.writeJSON(Reading.readPlainText(filenameValue), "output.json");
					break;
				case "xml":
					filenameValue = Read_XML.readXML(filenameValue);
					result.setText("Result is: " + String.valueOf(Reading.readPlainText(filenameValue)));
					break;
				case "yaml":
					result.setText(SetResultForYAML(filenameValue));
					Write_YAML.writeYAML(GetResultForYAML(filenameValue), "output.yaml");
					break;
				case "html":
					result.setText(SetResultForHTML(filenameValue));
					break;
				default:
					break;
				}
				// result.setText("Result is: " + String.valueOf(Reading.readPlainText(filenameValue)));
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
        FlowPane panel = new FlowPane(Orientation.VERTICAL, 0, 10, enterName, filename, formatComboBox,
        		arch, encr, enterPassword, password, but, currentName, result);
        panel.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(panel);
        stage.setScene(scene);
        stage.setTitle("IP Project");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }
    
    public void IsArchiving() throws FileNotFoundException, IOException, InvalidKeyException,
    NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, 
    BadPaddingException, InvalidAlgorithmParameterException {
    	if (arch.isSelected()) {
    		filenameValue = Dearchiving.dearchiving(filenameValue);
    	}
    }
    
    public void IsEncoding(String password) throws Exception {
    	if (encr.isSelected()) {
    		filenameValue = Decryption.decrypt(password, filenameValue);
    	}
    }
    
    public String SetResultForYAML(String filename) {
    	String expression = Read_YAML.readYAML(filename);
    	String OPZ = Counting.create_OPZ(expression);
    	String result = "Result is: " + String.valueOf(Counting.calculate_OPZ(OPZ));
    	return result;
    }
    
    public double GetResultForYAML(String filename) {
    	String expression = Read_YAML.readYAML(filename);
    	String OPZ = Counting.create_OPZ(expression);
    	double result = Counting.calculate_OPZ(OPZ);
    	return result;
    }
    
    public String SetResultForHTML(String filename) {
    	String expression = Read_HTML.readHTML(filename);
    	String OPZ = Counting.create_OPZ(expression);
    	String result = "Result is: " + String.valueOf(Counting.calculate_OPZ(OPZ));
    	return result;
    }
}