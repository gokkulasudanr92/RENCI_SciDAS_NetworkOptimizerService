package org.renci.scidas.pojo;

import java.util.List;
import java.util.Map;

public class RefinedRequest implements Comparable<RefinedRequest> {
	private String source;
	private List<String> data;
	private Map<String, List<String>> dataNodeMap;
	private RequestObject offer;
	private Long throughput = Long.valueOf(0);
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public List<String> getData() {
		return data;
	}
	
	public Map<String, List<String>> getDataNodeMap() {
		return dataNodeMap;
	}

	public void setDataNodeMap(Map<String, List<String>> dataNodeMap) {
		this.dataNodeMap = dataNodeMap;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
	
	public RequestObject getOffer() {
		return offer;
	}
	
	public void setOffer(RequestObject object) {
		this.offer = object;
	}

	public Long getThroughput() {
		return throughput;
	}

	public void setThroughput(Long throughput) {
		this.throughput = throughput;
	}

	@Override
	public int compareTo(RefinedRequest o) {
		return o.getThroughput().compareTo(this.getThroughput());
	}
	
}
