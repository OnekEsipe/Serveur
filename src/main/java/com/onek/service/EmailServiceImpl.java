package com.onek.service;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService, Serializable {
	private static final long serialVersionUID = 1L;

	private Session session;

	public EmailServiceImpl() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.user", "onek2018esipe@gmail.com");
		properties.put("mail.smtp.password", "onek2018!");
		properties.put("mail.from", "onek2018esipe@gmail.com");
		session = Session.getInstance(properties);
	}

	@Override
	public void sendMail(String to, String subject, String msg) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(session.getProperty("mail.from")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(msg);
			Transport transport = session.getTransport("smtp");
			transport.connect(session.getProperty("mail.smtp.host"), session.getProperty("mail.smtp.user"),
					session.getProperty("mail.smtp.password"));
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
