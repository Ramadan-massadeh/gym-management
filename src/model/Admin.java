package model;

/**
 * Ramadan masadekh
 * Admin user extends base User.
 * 
 */
public class Admin extends User {

    public Admin() {
        setRole("Admin");
    }

    public Admin(int userId, String name, String email, String phoneNumber,
                 String address, String passwordHash) {
        super(userId, name, email, phoneNumber, address, passwordHash, "Admin");
    }
}