package com.onek.managedbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.utils.Navigation;

@Component("principalMenu")
public class MenuBean {
	
	@Autowired
	Navigation navigation;
	
	public void event() {
		navigation.redirect("accueil.xhtml");
	}
	
	public void user() {
		
	}
	
	public void account() {
		
	}
	
	public void help() {
		
	}
	
	public void exit() {
		navigation.redirect("index.xhtml");
	}
	
}
