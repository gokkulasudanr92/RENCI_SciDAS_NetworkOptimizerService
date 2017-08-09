package org.renci.scidas.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.renci.scidas.consumer.ShellConsumer;
import org.renci.scidas.helper.RequestBodyParserHelper;
import org.renci.scidas.pojo.DataSetAndOffers;
import org.renci.scidas.pojo.DataSetAndOffersRequest;
import org.renci.scidas.pojo.OfferRankPOJO;
import org.renci.scidas.processor.NetworkOptimizerServiceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.tutorial.AddressBookProtos;

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
	
	@Autowired
	@Qualifier("ShellConsumer")
	ShellConsumer shellConsumer;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		shellConsumer.irodsDomainIdentifier();
		return "This is a test api";
	}
	
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
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET, 
			produces = { MediaType.APPLICATION_OCTET_STREAM_VALUE, "application/x-protobuf" })
	@ResponseBody
	public AddressBookProtos.Person test2() {
		return AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("Sam")
                .setEmail("sam@sampullara.com")
                .addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("415-555-1212")
                        .setType(AddressBookProtos.Person.PhoneType.MOBILE)
                        .build())
                .build();
	}
}
