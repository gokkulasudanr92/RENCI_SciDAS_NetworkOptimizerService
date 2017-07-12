package org.renci.scidas.pojo;

public class ThroughputDataPOJO implements Comparable<ThroughputDataPOJO> {
	
	public String offer;
	public String destination;
	public Long value;

	public String getOffer() {
		return offer;
	}
	
	public void setOffer(String offer) {
		this.offer = offer;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public int compareTo(ThroughputDataPOJO o) {
		return o.getValue().compareTo(this.getValue());
	}

}
