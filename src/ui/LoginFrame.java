package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UserDAO;
import ui.ReceptionistDashboard;
import ui.DoctorDashboard;
import model.LoginResult;

public class LoginFrame extends JFrame implements ActionListener {

    JLabel lblTitle;
    JLabel lblUsername;
    JLabel lblPassword;

    JTextField txtUsername;

    JPasswordField txtPassword;

    JButton btnLogin;
    UserDAO userDAO;

    JPanel mainPanel;
    JPanel formPanel;
    JPanel buttonPanel;
    
    JButton btnPatientStatus;

    public LoginFrame() {

        setTitle("Hospital Queue System Login");

        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        userDAO = new UserDAO();

        // MAIN PANEL

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10,10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30,30,30,30)
        );

        // TITLE

        lblTitle = new JLabel("Hospital Queue System");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // FORM PANEL

        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2,2,10,10));
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // BUTTON PANEL

        btnPatientStatus = new JButton("Check Patent Status");
        btnPatientStatus.addActionListener(this);
        buttonPanel = new JPanel();
        btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(this);
        buttonPanel.add(btnLogin);
       buttonPanel. add(btnPatientStatus);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
         // ADD MAIN PANEL TO FRAME

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource() == btnLogin) {
    		
    		System.out.println("Login Button Clicked");
    		
    		String username=txtUsername.getText();
        	String password=String.valueOf(txtPassword.getPassword());
        	LoginResult result = userDAO.login(username, password);
        	
        	if(result!=null) {
        		
        		String role = result.getRole();
        		
        		JOptionPane.showMessageDialog(this, "Login Successful");
        		
        		if(role.equals("ADMIN")) {
        			new AdminDashboard();
        		}
        		else if(role.equals("RECEPTIONIST")) {
        			new ReceptionistDashboard();
        		}
        		else if(role.equals("DOCTOR")) {
        			new DoctorDashboard(result.getDoctorId());
        		}
        		
        		dispose();
        	}
        	else {
        		JOptionPane.showMessageDialog(this, "Invalid Username or password");
        	}
    		
    	}
    	
    	else if(e.getSource() == btnPatientStatus) {
    		new PatientStatusFrame();
    	}
    	
    }
}