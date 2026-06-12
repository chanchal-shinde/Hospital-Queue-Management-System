package ui;

import dao.UserDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class UserManagementFrame extends JFrame{
	
	JTable table;
	DefaultTableModel model;
	
	UserDAO userDAO;
	
	public UserManagementFrame() {
		
		userDAO = new UserDAO();
		
		setTitle("User Management");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800,500);
		
		
		model = new DefaultTableModel();
		
		model.addColumn("User ID");
		model.addColumn("Username");
		model.addColumn("Role");
		model.addColumn("Doctor ID");
		
		table = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		add(scrollPane);
		loadUsers();
		setVisible(true);
	}
	
	public void loadUsers() {
		
		try {
			
			ResultSet rs = userDAO.getAllUsers();
			
			while(rs.next()) {
				
				int userId = rs.getInt("user_id");
				String username = rs.getString("username");
				String role = rs.getString("role");
				int doctorId = rs.getInt("doctor_id");
				
				model.addRow(new Object[] { userId,
											username,
											role,
											doctorId
				});
			}
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}

}
