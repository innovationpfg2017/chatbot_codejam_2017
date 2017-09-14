package com.principal.ind.ChatBot;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtility {

	public static void sendEmail(String toEmail, String subject, String body, Boolean attachFile,
			String attachFileLocation) {
		// TODO Auto-generated method stub
		System.out.println("Mail Start");

		String smtpHostServer = "smtp.gmail.com";
		final String fromEmail = "nullpointers967@gmail.com";
		final String password = "abcd123$";

		Properties props = new Properties();

		props.setProperty("mail.transport.protocoal", "smtp");
		props.put("mail.host", smtpHostServer);
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);

		sendEmail(session, toEmail, subject, body, attachFile, attachFileLocation);

	}

	public static void sendEmail(Session session, String toEmail, String subject, String body, Boolean attachFile,
			String attachFileLocation) {

		try {
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("nullpointers967@gmail.com", "NoReply"));

			msg.setReplyTo(InternetAddress.parse("nullpointers967@gmail.com", false));

			msg.setSubject(subject, "UTF-8");

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(body);
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			if (attachFile) {
				
				

				// Set text message part
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				String filename = attachFileLocation;
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				
			}
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("Message is ready");
			Transport.send(msg);

			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
