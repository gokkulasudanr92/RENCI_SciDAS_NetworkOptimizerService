package org.renci.scidas.controller;

import javax.ws.rs.Produces;

import org.renci.scidas.pojo.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

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
		Test data = new Test();
		data.setName("Network Optimizer");
		data.setDescription("This is a test POJO Object");
		return data;
	}
	
}
