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

}
