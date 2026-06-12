package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;
import model.LoginResult;

public class UserDAO {
	
	Connection con;
	
	public UserDAO() {
		con=DBConnection.getConnection();
	}
	public LoginResult login(String username,String password) {
		LoginResult result = null;
		
		try {
			String query="select role,doctor_id from users where username=? and password=?";
			
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1,username);
			pst.setString(2, password);
			
		ResultSet rs=pst.executeQuery();
		
		if(rs.next()) {
			String role=rs.getString("role");
			
			int doctorId = rs.getInt("doctor_id");
			
			result = new LoginResult(role,doctorId);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean addDoctorUser(String username , String password , int doctorId) {
		
		boolean inserted = false;
		
		try {
			
			String query ="INSERT INTO users(username,password,role,doctor_id) VALUES(?,?,?,?)";
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, "DOCTOR");
			pst.setInt(4, doctorId);
			
			int rows = pst.executeUpdate();
			
			if(rows > 0) {
				inserted = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return inserted;
	}
	
	public ResultSet getAllUsers() {
		
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM users";
			
			PreparedStatement pst = con.prepareStatement(query);
			
			rs = pst.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}

	public int getTotalUsers() {
		
		int count = 0;
		
		try {
			
			String query = "SELECT COUNT(*) FROM users";
			
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
