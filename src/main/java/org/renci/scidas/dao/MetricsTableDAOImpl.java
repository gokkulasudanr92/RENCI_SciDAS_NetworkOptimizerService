package org.renci.scidas.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.renci.scidas.model.MetricsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Qualifier("MetricsTableDAOImpl")
public class MetricsTableDAOImpl implements MetricsTableDAO {
	
	public static final Logger LOG = Logger.getLogger(MetricsTableDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addEntry(MetricsTable object) {
		LOG.info("Insert Operation to DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(object);
		} catch (Exception e) {
			LOG.error("Exception while inserting an object to DB", e);
		}
	}

	@Override
	public void updateEntry(MetricsTable object) {
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
	public List<MetricsTable> list() {
		List<MetricsTable> result;
		LOG.info("List data from DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			result = (List<MetricsTable>) session.createQuery("from MetricsTable").getResultList();
		} catch (Exception e) {
			LOG.error("Exception while listing all the objects from DB", e);
			result = null;
		}
		return result;
	}

	@Override
	public MetricsTable getEntryById(Integer id) {
		MetricsTable result;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			result = (MetricsTable) session.load(MetricsTable.class, id);
		} catch (Exception e) {
			LOG.error("Exception while selecting an object from DB", e);
			result = null;
		}
		return result;
	}

	@Override
	public void removeEntry(Integer id) {
		LOG.info("Removing an object from DB");
		try {
			Session session = this.sessionFactory.getCurrentSession();
			MetricsTable search = (MetricsTable) session.load(MetricsTable.class, id);
			if (search != null) {
				session.delete(search);
			}
		} catch (Exception e) {
			LOG.error("Exception while removing an object from DB", e);
		}
	}

}
