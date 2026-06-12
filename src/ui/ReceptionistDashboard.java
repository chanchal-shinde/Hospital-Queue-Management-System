package ui;

import dao.PatientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class ReceptionistDashboard extends JFrame implements ActionListener {

    JLabel lblTitle;

    JButton btnAddPatient;
    JButton btnRefresh;
    JButton btnSearch;
    JButton btnHistory;

    JTextField txtSearch;

    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;

    PatientDAO patientDAO;

    JLabel lblTotalPatients;
    JLabel lblTodayTotalPatients;
    JLabel lblWaitingPatients;
    JLabel lblServingPatients;
    JLabel lblCompletedPatients;

    JPanel mainPanel;

    public ReceptionistDashboard() {

        patientDAO = new PatientDAO();

        setTitle("Receptionist Dashboard");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15));

        add(mainPanel);

        //---------------- TITLE ----------------//

        lblTitle = new JLabel("RECEPTIONIST DASHBOARD");

        lblTitle.setFont(
                new Font("Arial",
                        Font.BOLD,
                        28));

        lblTitle.setHorizontalAlignment(
                SwingConstants.CENTER);

        JPanel titlePanel = new JPanel();

        titlePanel.add(lblTitle);

        //---------------- BUTTONS ----------------//

        btnAddPatient = new JButton("Add Patient");
        btnRefresh = new JButton("Refresh");
        btnSearch = new JButton("Search");
        btnHistory = new JButton("Patient History");

        btnAddPatient.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnSearch.addActionListener(this);
        btnHistory.addActionListener(this);

        btnAddPatient.setFocusPainted(false);
        btnRefresh.setFocusPainted(false);
        btnSearch.setFocusPainted(false);
        btnHistory.setFocusPainted(false);

        txtSearch = new JTextField(15);

        JPanel controlPanel = new JPanel();

        controlPanel.add(btnAddPatient);
        controlPanel.add(btnHistory);
        controlPanel.add(btnRefresh);

        controlPanel.add(new JLabel("Search Patient:"));

        controlPanel.add(txtSearch);
        controlPanel.add(btnSearch);

        //---------------- NORTH PANEL ----------------//

        JPanel northPanel = new JPanel(new BorderLayout());

        northPanel.add(titlePanel, BorderLayout.NORTH);
        northPanel.add(controlPanel, BorderLayout.SOUTH);

        mainPanel.add(northPanel, BorderLayout.NORTH);

        //---------------- TABLE ----------------//

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

        scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //---------------- STATISTICS ----------------//

        lblTotalPatients = new JLabel();
        lblTodayTotalPatients = new JLabel();
        lblWaitingPatients = new JLabel();
        lblServingPatients = new JLabel();
        lblCompletedPatients = new JLabel();

        lblTotalPatients.setBorder(
                BorderFactory.createEtchedBorder());

        lblTodayTotalPatients.setBorder(
                BorderFactory.createEtchedBorder());

        lblWaitingPatients.setBorder(
                BorderFactory.createEtchedBorder());

        lblServingPatients.setBorder(
                BorderFactory.createEtchedBorder());

        lblCompletedPatients.setBorder(
                BorderFactory.createEtchedBorder());

        JPanel statsPanel = new JPanel();

        statsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Queue Statistics"));

        statsPanel.setLayout(
                new GridLayout(1, 5, 10, 10));

        statsPanel.add(lblTotalPatients);
        statsPanel.add(lblTodayTotalPatients);
        statsPanel.add(lblWaitingPatients);
        statsPanel.add(lblServingPatients);
        statsPanel.add(lblCompletedPatients);

        mainPanel.add(statsPanel, BorderLayout.SOUTH);

        //---------------- LOAD DATA ----------------//

        loadPatients();
        loadStatistics();

        setVisible(true);
    }

    public void loadPatients() {

        try {

            model.setRowCount(0);

            ResultSet rs = patientDAO.getAllPatients();

            while (rs.next()) {

                int token = rs.getInt("token_number");
                String name = rs.getString("patient_name");
                String status = rs.getString("status");

                model.addRow(
                        new Object[]{
                                token,
                                name,
                                status
                        });
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAddPatient) {

            new AddPatientFrame();
        }

        else if (e.getSource() == btnRefresh) {

            loadDashboardData();
        }

        else if (e.getSource() == btnSearch) {

            String name = txtSearch.getText();

            searchPatient(name);
        }

        else if (e.getSource() == btnHistory) {

            new PatientHistoryFrame();
        }
    }

    public void loadStatistics() {

        int total = patientDAO.getTotalPatients();

        int todayTotal =
                patientDAO.getTodayTotalPatients();

        int waiting =
                patientDAO.getTodayWaitingPatients();

        int serving =
                patientDAO.getTodayServingPatients();

        int completed =
                patientDAO.getTodayCompletedPatients();

        lblTotalPatients.setText(
                "Total : " + total);

        lblTodayTotalPatients.setText(
                "Today's Total: " + todayTotal);

        lblWaitingPatients.setText(
                "Waiting : " + waiting);

        lblServingPatients.setText(
                "Serving : " + serving);

        lblCompletedPatients.setText(
                "Completed : " + completed);
    }

    public void loadDashboardData() {

        loadPatients();
        loadStatistics();
    }

    public void searchPatient(String name) {

        try {

            model.setRowCount(0);

            ResultSet rs =
                    patientDAO.searchPatientByName(name);

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                rs.getInt("token_number"),
                                rs.getString("patient_name"),
                                rs.getString("status")
                        });
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}