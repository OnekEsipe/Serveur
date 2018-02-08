package com.onek.managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.onek.model.Candidat;

@Component("eventAccueil")
public class eventAccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String statut;
	private Date dateStart;
	private Date dateEnd;
	private Date timeStart;
	private Date timeEnd;
	private String message;
	
	private List<Candidat> candidats;
	private Candidat candidat;
	
	@PostConstruct
    public void init() {
      
       
    }
	
	public List<Candidat> getCandidats() {
		return candidats;
	}

	public void setCandidats(List<Candidat> candidats) {
		this.candidats = candidats;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public void eventUpdateButton() {
		// Pour test
		DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dfDate.format(dateStart);
		
		DateFormat dfTime = new SimpleDateFormat("HH:mm");
		String sTime = dfTime.format(timeStart);
		
		message = "Statut: " + statut + " dateStart:" + dateStart + " dateStartFORMATTE:" + sDate + " dateEnd:" + dateEnd + " timeStart:" + timeStart
				+ " timeStartFORMATTEE:" + sTime + " timeEnd:" + timeEnd;
	}
}
