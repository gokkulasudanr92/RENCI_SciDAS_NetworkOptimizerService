package org.renci.scidas.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.renci.scidas.consumer.PerfSONARRestConsumer;
import org.renci.scidas.helper.ConstructURIHelper;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.OfferRankPOJO;
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

	public OfferRankPOJO singleDataSiteLogic(List<String> listOfSourcesAsString,
			List<String> listOfDestinationsAsString) {
		LOG.info("Method to execute Single Data Site Logic Algorithm");
		OfferRankPOJO result = null;
		try {
			// Here there should a query from IRODS to identify Dataset IPs
			// For the time being Using direct IPs
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

}
