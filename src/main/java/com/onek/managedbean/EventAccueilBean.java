package com.onek.managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.EventAccueilService;
import com.onek.utils.Navigation;
import com.onek.utils.PasswordGenerator;

@Component("eventAccueil")
public class EventAccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	@Autowired
	EvenementService evenement;

	private final Navigation navigation = new Navigation();

	private int idEvent;
	private Evenement event;

	private String statut;
	private Date dateStart;
	private Date dateEnd;
	private Date timeStart;
	private Date timeEnd;
	private String message;
	private int juryAnonyme;

	private List<Candidat> filteredcandidats;
	private Candidat selectedcandidat;
	private List<Candidat> candidats;
	private Candidat candidat;

	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateur;

	private PasswordGenerator passwordGenerator;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			String idEventString = navigation.getURLParameter("id");
			setIdEvent(Integer.parseInt(idEventString));
			this.event = evenement.findById(idEvent);
			candidats = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);
			this.statut = event.getStatus();
			this.dateStart = event.getDatestart();
			this.dateEnd = event.getDatestop();
		}
	}

	public void addJuryAnonymeButton() {
		passwordGenerator = new PasswordGenerator();
		Utilisateur anonymousJury;
		if (juryAnonyme > 0) {
			for (int i = 0; i < juryAnonyme; i++) {
				anonymousJury = new Utilisateur();
				anonymousJury.setDroits("A");
				anonymousJury.setIsdeleted(false);
				if (i < 10) {
					anonymousJury.setLogin("Jury00" + i + "_" + idEvent);
				}
				if ((i >= 10) && (i < 100)) {
					anonymousJury.setLogin("Jury0" + i + "_" + idEvent);
				}
				if ((i >= 100) && (i < 1000)) {
					anonymousJury.setLogin("Jury" + i + "_" + idEvent);
				}
				anonymousJury.setMotdepasse(passwordGenerator.generatePassword(8));
				anonymousJury.setMail("");
				anonymousJury.setNom("");
				anonymousJury.setPrenom("");
				// save juryAnonyme
				System.out.println("login: " + anonymousJury.getLogin());
			}			
		}
	}

	public void eventUpdateButton() {
		// Pour test
		DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dfDate.format(dateStart);

		DateFormat dfTime = new SimpleDateFormat("HH:mm");
		String sTime = dfTime.format(timeStart);

		message = "Statut: " + statut + " dateStart:" + dateStart + " dateStartFORMATTE:" + sDate + " dateEnd:"
				+ dateEnd + " timeStart:" + timeStart + " timeStartFORMATTEE:" + sTime + " timeEnd:" + timeEnd;
	}

	public void supprimerCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int idcandidat = Integer.valueOf(params.get("idcandidat"));

		eventAccueilservice.supprimerCandidat(idcandidat);
		candidats = eventAccueilservice.listCandidatsByEvent(1);

	}

	public void supprimerUtilisateur() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int iduser = Integer.valueOf(params.get("iduser"));

		eventAccueilservice.supprimerUtilisateur(iduser);
		utilisateurs = eventAccueilservice.listJurysByEvent(1);
	}

	public void buttonGrille() {
		navigation.redirect("grille.xhtml?id=" + idEvent);
	}

	public void buttonAttribution() {
		navigation.redirect("attributionJuryCandidat.xhtml?id=" + idEvent);
	}

	public void buttonAddJury() {
		navigation.redirect("addJury.xhtml?id=" + idEvent);
	}

	public void buttonAddCandidat() {
		navigation.redirect("addCandidates.xhtml?id=" + idEvent);
	}

	public void buttonExport() {
		// to do
		// eventAccueilservice.listJurysByEvent().
	}
	
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

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

}
