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
	public File getConfFile() {
		File result = null;
		LOG.info("Get the mapping configuration file from resource folder");
		try {
			// Get file from resources folder
			ClassLoader loader = getClass().getClassLoader();
			result = new File(loader.getResource("conf/perfSONAR.conf").getFile());
		} catch (Exception e) {
			LOG.error("Exception while accessing resource folder for private key", e);
		}
		return result;
	}

}
