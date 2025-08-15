package service;

import dao.TrainerDao;
import dao.WorkoutClassDao;
import dao.MembershipDao;
import dao.GymMerchDao;
import model.Trainer;
import model.WorkoutClass;
import model.Membership;
import model.Merch;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerService {
    private final TrainerDao trainerDao;
    private final WorkoutClassDao classDao;
    private final MembershipDao membershipDao;
    private final GymMerchDao merchDao;

    public TrainerService() {
        this.trainerDao = new TrainerDao();
        this.classDao = new WorkoutClassDao();
        this.membershipDao = new MembershipDao();
        this.merchDao = new GymMerchDao();
    }
    public TrainerService(TrainerDao trainerDao, WorkoutClassDao classDao, MembershipDao membershipDao, GymMerchDao merchDao) {
        this.trainerDao = trainerDao;
        this.classDao = classDao;
        this.membershipDao = membershipDao;
        this.merchDao = merchDao;
    }

    // Trainer profile management
    public void addTrainer(Trainer t) { trainerDao.addTrainer(t); }
    public void updateTrainer(Trainer t) { trainerDao.updateTrainer(t); }
    public void displayAllTrainers() { trainerDao.displayAllTrainers(); }

    // Classes owned by trainer
    public void createClass(WorkoutClass c) { classDao.saveClass(c); }
    public void updateClass(WorkoutClass c) { classDao.updateClass(c); }
    public void deleteClass(int classId) { classDao.deleteClass(classId); }
    public List<WorkoutClass> myClasses(int trainerId) {
        return classDao.getAllClasses().stream().filter(c -> c.getTrainerId() == trainerId).collect(Collectors.toList());
    }

    public void buyMembership(Membership m) { membershipDao.saveMembership(m); }

    public List<Merch> viewMerch() { return merchDao.getAllItems(); }
}
