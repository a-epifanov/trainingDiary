package dao;

import dao.impl.factory.XMLDAOFactory;

/**
* Abstract class DAO Factory
*
* @version 1.0
* @author Nikita Levchenko
*/
public abstract class DAOFactory {
	// List of DAO types supported by the factory
	  public static final int XML = 1;

	  public abstract IExerciseDAO getExerciseDAO();
	  public abstract ITrainingPlanDAO getTrainingPlanDAO();
	  public abstract IResultDAO getResultDAO();
	  public abstract IConfigDAO getConfigDAO();

	 /* public abstract void createDataSource();
	  public abstract void closeDataSource();*/

	  public static DAOFactory getDAOFactory(
	      int whichFactory) {
	  
		  switch (whichFactory) {
		  	case XML: 
		  		return new XMLDAOFactory();
		    default           : 
		    	return null;
		  }
	  }
}
