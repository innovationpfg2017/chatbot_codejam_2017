package com.principal.ind.ChatBot;


	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import com.fasterxml.jackson.annotation.JsonAnyGetter;
	import com.fasterxml.jackson.annotation.JsonAnySetter;
	import com.fasterxml.jackson.annotation.JsonIgnore;
	import com.fasterxml.jackson.annotation.JsonInclude;
	import com.fasterxml.jackson.annotation.JsonProperty;
	import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	"source",
	"resolvedQuery",
	"action",
	"actionIncomplete",
	"parameters",
	"contexts",
	"metadata",
	"fulfillment",
	"score"
	})
	public class Result {

	@JsonProperty("source")
	private String source;
	@JsonProperty("resolvedQuery")
	private String resolvedQuery;
	@JsonProperty("action")
	private String action;
	@JsonProperty("actionIncomplete")
	private Boolean actionIncomplete;
	@JsonProperty("parameters")
	private Parameters parameters;
	@JsonProperty("contexts")
	private List<Context> contexts = null;
	@JsonProperty("metadata")
	private Metadata metadata;
	@JsonProperty("fulfillment")
	private Fulfillment fulfillment;
	@JsonProperty("score")
	private Integer score;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("source")
	public String getSource() {
	return source;
	}

	@JsonProperty("source")
	public void setSource(String source) {
	this.source = source;
	}

	@JsonProperty("resolvedQuery")
	public String getResolvedQuery() {
	return resolvedQuery;
	}

	@JsonProperty("resolvedQuery")
	public void setResolvedQuery(String resolvedQuery) {
	this.resolvedQuery = resolvedQuery;
	}

	@JsonProperty("action")
	public String getAction() {
	return action;
	}

	@JsonProperty("action")
	public void setAction(String action) {
	this.action = action;
	}

	@JsonProperty("actionIncomplete")
	public Boolean getActionIncomplete() {
	return actionIncomplete;
	}

	@JsonProperty("actionIncomplete")
	public void setActionIncomplete(Boolean actionIncomplete) {
	this.actionIncomplete = actionIncomplete;
	}

	@JsonProperty("parameters")
	public Parameters getParameters() {
	return parameters;
	}

	@JsonProperty("parameters")
	public void setParameters(Parameters parameters) {
	this.parameters = parameters;
	}

	@JsonProperty("contexts")
	public List<Context> getContexts() {
	return contexts;
	}

	@JsonProperty("contexts")
	public void setContexts(List<Context> contexts) {
	this.contexts = contexts;
	}

	@JsonProperty("metadata")
	public Metadata getMetadata() {
	return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(Metadata metadata) {
	this.metadata = metadata;
	}

	@JsonProperty("fulfillment")
	public Fulfillment getFulfillment() {
	return fulfillment;
	}

	@JsonProperty("fulfillment")
	public void setFulfillment(Fulfillment fulfillment) {
	this.fulfillment = fulfillment;
	}

	@JsonProperty("score")
	public Integer getScore() {
	return score;
	}

	@JsonProperty("score")
	public void setScore(Integer score) {
	this.score = score;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

	}