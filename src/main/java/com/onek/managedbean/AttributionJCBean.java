package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.service.EventAccueilService;
import com.onek.service.JuryService;

@Component("attributionjc")
public class AttributionJCBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;
	
	@Autowired
	private JuryService juryservice;

	private int idEvent;

	private List<Utilisateur> utilisateursJurys;
	private List<Candidat> candidatsJurys;

	private Map<String, Utilisateur> jurys = new LinkedHashMap<>();
	private Map<String, Candidat> candidats = new LinkedHashMap<>();
	private Map<String, Map<String, Boolean>> attribJC = new LinkedHashMap<>();

	private Map<String, ArrayList<String>> attributionFinal = new LinkedHashMap<>();

	private String message = "";

	private boolean booleanFlag = true;

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));

			// Initialisation-update de la liste des candidats, des jurys et de l'attribution deja realisee

			candidatsJurys = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateursJurys = eventAccueilservice.listJurysByEvent(idEvent);

			// Remplissage des maps
			for (Utilisateur utilisateur : utilisateursJurys) {
				jurys.put(utilisateur.getNom() + " " + utilisateur.getPrenom(), utilisateur);
			}
			for (Candidat candidat : candidatsJurys) {
				candidats.put(candidat.getNom() + " " + candidat.getPrenom(), candidat);
			}
			
			/// Recuperation des attributions jury-candidat actuel de l'evenement
			List<Jury> test2 = juryservice.findJuryByIdevent(idEvent);
			for(Jury jury : test2) {
				System.out.println(jury.getUtilisateur().getNom());
			}
			
			HashMap<Jury, List<Candidat>> associatedJurysCandidates = juryservice.associatedJurysCandidatesByEvent(test2, idEvent);
			for (Entry<Jury, List<Candidat>> entry8 : associatedJurysCandidates.entrySet()) {
				List<Candidat> testt = entry8.getValue();
				System.out.print(entry8.getKey().getUtilisateur().getNom() + ": ");
				for(Candidat candidatt : testt) {
					System.out.print(candidatt.getNom() + " ");
				}
				System.out.println("");
			}
			// FIN TEST

			LinkedHashMap<String, Boolean> test = new LinkedHashMap<String, Boolean>();
			test.put("Hugo Fourcade", true);

			for (String jury : jurys.keySet()) {
				if (jury.equals("Damien Duper")) {
					attribJC.put(jury, test);
				} else {
					attribJC.put(jury, new LinkedHashMap<String, Boolean>());
				}
			}
		}
	}

	public boolean isBooleanFlag() {
		return booleanFlag;
	}

	public void setBooleanFlag(boolean booleanFlag) {
		this.booleanFlag = booleanFlag;
	}

	public Map<String, Candidat> getCandidats() {
		return candidats;
	}

	public void setCandidats(Map<String, Candidat> candidats) {
		this.candidats = candidats;
	}

	public Map<String, Utilisateur> getJurys() {
		return jurys;
	}

	public void setJurys(Map<String, Utilisateur> jurys) {
		this.jurys = jurys;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, ArrayList<String>> getAttributionFinal() {
		return attributionFinal;
	}

	public void setAttributionFinal(Map<String, ArrayList<String>> attributionFinal) {
		this.attributionFinal = attributionFinal;
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

	public Map<String, Map<String, Boolean>> getAttribJC() {
		return attribJC;
	}

	public void setAttribJC(Map<String, Map<String, Boolean>> attribJC) {
		this.attribJC = attribJC;
	}

	public void previsualitionButton() {
		ArrayList<String> selectedCandidates;

		// Recuperation de la map pour boucle
		for (Entry<String, Map<String, Boolean>> entry : attribJC.entrySet()) {
			String jury = entry.getKey();
			Map<String, Boolean> candidats = entry.getValue();
			selectedCandidates = new ArrayList<>();

			// Ajout dans attributionFinal si checkbox selectionnee
			for (Entry<String, Boolean> entry2 : candidats.entrySet()) {
				if (entry2.getValue() == true) {
					selectedCandidates.add(entry2.getKey());
				}
				attributionFinal.put(jury, selectedCandidates);
			}
		}
		System.out.println("--------------------PRINT ATTRIBUTIONS--------------------------");
		for (Entry<String, ArrayList<String>> entry3 : attributionFinal.entrySet()) {
			System.out.println("jury: " + entry3.getKey() + " ||| liste de candidats: " + entry3.getValue().toString());
			message = message + "jury: " + entry3.getKey() + " ||| liste de candidats: " + entry3.getValue().toString()
					+ "\n";
		}

		// FacesContext fc = FacesContext.getCurrentInstance();
		// NavigationHandler nh = fc.getApplication().getNavigationHandler();
		// nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true",
		// "eventAccueil.xhtml", "eventAccueil.xhtml".contains("?") ? "&" : "?"));
	}
	public void attributionAutomatique() {
		
	}
}