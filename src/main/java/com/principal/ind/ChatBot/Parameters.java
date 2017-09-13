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
"any",
"email",
"number",
"date"
})
public class Parameters {

@JsonProperty("any")
private String any;
@JsonProperty("email")
private String email;

@JsonProperty("number")
private int number;
@JsonProperty("date")
private String date;

@JsonProperty("number")
public int getNumber() {
	return number;
}
@JsonProperty("number")
public void setNumber(int number) {
	this.number = number;
}

@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("any")
public String getAny() {
return any;
}

@JsonProperty("any")
public void setAny(String any) {
this.any = any;
}

@JsonProperty("email")
public String getEmail() {
return email;
}

@JsonProperty("email")
public void setEmail(String email) {
this.email = email;
}


@JsonProperty("date")
public String getDate() {
	return date;
}
@JsonProperty("date")
public void setDate(String date) {
	this.date = date;
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