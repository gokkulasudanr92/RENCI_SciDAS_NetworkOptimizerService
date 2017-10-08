package org.renci.scidas.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IRODSFileName {
	
	@JsonProperty("irods_filenames")
	private List<String> irodsFilenames;

	public List<String> getIrodsFilenames() {
		return irodsFilenames;
	}

	public void setIrodsFilenames(List<String> irodsFilenames) {
		this.irodsFilenames = irodsFilenames;
	}
	
}
