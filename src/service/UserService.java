package service;

import dao.UserDao;
import model.User;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDao userDao;

    public UserService() { this.userDao = new UserDao(); }
    public UserService(UserDao userDao) { this.userDao = userDao; }

    public void register(User user) { userDao.saveUser(user); }

    public void registerWithPlainPassword(User user, String plainPassword) {
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        user.setPasswordHash(hashed);
        userDao.saveUser(user);
    }

    public List<User> getAllUsers() { return userDao.getAllUsers(); }
    public void deleteUser(int userId) { userDao.deleteUser(userId); }
}
