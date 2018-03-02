package com.onek.bean;

/**
 * Classe de définition pour le smtp
 */
public class MailBean {

	private String port;
	private String auth;
	private String starttlsEnable;
	private String host;
	private String user;
	private String password;
	private String from;
	private String url;
	
	/**
	 * @return port du smtp
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * @param port du smtp
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * Correspond à un boolean en format String<br/>
	 * true = une authentification est requise pour se connecter<br/> 
	 * false = une authentification n'est pas requise pour se connecter 
	 * @return auth
	 */
	public String getAuth() {
		return auth;
	}
	
	/**
	 * Prend un boolean en format string
	 * @param auth
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	/**
	 * @return starttlsEnable
	 */
	public String getStarttlsEnable() {
		return starttlsEnable;
	}
	
	/**
	 * @param starttlsEnable
	 */
	public void setStarttlsEnable(String starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}
	
	/**
	 * @return hote du smtp
	 */
	public String getHost() {
		return host;
	}
	
	/**
	 * @param host du smtp
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * @return utilisateur du smtp
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @param user du smtp
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * @return password de l'utilisateur du smtp
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password de l'utilisateur du smtp
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return mail expediteur
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * @param mail expediteur
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}	
	
}
