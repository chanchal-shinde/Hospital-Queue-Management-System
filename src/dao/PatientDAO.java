package dao;
import database.DBConnection;
import model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientDAO {
	
	Connection con;
	
	public PatientDAO() {
		
		con=DBConnection.getConnection();
		
	}
	
	public int generateTokenNumber() {
		int token=1;
		
		try {
			String query="SELECT MAX(token_number) From patients WHERE visit_date=CURDATE()";
			
			PreparedStatement pst = con.prepareStatement(query);
			
			ResultSet  rs = pst.executeQuery();
			
			if(rs.next()) {
				token=rs.getInt(1)+1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	public boolean addPatient(Patient patient) {
		boolean inserted = false;
		int token=generateTokenNumber();
		try {
			String query=
					"INSERT INTO patients(patient_name,age,gender,doctor_id,token_number,status,phone,visit_date) "
					+ "VALUES(?,?,?,?,?,?,?,?)";
			
			PreparedStatement pst= con.prepareStatement(query);
			
			pst.setString(1,patient.getName());
			pst.setInt(2,patient.getAge()); 
			pst.setString(3,patient.getGender());
			pst.setInt(4, patient.getDoctorId());	
			pst.setInt(5, token);
			pst.setString(6, "WAITING");
			pst.setString(7,patient.getPhone());
			pst.setDate(8, java.sql.Date.valueOf(java.time.LocalDate.now()));
			
			
			int rows=pst.executeUpdate();
			
			if(rows>0) {
				inserted=true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return inserted;
	}
	
	
	public ResultSet getAllPatients() {
		
		ResultSet rs = null;
		
		try {
			
			String query="SELECT token_number,patient_name,status from patients WHERE visit_date = CURDATE()";
			
			PreparedStatement pst=con.prepareStatement(query);
			
			rs=pst.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet getPatientsByDoctor(int doctorId) {
		
		ResultSet rs = null;
		
		try {
			
					String query =
				    "SELECT token_number, patient_name, status " +
				    "FROM patients " +
				    "WHERE doctor_id=? AND visit_date = CURDATE()";		
					PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, doctorId);
			rs = pst.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean callNextPatient(int doctorId) {
		boolean updated = false;
		
		try {
			String completeQuery = "UPDATE patients SET status = 'COMPLETED' WHERE doctor_id = ? AND status = 'SERVING' ";
			PreparedStatement pst1 = con.prepareStatement(completeQuery);
			pst1.setInt(1, doctorId);
			pst1.executeUpdate();
			
			String query = "UPDATE patients SET status = 'SERVING' WHERE doctor_id=? AND status = 'WAITING' ORDER BY token_number ASC LIMIT 1";
			PreparedStatement pst2= con.prepareStatement(query);
			pst2.setInt(1, doctorId);
			
			int rows=pst2.executeUpdate();
			
			if(rows>0) {
				updated = true;
				System.out.println("Next patient Called");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
	public boolean completeCurrentPatient(int doctorId) {
		
		boolean updated = false ;
		
		try {
			
			String query = "UPDATE patients SET status = 'COMPLETED' WHERE doctor_id = ? AND status = 'SERVING' ";
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setInt(1, doctorId);
			
			int rows=pst.executeUpdate();
			
			if(rows>0) {
				updated = true;
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return updated;
	}
	
	public ResultSet getPatientStatus(int tokenNumber) {
		
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT * FROM patients WHERE token_number = ? AND visit_date = CURDATE()";
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, tokenNumber);
			rs = pst.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int getQueuePosition(int tokenNumber,int doctorId) {
		int position = 1;
		
		try {
			
			String query = "SELECT COUNT(*) FROM patients WHERE doctor_id = ? AND status = 'WAITING' AND visit_date = CURDATE() AND token_number<?";
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, doctorId);
			pst.setInt(2, tokenNumber);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				position = rs.getInt(1)+1;
		}
	}catch(Exception e) {
		
		e.printStackTrace();
		
	}
		return position;
	}
	
	
	public int getTotalPatients() {
		
		int count = 0 ;
		try {
			
			String query ="SELECT COUNT(*) FROM patients";
			
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
	
	public int getTodayTotalPatients() {
		
		int count = 0 ;
		try {
			
			String query ="SELECT COUNT(*) FROM patients WHERE visit_date=CURDATE()";
			
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
	
	public int getTodayWaitingPatients() {
		
		int count = 0 ;
		
		try {
			String query = "SELECT COUNT(*) FROM patients WHERE status = 'WAITING' AND visit_date=CURDATE() ";
			
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return count ;
		
		
	}
	
	public int getTodayServingPatients() {
		
		int count = 0;
		
		try {
			
			String query = "SELECT COUNT(*) FROM patients WHERE status = 'SERVING' AND visit_date=CURDATE()";
			
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return count ;
	}
	
	public int getTodayCompletedPatients() {
		
		int count = 0;
		
		try {
			
			String query = "SELECT COUNT(*) FROM patients WHERE status = 'COMPLETED' AND visit_date=CURDATE()";
			
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return count ;
	}
	
	public ResultSet searchPatientByName(String patientName) {
		
		ResultSet rs = null;
		
		try {
			String query = "SELECT * FROM patients WHERE patient_name LIKE ?";
			
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1, "%"+patientName+"%");
			
			rs = pst.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	
	public ResultSet getPatientHistory(String patientName) {
		
		ResultSet rs = null;
		
		try {
			
			String query = "SELECT patient_name,visit_date,doctor_id,token_number,status FROM patients WHERE patient_name LIKE ? ORDER BY visit_date DESC";
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "%" + patientName +"%");
			
			rs = pst.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
}
