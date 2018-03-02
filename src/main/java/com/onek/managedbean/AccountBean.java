package com.onek.managedbean;

import java.io.Serializable;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.Encode;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

/**
 * ManagedBean AccountBean
 */
@Component("userInfo")
public class AccountBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(AccountBean.class);

	@Autowired
	private UserService userService;

	private Utilisateur user;
	private String login;
	private String lastEmail;

	private String lastPassword;
	private String newPassword;
	private String confirmNewPassword;
	private String newEmail;
	private String logPassword;
	private String logEmail;

	/**
	 * Méthode appelée lors d'un GET sur la page account.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				Navigation.redirect("index.xhtml");
				return;
			}
			String name = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			user = userService.findByLogin(name);

			this.lastEmail = user.getMail();
			this.login = name;
			emptyForm();
		}
	}

	private void emptyForm() {
		setLastPassword("");
		setNewPassword("");
		setConfirmNewPassword("");
		setNewEmail("");
	}

	/**
	 * Getter de la variable login.
	 * @return Le login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter de la variable login.
	 * @param login Le login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter de la variable newPassword
	 * @return newPassword Le nouveau password
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Setter de la variable newPassword
	 * @param newPassword Le nouveau password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * Getter de la variable newEmail
	 * @return newEmail Le nouvel email
	 */
	public String getNewEmail() {
		return newEmail;
	}

	/**
	 * Setter de la variable newEmail
	 * @param newEmail Le nouvel email
	 */
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	/**
	 * Getter de la variable lastPassword
	 * @return lastPassword Le password actuel
	 */
	public String getLastPassword() {
		return lastPassword;
	}

	/**
	 * Setter de la variable lastPassword
	 * @param lastPassword Le password actuel
	 */
	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	/**
	 * Getter de la variable confirmNewPassword
	 * @return confirmNewPassword Confirmation du nouveau password
	 */
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * Setter de la variable confirmNewPassword
	 * @param confirmNewPassword Confirmation du nouveau password
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * Getter de la variable logPassword
	 * @return logPassword Message lié au password
	 */
	public String getLogPassword() {
		return logPassword;
	}

	/**
	 * Setter de la variable logPassword
	 * @param logPassword Message lié au password
	 */
	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
	}

	/**
	 * Getter de la variable logEmail
	 * @return logEmail Message lié à l'email
	 */
	public String getLogEmail() {
		return logEmail;
	}

	/**
	 * Setter de la variable logEmail
	 * @param logEmail Message lié à l'email
	 */
	public void setLogEmail(String logEmail) {
		this.logEmail = logEmail;
	}

	/**
	 * Getter de la variable user
	 * @return User L'utilisateur
	 */
	public Utilisateur getUser() {
		return user;
	}

	/**
	 * Setter de la variable user
	 * @param user L'utilisateur
	 */
	public void setUser(Utilisateur user) {
		this.user = user;
	}

	/**
	 * Getter de la variable lastEmail
	 * @return lastEmail Adresse mail actuel
	 */
	public String getLastEmail() {
		return lastEmail;
	}

	/**
	 * Setter de la variable lastEmail
	 * @param lastEmail Adresse mail actuel
	 */
	public void setLastEmail(String lastEmail) {
		this.lastEmail = lastEmail;
	}

	/**
	 * Modifie le mot de passe de l'utilisateur. Des messages sont générés en fonction des erreurs.
	 */
	public void modifyPassword() {
		logEmail = "";
		if (lastPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
			showMessagePassword("Merci de renseigner tous les champs.");
			return;
		}
		if (!newPassword.equals(confirmNewPassword)) {
			showMessagePassword("Nouveau mot de passe différent du mot de passe de confirmation.");
			return;
		}
		try {
			String hash = Encode.sha1(lastPassword);
			if (!user.getMotdepasse().equals(hash)) {
				showMessagePassword("Mot de passe incorrect, merci de réessayer.");
				return;
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			showMessagePassword("Une erreur est survenue.");
			logger.error(this.getClass().getName(), e);
			return;
		}
		if (!Password.verifyPasswordRule(newPassword)) {
			showMessagePassword("Le mot de passe doit être composé d'au moins 6 caractères et comporter au moins une majuscule.");
			return;
		}		
		try {
			String hash = Encode.sha1(newPassword);
			user.setMotdepasse(hash);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(this.getClass().getName(), e);
			showMessagePassword("Une erreur est survenue.");
			return;
		}
		userService.updateUserInfos(user);
		showMessagePassword("Modification effectuée avec succès !");
	}

	/**
	 * Modifie l'adresse mail de l'utilisateur. Des messages sont générés en fonction des erreurs.
	 */
	public void modifyMail() {
		logPassword = "";
		if (newEmail == null)
			return;
		if (newEmail.isEmpty()) {			
			newEmail = "";
			showMessageMail("Merci de saisir votre adresse mail.");
			return;
		}
		if (userService.mailExist(newEmail)) {			
			newEmail = "";
			showMessageMail("L'adresse mail est déjà utilisée.");
			return;
		}
		lastEmail = newEmail;
		user.setMail(newEmail);
		newEmail = "";
		userService.updateUserInfos(user);
		showMessageMail("Modification effectuée avec succès !");
	}
	
	private void showMessagePassword(String logPassword) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification du mot de passe", logPassword));
	}
	
	private void showMessageMail(String logMail) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Modification de l'adresse mail", logMail));
	}
}
