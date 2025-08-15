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
import java.util.stream.Collectors;

public class MemberService {
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
    public void addMember(Member m) { memberDao.addMember(m); }
    public void updateMember(Member m) { memberDao.updateMember(m); }
    public void displayAllMembers() { memberDao.displayAllMembers(); }

    // Memberships
    public void buyMembership(Membership m) { membershipDao.saveMembership(m); }
    public List<Membership> myMemberships(int memberId) {
        return membershipDao.getAllMemberships().stream().filter(x -> x.getMemberId() == memberId).collect(Collectors.toList());
    }
    public double myTotalSpent(int memberId) {
        return myMemberships(memberId).stream().mapToDouble(Membership::getPrice).sum();
    }

    // Classes
    public List<WorkoutClass> browseClasses() { return classDao.getAllClasses(); }

    // Merchandise
    public List<Merch> viewMerch() { return merchDao.getAllItems(); }
}
