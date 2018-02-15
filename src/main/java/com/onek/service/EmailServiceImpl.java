package com.onek.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

//@Service
public class EmailServiceImpl implements EmailService, Serializable
{
	private static final long serialVersionUID = 1L;
	
	//@Autowired
	//private MailSender mailSender;

	@Override
	public void sendMail(String from, String to, String subject, String msg) {
		//SimpleMailMessage message = new SimpleMailMessage();
		//message.setFrom(from);
		//message.setTo(to);
		//message.setSubject(subject);
		//message.setText(msg);
		//mailSender.send(message);
	}
}

