package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.service.EventAccueilService;

@Component("attribJury")
public class attributionJury implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	
    private DualListModel<String> cities;

	@PostConstruct
	public void init() {
		List<String> citiesSource = new ArrayList<String>();
        List<String> citiesTarget = new ArrayList<String>();
         
        citiesSource.add("San Francisco");
        citiesSource.add("London");
        citiesSource.add("Paris");
        citiesSource.add("Istanbul");
        citiesSource.add("Berlin");
        citiesSource.add("Barcelona");
        citiesSource.add("Rome");
        
        citiesTarget.add("test");
        citiesTarget.add("test2");
        citiesTarget.add("test3");
         
        cities = new DualListModel<String>(citiesSource, citiesTarget);
         
	}

	public DualListModel<String> getCities() {
		return cities;
	}

	public void setCities(DualListModel<String> cities) {
		this.cities = cities;
	}
}
