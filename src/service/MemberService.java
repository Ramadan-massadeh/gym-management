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

public class MemberService {
    private static final Logger logger = Logger.getLogger(MemberService.class.getName());

    private final MemberDao memberDao;
    private final MembershipDao membershipDao;
    private final WorkoutClassDao classDao;
    private final GymMerchDao merchDao;

    public MemberService() {
        this.memberDao = new MemberDao();
        this.membershipDao = new MembershipDao();
        this.classDao = new WorkoutClassDao();
        this.merchDao = new GymMerchDao();
    }

    public MemberService(MemberDao memberDao, MembershipDao membershipDao, WorkoutClassDao classDao, GymMerchDao merchDao) {
        this.memberDao = memberDao;
        this.membershipDao = membershipDao;
        this.classDao = classDao;
        this.merchDao = merchDao;
    }

    // Member profile
    public void addMember(Member m) {
        try {
            memberDao.addMember(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add member", e);
        }
    }

    public void updateMember(Member m) {
        try {
            memberDao.updateMember(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update member", e);
        }
    }

    public void displayAllMembers() {
        try {
            memberDao.displayAllMembers();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to display members", e);
        }
    }

    // Memberships
    public void buyMembership(Membership m) {
        try {
            membershipDao.saveMembership(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to buy membership", e);
        }
    }

    public List<Membership> myMemberships(int memberId) {
        try {
            return membershipDao.getMembershipsByMemberId(memberId);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to get memberships for member ID: " + memberId, e);
            return List.of(); // return empty list on error
        }
    }

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

    // Classes
    public List<WorkoutClass> browseClasses() {
        try {
            return classDao.getAllClasses();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to browse workout classes", e);
            return List.of();
        }
    }

    // Merchandise
    public List<Merch> viewMerch() {
        try {
            return merchDao.getAllItems();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to view merch items", e);
            return List.of();
        }
    }
}
