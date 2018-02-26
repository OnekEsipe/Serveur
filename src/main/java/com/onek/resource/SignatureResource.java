package com.onek.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.onek.model.Signature;

public class SignatureResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("Bitmap")
	private byte[] signatureData;
	
	/* empty constructor */
	public SignatureResource() {
		
	}
	
	@JsonIgnore
	public Signature createSignature() {
		Signature signature = new Signature();
		signature.setNom(name);
		signature.setSignature(signatureData);
		return signature;
	}	
	
}
