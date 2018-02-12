package com.onek.managedbean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.primefaces.model.UploadedFile;
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

	private List<Candidat> filteredcandidats;

	private String firstName;
	private String lastName;
	private int idevent = 1; /* pour les tests*/


	private  List<Candidat> candidats;
	private Candidat candidat;
	private Evenement event;
	//Gestion import
	private final List<Candidat> importedCandidates = new ArrayList<>();


	private UploadedFile file;



	private Candidat newCandidat;
	private String logInfo;
	private String importLog;


	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	@PostConstruct
	public void init() {
		candidats = candidateService.findCandidatesByEvent(idevent);
		event = new Evenement();
		event.setIdevent(idevent);
	}

	//Ajout de candidats manuellement
	public void addCandidate() {
		newCandidat = new Candidat();
		newCandidat.setPrenom(firstName);
		newCandidat.setNom(lastName);
		newCandidat.setEvenement(event);
		candidateService.addCandidate(newCandidat);
	}
	//Ajout de candidats via un import de fichier
	public void addCandidates(List<Candidat> candidates) {
		candidateService.addCandidates(candidates);
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

	public List<Candidat> getFilteredcandidats() {
		return filteredcandidats;
	}

	public void setFilteredcandidats(List<Candidat> filteredcandidats) {
		this.filteredcandidats = filteredcandidats;
	}

	public UploadedFile getFile() {
		return file;
	}


	public List<Candidat> getImportedCandidates() {
		return importedCandidates;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}


	public String getImportLog() {
		return importLog;
	}

	public void setImportLog(String importLog) {
		this.importLog = importLog;
	}

	public void click() {

		if(firstName.isEmpty() || lastName.isEmpty()) {
			logInfo = "Merci de remplir tous les champs du formulaire";
			return;
		}
		addCandidate();
		logInfo = "Ajout ok";
	}

	public void importFile()  {

		if(file.getFileName().isEmpty()) {
			importLog = "Merci d'importer votre fichier de candidat";
		}else {
			importLog = "Votre fichier a bien été envoyé";
			try (BufferedReader reader =  new BufferedReader(new FileReader(file.getFileName()))){
				String line;
				while((line = reader.readLine()) != null) {
					String [] values = line.split(",|;|:", 2);
					
					// Création de l'objet candidat
					
					Candidat candidat = new Candidat();
					candidat.setNom(values[0]);
					candidat.setPrenom(values[1]);
					candidat.setEvenement(event);
					importedCandidates.add(candidat);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			addCandidates(importedCandidates);
		}
	}


}
