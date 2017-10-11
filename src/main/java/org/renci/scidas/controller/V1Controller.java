package org.renci.scidas.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mesos.v1.scheduler.Protos.Event;
import org.renci.scidas.helper.RequestBodyParserHelper;
import org.renci.scidas.pojo.DataSetAndOffersForProtobuf;
import org.renci.scidas.pojo.OfferRank;
import org.renci.scidas.pojo.OfferRankPOJOForProtobuf;
import org.renci.scidas.pojo.RefinedRequest;
import org.renci.scidas.pojo.RequestJSON;
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
	RequestBodyParserHelper requestBodyParserHelper;
	
	@Autowired
	@Qualifier("NetworkOptimizerServiceProcessor")
	NetworkOptimizerServiceProcessor networkOptimizerServiceProcessor;
	
	/**
	 * Server check up test
	 */
	@RequestMapping(value = "/isUp", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String test() {
		return "The server is up";
	}
	
	/**
	 * Controller call for micro service API (for IRODS)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/microservice", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	@ResponseBody
	public OfferRank microServiceAPIUpdate(@RequestBody RequestJSON request) {
		LOG.info("Micro Service API POST Call for network optimization");
		OfferRank result = null;
		try {
			List<RefinedRequest> input = requestBodyParserHelper.convertRequestJSONtoRefinedRequest(request);
			result = networkOptimizerServiceProcessor.microServiceUpdate(input);
		} catch (Exception e) {
			LOG.error("Exception while ");
		}
		return result;
	}
	
	/**
	 * Controller call for micro service API (Protobuf)
	 * @param event
	 * @return
	 */
	@RequestMapping(value = "/microserviceforprotobuf", method = RequestMethod.POST,
			consumes = "application/x-protobuf", produces = "application/x-protobuf")
	@ResponseBody
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
