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
	
	private List<Utilisateur> users = new ArrayList<>();
	
	private List<Utilisateur> filteredusers = new ArrayList<>();
	
	private String logInfo;
	
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			users = userService.getAllUsersExceptDeleted();
		}
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

	public void onClickAdd() {
		if (!password.equals(confirmationPassword)) {
			logInfo = "Les mots de passe ne correspondent pas !";
		}
		Utilisateur newUser = new Utilisateur();
		newUser.setNom(lastName);
		newUser.setPrenom(firstName);
		newUser.setMail(mail);
		newUser.setLogin(login);
		// TODO AJOUTER HASH PASSWORD
		newUser.setMotdepasse(password);
//		if (isAdmin) {
//			newUser.setDroits("A");
//		} else {
//			newUser.setDroits("O");
//		}
		newUser.setDroits("O");
		newUser.setIsdeleted(false);
		userService.addUser(newUser);
		users.add(newUser);
	}
	
	public void deleteUser() {
		System.out.println("hello");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		System.out.println("hello");
		iduser = Integer.valueOf(params.get("iduser"));
		System.out.println(iduser);
		userService.deleteUser(iduser);
		users = userService.getAllUsersExceptDeleted();
	}

}
