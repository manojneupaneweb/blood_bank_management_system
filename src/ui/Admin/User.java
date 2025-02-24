import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private Role role;

    // Constructor
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter and setter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Method to register a new user
    public static void registerUser(String username, String password, Role role) {
        User newUser = new User(username, password, role);
        DatabaseConnection.saveUser(newUser);  // Save to the database
    }
}

