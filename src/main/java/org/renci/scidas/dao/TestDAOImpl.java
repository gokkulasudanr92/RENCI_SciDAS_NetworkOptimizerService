package org.renci.scidas.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.renci.scidas.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Qualifier("TestDAOImpl")
public class TestDAOImpl implements TestDAO {
	
	public static final Logger LOG = Logger.getLogger(TestDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addTest(Test object) {
		LOG.info("Insert Operation to DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(object);
		} catch (Exception e) {
			LOG.error("Exception while inserting an object to DB", e);
		}
	}

	@Override
	public void updateTest(Test object) {
		LOG.info("Update Operation to DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.update(object);
		} catch (Exception e) {
			LOG.error("Exception while updating an object to DB", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Test> listTest() {
		List<Test> result;
		LOG.info("List data from DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			result = (List<Test>) session.createQuery("from Test").getResultList();
		} catch (Exception e) {
			LOG.error("Exception while listing all the objects from DB", e);
			result = null;
		}
		return result;
	}

	@Override
	public Test getTestById(Integer id) {
		Test result;
		LOG.info("Select an object from DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			result = (Test) session.load(Test.class, id);
		} catch (Exception e) {
			LOG.error("Exception while selecting an object from DB", e);
			result = null;
		}
		return result;
	}

	@Override
	public void removeTest(Integer id) {
		LOG.info("Removing an object from DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Test t = (Test) session.load(Test.class, id);
			if (t != null) {
				session.delete(t);
			}
		} catch (Exception e) {
			LOG.error("Exception while removing an object from DB", e);
		}
	}

}
