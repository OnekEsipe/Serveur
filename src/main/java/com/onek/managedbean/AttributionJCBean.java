package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	// A faire : reset message arrayList dans before
	// Check le status de l'event pour ajout/suppr de l'evaluation
	// Afficher un putain de growl pour validation ou un message
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	@Autowired
	private JuryService juryservice;
	
	@Autowired
	private EvaluationService evaluationService;

	private int idEvent;

	private List<Utilisateur> utilisateursJurys;
	private List<Candidat> candidatsJurys;

	private Map<String, Utilisateur> jurys;
	private Map<String, Candidat> candidats;
	private HashMap<Jury, List<Candidat>> associatedJurysCandidates;
	private Map<String, ArrayList<String>> attributionFinal;

	private Map<String, Map<String, Boolean>> attribJC;

	private ArrayList<String> message;
	
	private Navigation navigation = new Navigation();

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));

			jurys = new LinkedHashMap<>();
			candidats = new LinkedHashMap<>();
			attribJC = new LinkedHashMap<>();
			attributionFinal = new LinkedHashMap<>();
			message = new ArrayList<>();

			// Initialisation-update de la liste des candidats, utilisateurs, jurys et de
			// l'attribution deja realisee
			candidatsJurys = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateursJurys = eventAccueilservice.listJurysByEvent(idEvent);
			List<Jury> juryList = juryservice.findJuryByIdevent(idEvent);
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
				attribJC.put(entryAssociation.getKey().getUtilisateur().getNom() + " " + entryAssociation.getKey().getUtilisateur().getPrenom(), candidatesPreChecked);
			}
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

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}

	public void previsualisationButton() {
		ArrayList<String> selectedCandidates;

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

		// Formatage de l'affichage
		for (Entry<String, ArrayList<String>> attrib : attributionFinal.entrySet()) {
			ArrayList<String> candidats = attrib.getValue();
			StringBuilder sb = new StringBuilder();
			for (String candidat : candidats) {
				sb.append(candidat).append(", ");
			}
			sb.setLength(Math.max(sb.length() - 2, 0));
			message.add("Jury " + attrib.getKey() + " : " + sb.toString());
		}
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
			ArrayList<String> candidatesEnd = attributionFinal.get(jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom());

			for (Candidat candidatBegin : candidatesBegin) {
				if (candidatesEnd.contains(candidatBegin.getNom() + " " + candidatBegin.getPrenom())) {
					System.out.println(candidatBegin.getNom() + " " + candidatBegin.getPrenom() + " trouvé dans les 2 listes -> Attibution identique avant/aprés : pas d'action a faire sur l'evaluation");
				} else if (!(candidatesEnd.contains(candidatBegin.getNom() + " " + candidatBegin.getPrenom()))) {
					System.out.println(candidatBegin.getNom() + " " + candidatBegin.getPrenom() + " pas dans candidatEnd -> suppression de l'evaluation candidatBegin");
					evaluationService.deleteEvaluation(jury.getIdjury(), candidatBegin.getIdcandidat());
				}
			}

			// Creation d'un arrayList STRING à partir de candidatesBegin pour pouvoir comparer begin et end
			ArrayList<String> candidatesStringBegin = new ArrayList<>();
			for (Candidat candidatBegin : candidatesBegin) {
				candidatesStringBegin.add(candidatBegin.getNom() + " " + candidatBegin.getPrenom());
			}
			
			for (String candidatEnd : candidatesEnd) {
				if (!(candidatesStringBegin.contains(candidatEnd))) {
					System.out.println(candidatEnd + " pas dans candidatBegin -> creation evaluation candidatEnd");
					String[] splitNameCandidatEnd = candidatEnd.split(" ");
					evaluationService.saveEvaluation(splitNameCandidatEnd[0], splitNameCandidatEnd[1], idEvent, jury, date);
				}
			}
			
			// Update des listes
			List<Jury> juryList = juryservice.findJuryByIdevent(idEvent);
			associatedJurysCandidates = juryservice.associatedJurysCandidatesByEvent(juryList, idEvent);
		}
	}
	
	public void retour() {
		navigation.redirect("eventAccueil.xhtml");
	}
}