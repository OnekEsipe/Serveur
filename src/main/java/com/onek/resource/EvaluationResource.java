package com.onek.resource;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.onek.model.Evaluation;
import com.onek.model.Note;

public class EvaluationResource implements Serializable {
	private static final long serialVersionUID = 1L;
	private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@JsonProperty("Id")
	private Integer idEvaluation;
	
	@JsonProperty("IdJury")
	private Integer idJury;
	
	@JsonProperty("IdEvent")
	private Integer idEvent;
	
	@JsonProperty("IdCandidate")
	private Integer idCandidate;
	
	@JsonProperty("Comment")
	private String comment;
	
	@JsonIgnore
	private Date lastUpdatedDate;
	
	@JsonProperty("Criterias")
	private final List<NoteResource> notes = new ArrayList<>();
	
	/* empty constructor */
	public EvaluationResource() {
		
	}
	
	public EvaluationResource(Evaluation evaluation) {
		idEvaluation = evaluation.getIdevaluation();
		idJury = evaluation.getJury().getUtilisateur().getIduser();
		idEvent = evaluation.getJury().getEvenement().getIdevent();
		idCandidate = evaluation.getCandidat().getIdcandidat();
		comment = evaluation.getCommentaire();	
		lastUpdatedDate = evaluation.getDatedernieremodif();
		for(Note note : evaluation.getNotes()) {
			notes.add(new NoteResource(note));
		}
	}
	
	@JsonIgnore
	public Integer getIdEvaluation() {
		return idEvaluation;
	}
	
	@JsonSetter("LastUpdatedDate")
	public void setDateLastChange(String date) {
		try {
			lastUpdatedDate = formater.parse(date);
		} catch (ParseException e) {
			lastUpdatedDate = new Date();
		}
	}
	
	@JsonGetter("LastUpdatedDate")
	public String getDateLastChangeString() {
		if (lastUpdatedDate == null) {
			return null;
		}
		return formater.format(lastUpdatedDate);
	}
	
	@JsonIgnore
	public Date getDateLastChange() {
		return lastUpdatedDate;
	}
	
	@JsonIgnore
	public List<NoteResource> getNotes() {
		return notes;
	}
	
	@JsonIgnore
	public String getComment() {
		return comment;
	}
	
	@JsonIgnore
	public Integer getIdEvent() {
		return idEvent;
	}
	
}
