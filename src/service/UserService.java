package service;

import dao.UserDao;
import model.User;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Service class responsible for user-related operations,
 * such as registration and login. Handles logic before interacting with the DAO.
 */
public class UserService {
    private final UserDao userDao;

    /**
     * Constructor with dependency injection.
     *
     * @param userDao The UserDao instance to be used.
     */
    public UserService(UserDao userDao) { this.userDao = userDao; }

    /**
     * Registers a new user after hashing their plain-text password.
     *
     * @param plainPassword         The User object containing the user's information.
     * @param user The plain-text password to be hashed and stored.
     */
    public void registerWithPlainPassword(User user, String plainPassword) {
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        user.setPasswordHash(hashed);
        userDao.saveUser(user);
    }

    /**
     * Authenticates a user by comparing the input password with the hashed password in the database.
     *
     * @param email    The email used for login.
     * @param plainPassword The plain-text password entered by the user.
     * @return The authenticated User object if credentials are valid; otherwise null.
     */
    public User login(String email, String plainPassword) {
        User user = userDao.findByEmail(email);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
            return user;
        }
        return null; // Login failed
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of User objects.
     */
    public List<User> getAllUsers() { return userDao.getAllUsers(); }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    public void deleteUser(int userId) { userDao.deleteUser(userId); }
}
