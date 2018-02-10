package com.onek.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Descripteur;
import com.onek.model.Note;

public class NoteResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Id")
	private Integer idNote;
	
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
	
	/* empty constructor */
	public NoteResource() {
		
	}
	
	public NoteResource(Note note) {
		idNote = note.getIdnote();
		text = note.getCritere().getTexte();
		category = note.getCritere().getCategorie();
		comment = note.getCommentaire();
		for(Descripteur descripteur : note.getCritere().getDescripteurs()) {
			descriptors.add(new DescripteurResource(descripteur));
		}
		level = note.getNiveau();
	}

}
