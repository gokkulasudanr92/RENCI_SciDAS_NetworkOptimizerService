package org.renci.scidas.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.renci.scidas.dao.MetricsTableDAO;
import org.renci.scidas.model.MetricsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MetricsTableServiceImpl")
public class MetricsTableServiceImpl implements MetricsTableService {
	
	public static final Logger LOG = Logger.getLogger(MetricsTableServiceImpl.class);
	
	@Autowired
	@Qualifier("MetricsTableDAOImpl")
	private MetricsTableDAO metricsTableDAO;

	@Override
	public void addEntry(MetricsTable object) {
		LOG.info("Service to add an entry");
		try {
			metricsTableDAO.addEntry(object);
		} catch (Exception e) {
			LOG.error("Exception while adding an entry", e);
		}
	}

	@Override
	public void updateEntry(MetricsTable object) {
		LOG.info("Service to update an entry");
		try {
			metricsTableDAO.updateEntry(object);
		} catch (Exception e) {
			LOG.error("Exceptin while updating an entry");
		}
	}

	@Override
	public List<MetricsTable> list() {
		List<MetricsTable> result;
		LOG.info("Service to list all the entries");
		try {
			result = metricsTableDAO.list();
		} catch (Exception e) {
			LOG.error("Exception while listing all the entries");
			result = null;
		}
		return result;
	}

	@Override
	public MetricsTable getEntryById(Integer id) {
		MetricsTable result;
		LOG.info("Service to select an entry");
		try {
			result = metricsTableDAO.getEntryById(id);
		} catch (Exception e) {
			LOG.error("Exception while selecting an object", e);
			result = null;
		}
		return result;
	}

	@Override
	public void removeEntry(Integer id) {
		LOG.info("Service to remove an entry");
		try {
			metricsTableDAO.removeEntry(id);
		} catch (Exception e) {
			LOG.error("Exception while removing an entry", e);
		}
	}

}
