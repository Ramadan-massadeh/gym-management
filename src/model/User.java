package model;

/**
 * Base user for the system.
 * Ramadan masadekh
 */
public class User {
    private int userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String passwordHash; 
    private String role; // "Admin", "Trainer", "Member"

    public User() {
    }

    public User(int userId, String name, String email, String phoneNumber,
                String address, String passwordHash, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{id=" + userId + ", name='" + name + "', email='" + email +
               "', phone='" + phoneNumber + "', role=" + role + "}";
    }
}