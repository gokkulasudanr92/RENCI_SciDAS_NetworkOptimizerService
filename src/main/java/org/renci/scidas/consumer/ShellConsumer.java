package org.renci.scidas.consumer;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.mesos.v1.scheduler.Protos.Event;
import org.renci.scidas.helper.PropertyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Configuration
@Qualifier("ShellConsumer")
public class ShellConsumer {
	
	public static final Logger LOG = Logger.getLogger(ShellConsumer.class);
	
	@Autowired
	@Qualifier("PropertyHelper")
	PropertyHelper propertyHelper;
	
	// Update the IRODS SERVER URL Accordingly
	public static String IRODS_SERVER_HOST = "139.62.242.18";
	// Update the IRODS SERVER USER Accordingly
	public static String IRODS_SERVER_USER = "root";
	// Update the list of IROD Commands that needs 
	// to be executed to identify the perfSONAR IP
	public static String COMMAND_1 = "ls -l /";
	
	/**
	 * Method call to identify the perfSONAR IP of the corresponding DTN
	 * @param event
	 */
	public void irodsDomainIdentifier(Event event) {
		/*
		 * 
		 * Event Object of the protobuf message needs to be defined
		 * with TaskInfo object. The TaskInfo object holds information
		 * about the data set location. This needs to be used to identify
		 * the DTN location where perfSONAR will be recording metrics. 
		 * 
		 */
		LOG.info("Consumer method to identify domain for file using IRODS");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(propertyHelper.getKeyFile().getAbsolutePath());
			Session session = jsch.getSession(IRODS_SERVER_USER, IRODS_SERVER_HOST);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			Properties config = new Properties();
			config.setProperty("StrictHostKeyChecking", "no");
			session.setConfig(config);
			
			// Connect to Session
			session.connect();
			
			// Execute Command
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(COMMAND_1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			
			InputStream in = channel.getInputStream();
			channel.connect();
			
			byte[] tmp = new byte[1024];
			while (true) {
				while(in.available() > 0) {
					int i = in.read(tmp, 0 , 1024);
					if (i < 0)
						break;
					System.out.println(new String(tmp, 0, i));
				}
				
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			LOG.error("Exception while consuming irods using SSH execution", e);
		}
	}

}
