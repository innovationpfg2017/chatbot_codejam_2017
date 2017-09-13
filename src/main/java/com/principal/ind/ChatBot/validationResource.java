package com.principal.ind.ChatBot;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.postgresql.jdbc2.AbstractJdbc2ResultSet.CursorResultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/chatservice")
public class validationResource {
	
	private static  final String UNAUTHORIZED = "Authorization failed. Do you want to try again?";
	private static final String AUTHORIZED = "Great! Your authorization is successful. We can proceed with your request. Please specify the loan amount you wish to apply.";
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebhookResponse processRequests(String jsonString) throws Exception{

		Class.forName("org.postgresql.Driver");
		Connection connection = getConnection();
		ObjectMapper mapper = new ObjectMapper();
		Example javaObject =mapper.readValue(jsonString, Example.class);
		Parameters parameters = new Parameters();
		WebhookResponse webHookResponse = null;
		for(Context context : javaObject.getResult().getContexts()){
			if(context.getName().equalsIgnoreCase("user_name")){
				parameters = context.getParameters();
			}
		}
		//parameters = javaObject.getResult().getContexts().get(0).getParameters();
		if(javaObject.getResult().getAction().equalsIgnoreCase("authorizeUser")){
			webHookResponse = authorizeUser(parameters, connection);
		}else if(javaObject.getResult().getAction().equalsIgnoreCase("processLoan")){
			webHookResponse = insertIntoTransactionDB(parameters, connection);
		}else if(javaObject.getResult().getAction().equalsIgnoreCase("withholding_response_yes.withholding_response_yes-no.Assignee_irr-no-yes")){
			EsignatureResource esignatureResource = new EsignatureResource();
			webHookResponse = esignatureResource.processEsignature(jsonString);
		}
		return webHookResponse;

	}


	private WebhookResponse authorizeUser(Parameters parameters, Connection connection)
			throws ClassNotFoundException, Exception, SQLException {
		WebhookResponse webHookResponse;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		webHookResponse = new WebhookResponse(UNAUTHORIZED, UNAUTHORIZED);
		String dob = formatter.format(parameters.getBirthDate());
		
			
			Statement stmt = connection.createStatement();
			String query = "SELECT * FROM user_details WHERE contract_no=" + parameters.getContractNo() + " AND dob='" + dob +"'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				
				Integer ssnNo = rs.getInt(2);
				Integer maskedSSNNo = ssnNo %10000;
				
				if(maskedSSNNo == parameters.getSSN()){
					webHookResponse.setDisplayText(AUTHORIZED);
					webHookResponse.setSpeech(AUTHORIZED);
				}else{
					webHookResponse.setDisplayText(UNAUTHORIZED);
					webHookResponse.setSpeech(UNAUTHORIZED);
				}

			}
			rs.close();
			stmt.close();
		return webHookResponse;
	}
	
	private WebhookResponse insertIntoTransactionDB(Parameters parameters, Connection connection) throws SQLException{
		String query;
		String response;
		Statement stmt = connection.createStatement(); 
		Integer requestCode=1;
		if(Double.compare(parameters.getLoanAmount(), 0.0d)==0){
			requestCode=2;
		}
		
		query = "SELECT max_loan_amt FROM user_details WHERE contract_no=" + parameters.getContractNo();
		ResultSet rs = stmt.executeQuery(query);
		Double maxLoanAmt = null;
		while(rs.next()){
			maxLoanAmt = rs.getDouble(1);
		}
		
		if(parameters.getLoanAmount()>maxLoanAmt){
			response = "Requested loan amount exceeds maximum loan amount for your policy. Maximum loan amount permitted for your policy is $"+maxLoanAmt;
			return new WebhookResponse(response, response);
		}
		query = "INSERT INTO transactions(contract_no, loan_amt, request_code) values ("+parameters.getContractNo()+","+parameters.getLoanAmount()+","+requestCode+")";
		stmt.executeUpdate(query);
		response="Do you wish to elect to have fereral or state tax withheld from any taxable portion of your proceeds?";
		return new WebhookResponse(response, response);
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
