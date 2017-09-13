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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.postgresql.jdbc2.AbstractJdbc2ResultSet.CursorResultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/processPolicyAdminEsignature")
public class EsignaturePolicyAdminResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{contractNo}")
	public void processPolicyAdminEsignature(@PathParam("contractNo") String contractNo) throws Exception{

		System.out.println("in process policy admin" + contractNo);
		Class.forName("org.postgresql.Driver");
		Connection connection = getConnection();
		Statement stmt = connection.createStatement();
		String query = "SELECT work_id from transactions where contract_no =" + contractNo;
		ResultSet rs = stmt.executeQuery(query);
		int workid = 0;
		while (rs.next()) {
			workid = rs.getInt(1);
		} 
		
		query = "UPDATE bpm set esignature = 'Yes', task_status = 'Completed' where contract_no =" + contractNo;
		int res = stmt.executeUpdate(query);
		System.out.println("Update BPM completed");
		
		int contractNumber = Integer.parseInt(contractNo);
		String loanStatus = "'approved'";
		String comma = ",";
		query = "INSERT INTO pas (work_id, contract_no, loan_status) values (" + workid + comma + contractNumber + comma + loanStatus + ")";
		res = stmt.executeUpdate(query);
		
		System.out.println("Insert Pas complete");
		
		
		query = "SELECT * from user_details where contract_no =" + contractNo;		
		rs = stmt.executeQuery(query);
		
		String emailId = null;
		while (rs.next()) {
			emailId = rs.getString(5);
		} 
		//EmailHelper emailHelper = new EmailHelper(emailId, false, null);
		String subject = "Loan Request Complete";
		String body = "Your Loan Request has been processed. Thank You!";
		EmailUtility.sendEmail(emailId, subject, body, false, null);
		rs.close();
		stmt.close();
		connection.close();
		
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
}
