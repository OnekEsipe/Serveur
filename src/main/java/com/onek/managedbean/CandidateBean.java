package com.onek.managedbean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.FileUploadEvent;
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

	private  List<Candidat> candidats;
	private Candidat candidat;
	//Gestion import
	private final List<Candidat> importedCandidates = new ArrayList<>();

	private String logInfo;
	private String importLog;
	private String message = "Confirmer l'envoi du fichier ?";

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				Navigation.redirect("index.xhtml");
				return;
			}
			if(!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("idEvent")) {
				Navigation.redirect("accueil.xhtml");
				return;
			}
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));
			this.event = evenement.findById(idEvent);
			candidats = candidateService.findCandidatesByEvent(idEvent);
			emptyForm();
		}
	}

	private void emptyForm() {
		setFirstName("");
		setLastName("");
		setImportLog("");
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
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

	public List<Candidat> getImportedCandidates() {
		return importedCandidates;
	}


	public String getImportLog() {
		return importLog;
	}

	public void setImportLog(String importLog) {
		this.importLog = importLog;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void click() {
		if(lastName.isEmpty()) {
			logInfo = "Merci de remplir tous les champs du formulaire";
			return;
		}
		if(firstName.isEmpty()) {
			firstName = "";
		}
		Candidat newCandidat = new Candidat();
		newCandidat.setPrenom(firstName);
		newCandidat.setNom(lastName);
		newCandidat.setEvenement(event);
		candidateService.addCandidate(newCandidat);
		candidats.add(newCandidat);
		firstName = "";
		lastName = "";
	}

	public void importFile(FileUploadEvent  event)   {

		InputStream input = null;
		BufferedReader reader = null;
			try {
				 input = event.getFile().getInputstream();
				 reader = new BufferedReader(new InputStreamReader(input));
				String line;
				while((line = reader.readLine()) != null) {
					String [] values = line.split(",|;");
					
					if(values.length > 1 && values[0].length() > 0 && values[1].length() > 0) {
						// Création de l'objet candidat
						if(values[0].contains("|,|;") || values[1].contains("|,|;")) {
							importLog = "Le Contenu de votre fichier est incorrect ! Merci de le modifier et réessayer.";
							return;
						}

						addCandidate(values[0],values[1]);
					}
					else if(values.length == 1 && values[0].length() > 0) {
						addCandidate(values[0],"");
						
					}else {
						//On autorise les lignes vides (simplicité  le cas écheant pour l'utilisaliteur en terme de lisibilité)
						continue;
					}

				}
			} catch (IOException e) {
				importLog = "Le Contenu de votre fichier est incorrect ! Merci de le modifier et réessayer.";
				return;
			}finally {
				try {
					input.close();
					reader.close();
				} catch (IOException e) {
					return;
				}
				
			}
			//verification si doublon
		if(!importedCandidates.isEmpty()) {

			//Verification si doublon
			Set<String> setCandidats = new HashSet<>();
			importedCandidates.forEach(candidat -> {
				System.out.println(candidat.getNom()+" "+candidat.getPrenom());
				if(!setCandidats.add(candidat.getNom()+candidat.getPrenom())) {
					String str  = "Votre fichier comporte des candidats homonymes, voulez vous envoyez?";
					
					//Mettre à jour le contenu du dialogue
					setMessage(str);
					return;
				}
			});
			setCandidats.clear();
		}else {
			importLog = "Le Contenu de votre fichier est incorrect ! Merci de le modifier et réessayer.";
			return;
		}

	}

	private void addCandidate(String nom, String prenom) {
		Candidat candidat = new Candidat();
		candidat.setNom(nom);
		candidat.setPrenom(prenom);
		candidat.setEvenement(event);
		importedCandidates.add(candidat);
		setMessage("Confirmer l'envoi du fichier ?");
	}

	public void sendFile() {

		if(importedCandidates.isEmpty()) return;
		addCandidates(importedCandidates);
		importedCandidates.clear();
		importLog = "Votre fichier a bien été envoyé";
	}

	public void supprimerCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		int idcandidat = Integer.valueOf(params.get("idcandidat"));
		candidateService.supprimerCandidat(idcandidat);
		candidats = candidateService.findCandidatesByEvent(idEvent);   
	}

	public void retour() {
		Navigation.redirect("eventAccueil.xhtml");
	}
}
