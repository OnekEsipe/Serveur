package com.onek.managedbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.onek.model.Utilisateur;
import com.onek.service.UserService;
import com.onek.utils.Navigation;

@Component("users")
@Scope("session")
public class UsersBean {
	
	@Autowired
	private UserService userService;
	
	private int iduser;	
	private List<Utilisateur> users = new ArrayList<>();
	private List<Utilisateur> filteredusers = new ArrayList<>();
	private List<Utilisateur> selectedusers = new ArrayList<>();
	
	private Utilisateur utilisateurPrincipal;
	
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
			Navigation.redirect("index.xhtml");
			return;
		}
		String loginUtilisateur = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		utilisateurPrincipal = userService.findByLogin(loginUtilisateur);
		users = userService.getAllUsersExceptCurrentAndAnonymous(utilisateurPrincipal.getIduser());
	}
	
	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	
	public List<Utilisateur> getUsers() {
		return users;
	}
	
	public void setUsers(List<Utilisateur> users) {
		this.users = users;
	}
	
	public List<Utilisateur> getFilteredusers() {
		return filteredusers;
	}

	public void setFilteredusers(List<Utilisateur> filteredusers) {
		this.filteredusers = filteredusers;
	}
	
	public List<Utilisateur> getSelectedusers() {
		return selectedusers;
	}

	public void setSelectedusers(List<Utilisateur> selectedusers) {
		this.selectedusers = selectedusers;
	}
	
	public void deleteUser() {
		System.err.println("delete");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		iduser = Integer.valueOf(params.get("iduser"));
		if(iduser != utilisateurPrincipal.getIduser()) {
			userService.deleteUser(iduser);
		}		
	}
	
	public void reactiverUser() {
		System.err.println("reactivation");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		iduser = Integer.valueOf(params.get("iduser"));
		Utilisateur user = userService.findUserById(iduser);
		user.setIsdeleted(false);		
		userService.updateUserInfos(user);		
	}
	
	public void createUser() {
		Navigation.redirect("addUser.xhtml?i=1");
	}	

}
