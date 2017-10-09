package org.renci.scidas.pojo;

import java.util.List;

public class OfferRank {
	
	private boolean status = true;
	private List<RequestObject> offers;
	private List<String> data;
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<RequestObject> getOffers() {
		return offers;
	}

	public void setOffers(List<RequestObject> offers) {
		this.offers = offers;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
