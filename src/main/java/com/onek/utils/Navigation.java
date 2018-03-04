package com.onek.utils;

import java.util.Map;
import java.util.Objects;

import javax.inject.Named;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@Named(value = "navigation")
@RequestScoped
public class Navigation {

	public static void redirect(String page) {
		Objects.requireNonNull(page);
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true", page, page.contains("?") ? "&" : "?"));
	}

	public static String getURLParameter(String name) {
		Objects.requireNonNull(name);
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String result = params.get(name);
		return "".equals(result) ? null : result;
	}	

}