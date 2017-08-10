package org.renci.scidas.helper;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("PropertyHelper")
public class PropertyHelper {
	
	public static final Logger LOG = Logger.getLogger(PropertyHelper.class);
	
	/**
	 * Method to return the private key file location
	 * which is used in Shell Consumer
	 * @return
	 */
	public File getKeyFile() {
		File result = null;
		LOG.info("Get the private key information from resource folder");
		try {
			// Get file from resources folder
			ClassLoader loader = getClass().getClassLoader();
			result = new File(loader.getResource("properties/keys/id_rsa").getFile());
		} catch (Exception e) {
			LOG.error("Exception while accessing resource folder for private key", e);
		}
		return result;
	}

}
