package ui.Auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPage extends JFrame implements ActionListener {
    // Form components
    private JLabel nameLabel, contactLabel, emailLabel, bloodGroupLabel, healthStatusLabel;
    private JTextField nameField, contactField, emailField;
    private JComboBox<String> bloodGroupCombo;
    private JCheckBox healthStatusCheck;
    private JButton submitButton, resetButton;

    public RegistrationPage() {
        // Set up the JFrame
        setTitle("Donor Registration");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Make the window full-screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for better organization

        // Create a main panel with GridBagLayout for flexibility
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Initialize components
        nameLabel = new JLabel("Name:");
        contactLabel = new JLabel("Contact Number:");
        emailLabel = new JLabel("Email:");
        bloodGroupLabel = new JLabel("Blood Group:");
        healthStatusLabel = new JLabel("Healthy (Eligible to Donate):");

        nameField = new JTextField(20);
        contactField = new JTextField(20);
        emailField = new JTextField(20);

        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        bloodGroupCombo = new JComboBox<>(bloodGroups);

        healthStatusCheck = new JCheckBox();

        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");

        // Add components to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(contactLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(bloodGroupLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(bloodGroupCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(healthStatusLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(healthStatusCheck, gbc);

        // Add buttons to a separate panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        // Add main panel and button panel to the JFrame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        submitButton.addActionListener(this);
        resetButton.addActionListener(this);

        // Display the JFrame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Handle form submission (UI only, no database)
            String name = nameField.getText();
            String contact = contactField.getText();
            String email = emailField.getText();
            String bloodGroup = (String) bloodGroupCombo.getSelectedItem();
            boolean isHealthy = healthStatusCheck.isSelected();

            // Display a confirmation message
            String message = "Donor Details:\n" +
                    "Name: " + name + "\n" +
                    "Contact: " + contact + "\n" +
                    "Email: " + email + "\n" +
                    "Blood Group: " + bloodGroup + "\n" +
                    "Healthy: " + (isHealthy ? "Yes" : "No");

            JOptionPane.showMessageDialog(this, message, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else if (e.getSource() == resetButton) {
            // Clear the form
            clearForm();
        }
    }

    private void clearForm() {
        nameField.setText("");
        contactField.setText("");
        emailField.setText("");
        bloodGroupCombo.setSelectedIndex(0);
        healthStatusCheck.setSelected(false);
    }

    public static void main(String[] args) {
        // Run the registration page
        new RegistrationPage();
    }
}