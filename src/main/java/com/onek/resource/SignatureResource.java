package com.onek.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Signature;

public class SignatureResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("IdSignature")
	private Integer idSignature;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("IdEvaluation")
	private Integer idEvaluation;
	
	@JsonProperty("Signature")
	private byte[] signatureData;
	
	/* empty constructor */
	public SignatureResource() {
		
	}
	
	public SignatureResource(Signature signature) {
		this.idSignature = signature.getIdsignature();
		this.name = signature.getNon();
		this.idEvaluation = signature.getEvaluation().getIdevaluation();
		this.signatureData = signature.getSignature();
	}
	
	@JsonIgnore
	public Integer getIdEvaluation() {
		return idEvaluation;
	}
	
	@JsonIgnore
	public Signature createSignature() {
		Signature signature = new Signature();
		signature.setIdsignature(idSignature);
		signature.setNon(name);
		signature.setSignature(signatureData);
		return signature;
	}	
	
}
