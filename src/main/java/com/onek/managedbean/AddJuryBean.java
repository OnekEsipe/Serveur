package com.onek.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Utilisateur;
import com.onek.service.EvenementService;
import com.onek.service.JuryService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;
import com.onek.utils.Password;

@Component("addjury")
public class AddJuryBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private JuryService juryService;

	@Autowired
	private EvenementService evenement;

	@Autowired
	private UserService userService;

	private int idEvent;
	private Evenement event;

	private Jury newjuryEvent;

	private List<Utilisateur> utilisateurs;
	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private List<Utilisateur> selectedutilisateurs;
	private List<Utilisateur> utilisateursAll;
	private List<Utilisateur> utilisateursAnos;

	private int juryAnonyme;
	
	private String exportName;

	public List<Utilisateur> getUtilisateursAnos() {
		return utilisateursAnos;
	}

	public void setUtilisateursAnos(List<Utilisateur> utilisateursAnos) {
		this.utilisateursAnos = utilisateursAnos;
	}

	public int getJuryAnonyme() {
		return juryAnonyme;
	}

	public void setJuryAnonyme(int juryAnonyme) {
		this.juryAnonyme = juryAnonyme;
	}

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Utilisateur> getFilteredutilisateurs() {
		return filteredutilisateurs;
	}

	public void setFilteredutilisateurs(List<Utilisateur> filteredutilisateurs) {
		this.filteredutilisateurs = filteredutilisateurs;
	}

	public List<Utilisateur> getSelectedutilisateurs() {
		return selectedutilisateurs;
	}

	public void setSelectedutilisateurs(List<Utilisateur> selectedutilisateurs) {
		this.selectedutilisateurs = selectedutilisateurs;
	}

	public List<Utilisateur> getUtilisateursAll() {
		return utilisateursAll;
	}

	public void setUtilisateursAll(List<Utilisateur> utilisateursAll) {
		this.utilisateursAll = utilisateursAll;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int id) {
		this.idEvent = id;
	}

	public String getExportName() {
		return exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
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
			this.event = evenement.findById(idEvent);
			utilisateurs = juryService.listJurysByEvent(idEvent);
			utilisateursAll = userService.getAllUsersExceptDeleted();
			for (Utilisateur utilisateur : utilisateurs) {
				utilisateursAll.remove(utilisateur);
			}
			utilisateursAnos = new ArrayList<>();
			List<Jury> jurys = juryService.listJurysAnnonymesByEvent(idEvent);
			jurys.forEach(jury -> utilisateursAnos.add(jury.getUtilisateur()));
			exportName = "liste_jurys_anonymes_"+event.getNom();

		}
	}

	public void supprimerUtilisateur() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int iduser = Integer.valueOf(params.get("iduser"));
		Utilisateur jury = juryService.findById(iduser);
		juryService.supprimerUtilisateur(iduser, idEvent);
		utilisateurs.remove(jury);
		if(jury.getDroits().equals("A")) {
			utilisateursAnos.remove(jury);
			juryService.supprimerUtilisateurAnonyme(iduser);
		}
		else {
			utilisateursAll.add(jury);
		}
	}

	public void buttonAdd() {

		for (Utilisateur utilisateur : selectedutilisateurs) {
			newjuryEvent = new Jury();
			newjuryEvent.setEvenement(event);
			newjuryEvent.setUtilisateur(utilisateur);
			juryService.addJuryToEvent(newjuryEvent);
			utilisateurs.add(utilisateur);
			utilisateursAll.remove(utilisateur);
		}
		selectedutilisateurs.clear();
	}

	public void buttonActionValider() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	public void retour() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	public void addJuryAnonymeButton() {
		List<Utilisateur> anonymousJurys = new ArrayList<>();
		Utilisateur anonymousJury;
		List<Jury> jurys = juryService.listJurysAnnonymesByEvent(idEvent);
		int increment = jurys.size();
		if (juryAnonyme > 0) {
			for (int i = 0; i < juryAnonyme + increment; i++) {
				anonymousJury = new Utilisateur();
				anonymousJury.setDroits("A");
				anonymousJury.setIsdeleted(false);
				if (i < 10) {
					anonymousJury.setLogin("Jury00" + i + "_" + idEvent);
					anonymousJury.setNom("Jury00" + i + "_" + idEvent);
				}
				if ((i >= 10) && (i < 100)) {
					anonymousJury.setLogin("Jury0" + i + "_" + idEvent);
					anonymousJury.setNom("Jury0" + i + "_" + idEvent);
				}
				if ((i >= 100) && (i < 1000)) {
					anonymousJury.setLogin("Jury" + i + "_" + idEvent);
					anonymousJury.setNom("Jury" + i + "_" + idEvent);
				}
				
				// check if login is not used
				boolean loginIsUsed = false;
				for(Jury jury : jurys) {
					if (jury.getUtilisateur().getLogin().equals(anonymousJury.getLogin())) {
						loginIsUsed = true;
						break;
					}
				}
				if (loginIsUsed) {
					continue;
				}
				
				anonymousJury.setMotdepasse(Password.generatePassword(8));
				anonymousJury.setMail("");
				anonymousJury.setPrenom("");
				anonymousJurys.add(anonymousJury);
			}
			userService.addJurysAnonymes(anonymousJurys, event);
			utilisateurs = juryService.listJurysByEvent(idEvent);

			// On met à jour la liste des jurys anonymes
			anonymousJurys.forEach(jury -> utilisateursAnos.add(jury));
			utilisateursAnos.forEach(juryAno -> System.out.println(juryAno.getNom()));
		}
	}
}
