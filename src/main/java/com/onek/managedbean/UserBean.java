package com.onek.managedbean;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.stereotype.Component;

@Component("user")
public class UserBean {
	
	private String user;
	
	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			user = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			System.out.println(user);
		}
	}
	
	

}
