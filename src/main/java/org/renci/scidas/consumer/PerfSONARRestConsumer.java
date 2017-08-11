package org.renci.scidas.consumer;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.renci.scidas.pojo.ThroughputDataJSON;
import org.renci.scidas.pojo.ThroughputEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Configuration
@Qualifier("PerfSONARRestConsumer")
public class PerfSONARRestConsumer {

	public static final Logger LOG = Logger.getLogger(PerfSONARRestConsumer.class);
	
	/**
	 * Jersey Consumer Method call to perfSONAR
	 * @param uri
	 * @return
	 */
	public ThroughputEvent getURIFromThroughput(String uri) {
		ThroughputEvent result = null;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(uri);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			String jsonString = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			TypeFactory typeFactory = mapper.getTypeFactory();
			CollectionType collectionType = typeFactory.constructCollectionType(List.class, 
					ThroughputEvent.class);
			List<ThroughputEvent> list = mapper.readValue(jsonString, collectionType);
			result = list.get(0);
		} catch (Exception e) {
			LOG.error("Exception while consuming the endpoint URI from throughput test", e);
		}
		return result;
	}
	
	/**
	 * Jersey Consumer Method call to perfSONAR
	 * @param uri
	 * @return
	 */
	public List<ThroughputDataJSON> getURIForThroughputData(String uri) {
		List<ThroughputDataJSON> result = null;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(uri);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			String jsonString = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			TypeFactory typeFactory = mapper.getTypeFactory();
			CollectionType collectionType = typeFactory.constructCollectionType(List.class, 
					ThroughputDataJSON.class);
			result = mapper.readValue(jsonString, collectionType);
		} catch (Exception e) {
			LOG.error("Exception while consuming the endpoint URI from throughput test", e);
		}
		return result;
	}

}
