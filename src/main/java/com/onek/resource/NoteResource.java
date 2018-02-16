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
import com.onek.model.Descripteur;
import com.onek.model.Note;

public class NoteResource implements Serializable {
	private static final long serialVersionUID = 1L;
	private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@JsonProperty("Id")
	private Integer idCriteria;
	
	@JsonProperty("Text")
	private String text;
	
	@JsonProperty("Category")
	private String category;
	
	@JsonProperty("Comment")
	private String comment;
	
	@JsonProperty("Descriptor")
	private List<DescripteurResource> descriptors = new ArrayList<>();
	
	@JsonIgnore
	private DescripteurResource selectedDescriptor;
	
	@JsonIgnore
	private Integer level;
	
	@JsonIgnore
	private Date date;
	
	/* empty constructor */
	public NoteResource() {
		
	}
	
	public NoteResource(Note note) {
		idCriteria = note.getCritere().getIdcritere();
		text = note.getCritere().getTexte();
		category = note.getCritere().getCategorie();
		comment = note.getCommentaire();
		level = note.getNiveau();
		date = note.getDate();
		for(Descripteur descripteur : note.getCritere().getDescripteurs()) {
			DescripteurResource descripteurResource = new DescripteurResource(descripteur);
			descriptors.add(descripteurResource);
			if (level != -1 && descripteur.getNiveau().equals(String.valueOf((char) (level + 65)))) {
				selectedDescriptor = descripteurResource;
			}
		}				
	}
	
	@JsonIgnore
	public Integer getIdCriteria() {
		return idCriteria;
	}
	
	@JsonGetter("LastModification")
	public String getDateString() {
		return formater.format(date);
	}
	
	@JsonSetter("LastModification")
	public void setDate(String dateString) {
		try {
			date = formater.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}
	}
	
	@JsonIgnore
	public Note createNote() {
		Note note = new Note();
		note.setNiveau(level);
		note.setCommentaire(comment);
		note.setDate(date);
		return note;
	}
	
	@JsonSetter("SelectedLevel")
	public void setSelectedLevel(String selectedLevel) {
		if (selectedLevel.isEmpty()) {
			level = -1;
			return;
		}
		char levelChar = selectedLevel.charAt(0);
		level = levelChar % 65;
	}
	
	@JsonGetter("SelectedLevel")
	public String getSelectedLevel() {
		if (level == -1) {
			return "";
		}
		char levelChar = (char) (level + 65);
		return String.valueOf(levelChar);
	}
	
	@JsonGetter("SelectedDescriptor")
	public DescripteurResource getSelectedDescriptor() {
		return selectedDescriptor;
	}
}
