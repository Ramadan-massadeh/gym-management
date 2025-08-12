package model;

/**
 * Member user extends base User.
 * Ramadan masadekh
 */
public class Member extends User {

    public Member() {
        setRole("Member");
    }

   public Member(int userId, String name, String email, String phoneNumber,
              String address, String passwordHash) {
    super(userId, name, email, phoneNumber, address, passwordHash, "Member");
}
}