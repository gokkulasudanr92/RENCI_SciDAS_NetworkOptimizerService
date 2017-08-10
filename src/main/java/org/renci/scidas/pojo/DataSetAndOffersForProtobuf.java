package org.renci.scidas.pojo;

import java.util.List;

public class DataSetAndOffersForProtobuf {
	
	List<String> sources;
	List<DestinationObject> destinations;

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}

	public List<DestinationObject> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<DestinationObject> destinations) {
		this.destinations = destinations;
	}

}
