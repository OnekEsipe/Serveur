package com.onek.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the signatures database table.
 * 
 */
@Entity
@Table(name="signatures")
@NamedQuery(name="Signature.findAll", query="SELECT s FROM Signature s")
public class Signature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idsignature;

	private String non;

	private byte[] signature;

	//bi-directional many-to-one association to Evaluation
	@ManyToOne
	@JoinColumn(name="idevaluation")
	private Evaluation evaluation;

	public Signature() {
	}

	public Integer getIdsignature() {
		return this.idsignature;
	}

	public void setIdsignature(Integer idsignature) {
		this.idsignature = idsignature;
	}

	public String getNon() {
		return this.non;
	}

	public void setNon(String non) {
		this.non = non;
	}

	public byte[] getSignature() {
		return this.signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public Evaluation getEvaluation() {
		return this.evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

}