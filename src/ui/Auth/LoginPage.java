package ui.Auth;

import javax.swing.*;

import ui.Admin.AdminPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginPage() {
        setTitle("Blood Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Setting Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(44, 62, 80)); // Dark background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(52, 152, 219)); // Blue button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(46, 204, 113)); // Green button
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        add(panel);

        // Action Listener for Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Dummy validation (Replace with database validation)
                if (username.equals("admin") && password.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new AdminPanel(); // Close login window
                    // new AdminPanel(); // Uncomment this to open admin panel after login
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action Listener for Register Button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationPage();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage(); // Starts the login page
    }
}
