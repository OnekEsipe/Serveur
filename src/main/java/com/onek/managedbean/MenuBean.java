package com.onek.managedbean;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.stereotype.Component;

import com.onek.utils.Navigation;

/**
 * ManagedBean MenuBean
 */
@Component("principalMenu")
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
