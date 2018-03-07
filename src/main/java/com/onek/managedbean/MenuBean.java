package com.onek.managedbean;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.onek.utils.Navigation;

/**
 * ManagedBean MenuBean
 */
@Component("principalMenu")
@Scope("session")
public class MenuBean {
	
	/**
	 * Gestion de l'affichage du menu
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				Navigation.redirect("index.xhtml");
				return;
			}
		}
	}

}
