package com.onek.managedbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.DroitsUtilisateur;

@Component("user")
public class UserBean {

	@Autowired
	UserService userService;

	private Utilisateur utilisateur;
	private int iduser;
	private String lastName;
	private String firstName;
	private String login;
	private String password;
	private String confirmationPassword;
	private String mail;
	private Boolean isAdmin;
	private String option;
	
	
	private List<Utilisateur> users = new ArrayList<>();

	private List<Utilisateur> filteredusers = new ArrayList<>();
	private List<Utilisateur> selectedusers = new ArrayList<>();

	private String logInfo;

	public void before(ComponentSystemEvent e) {

		users = userService.getAllUsersExceptDeleted();
		emptyForm();
		
	}
	
	private void emptyForm() {
		setFirstName("");
		setLastName("");
		setLogin("");
		setPassword("");
		setConfirmationPassword("");
		setMail("");
		logInfo="";
		
	}
	public String isOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public List<Utilisateur> getSelectedusers() {
		return selectedusers;
	}

	public void setSelectedusers(List<Utilisateur> selectedusers) {
		this.selectedusers = selectedusers;
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

	public List<Utilisateur> getFilteredusers() {
		return filteredusers;
	}

	public void setFilteredusers(List<Utilisateur> filteredusers) {
		this.filteredusers = filteredusers;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public void click() {
		System.out.println("lol");
	}

	public void onClickAdd() {		
		if (!password.equals(confirmationPassword)) {
			logInfo = "Les mots de passe ne correspondent pas !";
			return;
		}
		Utilisateur newUser = new Utilisateur();
		newUser.setNom(lastName);
		newUser.setPrenom(firstName);
		newUser.setMail(mail);
		newUser.setLogin(login);
		newUser.setMotdepasse(password);
		if(isAdmin) {
			newUser.setDroits(DroitsUtilisateur.ADMINISTRATEUR.toString());
		}else {
			newUser.setDroits(DroitsUtilisateur.ORGANISATEUR.toString());
		}
		newUser.setIsdeleted(false);
		try {
			userService.addUser(newUser);
			users.add(newUser);
		}		
		catch(IllegalStateException e) { // login exist
			logInfo = "Création impossible : le login est déjà utilisé.";
		}
	}

	public void deleteUser() {

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		iduser = Integer.valueOf(params.get("iduser"));
		userService.deleteUser(iduser);
		users = userService.getAllUsersExceptDeleted();

	}

}
