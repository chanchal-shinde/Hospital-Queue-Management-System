package ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class AdminDashboard extends JFrame implements ActionListener {

    JButton btnAddDoctor;
    JButton btnViewDoctors;
    JButton btnViewUsers;
    JButton btnStatistics;
    JButton btnPatientHistory;

    JLabel lblTitle;

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20));

        add(mainPanel);

        // Title

        lblTitle = new JLabel("ADMIN DASHBOARD");

        lblTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30));

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        mainPanel.add(
                lblTitle,
                BorderLayout.NORTH);

        // Center Panel

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(
                new GridLayout(
                        5,
                        1,
                        15,
                        15));

        // Buttons

        btnAddDoctor =
                new JButton("Add Doctor");

        btnViewDoctors =
                new JButton("View Doctors");

        btnViewUsers =
                new JButton("View Users");

        btnStatistics =
                new JButton("Statistics");

        btnPatientHistory =
                new JButton("Patient History");

        Font buttonFont =
                new Font(
                        "Arial",
                        Font.BOLD,
                        18);

        btnAddDoctor.setFont(buttonFont);
        btnViewDoctors.setFont(buttonFont);
        btnViewUsers.setFont(buttonFont);
        btnStatistics.setFont(buttonFont);
        btnPatientHistory.setFont(buttonFont);

        buttonPanel.add(btnAddDoctor);
        buttonPanel.add(btnViewDoctors);
        buttonPanel.add(btnViewUsers);
        buttonPanel.add(btnStatistics);
        buttonPanel.add(btnPatientHistory);

        JPanel wrapperPanel = new JPanel(
                new GridBagLayout());

        wrapperPanel.add(buttonPanel);

        mainPanel.add(
                wrapperPanel,
                BorderLayout.CENTER);

        // Action Listeners

        btnAddDoctor.addActionListener(this);
        btnViewDoctors.addActionListener(this);
        btnViewUsers.addActionListener(this);
        btnStatistics.addActionListener(this);
        btnPatientHistory.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAddDoctor) {

            new AddDoctorFrame();
        }

        else if (e.getSource() == btnViewDoctors) {

            new DoctorManagementFrame();
        }

        else if (e.getSource() == btnViewUsers) {

            new UserManagementFrame();
        }

        else if (e.getSource() == btnStatistics) {

            new StatisticsFrame();
        }

        else if (e.getSource() == btnPatientHistory) {

            new PatientHistoryFrame();
        }
    }
}