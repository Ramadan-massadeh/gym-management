package service;

import dao.GymMerchDao;
import dao.MembershipDao;
import dao.UserDao;
import model.Merch;
import model.Membership;
import model.User;

import java.util.List;

/**
 * Service class for administrative functionalities in the Gym Management System.
 * Admins can view users, manage memberships, and control gym merchandise.
 */
public class AdminService {
    private final UserDao userDao;
    private final MembershipDao membershipDao;
    private final GymMerchDao merchDao;

    /**
     * Default constructor that initializes DAOs with default implementations.
     */
    public AdminService() {
        this.userDao = new UserDao();
        this.membershipDao = new MembershipDao();
        this.merchDao = new GymMerchDao();
    }

    /**
     * Constructor for dependency injection.
     *
     * @param userDao        DAO for user-related database operations.
     * @param membershipDao  DAO for membership-related operations.
     * @param merchDao       DAO for merchandise-related operations.
     */
    public AdminService(UserDao userDao, MembershipDao membershipDao, GymMerchDao merchDao) {
        this.userDao = userDao;
        this.membershipDao = membershipDao;
        this.merchDao = merchDao;
    }

    // ------------------------- User Management -------------------------

    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of User objects.
     */
    public List<User> viewAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Deletes a user from the system based on their user ID.
     *
     * @param userId the ID of the user to delete.
     */
    public void deleteUser(int userId) {
        userDao.deleteUser(userId);
    }

    // ------------------------- Membership Management -------------------------

    /**
     * Retrieves all gym memberships from the database.
     *
     * @return List of Membership objects.
     */
    public List<Membership> viewAllMemberships() {
        return membershipDao.getAllMemberships();
    }

    /**
     * Calculates the total revenue from all memberships.
     *
     * @return total revenue as a double.
     */
    public double totalMembershipRevenue() {
        return membershipDao.getAllMemberships().stream()
                .mapToDouble(Membership::getPrice)
                .sum();
    }

    // ------------------------- Merchandise Management -------------------------

    /**
     * Saves a new merchandise item to the database.
     *
     * @param m the Merch object to be saved.
     */
    public void addItem(Merch m) {
        merchDao.saveItem(m);
    }

    /**
     * Updates an existing merchandise item.
     *
     * @param m the Merch object with updated data.
     */
    public void updateItem(Merch m) {
        merchDao.updateItem(m);
    }

    /**
     * Deletes a merchandise item by its item ID.
     *
     * @param itemId ID of the item to delete.
     */
    public void deleteItem(int itemId) {
        merchDao.deleteItem(itemId);
    }

    /**
     * Retrieves a merchandise item by its ID.
     *
     * @param itemId ID of the item to retrieve.
     * @return the Merch object if found, otherwise null.
     */
    public Merch getItem(int itemId) {
        return merchDao.getItemById(itemId);
    }

    /**
     * Lists all merchandise items in the inventory.
     *
     * @return List of Merch objects.
     */
    public List<Merch> listItems() {
        return merchDao.getAllItems();
    }

    /**
     * Calculates the total stock value of all merchandise (price * quantity).
     *
     * @return total stock value as a double.
     */
    public double totalStockValue() {
        return merchDao.getTotalStockValue();
    }
}
