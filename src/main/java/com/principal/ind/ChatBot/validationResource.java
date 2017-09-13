package com.principal.ind.ChatBot;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.postgresql.jdbc2.AbstractJdbc2ResultSet.CursorResultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/validate")
public class validationResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebhookResponse addMessage(String jsonString) throws Exception{

		int count = 0;
		ObjectMapper mapper = new ObjectMapper();
		Example javaObject = new Example();
		WebhookResponse webHookRespnse = null;
		javaObject = mapper.readValue(jsonString, Example.class);
		
		if(javaObject.getResult().getAction().equals("NameEmailValidation")){
			Class.forName("org.postgresql.Driver");
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			String insuredName = javaObject.getResult().getParameters().getClientName().toLowerCase();
			String insuredEmail = javaObject.getResult().getParameters().getClientName();
			String speechValidPrefix = "Thanks ";
			String speechValidSuffix = " ,to Validate you in our systems, Please give me your phone number as well as your birthdate";
			String name = "";
			String speechInvalid = "Unfortunatly we were unable to verify your identity online, Do you want to give another try?";
			ResultSet rs=stmt.executeQuery("select insured_name from POLICY_DETAILS where insured_name = "+"'"+insuredName+"'"+"and insured_email= "+"'"+insuredEmail+"'" );  
			while(rs.next()){ 
				count++;
				name = rs.getString(1);
			}
			
			if(count>0){
				webHookRespnse = new WebhookResponse(speechValidPrefix+name+speechValidSuffix, speechValidPrefix+name+speechValidSuffix);
			}else{
				webHookRespnse = new WebhookResponse(speechInvalid, speechInvalid);
			}
			rs.close();
			connection.close(); 
	
		}else if(javaObject.getResult().getAction().equals("ValidatePolicyNumber")){
			Class.forName("org.postgresql.Driver");
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			int insuredPolicy = javaObject.getResult().getParameters().getContractNo();
			
			String speechValidPrefix = "Perfect! ";
			String speechValidSuffix = " Now can you confirm the name of the insured on this policy?";
			String name = "";
			String speechInvalid = "Unfortunatly we were unable to verify your policy details online, Do you want to give another try?";
			ResultSet rs=stmt.executeQuery("select insured_name from POLICY_DETAILS where policy_num = "+insuredPolicy);  
			while(rs.next()){ 
				count++;
				name = rs.getString(1);
			}
			
			if(count>0){
				webHookRespnse = new WebhookResponse(speechValidPrefix+speechValidSuffix, speechValidPrefix+speechValidSuffix);
			}else{
				webHookRespnse = new WebhookResponse(speechInvalid, speechInvalid);
			}
			rs.close();
			connection.close(); 
		}
		else if(javaObject.getResult().getAction().equals("CheckIfMinor")) {
			String beneficicaryBirthdate = javaObject.getResult().getParameters().getBirthDate().toString();
			System.out.println("Bene Bdate:"+beneficicaryBirthdate);
			String year = beneficicaryBirthdate.substring(0,4);
			System.out.println(year);
			Integer beneBirthyYear= Integer.parseInt(year);
			System.out.println(beneBirthyYear);
			String BeneMinor = "Looks like your beneficiary is minor";
			String BeneNotMinor = "Sorry, we currently provide this facility only if beneficiary is minor.Thanks for contacting Principal";
			if ((Calendar.getInstance().get(Calendar.YEAR) - beneBirthyYear) < 18) {
				webHookRespnse = new WebhookResponse(BeneMinor, BeneMinor);
			}
			else {
				webHookRespnse = new WebhookResponse(BeneNotMinor, BeneNotMinor);
			}
		}
		return webHookRespnse;

	}


	private static Connection getConnection() throws Exception {
		URI dbUri = new URI("postgres://edknyzsqmgmhcl:8105f354652cf9b3de0cd7818bb4420bff0f098afdd8e650345fd222e5fae980@ec2-54-221-196-253.compute-1.amazonaws.com:5432/ddqdcgl6ng02kc");

		//		String username = dbUri.getUserInfo().split(":")[0];
		//		String password = dbUri.getUserInfo().split(":")[1];
		//		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()+"?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
		return DriverManager.getConnection(dbUrl, username, password);
	}


	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMessages() throws Exception{
		Class.forName("org.postgresql.Driver");
		Connection connection = getConnection();

		Statement stmt = connection.createStatement();
		System.out.println("getMessages() Connection done!!");
		ResultSet rs = stmt.executeQuery("SELECT * from bpm");
		Integer lastValue = 0;
		while (rs.next()) {
			System.out.println("Read from DB: " + rs.getInt(1));
			lastValue= rs.getInt(1);
		}
		return "Get reached!! "+lastValue;
	}
}
