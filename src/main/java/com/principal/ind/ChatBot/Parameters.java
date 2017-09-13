package com.principal.ind.ChatBot;

import java.util.Date;
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
"birthDate",
"clientName",
"contractNo",
"loanAmount",
"SSN",
"zipcode"
})
public class Parameters {

@JsonProperty("birthDate")
private Date birthDate;

@JsonProperty("clientName")
private String clientName;

@JsonProperty("contractNo")
private int contractNo;

@JsonProperty("loanAmount")
private Double loanAmount;

@JsonProperty("SSN")
private int SSN;

@JsonProperty("zipcode")
private int zipcode;

@JsonProperty("zipcode")
public int getZipcode() {
	return zipcode;
}

@JsonProperty("zipcode")
public void setZipcode(int zipcode) {
	this.zipcode = zipcode;
}

@JsonProperty("SSN")
public int getSSN() {
	return SSN;
}
@JsonProperty("SSN")
public void setSSN(int sSN) {
	SSN = sSN;
}
@JsonProperty("contractNo")
public int getContractNo() {
	return contractNo;
}
@JsonProperty("contractNo")
public void setContractNo(int contractNo) {
	this.contractNo = contractNo;
}

@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("loanAmount")
public Double getLoanAmount() {
return loanAmount;
}

@JsonProperty("birthDate")
public Date getBirthDate() {
return birthDate;
}

@JsonProperty("birthDate")
public void setBirthDate(Date birthDate) {
this.birthDate = birthDate;
}

@JsonProperty("clientName")
public String getClientName() {
return clientName;
}

@JsonProperty("clientName")
public void setClientName(String clientName) {
this.clientName = clientName;
}


@JsonProperty("loanAmount")
public Double setLoanAmount() {
	return loanAmount;
}
@JsonProperty("loanAmount")
public void setLoanAmount(Double loanAmount) {
	this.loanAmount = loanAmount;
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