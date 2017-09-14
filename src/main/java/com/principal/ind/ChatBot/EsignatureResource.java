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

public class EsignatureResource {

	public void processEsignature(String jsonString) throws Exception{

		PDFGenerator pdfGenerator = new PDFGenerator();
		//pdfGenerator.generatePdf(jsonString);
		EmailHelper emailHelper = pdfGenerator.generateFirstPdf(jsonString); 
		String subject = "Request for Esignature";
		String body = "Please click on following link to complete e signature: https://chatbot-codejam2017.herokuapp.com/esign.html?cn=" + emailHelper.getContractNo();
		EmailUtility.sendEmail(emailHelper.getToEmailId(), subject, body, emailHelper.isAttachmentPresent(), emailHelper.getAttachmentFileLocation());
		
		
		Class.forName("org.postgresql.Driver");
		Connection connection = getConnection();
		Statement stmt = connection.createStatement();
		String query = "SELECT work_id from transactions where contract_no =" + emailHelper.getContractNo();
		ResultSet rs = stmt.executeQuery(query);
		int workid = 0;
		while (rs.next()) {
			workid = rs.getInt(1);
		} 
		
		
		
		String comma = ",";
		String no = "'no'";
		String pending = "'pending'";
		query = "INSERT into bpm (work_id, contract_no, esignature, task_status) values (" + workid + comma + emailHelper.getContractNo() + comma + no + comma + pending + ")" ;
		int result = stmt.executeUpdate(query);
		System.out.println("bpm table updated");
		//String resopnse = "You will receive a mail shortly on your registered mail id. Please follow the instructions given in the mail to confirm the change. Thank you for being our valuable customer.";
		
		//WebhookResponse webHookRespnse = new WebhookResponse(resopnse,resopnse);
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
