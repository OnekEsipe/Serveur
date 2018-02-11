package com.onek.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.service.CandidateService;
import com.onek.service.EvenementService;
import com.onek.utils.Navigation;

@Component("candidate")
public class CandidateBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EvenementService evenement;
	
	private List<Candidat> filteredcandidats;
	
	private String firstName;
	private String lastName;
	
	private int idEvent;
	private Evenement event;

	private List<Candidat> candidats;
	private Candidat candidat;

	private Candidat newCandidat;
	private String logInfo;
	
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			Navigation navigation = new Navigation();
			String idEventString = navigation.getURLParameter("id");
			setIdEvent(Integer.parseInt(idEventString));
			this.event = evenement.findById(idEvent);
			candidats = candidateService.findCandidatesByEvent(idEvent);
		}
	}
	
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	public void addCandidate() {
		newCandidat = new Candidat();
		newCandidat.setPrenom(firstName);
		newCandidat.setNom(lastName);
		newCandidat.setEvenement(event);
		candidateService.addCandidate(newCandidat);
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idevent) {
		this.idEvent = idevent;
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
	
	public List<Candidat> getFilteredcandidats() {
		return filteredcandidats;
	}

	public void setFilteredcandidats(List<Candidat> filteredcandidats) {
		this.filteredcandidats = filteredcandidats;
	}

	public void click() {
		
		if(firstName.isEmpty() || lastName.isEmpty()) {
			logInfo = "Merci de remplir tous les champs du formulaire";
			return;
		}
		addCandidate();
		logInfo = "Ajout ok";
	}
	
	public void supprimerCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		int idcandidat = Integer.valueOf(params.get("idcandidat"));
        
		candidateService.supprimerCandidat(idcandidat);
		candidats = candidateService.findCandidatesByEvent(idEvent);
	      
	}
	
}
