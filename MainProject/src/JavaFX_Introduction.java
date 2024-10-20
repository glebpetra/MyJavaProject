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

public class JavaFX_Introduction extends Application {
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
        
        but.setOnAction(event -> {
			try {
				currentName.setText("You have entered: " + filename.getText());
				String pass = password.getText();
				String name = filename.getText();
				String name_dearch = Dearchiving.dearchiving(name);
				String name_dearch_decr = Decryption.decryption(pass, name_dearch);
				result.setText("Result is: " + String.valueOf(Reading.readPlainText(name_dearch_decr)));
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
			}
		});
        FlowPane panel = new FlowPane(Orientation.VERTICAL, 0, 10, enterName, filename,
        		enterPassword, password, but, currentName, result);
        panel.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(panel);
        // scene.setFill(Color.RED);
        stage.setScene(scene);
        stage.setTitle("IP Project");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }

}