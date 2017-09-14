package com.principal.ind.ChatBot;

import java.io.File;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

@Path("/speechToText")
public class AudioText {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void audioToText () throws Exception {
		String name;
		String contractNo;
		String address;
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword("cfd674d6-7138-4ef8-83bf-a4b23d9a84b5", "081hgBr0Rb7E");
		
		//ClassLoader cl = getClass().getClassLoader();
		File file = new File("src/main/resource/audio.mp3");
		SpeechResults transcript = service.recognize(file).execute();
		String response = transcript.getResults().get(0).getAlternatives().get(0).getTranscript();
		System.out.println(response);
		
		name = StringUtils.substringBetween(response, "my name is ", " my contract number");
		contractNo = StringUtils.substringBetween(response, "contract number is ", " and my new");
		String digitContractNo = convertwordtoDigit(contractNo);
		address = StringUtils.substringBetween(response, "address is ", " please update");
			
		String toEmailId = updateAddress(digitContractNo, name, address);
		
		if(null != toEmailId){
			EmailUtility.sendEmail(toEmailId, "Address Changed Confirmation", "Your address has been changed in our system to '" + address + "'" + "\n\n Have a Nice Day!", false, null);
		}
        else{
        	 EmailUtility.sendEmail(toEmailId, "Address Change Failure Notification", "Sorry, we were unable to process your address change request. Please get in touch with our representatives for further help.", false, null);
        }
			
	}
	
	public static String updateAddress(String contractNo,String name, String newAddress) throws Exception{
		Class.forName("org.postgresql.Driver");
		Connection connection = getConnection();
		Statement stmt = connection.createStatement();
		String query = "SELECT name, email from user_details where name = '" + name + "' and contract_no = " + contractNo ;		
		ResultSet rs = stmt.executeQuery(query);
		int res = 0;
		String toEmailId = null;
		if(rs.next()){
			name = null;
			name = rs.getString(1);
			toEmailId = rs.getString(2);
			newAddress = "'" + newAddress + "'";
			query = "UPDATE user_details set address = " + newAddress + " where name = '" + name + "' and contract_no = " + contractNo;
			res = stmt.executeUpdate(query);
			stmt.close();
			rs.close();
			connection.close();
		}
		 
		return toEmailId;
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
	
	private String convertwordtoDigit(String contractNo){
		String tokens[] = contractNo.split(" ");
		String digit = "";
		for(String token:tokens){
			if(token.equalsIgnoreCase("one")){
				digit = digit + "1";
			}
			else if(token.equalsIgnoreCase("two")){
				digit = digit + "2";
			}
			else if(token.equalsIgnoreCase("three")){
				digit = digit + "3";
			}
			else if(token.equalsIgnoreCase("four")){
				digit = digit + "4";
			}
			else if(token.equalsIgnoreCase("five")){
				digit = digit + "5";
			}
			else if(token.equalsIgnoreCase("six")){
				digit = digit + "6";
			}
			else if(token.equalsIgnoreCase("seven")){
				digit = digit + "7";
			}
			else if(token.equalsIgnoreCase("eight")){
				digit = digit + "8";
			}
			else if(token.equalsIgnoreCase("nine")){
				digit = digit + "9";
			}
			else if(token.equalsIgnoreCase("zero")){
				digit = digit + "0";
			}
		}
		
		return digit;
		
	}
}
