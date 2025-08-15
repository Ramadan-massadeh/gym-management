// Ramadan Masadekh
package service;

import dao.MemberDao;
import dao.MembershipDao;
import dao.WorkoutClassDao;
import dao.GymMerchDao;
import model.Member;
import model.Membership;
import model.WorkoutClass;
import model.Merch;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for Member-related functionalities in the Gym Management System.
 * Allows members to manage profiles, memberships, browse classes, and view merch.
 */
public class MemberService {
    private static final Logger logger = Logger.getLogger(MemberService.class.getName());

    private final MemberDao memberDao;
    private final MembershipDao membershipDao;
    private final WorkoutClassDao classDao;
    private final GymMerchDao merchDao;

    /**
     * Default constructor initializing DAOs with default implementations.
     */
    public MemberService() {
        this.memberDao = new MemberDao();
        this.membershipDao = new MembershipDao();
        this.classDao = new WorkoutClassDao();
        this.merchDao = new GymMerchDao();
    }

    /**
     * Constructor for dependency injection.
     *
     * @param memberDao       DAO for member profile management.
     * @param membershipDao   DAO for handling memberships.
     * @param classDao        DAO for workout class browsing.
     * @param merchDao        DAO for viewing merchandise.
     */
    public MemberService(MemberDao memberDao, MembershipDao membershipDao, WorkoutClassDao classDao, GymMerchDao merchDao) {
        this.memberDao = memberDao;
        this.membershipDao = membershipDao;
        this.classDao = classDao;
        this.merchDao = merchDao;
    }


    // ------------------------- Member Profile -------------------------

    /**
     * Adds a new member to the database.
     *
     * @param m the Member object to add.
     */
    public void addMember(Member m) {
        try {
            memberDao.addMember(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add member", e);
        }
    }

    /**
     * Updates an existing member's information.
     *
     * @param m the Member object with updated data.
     */
    public void updateMember(Member m) {
        try {
            memberDao.updateMember(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update member", e);
        }
    }

    /**
     * Displays all members in the system.
     */
    public void displayAllMembers() {
        try {
            memberDao.displayAllMembers();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to display members", e);
        }
    }

    // ------------------------- Memberships -------------------------

    /**
     * Allows a member to purchase a gym membership.
     *
     * @param m the Membership object to save.
     */
    public void buyMembership(Membership m) {
        try {
            membershipDao.saveMembership(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to buy membership", e);
        }
    }

    /**
     * Returns a list of memberships owned by a specific member.
     *
     * @param memberId the member's user ID.
     * @return List of Membership objects.
     */
    public List<Membership> myMemberships(int memberId) {
        try {
            return membershipDao.getMembershipsByMemberId(memberId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get memberships for member ID: " + memberId, e);
            return List.of(); // return empty list on error
        }
    }

    /**
     * Calculates the total money spent by the member on memberships.
     *
     * @param memberId the member's user ID.
     * @return total amount spent as a double.
     */
    public double myTotalSpent(int memberId) {
        try {
            return myMemberships(memberId).stream()
                    .mapToDouble(Membership::getPrice)
                    .sum();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to calculate total spent for member ID: " + memberId, e);
            return 0;
        }
    }

    // ------------------------- Workout Classes -------------------------

    /**
     * Returns a list of all workout classes available in the system.
     *
     * @return List of WorkoutClass objects.
     */
    public List<WorkoutClass> browseClasses() {
        try {
            return classDao.getAllClasses();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to browse workout classes", e);
            return List.of();
        }
    }

    // ------------------------- Merchandise -------------------------

    /**
     * Returns a list of all merchandise items available in the gym store.
     *
     * @return List of Merch objects.
     */
    public List<Merch> viewMerch() {
        try {
            return merchDao.getAllItems();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to view merch items", e);
            return List.of();
        }
    }
}
