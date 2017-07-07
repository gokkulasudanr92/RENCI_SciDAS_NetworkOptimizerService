package org.renci.scidas.consumer;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Configuration
@Qualifier("PerfSONARRestConsumer")
public class PerfSONARRestConsumer {
	
	public static final Logger LOG = Logger.getLogger(PerfSONARRestConsumer.class);
	public static final String URI = "http://139.62.242.122/esmond/perfsonar/archive/?event-type=throughput";
	
	public String getThroughput() {
		String result = "";
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(URI);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			
			result = response.getEntity(String.class);
		} catch (Exception e) {
			LOG.error("Exception while consuming the throughput information", e);
		}
		return result;
	}

}
