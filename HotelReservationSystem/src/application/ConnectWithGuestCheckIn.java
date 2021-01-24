package application;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnectWithGuestCheckIn {

	private ResultSet rs = null;
	ConnectWithGuestCheckIn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation_system", "sqluser", "sqluserpw");
			Statement st = (Statement) con.createStatement();
			rs = st.executeQuery("select guestID, customerName, guest.email, checkInDate, checkInStatus, guest.roomNumber, roomType from guest, customer, room"
					+ " where customer.email = guest.email and guest.roomNumber = room.roomNumber;");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getResultSet() {
		return rs;
	}
}
