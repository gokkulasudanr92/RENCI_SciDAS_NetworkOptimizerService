package org.renci.scidas.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mesos.v1.Protos.Offer;
import org.apache.mesos.v1.scheduler.Protos.Event;
import org.apache.mesos.v1.scheduler.Protos.Event.Offers;
import org.renci.scidas.consumer.PerfSONARRestConsumer;
import org.renci.scidas.helper.ConstructURIHelper;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.DataSetAndOffersForProtobuf;
import org.renci.scidas.pojo.DestinationObject;
import org.renci.scidas.pojo.OfferRankPOJO;
import org.renci.scidas.pojo.OfferRankPOJOForProtobuf;
import org.renci.scidas.pojo.ThroughputDataJSON;
import org.renci.scidas.pojo.ThroughputDataPOJO;
import org.renci.scidas.pojo.ThroughputEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("NetworkOptimizerServiceProcessor")
public class NetworkOptimizerServiceProcessor {

	public static final Logger LOG = Logger.getLogger(NetworkOptimizerServiceProcessor.class);

	@Autowired
	@Qualifier("PerfSONARRestConsumer")
	public PerfSONARRestConsumer perfsonarConsumer;

	@Autowired
	@Qualifier("ConstructURIHelper")
	public ConstructURIHelper constructURIHelper;

	/**
	 * Method to rank the offers for the data set
	 * @param data
	 * @return
	 */
	public OfferRankPOJO microServiceProcessor(DataSetAndOffers data) {
		LOG.info("Processing Data Set and Offers...");
		OfferRankPOJO result = null;
		try {
			List<String> listOfSourcesAsString = data.getSources();
			List<String> listOfDestinationsAsString = data.getDestinations();

			if (listOfSourcesAsString == null || listOfDestinationsAsString == null) {
				throw new RuntimeException("Either source list or destination list is null or empty");
			}

			if (listOfSourcesAsString.size() == 0 || listOfDestinationsAsString.size() == 0) {
				throw new RuntimeException("Either source list or destination list is null or empty");
			}

			if (listOfSourcesAsString.size() == 1) {
				// Single Data Site to multiple Offers
				result = singleDataSiteLogic(listOfSourcesAsString, listOfDestinationsAsString);
			} else {
				// Multiple Data Sites to multiple Offers
			}

			if (result == null) {
				throw new RuntimeException("The processing failed to obtain data from perfSONAR");
			}
		} catch (Exception e) {
			LOG.error("Exception while processing for the microservice", e);
		}
		return result;
	}

	/**
	 * Method to rank for Single Data Set Logic
	 * @param listOfSourcesAsString
	 * @param listOfDestinationsAsString
	 * @return
	 */
	public OfferRankPOJO singleDataSiteLogic(List<String> listOfSourcesAsString,
			List<String> listOfDestinationsAsString) {
		LOG.info("Method to execute Single Data Site Logic Algorithm");
		OfferRankPOJO result = null;
		try {
			Map<String, String> mapOfSources = new HashMap<String, String>();
			for (String source : listOfSourcesAsString) {
				mapOfSources.put(source, source);
			}

			Map<String, String> mapOfDestinations = new HashMap<String, String>();
			for (String destination : listOfDestinationsAsString) {
				mapOfDestinations.put(destination, destination);
			}

			result = new OfferRankPOJO();
			for (String source : mapOfSources.keySet()) {
				result.setSourceDataSite(source);
				result.setOffers(new ArrayList<ThroughputDataPOJO>());

				for (String destination : mapOfDestinations.keySet()) {
					String uriToConsumeForThroughputEvent = constructURIHelper.constructPerfSONARUriForThroughputEvent(
							mapOfSources.get(source), mapOfDestinations.get(destination));

					// Consume the PerfSONAR REST API to abstract Base URIs
					ThroughputEvent event = perfsonarConsumer.getURIFromThroughput(uriToConsumeForThroughputEvent);

					// Get the latest network throughput result for the base-uri
					// from perfSONAR
					String uriToConsumeForThroughputData = constructURIHelper
							.constructPerfSONARUriForThroughputData(mapOfSources.get(source), event.getUri());
					List<ThroughputDataJSON> throughputDataList = perfsonarConsumer
							.getURIForThroughputData(uriToConsumeForThroughputData);
					ThroughputDataPOJO data = this.convertToThroughputDataPOJO(destination, mapOfDestinations,
							throughputDataList.get(throughputDataList.size() - 1));

					// Add the result to the response Object list
					System.out.println("The current time stamp is: "
							+ throughputDataList.get(throughputDataList.size() - 1).getTs());
					result.getOffers().add(data);
				}
			}

			Collections.sort(result.getOffers());
		} catch (Exception e) {
			LOG.error("Exception while executing single data site logic algorithm", e);
		}
		return result;
	}

	/**
	 * Convert the result to ThroughputDataPOJO
	 * @param destination
	 * @param mapOfDestinations
	 * @param data
	 * @return
	 */
	public ThroughputDataPOJO convertToThroughputDataPOJO(String destination, Map<String, String> mapOfDestinations,
			ThroughputDataJSON data) {
		LOG.info("Method to convert Object to ThroughputData POJO");
		ThroughputDataPOJO result;
		try {
			if (data == null) {
				throw new RuntimeException("Failed to get data from perfSONAR REST Consumer");
			}

			result = new ThroughputDataPOJO();
			result.setDestination(mapOfDestinations.get(destination));
			result.setOffer(destination);
			result.setValue(data.getVal());
		} catch (Exception e) {
			LOG.error("Exception while converting Object to ThroughputData POJO", e);
			result = new ThroughputDataPOJO();
		}
		return result;
	}
	
	/**
	 * Method to rank the offers for the data set
	 * @param data
	 * @return
	 */
	public OfferRankPOJOForProtobuf microServiceProcessorForProtobuf(DataSetAndOffersForProtobuf data) {
		LOG.info("Processing Data Set and Offers...");
		OfferRankPOJOForProtobuf result = null;
		try {
			List<String> listOfSources = data.getSources();
			List<DestinationObject> listOfDestinations = data.getDestinations();

			if (listOfSources == null || listOfDestinations == null) {
				throw new RuntimeException("Either source list or destination list is null or empty");
			}

			if (listOfSources.size() == 0 || listOfDestinations.size() == 0) {
				throw new RuntimeException("Either source list or destination list is null or empty");
			}

			if (listOfSources.size() == 1) {
				// Single Data Site to multiple Offers
				result = singleDataSiteLogicForProtobuf(listOfSources, listOfDestinations);
			} else {
				// Multiple Data Sites to multiple Offers
			}

			if (result == null) {
				throw new RuntimeException("The processing failed to obtain data from perfSONAR");
			}
		} catch (Exception e) {
			LOG.error("Exception while processing for the microservice", e);
		}
		return result;
	}

	/**
	 * Method to rank for Single Data Set Logic
	 * @param listOfSourcesAsString
	 * @param listOfDestinationsAsString
	 * @return
	 */
	public OfferRankPOJOForProtobuf singleDataSiteLogicForProtobuf(List<String> listOfSources,
			List<DestinationObject> listOfDestinations) {
		LOG.info("Method to execute Single Data Site Logic Algorithm");
		OfferRankPOJOForProtobuf result = null;
		try {
			Map<String, String> mapOfSources = new HashMap<String, String>();
			for (String source : listOfSources) {
				mapOfSources.put(source, source);
			}

			Map<String, String> mapOfDestinations = new HashMap<String, String>();
			for (DestinationObject destination : listOfDestinations) {
				mapOfDestinations.put(destination.getAgentIp(), destination.getMasterIp());
			}

			result = new OfferRankPOJOForProtobuf();
			for (String source : mapOfSources.keySet()) {
				result.setSourceDataSite(source);
				result.setOffers(new ArrayList<ThroughputDataPOJO>());

				for (String destination : mapOfDestinations.keySet()) {
					String uriToConsumeForThroughputEvent = constructURIHelper.constructPerfSONARUriForThroughputEvent(
							mapOfSources.get(source), mapOfDestinations.get(destination));

					// Consume the PerfSONAR REST API to abstract Base URIs
					ThroughputEvent event = perfsonarConsumer.getURIFromThroughput(uriToConsumeForThroughputEvent);

					// Get the latest network throughput result for the base-uri
					// from perfSONAR
					String uriToConsumeForThroughputData = constructURIHelper
							.constructPerfSONARUriForThroughputData(mapOfSources.get(source), event.getUri());
					List<ThroughputDataJSON> throughputDataList = perfsonarConsumer
							.getURIForThroughputData(uriToConsumeForThroughputData);
					ThroughputDataPOJO data = this.convertToThroughputDataPOJOForProtobuf(destination, mapOfDestinations,
							throughputDataList.get(throughputDataList.size() - 1));

					// Add the result to the response Object list
					result.getOffers().add(data);
				}
			}

			Collections.sort(result.getOffers());
		} catch (Exception e) {
			LOG.error("Exception while executing single data site logic algorithm", e);
		}
		return result;
	}

	/**
	 * Convert the result to ThroughputDataPOJO
	 * @param destination
	 * @param mapOfDestinations
	 * @param data
	 * @return
	 */
	public ThroughputDataPOJO convertToThroughputDataPOJOForProtobuf(String destination, Map<String, String> mapOfDestinations,
			ThroughputDataJSON data) {
		LOG.info("Method to convert Object to ThroughputData POJO");
		ThroughputDataPOJO result = null;
		try {
			if (data == null) {
				throw new RuntimeException("Failed to get data from perfSONAR REST Consumer");
			}

			result = new ThroughputDataPOJO();
			result.setDestination(mapOfDestinations.get(destination));
			result.setOffer(destination);
			result.setValue(data.getVal());
		} catch (Exception e) {
			LOG.error("Exception while converting Object to ThroughputData POJO", e);
		}
		return result;
	}
	
	/**
	 * Method arrange the offers within event message
	 * @param event
	 * @param list
	 */
	public void arrangeOffers(Event event, OfferRankPOJOForProtobuf list) {
		LOG.info("Method call to arrange offers within the Event Message");
		try {
			List<Offer> offers = event.getOffers().getOffersList();
			List<Offer> updatedList = new ArrayList<Offer>();
			for (ThroughputDataPOJO item: list.getOffers()) {
				for (Offer offer: offers) {
					if (item.getOffer().equalsIgnoreCase(offer.getHostname())) {
						updatedList.add(offer);
						break;
					}
				}
			}
			Offers updatedOffers = Offers.newBuilder().addAllOffers(updatedList).build();
			event.toBuilder().setOffers(updatedOffers);
		} catch (Exception e) {
			LOG.error("Exception while arranging objects within the Event Object");
		}
	}

}
