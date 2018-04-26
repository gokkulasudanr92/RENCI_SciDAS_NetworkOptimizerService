package org.renci.scidas.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.renci.scidas.dao.TestDAO;
import org.renci.scidas.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("TestServiceImpl")
public class TestServiceImpl implements TestService {
	
	public static final Logger LOG = Logger.getLogger(TestServiceImpl.class);
	
	@Autowired
	@Qualifier("TestDAOImpl")
	private TestDAO testDAO;

	@Override
	public void addTest(Test object) {
		LOG.info("Service to add an object");
		try {
			testDAO.addTest(object);
		} catch (Exception e) {
			LOG.error("Exception while adding an object", e);
		}
	}

	@Override
	public void updateTest(Test object) {
		LOG.info("Service to update an object");
		try {
			testDAO.updateTest(object);
		} catch (Exception e) {
			LOG.error("Exceptin while updating an object");
		}
	}

	@Override
	public List<Test> listTest() {
		List<Test> result;
		LOG.info("Service to list all the objects");
		try {
			result = testDAO.listTest();
		} catch (Exception e) {
			LOG.error("Exception while listing all the objects");
			result = null;
		}
		return result;
	}

	@Override
	public Test getTestById(Integer id) {
		Test result;
		LOG.info("Service to select an object");
		try {
			result = testDAO.getTestById(id);
		} catch (Exception e) {
			LOG.error("Exception while selecting an object", e);
			result = null;
		}
		return result;
	}

	@Override
	public void removeTest(Integer id) {
		LOG.info("Service to remove an object");
		try {
			testDAO.removeTest(id);
		} catch (Exception e) {
			LOG.error("Exception while removing an object", e);
		}
	}
	
	

}
