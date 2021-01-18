package application;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReserveController implements Initializable{


	String url = "jdbc:mysql://localhost:3306/hotel_reservation_system";
	String user = "sqluser";
	String password = "sqluserpw";

	@FXML
	private TextField Type;

	@FXML
	private TextField name;

	@FXML
	private TextField email;

	@FXML
	private TextField phone;

	@FXML
	private DatePicker checkIn;

	@FXML
	private DatePicker checkOut;

	@FXML
	private Label roomNumber;

	private Room selectedRoom;

	@FXML
	private Label roomTypeLabel;

	public void initData(Room room) {
		selectedRoom = room;
		roomTypeLabel.setText(selectedRoom.getRoomType());
		roomNumber.setText(selectedRoom.getRoomNumber());
	}

	@FXML
	public void Back(ActionEvent event) throws Exception {


		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/SearchRoom.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	@FXML
	public void Book(ActionEvent event) throws Exception {

		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			//2.Create a statement
			Statement myStmt = myConn.createStatement();
			ResultSet rs = null;

			String queryEmail = "select email from customer";
			rs = myStmt.executeQuery(queryEmail);
			boolean checkEmail = false;
			while (rs.next()) {
				checkEmail = (rs.getString(1).equalsIgnoreCase(email.getText()));
			}

			if (checkEmail) {
				String insertNewcomer = "insert into customer "
						+ " (email, customerName, phone)"
						+ " values ('" + email.getText() + "','" + name.getText() + "','" + phone.getText() + "')";
				myStmt.executeUpdate(insertNewcomer);
			}

			//3.Execute SQL query
			String sql = "insert into guest "
					+ " (guestID, email, roomNumber, checkInDate, checkOutDate, checkInStatus, checkOutStatus)"
					+ " values (default,'"+email.getText()+"','"+roomNumber.getText()+"','"+ this.checkIn.getValue().toString() +"','"+this.checkOut.getValue().toString()+"','no','no')";


			String update = "update hotel_reservation_system.room"
					+ " set availableStatus = 'no' , availableDate = '" + this.checkOut.getValue().toString() + "'"
					+ " where roomNumber = '" + roomNumber.getText() + "';";


			myStmt.executeUpdate(sql);
			myStmt.executeUpdate(update);

		//	System.out.println("Insert Complete.");

		}catch (Exception e) {
			System.out.println(e);
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/Email.fxml"));

		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = loader.load();
		primaryStage.setTitle("Email");

		EmailController controller = loader.getController();
		controller.initEmail(email.getText());
		Scene scene = new Scene(root);

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
