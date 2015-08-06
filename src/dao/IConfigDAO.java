package dao;

/**
* Interface that all ConfigDAO must support
*
* @version 1.5
* @author Nikita Levchenko
*/
public interface IConfigDAO {
	public void setCurrentTrainingPlan(int id);
	public void removeCurrentTrainingPlan();
	public Integer getCurrentTrainingPlan();
	public void setCurrentStyle(int id);
	public Integer getCurrentStyle();
}
