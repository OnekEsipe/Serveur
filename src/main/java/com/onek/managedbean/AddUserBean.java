package com.onek.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

/**
 * ManagedBean AddUserBean
 */
@Component("addUser")
public class AddUserBean {

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

	/**
	 * Méthode appelée lors d'un GET sur la page addUser.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
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

	/**
	 * Getter de la variable isdeleted
	 * @return Boolean isdeleted
	 */
	public boolean isIsdeleted() {
		return isdeleted;
	}

	/**
	 * Getter de la variable isdeleted
	 * @param isdeleted
	 */
	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	/**
	 * Getter de la variable option
	 * @return isOption
	 */
	public String isOption() {
		return option;
	}

	/**
	 * Setter de la variable option
	 * @param option
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * Getter de la variable iduser
	 * @return L'id de l'utilisateur
	 */
	public int getIduser() {
		return iduser;
	}

	/**
	 * Setter de la variable iduser
	 * @param iduser Id de l'utilisateur
	 */
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	/**
	 * Getter de la variable utilisateur
	 * @return L'utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * Getter de la variable utilisateur
	 * @param user L'utilisateur
	 */
	public void setUtilisateur(Utilisateur user) {
		this.utilisateur = user;
	}

	/**
	 * Getter de la variable users
	 * @return users La liste des utilisateurs
	 */
	public List<Utilisateur> getUsers() {
		return users;
	}

	/**
	 * Setter de la variable users
	 * @param users Une liste d'utilisateurs
	 */
	public void setUsers(List<Utilisateur> users) {
		this.users = users;
	}

	/**
	 * Getter de la variable lastName
	 * @return lastName Nom de l'utilisateur
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter de la variable lastName
	 * @param lastName Nom de l'utilisateur
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter de la variable firstName
	 * @return firstName Prenom de l'utilisateur
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter de la variable firstName
	 * @param firstName Prenom de l'utilisateur
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter de la variable login
	 * @return login de l'utilisateur
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter de la variable login
	 * @param login de l'utilisateur
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter de la variable password
	 * @return password de l'utilisateur
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter de la variable
	 * @param password de l'utilisateur
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter de la variable confirmationPassword
	 * @return confirmationPassword
	 */
	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	/**
	 * Setter de la variable confirmationPassword
	 * @param confirmationPassword
	 */
	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	/**
	 * Getter de la variable mail
	 * @return mail L'adresse mail de l'utilisateur
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setter de la variable mail
	 * @param mail L'adresse mail de l'utilisateur
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Getter de la variable isAdmin
	 * @return True si l'utilisateur est un admin
	 */
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * Setter de la variable isAdmin
	 * @param isAdmin Boolean du droit administrateur d'un utilisateur
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Getter de la variable logInfo
	 * @return logInfo Message d'information
	 */
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * Setter de la variable logInfo
	 * @param logInfo Le message a afficher
	 */
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	/**
	 * Ajoute un nouvel utilisateur. Vérifie :<br/>
	 * Que le mot de passe saisie correspond aux règles de password<br/>
	 * Que le mot de passe saisi est identique au mot de passe de confirmation <br/>
	 * Que l'adresse mail saisie n'existe pas<br/>
	 * Que le login n'existe pas
	 */
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
		newUser.setMotdepasse(password);
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
		} catch (IllegalStateException e) { 
			logInfo = "Création impossible : le login est déjà utilisé.";
			showLogMessage(true);
		}
	}
	
	/**
	 * Gestion de l'affichage des messages d'erreurs
	 * @param error Boolean indiquant si l'on souhaite afficher le message d'erreur
	 */
	public void showLogMessage(boolean error) {
		this.error = error;
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout d'un nouvel utilisateur", logInfo));
	}
	
	/**
	 * Navigation vers la page users.xhtml en transmettant l'index du menu (pour affichage correct de l'onglet actif)
	 */
	public void retour() {
		Navigation.redirect("users.xhtml?i=1");
	}

}
