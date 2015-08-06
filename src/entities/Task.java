package entities;

/**
* Task entity
*
* @version 1.5
* 
* @author Nikita Levchenko
*/
public class Task {
	private Integer  taskId;
	private Exercise taskExecrise;
	private int      taskRepetitionsCount;
	private int      taskSetsCount;
	private int      taskWeight;
	
	public Task(Exercise execrise) {
		this.setExecrise(execrise);
		this.setRepetitionsCount(1);
		this.setSetsCount(1);
		this.setWeight(0);
	}
	
	public Task(Exercise execrise, int repetitionsCount) {
		this.setExecrise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(1);
		this.setWeight(0);
	}
	
	public Task(Exercise execrise, int repetitionsCount, int setsCount) {
		this.setExecrise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(setsCount);
		this.setWeight(0);
	}
	
	public Task(Exercise execrise, int repetitionsCount, int setsCount, int weight) {
		this.setExecrise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(setsCount);
		this.setWeight(weight);
	}
	
	public Task(Integer id, Exercise execrise, int repetitionsCount, int setsCount, int weight) {
		this.taskId = id;
		this.setExecrise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(setsCount);
		this.setWeight(weight);
	}

	public Integer getId() {
		return this.taskId;
	}
	
	public Exercise getExecrise() {
		return taskExecrise;
	}

	public void setExecrise(Exercise taskExecrise) {
		this.taskExecrise = taskExecrise;
	}

	public int getWeight() {
		return taskWeight;
	}

	public void setWeight(int taskWeight) {
		this.taskWeight = taskWeight;
	}

	public int getRepetitionsCount() {
		return taskRepetitionsCount;
	}

	public void setRepetitionsCount(int taskRepetitionsCount) {
		this.taskRepetitionsCount = taskRepetitionsCount;
	}

	public int getSetsCount() {
		return taskSetsCount;
	}

	public void setSetsCount(int taskSetsCount) {
		this.taskSetsCount = taskSetsCount;
	}


}
