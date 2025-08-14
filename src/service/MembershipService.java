package service;

import dao.MembershipDao;
import model.Membership;
import java.util.List;
import java.util.stream.Collectors;

public class MembershipService {
    private final MembershipDao membershipDao;

    public MembershipService() { this.membershipDao = new MembershipDao(); }
    public MembershipService(MembershipDao membershipDao) { this.membershipDao = membershipDao; }

    public void purchase(Membership m) { membershipDao.saveMembership(m); }
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
