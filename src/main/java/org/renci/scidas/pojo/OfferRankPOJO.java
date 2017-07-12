package org.renci.scidas.pojo;

import java.util.List;

public class OfferRankPOJO {
	
	public String sourceOffer;
	public List<ThroughputDataPOJO> destinationOffers;

	public String getSourceOffer() {
		return sourceOffer;
	}

	public void setSourceOffer(String sourceOffer) {
		this.sourceOffer = sourceOffer;
	}

	public List<ThroughputDataPOJO> getDestinationOffers() {
		return destinationOffers;
	}

	public void setDestinationOffers(List<ThroughputDataPOJO> destinationOffers) {
		this.destinationOffers = destinationOffers;
	}

}
