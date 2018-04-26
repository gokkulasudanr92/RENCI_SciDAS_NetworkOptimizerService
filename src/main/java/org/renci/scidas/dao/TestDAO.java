package org.renci.scidas.dao;

import java.util.List;

import org.renci.scidas.model.Test;

public interface TestDAO {
	
	public void addTest(Test object);
	public void updateTest(Test object);
	public List<Test> listTest();
	public Test getTestById(Integer id);
	public void removeTest(Integer id);

}
