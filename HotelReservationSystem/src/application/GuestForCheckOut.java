package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GuestForCheckOut {

	private final SimpleIntegerProperty guestID;
	private final SimpleStringProperty customerName;
	private final SimpleStringProperty roomNumber;
	private final SimpleStringProperty roomType;
	private final SimpleIntegerProperty price;
	private final SimpleStringProperty checkOutDate;
	private final SimpleStringProperty checkOutStatus;



	public GuestForCheckOut(int guestID,String customerName, String roomNumber, String roomType, int price, String checkOutDate, String checkOutStatus) {
		super();
		this.guestID = new SimpleIntegerProperty(guestID);
		this.customerName = new SimpleStringProperty(customerName);
		this.roomNumber = new SimpleStringProperty(roomNumber);
		this.roomType = new SimpleStringProperty(roomType);
		this.price = new SimpleIntegerProperty(price);
		this.checkOutDate = new SimpleStringProperty(checkOutDate);
		this.checkOutStatus = new SimpleStringProperty(checkOutStatus);
	}

	public int getGuestId() {
		return guestID.get();
	}

	public String getCustomerName() {
		return customerName.get();
	}

	public String getRoomNumber() {
		return roomNumber.get();
	}

	public String getRoomType() {
		return roomType.get();
	}

	public int getPrice() {
		return price.get();
	}
	public String getCheckOutDate() {
		return checkOutDate.get();
	}

	public String getCheckOutStatus() {
		return checkOutStatus.get();
	}
}
