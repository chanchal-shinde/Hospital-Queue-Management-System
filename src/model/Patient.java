package model;

public class Patient {
	
	private String name;
	private int age;
	private String gender;
	private int doctorId;
	private String phone;
	
	public Patient() {
		
	}
	public Patient(String name,int age,String gender,int doctorId,String phone) {
		this.name=name;
		this.age=age;
		this.gender=gender;
		this.doctorId=doctorId;
		this.phone=phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String Name) {
		this.name=name; 
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age=age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender=gender;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctor(int doctorId) {
		this.doctorId=doctorId;
	}
	public String getPhone() {
		return phone;
	} 
	public void setPhone(String phone) {
		this.phone=phone;
	}

}
