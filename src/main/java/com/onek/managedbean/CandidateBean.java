package com.onek.managedbean;

import java.io.IOException;
import java.io.InputStreamReader;
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

/**
 * ManagedBean CandidateBean
 */
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
	private final List<Candidat> importedCandidates = new ArrayList<>();

	private String logInfo;
	private String importLog;
	private String message = "Confirmer l'envoi du fichier ?";

	/**
	 * Méthode appelée lors d'un GET sur la page addCandidates.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
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

	/**
	 * Getter de la variable logInfo
	 * @return logInfo Message d'information
	 */
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * Setter de la variable logInfo
	 * @param logInfo Le message a afficher
	 */
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	/**
	 * Ajout de candidats via un import de fichier
	 * @param candidates Liste de candidats
	 */
	public void addCandidates(List<Candidat> candidates) {
		candidateService.addCandidates(candidates);
	}

	/**
	 * Getter de la variable firstName
	 * @return firstName Prenom de l'utilisateur
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter de la variable firstName
	 * @param firstName Prenom de l'utilisateur
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter de la variable lastName
	 * @return lastName Nom de l'utilisateur
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter de la variable lastName
	 * @param lastName Nom de l'utilisateur
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter de la variable idEvent
	 * @return idEvent Id de l'événement
	 */
	public int getIdEvent() {
		return idEvent;
	}

	/**
	 * Setter de la variable idEvent
	 * @param idEvent Id de l'événement
	 */
	public void setIdEvent(int idevent) {
		this.idEvent = idevent;
	}

	/**
	 * Getter de la variable candidats
	 * @return candidats Liste des candidats
	 */
	public List<Candidat> getCandidats() {
		return candidats;
	}

	/**
	 * Setter de la variable candidats
	 * @param candidats Liste des candidats
	 */
	public void setCandidats(List<Candidat> candidats) {
		this.candidats = candidats;
	}

	/**
	 * Getter de la variable candidat
	 * @return candidat Un candidat
	 */
	public Candidat getCandidat() {
		return candidat;
	}

	/**
	 * Setter de la variable candidat
	 * @param candidat Un candidat
	 */
	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	/**
	 * Getter de la variable filteredcandidats
	 * @return filteredcandidats Liste des candidats filtrés
	 */
	public List<Candidat> getFilteredcandidats() {
		return filteredcandidats;
	}

	/**
	 * Setter de la variable filteredcandidats
	 * @param filteredcandidats Liste des candidats filtrés
	 */
	public void setFilteredcandidats(List<Candidat> filteredcandidats) {
		this.filteredcandidats = filteredcandidats;
	}

	/**
	 * Getter de la variable importedCandidates
	 * @return importedCandidates Liste des candidats importés
	 */
	public List<Candidat> getImportedCandidates() {
		return importedCandidates;
	}

	/**
	 * Getter de la variable importLog
	 * @return importLog Message pour l'importation de candidats
	 */
	public String getImportLog() {
		return importLog;
	}

	/**
	 * Setter de la variable importLog
	 * @param importLog Message pour l'importation de candidats
	 */
	public void setImportLog(String importLog) {
		this.importLog = importLog;
	}

	/**
	 * Getter de la variable message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter de la variable message
	 * @return message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * Création d'un candidat. Le prénom est facultatif
	 */
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

	/**
	 * Importation d'un fichier candidat de type csv. Format du csv : nom;prenom
	 * @param event Evenement lié à l'upload du fichier
	 * @throws IOException
	 */
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

	/**
	 * Suppression d'un candidat
	 */
	public void supprimerCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int idcandidat = Integer.valueOf(params.get("idcandidat"));
		Candidat candidat = candidateService.findCandidatesById(idcandidat);
		candidateService.supprimerCandidat(idcandidat);
		candidats.remove(candidat);
	}

	/**
	 * Création du modele csv pour l'import
	 * @throws IOException
	 */
	public void telechargerModele() throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseContentType("text/csv; charset=UTF-8");
	    externalContext.setResponseCharacterEncoding("UTF-8");
	    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"modeleCandidat.csv\"");
	    Writer writer = externalContext.getResponseOutputWriter();
	    try {
	        writer.write("nom;prenom\n");
	        writer.write("Smith;James");
	    } finally {
	        if (writer != null) {
	            writer.close();
	        }
	    }

	    facesContext.responseComplete();
	}
	
	/**
	 * Suppression de tous les candidats
	 */
	public void suppressAllCandidates() {
		if(candidats.size() > 0) {
			for(Candidat candidat : candidats) {
				candidateService.supprimerCandidat(candidat.getIdcandidat());
			}
		}
		Navigation.redirect("addCandidates.xhtml");
	}
}
