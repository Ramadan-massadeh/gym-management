package service;

import dao.WorkoutClassDao;
import model.WorkoutClass;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutClassService {
    private final WorkoutClassDao classDao;

    public WorkoutClassService() { this.classDao = new WorkoutClassDao(); }
    public WorkoutClassService(WorkoutClassDao classDao) { this.classDao = classDao; }

    public void create(WorkoutClass c) { classDao.saveClass(c); }
    public void update(WorkoutClass c) { classDao.updateClass(c); }
    public void delete(int classId) { classDao.deleteClass(classId); }

    public WorkoutClass getById(int classId) { return classDao.getClassById(classId); }
    public List<WorkoutClass> listAll() { return classDao.getAllClasses(); }

    public List<WorkoutClass> listByTrainer(int trainerId) {
        return classDao.getAllClasses().stream().filter(c -> c.getTrainerId() == trainerId).collect(Collectors.toList());
    }
}
