 package ui;
 
 import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import dao.PatientDAO;
import dao.DoctorDAO;

public class PatientHistoryFrame extends JFrame implements ActionListener{
	
	JLabel lblTitle;
	JLabel lblName;
	
	JTextField txtName;
	
	JButton btnSearch;

	JTable table;
	DefaultTableModel model;
	JScrollPane scrollPane;

	PatientDAO patientDAO;
	DoctorDAO doctorDAO;
	
	JPanel topPanel;
	
	public PatientHistoryFrame() {
		
		patientDAO = new PatientDAO();
		doctorDAO = new DoctorDAO();
		
		setTitle("Patient History");
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		topPanel = new JPanel();
		
		lblTitle = new JLabel("PATIENT HISTORY");
		lblTitle.setFont(new Font("Arial",Font.BOLD,22));
		lblName = new JLabel("Patient Name");
		txtName = new JTextField(15);
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		
		topPanel.add(lblTitle);
		topPanel.add(lblName);
		topPanel.add(txtName);
		topPanel.add(btnSearch);
		
		add(topPanel,BorderLayout.NORTH);
		
		model = new DefaultTableModel();
		
		model.addColumn("Patient Name");
		model.addColumn("Visit Date");
		model.addColumn("Doctor Name");
		model.addColumn("Token");
		model.addColumn("Status");
		
		table = new JTable(model);
		
		scrollPane = new JScrollPane(table);
		
		add(scrollPane,BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnSearch) {
			searchHistory();
		}
	}
	
	public void searchHistory() {
		try {
			model.setRowCount(0);
			
			String patientName = txtName.getText();
			ResultSet rs = patientDAO.getPatientHistory(patientName);
			
			while(rs.next()) {
				
				
				String visitDate = rs.getDate("visit_date").toString();
				String patient_Name = rs.getString("patient_name");
				int doctorId = rs.getInt("doctor_id");
				String doctorName = doctorDAO.getDoctorNameById(doctorId);
				int token = rs.getInt("token_number");
				String status = rs.getString("status");
				
				model.addRow(new Object[] {
						patient_Name,
						visitDate,
						doctorName,
						token,
						status
				});
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}


