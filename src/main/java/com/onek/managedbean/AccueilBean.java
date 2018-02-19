package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.UserService;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Navigation;

@Component("accueil")
public class AccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EvenementService eventService;
	
	@Autowired
	private UserService userService;
	
	private final Navigation navigation = new Navigation();

	private List<Evenement> events;
	private Evenement evenement;
	private List<Evenement> filteredevents;
	private Evenement selectedevent;

	private Utilisateur user;
	private String login;
	
	private String visible = "false";

	private int idevent;
	private String typeMenu;
	
	public void before(ComponentSystemEvent e) {

		if (!FacesContext.getCurrentInstance().isPostback()) {
		 login =  (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			user = userService.findByLogin(login);
			if(user.getDroits().equals(DroitsUtilisateur.ADMINISTRATEUR.toString())) {
				typeMenu = "menu.xhtml";
				setVisible("true");
			}else {
				typeMenu = "menuorg.xhtml";
			}
			
			FacesContext context = FacesContext.getCurrentInstance();
			String viewId = context.getViewRoot().getViewId();
			 ViewHandler handler = context.getApplication().getViewHandler();
			UIViewRoot root = handler.createView(context, viewId);
			root.setViewId(viewId);
			context.setViewRoot(root);
			
		}
		
	}
	
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
		
		if(user.getDroits().equals(DroitsUtilisateur.ADMINISTRATEUR.toString())) {
			events = eventService.findAll();
		}else if(user.getDroits().equals(DroitsUtilisateur.ORGANISATEUR.toString())) {
			events = eventService.myListEvents(user.getIduser());
		}else {
			events = new ArrayList<>();
		}
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
	
	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public String getTypeMenu() {
		return typeMenu;
	}

	public void setTypeMenu(String typeMenu) {
		this.typeMenu = typeMenu;
	}
	

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public void supprimerEvent() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		idevent = Integer.valueOf(params.get("idevent"));
		eventService.supprimerEvent(idevent);
		events = eventService.findAll();
		refresh();
		
	}

	public void onRowSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idEvent", selectedevent.getIdevent());
		navigation.redirect("eventAccueil.xhtml");
	}
	
	public void buttonAction() {
		navigation.redirect("viewCreateEvent.xhtml");
    }
}
