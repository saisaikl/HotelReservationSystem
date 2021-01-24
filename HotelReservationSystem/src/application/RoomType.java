package application;

import javafx.beans.property.SimpleStringProperty;

public class RoomType {

	private final SimpleStringProperty roomType;

	public RoomType(String roomType) {
		super();
		this.roomType = new SimpleStringProperty(roomType);
	}

	public String getRoomType() {
		return roomType.get();
	}
}
