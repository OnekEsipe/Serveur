package com.onek.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.JuryService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;

@Component("addjury")
public class AddJuryBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private JuryService juryService;
	
	@Autowired
	private EvenementService evenement;
	
	@Autowired
	private UserService userService;

	private int idEvent;
	private Evenement event;
	
	private Jury newjuryEvent;

	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private List<Utilisateur> selectedutilisateurs;
	private List<Utilisateur> utilisateursAll;
	

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Utilisateur> getFilteredutilisateurs() {
		return filteredutilisateurs;
	}

	public void setFilteredutilisateurs(List<Utilisateur> filteredutilisateurs) {
		this.filteredutilisateurs = filteredutilisateurs;
	}

	public List<Utilisateur> getSelectedutilisateurs() {
		return selectedutilisateurs;
	}

	public void setSelectedutilisateurs(List<Utilisateur> selectedutilisateurs) {
		this.selectedutilisateurs = selectedutilisateurs;
	}

	public List<Utilisateur> getUtilisateursAll() {
		return utilisateursAll;
	}

	public void setUtilisateursAll(List<Utilisateur> utilisateursAll) {
		this.utilisateursAll = utilisateursAll;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int id) {
		this.idEvent = id;
	}

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if(!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("idEvent")) {
				Navigation navigation = new Navigation();
				navigation.redirect("accueil.xhtml");
				return;
			}
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));
			this.event = evenement.findById(idEvent);
			utilisateurs = juryService.listJurysByEvent(idEvent);
			utilisateursAll = userService.findAllJurys();
			for (Utilisateur utilisateur : utilisateurs) {
				utilisateursAll.remove(utilisateur);
			}

		}
	}

	public void supprimerUtilisateur() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int iduser = Integer.valueOf(params.get("iduser"));

		juryService.supprimerUtilisateur(iduser);
		utilisateurs = juryService.listJurysByEvent(idEvent);
		utilisateursAll = userService.findAllJurys();
		for (Utilisateur utilisateur : utilisateurs) {
			utilisateursAll.remove(utilisateur);
		}
	}

	public void buttonAdd() {
		
		for (Utilisateur utilisateur : selectedutilisateurs) {
			newjuryEvent = new Jury();
			newjuryEvent.setEvenement(event);
			newjuryEvent.setUtilisateur(utilisateur);
			juryService.addJuryToEvent(newjuryEvent);
		}
		utilisateurs = juryService.listJurysByEvent(idEvent);
		utilisateursAll = juryService.findAllJurys();
		for (Utilisateur utilisateur : utilisateurs) {
			utilisateursAll.remove(utilisateur);
		}
		
	}
	
	public void buttonActionValider() {
		Navigation navigation = new Navigation();
		navigation.redirect("eventAccueil.xhtml?id="+idEvent);
	}


}
