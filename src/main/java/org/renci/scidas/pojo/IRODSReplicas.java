package org.renci.scidas.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IRODSReplicas {
	@JsonProperty("replicas")
	private List<Replicas> replicas;

	public List<Replicas> getReplicas() {
		return replicas;
	}

	public void setReplicas(List<Replicas> replicas) {
		this.replicas = replicas;
	}

}
