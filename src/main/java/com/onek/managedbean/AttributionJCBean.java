package com.onek.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

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
	
	@Autowired
	Navigation navigation;	
	
	private List<Candidat> filteredcandidats;
	private Candidat selectedcandidats ;
	private List<Candidat> candidats;
	private Candidat candidat;
	
	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateurs;
	
	
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
	
	@PostConstruct
    public void init() {
      candidats = attributionjcservice.listCandidatsByEvent(1);
      utilisateurs = attributionjcservice.listJurysByEvent(1);
    }
	public void buttonActionValider() {
		navigation.redirect("eventAccueil.xhtml");
	}
}
