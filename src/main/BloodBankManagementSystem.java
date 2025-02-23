import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.*;
import javax.imageio.ImageIO;

public class BloodBankManagementSystem extends JFrame {
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(220, 53, 69);
    private static final Color SECONDARY_COLOR = new Color(248, 249, 250);
    private static final Color ACCENT_COLOR = new Color(33, 37, 41);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    
    // Theme variables
    private boolean darkMode = false;
    
    // Components
    private JPanel mainPanel;
    private JPanel signupPanel;
    private JPanel loginPanel;
    private JPanel dashboardPanel;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox termsCheckbox;
    private JLabel profilePictureLabel;
    private JProgressBar passwordStrengthBar;
    private JLabel passwordStrengthLabel;
    private Timer animationTimer;
    private BufferedImage profileImage;
    private boolean passwordVisible = false;
    private CardLayout cardLayout;
    
    public BloodBankManagementSystem() {
        setTitle("BloodLink - Blood Bank Management System");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels
        createSplashScreen();
        createSignupPanel();
        createLoginPanel();
        createDashboardPanel();
        
        // Add panels to main container
        mainPanel.add(createSplashScreen(), "splash");
        mainPanel.add(signupPanel, "signup");
        mainPanel.add(loginPanel, "login");
        mainPanel.add(dashboardPanel, "dashboard");
        
        // Show splash screen first
        cardLayout.show(mainPanel, "splash");
        
        // Start timer to transition to login after splash
        Timer splashTimer = new Timer(2500, e -> cardLayout.show(mainPanel, "signup"));
        splashTimer.setRepeats(false);
        splashTimer.start();
        
        add(mainPanel);
    }
    
    private JPanel createSplashScreen() {
        JPanel splashPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY_COLOR, 
                        getWidth(), getHeight(), 
                        new Color(33, 37, 41));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw animated blood drop
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2 - 40;
                
                long time = System.currentTimeMillis() % 2000;
                float pulse = (float) Math.sin(time * Math.PI / 1000) * 0.1f + 0.9f;
                
                // Blood drop shape
                Path2D dropPath = new Path2D.Double();
                dropPath.moveTo(centerX, centerY - 80 * pulse);
                dropPath.curveTo(
                        centerX - 60 * pulse, centerY - 30 * pulse, 
                        centerX - 60 * pulse, centerY + 50 * pulse, 
                        centerX, centerY + 80 * pulse);
                dropPath.curveTo(
                        centerX + 60 * pulse, centerY + 50 * pulse, 
                        centerX + 60 * pulse, centerY - 30 * pulse, 
                        centerX, centerY - 80 * pulse);
                
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setPaint(new Color(220, 53, 69, 240));
                g2d.fill(dropPath);
                
                // Add shine
                g2d.setPaint(new Color(255, 255, 255, 100));
                g2d.fillOval(centerX - 20, centerY - 40, 25, 15);
                
                // Draw text
                g2d.setFont(new Font("Arial", Font.BOLD, 36));
                g2d.setPaint(Color.WHITE);
                String title = "BloodLink";
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(title, centerX - fm.stringWidth(title)/2, centerY + 120);
                
                g2d.setFont(new Font("Arial", Font.PLAIN, 16));
                String subtitle = "Blood Bank Management System";
                fm = g2d.getFontMetrics();
                g2d.drawString(subtitle, centerX - fm.stringWidth(subtitle)/2, centerY + 150);
                
                g2d.dispose();
            }
        };
        
        // Use timer to trigger repaints for animation
        Timer animationTimer = new Timer(50, e -> splashPanel.repaint());
        animationTimer.start();
        
        return splashPanel;
    }
    
    private void createSignupPanel() {
        signupPanel = new AnimatedPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Gradient background based on theme
                Color startColor = darkMode ? new Color(33, 37, 41) : new Color(248, 249, 250);
                Color endColor = darkMode ? new Color(20, 20, 20) : new Color(220, 53, 69, 40);
                
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor, 
                        getWidth(), getHeight(), 
                        endColor);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Animated circles in background
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
                long time = System.currentTimeMillis();
                for (int i = 0; i < 5; i++) {
                    double angle = (time % 10000) / 10000.0 * 2 * Math.PI + i * Math.PI / 2.5;
                    int x = (int)(Math.sin(angle) * 100) + getWidth() / 2;
                    int y = (int)(Math.cos(angle) * 100) + getHeight() / 2;
                    int size = 150 + i * 50;
                    g2d.setPaint(PRIMARY_COLOR);
                    g2d.fillOval(x - size/2, y - size/2, size, size);
                }
                
                g2d.dispose();
            }
        };
        
        signupPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Create Your Donor Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Theme toggle
        JToggleButton themeToggle = new JToggleButton("🌙");
        themeToggle.setFocusPainted(false);
        themeToggle.setBorderPainted(false);
        themeToggle.setContentAreaFilled(false);
        themeToggle.setFont(new Font("Dialog", Font.PLAIN, 18));
        themeToggle.addActionListener(e -> toggleTheme());
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        themePanel.setOpaque(false);
        themePanel.add(themeToggle);
        headerPanel.add(themePanel, BorderLayout.EAST);
        
        signupPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form panel (center)
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setOpaque(false);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        // Create two columns
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setOpaque(false);
        
        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setOpaque(false);
        
        // Profile picture panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setOpaque(false);
        profilePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        profilePictureLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw placeholder or actual image
                if (profileImage != null) {
                    int diameter = Math.min(getWidth(), getHeight());
                    BufferedImage circularImage = createCircularImage(profileImage, diameter);
                    g2d.drawImage(circularImage, 0, 0, this);
                } else {
                    g2d.setColor(darkMode ? new Color(60, 60, 60) : new Color(220, 220, 220));
                    g2d.fillOval(0, 0, getWidth(), getHeight());
                    g2d.setColor(darkMode ? new Color(100, 100, 100) : new Color(180, 180, 180));
                    g2d.fillOval(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);
                }
                
                g2d.dispose();
            }
        };
        profilePictureLabel.setPreferredSize(new Dimension(120, 120));
        profilePictureLabel.setMinimumSize(new Dimension(120, 120));
        profilePictureLabel.setMaximumSize(new Dimension(120, 120));
        
        JButton uploadButton = new JButton("Upload Photo");
        styleButton(uploadButton);
        uploadButton.addActionListener(e -> uploadProfilePicture());
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel pictureLabelPanel = new JPanel();
        pictureLabelPanel.setOpaque(false);
        pictureLabelPanel.add(profilePictureLabel);
        
        profilePanel.add(pictureLabelPanel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(uploadButton);
        profilePanel.add(Box.createVerticalStrut(20));
        
        rightColumn.add(profilePanel);
        
        // Add form fields to left column
        fullNameField = createAnimatedTextField("Full Name");
        emailField = createAnimatedTextField("Email Address");
        phoneField = createAnimatedTextField("Phone Number");
        usernameField = createAnimatedTextField("Username");
        
        // Password fields with strength meter
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setOpaque(false);
        
        JPanel passwordInputPanel = new JPanel(new BorderLayout());
        passwordInputPanel.setOpaque(false);
        
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 40));
        passwordField.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        passwordField.setForeground(darkMode ? Color.WHITE : Color.BLACK);
        passwordField.setOpaque(true);
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
        ((JComponent) passwordField).setOpaque(true);
        
        // Password visibility toggle
        JButton visibilityButton = new JButton("👁️");
        visibilityButton.setBorderPainted(false);
        visibilityButton.setContentAreaFilled(false);
        visibilityButton.setFocusPainted(false);
        visibilityButton.addActionListener(e -> togglePasswordVisibility());
        
        passwordInputPanel.add(passwordField, BorderLayout.CENTER);
        passwordInputPanel.add(visibilityButton, BorderLayout.EAST);
        
        // Password strength components
        passwordStrengthBar = new JProgressBar(0, 100);
        passwordStrengthBar.setPreferredSize(new Dimension(0, 5));
        passwordStrengthBar.setBorderPainted(false);
        passwordStrengthBar.setStringPainted(false);
        
        passwordStrengthLabel = new JLabel("Password Strength");
        passwordStrengthLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        passwordStrengthLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
        });
        
        JPanel strengthPanel = new JPanel(new BorderLayout(5, 2));
        strengthPanel.setOpaque(false);
        strengthPanel.add(passwordStrengthBar, BorderLayout.CENTER);
        strengthPanel.add(passwordStrengthLabel, BorderLayout.EAST);
        
        passwordPanel.add(passwordInputPanel);
        passwordPanel.add(strengthPanel);
        
        // Confirm password field
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        confirmPasswordField.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        confirmPasswordField.setForeground(darkMode ? Color.WHITE : Color.BLACK);
        confirmPasswordField.putClientProperty("JTextField.placeholderText", "Confirm Password");
        ((JComponent) confirmPasswordField).setOpaque(true);
        
        // Terms and conditions
        termsCheckbox = new JCheckBox("I agree to the Terms and Conditions");
        termsCheckbox.setOpaque(false);
        termsCheckbox.setForeground(darkMode ? Color.WHITE : Color.BLACK);
        
        // Social logins panel
        JPanel socialPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        socialPanel.setOpaque(false);
        
        JButton googleButton = createSocialButton("Sign up with Google", new Color(219, 68, 55));
        JButton facebookButton = createSocialButton("Sign up with Facebook", new Color(59, 89, 152));
        
        socialPanel.add(googleButton);
        socialPanel.add(facebookButton);
        
        // Add components to left column with vertical spacing
        leftColumn.add(Box.createVerticalStrut(10));
        leftColumn.add(createInputWrapper(fullNameField, "Full Name"));
        leftColumn.add(Box.createVerticalStrut(15));
        leftColumn.add(createInputWrapper(emailField, "Email Address"));
        leftColumn.add(Box.createVerticalStrut(15));
        leftColumn.add(createInputWrapper(phoneField, "Phone Number"));
        leftColumn.add(Box.createVerticalStrut(15));
        leftColumn.add(createInputWrapper(usernameField, "Username"));
        leftColumn.add(Box.createVerticalStrut(15));
        leftColumn.add(passwordPanel);
        leftColumn.add(Box.createVerticalStrut(15));
        leftColumn.add(createInputWrapper(confirmPasswordField, "Confirm Password"));
        leftColumn.add(Box.createVerticalStrut(20));
        leftColumn.add(termsCheckbox);
        leftColumn.add(Box.createVerticalStrut(15));
        
        // Add social login options to right column
        rightColumn.add(Box.createVerticalStrut(20));
        JLabel orLabel = new JLabel("OR");
        orLabel.setFont(new Font("Arial", Font.BOLD, 12));
        orLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        orLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightColumn.add(orLabel);
        rightColumn.add(Box.createVerticalStrut(20));
        rightColumn.add(socialPanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton signupButton = new JButton("Create Account");
        styleButton(signupButton);
        signupButton.setBackground(PRIMARY_COLOR);
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(e -> validateAndSignup());
        
        JButton loginButton = new JButton("Already have an account? Log in");
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(PRIMARY_COLOR);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        
        buttonPanel.add(signupButton);
        
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setOpaque(false);
        linkPanel.add(loginButton);
        
        // Create two-column layout
        JPanel columnsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        columnsPanel.setOpaque(false);
        columnsPanel.add(leftColumn);
        columnsPanel.add(rightColumn);
        
        formPanel.add(columnsPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(buttonPanel);
        formPanel.add(linkPanel);
        
        formWrapper.add(formPanel);
        signupPanel.add(formWrapper, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("© 2025 BloodLink | Blood Bank Management System");
        footerLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        footerPanel.add(footerLabel);
        signupPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Animate panel
        Timer timer = new Timer(60, e -> signupPanel.repaint());
        timer.start();
    }
    
    private JPanel createInputWrapper(JTextField field, String labelText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JLabel floatingLabel = new JLabel(labelText);
        floatingLabel.setForeground(darkMode ? new Color(180, 180, 180) : Color.GRAY);
        floatingLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        floatingLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        
        panel.add(floatingLabel, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JTextField createAnimatedTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Add animation effect on focus
                if (hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Draw a subtle glow effect
                    for (int i = 0; i < 5; i++) {
                        g2d.setColor(new Color(PRIMARY_COLOR.getRed(), 
                                              PRIMARY_COLOR.getGreen(), 
                                              PRIMARY_COLOR.getBlue(), 
                                              10 - i * 2));
                        g2d.drawRoundRect(i, i, getWidth() - i * 2 - 1, getHeight() - i * 2 - 1, 5, 5);
                    }
                    g2d.dispose();
                }
            }
        };
        
        field.putClientProperty("JTextField.placeholderText", placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        field.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        field.setForeground(darkMode ? Color.WHITE : Color.BLACK);
        
        field.addFocusListener(new FocusAdapter() {
            Timer timer;
            
            @Override
            public void focusGained(FocusEvent e) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                timer = new Timer(50, evt -> field.repaint());
                timer.start();
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (timer != null) {
                    timer.stop();
                }
            }
        });
        
        return field;
    }
    
    private JButton createSocialButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                    "Social login integration would be implemented here.", 
                    "Social Login", 
                    JOptionPane.INFORMATION_MESSAGE);
        });
        
        return button;
    }
    
    private void styleButton(JButton button) {
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(
                        Math.max(PRIMARY_COLOR.getRed() - 20, 0),
                        Math.max(PRIMARY_COLOR.getGreen() - 20, 0),
                        Math.max(PRIMARY_COLOR.getBlue() - 20, 0)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }
    
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        if (passwordVisible) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('•');
        }
    }
    
    private void updatePasswordStrength() {
        String password = new String(passwordField.getPassword());
        int strength = calculatePasswordStrength(password);
        
        passwordStrengthBar.setValue(strength);
        
        if (strength < 40) {
            passwordStrengthBar.setForeground(new Color(220, 53, 69)); // Red
            passwordStrengthLabel.setText("Weak");
            passwordStrengthLabel.setForeground(new Color(220, 53, 69));
        } else if (strength < 70) {
            passwordStrengthBar.setForeground(new Color(255, 193, 7)); // Yellow
            passwordStrengthLabel.setText("Medium");
            passwordStrengthLabel.setForeground(new Color(255, 193, 7));
        } else {
            passwordStrengthBar.setForeground(new Color(40, 167, 69)); // Green
            passwordStrengthLabel.setText("Strong");
            passwordStrengthLabel.setForeground(new Color(40, 167, 69));
        }
    }
    
    private int calculatePasswordStrength(String password) {
        if (password.length() == 0) return 0;
        
        int score = 0;
        
        // Length
        score += Math.min(password.length() * 4, 40);
        
        // Complexity
        if (Pattern.compile("[a-z]").matcher(password).find()) score += 10;
        if (Pattern.compile("[A-Z]").matcher(password).find()) score += 10;
        if (Pattern.compile("[0-9]").matcher(password).find()) score += 10;
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) score += 15;
        
        // Variety
        int uniqueChars = (int) password.chars().distinct().count();
        score += Math.min(uniqueChars * 2, 15);
        
        return Math.min(score, 100);
    }
    
    private void validateAndSignup() {
        // Simple validation
        if (fullNameField.getText().trim().isEmpty()) {
            showError("Please enter your full name");
            return;
        }
        
        if (!isValidEmail(emailField.getText())) {
            showError("Please enter a valid email address");
            return;
        }
        
        if (!isValidPhone(phoneField.getText())) {
            showError("Please enter a valid phone number");
            return;
        }
        
        if (usernameField.getText().trim().length() < 4) {
            showError("Username must be at least 4 characters long");
            return;
        }
        
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (password.length() < 8) {
            showError("Password must be at least 8 characters long");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        }
        
        if (!termsCheckbox.isSelected()) {
            showError("You must agree to the Terms and Conditions");
            return;
        }
        
        // Show success and transition to dashboard
        showSuccess("Account created successfully!");
        cardLayout.show(mainPanel, "dashboard");
    }
    
    private boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }
    
    private boolean isValidPhone(String phone) {
        return Pattern.matches("^[0-9]{10,15}$", phone);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void uploadProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage originalImage = ImageIO.read(selectedFile);
                if (originalImage != null) {
                    // Resize image to fit the profile picture label
                    profileImage = resizeImage(originalImage, 120, 120);
                    profilePictureLabel.repaint();
                }
            } catch (Exception ex) {
                showError("Failed to load image: " + ex.getMessage());
            }
        }
    }
    
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    
    private BufferedImage createCircularImage(BufferedImage image, int diameter) {
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.setClip(shape);
        g2.drawImage(image, 0, 0, diameter, diameter, null);
        
        g2.setClip(null);
        g2.setColor(darkMode ? new Color(60, 60, 60) : new Color(220, 220, 220));
        g2.setStroke(new BasicStroke(2));
        g2.draw(shape);
        g2.dispose();
        return circularImage;
    }
    
    private void toggleTheme() {
        darkMode = !darkMode;
        updateTheme();
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    private void updateTheme() {
        Color bgColor = darkMode ? new Color(33, 37, 41) : SECONDARY_COLOR;
        Color textColor = darkMode ? Color.WHITE : Color.BLACK;
        
        // Update background colors
        signupPanel.setBackground(bgColor);
        
        // Update text fields
        updateTextField(fullNameField, textColor);
        updateTextField(emailField, textColor);
        updateTextField(phoneField, textColor);
        updateTextField(usernameField, textColor);
        updateTextField(passwordField, textColor);
        updateTextField(confirmPasswordField, textColor);
        
        // Update checkbox
        termsCheckbox.setForeground(textColor);
        
        repaint();
    }
    
    private void updateTextField(JTextField field, Color textColor) {
        field.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        field.setForeground(textColor);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, darkMode ? Color.GRAY : Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }
    
    private void createLoginPanel() {
        loginPanel = new AnimatedPanel();
        loginPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Log in to Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        loginPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Login form
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setOpaque(false);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        JTextField usernameField = createAnimatedTextField("Username or Email");
        JPasswordField passwordField = new JPasswordField();
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        passwordField.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        passwordField.setForeground(darkMode ? Color.WHITE : Color.BLACK);
        
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setOpaque(false);
        rememberMe.setForeground(darkMode ? Color.WHITE : Color.BLACK);
        
        JButton forgotPassword = new JButton("Forgot Password?");
        forgotPassword.setBorderPainted(false);
        forgotPassword.setContentAreaFilled(false);
        forgotPassword.setForeground(PRIMARY_COLOR);
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton loginButton = new JButton("Log In");
        styleButton(loginButton);
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        
        JButton signupButton = new JButton("Don't have an account? Sign up");
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setForeground(PRIMARY_COLOR);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.addActionListener(e -> cardLayout.show(mainPanel, "signup"));
        
        // Social logins
        JLabel orLabel = new JLabel("OR");
        orLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        orLabel.setFont(new Font("Arial", Font.BOLD, 12));
        orLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        
        JPanel socialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        socialPanel.setOpaque(false);
        
        JButton googleLogin = createSocialButton("Log in with Google", new Color(219, 68, 55));
        JButton facebookLogin = createSocialButton("Log in with Facebook", new Color(59, 89, 152));
        
        socialPanel.add(googleLogin);
        socialPanel.add(facebookLogin);
        
        // Add components to form with spacing
        formPanel.add(createInputWrapper(usernameField, "Username or Email"));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createInputWrapper(passwordField, "Password"));
        formPanel.add(Box.createVerticalStrut(10));
        
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setOpaque(false);
        optionsPanel.add(rememberMe, BorderLayout.WEST);
        optionsPanel.add(forgotPassword, BorderLayout.EAST);
        
        formPanel.add(optionsPanel);
        formPanel.add(Box.createVerticalStrut(20));
        
        JPanel loginButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButtonPanel.setOpaque(false);
        loginButtonPanel.add(loginButton);
        formPanel.add(loginButtonPanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        JPanel orPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        orPanel.setOpaque(false);
        orPanel.add(orLabel);
        formPanel.add(orPanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        formPanel.add(socialPanel);
        formPanel.add(Box.createVerticalStrut(20));
        
        JPanel signupButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupButtonPanel.setOpaque(false);
        signupButtonPanel.add(signupButton);
        formPanel.add(signupButtonPanel);
        
        formWrapper.add(formPanel);
        loginPanel.add(formWrapper, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setOpaque(false);
        JLabel footerLabel = new JLabel("© 2025 BloodLink | Blood Bank Management System");
        footerLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        footerPanel.add(footerLabel);
        loginPanel.add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void createDashboardPanel() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(darkMode ? new Color(33, 37, 41) : SECONDARY_COLOR);
        
        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(PRIMARY_COLOR);
        sidebar.setPreferredSize(new Dimension(200, 0));
        
        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setOpaque(false);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel logoLabel = new JLabel("BloodLink");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);
        
        sidebar.add(logoPanel);
        
        // Menu items
        String[] menuItems = {
            "Dashboard", "Donors", "Blood Inventory", 
            "Blood Requests", "Donations", "Reports", 
            "Users", "Settings", "Logout"
        };
        
        for (String item : menuItems) {
            JPanel menuItem = createMenuItem(item);
            sidebar.add(menuItem);
        }
        
        // Main content
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(darkMode ? new Color(45, 45, 45) : Color.WHITE);
        
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome to Blood Bank Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(darkMode ? Color.WHITE : ACCENT_COLOR);
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        
        JLabel userLabel = new JLabel("Admin User");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        
        userPanel.add(userLabel);
        
        header.add(welcomeLabel, BorderLayout.WEST);
        header.add(userPanel, BorderLayout.EAST);
        
        // Dashboard content
        JPanel dashboard = new JPanel(new GridLayout(2, 2, 20, 20));
        dashboard.setBackground(darkMode ? new Color(45, 45, 45) : Color.WHITE);
        dashboard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Stats cards
        dashboard.add(createStatCard("Total Donors", "1,245", new Color(220, 53, 69)));
        dashboard.add(createStatCard("Available Blood Units", "850", new Color(40, 167, 69)));
        dashboard.add(createStatCard("Blood Requests", "28", new Color(255, 193, 7)));
        dashboard.add(createStatCard("Donations This Month", "125", new Color(13, 110, 253)));
        
        // Blood inventory panel
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        inventoryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 20, 20, 20),
                BorderFactory.createLineBorder(darkMode ? new Color(70, 70, 70) : Color.LIGHT_GRAY, 1)
        ));
        
        JPanel inventoryHeader = new JPanel(new BorderLayout());
        inventoryHeader.setBackground(darkMode ? new Color(60, 60, 60) : new Color(248, 249, 250));
        inventoryHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel inventoryTitle = new JLabel("Blood Inventory Status");
        inventoryTitle.setFont(new Font("Arial", Font.BOLD, 16));
        inventoryTitle.setForeground(darkMode ? Color.WHITE : ACCENT_COLOR);
        
        inventoryHeader.add(inventoryTitle, BorderLayout.WEST);
        
        // Inventory table
        String[] columns = {"Blood Type", "Available Units", "Status"};
        Object[][] data = {
            {"A+", 120, "Adequate"},
            {"A-", 45, "Low"},
            {"B+", 98, "Adequate"},
            {"B-", 32, "Low"},
            {"AB+", 56, "Adequate"},
            {"AB-", 18, "Critical"},
            {"O+", 145, "Adequate"},
            {"O-", 67, "Low"}
        };
        
        JTable inventoryTable = new JTable(data, columns) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                
                if (column == 2) {
                    String status = (String) getValueAt(row, column);
                    if ("Adequate".equals(status)) {
                        comp.setForeground(SUCCESS_COLOR);
                    } else if ("Low".equals(status)) {
                        comp.setForeground(new Color(255, 193, 7));
                    } else if ("Critical".equals(status)) {
                        comp.setForeground(PRIMARY_COLOR);
                    }
                } else {
                    comp.setForeground(darkMode ? Color.WHITE : Color.BLACK);
                }
                
                comp.setBackground(row % 2 == 0 ? 
                        (darkMode ? new Color(55, 55, 55) : new Color(248, 249, 250)) : 
                        (darkMode ? new Color(60, 60, 60) : Color.WHITE));
                
                return comp;
            }
        };
        
        inventoryTable.setRowHeight(30);
        inventoryTable.getTableHeader().setBackground(darkMode ? new Color(70, 70, 70) : new Color(240, 240, 240));
        inventoryTable.getTableHeader().setForeground(darkMode ? Color.WHITE : Color.BLACK);
        inventoryTable.setGridColor(darkMode ? new Color(70, 70, 70) : Color.LIGHT_GRAY);
        inventoryTable.setShowGrid(true);
        
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        inventoryPanel.add(inventoryHeader, BorderLayout.NORTH);
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Recent activity panel
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 20, 20, 20),
                BorderFactory.createLineBorder(darkMode ? new Color(70, 70, 70) : Color.LIGHT_GRAY, 1)
        ));
        
        JPanel activityHeader = new JPanel(new BorderLayout());
        activityHeader.setBackground(darkMode ? new Color(60, 60, 60) : new Color(248, 249, 250));
        activityHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel activityTitle = new JLabel("Recent Activities");
        activityTitle.setFont(new Font("Arial", Font.BOLD, 16));
        activityTitle.setForeground(darkMode ? Color.WHITE : ACCENT_COLOR);
        
        activityHeader.add(activityTitle, BorderLayout.WEST);
        
        // Activity list
        JPanel activityList = new JPanel();
        activityList.setLayout(new BoxLayout(activityList, BoxLayout.Y_AXIS));
        activityList.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        
        String[] activities = {
            "John Doe donated 450ml of A+ blood - 20 minutes ago",
            "Blood request #28491 approved for City Hospital - 1 hour ago",
            "New donor Sarah Johnson registered - 3 hours ago",
            "10 units of O- transferred to Memorial Hospital - 5 hours ago",
            "Blood inventory updated by Admin - Yesterday"
        };
        
        for (String activity : activities) {
            JPanel activityItem = new JPanel(new BorderLayout());
            activityItem.setBackground(darkMode ? new Color(55, 55, 55) : new Color(248, 249, 250));
            activityItem.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(2, 0, 2, 0),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            JLabel activityLabel = new JLabel(activity);
            activityLabel.setForeground(darkMode ? Color.WHITE : Color.BLACK);
            activityItem.add(activityLabel, BorderLayout.CENTER);
            
            activityList.add(activityItem);
            activityList.add(Box.createVerticalStrut(5));
        }
        
        JScrollPane activityScroll = new JScrollPane(activityList);
        activityScroll.setBorder(BorderFactory.createEmptyBorder());
        
        activityPanel.add(activityHeader, BorderLayout.NORTH);
        activityPanel.add(activityScroll, BorderLayout.CENTER);
        
        // Combine panels
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(darkMode ? new Color(45, 45, 45) : Color.WHITE);
        
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setOpaque(false);
        statsPanel.add(dashboard, BorderLayout.NORTH);
        
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        tablesPanel.setOpaque(false);
        tablesPanel.add(inventoryPanel);
        tablesPanel.add(activityPanel);
        
        contentWrapper.add(statsPanel, BorderLayout.NORTH);
        contentWrapper.add(tablesPanel, BorderLayout.CENTER);
        
        content.add(header, BorderLayout.NORTH);
        content.add(contentWrapper, BorderLayout.CENTER);
        
        // Add components to main panel
        dashboardPanel.add(sidebar, BorderLayout.WEST);
        dashboardPanel.add(content, BorderLayout.CENTER);
    }
    
    private JPanel createMenuItem(String text) {
        JPanel menuItem = new JPanel(new BorderLayout());
        menuItem.setOpaque(false);
        menuItem.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        
        menuItem.add(label, BorderLayout.CENTER);
        
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setOpaque(true);
                menuItem.setBackground(new Color(0, 0, 0, 50));
                menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setOpaque(false);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle menu item clicks
                if ("Logout".equals(text)) {
                    cardLayout.show(mainPanel, "login");
                }
            }
        });
        
        return menuItem;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient for card
                GradientPaint gradient = new GradientPaint(
                        0, 0, 
                        darkMode ? new Color(color.getRed(), color.getGreen(), color.getBlue(), 50) : 
                                  new Color(color.getRed(), color.getGreen(), color.getBlue(), 30),
                        0, getHeight(),
                        darkMode ? new Color(45, 45, 45) : Color.WHITE);
                
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Add border
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                
                g2d.dispose();
            }
        };
        
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    // Animated panel class for background effects
    private class AnimatedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Create gradient background based on theme
            Color startColor = darkMode ? new Color(33, 37, 41) : new Color(248, 249, 250);
            Color endColor = darkMode ? new Color(20, 20, 20) : new Color(240, 240, 240);
            
            GradientPaint gradient = new GradientPaint(
                    0, 0, startColor, 
                    getWidth(), getHeight(), 
                    endColor);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.dispose();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Custom UI tweaks
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("CheckBox.arc", 5);
            UIManager.put("ProgressBar.arc", 8);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            BloodBankManagementSystem app = new BloodBankManagementSystem();
            app.setVisible(true);
        });
    }
}