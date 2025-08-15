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

/**
 * Service class for Trainer-specific functionalities in the Gym Management System.
 * Trainers can manage their classes, view merch, and purchase gym memberships.
 */
public class TrainerService {
    private final TrainerDao trainerDao;
    private final WorkoutClassDao classDao;
    private final MembershipDao membershipDao;
    private final GymMerchDao merchDao;

    /**
     * Default constructor initializing DAOs with default implementations.
     */
    public TrainerService() {
        this.trainerDao = new TrainerDao();
        this.classDao = new WorkoutClassDao();
        this.membershipDao = new MembershipDao();
        this.merchDao = new GymMerchDao();
    }

    /**
     * Constructor for dependency injection.
     *
     * @param classDao        DAO for workout class management.
     * @param membershipDao   DAO for trainer membership handling.
     * @param merchDao        DAO for viewing merchandise.
     */
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

    // ------------------------- Classes -------------------------

    /**
     * Creates a new workout class.
     *
     * @param c WorkoutClass object containing class details.
     */
    public void createClass(WorkoutClass c) { classDao.saveClass(c); }

    /**
     * Updates the details of an existing class.
     *
     * @param c WorkoutClass object with updated values.
     */
    public void updateClass(WorkoutClass c) { classDao.updateClass(c); }

    /**
     * Deletes a workout class by its ID.
     *
     * @param classId the unique identifier of the class to delete.
     */
    public void deleteClass(int classId) { classDao.deleteClass(classId); }

    /**
     * Lists all classes that a specific trainer is responsible for.
     *
     * @param trainerId the ID of the trainer.
     * @return List of WorkoutClass objects.
     */
    public List<WorkoutClass> myClasses(int trainerId) {
        return classDao.getAllClasses().stream().filter(c -> c.getTrainerId() == trainerId).collect(Collectors.toList());
    }

    // ------------------------- Membership -------------------------

    /**
     * Allows a trainer to purchase a gym membership.
     *
     * @param m Membership object representing the purchase.
     */
    public void buyMembership(Membership m) { membershipDao.saveMembership(m); }

    /**
     * Retrieves a list of all merchandise items available at the gym.
     *
     * @return List of Merch objects.
     */
    public List<Merch> viewMerch() { return merchDao.getAllItems(); }
}
