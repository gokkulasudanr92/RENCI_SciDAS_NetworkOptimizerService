package org.renci.scidas.helper;

import org.apache.log4j.Logger;
import org.renci.scidas.constants.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("ConstructURIHelper")
public class ConstructURIHelper {
	
	public static final Logger LOG = Logger.getLogger(ConstructURIHelper.class);
	public static final String PROTOCOL = "http://";
	public static final String ENDPOINT = "139.62.242.122"; 
		// This should be ideally source of dataset but for the time being it is being harcoded
	public static final String ARCHIVE_PART = "/esmond/perfsonar/archive/?";
	public static final String SOURCE_PART = "source=";
	public static final String DESTINATION_PART = "destination=";
	public static final String EVENT_TYPE_PART = "event-type=throughput";
	public static final String THROUGHPUT_PART = "throughput/base?";
	public static final String TIME_RANGE_PART = "time-range=";
	public static final String START_TIME_PART = "time-start=";
	
	
	public String constructPerfSONARUriForThroughputEvent(String source, String destination) {
		LOG.info("Helper method to construct the perfSONAR URI for Throughput Event");
		String result = "";
		try {
			if (source == null || destination == null) {
				throw new RuntimeException("Either source or destination is null or empty");
			}
			
			if (source.length() == 0 || destination.length() == 0) {
				throw new RuntimeException("Either source or destination is null or empty");
			}
			
			result += PROTOCOL;
			result += ENDPOINT;
			result += ARCHIVE_PART;
			result += SOURCE_PART + source + Constants.AMPERSEND;
			result += DESTINATION_PART + destination + Constants.AMPERSEND;
			result += EVENT_TYPE_PART;
		} catch (Exception e) {
			LOG.error("Exceptoin while constructing perfSONAR URI for obtaining Throughput event", e);
		}
		return result;
	}
	
	public String constructPerfSONARUriForThroughputData(String source, String baseUri) {
		LOG.info("Helper method to construct the perfSONAR URI for Throughput data");
		String result = "";
		try {
			if (baseUri.isEmpty() 
					|| baseUri == null) {
				throw new RuntimeException("Either base uri is null or empty");
			}
			
			if (baseUri.length() == 0) {
				throw new RuntimeException("Either base uri is null or empty");
			}
			
			result += PROTOCOL;
			result += ENDPOINT; // It should be source
			result += baseUri;
			result += THROUGHPUT_PART;
			result += TIME_RANGE_PART + Constants.TIME_IN_HOURS + Constants.AMPERSEND;
			Long currentTime = System.currentTimeMillis() / 1000L;
			Long startTime = currentTime - Constants.TIME_FOR_ONE_HOUR;
			result += START_TIME_PART + startTime;
		} catch (Exception e) {
			LOG.error("Exception while constructing perfSONAR base uri for obtaining throughput data", e);
		}
		return result;
	}

}
