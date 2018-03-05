package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.service.CandidateService;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.service.JuryService;
import com.onek.utils.Navigation;

@Component("attributionjc")
public class AttributionJCBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private JuryService juryservice;

	@Autowired
	private EvaluationService evaluationService;

	@Autowired
	private EvenementService evenement;

	@Autowired
	private CandidateService candidatservice;

	private int idEvent;

	private int methode;
	private int randomX;
	private boolean saveConfirmed;

	private List<Candidat> candidatsJurys;
	private boolean isopen;
	private String status = "";
	private List<Jury> juryList;
	private HashMap<Jury, List<Candidat>> associatedJurysCandidates;
	private Map<Jury, ArrayList<Candidat>> attributionFinal;

	private Map<Jury, Map<Candidat, Boolean>> attribJC;
	
	// Gestion des checkbox : disabled si le statut événement n'est pas Brouillon
	private Map<Jury, Map<Candidat, Boolean>> attribJCDisabledCheckBox;

	private List<MessageAttrib> messageAttrib;
	private String avertissementMessage;
	private String avertMessage;

	public Map<Jury, Map<Candidat, Boolean>> getAttribJCDisabledCheckBox() {
		return attribJCDisabledCheckBox;
	}

	public void setAttribJCDisabledCheckBox(Map<Jury, Map<Candidat, Boolean>> attribJCDisabledCheckBox) {
		this.attribJCDisabledCheckBox = attribJCDisabledCheckBox;
	}

	public String getAvertMessage() {
		return avertMessage;
	}

	public void setAvertMessage(String avertMessage) {
		this.avertMessage = avertMessage;
	}

	public boolean isIsopen() {
		return isopen;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}

	public int getMethode() {
		return methode;
	}

	public void setMethode(int methode) {
		this.methode = methode;
	}

	public int getRandomX() {
		return randomX;
	}

	public void setRandomX(int randomX) {
		this.randomX = randomX;
	}

	public void before(ComponentSystemEvent e) {

		if (!FacesContext.getCurrentInstance().isPostback()) {
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("user")) {
				Navigation.redirect("index.xhtml");
				return;
			}
			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("idEvent")) {
				Navigation.redirect("accueil.xhtml");
				return;
			}
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));
			isopen = true;
			attribJC = new LinkedHashMap<>();
			attribJCDisabledCheckBox = new LinkedHashMap<>();
			attributionFinal = new LinkedHashMap<>();
			messageAttrib = new ArrayList<>();
			// Initialisation-update de la liste des candidats, utilisateurs, jurys et de
			// l'attribution deja realisee + init du message d'avertissement
			status = evenement.findById(idEvent).getStatus();
			avertissementMessage = "";

			if (!status.equals("Brouillon")) {
				isopen = false;
				avertissementMessage = "Statut de l'événement: " + status
						+ ". Les suppressions d'attributions ne seront pas prises en compte.";
			}
			candidatsJurys = candidatservice.findCandidatesByEvent(idEvent);
			juryList = juryservice.findJurysByIdevent(idEvent);
			if (!juryList.isEmpty()) {
				associatedJurysCandidates = juryservice.associatedJurysCandidatesByEvent(juryList, idEvent);

				for (Entry<Jury, List<Candidat>> entryAssociation : associatedJurysCandidates.entrySet()) {
					List<Candidat> candidatesList = entryAssociation.getValue();
					LinkedHashMap<Candidat, Boolean> candidatesPreChecked = new LinkedHashMap<>();
					for (Candidat candidatAttributed : candidatesList) {
						candidatesPreChecked.put(candidatAttributed, true);
					}
					if(isopen == false) {
						attribJCDisabledCheckBox.put(entryAssociation.getKey(), candidatesPreChecked);
					}
					attribJC.put(entryAssociation.getKey(), candidatesPreChecked);
				}
				displayAttrib();
			}

			if (saveConfirmed) {
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Confirmation", "Les modifications ont été enregistrées avec succès !"));
				saveConfirmed = false;
			}

		}
	}

	public String getAvertissementMessage() {
		return avertissementMessage;
	}

	public void setAvertissementMessage(String avertissementMessage) {
		this.avertissementMessage = avertissementMessage;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public List<Candidat> getCandidatsJurys() {
		return candidatsJurys;
	}

	public void setCandidatsJurys(List<Candidat> candidatsJurys) {
		this.candidatsJurys = candidatsJurys;
	}

	public List<MessageAttrib> getMessageAttrib() {
		return messageAttrib;
	}

	public void setMessageAttrib(List<MessageAttrib> messageAttrib) {
		this.messageAttrib = messageAttrib;
	}

	public List<Jury> getJuryList() {
		return juryList;
	}

	public void setJuryList(List<Jury> juryList) {
		this.juryList = juryList;
	}

	public Map<Jury, Map<Candidat, Boolean>> getAttribJC() {
		return attribJC;
	}

	public void setAttribJC(Map<Jury, Map<Candidat, Boolean>> attribJC) {
		this.attribJC = attribJC;
	}

	public void validationButton(ActionEvent actionEvent) {
		ArrayList<Candidat> selectedCandidates;
		Date date = new Date();

		// Remplissage map en fonction checkbox selectionnees
		for (Entry<Jury, Map<Candidat, Boolean>> entry : attribJC.entrySet()) {
			Jury jury = entry.getKey();
			Map<Candidat, Boolean> candidats = entry.getValue();
			selectedCandidates = new ArrayList<>();
			for (Entry<Candidat, Boolean> entry2 : candidats.entrySet()) {
				if (entry2.getValue() == true) {
					selectedCandidates.add(entry2.getKey());
				}
				attributionFinal.put(jury, selectedCandidates);
			}
		}

		for (Entry<Jury, List<Candidat>> entry : associatedJurysCandidates.entrySet()) {
			Jury jury = entry.getKey();
			List<Candidat> candidatesBegin = entry.getValue();
			ArrayList<Candidat> candidatesEnd = attributionFinal.get(jury);

			for (Candidat candidatBegin : candidatesBegin) {
				if (candidatesEnd.contains(candidatBegin)) {
					System.out.println(candidatBegin.getNom() + " " + candidatBegin.getPrenom()
							+ " trouvé dans les 2 listes -> Attibution identique avant/aprés : pas d'action a faire sur l'evaluation");
				} else if (!(candidatesEnd.contains(candidatBegin))) {
					if (status.equals("Brouillon")) {
						System.out.println(candidatBegin.getNom() + " " + candidatBegin.getPrenom()
								+ " pas dans candidatEnd -> suppression de l'evaluation candidatBegin");
						evaluationService.deleteEvaluation(jury.getIdjury(), candidatBegin.getIdcandidat());
					}
				}
			}

			for (Candidat candidatEnd : candidatesEnd) {
				if (!(candidatesBegin.contains(candidatEnd))) {
					System.out.println(candidatEnd.getNom() + " " + candidatEnd.getPrenom()
							+ " pas dans candidatBegin -> creation evaluation candidatEnd");
					evaluationService.saveEvaluation(candidatEnd, jury, date, idEvent);
				}
			}
		}
		saveConfirmed = true;
		Navigation.redirect("attributionJuryCandidat.xhtml");
	}

	public void retour() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	public void attributionAutomatique() {
		if (methode == 2) {
			candidatParJury(randomX);
		} else {
			juryParCandidat(randomX);
		}

	}

	private void juryParCandidat(int randomX) {
		if (randomX > juryList.size()) {
			showErrorAssignment("Le nombre de jurys affectés à cet événement est insuffisant.");
			return;
		}
		HashMap<Integer, List<Jury>> alljury = new HashMap<>();
		HashMap<Candidat, List<Jury>> attribution = new HashMap<>();
		alljury.put(1, new ArrayList<>());
		int indexJury = 0;
		int indexAttributionCandidat = 1;
		int indexCandidat = 0;
		alljury.put(0, juryList);
		Random random = new Random();
		int x;
		int sizeList = 0;

		clearAttribution();

		while (indexCandidat < candidatsJurys.size()) {
			List<Jury> juryParCandidat = new ArrayList<>();
			int i = 0;
			while (i < randomX) {

				sizeList = alljury.get(indexJury).size();

				if (sizeList <= 0) {
					indexJury++;
					indexAttributionCandidat++;
					alljury.put(indexAttributionCandidat, new ArrayList<>());
					sizeList = alljury.get(indexJury).size();
				}
				x = random.nextInt(sizeList);
				Jury jury = alljury.get(indexJury).get(x);
				if (!juryParCandidat.contains(jury)) {
					juryParCandidat.add(jury);
					alljury.get(indexJury).remove(x);
					alljury.get(indexAttributionCandidat).add(jury);
					i++;
				}

			}
			attribution.put(candidatsJurys.get(indexCandidat), juryParCandidat);
			for (Jury jury : juryParCandidat) {
				attribJC.get(jury).put(candidatsJurys.get(indexCandidat), true);
			}
			indexCandidat++;
		}
	}

	public void candidatParJury(int randomX) {
		if (randomX > candidatsJurys.size()) {
			showErrorAssignment("Le nombre de candidats affectés à cet événement est insuffisant.");
			return;
		}
		HashMap<Integer, List<Candidat>> allcandidat = new HashMap<>();
		HashMap<Jury, List<Candidat>> attribution = new HashMap<>();
		allcandidat.put(1, new ArrayList<>());
		int indexJury = 0;
		int indexAttributionCandidat = 1;
		int indexCandidat = 0;
		allcandidat.put(0, candidatsJurys);

		Random random = new Random();
		int x;
		int sizeList = 0;

		while (indexJury < juryList.size()) {

			List<Candidat> candidatparJury = new ArrayList<>();
			int i = 0;
			while (i < randomX) {
				sizeList = allcandidat.get(indexCandidat).size();

				if (sizeList <= 0) {
					indexCandidat++;
					indexAttributionCandidat++;
					allcandidat.put(indexAttributionCandidat, new ArrayList<>());
					sizeList = allcandidat.get(indexCandidat).size();
				}
				x = random.nextInt(sizeList);
				Candidat candidat = allcandidat.get(indexCandidat).get(x);
				if (!candidatparJury.contains(candidat)) {
					i++;
					candidatparJury.add(candidat);
					allcandidat.get(indexCandidat).remove(x);
					allcandidat.get(indexAttributionCandidat).add(candidat);
				}

			}
			attribution.put(juryList.get(indexJury), candidatparJury);
			indexJury++;
		}

		clearAttribution();

		for (Jury jury : attribution.keySet()) {
			for (Candidat candidat : attribution.get(jury)) {
				attribJC.get(jury).put(candidat, true);
			}
		}
	}

	public void clearButton(ActionEvent actionEvent) {
		clearAttribution();
	}

	private void clearAttribution() {
		for (Jury jury : attribJC.keySet()) {
			Map<Candidat, Boolean> candidatsCheckbox = attribJC.get(jury);
			for (Candidat ligne : candidatsCheckbox.keySet()) {
				candidatsCheckbox.replace(ligne, false);
			}
			attribJC.put(jury, candidatsCheckbox);

		}
		candidatsJurys = candidatservice.findCandidatesByEvent(idEvent);
		juryList = juryservice.findJurysByIdevent(idEvent);
	}

	private void displayAttrib() {
		for (Entry<Jury, Map<Candidat, Boolean>> entry : attribJC.entrySet()) {
			Jury jury = entry.getKey();
			Map<Candidat, Boolean> candidats = entry.getValue();
			ArrayList<Candidat> selectedCandidates = new ArrayList<>();
			for (Entry<Candidat, Boolean> entry2 : candidats.entrySet()) {
				if (entry2.getValue() == true) {
					selectedCandidates.add(entry2.getKey());
				}
				attributionFinal.put(jury, selectedCandidates);
			}
		}

		// Formatage de l'affichage
		messageAttrib = new ArrayList<>();
		for (Entry<Jury, ArrayList<Candidat>> attrib : attributionFinal.entrySet()) {
			ArrayList<Candidat> candidats = attrib.getValue();
			StringBuilder sb = new StringBuilder();
			for (Candidat candidat : candidats) {
				sb.append(candidat).append(", ");
			}
			sb.setLength(Math.max(sb.length() - 2, 0));
			;
			messageAttrib.add(new MessageAttrib(attrib.getKey().getUtilisateur().toString(), sb.toString()));
		}
		Collections.sort(messageAttrib, (o1, o2) -> o1.getJury().compareTo(o2.getJury()));
	}
	
	private void showErrorAssignment(String logErrorAssignment) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur lors de l'attribution", logErrorAssignment));
	}

	// Inner class pour la datatable
	public static class MessageAttrib {
		private String jury;
		private String candidats;

		public MessageAttrib(String jury, String candidats) {
			this.jury = jury;
			this.candidats = candidats;
		}

		public String getJury() {
			return jury;
		}

		public void setJury(String jury) {
			this.jury = jury;
		}

		public String getCandidats() {
			return candidats;
		}

		public void setCandidats(String candidats) {
			this.candidats = candidats;
		}
	}
}