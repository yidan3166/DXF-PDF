package com.websiteName.bo;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.websiteName.util.EmailMessages;

public class BO_SendEmail implements EmailMessages{
	
	public boolean sendInfoEmail(String email, String emailMessage) {
		
		try {
			String host = "mail.myWebsite.ba"; // hosting smtp adress (smtp.gmail.com - for testing)
			String user = "myEmail@myWebsite.ba"; // smtp username (gmail email adress - for testing)
			String pass = "myPassw"; // smtp password (gmail email password -for testing)
			String to = "myEmail@myWebsite.ba"; // receiver adress
			String from = email;
			String subject = "CTT - Primili ste email preko kontakt forme";
			String messageText = emailMessage;
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");

			// If this security par doesn't work, just remove JRE Library from
			// build path, and then add it again
			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);

			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));

			InternetAddress[] address = { new InternetAddress(to) };

			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSentDate(new Date());
			msg.setSubject(subject, "UTF-8"); //important for encoding
			msg.setText(messageText, "UTF-8"); //important for encoding
			//IMPORTANT for encoding of GET method (URI encoding):
			msg.setContent(messageText, "text/plain; charset=UTF-8");
			
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

			return true;

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return false;
	}

	public boolean sendResponseEmail(String email, String language) {
		
		String subject;
		String messageText;
		
		//Select language to automatically reply the client
		if (language.equals("en")) {
			subject = EN_subject;
			messageText = EN_Message;
		}else if (language.equals("de")) {
			subject = DE_subject;
			messageText = DE_Message;
		}else {
			subject = BA_subject;
			messageText = BA_Message;
		}
		
		
		try {
			String host = "mail.myWebsite.ba"; // hosting smtp adress (smtp.gmail.com - for testing)
			String user = "myEmail@myWebsite.ba"; // smtp username (gmail email adress - for testing)
			String pass = "myPassw"; // smtp password (gmail email password -for testing)
			String to = email; // client adress
			String from = "myEmail@myWebsite.ba";
			//subject;
			//messageText;
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");

			// If this security par doesn't work, just remove JRE Library from
			// build path, and then add it again
			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);

			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));

			InternetAddress[] address = { new InternetAddress(to) };

			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSentDate(new Date());
			
			msg.setSubject(subject, "UTF-8");
			msg.setText(messageText, "UTF-8");
			msg.setContent(messageText, "text/plain; charset=UTF-8"); 

			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

			return true;

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return false;
	}
	
	
}
