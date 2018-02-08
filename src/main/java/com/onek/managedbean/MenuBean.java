package com.onek.managedbean;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component("principalMenu")
public class MenuBean {
	

	public void redirect(String page) {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", page,
				page.contains("?") ? "&" : "?"));
	}
	
	public void event() {
		redirect("eventAccueil.xhtml");
	}
	
	public void user() {
		
	}
	
	public void account() {
		
	}
	
	public void help() {
		
	}
	
	public void exit() {
		redirect("index.xhtml");
	}
	
}
