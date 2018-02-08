package com.onek.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Critere;
import com.onek.model.Evenement;

public class EvenementResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("Id")  
	private final Integer idEvent;
	
	@JsonProperty("Begin")  
	private final Date dateStart;
	
	@JsonProperty("End")  
	private final Date dateStop;
	
	@JsonProperty("IsOpened")
	private final Boolean isOpened;
	
	@JsonProperty("IsSigned")
	private final Boolean isSigned;
	
	@JsonProperty("Name")
	private final String nom;	
	
	@JsonProperty("Criterias")
	private final ArrayList<CritereResource> criteres = new ArrayList<>();
	
	@JsonProperty("Jurys")
	private List<JuryResource> jurys;

	public EvenementResource(Evenement evenement) {
		idEvent = evenement.getIdevent();
		dateStart = evenement.getDatestart();
		dateStop = evenement.getDatestop();
		isOpened = evenement.getIssigned();
		isSigned = evenement.getIssigned();
		nom = evenement.getNom();
		for(Critere critere : evenement.getCriteres()) {
			criteres.add(new CritereResource(critere));
		}		
	}
	
	public void setJurys(List<JuryResource> jurys) {
		this.jurys = jurys;
	}

}
