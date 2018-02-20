package com.onek.service;

import com.onek.bean.MailBean;

public interface EmailService {
	public boolean sendMail(String to, String subject, String msg);
	public MailBean getMailBean();
}
