package com.onek.managedbean;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Encode;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

@Component("addUser")
public class AddUserBean {
	private final static Logger logger = Logger.getLogger(AddUserBean.class);

	@Autowired
	UserService userService;

	private Utilisateur utilisateur;
	private int iduser;
	private boolean isdeleted;
	private String lastName;
	private String firstName;
	private String login;
	private String password;
	private String confirmationPassword;
	private String mail;
	private Boolean isAdmin;
	private String option;

	private List<Utilisateur> users = new ArrayList<>();

	private String logInfo;
	private boolean error;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
			Navigation.redirect("index.xhtml");
			return;
		}
		if (!error) {
			emptyForm();			
		}
		error = false;
	}

	private void emptyForm() {
		setFirstName("");
		setLastName("");
		setLogin("");
		setPassword("");
		setConfirmationPassword("");
		setMail("");
		setLogInfo("");
	}

	public boolean isIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String isOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUstilisateur(Utilisateur user) {
		this.utilisateur = user;
	}

	public List<Utilisateur> getUsers() {
		return users;
	}

	public void setUsers(List<Utilisateur> users) {
		this.users = users;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public void onClickAdd() {
		if (!password.equals(confirmationPassword)) {
			logInfo = "Les mots de passe ne correspondent pas !";
			showLogMessage(true);
			return;
		}
		if (!Password.verifyPasswordRule(password)) {
			logInfo = "Création impossible : Le mot de passe doit être composé d'au moins 6 caractères et comporter au moins une majuscule.";
			showLogMessage(true);
			return;
		}
		if (userService.mailExist(mail)) {
			logInfo = "Création impossible : adresse mail déjà utilisée.";
			showLogMessage(true);
			return;
		}
		Utilisateur newUser = new Utilisateur();
		newUser.setNom(lastName);
		newUser.setPrenom(firstName);
		newUser.setMail(mail);
		newUser.setLogin(login);
		try {
			newUser.setMotdepasse(Encode.sha1(password));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
			logger.error(this.getClass().getName(), e1);
			logInfo = "Création impossible : une erreur interne est survenue.";
			showLogMessage(true);
		}
		newUser.setIsdeleted(false);
		if (isAdmin) {
			newUser.setDroits(DroitsUtilisateur.ADMINISTRATEUR.toString());
		} else {
			newUser.setDroits(DroitsUtilisateur.ORGANISATEUR.toString());
		}
		
		try {
			userService.addUser(newUser);
			users.add(newUser);
			logInfo = "Ajout effectué avec succès !";
			showLogMessage(false);
		} catch (IllegalStateException e) { // login exist
			logInfo = "Création impossible : le login est déjà utilisé.";
			showLogMessage(true);
		}
	}
	
	public void showLogMessage(boolean error) {
		this.error = error;
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout d'un nouvel utilisateur", logInfo));
	}
	
	public void retour() {
		Navigation.redirect("users.xhtml?i=1");
	}

}
