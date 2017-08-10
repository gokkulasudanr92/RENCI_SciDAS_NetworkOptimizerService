package org.renci.scidas.pojo;

import java.util.List;

public class OfferRankPOJOForProtobuf {
	
	public String sourceDataSite;
	public List<ThroughputDataPOJO> offers;

	public String getSourceDataSite() {
		return sourceDataSite;
	}

	public void setSourceDataSite(String sourceOffer) {
		this.sourceDataSite = sourceOffer;
	}

	public List<ThroughputDataPOJO> getOffers() {
		return offers;
	}

	public void setOffers(List<ThroughputDataPOJO> destinationOffers) {
		this.offers = destinationOffers;
	}

}
