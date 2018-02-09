package com.onek.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.service.CandidateService;

@Component("candidate")
public class CandidateBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CandidateService candidateService;
	
	private String firstName;
	private String lastName;
	private int idevent = 1; /* pour les tests*/
	

	private List<Candidat> candidats;
	private Candidat candidat;

	private Candidat newCandidat;
	private String logInfo;
	
	
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	@PostConstruct
    public void init() {
      candidats = candidateService.findCandidatesByEvent(idevent);
    }
	
	public void addCandidate() {
		newCandidat = new Candidat();
		newCandidat.setPrenom(firstName);
		newCandidat.setNom(lastName);
		Evenement event = new Evenement();
		event.setIdevent(idevent);
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
	public int getIdevent() {
		return idevent;
	}
	public void setIdevent(int idevent) {
		this.idevent = idevent;
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
	public void click() {
		
		if(firstName.isEmpty() || lastName.isEmpty()) {
			logInfo = "Merci de remplir tous les champs du formulaire";
			return;
		}
		addCandidate();
		logInfo = "Ajout ok";
	}
	

	
}
