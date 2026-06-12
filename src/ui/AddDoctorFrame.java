package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import dao.DoctorDAO;
import dao.UserDAO;

public class AddDoctorFrame extends JFrame implements ActionListener {
	
	JLabel lblTitle;
	JLabel lblDoctorName;
	JLabel lblSpecialization;
	JLabel lblUsername;
	JLabel lblPassword;
	
	JTextField txtDoctorName;
	JTextField txtSpecialization;
	JTextField txtUsername;
	
	JPasswordField txtPassword;
	JButton btnSave;
	
	DoctorDAO doctorDAO ;
	UserDAO userDAO;
	
	public AddDoctorFrame() {
		
		doctorDAO = new DoctorDAO();
		userDAO = new UserDAO();
		
		setTitle("Add Doctor");
		setSize(450,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new GridLayout(6,2,10,10));
		
		
		JLabel lbl = new JLabel("ADD NEW DOCTOR");
		
		lblDoctorName = new JLabel("Doctor Name");
		lblSpecialization = new JLabel("Specialization");
		lblUsername = new JLabel("Username");
		lblPassword = new JLabel("Password");
		
		txtDoctorName = new JTextField();
		txtSpecialization = new JTextField();
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		
		btnSave= new JButton("Save");
		btnSave.addActionListener(this);
		
		add(lblDoctorName);
		add(txtDoctorName);
		add(lblSpecialization);
		add(txtSpecialization);
		add(lblUsername);
		add(txtUsername);
		add(lblPassword);
		add(txtPassword);
		
		add(new JLabel());
		add(btnSave);
		
		setVisible(true);
	}
	
	
	@Override public void actionPerformed(ActionEvent e) {
		
		String doctorName = txtDoctorName.getText();
		String specialization = txtSpecialization.getText();
		String username = txtUsername.getText();
		String password = String.valueOf(txtPassword.getPassword());
		
		int doctorId = doctorDAO.addDoctor(doctorName, specialization);
		
		if(doctorId != -1) {
			
			boolean userCreated = userDAO.addDoctorUser(username, password, doctorId);
			
			if(userCreated) {
				JOptionPane.showMessageDialog(this, "Doctor Added Successfully");
				dispose();
		}
			else {
				JOptionPane.showMessageDialog(this, "Doctor Added But User Creation Failed");
				}
			}
		else {
			
			JOptionPane.showMessageDialog(this, "Failed To Add Doctor");
		}
	}
	

}
