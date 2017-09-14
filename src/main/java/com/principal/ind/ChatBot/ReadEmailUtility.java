package com.principal.ind.ChatBot;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

@Path("/readEmail")
public class ReadEmailUtility {

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public static void check() 
   {
      try {

    	  String host = "pop.gmail.com";// change accordingly
          String mailStoreType = "pop3";
          String username = "nullpointers967@gmail.com";// change accordingly
          String password = "abcd123$";// change accordingly
      //create properties field
      Properties properties = new Properties();

      properties.put("mail.pop3.host", host);
      properties.put("mail.pop3.port", "995");
      properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getDefaultInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("pop3s");

      store.connect(host, username, password);

      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      System.out.println("messages.length---" + messages.length);

      for (int i = 0, n = messages.length; i < n; i++) {
         Message message = messages[i];
         System.out.println("---------------------------------");
         System.out.println("Email Number " + (i + 1));
         System.out.println("Subject: " + message.getSubject());
         System.out.println("From: " + message.getFrom()[0]);
         System.out.println("Text: " + getTextFromMessage(message));
         String from = InternetAddress.toString(message.getFrom());
         if ((getTextFromMessage(message).toLowerCase().contains("address") && getTextFromMessage(message).toLowerCase().contains("change")) 
        		 || (getTextFromMessage(message).toLowerCase().contains("chg") && getTextFromMessage(message).toLowerCase().contains("my") && getTextFromMessage(message).toLowerCase().contains("add")
        				 || (getTextFromMessage(message).toLowerCase().contains("new") && getTextFromMessage(message).toLowerCase().contains("address")
        						 || getTextFromMessage(message).toLowerCase().contains("mailing") && getTextFromMessage(message).toLowerCase().contains("add")))) {
        	 Date date = null;
        	 date = message.getSentDate();
             // Get all the information from the message
             
             if (from != null) {
                System.out.println("From: " + from);
             }
             String body = "\n\n" + "Contract No:" + "\n"+ "Name:" + "\n" + "Old Address:" + "\n" + "Updated Address:";
            	 
             EmailUtility.sendEmail(from, " More Information needed", "In order to process your request, Please enter the following information in the specified format below." + body, false, null);
         } else if (getTextFromMessage(message).contains("Name") && getTextFromMessage(message).contains("Old Address") && getTextFromMessage(message).contains("Updated Address")) {
        	String text = getTextFromMessage(message);
        	String[] emailContents = StringUtils.split(text, "\n");
        	String contractNoContents = emailContents[0];
        	String nameContents = emailContents[1];
        	String newAddressContent = emailContents[3];
        	AddressHelper addressHelper = new AddressHelper();
        	
        	String[] fullContractNoContents = StringUtils.split(contractNoContents, ":");
        	String[] fullNameContents = StringUtils.split(nameContents, ":");
        	String[] fullNewAddress = StringUtils.split(newAddressContent, ":");
        		
        	addressHelper.setName(fullNameContents[1].replace("\r", "").trim());
        	addressHelper.setNewAddress(fullNewAddress[1].replace("\r", "").trim());
        	addressHelper.setContractNo(fullContractNoContents[1].replace("\r", "").trim());
        	
        	System.out.println(addressHelper.getName() + addressHelper.getNewAddress());
        	
        	
        	 
        	 if(updateAddress(addressHelper.getContractNo(),addressHelper.getName(), addressHelper.getNewAddress())!=0){
        		 EmailUtility.sendEmail(from, "Address Changed Confirmation", "Your address has been changed in our system to '" + addressHelper.getNewAddress() + "'" + "\n\n Have a Nice Day!", false, null);
        	 }
        	 else{
        		 EmailUtility.sendEmail(from, "Address Change Failure Notification", "Sorry, we were unable to process your address change request. Please get in touch with our representatives for further help.", false, null);
        	 }
        	 
         } else {
        	 EmailUtility.sendEmail(from, "Unable to process request", "Sorry, Currently we are unable to process  your request. Please get in touch with our representatives for further help.", false, null);
         }

      }
      
      

      //close the store and folder objects
      emailFolder.close(false);
      store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

public static int updateAddress(String contractNo,String name, String newAddress) throws Exception{
	Class.forName("org.postgresql.Driver");
	Connection connection = getConnection();
	Statement stmt = connection.createStatement();
	String query = "SELECT name from user_details where name = '" + name + "' and contract_no = " + contractNo ;		
	ResultSet rs = stmt.executeQuery(query);
	int res = 0;
	if(rs.next()){
		name = null;
		 
		
			name = rs.getString(1);
			 
		 
		
		newAddress = "'" + newAddress + "'";
		query = "UPDATE user_details set address = " + newAddress + " where name = '" + name + "' and contract_no = " + contractNo;
		res = stmt.executeUpdate(query);
		stmt.close();
		rs.close();
		connection.close();
	}
	 
	return res;
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

   private static String getTextFromMessage(Message message) throws Exception {
	    if (message.isMimeType("text/plain")){
	        return message.getContent().toString();
	    }else if (message.isMimeType("multipart/*")) {
	        String result = "";
	        MimeMultipart mimeMultipart = (MimeMultipart)message.getContent();
	        int count = mimeMultipart.getCount();
	        for (int i = 0; i < count; i ++){
	            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	            if (bodyPart.isMimeType("text/plain")){
	                result = result + "\n" + bodyPart.getContent();
	                break;  //without break same text appears twice in my tests
	            } else if (bodyPart.isMimeType("text/html")){
	                String html = (String) bodyPart.getContent();
	                result = result + "\n" + Jsoup.parse(html).text();

	            }
	        }
	        return result;
	    }
	    return "";
	}

}