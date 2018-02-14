package com.onek.managedbean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Utilisateur;
import com.onek.service.EventAccueilService;
import com.onek.utils.Navigation;

@Component("attributionjc")
public class AttributionJCBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	private int idEvent;

	private List<Utilisateur> utilisateursJurys;
	private List<Candidat> candidatsJurys;

	private Map<String, String> jurys = new LinkedHashMap<>();
	private Map<String, String> candidats = new LinkedHashMap<>();
	private Map<String, Map<String, Boolean>> attribJC = new LinkedHashMap<>();
	
	private Map<String, String> attributionFinal = new LinkedHashMap<>();
	private String message="";

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			Navigation navigation = new Navigation();
			String idEventString = navigation.getURLParameter("id");
			setIdEvent(Integer.parseInt(idEventString));
			
			// Initialisation-update de la liste des candidats et jurys
			candidatsJurys = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateursJurys = eventAccueilservice.listJurysByEvent(idEvent);

			// Remplissage des maps
			for (Utilisateur utilisateur : utilisateursJurys) {
				jurys.put(utilisateur.getNom() + " " + utilisateur.getPrenom(), utilisateur.getNom() + " " + utilisateur.getPrenom());
			}
			for (Candidat candidat : candidatsJurys) {
				candidats.put(candidat.getNom() + " " + candidat.getPrenom(), candidat.getNom() + " " + candidat.getPrenom());
			}

			for (String jury : jurys.keySet()) {
				attribJC.put(jury, new LinkedHashMap<String, Boolean>());
			}
		}
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
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


	public Map<String, String> getJurys() {
		return jurys;
	}


	public void setJurys(Map<String, String> jurys) {
		this.jurys = jurys;
	}


	public Map<String, String> getCandidats() {
		return candidats;
	}


	public void setCandidats(Map<String, String> candidats) {
		this.candidats = candidats;
	}


	public Map<String, Map<String, Boolean>> getAttribJC() {
		return attribJC;
	}


	public void setAttribJC(Map<String, Map<String, Boolean>> attribJC) {
		this.attribJC = attribJC;
	}


	public void buttonActionValider() {
		for (Entry<String, Map<String, Boolean>> entry : attribJC.entrySet()) {
			String jury = entry.getKey();
			Map<String, Boolean> candidats = entry.getValue();
			
			for (Entry<String, Boolean> entry2 : candidats.entrySet()) {
			    System.out.println(jury + ": " + entry2.getKey() +" : "+entry2.getValue());
			    if(entry2.getValue() == true) {
			    	 attributionFinal.put(jury, entry2.getKey());
			    }
			}
		}
		System.out.println("------------------------TEST PRINT--------------------------");
		for (Entry<String, String> entry3 : attributionFinal.entrySet()) {
		    System.out.println("jury: " + entry3.getKey() + " ||| liste de candidats: " + entry3.getValue());
		}
		
		// FacesContext fc = FacesContext.getCurrentInstance();
		// NavigationHandler nh = fc.getApplication().getNavigationHandler();
		// nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true",
		// "eventAccueil.xhtml", "eventAccueil.xhtml".contains("?") ? "&" : "?"));
	}
}
