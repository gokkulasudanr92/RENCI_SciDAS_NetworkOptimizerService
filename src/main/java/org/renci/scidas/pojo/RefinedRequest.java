package org.renci.scidas.pojo;

import java.util.List;

public class RefinedRequest {
	private List<String> source;
	private List<String> data;
	private String agent;
	
	public List<String> getSource() {
		return source;
	}
	
	public void setSource(List<String> source) {
		this.source = source;
	}
	
	public List<String> getData() {
		return data;
	}
	
	public void setData(List<String> data) {
		this.data = data;
	}
	
	public String getAgent() {
		return agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
}
