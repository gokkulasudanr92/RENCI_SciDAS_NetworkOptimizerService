Network Optimizer Service for SciDAS
====================================
The main goal of the project is to optimize the network throughput between the compute sites and the data sites. This is a REST-based service which returns the `Protos` message defined as per the Apache Mesos project.

## Environment
The micro service is build on the following tools:
* Maven
* Spring 4 MVC
* Google Protobuf

The spring framework is different from java servlets that all the request calls reach the application through Dispatcher Servlet. The Dispatcher Servlet is configured in the `web.xml` file by using the following code snippet: 
```xml
<servlet>
	<servlet-name>mvc-dispatcher</servlet-name>
    <servlet-class>
    	org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>mvc-dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

## Maven Configuration
Maven is a tool which is used to include dependencies for our project. The `pom.xml` file is used to define our dependencies for the application. The maven tool also introduces plugins which simplifies operations and also helps in continuous integration. This project mainly gives importance to 2 plugins, namely `maven-war-plugin` and `maven-protoc-plugin`.

* maven-war-plugin - This is used to generate the war file of the project, which can be deployed with the help of known servers like Apache Tomcat, Glassfish etc. The war file generated on maven build appears at the location `target`.
```xml
<plugin>
	<artifactId>maven-war-plugin</artifactId>
	<version>2.4</version>
	<configuration>
		<warSourceDirectory>src/main/webapp</warSourceDirectory>
		<failOnMissingWebXml>false</failOnMissingWebXml>
	</configuration>
</plugin>
```
* maven-protoc-plugin - This is used to generate the corresponding Java files for the `.proto` definition files. The `.proto` files are defined here as per the [MesoCordinator Repository] (https://github.com/SciDAS/MesosCoordinator). The maven build generates the Java files at `src/generated/java` location.(Note: `src/generated/java` should be added to the java build path). The protoc location should be defined in the plugin configuration with the help of `<protocExecutable>` tag.
```xml
<plugin>
	<groupId>com.google.protobuf.tools</groupId>
	<artifactId>maven-protoc-plugin</artifactId>
	<version>0.3.2</version>
	<configuration>
		<protocExecutable>C:/protoc-3.3.0-win32/bin/protoc.exe</protocExecutable>
		<outputDirectory>src/generated/java</outputDirectory>
	</configuration>
	<executions>
		<execution>
			<goals>
				<goal>compile</goal>
				<goal>testCompile</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
Note: This is a final note on how to execute the maven, the command is `mvn clean install -U`

## Spring Configuration
Spring configuration is done in this application with the help of `mvc-dispatcher-servlet.xml` located at `src/main/webapp/WEB-INF`. The xml was configured for annotation-driven programming. It also enables Http Request Handler for handling both JSON input and protobuf input. The previous mentioned configurations was enbled by using the following code snippet:
```xml
<context:component-scan base-package="org.renci.scidas" />
<mvc:annotation-driven />
<!-- Configure to plugin JSON as request and response in method handler -->
<bean
	class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
	<property name="messageConverters">
		<list>
			<ref bean="jsonMessageConverter" />
			<ref bean="protobufMessageConverter" />
		</list>
	</property>
</bean>

<!-- Configure bean to convert JSON to POJO and vice versa -->
<bean id="jsonMessageConverter"
	class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />
		
<bean id="protobufMessageConverter"
		class="org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter" />
``` 

## Key Configuration for Shell Consumer
The `ShellConsumer` class is used to remote login to a resource using the JSch Java Library. The identity key/private key for ssh remote login is placed at the `src/main/resources/properties/keys/id_rsa` relative path file.

## Logging
The application logging is done with the help of `Apache Log4j` library. The log configuration was defined at the `log4j.properties` file located at `src/main/resources`. The log4j.properties file defines logging as console message and also as file log.

## Code Structure
#### 1. org.renci.scidas.controller
This package contains the `V1Controller.java` file which is responsible contains the RESTful definition of how to invoke the micro service. Note: The URI call to invoke the service when the server is up is `http://<server-ip>:8181/<root-context>/v1/microserviceforprotobuf`.
#### 2. org.renci.scidas.consumer
This package contains the `PerfSONARRestConsumer.java` and `ShellConsumer.java` files. The first file contains the methods which is responsible for calling perfSONAR host and consolidating the results. The second file contains the methods required to identify the perfSONAR host from TaskInfo message from the protobuf.
#### 3. org.renci.scidas.processor
This package contains the main business logic of the micro service. The `NetworkOptimizerServiceProcessor.java` file contains all the methods which defines the business logic of the service and also methods which transforms the result to proto Event message.
#### 4. org.renci.scidas.pojo
This package contains a list of singleton classes.
#### 5. org.renci.scidas.helper
This package contains helper methods to process request, construct uri and file locator.
#### 6. org.renci.scidas.constants
This package defines all the fixed constants used within the project.
#### 7. src/generated/java
This source folder contains all the generated java files corresponding to the definition of `.proto` files.