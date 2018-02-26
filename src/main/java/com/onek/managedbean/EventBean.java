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

	public String getLogInfo() {
		return logInfo;
	}

	public String getDebug() {
		return debug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Date getHour1() {
		return hour1;
	}

	public void setHour1(Date hour1) {
		this.hour1 = hour1;
	}

	public Date getHour2() {
		return hour2;
	}

	public void setHour2(Date hour2) {
		this.hour2 = hour2;
	}

	public boolean getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	public boolean getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(boolean issigned) {
		this.isSigned = issigned;
	}	

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
		Password pass = new Password();
		Integer id = event.getIdevent();
		int length = (int) (Math.log10(id) + 1);
		String codeEvent = id+pass.generateCode(10-length);
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
	public void debug() {
		debug = "Données de l'évènement : \nNom : "+name+"\nDate de début : "+date1+"\nDate de fin : "+
				date2+"Heure de debut : "+hour1+"\n"+"Heure de fin : "+hour2;
	}


}
