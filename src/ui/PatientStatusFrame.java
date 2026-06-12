package ui;

import dao.PatientDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class PatientStatusFrame extends JFrame implements ActionListener {

    JLabel lblTitle;
    JLabel lblToken;

    JTextField txtToken;

    JButton btnCheck;

    JTextArea areaResult;

    PatientDAO patientDAO;

    public PatientStatusFrame() {

        patientDAO = new PatientDAO();

        setTitle("Patient Status");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // TITLE

        lblTitle = new JLabel("PATIENT STATUS TRACKER");

        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        add(lblTitle, BorderLayout.NORTH);

        // CENTER PANEL

        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(2,1,10,10));

        // TOKEN PANEL

        JPanel tokenPanel = new JPanel();

        lblToken = new JLabel("Enter Token : ");

        txtToken = new JTextField(10);

        btnCheck = new JButton("Check Status");

        btnCheck.addActionListener(this);

        tokenPanel.add(lblToken);
        tokenPanel.add(txtToken);
        tokenPanel.add(btnCheck);

        centerPanel.add(tokenPanel);

        // RESULT AREA

        areaResult = new JTextArea();

        areaResult.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(areaResult);

        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            int token = Integer.parseInt(txtToken.getText());

            ResultSet rs = patientDAO.getPatientStatus(token);

            if(rs.next()) {

                String name = rs.getString("patient_name");

                String status = rs.getString("status");

                int doctorId = rs.getInt("doctor_id");

                int position = patientDAO.getQueuePosition(token, doctorId);

                if(status.equals("WAITING")) {

                    areaResult.setText(
                            "Patient Name : " + name +
                            "\nDoctor ID : " + doctorId +
                            "\nStatus : " + status +
                            "\nQueue Position : " + position
                    );
                }

                else if(status.equals("SERVING")) {

                    areaResult.setText(
                            "Patient Name : " + name +
                            "\nDoctor ID : " + doctorId +
                            "\nStatus : CURRENTLY SERVING"
                    );
                }

                else if(status.equals("COMPLETED")) {

                    areaResult.setText(
                            "Patient Name : " + name +
                            "\nDoctor ID : " + doctorId +
                            "\nStatus : CONSULTATION COMPLETED"
                    );
                }

            }

            else {

                areaResult.setText("Invalid Token Number");
            }

        }

        catch(Exception ex) {

            ex.printStackTrace();
        }
    }
}