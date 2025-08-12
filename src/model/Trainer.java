package model;

/**
 * Ramadan Masadekh
 * Trainer class extends User.
 */
public class Trainer extends User {

    public Trainer() {
        setRole("Trainer");
    }

    public Trainer(int userId, String name, String email, String phoneNumber,
                   String address, String passwordHash) {
        super(userId, name, email, phoneNumber, address, passwordHash, "Trainer");
    }

  
    public int getTrainerId() {
        return getUserId();
    }
}