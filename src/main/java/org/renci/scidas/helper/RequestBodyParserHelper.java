package org.renci.scidas.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mesos.v1.Protos.Offer;
import org.apache.mesos.v1.scheduler.Protos.Event;
import org.renci.scidas.constants.Constants;
import org.renci.scidas.pojo.DataSetAndOffersForProtobuf;
import org.renci.scidas.pojo.DestinationObject;
import org.renci.scidas.pojo.RefinedRequest;
import org.renci.scidas.pojo.RequestJSON;
import org.renci.scidas.pojo.RequestObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("RequestBodyParserHelper")
public class RequestBodyParserHelper {

	public static final Logger LOG = Logger.getLogger(RequestBodyParserHelper.class);

	/**
	* Method to convert the request parameter to
	* help in processing
	* @param request
	* @return
	*/
	public List<RefinedRequest> convertRequestJSONtoRefinedRequest(RequestJSON input) {
		LOG.info("Method to convert the RequestJSON to a list of Refined Request");
		List<RefinedRequest> result = null;
		try {
			result = new ArrayList<RefinedRequest>();
			if (input == null || input.getOffers().isEmpty() || input.getOffers() == null) {
				throw new Exception("Missing Input Data from POST Request");
			}
			
			for (RequestObject object: input.getOffers()) {
				RefinedRequest temp = new RefinedRequest();
				temp.setOffer(object);
				temp.setData(input.getData());
				
				if (object.getMaster().contains(Constants.COLON)) {
					temp.setSource(object.getMaster().split(Constants.COLON)[0]);
				} else {
					temp.setSource(object.getMaster());
				}
				
				temp.setDataNodeMap(new HashMap<String, List<String>>());
				result.add(temp);
			}
		} catch (Exception e) {
			LOG.error("Exception while converting the RequestJSON to a list of Refined Request", e);
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
