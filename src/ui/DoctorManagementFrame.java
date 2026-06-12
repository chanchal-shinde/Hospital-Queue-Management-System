package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.DoctorDAO;
import model.Doctor;
import java.awt.*;
import java.util.ArrayList;

public class DoctorManagementFrame extends JFrame{
	
	JTable table;
	DefaultTableModel model;
	JScrollPane scrollPane;
	
	DoctorDAO doctorDAO;
	
	public DoctorManagementFrame() {
		
		doctorDAO = new DoctorDAO();
		
		setTitle("Manage Doctors");
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JLabel lblTitle = new JLabel("DOCTOR MANAGEMENT");
		lblTitle.setFont(new Font("Arial",Font.BOLD,22));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitle,BorderLayout.NORTH);
		
		model = new DefaultTableModel();
		
		model.addColumn("Doctor ID");
		model.addColumn("Doctor Name");
		model.addColumn("Specialization");
		
		table = new JTable(model);
		
		scrollPane = new JScrollPane(table);
		add(scrollPane , BorderLayout.CENTER);
		loadDoctors();
		
		setVisible(true);
	}
	
	public void loadDoctors() {
		
		try {
			ArrayList<Doctor> doctorList = doctorDAO.getAllDoctors();
			
			for(Doctor doctor : doctorList) {
				model.addRow(new Object[] {
						doctor.getDoctorId(),
						doctor.getDoctorName(),
						doctor.getSpecialization()
				});
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
