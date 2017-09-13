package com.principal.ind.ChatBot;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PDFGenerator {
	
	public EmailHelper generateFirstPdf(String jsonString) throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		Example javaObject =mapper.readValue(jsonString, Example.class);
		Parameters parameters = new Parameters();
		WebhookResponse webHookRespnse = null;
		parameters = javaObject.getResult().getContexts().get(0).getParameters();
		
			Class.forName("org.postgresql.Driver");
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			String query = "SELECT loan_amt from transactions where contract_no =" + parameters.getContractNo();
			ResultSet rs = stmt.executeQuery(query);
			String loan_amount = null;
			while (rs.next()) {
				 loan_amount = rs.getString(1);
			} 
			
			query = "SELECT * from user_details where contract_no =" + parameters.getContractNo();		
			rs = stmt.executeQuery(query);
			
			String name = null;
			 String dateOfBirth = null;
			 String emailId = null;
			 int contractNo = 0;
			
			while (rs.next()) {
				name = rs.getString(1);
				 dateOfBirth = rs.getString(4);
				  emailId = rs.getString(5);
				 contractNo = parameters.getContractNo();
			} 
		
		
		   final PDPage singlePage = new PDPage();
		   final PDFont courierBoldFont = PDType1Font.COURIER;
		   final int fontSize = 12;
		   try (final PDDocument document = new PDDocument())
		   {
		      document.addPage(singlePage);
		      final PDPageContentStream contentStream = new PDPageContentStream(document, singlePage);
		      contentStream.beginText();
		      contentStream.setFont(courierBoldFont, fontSize);
		      contentStream.newLineAtOffset(50, 750);
		      contentStream.showText("Hello " + name);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.newLine();
		      contentStream.showText("This is the inforamtion you have provided:");
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.newLine();
		      contentStream.showText("Name: " + name);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.showText("Date of Birth: " + dateOfBirth);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.showText("Contract No: " + contractNo);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.showText("Loan amount: $" + loan_amount);
		      contentStream.endText();
		     
		      contentStream.close();  // Stream must be closed before saving document.
		      document.save("nullpointers.pdf");
		      
		      EmailHelper emailHelper = new EmailHelper(emailId, true, "nullpointers.pdf");
		      emailHelper.setContractNo(contractNo);
		      rs.close();
		      stmt.close();
		      connection.close();
		      return emailHelper;
		   }
		   catch (IOException ioEx)
		   {
		      return null;
		   }  
}
	
public EmailHelper generateDeclinePdf(String contractNo) throws Exception{
		
			Class.forName("org.postgresql.Driver");
			Connection connection = getConnection();
			Statement stmt = connection.createStatement();
			String query = "SELECT loan_amt from transactions where contract_no =" + contractNo;
			ResultSet rs = stmt.executeQuery(query);
			String loan_amount = null;
			while (rs.next()) {
				 loan_amount = rs.getString(1);
			} 
			
			query = "SELECT * from user_details where contract_no =" + contractNo;		
			rs = stmt.executeQuery(query);
			
			String name = null;
			 String dateOfBirth = null;
			 String emailId = null;
			 
			
			while (rs.next()) {
				name = rs.getString(1);
				 dateOfBirth = rs.getString(4);
				  emailId = rs.getString(5);
				 
			} 
		
		
		   final PDPage singlePage = new PDPage();
		   final PDFont courierBoldFont = PDType1Font.COURIER;
		   final int fontSize = 12;
		   try (final PDDocument document = new PDDocument())
		   {
		      document.addPage(singlePage);
		      final PDPageContentStream contentStream = new PDPageContentStream(document, singlePage);
		      contentStream.beginText();
		      contentStream.setFont(courierBoldFont, fontSize);
		      contentStream.newLineAtOffset(50, 750);
		      contentStream.showText("Hello " + name);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.newLine();
		      contentStream.showText("This is the inforamtion you have provided:");
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.newLine();
		      contentStream.showText("Name: " + name);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.showText("Date of Birth: " + dateOfBirth);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.showText("Contract No: " + contractNo);
		      contentStream.setLeading(10);
		      contentStream.newLine();
		      contentStream.showText("Loan amount: $" + loan_amount);
		      contentStream.endText();
		     
		      contentStream.close();  // Stream must be closed before saving document.
		      document.save("nullpointers.pdf");
		      
		      EmailHelper emailHelper = new EmailHelper(emailId, true, "nullpointers.pdf");
		      emailHelper.setContractNo(Integer.parseInt(contractNo));
		      return emailHelper;
		   }
		   catch (IOException ioEx)
		   {
		      return null;
		   }  
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

