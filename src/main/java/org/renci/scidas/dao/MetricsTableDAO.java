package org.renci.scidas.dao;

import java.util.List;

import org.renci.scidas.model.MetricsTable;

public interface MetricsTableDAO {
	
	public void addEntry(MetricsTable object);
	public void updateEntry(MetricsTable object);
	public List<MetricsTable> list();
	public MetricsTable getEntryById(Integer id);
	public void removeEntry(Integer id);
	
}
