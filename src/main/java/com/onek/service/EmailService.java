package com.onek.service;

public interface EmailService {
	public boolean sendMail(String to, String subject, String msg);
}
