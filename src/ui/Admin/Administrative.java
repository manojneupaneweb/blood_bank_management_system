package ui.Auth;

import javax.swing.*;

import ui.Auth.LoginPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrative extends JFrame {

    private String currentRole; // To determine the role of the logged-in user

    public Administrative(String role) {
        this.currentRole = role; // The role is passed when the Admin Panel is opened

        setTitle("Admin Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(236, 240, 241));

        // Top panel with user info
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(new Color(52, 152, 219));

        JLabel usernameLabel = new JLabel("Logged in as: " + currentRole);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);

        topPanel.add(usernameLabel);
        topPanel.add(logoutButton);

        // Center panel for different features based on roles
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10));
        centerPanel.setBackground(new Color(236, 240, 241));

        // Common buttons
        JButton reportsButton = new JButton("Reports");
        JButton settingsButton = new JButton("Settings");
        JButton bloodStockButton = new JButton("Blood Stock Management");

        // Administrator only buttons
        JButton userManagementButton = new JButton("User Management");
        JButton roleAssignmentButton = new JButton("Role Assignment");
        JButton dataBackupButton = new JButton("Data Backup");
        JButton auditLogsButton = new JButton("Audit Logs");

        // Add buttons based on the current role
        if (currentRole.equals("Administrator")) {
            centerPanel.add(userManagementButton);
            centerPanel.add(roleAssignmentButton);
            centerPanel.add(dataBackupButton);
            centerPanel.add(auditLogsButton);
        }

        // Common buttons for all roles
        centerPanel.add(reportsButton);
        centerPanel.add(settingsButton);
        centerPanel.add(bloodStockButton);

        // Add panels to frame
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);

        // Logout button action
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close admin panel
                new LoginPage(); // Return to login page
            }
        });

        // Reports button action (for all roles)
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Accessing Reports...");
                // Add code for reports functionality
            }
        });

        // Settings button action (for all roles)
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Accessing Settings...");
                // Add code for settings functionality
            }
        });

        // Blood Stock Management button action (for all roles)
        bloodStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Accessing Blood Stock Management...");
                // Add code for blood stock management functionality
            }
        });

        // User Management button action (Administrator only)
        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Managing Users...");
                // Add code for managing users
            }
        });

        // Role Assignment button action (Administrator only)
        roleAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Assigning Roles...");
                // Add code for assigning roles
            }
        });

        // Data Backup button action (Administrator only)
        dataBackupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Performing Data Backup...");
                // Add code for data backup
            }
        });

        // Audit Logs button action (Administrator only)
        auditLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Viewing Audit Logs...");
                // Add code for viewing audit logs
            }
        });
    }

    public static void main(String[] args) {
        new Administrative("Administrator"); // Pass role as 'Administrator' for testing
    }
}
