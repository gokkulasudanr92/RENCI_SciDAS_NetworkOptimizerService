package org.renci.scidas.controller;

import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.renci.scidas.consumer.PerfSONARRestConsumer;
import org.renci.scidas.pojo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {
	
	public static final Logger LOG = Logger.getLogger(MainController.class);
	
	@Autowired
	@Qualifier("PerfSONARRestConsumer")
	public PerfSONARRestConsumer perfsonarConsumer;

	/*@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(ModelMap model) {
		model.addAttribute("msg", "JCG Hello World!");
		return "helloWorld";
	}
	
	@RequestMapping(value = "/displayMessage/{msg}", method = RequestMethod.GET)
	public String displayMessage(@PathVariable String msg, ModelMap model) {
		model.addAttribute("msg", msg);
		return "helloWorld";
	}*/
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public Test test() {
		LOG.info("GET Method for test() method");
		Test data = new Test();
		data.setName("Network Optimizer");
		data.setDescription("This is a test POJO Object");
		return data;
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	@Produces("application/json")
	@ResponseBody
	public String test2() {
		LOG.info("GET Method for test2() method");
		String result = perfsonarConsumer.getThroughput();
		return result;
	}
	
}
