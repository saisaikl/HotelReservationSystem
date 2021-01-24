package application;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnectWithGuestCheckOut {

	private ResultSet rs = null;
	ConnectWithGuestCheckOut() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation_system", "sqluser", "sqluserpw");
			Statement st = (Statement) con.createStatement();
			rs = st.executeQuery("select guestID, customerName, guest.roomNumber, roomType, price, checkOutDate, checkOutStatus from guest natural join customer natural join room");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getResultSet() {
		return rs;
	}
}
