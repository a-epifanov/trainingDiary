package entities;

import java.awt.Image;

/**
* Exercise entity
*
* @version 1.5
* @author Nikita Levchenko
*/
public class Exercise {
	private Integer exerciseId;
	private String  exerciseName;
	private String  exerciseDescription;
	private String  exerciseCategory;
	
	public Exercise(String name) {
		this.setName(name);
		this.setDescription("Описание отсутствует");
		this.setCategory("Другое");
	}
	
	/*public Exercise(String name, String description) {
		this.setName(name);
		this.setDescription(description);
		this.setCategory("Другое");
	}
	
	public Exercise(String name, String category, String description) {
		this.setName(name);
		this.setDescription(description);
		this.setCategory(category);
		
	}
	
	public Exercise(String name, Image image) {
		this.setName(name);
		this.setDescription("Описание отсутствует");
		this.setCategory("Другое");
	}*/
	
	
	public Exercise(String name, String description, String category) {
		this.setName(name);
		if (description == null) {
			this.setDescription("Описание отсутствует");
		} else {
			this.setDescription(description);
		}
		if (description == null) {
			this.setCategory("Другое");
		} else {
			this.setCategory(category);
		}
	}
	
	

	/*public Exercise(Integer Id, String name, String description) {
		this.exerciseId = Id;
		this.setName(name);
		this.setDescription(description);
		this.setCategory("Другое");
	}*/
	
	public Exercise(Integer Id, String name, String description,  String category) {
		this.exerciseId = Id;
		this.setName(name);
		if (description == null) {
			this.setDescription("Описание отсутствует");
		} else {
			this.setDescription(description);
		}
		if (description == null) {
			this.setCategory("Другое");
		} else {
			this.setCategory(category);
		}
		
	}
	public void setId(int id) {
		this.exerciseId = id;
	}
	
	public Integer getId() {
		return exerciseId;
	}

	public String getName() {
		return exerciseName;
	}

	public void setName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getDescription() {
		return exerciseDescription;
	}

	public void setDescription(String exerciseDescription) {
		this.exerciseDescription = exerciseDescription;
	}

	public String getCategory() {
		return exerciseCategory;
	}

	public void setCategory(String category) {
		this.exerciseCategory = category;
	}

}
