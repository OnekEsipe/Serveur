package com.onek.managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.onek.utils.Password;

/**
 * ManagedBean EventAccueilBean
 */
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
	private List<String> selectedoptions;
	private boolean disabledSiBrouillon;
	private boolean disabledSiSupprime;

	private String visibleB = "false";
	private String visibleO = "false";
	private String visibleF = "false";

	/**
	 * Getter de la variable disabledSiSupprime
	 * @return disabledSiSupprime 
	 */
	public boolean isDisabledSiSupprime() {
		return disabledSiSupprime;
	}

	/**
	 * Setter de la variable disabledSiSupprime
	 * @param disabledSiSupprime 
	 */
	public void setDisabledSiSupprime(boolean disabledSiSupprime) {
		this.disabledSiSupprime = disabledSiSupprime;
	}

	/**
	 * Getter de la variable disabledSiBrouillon
	 * @return disabledSiBrouillon 
	 */
	public boolean isDisabledSiBrouillon() {
		return disabledSiBrouillon;
	}

	/**
	 * Setter de la variable disabledSiBrouillon
	 * @param disabledSiBrouillon 
	 */
	public void setDisabledSiBrouillon(boolean disabledSiBrouillon) {
		this.disabledSiBrouillon = disabledSiBrouillon;
	}

	/**
	 * Getter de la variable nom
	 * @return nom 
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de la variable nom
	 * @param nom 
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de la variable visibleF
	 * @return visibleF Visibilité en statut fermé
	 */
	public String getVisibleF() {
		return visibleF;
	}

	/**
	 * Setter de la variable visibleF
	 * @param visibleF Visibilité en statut fermé
	 */
	public void setVisibleF(String visibleF) {
		this.visibleF = visibleF;
	}

	/**
	 * Getter de la variable visibleB
	 * @return visibleB Visibilité en statut brouillon
	 */
	public String getVisibleB() {
		return visibleB;
	}

	/**
	 * Setter de la variable visibleB
	 * @return visibleB Visibilité en statut brouillon
	 */
	public void setVisibleB(String visibleB) {
		this.visibleB = visibleB;
	}

	/**
	 * Getter de la variable visibleO
	 * @return visibleO Visibilité en statut ouvert
	 */
	public String getVisibleO() {
		return visibleO;
	}

	/**
	 * Setter de la variable visibleO
	 * @param visibleO Visibilité en statut ouvert
	 */
	public void setVisibleO(String visibleO) {
		this.visibleO = visibleO;
	}

	/**
	 * Getter de la variable event
	 * @return event
	 */
	public Evenement getEvent() {
		return event;
	}

	/**
	 * Setter de la variable event
	 * @param event
	 */
	public void setEvent(Evenement event) {
		this.event = event;
	}

	/**
	 * Getter de la variable getSelectedoptions
	 * @return getSelectedoptions Liste des options selectionnées
	 */
	public List<String> getSelectedoptions() {
		return selectedoptions;
	}

	/**
	 * Setter de la variable getSelectedoptions
	 * @param getSelectedoptions Liste des options selectionnées
	 */
	public void setSelectedoptions(List<String> selectedoptions) {
		this.selectedoptions = selectedoptions;
	}

	/**
	 * Méthode appelée lors d'un GET sur la page eventAccueil.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
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
			this.nom = event.getNom();
			disabledSiSupprime = event.getIsdeleted();
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

	/**
	 * Getter de la variable getTimeStart
	 * @return getTimeStart Heure de début
	 */
	public Date getTimeStart() {
		return timeStart;
	}

	/**
	 * Setter de la variable getTimeStart
	 * @param getTimeStart Heure de début
	 */
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * Getter de la variable timeEnd
	 * @return timeEnd Heure de fin
	 */
	public Date getTimeEnd() {
		return timeEnd;
	}

	/**
	 * Setter de la variable timeEnd
	 * @param timeEnd Heure de fin
	 */
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	/**
	 * Getter de la variable dateEnd
	 * @return dateEnd Date de fin
	 */
	public Date getDateEnd() {
		return dateEnd;
	}

	/**
	 * Setter de la variable dateEnd
	 * @param dateEnd Date de fin
	 */
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	/**
	 * Getter de la variable dateStart
	 * @return dateStart Date de début
	 */
	public Date getDateStart() {
		return dateStart;
	}

	/**
	 * Setter de la variable dateStart
	 * @param dateStart Date de début
	 */
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * Getter de la variable statut
	 * @return statut
	 */
	public String getStatut() {
		return statut;
	}

	/**
	 * Setter de la variable statut
	 * @param statut
	 */
	public void setStatut(String statut) {
		this.statut = statut;
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
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	/**
	 * Update des champs du formulaire
	 */
	public void eventUpdateButton() {
		event.setDatestart(new Date(dateStart.getTime() + timeStart.getTime()));
		event.setDatestop(new Date(dateEnd.getTime() + timeEnd.getTime()));
		event.setStatus(statut);
		if (!nom.isEmpty()) {
			event.setNom(nom);
		}
		eventAccueilservice.editEvenement(event);
		Navigation.redirect("eventAccueil.xhtml");
	}

	/**
	 * Supprime un événement et redirige vers la page accueil.xhtml
	 */
	public void supprimerEvent() {
		evenementService.supprimerEvent(event.getIdevent());
		Navigation.redirect("accueil.xhtml");
	}

	/**
	 * Ré-active un événement et redirige vers la page accueil.xhtml
	 */
	public void buttonRecuperer() {
		event.setIsdeleted(false);
		evenementService.editEvenement(event);
		Navigation.redirect("accueil.xhtml");
	}

	/**
	 * Duplique un événement et ses critères. Il est possible aussi de dupliquer les candidats et les jurys si les options sont selectionnées.
	 */
	public void buttonDupliquer() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String login = (String) fc.getExternalContext().getSessionMap().get("user");
		Utilisateur utilisateur = userService.findByLogin(login);
		Evenement eventbis = evenementService.addEvenement(createEventDuplique(utilisateur));
		List<Critere> criteresbis = new ArrayList<>();
		criteresbis = duplicateCritere(eventbis);
		if (!criteresbis.isEmpty()) {
			grilleservice.addCriteres(criteresbis);
			eventbis.setCriteres(new ArrayList<>());
			for (Critere critere : criteresbis) {
				eventbis.addCritere(critere);
			}
		}
		if (!selectedoptions.isEmpty()) {
			for (String option : selectedoptions) {
				if (option.equals("candidats")) {
					List<Candidat> candidatsBis = new ArrayList<>();
					candidatsBis = duplicateCandidat(eventbis);
					if (!candidatsBis.isEmpty()) {
						candidatService.addCandidates(candidatsBis);
						eventbis.setCandidats(new ArrayList<>());
						for (Candidat candidat : candidatsBis) {
							eventbis.addCandidat(candidat);
						}
					}
				}
				if (option.equals("jurys")) {
					List<Jury> jurysBis = new ArrayList<>();
					jurysBis = duplicateJury(eventbis);
					if (!jurysBis.isEmpty()) {
						eventbis.setJurys(new ArrayList<>());
						for (Jury jury : jurysBis) {
							eventbis.addJury(jury);
						}
						juryService.addListJurys(jurysBis);
					}
				}
			}
		}
		selectedoptions.clear();
		Navigation.redirect("accueil.xhtml");
	}

	private List<Critere> duplicateCritere(Evenement eventbis) {
		List<Critere> criteres = new ArrayList<>();
		if (!event.getCriteres().isEmpty()) {
			for (Critere critere : event.getCriteres()) {
				Critere c = new Critere();
				c.setDescripteurs(new ArrayList<>());
				c.setEvenement(eventbis);
				c.setCategorie(critere.getCategorie());
				c.setCoefficient(critere.getCoefficient());
				c.setTexte(critere.getTexte());
				if (!critere.getDescripteurs().isEmpty()) {
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
			}
		}
		return criteres;
	}

	private List<Jury> duplicateJury(Evenement eventbis) {
		List<Jury> jurysBis = new ArrayList<>();
		if (!event.getJurys().isEmpty()) {
			for (Jury jury : event.getJurys()) {
				Jury jurybis = new Jury();
				jurybis.setEvenement(eventbis);
				jurybis.setUtilisateur(jury.getUtilisateur());
				jurysBis.add(jurybis);
			}
		}
		return jurysBis;
	}

	private List<Candidat> duplicateCandidat(Evenement eventbis) {
		List<Candidat> candidatsBis = new ArrayList<>();
		if (!event.getCandidats().isEmpty()) {
			for (Candidat candidat : event.getCandidats()) {
				Candidat candidatbis = new Candidat();
				candidatbis.setNom(candidat.getNom());
				candidatbis.setPrenom(candidat.getPrenom());
				candidatbis.setEvenement(eventbis);
				candidatsBis.add(candidatbis);
			}
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
		eventBis.setSigningneeded(event.getSigningneeded());
		eventBis.setCode(generateCode());
		return eventBis;
	}

	private String generateCode() {
		Integer id = event.getIdevent();
		int length = (int) (Math.log10(id) + 1);
		String codeEvent = id + Password.generateCode(10 - length);
		return codeEvent;
	}

}