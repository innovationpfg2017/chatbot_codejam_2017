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
		String notAuthorized = "You are not Authorized";
		String authorized = "You are Authorized";
		Example javaObject =mapper.readValue(jsonString, Example.class);
		Parameters parameters = new Parameters();
		WebhookResponse webHookRespnse = null;
		parameters = javaObject.getResult().getParameters();
		
			Class.forName("org.postgresql.Driver");
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			String query = "SELECT * from User where \"contractNo\" =" + parameters.getContractNo();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				 if(parameters.getBirthDate() != rs.getDate(4)) {
					webHookRespnse.setDisplayText(notAuthorized);
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
