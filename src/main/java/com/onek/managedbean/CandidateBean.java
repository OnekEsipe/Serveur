package com.onek.managedbean;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
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

import au.com.bytecode.opencsv.CSVReader;

@Component("candidate")
public class CandidateBean implements Serializable {
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
	// Gestion import
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
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("idEvent")) {
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

	// Ajout de candidats via un import de fichier
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
		if (lastName.isEmpty()) {
			logInfo = "Merci de remplir tous les champs du formulaire";
			return;
		}
		if (firstName.isEmpty()) {
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

	public void fileImportCsv(FileUploadEvent event) throws IOException {

		List<String[]> data = new ArrayList<String[]>();
		List<Candidat> importedCandidats = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new InputStreamReader(event.getFile().getInputstream()), ';')) {
			String[] nextLine = null;
			// récupération des données du fichier
			try {
				while ((nextLine = reader.readNext()) != null) {
					int size = nextLine.length;
					// ligne vide
					if (size == 0) {
						continue;
					}
					String debut = nextLine[0].trim();
					if (debut.length() == 0 && size == 1) {
						continue;
					}
					// ligne de commentaire
					if (debut.startsWith("#")) {
						continue;
					}
					data.add(nextLine);
				}
			} catch (IOException e) {
				importLog = "Le contenu de votre fichier est incorrect ! Merci de le modifier et réessayer.";
				return;
			}

			int indexnom = -1;
			int indexprenom = -1;
			int colonnesize = data.get(0).length;
			for (int i = 0; i < colonnesize; i++) {
				if (data.get(0)[i].equalsIgnoreCase("nom")) {
					indexnom = i;
				} else if (data.get(0)[i].equalsIgnoreCase("prenom")) {
					indexprenom = i;
				}
			}
			if (indexnom == -1) {

				importLog = "Le Contenu de votre fichier est incorrect ! Merci de le modifier et réessayer.";
				return;
			}

			if (indexprenom != -1) {
				for (int i = 1; i < data.size(); i++) {
					importedCandidats.add(creerCandidatImporte(data.get(i)[indexnom], data.get(i)[indexprenom]));
				}
			} else {
				for (int i = 1; i < data.size(); i++) {
					importedCandidats.add(creerCandidatImporte(data.get(i)[indexnom], ""));
				}
			}
			if (!importedCandidats.isEmpty()) {
				candidateService.addCandidates(importedCandidats);
				candidats.addAll(importedCandidats);
			}
		} catch (IOException e) {
			importLog = "Le Contenu de votre fichier est incorrect ! Merci de le modifier et réessayer.";
			return;
		}

	}

	private Candidat creerCandidatImporte(String nom, String prenom) {
		Candidat candidat = new Candidat();
		candidat.setNom(nom);
		candidat.setPrenom(prenom);
		candidat.setEvenement(event);
		return candidat;
	}

	public void supprimerCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int idcandidat = Integer.valueOf(params.get("idcandidat"));
		Candidat candidat = candidateService.findCandidatesById(idcandidat);
		candidateService.supprimerCandidat(idcandidat);
		candidats.remove(candidat);
	}

	public void telechargerModele() throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("text/csv");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"modeleCandidat.csv\"");

	    OutputStream out = externalContext.getResponseOutputStream();
	    Writer writer = new OutputStreamWriter(out);

	    try {
	        writer.write("nom;prenom\n");
	        writer.write("exempleNom;exemplePrenom");
	    } finally {
	        if (writer != null) {
	            writer.close();
	        }
	    }

	    facesContext.responseComplete();
	}

	public void retour() {
		Navigation.redirect("eventAccueil.xhtml");
	}
}
