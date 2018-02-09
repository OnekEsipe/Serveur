package com.onek.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.service.AccueilService;

@Component("accueil")
public class AccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private AccueilService accueilservice;

	private List<Evenement> events;
	private List<Evenement> filteredevents;
	private Evenement selectedevent;

	private String nom;
	private String status;
	private Date datestart;
	private Date datestop;

	@PostConstruct
	public void init() {
		events = accueilservice.listEvents();
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
		// to do
	}

	public void onRowSelect(SelectEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "eventAccueil.xhtml", "eventAccueil.xhtml".contains("?") ? "&" : "?"));
	}
	public void buttonAction() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", "viewCreateEvent.xhtml", "viewCreateEvent.xhtml".contains("?") ? "&" : "?"));
    }
}