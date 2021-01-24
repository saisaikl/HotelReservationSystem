package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GuestForCheckIn {

	private final SimpleIntegerProperty guestID;
	private final SimpleStringProperty customerName;
	private final SimpleStringProperty email;
	private final SimpleStringProperty checkInDate;
	private final SimpleStringProperty checkInStatus;
	private final SimpleStringProperty roomNumber;
	private final SimpleStringProperty roomType;

	public GuestForCheckIn(int guestID,String customerName, String email, String checkInDate, String checkInStatus, String roomNumber, String roomType) {
		super();
		this.guestID = new SimpleIntegerProperty(guestID);
		this.customerName = new SimpleStringProperty(customerName);
		this.email = new SimpleStringProperty(email);
		this.checkInDate = new SimpleStringProperty(checkInDate);
		this.checkInStatus = new SimpleStringProperty(checkInStatus);
		this.roomNumber = new SimpleStringProperty(roomNumber);
		this.roomType = new SimpleStringProperty(roomType);
	}

	public int getGuestId() {
		return guestID.get();
	}

	public String getCustomerName() {
		return customerName.get();
	}

	public String getEmail() {
		return email.get();
	}

	public String getCheckInDate() {
		return checkInDate.get();
	}

	public String getCheckInStatus() {
		return checkInStatus.get();
	}

	public String getRoomNumber() {
		return roomNumber.get();
	}

	public String getRoomType() {
		return roomType.get();
	}


}
