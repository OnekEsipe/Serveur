package com.onek.utils;

import java.util.Map;

import javax.inject.Named;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@Named(value = "navigation")
@RequestScoped
public class Navigation {

	public void redirect(String page) {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", page, page.contains("?") ? "&" : "?"));
	}

	public String getURLParameter(String name) {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String result = params.get(name);
		return "".equals(result) ? null : result;
	}
}