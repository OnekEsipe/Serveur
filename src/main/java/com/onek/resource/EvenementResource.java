package com.onek.resource;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Critere;
import com.onek.model.Evenement;

/**
 * Json evenement
 */
public class EvenementResource implements Serializable {
	private static final long serialVersionUID = 1L;
	private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	@JsonProperty("Id")
	private final Integer idEvent;

	@JsonIgnore
	private final Date dateStart;

	@JsonIgnore
	private final Date dateStop;

	@JsonProperty("SigningNeeded")
	private final Boolean signingNeeded;

	@JsonProperty("Name")
	private final String nom;

	@JsonProperty("Criterias")
	private final ArrayList<CritereResource> criteres = new ArrayList<>();

	@JsonProperty("Jurys")
	private List<JuryResource> jurys;

	@JsonProperty("Evaluations")
	private List<EvaluationResource> evaluations;

	/**
	 * Données d'un evenement : <br/>
	 * Id - signingNeeded - Name - Criterias - Jurys - Evaluations - Begin - End
	 * @param evenement
	 */
	public EvenementResource(Evenement evenement) {
		idEvent = evenement.getIdevent();
		dateStart = evenement.getDatestart();
		dateStop = evenement.getDatestop();	
		signingNeeded = evenement.getSigningneeded();
		nom = evenement.getNom();
		for (Critere critere : evenement.getCriteres()) {
			criteres.add(new CritereResource(critere));
		}
	}

	public void setJurys(List<JuryResource> jurys) {
		this.jurys = jurys;
	}
	
	public void setEvaluations(List<EvaluationResource> evaluations) {
		this.evaluations = evaluations;
	}

	@JsonProperty("Begin")
	public String getDateStart() {
		return formater.format(dateStart);
	}

	@JsonProperty("End")
	public String getDateStop() {
		return formater.format(dateStop);
	}

}
