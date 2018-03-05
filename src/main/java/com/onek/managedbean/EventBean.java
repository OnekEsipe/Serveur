package com.onek.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

/**
 * ManagedBean EventBean
 */
@Component("event")
public class EventBean implements Serializable {
	@Autowired
	UserService userService;
	
	private static final long serialVersionUID = 1L;	
	private static final int ecartHour = 3_600_000; //en milliseconde

	@Autowired
	private EvenementService evenementService;
	
	private String name;
	private Date date1;					//format => dd-MM-yyy
	private Date date2;					//format => dd-MM-yyy
	private Date hour1;					//format => HH:mm
	private Date hour2;					//format => HH:mm
	private boolean isOpened = false;   //Default value
	private boolean isSigned = false;	//Default value
	private String status="Brouillon";	//Default state
	private String logInfo;
	private String debug;

	private Evenement event;

	/**
	 * Méthode appelée lors d'un GET sur la page viewCreateEvent.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
			Navigation.redirect("index.xhtml");
			return;
		}
		emptyForm();
	}

	private void emptyForm() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 1);
		setHour1(cal.getTime());
		setName("");
		setDate1(new Date());
		setDate2(new Date());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59);
		setHour2(cal.getTime());
		setIsOpened(false);
		setIsSigned(false);
	}

	/**
	 * Ajoute un événement 
	 */
	public void addEvent() {
	FacesContext fc = FacesContext.getCurrentInstance();
		String login = (String) fc.getExternalContext().getSessionMap().get("user");
		Utilisateur utilisateur = userService.findByLogin(login);
		event = new Evenement();
		event.setNom(name);
		event.setDatestart(new Date(date1.getTime()+hour1.getTime() + ecartHour));
		event.setDatestop(new Date(date2.getTime()+hour2.getTime() + ecartHour));
		event.setIsopened(isOpened);
		event.setSigningneeded(isSigned);
		event.setStatus(status);
		event.setUtilisateur(utilisateur);
		event.setIsdeleted(false);
		evenementService.addEvenement(event);
	}

	/**
	 * Getter de la variable logInfo
	 * @return logInfo Message d'information
	 */
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * Getter de la variable debug
	 * @return debug Message de debug
	 */
	public String getDebug() {
		return debug;
	}

	/**
	 * Getter de la variable name
	 * @return name Nom de l'événement
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter de la variable name
	 * @param name Nom de l'événement
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter de la variable date1
	 * @return date1 Date de début de l'événement
	 */
	public Date getDate1() {
		return date1;
	}

	/**
	 * Setter de la variable date1
	 * @param date1 Date de début de l'événement
	 */
	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	/**
	 * Getter de la variable date2
	 * @return date2 Date de fin de l'événement
	 */
	public Date getDate2() {
		return date2;
	}

	/**
	 * Setter de la variable date2
	 * @param date2 Date de fin de l'événement
	 */
	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	/**
	 * Getter de la variable hour1
	 * @return hour1 Heure de début de l'événement
	 */
	public Date getHour1() {
		return hour1;
	}

	/**
	 * Setter de la variable hour1
	 * @param hour1 Heure de début de l'événement
	 */
	public void setHour1(Date hour1) {
		this.hour1 = hour1;
	}

	/**
	 * Getter de la variable hour2
	 * @return hour2 Heure de fin de l'événement
	 */
	public Date getHour2() {
		return hour2;
	}

	/**
	 * Setter de la variable hour2
	 * @param hour2 Heure de fin de l'événement
	 */
	public void setHour2(Date hour2) {
		this.hour2 = hour2;
	}

	/**
	 * Getter de la variable isOpened
	 * @return isOpened Boolean lié à la checkbox "Ouvert à l'inscription"
	 */
	public boolean getIsOpened() {
		return isOpened;
	}

	/**
	 * Setter de la variable isOpened
	 * @param isOpened Boolean lié à la checkbox "Ouvert à l'inscription"
	 */
	public void setIsOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	/**
	 * Getter de la variable isSigned
	 * @return isSigned Boolean lié à la checkbox "Signature"
	 */
	public boolean getIsSigned() {
		return isSigned;
	}

	/**
	 * Setter de la variable isSigned
	 * @param isSigned Boolean lié à la checkbox "Signature"
	 */
	public void setIsSigned(boolean issigned) {
		this.isSigned = issigned;
	}	

	/**
	 * Vérifie les données et appelle les méthodes pour l'enregistrement d'un événement
	 */
	public void click() {
		if(name.isEmpty() || !validDate(date1, date2) || !validHour(hour1, hour2) ) {
			logInfo = "Formulaire invalide, merci de vérifier vos saisies "+name.isEmpty()+" "+validDate(date1, date2)+" "+validHour(hour1, hour2)+
					" "+date1.getTime()+" "+date2.getTime();
			return;
		}
		addEvent();
		addEvenementCode();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idEvent", event.getIdevent());
		Navigation.redirect("accueil.xhtml");
	}

	private void addEvenementCode() {
		Integer id = event.getIdevent();
		int length = (int) (Math.log10(id) + 1);
		String codeEvent = Password.generateCode(10-length)+id;
		event.setCode(codeEvent);
		evenementService.editEvenement(event);
	}

	private boolean validDate(Date d1, Date d2) {
		return d2.getTime() >= d1.getTime();
	}

	private boolean validHour(Date hour1, Date hour2) {
		if(date1.getTime() == date2.getTime()) {
			return hour2.getTime() > hour1.getTime();
		}
		return true;
	}
	
	/**
	 * Message de debug
	 */
	public void debug() {
		debug = "Données de l'évènement : \nNom : "+name+"\nDate de début : "+date1+"\nDate de fin : "+
				date2+"Heure de debut : "+hour1+"\n"+"Heure de fin : "+hour2;
	}


}
