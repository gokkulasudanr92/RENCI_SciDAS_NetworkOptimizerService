package org.renci.scidas.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Replicas {
	@JsonProperty("number")
	private Integer number;
	@JsonProperty("path")
	private String path;
	@JsonProperty("resource_name")
	private String resourceName;
	@JsonProperty("status")
	private Integer status;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
