package ui;

import dao.DoctorDAO;
import dao.PatientDAO;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class DoctorDashboard extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;

    JLabel lblTitle;
    JLabel lblDoctorName;

    JLabel lblTodayPatients;
    JLabel lblWaitingPatients;
    JLabel lblServingPatients;
    JLabel lblCompletedPatients;

    PatientDAO patientDAO;
    DoctorDAO doctorDAO;

    JButton btnCallPatient;
    JButton btnCompletePatient;
    JButton btnRefresh;

    Timer refreshTimer;

    int doctorId;

    public DoctorDashboard(int doctorId) {

        this.doctorId = doctorId;

        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();

        setTitle("Doctor Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15));

        add(mainPanel);

        // ================= TITLE =================

        lblTitle = new JLabel("DOCTOR DASHBOARD");
        lblTitle.setFont(
                new Font("Arial",
                        Font.BOLD,
                        28));
        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        String doctorName =
                doctorDAO.getDoctorNameById(
                        doctorId);

        lblDoctorName =
                new JLabel(
                        "Logged in as : " +
                                doctorName);

        lblDoctorName.setFont(
                new Font("Arial",
                        Font.BOLD,
                        14));

        // ================= BUTTONS =================

        btnCallPatient =
                new JButton("Call Patient");

        btnCompletePatient =
                new JButton("Complete Patient");

        btnRefresh =
                new JButton("Refresh");

        btnCallPatient.addActionListener(this);
        btnCompletePatient.addActionListener(this);
        btnRefresh.addActionListener(this);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnCallPatient);
        buttonPanel.add(btnCompletePatient);
        buttonPanel.add(btnRefresh);

        // ================= TOP PANEL =================

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(lblDoctorName,
                BorderLayout.WEST);

        topPanel.add(lblTitle,
                BorderLayout.CENTER);

        topPanel.add(buttonPanel,
                BorderLayout.SOUTH);

        mainPanel.add(topPanel,
                BorderLayout.NORTH);

        // ================= TABLE =================

        model = new DefaultTableModel();

        model.addColumn("Token");
        model.addColumn("Patient Name");
        model.addColumn("Status");

        table = new JTable(model);

        table.setRowHeight(28);

        table.getTableHeader().setFont(
                new Font("Arial",
                        Font.BOLD,
                        14));

        scrollPane =
                new JScrollPane(table);

        mainPanel.add(scrollPane,
                BorderLayout.CENTER);

        // ================= STATISTICS =================

        lblTodayPatients =
                new JLabel();

        lblWaitingPatients =
                new JLabel();

        lblServingPatients =
                new JLabel();

        lblCompletedPatients =
                new JLabel();

        JPanel statsPanel =
                new JPanel();

        statsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Today's Statistics"));

        statsPanel.setLayout(
                new GridLayout(
                        2, 2, 10, 10));

        statsPanel.add(
                lblTodayPatients);

        statsPanel.add(
                lblWaitingPatients);

        statsPanel.add(
                lblServingPatients);

        statsPanel.add(
                lblCompletedPatients);

        mainPanel.add(
                statsPanel,
                BorderLayout.SOUTH);

        // ================= LOAD DATA =================

        loadPatients();
        loadStatistics();

        // ================= AUTO REFRESH =================

        refreshTimer =
                new Timer(
                        3000,
                        new ActionListener() {

                            @Override
                            public void actionPerformed(
                                    ActionEvent e) {

                                loadPatients();
                                loadStatistics();
                            }
                        });

        refreshTimer.start();

        setVisible(true);
    }

    public void loadPatients() {

        try {

            model.setRowCount(0);

            ResultSet rs =
                    patientDAO.getPatientsByDoctor(
                            doctorId);

            while (rs.next()) {

                int token =
                        rs.getInt(
                                "token_number");

                String name =
                        rs.getString(
                                "patient_name");

                String status =
                        rs.getString(
                                "status");

                model.addRow(
                        new Object[]{
                                token,
                                name,
                                status
                        });
            }

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void loadStatistics() {

        try {

            int total = 0;
            int waiting = 0;
            int serving = 0;
            int completed = 0;

            ResultSet rs =
                    patientDAO.getPatientsByDoctor(
                            doctorId);

            while (rs.next()) {

                total++;

                String status =
                        rs.getString(
                                "status");

                if (status.equals(
                        "WAITING")) {

                    waiting++;
                }

                else if (status.equals(
                        "SERVING")) {

                    serving++;
                }

                else if (status.equals(
                        "COMPLETED")) {

                    completed++;
                }
            }

            lblTodayPatients.setText(
                    "Today's Patients : "
                            + total);

            lblWaitingPatients.setText(
                    "Waiting : "
                            + waiting);

            lblServingPatients.setText(
                    "Serving : "
                            + serving);

            lblCompletedPatients.setText(
                    "Completed : "
                            + completed);

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(
            ActionEvent e) {

        if (e.getSource() ==
                btnCallPatient) {

            boolean updated =
                    patientDAO.callNextPatient(
                            doctorId);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Next Patient Called");

                loadPatients();
                loadStatistics();
            }

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "No Waiting Patients");
            }
        }

        else if (e.getSource() ==
                btnCompletePatient) {

            boolean updated =
                    patientDAO.completeCurrentPatient(
                            doctorId);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient Completed");

                loadPatients();
                loadStatistics();
            }

            else {

                JOptionPane.showMessageDialog(
                        this,
                        "No Serving Patient");
            }
        }

        else if (e.getSource() ==
                btnRefresh) {

            loadPatients();
            loadStatistics();
        }
    }
}