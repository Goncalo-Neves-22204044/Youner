package auxiliary;

import javax.mail.*;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;



import java.util.Properties;



public class EmailSend {

	public EmailSend(){

	}

	public void send(String destination, String title, String text) {
		final String generator = "younerhelp@gmail.com";
		final String passwordGenerator = "younerHelp123";

		Properties prop = new Properties();

		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop,new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(generator, passwordGenerator);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(generator));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destination));

			message.setSubject(title);

			message.setText(text);                  

			Transport.send(message);

		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}