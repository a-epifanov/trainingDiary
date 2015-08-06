package entities;

import java.util.ArrayList;
import java.util.Date;

/**
* Training entity
*
* @version 1.5
* @author Nikita Levchenko
*/
public class Training {
	private Date            trainingDate;
	private ArrayList<Task> trainingTasks;
	
	public Training(Date date, Task task) {
		this.trainingDate  = date;
		this.trainingTasks = new ArrayList<Task>();
		this.trainingTasks.add(task);
	}
	
	public Training(Date date, ArrayList<Task> tasks) {
		this.trainingDate  = date;
		this.trainingTasks = tasks;
	}

	public Date getDate() {
		return trainingDate;
	}
	
	public ArrayList<Task> getTasks() {
		return trainingTasks;
	}
	
	public void setTasks(ArrayList<Task> tasks) {
		this.trainingTasks = tasks;
	}
}
