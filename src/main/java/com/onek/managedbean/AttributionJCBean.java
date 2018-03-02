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

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.service.CandidateService;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.service.JuryService;
import com.onek.utils.Navigation;

/**
 * ManagedBean AttributionJCBean
 */
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

	private List<Candidat> candidatsJurys;
	private boolean isopen;
	private String status = "";
	private List<Jury> juryList;
	private HashMap<Jury, List<Candidat>> associatedJurysCandidates;
	private Map<Jury, ArrayList<Candidat>> attributionFinal;

	private Map<Jury, Map<Candidat, Boolean>> attribJC;

	private List<MessageAttrib> messageAttrib;
	private String avertissementMessage;

	/**
	 * Getter de la variable isOpen
	 * @return isopen Boolean de gestion d'affichage en fonction du status de l'événement
	 */
	public boolean isIsopen() {
		return isopen;
	}

	/**
	 * Setter de la variable isOpen
	 * @param isopen Boolean de gestion d'affichage en fonction du status de l'événement
	 */
	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}

	/**
	 * Getter de la variable methode
	 * @return methode Indique le type d'attribution automatique : jury/candidat ou candidat/jury
	 */
	public int getMethode() {
		return methode;
	}

	/**
	 * Setter de la variable methode
	 * @param methode Indique le type d'attribution automatique : jury/candidat ou candidat/jury
	 */
	public void setMethode(int methode) {
		this.methode = methode;
	}

	/**
	 * Getter de la variable randomX
	 * @return randomX Le nombre choisi avec le spinner
	 */
	public int getRandomX() {
		return randomX;
	}

	/**
	 * Setter de la variable randomX
	 * @param randomX Le nombre choisi avec le spinner
	 */
	public void setRandomX(int randomX) {
		this.randomX = randomX;
	}

	/**
	 * Méthode appelée lors d'un GET sur la page attributionJuryCandidat.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * Pré-rempli la map attribJC en fonction des attributions existantes
	 * @param e ComponentSystemEvent
	 */
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
			attributionFinal = new LinkedHashMap<>();
			messageAttrib = new ArrayList<>();
			status = evenement.findById(idEvent).getStatus();
			avertissementMessage = "";

			if (!status.equals("Brouillon")) {
				isopen = false;
				avertissementMessage = "Status de l'événement: " + status
						+ ". Les suppressions d'attributions ne seront pas prises en compte ";
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
					attribJC.put(entryAssociation.getKey(), candidatesPreChecked);
				}
				displayAttrib();
			}
		}
	}

	/**
	 * Getter de la variable avertissementMessage
	 * @return avertissementMessage Message d'avertissement au cas  où l'événement est dans un statut autre que brouillon
	 */
	public String getAvertissementMessage() {
		return avertissementMessage;
	}

	/**
	 * Setter de la variable avertissementMessage
	 * @param avertissementMessage Message d'avertissement au cas  où l'événement est dans un statut autre que brouillon
	 */
	public void setAvertissementMessage(String avertissementMessage) {
		this.avertissementMessage = avertissementMessage;
	}

	/**
	 * Getter de la variable idEvent
	 * @return idEvent Id de l'événement
	 */
	public int getIdEvent() {
		return idEvent;
	}

	/**
	 * Setter de la variable idEvent
	 * @param idEvent Id de l'événement
	 */
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	/**
	 * Getter de la variable candidatsJurys
	 * @return candidatsJurys Liste des candidats
	 */
	public List<Candidat> getCandidatsJurys() {
		return candidatsJurys;
	}

	/**
	 * Setter de la variable candidatsJurys
	 * @param candidatsJurys Liste des candidats
	 */
	public void setCandidatsJurys(List<Candidat> candidatsJurys) {
		this.candidatsJurys = candidatsJurys;
	}

	/**
	 * Getter de la variable messageAttrib
	 * @return messageAttrib Liste de MessageAttrib
	 */
	public List<MessageAttrib> getMessageAttrib() {
		return messageAttrib;
	}

	/**
	 * Setter de la variable messageAttrib
	 * @param messageAttrib Liste de MessageAttrib
	 */
	public void setMessageAttrib(List<MessageAttrib> messageAttrib) {
		this.messageAttrib = messageAttrib;
	}

	/**
	 * Getter de la variable juryList
	 * @return juryList Liste des jurys
	 */
	public List<Jury> getJuryList() {
		return juryList;
	}

	/**
	 * Setter de la variable juryList
	 * @param juryList Liste des jurys
	 */
	public void setJuryList(List<Jury> juryList) {
		this.juryList = juryList;
	}

	/**
	 * Getter de la variable attribJC
	 * @return attribJC Map d'attributions des jurys-candidats. Elle est remplie avec les checkboxs
	 */
	public Map<Jury, Map<Candidat, Boolean>> getAttribJC() {
		return attribJC;
	}

	/**
	 * Setter de la variable attribJC
	 * @param attribJC Map d'attributions des jurys-candidats. Elle est remplie avec les checkboxs
	 */
	public void setAttribJC(Map<Jury, Map<Candidat, Boolean>> attribJC) {
		this.attribJC = attribJC;
	}

	/**
	 * Si une checkbox est selectionnée : sauvegarde de l'attribution. <br/>
	 * Si une checkbox est deselectionnée : suppression de l'attribution. <br/>
	 * Dans le cas d'un statut événement différent de brouillon, les suppressions ne sont pas prises en compte.
	 * @param actionEvent
	 */
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
		Navigation.redirect("attributionJuryCandidat.xhtml");
	}

	/**
	 * Navigation vers la page eventAccueil.xhtml
	 */
	public void retour() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	/**
	 * Apelle de la méthode correspondant au type d'attribution automatique
	 */
	public void attributionAutomatique() {

		if (methode == 2) {
			CandidatParJury(randomX);
		} else {
			JuryParCandidat(randomX);
		}
	}

	/**
	 * Attribution automatique jurys par candidat.
	 * @param randomX Nombre saisi par l'utilisateur avec le spinner
	 */
	private void JuryParCandidat(int randomX) {
		if (randomX > juryList.size()) {
			System.out.println("attribution impossible");
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

	/**
	 * Attribution automatique candidats par jury.
	 * @param randomX Nombre saisi par l'utilisateur avec le spinner
	 */
	public void CandidatParJury(int randomX) {
		if (randomX > candidatsJurys.size()) {
			System.out.println("attribution impossible");
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

	/**
	 * Passe a false toutes les attributions afin de vider les checkboxs
	 * @param actionEvent
	 */
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

	/**
	 * Inner class pour la datatable attribution. Les données sont des strings.
	 * La datatable est cachée et est necessaire pour réaliser l'exportation des données.
	 */
	public static class MessageAttrib {
		private String jury;
		private String candidats;

		/**
		 * @param jury Le string pour le jury
		 * @param candidats Le string pour les candidats
		 */
		public MessageAttrib(String jury, String candidats) {
			this.jury = jury;
			this.candidats = candidats;
		}

		/**
		 * Getter de la variable jury
		 * @return jury
		 */
		public String getJury() {
			return jury;
		}

		/**
		 * Setter de la variable jury
		 * @param jury
		 */
		public void setJury(String jury) {
			this.jury = jury;
		}

		/**
		 * Getter de la variable candidats
		 * @return candidats
		 */
		public String getCandidats() {
			return candidats;
		}

		/**
		 * Setter de la variable candidats
		 * @param candidats
		 */
		public void setCandidats(String candidats) {
			this.candidats = candidats;
		}
	}
}