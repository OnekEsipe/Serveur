package com.onek.managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.service.CandidateService;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.service.EventAccueilService;
import com.onek.service.GrilleService;
import com.onek.service.JuryService;
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
	private JuryService juryService;

	@Autowired
	EvaluationService evaluation;

	@Autowired
	private EvenementService evenementService;

	@Autowired
	private GrilleService grilleservice;

	@Autowired
	private CandidateService candidatService;

	private int idEvent;
	private Evenement event;

	private String nom;
	private String statut;
	private Date dateStart;
	private Date dateEnd;
	private Date timeStart;
	private Date timeEnd;
	private String message;
	private int juryAnonyme;
	private String visibleB = "false";
	private String visibleO = "false";
	private String visibleF = "false";

	private List<Candidat> filteredcandidats;
	private Candidat selectedcandidat;
	private List<Candidat> candidats;
	private Candidat candidat;

	private List<Utilisateur> utilisateurs;
	private List<Utilisateur> utilisateursAnos;

	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateur;
	private List<String> selectedoptions;
	private PasswordGenerator passwordGenerator;
	
	private boolean disabledSiBrouillon;

	public boolean isDisabledSiBrouillon() {
		return disabledSiBrouillon;
	}

	public void setDisabledSiBrouillon(boolean disabledSiBrouillon) {
		this.disabledSiBrouillon = disabledSiBrouillon;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getVisibleF() {
		return visibleF;
	}

	public void setVisibleF(String visibleF) {
		this.visibleF = visibleF;
	}

	public String getVisibleB() {
		return visibleB;
	}

	public void setVisibleB(String visibleB) {
		this.visibleB = visibleB;
	}

	public String getVisibleO() {
		return visibleO;
	}

	public void setVisibleO(String visibleO) {
		this.visibleO = visibleO;
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

	public Evenement getEvent() {
		return event;
	}

	public void setEvent(Evenement event) {
		this.event = event;
	}

	public List<String> getSelectedoptions() {
		return selectedoptions;
	}

	public void setSelectedoptions(List<String> selectedoptions) {
		this.selectedoptions = selectedoptions;
	}

	public void before(ComponentSystemEvent e) throws ParseException {
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
			candidats = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);

			utilisateursAnos = new ArrayList<>();
			List<Jury> jurys = juryService.listJurysAnnonymesByEvent(idEvent);
			jurys.forEach(jury -> utilisateursAnos.add(jury.getUtilisateur()));

			this.statut = event.getStatus();
			if (statut.equals("Brouillon")) {

				setVisibleB("true");
				setVisibleO("false");
				setVisibleF("false");
				disabledSiBrouillon = false;
			} else if (statut.equals("Ouvert")) {
				setVisibleB("false");
				setVisibleO("true");
				setVisibleF("false");
				disabledSiBrouillon = true;
			} else {
				setVisibleB("false");
				setVisibleO("false");
				setVisibleF("true");
				disabledSiBrouillon = true;
			}
			this.dateStart = event.getDatestart();
			this.dateEnd = event.getDatestop();

			DateFormat dfTime = new SimpleDateFormat("HH:mm");
			String sTimeStart = dfTime.format(event.getDatestart().getTime());
			String sTimeEnd = dfTime.format(event.getDatestop().getTime());
			timeStart = dfTime.parse(sTimeStart);
			timeEnd = dfTime.parse(sTimeEnd);
			FacesContext context = FacesContext.getCurrentInstance();
			String viewId = context.getViewRoot().getViewId();
			ViewHandler handler = context.getApplication().getViewHandler();
			UIViewRoot root = handler.createView(context, viewId);
			root.setViewId(viewId);
			context.setViewRoot(root);
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
		int increment = juryService.findAnonymousByIdEvent(idEvent).size();
		if (juryAnonyme > 0) {
			for (int i = 0 + increment; i < juryAnonyme + increment; i++) {
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

			// On met à jour la liste des jurys anonymes
			anonymousJurys.forEach(jury -> utilisateursAnos.add(jury));
			utilisateursAnos.forEach(juryAno -> System.out.println(juryAno.getNom()));

		}
	}

	public void eventUpdateButton() {
		event.setDatestart(new Date(dateStart.getTime() + timeStart.getTime()));
		event.setDatestop(new Date(dateEnd.getTime() + timeEnd.getTime()));
		event.setStatus(statut);
		event.setNom(nom);

		eventAccueilservice.editEvenement(event);
		Navigation.redirect("eventAccueil.xhtml");

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
		Navigation.redirect("grille.xhtml");
	}

	public void buttonAttribution() {
		Navigation.redirect("attributionJuryCandidat.xhtml");
	}

	public void buttonAddJury() {
		Navigation.redirect("addJury.xhtml");
	}

	public void buttonAddCandidat() {
		Navigation.redirect("addCandidates.xhtml");
	}

	public void buttonStats() {
		Navigation.redirect("statistiques.xhtml");
	}

	public void buttonDupliquer() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String login = (String) fc.getExternalContext().getSessionMap().get("user");
		Utilisateur utilisateur = userService.findByLogin(login);
		Evenement eventbis = evenementService.addDuplicatedEvent(createEventDuplique(utilisateur));
		List<Critere> criteresbis = new ArrayList<>();
		criteresbis = duplicateCritere(eventbis);
		grilleservice.addCriteres(criteresbis);
		eventbis.setCriteres(new ArrayList<>());
		for (Critere critere : criteresbis) {
			eventbis.addCritere(critere);
		}
		if (!selectedoptions.isEmpty()) {
			for (String option : selectedoptions) {
				if (option.equals("candidats")) {
					List<Candidat> candidatsBis = new ArrayList<>();
					candidatsBis = duplicateCandidat(eventbis);
					candidatService.addCandidates(candidatsBis);
					eventbis.setCandidats(new ArrayList<>());
					for (Candidat candidat : candidatsBis) {
						eventbis.addCandidat(candidat);
					}
				}
				if (option.equals("jurys")) {
					List<Jury> jurysBis = new ArrayList<>();
					jurysBis = duplicateJury(eventbis);
					eventbis.setJurys(new ArrayList<>());
					for (Jury jury : jurysBis) {
						eventbis.addJury(jury);
					}
					juryService.addListJurys(jurysBis);
				}
			}
		}
		selectedoptions.clear();

	}

	private List<Critere> duplicateCritere(Evenement eventbis) {
		List<Critere> criteres = new ArrayList<>();
		for (Critere critere : event.getCriteres()) {
			Critere c = new Critere();
			c.setDescripteurs(new ArrayList<>());
			c.setEvenement(eventbis);
			c.setCategorie(critere.getCategorie());
			c.setCoefficient(critere.getCoefficient());
			c.setTexte(critere.getTexte());
			for (Descripteur descripteur : critere.getDescripteurs()) {
				Descripteur d = new Descripteur();
				d.setNiveau(descripteur.getNiveau());
				d.setPoids(descripteur.getPoids());
				d.setTexte(descripteur.getTexte());
				d.setCritere(c);
				c.addDescripteur(d);
			}
			criteres.add(c);
		}
		return criteres;
	}

	private List<Jury> duplicateJury(Evenement eventbis) {
		List<Jury> jurysBis = new ArrayList<>();
		for (Jury jury : event.getJurys()) {
			Jury jurybis = new Jury();
			jurybis.setEvenement(eventbis);
			jurybis.setUtilisateur(jury.getUtilisateur());
			jurysBis.add(jurybis);
		}
		return jurysBis;
	}

	private List<Candidat> duplicateCandidat(Evenement eventbis) {
		List<Candidat> candidatsBis = new ArrayList<>();
		for (Candidat candidat : event.getCandidats()) {
			Candidat candidatbis = new Candidat();
			candidatbis.setNom(candidat.getNom());
			candidatbis.setPrenom(candidat.getPrenom());
			candidatbis.setEvenement(eventbis);
			candidatsBis.add(candidatbis);
		}
		return candidatsBis;
	}

	private Evenement createEventDuplique(Utilisateur utilisateur) {
		Evenement eventBis = new Evenement();
		eventBis.setNom(event.getNom() + "Bis");
		eventBis.setDatestart(event.getDatestart());
		eventBis.setDatestop(event.getDatestop());
		eventBis.setIsdeleted(false);
		eventBis.setIsopened(false);
		eventBis.setUtilisateur(utilisateur);
		eventBis.setStatus("Brouillon");
		eventBis.setIssigned(event.getIssigned());
		eventBis.setCode(generateCode());
		return eventBis;
	}

	private String generateCode() {
		PasswordGenerator pass = new PasswordGenerator();
		Integer id = event.getIdevent();
		int length = (int) (Math.log10(id) + 1);
		String codeEvent = id + pass.generateCode(10 - length);
		return codeEvent;
	}
}