package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CheckOutController implements Initializable{

	String url = "jdbc:mysql://localhost:3306/hotel_reservation_system";
	String user = "sqluser";
	String password = "sqluserpw";

	@FXML
	private TextField txtField;

	@FXML
	private TableView<GuestForCheckOut> guestForCheckOutTable;

	@FXML
	private TableColumn<GuestForCheckOut, Integer> guestId;

	@FXML
	private TableColumn<GuestForCheckOut, String> customerName;

	@FXML
	private TableColumn<GuestForCheckOut, String> roomNumber;

	@FXML
	private TableColumn<GuestForCheckOut, String> roomType;

	@FXML
	private TableColumn<GuestForCheckOut, Integer> price;

	@FXML
	private TableColumn<GuestForCheckOut, String> checkOutDate;

	@FXML
	private TableColumn<GuestForCheckOut, String> checkOutStatus;

	public ObservableList<GuestForCheckOut> list = FXCollections.observableArrayList();

	@FXML
	public void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Function.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void addGuestCheckOutInfo() throws SQLException {
		ConnectWithGuestCheckOut connect = new ConnectWithGuestCheckOut();
		ResultSet rs = connect.getResultSet();
		while (rs.next()) {
			list.add(new GuestForCheckOut(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7)));
		}
	}

	public void initialize (URL location, ResourceBundle resources) {

		guestId.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, Integer>("guestId"));
		customerName.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, String>("customerName"));
		roomNumber.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, String>("roomNumber"));
		roomType.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, String>("roomType"));
		price.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, Integer>("price"));
		checkOutDate.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, String>("checkOutDate"));
		checkOutStatus.setCellValueFactory(new PropertyValueFactory<GuestForCheckOut, String>("checkOutStatus"));

		try {
			addGuestCheckOutInfo();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FilteredList<GuestForCheckOut> filteredData = new FilteredList<>(list, e -> true);

		txtField.textProperty().addListener((observable,oldValue,newValue) -> {
			filteredData.setPredicate(guestForCheckIn -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String filteredText = newValue.toLowerCase();
				if (guestForCheckIn.getCustomerName().toLowerCase().contains(filteredText)) {
					return true;
				}
				return false;
			});
		});

		SortedList<GuestForCheckOut> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(guestForCheckOutTable.comparatorProperty());
		guestForCheckOutTable.setItems(sortedData);
	}

	@FXML
	public void CheckOut(ActionEvent event) throws Exception {

		GuestForCheckOut selectedGuest = guestForCheckOutTable.getSelectionModel().getSelectedItem();
		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			//2.Create a statement
			Statement myStmt = myConn.createStatement();

			String update = "update hotel_reservation_system.guest"
					+ " set checkOutStatus = 'yes' "
					+ " where guestID = '" + selectedGuest.getGuestId() + "';";

			String updateRoom = "update hotel_reservation_system.room"
					+ " set availableStatus = 'yes' , availableDate = '" + selectedGuest.getCheckOutDate() +"'"
					+ " where roomNumber = '" + selectedGuest.getRoomNumber() + "';";
			myStmt.executeUpdate(update);
			myStmt.executeUpdate(updateRoom);

		//	System.out.println("Insert Complete.");

		}catch (Exception e) {
			System.out.println(e);
		}
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Function.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
