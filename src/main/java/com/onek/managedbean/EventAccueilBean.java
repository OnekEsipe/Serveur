package com.onek.managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.service.AddJuryService;
import com.onek.service.EvenementService;
import com.onek.service.EventAccueilService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;
import com.onek.utils.PasswordGenerator;

@Component("eventAccueil")
public class EventAccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	@Autowired
	EvenementService evenement;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddJuryService juryServices;


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
	private List<Utilisateur> utilisateursAnos;
	
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateur;
	
	private PasswordGenerator passwordGenerator;

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

	public void before(ComponentSystemEvent e) throws ParseException {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			Navigation navigation = new Navigation();
			String idEventString = navigation.getURLParameter("id");
			setIdEvent(Integer.parseInt(idEventString));
			this.event = evenement.findById(idEvent);
			candidats = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);
			
			utilisateursAnos = new ArrayList<>();
			List<Jury> jurys = juryServices.listJurysAnnonymesByEvent(idEvent);
			jurys.forEach(jury -> utilisateursAnos.add(jury.getUtilisateur()));
			
			this.statut = event.getStatus();
			this.dateStart = event.getDatestart();
			this.dateEnd = event.getDatestop();

			DateFormat dfTime = new SimpleDateFormat("HH:mm");
			String sTimeStart = dfTime.format(event.getDatestart().getTime());
			String sTimeEnd = dfTime.format(event.getDatestop().getTime());
			timeStart = dfTime.parse(sTimeStart);
			timeEnd = dfTime.parse(sTimeEnd);
		}
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
	
	public List<Utilisateur> getUtilisateursAnos() {
		return utilisateursAnos;
	}

	public void setUtilisateursAnos(List<Utilisateur> utilisateursAnos) {
		this.utilisateursAnos = utilisateursAnos;
	}

	public void addJuryAnonymeButton() {
		passwordGenerator = new PasswordGenerator();
		List<Utilisateur> anonymousJurys = new ArrayList<>();
		Utilisateur anonymousJury;
		if (juryAnonyme > 0) {
			for (int i = 0; i < juryAnonyme; i++) {
				anonymousJury = new Utilisateur();
				anonymousJury.setDroits("A");
				anonymousJury.setIsdeleted(false);
				if (i < 10) {
					anonymousJury.setLogin("Jury00" + i + "_" + idEvent);
					anonymousJury.setNom("Jury00" + i + "_" + idEvent);
				}
				if ((i >= 10) && (i < 100)) {
					anonymousJury.setLogin("Jury0" + i + "_" + idEvent);
					anonymousJury.setNom("Jury0" + i + "_" + idEvent);
				}
				if ((i >= 100) && (i < 1000)) {
					anonymousJury.setLogin("Jury" + i + "_" + idEvent);
					anonymousJury.setNom("Jury" + i + "_" + idEvent);
				}
				anonymousJury.setMotdepasse(passwordGenerator.generatePassword(8));
				anonymousJury.setMail("");
				anonymousJury.setPrenom("");
				anonymousJurys.add(anonymousJury);
			}	
			userService.addJurysAnonymes(anonymousJurys, event);	
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);
			
			//On met à jour la liste des jurys anonymes
			anonymousJurys.forEach(jury -> utilisateursAnos.add(jury));
			utilisateursAnos.forEach(juryAno -> System.out.println(juryAno.getNom()) );

		}
	}

	public void eventUpdateButton() {
		event.setDatestart(new Date(dateStart.getTime() + timeStart.getTime()));
		event.setDatestop(new Date(dateEnd.getTime() + timeEnd.getTime()));
		event.setStatus(statut);
		eventAccueilservice.editEvenement(event);
		
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


}