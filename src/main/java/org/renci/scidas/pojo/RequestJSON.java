package org.renci.scidas.pojo;

import java.util.List;

public class RequestJSON {
	
	private List<RequestObject> offers;
	private List<String> data;

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
