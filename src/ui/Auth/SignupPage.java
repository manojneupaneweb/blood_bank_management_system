package ui.Auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SignupPage extends JFrame {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bloodlink";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // UI Components
    private JTextField nameField, usernameField, emailField, contactField;
    private JPasswordField passwordField, confirmPasswordField;
    private JToggleButton showPasswordBtn, showConfirmPasswordBtn;
    private JComboBox<String> bloodGroupCombo;
    private JComboBox<String> userTypeCombo;
    private JButton signupButton;

    // Validation Labels
    private JLabel usernameValidationLabel;
    private JLabel emailValidationLabel;

    // Validation Status
    private boolean isUsernameValid = false;
    private boolean isEmailValid = false;

    public SignupPage() {
        setTitle("Blood Link - Sign Up");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with two sections
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Left panel - Signup Form
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // App logo and title
        JLabel titleLabel = new JLabel("Blood Link");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(220, 20, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form components
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 1, 10, 15));  // Added one more row for user type
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // User Type Selection with updated styling
        JPanel userTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userTypePanel.setBackground(Color.WHITE);
        JLabel userTypeLabel = new JLabel("Register as: ");
        userTypeLabel.setForeground(Color.BLACK);
        String[] userTypes = {"Individual", "Organization"};
        userTypeCombo = new JComboBox<>(userTypes);
        userTypeCombo.setBackground(Color.WHITE);
        userTypeCombo.setForeground(Color.BLACK);
        userTypeCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        ((JComponent) userTypeCombo.getRenderer()).setForeground(Color.BLACK);
        userTypePanel.add(userTypeLabel);
        userTypePanel.add(userTypeCombo);

        nameField = createStyledTextField("Full Name");

        JPanel usernamePanel = new JPanel(new BorderLayout(5, 2));
        usernamePanel.setBackground(Color.WHITE);
        usernameField = createStyledTextField("Username");
        usernameValidationLabel = new JLabel("");
        usernameValidationLabel.setForeground(Color.RED);
        usernameValidationLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        usernamePanel.add(usernameValidationLabel, BorderLayout.SOUTH);

        JPanel emailPanel = new JPanel(new BorderLayout(5, 2));
        emailPanel.setBackground(Color.WHITE);
        emailField = createStyledTextField("Email");
        emailValidationLabel = new JLabel("");
        emailValidationLabel.setForeground(Color.RED);
        emailValidationLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailPanel.add(emailField, BorderLayout.CENTER);
        emailPanel.add(emailValidationLabel, BorderLayout.SOUTH);

        addValidationListener(usernameField, "username", usernameValidationLabel);
        addValidationListener(emailField, "email", emailValidationLabel);

        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordField = createStyledPasswordField("Password");
        showPasswordBtn = createEyeButton(passwordField);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordBtn, BorderLayout.EAST);

        JPanel confirmPasswordPanel = new JPanel(new BorderLayout(5, 0));
        confirmPasswordPanel.setBackground(Color.WHITE);
        confirmPasswordField = createStyledPasswordField("Confirm Password");
        showConfirmPasswordBtn = createEyeButton(confirmPasswordField);
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);
        confirmPasswordPanel.add(showConfirmPasswordBtn, BorderLayout.EAST);

        contactField = createStyledTextField("Contact Number");

        String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        bloodGroupCombo = new JComboBox<>(bloodGroups);
        bloodGroupCombo.setBackground(Color.WHITE);
        bloodGroupCombo.setForeground(Color.GRAY);
        bloodGroupCombo.setFont(new Font("Arial", Font.PLAIN, 12));
        ((JComponent) bloodGroupCombo.getRenderer()).setForeground(Color.GRAY);
        bloodGroupCombo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(220, 20, 20));
        styleButton(signupButton);

        // Add components to form panel
        formPanel.add(userTypePanel);
        formPanel.add(nameField);
        formPanel.add(usernamePanel);
        formPanel.add(emailPanel);
        formPanel.add(passwordPanel);
        formPanel.add(confirmPasswordPanel);
        formPanel.add(contactField);
        formPanel.add(bloodGroupCombo);
        formPanel.add(signupButton);

        // Change form based on user type selection
        userTypeCombo.addActionListener(e -> {
            boolean isIndividual = userTypeCombo.getSelectedItem().equals("Individual");
            nameField.setText(isIndividual ? "Full Name" : "Organization Name");
            bloodGroupCombo.setVisible(isIndividual);
            formPanel.revalidate();
            formPanel.repaint();
        });

        JPanel signInPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signInPanel.setBackground(Color.WHITE);
        JLabel signInLink = new JLabel("Already have an account? Sign In");
        signInLink.setForeground(new Color(220, 20, 20));
        signInLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signInPanel.add(signInLink);

        // Add components to left panel
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(formPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(signInPanel);

        // Right panel with gradient and heart
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(220, 20, 20),
                        getWidth(), getHeight(), new Color(180, 20, 20));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.WHITE);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;

                g2d.setStroke(new BasicStroke(3));
                drawHeart(g2d, centerX, centerY - 50, 100);

                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("Give the Gift of Life", centerX - 100, centerY + 100);
                g2d.setFont(new Font("Arial", Font.PLAIN, 18));
                g2d.drawString("Donate Blood Today", centerX - 80, centerY + 130);
            }
        };

        signInLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLoginPage();
            }
        });

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        signupButton.addActionListener(e -> handleSignup());

        add(mainPanel);
    }

    private void styleButton(JButton button) {
        // Updated button styling
        button.setBackground(new Color(220, 20, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        // Set preferred size for smaller button
        button.setPreferredSize(new Dimension(70, 35));
        // Center the button
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(180, 20, 20));  // Darker red on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(220, 20, 20));  // Original red
            }
        });
    }

    private void addValidationListener(JTextField field, String fieldType, JLabel validationLabel) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            private Timer timer = new Timer(500, e -> validateField(field.getText(), fieldType, validationLabel));

            @Override
            public void insertUpdate(DocumentEvent e) {
                timer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timer.restart();
            }
        });
    }

    private void validateField(String value, String fieldType, JLabel validationLabel) {
        if (value.isEmpty() || value.equals("Username") || value.equals("Email")) {
            validationLabel.setText("");
            if (fieldType.equals("username")) isUsernameValid = false;
            else isEmailValid = false;
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM users WHERE " + fieldType + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, value);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                validationLabel.setText(fieldType.substring(0, 1).toUpperCase() +
                        fieldType.substring(1) + " already exists");
                validationLabel.setForeground(Color.RED);
                if (fieldType.equals("username")) isUsernameValid = false;
                else isEmailValid = false;
            } else {
                validationLabel.setText(fieldType.substring(0, 1).toUpperCase() +
                        fieldType.substring(1) + " is available");
                validationLabel.setForeground(new Color(0, 150, 0));
                if (fieldType.equals("username")) isUsernameValid = true;
                else isEmailValid = true;
            }
        } catch (SQLException ex) {
            validationLabel.setText("Error checking " + fieldType);
            validationLabel.setForeground(Color.RED);
            if (fieldType.equals("username")) isUsernameValid = false;
            else isEmailValid = false;
        }
    }

    private JToggleButton createEyeButton(JPasswordField passwordField) {
        JToggleButton eyeButton = new JToggleButton("👁️");
        eyeButton.setBackground(Color.WHITE);
        eyeButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        eyeButton.setFocusPainted(false);
        eyeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        eyeButton.addActionListener(e -> {
            if (eyeButton.isSelected()) passwordField.setEchoChar((char) 0);
            else passwordField.setEchoChar('•');
        });

        return eyeButton;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(200, 35));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setPreferredSize(new Dimension(200, 35));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private void drawHeart(Graphics2D g2d, int x, int y, int size) {
        int[] xPoints = new int[20];
        int[] yPoints = new int[20];
        double angle;

        for (int i = 0; i < 20; i++) {
            angle = i * 2 * Math.PI / 20;
            xPoints[i] = (int) (x + size * (16 * Math.pow(Math.sin(angle), 3)) / 16);
            yPoints[i] = (int) (y - size * (13 * Math.cos(angle) - 5 * Math.cos(2 * angle)
                    - 2 * Math.cos(3 * angle) - Math.cos(4 * angle)) / 16);
        }

        g2d.drawPolygon(xPoints, yPoints, 20);
    }

    private void openLoginPage() {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
            this.dispose();
        });
    }

    private void handleSignup() {
        boolean isIndividual = userTypeCombo.getSelectedItem().equals("Individual");
        String nameFieldPlaceholder = isIndividual ? "Full Name" : "Organization Name";

        // Validation
        if (nameField.getText().isEmpty() || nameField.getText().equals(nameFieldPlaceholder) ||
                usernameField.getText().isEmpty() || usernameField.getText().equals("Username") ||
                emailField.getText().isEmpty() || emailField.getText().equals("Email") ||
                String.valueOf(passwordField.getPassword()).equals("Password") ||
                String.valueOf(confirmPasswordField.getPassword()).equals("Confirm Password") ||
                (isIndividual && bloodGroupCombo.getSelectedIndex() == 0) ||
                contactField.getText().isEmpty() || contactField.getText().equals("Contact Number")) {

            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Password match validation
        if (!String.valueOf(passwordField.getPassword())
                .equals(String.valueOf(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if username or email already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, usernameField.getText());
            checkStmt.setString(2, emailField.getText());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this,
                        "Username or Email already exists. Please choose another.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert new user
            String sql = "INSERT INTO users (name, username, email, password, blood_group, contact, user_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nameField.getText());
            pstmt.setString(2, usernameField.getText());
            pstmt.setString(3, emailField.getText());
            pstmt.setString(4, String.valueOf(passwordField.getPassword()));
            pstmt.setString(5, isIndividual ? bloodGroupCombo.getSelectedItem().toString() : null);
            pstmt.setString(6, contactField.getText());
            pstmt.setString(7, isIndividual ? "individual" : "organization");

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Registration successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Close resources
            pstmt.close();
            rs.close();
            checkStmt.close();
            conn.close();

            // Redirect to login page
            openLoginPage();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Database Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the application
        SwingUtilities.invokeLater(() -> {
            SignupPage signupPage = new SignupPage();
            signupPage.setVisible(true);
        });
    }
}