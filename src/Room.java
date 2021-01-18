package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room {

	private final SimpleStringProperty roomType;
	private final SimpleStringProperty roomNumber;
	private final SimpleStringProperty roomStatus;
	private final SimpleIntegerProperty price;
	private final SimpleStringProperty cleanStatus;
	private final SimpleStringProperty availableDate;

	public Room( String roomType,String roomNumber, String roomStatus, int price, String cleanStatus, String availableDate) {
		super();
		this.roomType = new SimpleStringProperty(roomType);
		this.roomNumber = new SimpleStringProperty(roomNumber);
		this.roomStatus = new SimpleStringProperty(roomStatus);
		this.price = new SimpleIntegerProperty(price);
		this.cleanStatus = new SimpleStringProperty(cleanStatus);
		this.availableDate = new SimpleStringProperty(availableDate);

	}

	public String getRoomNumber() {
		return roomNumber.get();
	}

	public String getRoomType() {
		return roomType.get();
	}

	public String getRoomStatus() {
		return roomStatus.get();
	}

	public int getPrice() {
		return price.get();
	}

	public String getCleanStatus() {
		return cleanStatus.get();
	}

	public String getAvailableDate() {
		return availableDate.get();
	}

}
