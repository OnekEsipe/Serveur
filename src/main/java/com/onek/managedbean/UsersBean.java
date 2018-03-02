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
import com.onek.utils.Navigation;

/**
 * ManagedBean UsersBean
 */
/**
 * @author julie
 *
 */
@Component("users")
public class UsersBean {
	
	@Autowired
	private UserService userService;
	
	private int iduser;	
	private List<Utilisateur> users = new ArrayList<>();
	private List<Utilisateur> filteredusers = new ArrayList<>();
	private List<Utilisateur> selectedusers = new ArrayList<>();
	
	private Utilisateur utilisateurPrincipal;
	
	/**
	 * Méthode appelée lors d'un GET sur la page users.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
			Navigation.redirect("index.xhtml");
			return;
		}
		String loginUtilisateur = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		utilisateurPrincipal = userService.findByLogin(loginUtilisateur);
		users = userService.getAllUsersExceptCurrentAndAnonymous(utilisateurPrincipal.getIduser());
	}
	
	/**
	 * Getter de la variable iduser
	 * @return iduser
	 */
	public int getIduser() {
		return iduser;
	}

	/**
	 * Setter de la variable iduser
	 * @param iduser
	 */
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	
	/**
	 * Getter de la variable users
	 * @return users Liste des utilisateurs
	 */
	public List<Utilisateur> getUsers() {
		return users;
	}
	
	/**
	 * Setter de la variable users
	 * @param users Liste des utilisateurs
	 */
	public void setUsers(List<Utilisateur> users) {
		this.users = users;
	}
	
	/**
	 * Getter de la variable filteredusers
	 * @return filteredusers Liste des utilisateurs filtrés
	 */
	public List<Utilisateur> getFilteredusers() {
		return filteredusers;
	}

	/**
	 * Setter de la variable filteredusers
	 * @param filteredusers Liste des utilisateurs filtrés
	 */
	public void setFilteredusers(List<Utilisateur> filteredusers) {
		this.filteredusers = filteredusers;
	}
	
	/**
	 * Getter de la variable selectedusers
	 * @return selectedusers Liste des utilisateurs selectionnés
	 */
	public List<Utilisateur> getSelectedusers() {
		return selectedusers;
	}

	/**
	 * Setter de la variable selectedusers
	 * @param selectedusers Liste des utilisateurs selectionnés
	 */
	public void setSelectedusers(List<Utilisateur> selectedusers) {
		this.selectedusers = selectedusers;
	}
	
	/**
	 * Marque un utilisateur comme supprimé 
	 */
	public void deleteUser() {
		System.err.println("delete");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		iduser = Integer.valueOf(params.get("iduser"));
		if(iduser != utilisateurPrincipal.getIduser()) {
			userService.deleteUser(iduser);
		}		
	}
	
	/**
	 * Réactive un utilisateur
	 */
	public void reactiverUser() {
		System.err.println("reactivation");
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		iduser = Integer.valueOf(params.get("iduser"));
		Utilisateur user = userService.findUserById(iduser);
		user.setIsdeleted(false);		
		userService.updateUserInfos(user);		
	}
	
	/**
	 * Navigation vers la page addUser.xhtml
	 */
	public void createUser() {
		Navigation.redirect("addUser.xhtml?i=1");
	}	

}
