package com.onek.resource;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Descripteur;
import com.onek.model.Note;

public class NoteResource implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@JsonProperty("Id")
	private Integer idNote;
	
	@JsonProperty("IdCriteria")
	private Integer idCriteria;
	
	@JsonProperty("Text")
	private String text;
	
	@JsonProperty("Category")
	private String category;
	
	@JsonProperty("Comment")
	private String comment;
	
	@JsonProperty("Descriptor")
	private List<DescripteurResource> descriptors = new ArrayList<>();
	
	@JsonProperty("SelectedDescriptorIndex")
	private Integer level;
	
	@JsonIgnore
	private Date date;
	
	/* empty constructor */
	public NoteResource() {
		
	}
	
	public NoteResource(Note note) {
		idNote = note.getIdnote();
		idCriteria = note.getCritere().getIdcritere();
		text = note.getCritere().getTexte();
		category = note.getCritere().getCategorie();
		comment = note.getCommentaire();
		for(Descripteur descripteur : note.getCritere().getDescripteurs()) {
			descriptors.add(new DescripteurResource(descripteur));
		}
		level = note.getNiveau();
		date = note.getDate();
	}
	
	@JsonIgnore
	public Integer getIdNote() {
		return idNote;
	}
	
	@JsonIgnore
	public Integer getIdCriteria() {
		return idCriteria;
	}
	
	@JsonProperty("Date")
	public String getDateString() {
		return formater.format(date);
	}
	
	public void setDate(String dateString) {
		try {
			date = formater.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}
	}
	
	@JsonIgnore
	public void setIdNote(Integer idNote) {
		this.idNote = idNote;
	}
	
	@JsonIgnore
	public Note createNote() {
		Note note = new Note();
		note.setNiveau(level);
		note.setCommentaire(comment);
		note.setDate(date);
		return note;
	}
}
