package ui.Auth;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bloodlink";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JToggleButton showPasswordBtn;
    private JButton loginButton;

    public LoginPage() {
        setTitle("Blood Link - Login");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with two sections
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // Left panel - Login Form
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // App logo and title
        JLabel titleLabel = new JLabel("Blood Link");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(220, 20, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(60, 60, 60));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form components
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Username field
        usernameField = createStyledTextField("Username or Email");

        // Password field with eye button
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordField = createStyledPasswordField("Password");
        showPasswordBtn = createEyeButton(passwordField);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(showPasswordBtn, BorderLayout.EAST);

        // Login button
        loginButton = new JButton("Login");
        styleButton(loginButton);

        // Add components to form panel
        formPanel.add(usernameField);
        formPanel.add(passwordPanel);
        formPanel.add(loginButton);

        // Sign up link panel
        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signUpPanel.setBackground(Color.WHITE);
        JLabel signUpLink = new JLabel("Don't have an account? Sign Up");
        signUpLink.setForeground(new Color(220, 20, 20));
        signUpLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpPanel.add(signUpLink);

        // Add components to left panel
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        leftPanel.add(formPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(signUpPanel);

        // Right panel - Image
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Create a gradient background
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(220, 20, 20), 
                    getWidth(), getHeight(), new Color(180, 20, 20));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add blood donation themed graphics
                g2d.setColor(Color.WHITE);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                // Draw a heart shape
                g2d.setStroke(new BasicStroke(3));
                drawHeart(g2d, centerX, centerY - 50, 100);
                
                // Add text
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("Give the Gift of Life", centerX - 100, centerY + 100);
                g2d.setFont(new Font("Arial", Font.PLAIN, 18));
                g2d.drawString("Donate Blood Today", centerX - 80, centerY + 130);
            }
        };

        // Add panels to main panel
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        
        // Add signup link click listener
        signUpLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openSignupPage();
            }
        });

        // Add main panel to frame
        add(mainPanel);
    }

    private void openSignupPage() {
        SwingUtilities.invokeLater(() -> {
            SignupPage signupPage = new SignupPage();
            signupPage.setVisible(true);
            this.dispose(); // Close the login window
        });
    }

    private JToggleButton createEyeButton(JPasswordField passwordField) {
        JToggleButton eyeButton = new JToggleButton("\uD83D\uDC41");  // Unicode eye symbol
        eyeButton.setBackground(Color.WHITE);
        eyeButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        eyeButton.setFocusPainted(false);
        eyeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        eyeButton.addActionListener(e -> {
            if (eyeButton.isSelected()) {
                passwordField.setEchoChar((char) 0);  // Show password
            } else {
                passwordField.setEchoChar('•');  // Hide password
            }
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
        field.setEchoChar((char) 0);  // Make the text visible initially
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('•');  // Change to password dots when typing
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setEchoChar((char) 0);  // Show text again
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(220, 20, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 14));
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

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Basic validation
        if (username.isEmpty() || username.equals("Username or Email") ||
            password.isEmpty() || password.equals("Password")) {
            
            JOptionPane.showMessageDialog(this,
                "Please enter both username/email and password",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Prepare SQL statement to check both username and email
            String sql = "SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // Set parameters
            pstmt.setString(1, username);
            pstmt.setString(2, username);  // Check against email too
            pstmt.setString(3, password);
            
            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                // Here you can add code to open the main application window
                
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invalid username/email or password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            // Close resources
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Error during login: " + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}
