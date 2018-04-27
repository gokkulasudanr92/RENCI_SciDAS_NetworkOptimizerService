package org.renci.scidas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="metrics_table")
public class MetricsTable {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="source")
	private String source;
	@Column(name="destination")
	private String destination;
	@Column(name="throughput")
	private Long throughput;
	@Column(name="updated_time")
	private Long updatedTime;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public Long getThroughput() {
		return throughput;
	}
	
	public void setThroughput(Long throughput) {
		this.throughput = throughput;
	}
	
	public Long getUpdatedTime() {
		return updatedTime;
	}
	
	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}
	
}
