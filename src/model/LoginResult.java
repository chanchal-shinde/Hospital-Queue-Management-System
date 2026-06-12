package model;

public class LoginResult {
	
	private String role;
	private int doctorId;
	
	public LoginResult(String role,int doctorId) {
		
		this.role=role;
		this.doctorId=doctorId;
	}
	
	public String getRole() {
		return role;
	}
	
	public int getDoctorId() {
		return doctorId;
	}

}
