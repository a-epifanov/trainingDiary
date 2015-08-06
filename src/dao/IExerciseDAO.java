package dao;

import java.util.List;

import entities.Exercise;

/**
* Interface that all ExerciseDAO must support
*
* @version 1.5
* @author Nikita Levchenko
*/
public interface  IExerciseDAO {
	public void         delete(int id);
	public int         create(Exercise exercise);
	public Exercise       findOne(int id);
	public void         update(Exercise exercise);
	public List<Exercise> findAll();
}