package dao;

import java.util.Date;
import java.util.List;

import entities.Training;
import entities.TrainingPlan;

/**
* Interface that all TrainingPlanDAO must support
*
* @version 1.5
* @author Nikita Levchenko
*/
public interface ITrainingPlanDAO {
	public void       delete(int id);
	public int       create(TrainingPlan trainingPlan);
	public TrainingPlan       findOne(int id);
	public void       changeName(int id, String name);
	public void       addTraining(int planId, Training training);
	public void       deleteTraining(int planId, Date trainingDate);
	public List<TrainingPlan> findAll();
}
