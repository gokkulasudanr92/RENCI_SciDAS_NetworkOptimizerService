package org.renci.scidas.pojo;

public class DataSetAndOffersRequest {
	
	public String sourceDataSites;
	public String offers;

	public String getSourceDataSites() {
		return sourceDataSites;
	}

	public void setSourceDataSites(String sources) {
		this.sourceDataSites = sources;
	}

	public String getOffers() {
		return offers;
	}

	public void setOffers(String destinations) {
		this.offers = destinations;
	}

}
