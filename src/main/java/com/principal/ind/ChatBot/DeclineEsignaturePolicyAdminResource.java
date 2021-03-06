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


@Path("/declinePolicyAdminEsignature")
public class DeclineEsignaturePolicyAdminResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{contractNo}")
	public void declinePolicyAdminEsignature(@PathParam("contractNo") String contractNo) throws Exception{

		System.out.println("in decline esginature" + contractNo);
		
		//EmailHelper emailHelper = new EmailHelper(emailId, false, null);
		String subject = "Wet Signature required";
		String body = "Since you have declined the E-signature, kindly go through the attached PDF, print the PDF, wet sign it and send it to our official address";
		PDFGenerator pdfGenerator = new PDFGenerator();
		EmailHelper emailHelper = pdfGenerator.generateDeclinePdf(contractNo);
		EmailUtility.sendEmail(emailHelper.getToEmailId(), subject, body, true, emailHelper.getAttachmentFileLocation());
		
		
	}
	
	public WebhookResponse declineEsignature(String jsonString) throws Exception{

		System.out.println("in decline esginature");
		
		PDFGenerator pdfGenerator = new PDFGenerator();
		//pdfGenerator.generatePdf(jsonString);
		EmailHelper emailHelper = pdfGenerator.generateFirstPdf(jsonString); 
		String subject = "Request for Wet Signature";
		String body = "Since you have declined the E-signature, kindly go through the attached PDF, print the PDF, wet sign it and post it to our official office address that you can find on our website";
		EmailUtility.sendEmail(emailHelper.getToEmailId(), subject, body, emailHelper.isAttachmentPresent(), emailHelper.getAttachmentFileLocation());
		String resopnse = "You will receive a mail shortly on your registered email id. Please follow the instructions given in the mail. Thank you for being our valuable customer.";
		return new WebhookResponse(resopnse,resopnse);
		
	}
	
	public WebhookResponse witholdingYes(String jsonString) throws Exception{

		System.out.println("inside witholdingYes");
		

		PDFGenerator pdfGenerator = new PDFGenerator();
		//pdfGenerator.generatePdf(jsonString);
		EmailHelper emailHelper = pdfGenerator.generateFirstPdf(jsonString); 
		String subject = "Request for Wet Signature";
		String body = "Since your policy has either Assignee or Irrevocable beneficiary, your application could not be processed online. Please take the printout of the attached document and send us the signed document.";
		EmailUtility.sendEmail(emailHelper.getToEmailId(), subject, body, emailHelper.isAttachmentPresent(), emailHelper.getAttachmentFileLocation());
		String resopnse = "You will receive a mail shortly on your registered email id. Please follow the instructions given in the mail. Thank you for being our valuable customer.";
		return new WebhookResponse(resopnse,resopnse);
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
