package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class SearchRoomController implements Initializable {

	@FXML
	private TableView<Room> roomTable;

	@FXML
	private TableColumn<Room, String> roomType;

	@FXML
	private TableColumn<Room, String> roomNumber;

	@FXML
	private TableColumn<Room, String> roomStatus;

	@FXML
	private TableColumn<Room, Integer> price;

	@FXML
	private TableColumn<Room, String> cleanStatus;

	@FXML
	private TableColumn<Room, String> availableDate;

	@FXML
	private TextField room_Type;


	public ObservableList<Room> list = FXCollections.observableArrayList();

	public void addRoomInfo() throws SQLException {
		ConnectWithRoom connect = new ConnectWithRoom();
		ResultSet rs = connect.getResultSet();
		while(rs.next()) {
			list.add(new Room(rs.getString(2),rs.getString(1),rs.getString(6),rs.getInt(5),rs.getString(4),rs.getString(3)));
		}
	}

	public void initialize (URL location, ResourceBundle resources) {
		roomType.setCellValueFactory(new PropertyValueFactory<Room, String>("roomType"));
		roomNumber.setCellValueFactory(new PropertyValueFactory<Room, String>("roomNumber"));
		roomStatus.setCellValueFactory(new PropertyValueFactory<Room, String>("roomStatus"));
		price.setCellValueFactory(new PropertyValueFactory<Room, Integer>("price"));
		cleanStatus.setCellValueFactory(new PropertyValueFactory<Room, String>("cleanStatus"));
		availableDate.setCellValueFactory(new PropertyValueFactory<Room, String>("availableDate"));

		try {
			addRoomInfo();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FilteredList<Room> filteredData = new FilteredList<>(list, e -> true);

		room_Type.textProperty().addListener((observable,oldValue,newValue) -> {
			filteredData.setPredicate(room -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String filteredText = newValue.toLowerCase();
				if (room.getRoomType().toLowerCase().contains(filteredText)) {
					return true;
				}
				return false;
			});
		});

		SortedList<Room> sortedData = new SortedList<>(filteredData);

		sortedData.comparatorProperty().bind(roomTable.comparatorProperty());

		roomTable.setItems(sortedData);
	}

	@FXML
	public void Book(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();


		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/Reserve.fxml"));
		Parent root = (Parent)loader.load();
		primaryStage.setTitle("Reserve Book Page");
		//access the controller and call a method
		ReserveController controller = loader.getController();
		controller.initData(roomTable.getSelectionModel().getSelectedItem());

		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	public void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Functions Page");
		Parent root = FXMLLoader.load(getClass().getResource("/application/Function.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}



}
