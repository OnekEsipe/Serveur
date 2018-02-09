package com.onek.managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;
import com.onek.service.EventAccueilService;

@Component("eventAccueil")
public class EventAccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;
	
	private String statut;
	private Date dateStart;
	private Date dateEnd;
	private Date timeStart;
	private Date timeEnd;
	private String message;
	private int juryAnonyme;
	
	private List<Candidat> filteredcandidats;
	private Candidat selectedcandidat ;
	private List<Candidat> candidats;
	private Candidat candidat;
	
	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateur;
	
	
	public List<Utilisateur> getFilteredutilisateurs() {
		return filteredutilisateurs;
	}

	public void setFilteredutilisateurs(List<Utilisateur> filteredutilisateurs) {
		this.filteredutilisateurs = filteredutilisateurs;
	}

	public Utilisateur getSelectedutilisateur() {
		return selectedutilisateur;
	}

	public void setSelectedutilisateur(Utilisateur selectedutilisateur) {
		this.selectedutilisateur = selectedutilisateur;
	}

	public List<Candidat> getFilteredcandidats() {
		return filteredcandidats;
	}

	public void setFilteredcandidats(List<Candidat> filteredcandidats) {
		this.filteredcandidats = filteredcandidats;
	}

	public Candidat getSelectedcandidat() {
		return selectedcandidat;
	}

	public void setSelectedcandidat(Candidat selectedcandidat) {
		this.selectedcandidat = selectedcandidat;
	}

	public int getJuryAnonyme() {
		return juryAnonyme;
	}

	public void setJuryAnonyme(int juryAnonyme) {
		this.juryAnonyme = juryAnonyme;
	}

	
	
	
	@PostConstruct
    public void init() {
      candidats = eventAccueilservice.listCandidatsByEvent(1);
      utilisateurs = eventAccueilservice.listJurysByEvent();
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}
	
	public void addJuryAnonymeButton() {
		message = "Nombre de jury anonymes à ajouter : " + juryAnonyme;
	}

	public void eventUpdateButton() {
		// Pour test
		DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dfDate.format(dateStart);
		
		DateFormat dfTime = new SimpleDateFormat("HH:mm");
		String sTime = dfTime.format(timeStart);
		
		message = "Statut: " + statut + " dateStart:" + dateStart + " dateStartFORMATTE:" + sDate + " dateEnd:" + dateEnd + " timeStart:" + timeStart
				+ " timeStartFORMATTEE:" + sTime + " timeEnd:" + timeEnd;
	}
	public void supprimerCandidat() {
		// to do
	}
	public void supprimerUtilisateur() {
		// to do
	}
	public void buttonGrille() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "viewCreateEvent.xhtml", "viewCreateEvent.xhtml".contains("?") ? "&" : "?"));
	}
	public void buttonAttribution() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "viewCreateEvent.xhtml", "viewCreateEvent.xhtml".contains("?") ? "&" : "?"));
	}
	public void buttonAddJury() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "viewCreateEvent.xhtml", "viewCreateEvent.xhtml".contains("?") ? "&" : "?"));
	}
	public void buttonAddCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "viewCreateEvent.xhtml", "viewCreateEvent.xhtml".contains("?") ? "&" : "?"));
	}
	public void buttonExport() {
		//to do
		//eventAccueilservice.listJurysByEvent().
	}
}
