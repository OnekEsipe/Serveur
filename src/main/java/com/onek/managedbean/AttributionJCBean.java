package com.onek.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;
import com.onek.service.AttributionJCService;
import com.onek.utils.Navigation;

@Component("attributionjc")
public class AttributionJCBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private AttributionJCService attributionjcservice;
	
	private int idEvent;
	
	private List<Candidat> filteredcandidats;
	private Candidat selectedcandidats ;
	private List<Candidat> candidats;
	private Candidat candidat;
	
	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateurs;
	
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));
			candidats = attributionjcservice.listCandidatsByEvent(idEvent);
		    utilisateurs = attributionjcservice.listJurysByEvent(idEvent);
		}
	}
	
	public List<Candidat> getFilteredcandidats() {
		return filteredcandidats;
	}
	public void setFilteredcandidats(List<Candidat> filteredcandidats) {
		this.filteredcandidats = filteredcandidats;
	}
	public Candidat getSelectedcandidats() {
		return selectedcandidats;
	}
	public void setSelectedcandidats(Candidat selectedcandidat) {
		this.selectedcandidats = selectedcandidat;
	}
	public List<Candidat> getCandidats() {
		return candidats;
	}
	public void setCandidats(List<Candidat> candidats) {
		this.candidats = candidats;
	}
	public Candidat getCandidat() {
		return candidat;
	}
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}
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
	public Utilisateur getSelectedutilisateurs() {
		return selectedutilisateurs;
	}
	public void setSelectedutilisateurs(Utilisateur selectedutilisateur) {
		this.selectedutilisateurs = selectedutilisateur;
	}
	
	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public void buttonActionValider() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "eventAccueil.xhtml", "eventAccueil.xhtml".contains("?") ? "&" : "?"));
	}
}
