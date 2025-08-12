package model;

/**
 * Ramadan masadekh
 * Represents a workout class created by a trainer.
 */
public class WorkoutClass {
    private int classId;
    private String title;
    private String description;
    private String schedule; 
    private int trainerId;   

    public WorkoutClass() {
    }

    public WorkoutClass(int classId, String title, String description,
                        String schedule, int trainerId) {
        this.classId = classId;
        this.title = title;
        this.description = description;
        this.schedule = schedule;
        this.trainerId = trainerId;
    }

    // getters and setters
    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public int getTrainerId() { return trainerId; }
    public void setTrainerId(int trainerId) { this.trainerId = trainerId; }

    @Override
    public String toString() {
        return "WorkoutClass{id=" + classId + ", title='" + title + "', schedule='" +
               schedule + "', trainerId=" + trainerId + "}";
    }
}