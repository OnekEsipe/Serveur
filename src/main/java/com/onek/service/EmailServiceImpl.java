package com.onek.service;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.onek.bean.MailBean;

@Service
public class EmailServiceImpl implements EmailService, Serializable {
	private static final long serialVersionUID = 1L;

	private MailBean mailBean;
	private Session session;

	public EmailServiceImpl() {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mail.xml")) {
			mailBean = (MailBean) context.getBean("mailBean");
			Properties properties = System.getProperties();
			properties.put("mail.smtp.port", mailBean.getPort());
			properties.put("mail.smtp.auth", mailBean.getAuth());
			properties.put("mail.smtp.starttls.enable", mailBean.getStarttlsEnable());
			properties.put("mail.smtp.host", mailBean.getHost());
			properties.put("mail.smtp.user", mailBean.getUser());
			properties.put("mail.smtp.password", mailBean.getPassword());
			properties.put("mail.from", mailBean.getFrom());
			session = Session.getInstance(properties);
		}		
	}

	@Override
	public boolean sendMail(String to, String subject, String msg) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(session.getProperty("mail.from")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(msg, "text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect(session.getProperty("mail.smtp.host"), session.getProperty("mail.smtp.user"),
					session.getProperty("mail.smtp.password"));
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public MailBean getMailBean() {
		return mailBean;
	}
}
