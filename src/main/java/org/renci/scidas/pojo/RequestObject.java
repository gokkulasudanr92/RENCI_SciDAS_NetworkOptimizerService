package org.renci.scidas.pojo;

import java.util.List;

public class RequestObject {
	private String master;
	private String agent;
	private Double disk;
	private Double mem;
	private Double cpus;
	private String agentId;
	private String ports;
	private List<String> data;
	
	public String getMaster() {
		return master;
	}
	
	public void setMaster(String master) {
		this.master = master;
	}
	
	public String getAgent() {
		return agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public Double getDisk() {
		return disk;
	}
	
	public void setDisk(Double disk) {
		this.disk = disk;
	}
	
	public Double getMem() {
		return mem;
	}
	
	public void setMem(Double mem) {
		this.mem = mem;
	}
	
	public Double getCpus() {
		return cpus;
	}
	
	public void setCpus(Double cpus) {
		this.cpus = cpus;
	}
	
	public String getAgentId() {
		return agentId;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public String getPorts() {
		return ports;
	}
	
	public void setPorts(String ports) {
		this.ports = ports;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
	
}
