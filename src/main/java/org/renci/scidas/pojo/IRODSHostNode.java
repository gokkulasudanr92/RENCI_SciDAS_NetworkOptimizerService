package org.renci.scidas.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IRODSHostNode {
	@JsonProperty("hostnode")
	private List<String> hostnode;

	public List<String> getHostnode() {
		return hostnode;
	}

	public void setHostnode(List<String> hostnode) {
		this.hostnode = hostnode;
	}
	
}
