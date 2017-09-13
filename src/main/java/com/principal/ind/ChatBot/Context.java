package com.principal.ind.ChatBot;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"name",
"parameters",
"lifespan"
})
public class Context {

@JsonProperty("name")
private String name;
@JsonProperty("parameters")
private Parameters parameters;
@JsonProperty("lifespan")
private Integer lifespan;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("parameters")
public Parameters getParameters() {
return parameters;
}

@JsonProperty("parameters")
public void setParameters(Parameters parameters) {
this.parameters = parameters;
}

@JsonProperty("lifespan")
public Integer getLifespan() {
return lifespan;
}

@JsonProperty("lifespan")
public void setLifespan(Integer lifespan) {
this.lifespan = lifespan;
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
