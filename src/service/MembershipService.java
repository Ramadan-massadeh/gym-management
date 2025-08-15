package service;

import dao.MembershipDao;
import model.Membership;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling gym membership operations,
 * such as purchasing or updating memberships.
 */
public class MembershipService {
    private final MembershipDao membershipDao;

    /**
     * Default constructor that initializes with a default MembershipDao instance.
     */
    public MembershipService() { this.membershipDao = new MembershipDao(); }

    /**
     * Constructor for dependency injection. Useful for testing or using custom DAO.
     *
     * @param membershipDao The MembershipDao instance to use.
     */
    public MembershipService(MembershipDao membershipDao) { this.membershipDao = membershipDao; }

    /**
     * Saves a new membership to the database, representing a purchase.
     *
     * @param m The Membership object to be saved.
     */
    public void purchase(Membership m) { membershipDao.saveMembership(m); }

    /**
     * Updates an existing membership with new data.
     *
     * @param m The updated Membership object.
     */
    public void update(Membership m) { membershipDao.updateMembership(m); }
    public void delete(int membershipId) { membershipDao.deleteMembership(membershipId); }

    public List<Membership> listAll() { return membershipDao.getAllMemberships(); }

    public List<Membership> listByMember(int memberId) {
        return membershipDao.getAllMemberships()
                .stream()
                .filter(m -> m.getMemberId() == memberId)
                .collect(Collectors.toList());
    }

    public double totalRevenue() {
        return membershipDao.getAllMemberships().stream().mapToDouble(Membership::getPrice).sum();
    }

    public double totalSpentByMember(int memberId) {
        return listByMember(memberId).stream().mapToDouble(Membership::getPrice).sum();
    }
}
