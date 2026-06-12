package dao;

import database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import model.Doctor;

public class DoctorDAO {
	
	Connection con;
	
	public DoctorDAO() {
		
		con=DBConnection.getConnection();
		
	}
	
	public ArrayList<Doctor> getAllDoctors(){
		ArrayList<Doctor> doctorList = new ArrayList<>();
		
		try {
			
			String query = "SELECT * FROM doctors";
			
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("doctor_id");
				String name = rs.getString("doctor_name");
				String specialization = rs.getString("specialization");
				
				Doctor doctor = new Doctor(id, name, specialization);
				
				doctorList.add(doctor);
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return doctorList;
	}
	
	public String getDoctorNameById(int doctorId) {
		String name = "" ;
		
		try {
			
			String query = "SELECT doctor_name FROM doctors WHERE doctor_id = ?";
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, doctorId);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				
				name = rs.getString("doctor_name");
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		} 
		return name;
	}
	

	public boolean addDoctorUser(int doctorId,String username,String password) {
		
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
				inserted = true ;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return inserted;
	}
	
	public int addDoctor(String doctorName, String specialization) {
		
		int doctorId = -1;
		
		try {
			
			String query = "INSERT INTO doctors(doctor_name,specialization) VALUES(?,?)";
			
			PreparedStatement pst = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, doctorName);
			pst.setString(2, specialization);
			
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			
			if(rs.next()) {
				doctorId = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return doctorId;
	}
	
	public int getTotalDoctors() {
		
		int count = 0;
		
		try {
			
			String query ="SELECT COUNT(*) FROM doctors";
			
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



