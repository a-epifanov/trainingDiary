package dao;

import java.util.List;

import entities.Result;

/**
* Interface that all ResultDAO must support
*
* @version 1.5
* @author Nikita Levchenko
*/
public interface IResultDAO {
	public void       delete(int id);
	public void       create(Result result);
	public Result       findOne(int id);
	public void       update(Result result);
	public List<Result> findAll();
}
