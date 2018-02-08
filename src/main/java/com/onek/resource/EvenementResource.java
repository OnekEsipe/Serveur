package com.onek.resource;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Critere;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;

public class EvenementResource implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

	@JsonProperty("Id")
	private final Integer idEvent;

	@JsonIgnore
	private final Date dateStart;

	@JsonIgnore
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

	// TODO complete this when json format evaluation will be defined
	@JsonProperty("Evaluations")
	private final List<Evaluation> evaluations = new ArrayList<>();

	public EvenementResource(Evenement evenement) {
		idEvent = evenement.getIdevent();
		dateStart = evenement.getDatestart();
		dateStop = evenement.getDatestop();
		isOpened = evenement.getIssigned();
		isSigned = evenement.getIssigned();
		nom = evenement.getNom();
		for (Critere critere : evenement.getCriteres()) {
			criteres.add(new CritereResource(critere));
		}
	}

	public void setJurys(List<JuryResource> jurys) {
		this.jurys = jurys;
	}

	@JsonProperty("Begin")
	public String getDateStart() {
		return dateFormat.format(dateStart);
	}

	@JsonProperty("End")
	public String getDateStop() {
		return dateFormat.format(dateStop);
	}

}
