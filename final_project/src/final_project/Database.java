package final_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

public class Database {
	
	public static Connection getConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://schedget-db.cmvyd0ufn9eh.us-west-1.rds.amazonaws.com/schedget?user=admin&password=SchedgetAdmin*01");
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} /*finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}*/
		return conn;
	}
	
	public static void insertUser(String username, String email, String password) {
		if(!Database.verifyUserNotExists(username)) {
			System.out.println("User already exists");
			return;
		}
		Connection conn = Database.getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean verifyUserNotExists(String username) {
		Connection conn = Database.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = conn.prepareStatement("SELECT * FROM users WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()==false) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/*public static void main(String[] args) {
		Connection conn = Database.getConnection();
		String hash_password = Users.generateHash("test");
		Database.insertUser("test", "test@usc.edu", hash_password);
	}*/
}
