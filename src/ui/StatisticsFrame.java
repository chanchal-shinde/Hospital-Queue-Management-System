package ui;

import javax.swing.*;
import java.awt.*;
import dao.PatientDAO;
import dao.DoctorDAO;
import dao.UserDAO;

public class StatisticsFrame extends JFrame {
	
	  	JLabel lblTotalPatients;
	  	JLabel lblTodayTotalPatients;
	    JLabel lblWaitingPatients;
	    JLabel lblServingPatients;
	    JLabel lblCompletedPatients;
	    JLabel lblTotalDoctors;
	    JLabel lblTotalUsers;
	    
public StatisticsFrame() {

	        setTitle("System Statistics");
	        setSize(400,300);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        setLayout(new GridLayout(6,1,10,10));

	        PatientDAO patientDAO = new PatientDAO();
	        DoctorDAO doctorDAO = new DoctorDAO();
	        UserDAO userDAO = new UserDAO();

	        lblTotalPatients =new JLabel("Total Patients : "+ patientDAO.getTotalPatients());
	        
	        lblTodayTotalPatients =new JLabel("Today's Total Patients : "+ patientDAO.getTodayTotalPatients());

	        lblWaitingPatients = new JLabel("Today's Waiting Patients : " + patientDAO.getTodayWaitingPatients());

	        lblServingPatients = new JLabel("Today's Serving Patients : " + patientDAO.getTodayServingPatients());

	        lblCompletedPatients =new JLabel("Today's Completed Patients : " + patientDAO.getTodayCompletedPatients());

	        lblTotalDoctors = new JLabel("Total Doctors : " + doctorDAO.getTotalDoctors());

	        lblTotalUsers =new JLabel("Total Users : " + userDAO.getTotalUsers());
	        
	        add(lblTotalPatients);
	        add(lblTodayTotalPatients);
	        add(lblWaitingPatients);
	        add(lblServingPatients);
	        add(lblCompletedPatients);
	        add(lblTotalDoctors);
	        add(lblTotalUsers);

	        setVisible(true);
	    }
	}

