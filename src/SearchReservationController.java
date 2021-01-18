package application;

import java.io.IOException;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class SearchReservationController implements Initializable {

	@FXML
	private TextField textField;

	@FXML
	private TableView<Reservation> guestTable;

	@FXML
	private TableColumn<Reservation, Integer> guestID;

	@FXML
	private TableColumn<Reservation, String> customerName;

	@FXML
	private TableColumn<Reservation, String> email;

	@FXML
	private TableColumn<Reservation, String> phone;

	@FXML
	private TableColumn<Reservation, String> checkIn;

	@FXML
	private TableColumn<Reservation, String> checkOut;

	@FXML
	private TableColumn<Reservation, String> roomNumber;

	@FXML
	private Label label_Guest_Id;

	@FXML
	private TextField txtField_Customer_Name;

	@FXML
	private TextField txtField_Email;

	@FXML
	private TextField txtField_Phone;

	@FXML
	private TextField txtField_CheckIn;

	@FXML
	private TextField txtField_CheckOut;

	@FXML
	private Label roomNumber_Value;

	@FXML
	private ComboBox<String> comboBox_roomType;

	final ObservableList<String> roomTypeList = FXCollections.observableArrayList();
	final ObservableList<String> roomNumberList = FXCollections.observableArrayList();

	public String url = "jdbc:mysql://localhost:3306/hotel_reservation_system";
	public String user = "sqluser";
	public String password = "sqluserpw";

	public ObservableList<Reservation> list = FXCollections.observableArrayList();

	public void addGuestInfo() throws SQLException {
		ConnectWithGuest connect = new ConnectWithGuest();
		ResultSet rs = connect.getResultSet();
		while(rs.next()) {
			list.add(new Reservation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
		}
	}

	//to filter or sort
	public void initialize (URL location, ResourceBundle resources) {

		guestID.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("guestId"));
		customerName.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
		email.setCellValueFactory(new PropertyValueFactory<Reservation, String>("email"));
		phone.setCellValueFactory(new PropertyValueFactory<Reservation, String>("phone"));
		checkIn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkIn"));
		checkOut.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkOut"));
		roomNumber.setCellValueFactory(new PropertyValueFactory<Reservation, String>("roomNumber"));

		try {
			addGuestInfo();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FilteredList<Reservation> filteredData = new FilteredList<>(list, e -> true);

		textField.textProperty().addListener((observable,oldValue,newValue) -> {
			filteredData.setPredicate(reservation -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String filteredText = newValue.toLowerCase();
				if (reservation.getCustomerName().toLowerCase().contains(filteredText)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Reservation> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(guestTable.comparatorProperty());

		guestTable.setItems(sortedData);
		setCellValueFromTableToTextField();
		try {
			fillRoomType();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			fillRoomNumber();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	/**To update customer information when customer ask
		1.to change checkIn or checkOut date for them
		2.to change roomType (for roomNumber, reception will choose it.)

		**/
	@FXML
	public void Update(ActionEvent event) throws Exception {

		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			//2.Create a statement
			Statement myStmt = myConn.createStatement();

			//3.Execute SQL query
			//Example : update hotel_reservation_system.room set availableStatus = 'no' , availableDate = '2017-12-26' where roomNumber = '102';
			String update = "update hotel_reservation_system.customer"
					+ " set customerName = '" + txtField_Customer_Name.getText() + "'"
					+ " where email = '" + txtField_Email.getText() + "';";

			String updateDate = "update hotel_reservation_system.guest"
					+ " set checkInDate = '" + txtField_CheckIn.getText() + "', checkOutDate = '" + txtField_CheckOut.getText() + "'"
					+ " where email = '" + txtField_Email.getText() + "';";

			myStmt.executeUpdate(update);
			myStmt.executeUpdate(updateDate);

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

		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			//2.Create a statement
			Statement myStmt = myConn.createStatement();

			//3.Execute SQL query
			// update customer name if they want to change name
			String updateCustomerInfo = "update hotel_reservation_system.customer"
					+ " set customerName = '" + txtField_Customer_Name.getText() + "', phone = '" + txtField_Phone.getText() + "'"
					+ " where email = '" + txtField_Email.getText() + "';";

			String updateDate = "update hotel_reservation_system.guest"
					+ " set checkInDate = '" + txtField_CheckIn.getText() + "', checkOutDate = '" + txtField_CheckOut.getText() + "'"
					+ " where email = '" + txtField_Email.getText() + "';";

			String updateRoom = "update hotel_reservation_system.room"
					+ " set availableStatus = 'no' , availableDate = '" + txtField_CheckOut.getText() + "'"
					+ " where roomNumber = '" + roomNumber.getText() + "';";

			myStmt.executeUpdate(updateCustomerInfo);
			myStmt.executeUpdate(updateDate);
			myStmt.executeUpdate(updateRoom);
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
	public void CancelBooking(ActionEvent event) throws IOException {

		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			//2.Create a statement
			Statement myStmt = myConn.createStatement();

			//3.Execute SQL query
			/** if customer cancel their booking,
				need to change
					1. available status = 'yes'
					2. available Date = checkIn date
					3.
			**/
			String updateRoom = "update hotel_reservation_system.room"
					+ " set availableStatus = 'yes' , availableDate = '" + txtField_CheckIn.getText() + "'"
					+ " where roomNumber = '" + roomNumber.getText() + "';";

			String deleteGuest = "delete from hotel_reservation_system.guest"
					+ " where guestID = '" + label_Guest_Id.getText() + "';";

			myStmt.executeUpdate(deleteGuest);
			myStmt.executeUpdate(updateRoom);

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

	//when we click one cell value of table and it will automatically pass value to text_field
	private void setCellValueFromTableToTextField() {
		txtField_Email.setDisable(true);
		guestTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Reservation r = guestTable.getItems().get(guestTable.getSelectionModel().getSelectedIndex());
				label_Guest_Id.setText(r.getGuestId() + "");
				txtField_Customer_Name.setText(r.getCustomerName());
				txtField_Email.setText(r.getEmail());
				txtField_Phone.setText(r.getPhone());
				txtField_CheckIn.setText(r.getCheckIn());
				txtField_CheckOut.setText(r.getCheckOut());
				comboBox_roomType.setValue("Superior");
			}
		});
	}

	//to fill roomType into comboBox and if customer want to change room type, we can change it for them.
	public void fillRoomType() throws SQLException {
		try {
			//1. Get a connection to database
			Connection myConn = DriverManager.getConnection(url,user,password);

			ResultSet rs = myConn.createStatement().executeQuery("select distinct roomType from room;");
			while (rs.next()) {
				roomTypeList.add(new RoomType(rs.getString(1)).getRoomType());
			}

		}catch (Exception e) {
			System.out.println(e);
		}
		comboBox_roomType.setItems(null);
		comboBox_roomType.setItems(roomTypeList);
	}

	public void fillRoomNumber() throws SQLException {
		try {
			Connection myConn = DriverManager.getConnection(url,user,password);

			ResultSet rs = myConn.createStatement().executeQuery("select roomNumber from room where roomType = '" + roomNumber_Value + "';");
			while (rs.next()) {
				roomNumberList.add(new RoomNumber(rs.getString(1)).getRoomNumber());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
