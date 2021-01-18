package application;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConnectWithRoom {

	private ResultSet rs = null;
	ConnectWithRoom() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation_system", "sqluser", "sqluserpw");
			Statement st = (Statement) con.createStatement();
			rs = st.executeQuery("select * from room" );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getResultSet() {
		return rs;
	}



}

