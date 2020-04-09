package final_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;


import com.google.gson.*;
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
	
	public static void insert_schedule(Schedule new_schedule, int user_id) {
		Connection conn = Database.getConnection();
		PreparedStatement ps;
		Gson gson = new Gson();
		String json_string = gson.toJson(new_schedule);
		try {
			ps = conn.prepareStatement("INSERT INTO schedules (user, schedule_data) VALUES (?, ?)");
			ps.setString(1, json_string);
			ps.setInt(2, user_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Schedule> get_user_schedules(int user_id) {
		ArrayList<Schedule> results = new ArrayList<Schedule>();
		Connection conn = Database.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		Gson gson = new Gson();
		try {
			ps = conn.prepareStatement("SELECT * FROM schedules WHERE user=?");
			ps.setString(1, Integer.toString(user_id));
			rs = ps.executeQuery();
			while(rs.next()) {
				String json_string = rs.getString("schedule_data");
				if(json_string!=null) {
					Schedule new_schedule = gson.fromJson(json_string, Schedule.class);
					results.add(new_schedule);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public static ArrayList<Reservation> get_room_reservations(int room_id){
		ArrayList<Reservation> results = new ArrayList<Reservation>();
		Connection conn = Database.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		Gson gson = new Gson();
		try {
			ps = conn.prepareStatement("SELECT * FROM reservations WHERE study_room=?");
			ps.setString(1, Integer.toString(room_id));
			rs = ps.executeQuery();
			while(rs.next()) {
				int user = rs.getInt("user");
				int study_room = rs.getInt("study_room");
				Time start_time = rs.getTime("start_time");
				Time end_time = rs.getTime("end_time");
				Date reservation_date = rs.getDate("reservation_date");
				//create new reservation and add
				Reservation new_reservation = new Reservation(user, study_room, 
						start_time, end_time, reservation_date);
				results.add(new_reservation);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public static void insert_reservation(Reservation new_reservation) {
		Connection conn = Database.getConnection();
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("INSERT INTO reservations (user, study_room, start_time, end_time, reservation_date) VALUES (?, ?, ?, ?, ?)");
			ps.setInt(1, new_reservation.user);
			ps.setInt(2, new_reservation.room);
			ps.setTime(3, new_reservation.start_time);
			ps.setTime(4, new_reservation.end_time);
			ps.setDate(5,  new_reservation.reservation_date);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) {
		Connection conn = Database.getConnection();
		String hash_password = Users.generateHash("test");
		Database.insertUser("test", "test@usc.edu", hash_password);
	}*/
}
