package org.renci.scidas.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mesos.v1.Protos.Offer;
import org.apache.mesos.v1.scheduler.Protos.Event;
import org.renci.scidas.constants.Constants;
import org.renci.scidas.consumer.ShellConsumer;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.DataSetAndOffersForProtobuf;
import org.renci.scidas.pojo.DataSetAndOffersRequest;
import org.renci.scidas.pojo.DestinationObject;
import org.renci.scidas.pojo.RefinedRequest;
import org.renci.scidas.pojo.RequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("RequestBodyParserHelper")
public class RequestBodyParserHelper {

	public static final Logger LOG = Logger.getLogger(RequestBodyParserHelper.class);
	
	@Autowired
	@Qualifier("ShellConsumer")
	ShellConsumer shellConsumer;

	/**
	 * Method to convert the request parameter to
	 * help in processing
	 * @param request
	 * @return
	 */
	public DataSetAndOffers convertDataSetAndOffersRequestToPOJO(DataSetAndOffersRequest request) {
		DataSetAndOffers result = null;
		try {
			result = new DataSetAndOffers();
			if (request.getSourceDataSites().contains(Constants.COMMA)) {
				List<String> listOfSources = Arrays.asList(request.getSourceDataSites().split(Constants.COMMA));
				result.setSources(listOfSources);
			} else if (request.getSourceDataSites().length() == 0) {
				result.setSources(null);
			} else {
				List<String> listOfSources = Arrays.asList(request.getSourceDataSites());
				result.setSources(listOfSources);
			}
			
			if (request.getOffers().contains(Constants.COMMA)) {
				List<String> listOfDestinations = Arrays.asList(request.getOffers().split(Constants.COMMA));
				result.setDestinations(listOfDestinations);
			} else if (request.getOffers().length() == 0) {
				result.setDestinations(null);
			} else {
				List<String> listOfDestinations = Arrays.asList(request.getOffers());
				result.setDestinations(listOfDestinations);
			}
		} catch (Exception e) {
			LOG.error("Exception while converting the Data Set Request to POJO", e);
		}
		return result;
	}
	
	public RefinedRequest convertRequestObjectToPOJO(RequestObject request) {
		LOG.info("Method to convert the request object to a refined request");
		RefinedRequest result = null;
		try {
			result = new RefinedRequest();
			// Master is assumed to be comma separated for multiple masters
			if (request.getMaster().contains(Constants.COMMA)) {
				List<String> listOfSources = Arrays.asList(request.getMaster().split(Constants.COMMA));
				List<String> list = new ArrayList<String>();
				for (String data: listOfSources) {
					if (data.contains(Constants.COLON)) {
						String temp = data.split(Constants.COLON)[0];
						if (!list.contains(temp)) {
							list.add(temp);
						}
					} else {
						if (!list.contains(data)) {
							list.add(data);
						}
					}
				}
				result.setSource(list);
			} else if (request.getMaster().length() == 0) {
				throw new Exception("Missing Master IP input");
			} else {
				List<String> listOfSources = Arrays.asList(request.getMaster());
				List<String> list = new ArrayList<String>();
				for (String data: listOfSources) {
					if (data.contains(Constants.COLON)) {
						String temp = data.split(Constants.COLON)[0];
						if (!list.contains(temp)) {
							list.add(temp);
						}
					} else {
						if (!list.contains(data)) {
							list.add(data);
						}
					}
				}
				result.setSource(list);
			}
			
			if (!request.getAgent().isEmpty() && request.getAgent() != null) {
				if (request.getAgent().contains(Constants.COLON)) {
					result.setAgent(request.getAgent().split(Constants.COLON)[0]);
				} else {
					result.setAgent(request.getAgent());
				}
			} else {
				throw new Exception("Missing Agent IP input");
			}
			
			if (!request.getData().isEmpty() && request.getData() != null) {
				result.setData(request.getData());
			} else {
				throw new Exception("Missing Data Set input");
			}
		} catch (Exception e) {
			LOG.error("Exception while converting the Request Object to POJO", e);
		}
		return result;
	}
	
	/**
	 * Method to convert the request parameter to
	 * identify the data set source and offer destinations
	 * @param event
	 * @return
	 */
	public DataSetAndOffersForProtobuf convertEventOfferstoPOJO(Event event) {
		DataSetAndOffersForProtobuf result = null;
		LOG.info("Method to convert Event request to POJO");
		try {
			result = new DataSetAndOffersForProtobuf();
			
			// The Sources of the data site is to be queried from
			// IRODS Server, The ShellConsumer class helps to query
			// and identify the data site
			/*
			 * 
			 * Part of the code to update the sourceDataSites
			 * to DataSetAndOffers Object
			 * 
			 */
			
			for(Offer offer: event.getOffers().getOffersList()) {
				String agentIP = offer.getHostname();
				// An method call is needed here which interacts 
				// with a reverse mapping of (agent ip to master ip) 
				// and identify the master ips
				//
				// Note: For the time being I am adding the agent ip to the 
				// 		 destination list
				if(agentIP.length() != 0) {
					if (result.getDestinations() == null) {
						result.setDestinations(new ArrayList<DestinationObject>());
					}
					DestinationObject temp = new DestinationObject();
					temp.setAgentIp(agentIP);
					temp.setMasterIp(agentIP);
					result.getDestinations().add(temp);
				}
			}
		} catch (Exception e) {
			LOG.error("Exception while converting to POJO", e);
		}
		return result;
	}

}
