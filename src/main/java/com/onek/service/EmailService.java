package com.onek.service;

//import org.springframework.mail.MailSender;

public interface EmailService {
	public void sendMail(String from, String to, String subject, String msg);
}
