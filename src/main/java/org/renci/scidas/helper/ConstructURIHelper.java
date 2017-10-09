package org.renci.scidas.helper;

import org.apache.log4j.Logger;
import org.renci.scidas.constants.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("ConstructURIHelper")
public class ConstructURIHelper {
	
	public static final Logger LOG = Logger.getLogger(ConstructURIHelper.class);
	// PerfSONAR Part
	public static final String PROTOCOL = "http://";
	public static final String ARCHIVE_PART = "/esmond/perfsonar/archive/?";
	public static final String SOURCE_PART = "source=";
	public static final String DESTINATION_PART = "destination=";
	public static final String EVENT_TYPE_PART = "event-type=throughput";
	public static final String THROUGHPUT_PART = "throughput/base?";
	public static final String TIME_RANGE_PART = "time-range=";
	public static final String START_TIME_PART = "time-start=";
	// iRODS Part
	public static final String IRODS_ENDPOINT = "http://scidas-dev.renci.org:8080/v1/";
	public static final String GET_LOGICAL_LOCATION = "getLogicalLocation?";
	public static final String FILE_NAME = "filename=";
	public static final String GET_LOGICAL_LOCATION_ENDPART = "&match_exact=true&include_trash=false";
	public static final String GET_REPLICAS = "getReplicas?";
	public static final String GET_HOST_NODE = "getHostNode?";
	public static final String RESOURCE_NAME = "resource_name=";
	
	/**
	 * Method to construct the URI to make the call to perfSONAR
	 * This obtains the base-uri from perfSONAR which is needed
	 * to get the throughput value.
	 * @param source
	 * @param destination
	 * @return
	 */
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
			result += source; // This should be the perfSONAR IP
			result += ARCHIVE_PART;
			result += SOURCE_PART + source + Constants.AMPERSEND;
			result += DESTINATION_PART + destination + Constants.AMPERSEND;
			result += EVENT_TYPE_PART;
			System.out.println(result);
		} catch (Exception e) {
			LOG.error("Exceptoin while constructing perfSONAR URI for obtaining Throughput event", e);
		}
		return result;
	}
	
	/**
	 * Method call to construct the base uri to make 
	 * a call to perfSONAR.
	 * @param source
	 * @param baseUri
	 * @return
	 */
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
			result += source; // This should be the perfSONAR IP
			result += baseUri;
			result += THROUGHPUT_PART;
			result += TIME_RANGE_PART + Constants.TIME_IN_HOURS + Constants.AMPERSEND;
			Long currentTime = System.currentTimeMillis() / 1000L;
			Long startTime = currentTime - Constants.TIME_FOR_TWO_HOUR;
			result += START_TIME_PART + startTime;
			System.out.println(result);
		} catch (Exception e) {
			LOG.error("Exception while constructing perfSONAR base uri for obtaining throughput data", e);
		}
		return result;
	}
	
	
	public String constructLogicalLocationURI(String filename) {
		LOG.info("Method to construct the Logical Location URI for the given file name");
		String result = null;
		try {
			if (filename.isEmpty() || filename == null) {
				throw new Exception("File Name is Missing");
			}
			result = IRODS_ENDPOINT + GET_LOGICAL_LOCATION + FILE_NAME;
			result += filename;
			result += GET_LOGICAL_LOCATION_ENDPART;
		} catch (Exception e) {
			LOG.error("Exception while constructing the Logical Location URI", e);
		}
		return result;
	}
	
	public String constructGetReplicasURI(String path) {
		LOG.info("Method to construct the get replicas URI for the given file path");
		String result = null;
		try {
			if (path.isEmpty() || path == null) {
				throw new Exception("Path is missing");
			}
			result = IRODS_ENDPOINT + GET_REPLICAS + FILE_NAME;
			result += path;
		} catch (Exception e) {
			LOG.error("Exception while constructing the Get Replicas URI", e);
		}
		return result;
	}
	
	public String constructGetHostNodeURI(String resource) {
		LOG.info("Method to construct the get host node URI for the given reource name");
		String result = null;
		try {
			if (resource.isEmpty() || resource == null) {
				throw new Exception("Missing Resource Name from IRods");
			}
			result = IRODS_ENDPOINT + GET_HOST_NODE + RESOURCE_NAME;
			result += resource;
		} catch (Exception e) {
			LOG.error("Exception while constructing the Get Host Node URI", e);
		}
		return result;
	}

}
