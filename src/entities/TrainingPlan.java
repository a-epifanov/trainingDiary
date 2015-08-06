package entities;

import java.util.ArrayList;

/**
* Training Plan entity
*
* @version 1.5
* @author Nikita Levchenko
*/
public class TrainingPlan {
	private Integer                 trainingPlanId;
	private String              trainingPlanName;
	private ArrayList<Training> trainingPlanTrainings;
	
	public TrainingPlan(String name) {
		this.setName(name);
		this.setTrainings(new ArrayList<Training>());
	}
	
	public TrainingPlan(String name, ArrayList<Training> trainings) {
		this.setName(name);
		this.setTrainings(trainings);
	}
	
	public TrainingPlan(Integer Id, String name, ArrayList<Training> trainings) {
		this.trainingPlanId = Id;
		this.setName(name);
		this.setTrainings(trainings);
	}
	
	public void setPlanId(Integer Id) {
		this.trainingPlanId = Id;
	}

	public Integer getPlanId() {
		return trainingPlanId;
	}

	public String getName() {
		return trainingPlanName;
	}

	public void setName(String trainingPlanName) {
		this.trainingPlanName = trainingPlanName;
	}

	public ArrayList<Training> getTrainings() {
		return trainingPlanTrainings;
	}

	public void setTrainings(ArrayList<Training> trainingPlanTrainings) {
		this.trainingPlanTrainings = trainingPlanTrainings;
	}

	public void addTraining(Training training) {
		this.trainingPlanTrainings.add(training);
	}
}
