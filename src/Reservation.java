package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reservation {

	private final SimpleIntegerProperty guestID;
	private final SimpleStringProperty customerName;
	private final SimpleStringProperty email;
	private final SimpleStringProperty phone;
	private final SimpleStringProperty checkIn;
	private final SimpleStringProperty checkOut;
	private final SimpleStringProperty roomNumber;

	public Reservation(int guestID, String customerName, String email, String phone, String checkIn, String checkOut, String roomNumber) {
		super();

		this.guestID = new SimpleIntegerProperty(guestID);
		this.customerName = new SimpleStringProperty(customerName);
		this.email = new SimpleStringProperty(email);
		this.phone = new SimpleStringProperty(phone);
		this.checkIn = new SimpleStringProperty(checkIn);
		this.checkOut = new SimpleStringProperty(checkOut);
		this.roomNumber = new SimpleStringProperty(roomNumber);
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

	public String getPhone() {
		return phone.get();
	}
	public String getCheckIn() {
		return checkIn.get();
	}

	public String getCheckOut() {
		return checkOut.get();
	}

	public String getRoomNumber() {
		return roomNumber.get();
	}

}
