package ui.Admin;

import javax.swing.*;

import ui.Auth.LoginPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame {

    public AdminPanel() {
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

        JLabel userImage = new JLabel(new ImageIcon("user.png")); // Replace with actual image path
        JLabel usernameLabel = new JLabel("Admin");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.RED);
        logoutButton.setForeground(Color.WHITE);

        topPanel.add(userImage);
        topPanel.add(usernameLabel);
        topPanel.add(logoutButton);

        // Center panel for other components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        centerPanel.setBackground(new Color(236, 240, 241));

        JButton manageUsers = new JButton("Manage Users");
        JButton manageDonations = new JButton("Manage Donations");
        JButton reports = new JButton("Reports");
        JButton settings = new JButton("Settings");

        centerPanel.add(manageUsers);
        centerPanel.add(manageDonations);
        centerPanel.add(reports);
        centerPanel.add(settings);

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
                new LoginPage(); // Return to login
            }
        });

        // Manage Users button action
        manageUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for managing users
                JOptionPane.showMessageDialog(null, "Manage Users clicked!");
            }
        });

        // Manage Donations button action
        manageDonations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for managing donations
                JOptionPane.showMessageDialog(null, "Manage Donations clicked!");
            }
        });

        // Reports button action
        reports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for viewing reports
                JOptionPane.showMessageDialog(null, "Reports clicked!");
            }
        });

        // Settings button action
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for changing settings
                JOptionPane.showMessageDialog(null, "Settings clicked!");
            }
        });
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}
