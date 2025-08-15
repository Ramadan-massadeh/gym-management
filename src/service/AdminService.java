package service;

import dao.GymMerchDao;
import dao.MembershipDao;
import dao.UserDao;
import model.Merch;
import model.Membership;
import model.User;
import java.util.List;

public class AdminService {
    private final UserDao userDao;
    private final MembershipDao membershipDao;
    private final GymMerchDao merchDao;

    public AdminService() {
        this.userDao = new UserDao();
        this.membershipDao = new MembershipDao();
        this.merchDao = new GymMerchDao();
    }
    public AdminService(UserDao userDao, MembershipDao membershipDao, GymMerchDao merchDao) {
        this.userDao = userDao;
        this.membershipDao = membershipDao;
        this.merchDao = merchDao;
    }

    // Users
    public List<User> viewAllUsers() { return userDao.getAllUsers(); }
    public void deleteUser(int userId) { userDao.deleteUser(userId); }

    // Memberships
    public List<Membership> viewAllMemberships() { return membershipDao.getAllMemberships(); }
    public double totalMembershipRevenue() {
        return membershipDao.getAllMemberships().stream().mapToDouble(Membership::getPrice).sum();
    }

    // Merchandise
    public void addItem(Merch m) { merchDao.saveItem(m); }
    public void updateItem(Merch m) { merchDao.updateItem(m); }
    public void deleteItem(int itemId) { merchDao.deleteItem(itemId); }
    public Merch getItem(int itemId) { return merchDao.getItemById(itemId); }
    public List<Merch> listItems() { return merchDao.getAllItems(); }
    public double totalStockValue() { return merchDao.getTotalStockValue(); }
}
