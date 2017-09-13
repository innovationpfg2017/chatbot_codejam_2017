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
"intentId",
"webhookUsed",
"webhookForSlotFillingUsed",
"intentName"
})
public class Metadata {

@JsonProperty("intentId")
private String intentId;
@JsonProperty("webhookUsed")
private Boolean webhookUsed;
@JsonProperty("webhookForSlotFillingUsed")
private Boolean webhookForSlotFillingUsed;
@JsonProperty("intentName")
private String intentName;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("intentId")
public String getIntentId() {
return intentId;
}

@JsonProperty("intentId")
public void setIntentId(String intentId) {
this.intentId = intentId;
}

@JsonProperty("webhookUsed")
public Boolean getWebhookUsed() {
return webhookUsed;
}

@JsonProperty("webhookUsed")
public void setWebhookUsed(Boolean webhookUsed) {
this.webhookUsed = webhookUsed;
}

@JsonProperty("webhookForSlotFillingUsed")
public Boolean getWebhookForSlotFillingUsed() {
return webhookForSlotFillingUsed;
}

@JsonProperty("webhookForSlotFillingUsed")
public void setWebhookForSlotFillingUsed(Boolean webhookForSlotFillingUsed) {
this.webhookForSlotFillingUsed = webhookForSlotFillingUsed;
}

@JsonProperty("intentName")
public String getIntentName() {
return intentName;
}

@JsonProperty("intentName")
public void setIntentName(String intentName) {
this.intentName = intentName;
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

