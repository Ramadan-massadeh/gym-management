package service;

import dao.UserDao;
import model.User;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDao userDao;
    public UserService(UserDao userDao) { this.userDao = userDao; }

    public void register(User user) { userDao.saveUser(user); }

    public void registerWithPlainPassword(User user, String plainPassword) {
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        user.setPasswordHash(hashed);
        userDao.saveUser(user);
    }

    public User login(String email, String plainPassword) {
        User user = userDao.findByEmail(email);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
            return user;
        }
        return null; // Login failed
    }

    public List<User> getAllUsers() { return userDao.getAllUsers(); }
    public void deleteUser(int userId) { userDao.deleteUser(userId); }
}
