package org.renci.scidas.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.apache.mesos.v1.scheduler.Protos.Event;
import org.renci.scidas.helper.RequestBodyParserHelper;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.DataSetAndOffersForProtobuf;
import org.renci.scidas.pojo.DataSetAndOffersRequest;
import org.renci.scidas.pojo.OfferRankPOJO;
import org.renci.scidas.pojo.OfferRankPOJOForProtobuf;
import org.renci.scidas.processor.NetworkOptimizerServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/")
public class V1Controller {
	
	public static final Logger LOG = Logger.getLogger(V1Controller.class);
	
	@Autowired
	@Qualifier("RequestBodyParserHelper")
	public RequestBodyParserHelper requestBodyParserHelper;
	
	@Autowired
	@Qualifier("NetworkOptimizerServiceProcessor")
	public NetworkOptimizerServiceProcessor networkOptimizerServiceProcessor;
	
	/**
	 * Controller call for micro service API (JSON)
	 * @param request
	 * @return
	 */
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
	
	/**
	 * Controller call for micro service API (Protobuf)
	 * @param event
	 * @return
	 */
	@RequestMapping(value = "/microservice", method = RequestMethod.POST,
			consumes = "application/x-protobuf", produces = "application/x-protobuf")
	public Event microServiceAPIForProtobuf(@RequestBody Event event) {
		LOG.info("Micro Service API POST Calll for network optimization");
		try {
			DataSetAndOffersForProtobuf refinedRequest = requestBodyParserHelper.convertEventOfferstoPOJO(event);
			if (refinedRequest.getSources() == null 
					|| refinedRequest.getSources().size() == 0
					|| refinedRequest.getDestinations() == null 
					|| refinedRequest.getDestinations().size() == 0) {
				throw new RuntimeException("Either Data set or Offers list was empty");
			}
			OfferRankPOJOForProtobuf rankList = networkOptimizerServiceProcessor.microServiceProcessorForProtobuf(refinedRequest);
			networkOptimizerServiceProcessor.arrangeOffers(event, rankList);
		} catch (Exception e) {
			LOG.error("Exception while Micro Service AOI POST Call for network optimization", e);
		}
		return event;
	}
	
}
