package org.renci.scidas.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThroughputDataJSON {
	
	@JsonProperty
	public Long ts;
	@JsonProperty
	public Long val;

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public Long getVal() {
		return val;
	}

	public void setVal(Long val) {
		this.val = val;
	}

}
