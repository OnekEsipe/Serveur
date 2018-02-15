package com.onek.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.service.AccueilService;
import com.onek.utils.Navigation;

@Component("accueil")
public class AccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private AccueilService accueilservice;

	private final Navigation navigation = new Navigation();

	private List<Evenement> events;
	private Evenement evenement;
	private List<Evenement> filteredevents;
	private Evenement selectedevent;

	private String nom;
	private String status;
	private Date datestart;
	private Date datestop;
	private int idevent;

	public int getIdevent() {
		return idevent;
	}

	public void setIdevent(int idevent) {
		this.idevent = idevent;
	}

	public Evenement getEvent() {
		return evenement;
	}

	public void setEvent(Evenement event) {
		this.evenement = event;
	}

	public void refresh() {
		events = accueilservice.listEvents();
		for (Evenement evenement : events) {
			if (evenement.getStatus().equals("Ouvert") && (evenement.getDatestart().compareTo(new Date()) < 0)) {
				evenement.setStatus("Démarré");
			}
			if (evenement.getStatus().equals("Démarré") && (evenement.getDatestop().compareTo(new Date()) < 0)) {
				evenement.setStatus("Stoppé");
			}
		}
	}

	public List<Evenement> getEvents() {
		return events;
	}

	public void setEvents(List<Evenement> events) {
		this.events = events;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDatestart() {
		return datestart;
	}

	public void setDatestart(Date datestart) {
		this.datestart = datestart;
	}

	public Date getDatestop() {
		return datestop;
	}

	public void setDatestop(Date datestop) {
		this.datestop = datestop;
	}

	public List<Evenement> getFilteredevents() {
		return filteredevents;
	}

	public void setFilteredevents(List<Evenement> filteredevents) {
		this.filteredevents = filteredevents;
	}

	public Evenement getSelectedevent() {
		return selectedevent;
	}

	public void setSelectedevent(Evenement selectedevent) {
		this.selectedevent = selectedevent;
	}

	public void supprimerEvent() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		idevent = Integer.valueOf(params.get("idevent"));
		System.out.println(idevent);
		accueilservice.supprimerEvent(idevent);
		events = accueilservice.listEvents();

	}

	public void onRowSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idEvent",
				selectedevent.getIdevent());
		navigation.redirect("eventAccueil.xhtml");
	}

	public void buttonAction() {
		navigation.redirect("viewCreateEvent.xhtml");
	}
}
