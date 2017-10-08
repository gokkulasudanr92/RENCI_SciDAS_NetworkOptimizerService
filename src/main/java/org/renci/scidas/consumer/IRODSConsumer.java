package org.renci.scidas.consumer;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.renci.scidas.helper.ConstructURIHelper;
import org.renci.scidas.pojo.IRODSFileName;
import org.renci.scidas.pojo.IRODSHostNode;
import org.renci.scidas.pojo.IRODSReplicas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Configuration
@Qualifier("IRODSConsumer")
public class IRODSConsumer {
	
	public static final Logger LOG = Logger.getLogger(IRODSConsumer.class);
	
	@Autowired
	@Qualifier("ConstructURIHelper")
	ConstructURIHelper helper;
	
	public IRODSFileName getLogicalLocation(String input) {
		LOG.info("Method to GET the logical location from iRODS");
		IRODSFileName result = null;
		try {
			String uri = helper.constructLogicalLocationURI(input);
			Client client = Client.create();
			WebResource webResource = client.resource(uri);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			String jsonString = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(jsonString, IRODSFileName.class);
		} catch (Exception e) {
			LOG.error("Exception while getting logical location from iRODS Consumer", e);
		}
		return result;
	}
	
	public IRODSReplicas getReplicas(String input) {
		LOG.info("Method to GET the replicas from iRODS");
		IRODSReplicas result = null;
		try {
			String uri = helper.constructGetReplicasURI(input);
			Client client = Client.create();
			WebResource webResource = client.resource(uri);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			String jsonString = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(jsonString, IRODSReplicas.class);
		} catch (Exception e) {
			LOG.error("Exception while getting the replicas from iRODS Consumer", e);
		}
		return result;
	}
	
	public IRODSHostNode getHostNode(String input) {
		LOG.info("Method to GET the host node from iRODS");
		IRODSHostNode result = null;
		try {
			String uri = helper.constructGetHostNodeURI(input);
			Client client = Client.create();
			WebResource webResource = client.resource(uri);
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);

			String jsonString = response.getEntity(String.class);
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(jsonString, IRODSHostNode.class);
		} catch (Exception e) {
			LOG.error("Exception while getting the host node from iRODS Consumer", e);
		}
		return result;
	}

}
