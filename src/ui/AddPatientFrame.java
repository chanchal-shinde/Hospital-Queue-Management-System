package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import dao.PatientDAO;
import dao.DoctorDAO;

import model.Patient;
import model.Doctor;

import java.util.ArrayList;

public class AddPatientFrame extends JFrame implements ActionListener {

    JLabel lblTitle;
    JLabel lblName;
    JLabel lblAge;
    JLabel lblGender;
    JLabel lblDoctor;
    JLabel lblPhone;

    JTextField txtName;
    JTextField txtAge;
    JTextField txtPhone;

    JComboBox<String> comboGender;
    JComboBox<String> comboDoctor;

    JButton btnSave;

    JPanel mainPanel;
    JPanel formPanel;
    JPanel buttonPanel;

    PatientDAO patientDAO;
    DoctorDAO doctorDAO;

    ArrayList<Doctor> doctorList;

    public AddPatientFrame() {

        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();

        setTitle("Add Patient");

        setSize(500,500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // MAIN PANEL

        mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout(10,10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20,20,20,20)
        );

        // TITLE

        lblTitle = new JLabel("ADD PATIENT FORM");

        lblTitle.setFont(
                new Font("Arial",Font.BOLD,24)
        );

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        mainPanel.add(lblTitle,BorderLayout.NORTH);

        // FORM PANEL

        formPanel = new JPanel();

        formPanel.setLayout(
                new GridLayout(5,2,10,15)
        );

        lblName = new JLabel("Patient Name");
        lblAge = new JLabel("Age");
        lblPhone = new JLabel("Phone Number");
        lblGender = new JLabel("Gender");
        lblDoctor = new JLabel("Doctor");

        txtName = new JTextField();
        txtAge = new JTextField();
        txtPhone = new JTextField();

        comboGender = new JComboBox<>();

        comboGender.addItem("Male");
        comboGender.addItem("Female");

        // DOCTOR COMBOBOX

        comboDoctor = new JComboBox<>();

        doctorList = doctorDAO.getAllDoctors();

        for(Doctor doctor : doctorList) {

            comboDoctor.addItem(
                    doctor.getDoctorName()
            );
        }

        // ADD COMPONENTS

        formPanel.add(lblName);
        formPanel.add(txtName);

        formPanel.add(lblAge);
        formPanel.add(txtAge);

        formPanel.add(lblPhone);
        formPanel.add(txtPhone);

        formPanel.add(lblGender);
        formPanel.add(comboGender);

        formPanel.add(lblDoctor);
        formPanel.add(comboDoctor);

        mainPanel.add(formPanel,BorderLayout.CENTER);

        // BUTTON PANEL

        buttonPanel = new JPanel();

        btnSave = new JButton("Save Patient");

        btnSave.addActionListener(this);

        buttonPanel.add(btnSave);

        mainPanel.add(buttonPanel,BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

        	
        	if (txtName.getText().trim().isEmpty() ||
        			txtAge.getText().trim().isEmpty() || 
        			txtPhone.getText().trim().isEmpty()) {
        		
        		JOptionPane.showMessageDialog(this, "All Fields Are Requried");
        		return ;
        		
        	}
            String name = txtName.getText();
            if(!name.matches("[a-zA-Z ]{2,50}")) {
            	JOptionPane.showMessageDialog(this, "Name Must Only Letters");
            	return ;
            }

            int age;
            try { 
            	age = Integer.parseInt(txtAge.getText());
            }catch ( Exception ex) {
            	
            	JOptionPane.showMessageDialog(this, "Age Must Be Number");
            	return ;
            }
            
            if(age <0 || age>120 ) {
            	
            	JOptionPane.showMessageDialog(this, "Invalid Age");
            	return ;
            }
            String phone = txtPhone.getText();
            
            if(phone.length() != 10) {
            	
            	JOptionPane.showMessageDialog(this, "Phone Number Must Be 10 Digits");
            	return;
            	}
            if(!phone.matches("[0-9]+")) {
            	JOptionPane.showMessageDialog(this, "Phone Number Must Contain Only Digits");
            	return ;
            }

            String gender =
                    comboGender.getSelectedItem().toString();

            // GET SELECTED DOCTOR

            int selectedIndex =
                    comboDoctor.getSelectedIndex();

            Doctor selectedDoctor =
                    doctorList.get(selectedIndex);

            int doctorId =
                    selectedDoctor.getDoctorId();

            // CREATE PATIENT OBJECT

            Patient patient = new Patient(
                    name,
                    age,
                    gender,
                    doctorId,
                    phone
            );

            // INSERT PATIENT

            boolean inserted =
                    patientDAO.addPatient(patient);

            if(inserted) {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient Added Successfully"
                );

                // CLEAR FORM

                txtName.setText("");
                txtAge.setText("");
                txtPhone.setText("");

                comboGender.setSelectedIndex(0);
                comboDoctor.setSelectedIndex(0);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Add Patient"
                );
            }

        } catch(Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }
}