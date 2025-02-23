package models;

import java.io.Serializable;

/**
 * Represents a user entity in the system.
 */
public class User implements Serializable {
    
    private int id;  // Auto-generated ID in database
    private String username;
    private String password;
    private String role;

    // Default constructor (needed for frameworks like Hibernate)
    public User() {}

    // Constructor without ID (for registration)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor with ID (for retrieving user data)
    public User(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}
