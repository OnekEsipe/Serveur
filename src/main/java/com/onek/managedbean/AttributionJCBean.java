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
import com.onek.model.Utilisateur;
import com.onek.service.EvaluationService;
import com.onek.service.EventAccueilService;
import com.onek.service.JuryService;
import com.onek.utils.Navigation;

@Component("attributionjc")
public class AttributionJCBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	@Autowired
	private JuryService juryservice;

	@Autowired
	private EvaluationService evaluationService;

	private int idEvent;

	private int methode;
	private int randomX;

	private List<Utilisateur> utilisateursJurys;
	private List<Candidat> candidatsJurys;

	private Map<String, Utilisateur> jurys;
	private Map<String, Candidat> candidats;
	private List<Jury> juryList;
	private HashMap<Jury, List<Candidat>> associatedJurysCandidates;
	private Map<String, ArrayList<String>> attributionFinal;

	private Map<String, Map<String, Boolean>> attribJC;

	private List<MessageAttrib> messageAttrib;
	private Navigation navigation = new Navigation();

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

			if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("idEvent")) {
				Navigation navigation = new Navigation();
				navigation.redirect("accueil.xhtml");
				return;
			}
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));

			jurys = new LinkedHashMap<>();
			candidats = new LinkedHashMap<>();
			attribJC = new LinkedHashMap<>();
			attributionFinal = new LinkedHashMap<>();
			messageAttrib = new ArrayList<>();

			// Initialisation-update de la liste des candidats, utilisateurs, jurys et de
			// l'attribution deja realisee
			candidatsJurys = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateursJurys = eventAccueilservice.listJurysByEvent(idEvent);
			juryList = juryservice.findJurysByIdevent(idEvent);
			associatedJurysCandidates = juryservice.associatedJurysCandidatesByEvent(juryList, idEvent);

			// Remplissage des maps pour l'affichage des noms des jurys/candidats dans le
			// jsf
			for (Utilisateur utilisateur : utilisateursJurys) {
				jurys.put(utilisateur.getNom() + " " + utilisateur.getPrenom(), utilisateur);
			}
			for (Candidat candidat : candidatsJurys) {
				candidats.put(candidat.getNom() + " " + candidat.getPrenom(), candidat);
			}

			System.out.println("------------Print associations déja existantes--------------------");
			for (Entry<Jury, List<Candidat>> entry8 : associatedJurysCandidates.entrySet()) {
				List<Candidat> testt = entry8.getValue();
				System.out.print(entry8.getKey().getUtilisateur().getNom() + ": ");
				for (Candidat candidatt : testt) {
					System.out.print(candidatt.getNom() + " ");
				}
				System.out.println("");
			}

			for (Entry<Jury, List<Candidat>> entryAssociation : associatedJurysCandidates.entrySet()) {
				List<Candidat> candidatesList = entryAssociation.getValue();
				LinkedHashMap<String, Boolean> candidatesPreChecked = new LinkedHashMap<String, Boolean>();
				for (Candidat candidatAttributed : candidatesList) {
					candidatesPreChecked.put(candidatAttributed.getNom() + " " + candidatAttributed.getPrenom(), true);
				}
				attribJC.put(entryAssociation.getKey().getUtilisateur().getNom() + " "
						+ entryAssociation.getKey().getUtilisateur().getPrenom(), candidatesPreChecked);
			}
			
			
			// --------- DEBUT Affichage des attributions dans la dataTable----------
			for (Entry<String, Map<String, Boolean>> entry : attribJC.entrySet()) {
				String jury = entry.getKey();
				Map<String, Boolean> candidats = entry.getValue();
				ArrayList<String> selectedCandidates = new ArrayList<>();
				for (Entry<String, Boolean> entry2 : candidats.entrySet()) {
					if (entry2.getValue() == true) {
						selectedCandidates.add(entry2.getKey());
					}
					attributionFinal.put(jury, selectedCandidates);
				}
			}

			// Formatage de l'affichage
			messageAttrib = new ArrayList<>();
			for (Entry<String, ArrayList<String>> attrib : attributionFinal.entrySet()) {
				ArrayList<String> candidats = attrib.getValue();
				StringBuilder sb = new StringBuilder();
				for (String candidat : candidats) {
					sb.append(candidat).append(", ");
				}
				sb.setLength(Math.max(sb.length() - 2, 0));;
				messageAttrib.add(new MessageAttrib(attrib.getKey(), sb.toString()));
			}
			Collections.sort(messageAttrib, (o1, o2) -> o1.getJury().compareTo(o2.getJury()));
			// --------- FIN Affichage des attributions dans la dataTable----------
		}
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public List<Utilisateur> getUtilisateursJurys() {
		return utilisateursJurys;
	}

	public void setUtilisateursJurys(List<Utilisateur> utilisateursJurys) {
		this.utilisateursJurys = utilisateursJurys;
	}

	public List<Candidat> getCandidatsJurys() {
		return candidatsJurys;
	}

	public void setCandidatsJurys(List<Candidat> candidatsJurys) {
		this.candidatsJurys = candidatsJurys;
	}

	public Map<String, Utilisateur> getJurys() {
		return jurys;
	}

	public void setJurys(Map<String, Utilisateur> jurys) {
		this.jurys = jurys;
	}

	public Map<String, Candidat> getCandidats() {
		return candidats;
	}

	public void setCandidats(Map<String, Candidat> candidats) {
		this.candidats = candidats;
	}

	public Map<String, Map<String, Boolean>> getAttribJC() {
		return attribJC;
	}

	public void setAttribJC(Map<String, Map<String, Boolean>> attribJC) {
		this.attribJC = attribJC;
	}

	public Map<String, ArrayList<String>> getAttributionFinal() {
		return attributionFinal;
	}

	public void setAttributionFinal(Map<String, ArrayList<String>> attributionFinal) {
		this.attributionFinal = attributionFinal;
	}

	public List<MessageAttrib> getMessageAttrib() {
		return messageAttrib;
	}

	public void setMessageAttrib(List<MessageAttrib> messageAttrib) {
		this.messageAttrib = messageAttrib;
	}

	public void previsualisationButton() {

		// Remplissage map en fonction checkbox selectionnees
		for (Entry<String, Map<String, Boolean>> entry : attribJC.entrySet()) {
			String jury = entry.getKey();
			Map<String, Boolean> candidats = entry.getValue();
			ArrayList<String> selectedCandidates = new ArrayList<>();
			for (Entry<String, Boolean> entry2 : candidats.entrySet()) {
				if (entry2.getValue() == true) {
					selectedCandidates.add(entry2.getKey());
				}
				attributionFinal.put(jury, selectedCandidates);
			}
		}

		// Formatage de l'affichage
		messageAttrib = new ArrayList<>();
		for (Entry<String, ArrayList<String>> attrib : attributionFinal.entrySet()) {
			ArrayList<String> candidats = attrib.getValue();
			StringBuilder sb = new StringBuilder();
			for (String candidat : candidats) {
				sb.append(candidat).append(", ");
			}
			sb.setLength(Math.max(sb.length() - 2, 0));;
			messageAttrib.add(new MessageAttrib(attrib.getKey(), sb.toString()));
		}
		Collections.sort(messageAttrib, (o1, o2) -> o1.getJury().compareTo(o2.getJury()));
	}

	public void validationButton(ActionEvent actionEvent) {
		ArrayList<String> selectedCandidates;
		Date date = new Date();

		// Remplissage map en fonction checkbox selectionnees
		for (Entry<String, Map<String, Boolean>> entry : attribJC.entrySet()) {
			String jury = entry.getKey();
			Map<String, Boolean> candidats = entry.getValue();
			selectedCandidates = new ArrayList<>();
			for (Entry<String, Boolean> entry2 : candidats.entrySet()) {
				if (entry2.getValue() == true) {
					selectedCandidates.add(entry2.getKey());
				}
				attributionFinal.put(jury, selectedCandidates);
			}
		}

		for (Entry<Jury, List<Candidat>> entry : associatedJurysCandidates.entrySet()) {
			Jury jury = entry.getKey();
			List<Candidat> candidatesBegin = entry.getValue();
			ArrayList<String> candidatesEnd = attributionFinal
					.get(jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom());

			for (Candidat candidatBegin : candidatesBegin) {
				if (candidatesEnd.contains(candidatBegin.getNom() + " " + candidatBegin.getPrenom())) {
					System.out.println(candidatBegin.getNom() + " " + candidatBegin.getPrenom()
							+ " trouvé dans les 2 listes -> Attibution identique avant/aprés : pas d'action a faire sur l'evaluation");
				} else if (!(candidatesEnd.contains(candidatBegin.getNom() + " " + candidatBegin.getPrenom()))) {
					System.out.println(candidatBegin.getNom() + " " + candidatBegin.getPrenom()
							+ " pas dans candidatEnd -> suppression de l'evaluation candidatBegin");
					evaluationService.deleteEvaluation(jury.getIdjury(), candidatBegin.getIdcandidat());
				}
			}

			// Creation d'un arrayList STRING à partir de candidatesBegin pour pouvoir
			// comparer begin et end
			ArrayList<String> candidatesStringBegin = new ArrayList<>();
			for (Candidat candidatBegin : candidatesBegin) {
				candidatesStringBegin.add(candidatBegin.getNom() + " " + candidatBegin.getPrenom());
			}

			for (String candidatEnd : candidatesEnd) {
				if (!(candidatesStringBegin.contains(candidatEnd))) {
					System.out.println(candidatEnd + " pas dans candidatBegin -> creation evaluation candidatEnd");
					String[] splitNameCandidatEnd = candidatEnd.split(" ");
					evaluationService.saveEvaluation(splitNameCandidatEnd[0], splitNameCandidatEnd[1], idEvent, jury,
							date);
				}
			}

			// Update des listes
			juryList = juryservice.findJurysByIdevent(idEvent);
			associatedJurysCandidates = juryservice.associatedJurysCandidatesByEvent(juryList, idEvent);
		}
	}

	public void retour() {
		navigation.redirect("eventAccueil.xhtml");
	}

	public void attributionAutomatique() {

		if (methode == 2) {
			CandidatParJury(randomX);
		} else {
			JuryParCandidat(randomX);
		}
	}

	private void JuryParCandidat(int randomX) {
		Date date = new Date();
		if (randomX > jurys.size()) {
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
			indexCandidat++;
		}

		for (Candidat candidat : attribution.keySet()) {
			// System.out.println("candidat: "+c.getNom()+" "+c.getPrenom());
			for (Jury jury : attribution.get(candidat)) {
				// System.out.print(jury.getUtilisateur().getNom()+"
				// "+jury.getUtilisateur().getPrenom() + " || ");
				evaluationService.saveEvaluation(candidat, jury, date, idEvent);
			}
			// System.out.println();
		}
		navigation.redirect("attributionJuryCandidat.xhtml");
	}

	public void CandidatParJury(int randomX) {
		Date date = new Date();
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

		while (indexJury < jurys.size()) {

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
		for (Jury jury : attribution.keySet()) {
			// System.out.println("jury: "+j.getUtilisateur().getNom()+"
			// "+j.getUtilisateur().getPrenom());
			for (Candidat candidat : attribution.get(jury)) {
				// System.out.print(candidat.getNom() +" "+candidat.getPrenom()+ " || ");
				evaluationService.saveEvaluation(candidat, jury, date, idEvent);
			}
			// System.out.println();
		}
		navigation.redirect("attributionJuryCandidat.xhtml");
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