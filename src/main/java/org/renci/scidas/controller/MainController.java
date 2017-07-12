package org.renci.scidas.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.renci.scidas.helper.RequestBodyParserHelper;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.DataSetAndOffersRequest;
import org.renci.scidas.pojo.OfferRankPOJO;
import org.renci.scidas.processor.NetworkOptimizerServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {
	
	public static final Logger LOG = Logger.getLogger(MainController.class);
	
	@Autowired
	@Qualifier("RequestBodyParserHelper")
	public RequestBodyParserHelper requestBodyParserHelper;
	
	@Autowired
	@Qualifier("NetworkOptimizerServiceProcessor")
	public NetworkOptimizerServiceProcessor networkOptimizerServiceProcessor;
	
	/*@RequestMapping(value = "/test", method = RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public String test() {
		return "Test";
	}*/
	
	@RequestMapping(value = "/networkOptimizer", method = RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	@ResponseBody
	public OfferRankPOJO microServiceAPI(@RequestBody DataSetAndOffersRequest request) {
		LOG.info("Micro Service API POST Call for network optimization");
		OfferRankPOJO result = null;
		try {
			DataSetAndOffers refinedRequest = requestBodyParserHelper.convertDataSetAndOffersRequestToPOJO(request);
			result = networkOptimizerServiceProcessor.microServiceProcessor(refinedRequest);
		} catch (Exception e) {
			LOG.error("Exception while Micro Service API POST Call for network optimization", e);
		}
		return result;
	}
	
}
