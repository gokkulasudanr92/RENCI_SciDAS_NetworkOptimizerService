package org.renci.scidas.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.renci.scidas.constants.Constants;
import org.renci.scidas.helper.PropertyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Qualifier("MapConfig")
public class MapConfig {
	public static final Logger LOG = Logger.getLogger(MapConfig.class);
	
	@Autowired
	@Qualifier("PropertyHelper")
	PropertyHelper propertyHelper;
	
	private Map<String, String> MasterPerfSONARMap;
	
	public Map<String, String> getMasterPerfSONARMap() {
		return MasterPerfSONARMap;
	}

	public void setMasterPerfSONARMap(Map<String, String> masterPerfSONARMap) {
		MasterPerfSONARMap = masterPerfSONARMap;
	}

	@Bean
	public Integer configMap() {
		LOG.info("Configuring Master IP to PerfSONAR IP...");
		this.MasterPerfSONARMap = new HashMap<String, String>();
		try {
			File file = propertyHelper.getConfFile();
			if (file == null) {
				throw new Exception("Configuration file is missing");
			}
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String line;
			while ((line = reader.readLine()) != null) {
				String split_list[] = line.split(Constants.COLON);
				String master = split_list[0];
				String perfSONAR = split_list[1];
				this.MasterPerfSONARMap.put(master, perfSONAR);
			}
			fileReader.close();
		} catch (Exception e) {
			LOG.error("Exception while obtaining the configuration file", e);
			return -1;
		}
		return 0;
	}

}
