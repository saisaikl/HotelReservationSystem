package application;

import javafx.beans.property.SimpleStringProperty;

public class RoomNumber {

	private final SimpleStringProperty roomNumber;

	public RoomNumber(String roomNumber) {
		super();
		this.roomNumber = new SimpleStringProperty(roomNumber);
	}

	public String getRoomNumber() {
		return roomNumber.get();
	}
}
