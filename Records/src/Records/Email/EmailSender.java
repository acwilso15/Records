/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Email;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailSender.
 *
 * @author Alexander
 */
class EmailSender {

	/** The email text. */
	private static String emailText;

	/** The subject text. */
	private static String subjectText;

	/**
	 * Instantiates a new email sender.
	 */
	public EmailSender() {
		emailText = "Feedback: " + RecordsMain.dialog.exDescription.getText();
		subjectText = "User Feedback: "
				+ RecordsMain.dialog.subjectLine.getText();
	}

	/**
	 * Send.
	 */
	void Send() {
		System.out.println("EmailSender.Send()");
		String Email = "RecordsFeedback@gmail.com";
		final String username = "RecordsFeedback@gmail.com";
		final String key = "recdebug";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, key);
					}
				});

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(username));
			// Students Email Should go here
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(Email));
			message.setSubject(subjectText);
			message.setText(emailText);

			Transport.send(message);
			System.out.println("Email Sent!");
		} catch (MessagingException ex) {
			Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}
