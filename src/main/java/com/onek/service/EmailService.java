package com.onek.service;

import com.onek.bean.MailBean;

/**
 * Interface de la classe EmailServiceImpl. Couche service
 */
public interface EmailService {
	
	/**
	 * Envoie de mail
	 * @param to Destination
	 * @param subject Sujet
	 * @param msg Corps du mail
	 * @return True si l'envoie s'est bien déroulé
	 */
	public boolean sendMail(String to, String subject, String msg);
	
	/**
	 * @return Le bean du mail
	 */
	public MailBean getMailBean();
}
