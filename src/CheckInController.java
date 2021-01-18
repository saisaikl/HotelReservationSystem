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

public class CheckInController implements Initializable {

	String url = "jdbc:mysql://localhost:3306/hotel_reservation_system";
	String user = "sqluser";
	String password = "sqluserpw";

	@FXML
	private TextField txtField;

	@FXML
	private TableView<GuestForCheckIn> guestForCheckInTable;

	@FXML
	private TableColumn<GuestForCheckIn, Integer> guestId;

	@FXML
	private TableColumn<GuestForCheckIn, String> customerName;

	@FXML
	private TableColumn<GuestForCheckIn, String> email;

	@FXML
	private TableColumn<GuestForCheckIn, String> checkInDate;

	@FXML
	private TableColumn<GuestForCheckIn, String> checkInStatus;

	@FXML
	private TableColumn<GuestForCheckIn, String> roomNumber;

	@FXML
	private TableColumn<GuestForCheckIn, String> roomType;

	public ObservableList<GuestForCheckIn> list = FXCollections.observableArrayList();

	public void addGuestCheckInInfo() throws SQLException {
		ConnectWithGuestCheckIn connect = new ConnectWithGuestCheckIn();
		ResultSet rs = connect.getResultSet();
		while (rs.next()) {
			list.add(new GuestForCheckIn(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
		}
	}

	public void initialize (URL location, ResourceBundle resources) {

		guestId.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, Integer>("guestId"));
		customerName.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, String>("customerName"));
		email.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, String>("email"));
		checkInDate.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, String>("checkInDate"));
		checkInStatus.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, String>("checkInStatus"));
		roomNumber.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, String>("roomNumber"));
		roomType.setCellValueFactory(new PropertyValueFactory<GuestForCheckIn, String>("roomType"));

		try {
			addGuestCheckInInfo();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FilteredList<GuestForCheckIn> filteredData = new FilteredList<>(list, e -> true);

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

		SortedList<GuestForCheckIn> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(guestForCheckInTable.comparatorProperty());
		guestForCheckInTable.setItems(sortedData);
	}

	@FXML
	public void Update(ActionEvent event) throws Exception {

		GuestForCheckIn selectedGuest = guestForCheckInTable.getSelectionModel().getSelectedItem();
		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			//2.Create a statement
			Statement myStmt = myConn.createStatement();

			String update = "update hotel_reservation_system.guest"
					+ " set checkInStatus = 'yes' "
					+ " where guestID = '" + selectedGuest.getGuestId() + "';";

			myStmt.executeUpdate(update);

		//	System.out.println("Insert Complete.");

		}catch (Exception e) {
			System.out.println(e);
		}

		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/Function.fxml"));
		Parent root = (Parent)loader.load();

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

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

}
