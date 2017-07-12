package org.renci.scidas.helper;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.renci.scidas.constants.Constants;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.DataSetAndOffersRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("RequestBodyParserHelper")
public class RequestBodyParserHelper {

	public static final Logger LOG = Logger.getLogger(RequestBodyParserHelper.class);

	public DataSetAndOffers convertDataSetAndOffersRequestToPOJO(DataSetAndOffersRequest request) {
		DataSetAndOffers result = new DataSetAndOffers();
		try {
			if (request.getSources().contains(Constants.SEMICOLON)) {
				List<String> listOfSources = Arrays.asList(request.getSources().split(Constants.SEMICOLON));
				result.setSources(listOfSources);
			} else if (request.getSources().length() == 0) {
				result.setSources(null);
			} else {
				List<String> listOfSources = Arrays.asList(request.getSources());
				result.setSources(listOfSources);
			}
			
			if (request.getDestinations().contains(Constants.SEMICOLON)) {
				List<String> listOfDestinations = Arrays.asList(request.getDestinations().split(Constants.SEMICOLON));
				result.setDestinations(listOfDestinations);
			} else if (request.getDestinations().length() == 0) {
				result.setDestinations(null);
			} else {
				List<String> listOfDestinations = Arrays.asList(request.getDestinations());
				result.setDestinations(listOfDestinations);
			}
		} catch (Exception e) {
			LOG.error("Exception while converting the Data Set Request to POJO", e);
		}
		return result;
	}

}
