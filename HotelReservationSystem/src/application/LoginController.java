package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class LoginController {
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label lblStatus;

	// Event Listener on Button.onAction
	@FXML
	public void Login(ActionEvent event) throws Exception {
		Parent root = null;
		if( txtUserName.getText().equals("reception") && txtPassword.getText().equals("pass")) {
			lblStatus.setText("Login Success");
			root = FXMLLoader.load(getClass().getResource("/application/Function.fxml"));

//		} else  if (txtUserName.getText().equals("housek") && txtPassword.getText().equals("pass")) {
//			lblStatus.setText("Login Success");
//			root = FXMLLoader.load(getClass().getResource("/application/HouseKeeping.fxml"));
//
		} else {
			lblStatus.setText("Login Fail");
		}
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Functions Page");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
