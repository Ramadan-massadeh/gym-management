package service;

import dao.WorkoutClassDao;
import model.WorkoutClass;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for handling workout class operations.
 * It acts as a middleman between the user interface and the DAO.
 */
public class WorkoutClassService {
    private final WorkoutClassDao classDao;

    /**
     * Constructor to initialize the WorkoutClassService with a DAO instance.
     */
    public WorkoutClassService() { this.classDao = new WorkoutClassDao(); }
    public WorkoutClassService(WorkoutClassDao classDao) { this.classDao = classDao; }

    /**
     * Saves a new workout class to the database.
     *
     * @param c The WorkoutClass object containing class details to be saved.
     */
    public void create(WorkoutClass c) { classDao.saveClass(c); }

    /**
     * Updates an existing workout class.
     *
     * @param c The WorkoutClass object with updated details.
     */
    public void update(WorkoutClass c) { classDao.updateClass(c); }

    /**
     * Deletes a workout class based on its ID.
     *
     * @param classId The ID of the class to be deleted.
     */
    public void delete(int classId) { classDao.deleteClass(classId); }

    public WorkoutClass getById(int classId) { return classDao.getClassById(classId); }

    /**
     * Retrieves a list of all workout classes in the system.
     *
     * @return A list of WorkoutClass objects.
     */
    public List<WorkoutClass> listAll() { return classDao.getAllClasses(); }

    /**
     * Retrieves a list of classes assigned to a specific trainer.
     *
     * @param trainerId The ID of the trainer.
     * @return A list of WorkoutClass objects associated with the given trainer.
     */
    public List<WorkoutClass> listByTrainer(int trainerId) {
        return classDao.getAllClasses().stream().filter(c -> c.getTrainerId() == trainerId).collect(Collectors.toList());
    }
}
