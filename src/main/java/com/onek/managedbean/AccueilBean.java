package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.UserService;
import com.onek.utils.DroitsUtilisateur;
import com.onek.utils.Navigation;

/**
 * ManagedBean AccueilBean
 */
@Component("accueil")
@Scope("session")
public class AccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EvenementService eventService;

	@Autowired
	private UserService userService;

	private List<Evenement> events;
	private Evenement evenement;
	private String evenementChoisi;
	private List<Evenement> filteredevents;
	private Evenement selectedevent;

	private Utilisateur user;
	private String login;

	private boolean visible;

	private int idevent;
	private String typeMenu;

	/**
	 * Méthode appelée lors d'un GET sur la page accueil.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				Navigation.redirect("index.xhtml");
				return;
			}
			if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("idEvent")) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idEvent");
			}
			login = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			user = userService.findByLogin(login);
			if (user.getDroits().equals(DroitsUtilisateur.ADMINISTRATEUR.toString())) {
				typeMenu = "menu.xhtml";
				setVisible(true);
			} else {
				typeMenu = "menuorg.xhtml";
				setVisible(false);
			}
			FacesContext context = FacesContext.getCurrentInstance();
			String viewId = context.getViewRoot().getViewId();
			ViewHandler handler = context.getApplication().getViewHandler();
			UIViewRoot root = handler.createView(context, viewId);
			root.setViewId(viewId);
			context.setViewRoot(root);
			refresh();
		}

	}

	/**
	 * Getter de la variable idevent.
	 * @return idevent L'id de l'événement
	 */
	public int getIdevent() {
		return idevent;
	}

	/**
	 * Setter de la variable idevent.
	 * @param idevent L'id de l'événement
	 */
	public void setIdevent(int idevent) {
		this.idevent = idevent;
	}

	/**
	 * Getter de la variable evenement
	 * @return evenement
	 */
	public Evenement getEvent() {
		return evenement;
	}

	/**
	 * Setter de la variable evenement
	 * @param event Evenement
	 */
	public void setEvent(Evenement event) {
		this.evenement = event;
	}

	/**
	 * Rafraichissement de l'affichage
	 */
	public void refresh() {
		if (user.getDroits().equals(DroitsUtilisateur.ADMINISTRATEUR.toString())) {
			events = eventService.findAll();
		} else if (user.getDroits().equals(DroitsUtilisateur.ORGANISATEUR.toString())) {
			events = eventService.myListEvents(user.getIduser());
		} else {
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

	/**
	 * Getter de la variable events
	 * @return events La liste des événements
	 */
	public List<Evenement> getEvents() {
		return events;
	}

	/**
	 * Setter de la variable events
	 * @param events La liste des événements
	 */
	public void setEvents(List<Evenement> events) {
		this.events = events;
	}

	/**
	 * Getter de la variable filterevents
	 * @return filterevents Liste des événements filtrés
	 */
	public List<Evenement> getFilteredevents() {
		return filteredevents;
	}

	/**
	 * Setter de la variable filterevents
	 * @param filteredevents Liste des événements filtrés
	 */
	public void setFilteredevents(List<Evenement> filteredevents) {
		this.filteredevents = filteredevents;
	}

	/**
	 * Getter de la variable selectedevent
	 * @return selectedevent Evenement selectionné
	 */
	public Evenement getSelectedevent() {
		return selectedevent;
	}

	/**
	 * Setter de la variable selectedevent
	 * @param selectedevent Evenement selectionné
	 */
	public void setSelectedevent(Evenement selectedevent) {
		this.selectedevent = selectedevent;
	}

	/**
	 * Getter de la variable user
	 * @return user L'utilisateur
	 */
	public Utilisateur getUser() {
		return user;
	}

	/**
	 * Setter de la variable user
	 * @param user L'utilisateur
	 */
	public void setUser(Utilisateur user) {
		this.user = user;
	}

	/**
	 * Getter de la variable typeMenu
	 * @return typeMenu Type du menu (organisateur ou administrateur)
	 */
	public String getTypeMenu() {
		return typeMenu;
	}

	/**
	 * Setter de la variable typeMenu
	 * @param typeMenu Type du menu (organisateur ou administrateur)
	 */
	public void setTypeMenu(String typeMenu) {
		this.typeMenu = typeMenu;
	}

	/**
	 * Getter de la variable visible
	 * @return visible
	 */
	public boolean getVisible() {
		return visible;
	}

	/**
	 * Setter de la variable visible
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Getter de la variable evenementChoisi
	 * @return evenementChoisi
	 */
	public String getEvenementChoisi() {
		return evenementChoisi;
	}

	/**
	 * Setter de la variable evenementChoisi
	 * @param evenementChoisi
	 */
	public void setEvenementChoisi(String evenementChoisi) {
		this.evenementChoisi = evenementChoisi;
	}

	/**
	 * Navigation vers d'autres pages xhtml en fonction de la lignes cliquées.
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idEvent",
				selectedevent.getIdevent());
		setEvenementChoisi(selectedevent.getNom());
		Navigation.redirect("eventManager.xhtml");
	}

	/**
	 * Navigation vers viewCreateEvent.xhtml
	 */
	public void buttonAction() {
		Navigation.redirect("viewCreateEvent.xhtml");
	}
	
}
