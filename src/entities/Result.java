package entities;

import java.util.Date;

/**
* Result entity
*
* @version 1.5
* @author Nikita Levchenko
*/
public class Result {
	private Integer resultId;
	private Exercise resultExercise;
	private int      resultRepetitionsCount;
	private int      resultSetsCount;
	private int      resultWeight;
	private Date     resultDate;
	
	public Result(Date date, Exercise exercise) {
		this.resultDate = date;
		this.setExercise(exercise);
		this.setRepetitionsCount(1);
		this.setSetsCount(1);
		this.setWeight(0);
	}
	
	public Result(Date date, Exercise execrise, int repetitionsCount) {
		this.resultDate = date;
		this.setExercise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(1);
		this.setWeight(0);
	}
	
	public Result(Date date, Exercise execrise, int repetitionsCount, int setsCount) {
		this.resultDate = date;
		this.setExercise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(setsCount);
		this.setWeight(0);
	}
	
	public Result(Date date, Exercise execrise, int repetitionsCount, int setsCount, int weight) {
		this.resultDate = date;
		this.setExercise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(setsCount);
		this.setWeight(weight);
	}
	
	public Result(Integer id, Date date, Exercise execrise, int repetitionsCount, int setsCount, int weight) {
		this.resultId = id;
		this.resultDate = date;
		this.setExercise(execrise);
		this.setRepetitionsCount(repetitionsCount);
		this.setSetsCount(setsCount);
		this.setWeight(weight);
	}
	
	public Integer getId() {
		return this.resultId;
	}
	
	public Date getDate() {
		return this.resultDate;
	}

	public Exercise getExercise() {
		return resultExercise;
	}

	public void setExercise(Exercise resultExercise) {
		this.resultExercise = resultExercise;
	}

	public int getRepetitionsCount() {
		return resultRepetitionsCount;
	}

	public void setRepetitionsCount(int resultRepetitionsCount) {
		this.resultRepetitionsCount = resultRepetitionsCount;
	}

	public int getWeight() {
		return resultWeight;
	}

	public void setWeight(int resultWeight) {
		this.resultWeight = resultWeight;
	}

	public int getSetsCount() {
		return resultSetsCount;
	}

	public void setSetsCount(int resultSetsCount) {
		this.resultSetsCount = resultSetsCount;
	}

}
