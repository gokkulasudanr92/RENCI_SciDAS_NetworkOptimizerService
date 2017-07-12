package org.renci.scidas.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "url", "metadata-key", "subject-type", "event-types", 
		"measurement-agent", "input-source", "input-destination", "bw-parallel-streams", 
		"ip-transport-protocol", "metadata-count-total", "metadata-previous-page",
        "metadata-next-page" })
public class ThroughputEvent {

	@JsonProperty
	public String source;
	@JsonProperty
	public String destination;
	@JsonProperty("tool-name")
	public String toolName;
	@JsonProperty("pscheduler-test-type")
	public String testType;
	@JsonProperty("time-duration")
	public String timeDuration;
	@JsonProperty
	public String uri;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTimeDuration() {
		return timeDuration;
	}

	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}
	
	@Override
	public String toString() {
		return "[ source: " + this.getSource() + 
				", destination: " + this.getDestination() +
				", tool-name: " + this.getToolName() + 
				", test-type: " + this.getTestType() +
				", time-duration: " + this.getTimeDuration() +
				", uri: " + this.getUri() + "]";
	}

}
