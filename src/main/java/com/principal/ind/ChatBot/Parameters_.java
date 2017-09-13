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
"email.original",
"any.original",
"any",
"email",
"number",
"number.original",
"date",
"date.original"
})
public class Parameters_ {

@JsonProperty("email.original")
private String emailOriginal;
@JsonProperty("any.original")
private String anyOriginal;
@JsonProperty("any")
private String any;
@JsonProperty("email")
private String email;

@JsonProperty("number.original")
private int numberOriginal;

@JsonProperty("number")
private int number;

@JsonProperty("date.original")
private String dateOriginal;

@JsonProperty("date")
private String date;


@JsonProperty("number.original")
public int getNumberOriginal() {
	return numberOriginal;
}

@JsonProperty("number.original")
public void setNumberOriginal(int numberOriginal) {
	this.numberOriginal = numberOriginal;
}
@JsonProperty("number")
public int getNumber() {
	return number;
}
@JsonProperty("number")
public void setNumber(int number) {
	this.number = number;
}

@JsonProperty("date.original")
public String getDateOriginal() {
	return dateOriginal;
}
@JsonProperty("date.original")
public void setDateOriginal(String dateOriginal) {
	this.dateOriginal = dateOriginal;
}
@JsonProperty("date")
public String getDate() {
	return date;
}
@JsonProperty("date")
public void setDate(String date) {
	this.date = date;
}


@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("email.original")
public String getEmailOriginal() {
return emailOriginal;
}

@JsonProperty("email.original")
public void setEmailOriginal(String emailOriginal) {
this.emailOriginal = emailOriginal;
}

@JsonProperty("any.original")
public String getAnyOriginal() {
return anyOriginal;
}

@JsonProperty("any.original")
public void setAnyOriginal(String anyOriginal) {
this.anyOriginal = anyOriginal;
}

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

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
