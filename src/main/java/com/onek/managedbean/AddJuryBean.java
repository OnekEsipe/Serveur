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

/**
 * ManagedBean AddJuryBean
 */
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

	private Password passwordGenerator;
	private int juryAnonyme;

	/**
	 * Getter de la variable utilisateursAnos
	 * @return utilisateursAnos Liste des utilisateurs anonymes
	 */
	public List<Utilisateur> getUtilisateursAnos() {
		return utilisateursAnos;
	}

	/**
	 * Setter de la variable utilisateursAnos
	 * @param utilisateursAnos Liste des utilisateurs anonymes
	 */
	public void setUtilisateursAnos(List<Utilisateur> utilisateursAnos) {
		this.utilisateursAnos = utilisateursAnos;
	}

	/**
	 * Getter de la variable juryAnonyme
	 * @return juryAnonyme 
	 */
	public int getJuryAnonyme() {
		return juryAnonyme;
	}

	/**
	 * Setter de la variable juryAnonyme
	 * @param juryAnonyme
	 */
	public void setJuryAnonyme(int juryAnonyme) {
		this.juryAnonyme = juryAnonyme;
	}

	/**
	 * Getter de la variable passwordGenerator
	 * @return passwordGenerator Le mot de passe généré pour les jurys anonymes
	 */
	public Password getPasswordGenerator() {
		return passwordGenerator;
	}

	/**
	 * Setter de la variable passwordGenerator
	 * @param passwordGenerator Le mot de passe généré pour les jurys anonymes
	 */
	public void setPasswordGenerator(Password passwordGenerator) {
		this.passwordGenerator = passwordGenerator;
	}

	/**
	 * Getter de la variable utilisateurs
	 * @return utilisateurs Liste des utilisateurs
	 */
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	/**
	 * Setter de la variable utilisateurs
	 * @param utilisateurs Liste des utilisateurs
	 */
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	/**
	 * Getter de la variable utilisateur
	 * @return utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * Getter de la variable utilisateur
	 * @param utilisateur
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * Getter de la variable filteredutilisateurs
	 * @return filteredutilisateurs Liste des utilisateurs filtrés
	 */
	public List<Utilisateur> getFilteredutilisateurs() {
		return filteredutilisateurs;
	}

	/**
	 * Setter de la variable filteredutilisateurs
	 * @param filteredutilisateurs Liste des utilisateurs filtrés
	 */
	public void setFilteredutilisateurs(List<Utilisateur> filteredutilisateurs) {
		this.filteredutilisateurs = filteredutilisateurs;
	}

	/**
	 * Getter de la variable selectedutilisateurs
	 * @return selectedutilisateurs La liste des utilisateurs selectionnés
	 */
	public List<Utilisateur> getSelectedutilisateurs() {
		return selectedutilisateurs;
	}

	/**
	 * Setter de la variable selectedutilisateurs
	 * @param selectedutilisateurs Liste des utilisateurs selectionnés
	 */
	public void setSelectedutilisateurs(List<Utilisateur> selectedutilisateurs) {
		this.selectedutilisateurs = selectedutilisateurs;
	}

	/**
	 * Getter de la variable utilisateursAll
	 * @return utilisateursAll Liste de tous les utilisateurs
	 */
	public List<Utilisateur> getUtilisateursAll() {
		return utilisateursAll;
	}

	/**
	 * Setter de la variable utilisateursAll
	 * @param utilisateursAll Liste de tous les utilisateurs
	 */
	public void setUtilisateursAll(List<Utilisateur> utilisateursAll) {
		this.utilisateursAll = utilisateursAll;
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
	 * @param id Id de l'événement
	 */
	public void setIdEvent(int id) {
		this.idEvent = id;
	}

	/**
	 * Méthode appelée lors d'un GET sur la page addJury.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
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
			this.event = evenement.findById(idEvent);
			utilisateurs = juryService.listJurysByEvent(idEvent);
			utilisateursAll = userService.getAllUsersExceptDeleted();
			for (Utilisateur utilisateur : utilisateurs) {
				utilisateursAll.remove(utilisateur);
			}
			utilisateursAnos = new ArrayList<>();
			List<Jury> jurys = juryService.listJurysAnnonymesByEvent(idEvent);
			jurys.forEach(jury -> utilisateursAnos.add(jury.getUtilisateur()));

		}
	}

	/**
	 * Un jury anonyme est supprimé de la base de donnée et de la liste. 
	 * Un jury non anonyme est supprimé de la base de données, supprimé de la liste des jurys et replacé dans la liste des utilisateurs disponibles pour l'événement.
	 */
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

	/**
	 * Ajoute un jury à l'événement. Le jury correspond à un utilisateur.
	 */
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

	/**
	 * Navigation vers eventAccueil.xhtml
	 */
	public void buttonActionValider() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	/**
	 * Navigation vers eventAccueil.xhtml
	 */
	public void retour() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	/**
	 * Génére des jurys anonymes avec un formatage du nom en fonction de l'id événement et génére un mot de passe.
	 */
	public void addJuryAnonymeButton() {
		passwordGenerator = new Password();
		List<Utilisateur> anonymousJurys = new ArrayList<>();
		Utilisateur anonymousJury;
		int increment = juryService.findAnonymousByIdEvent(idEvent).size();
		if (juryAnonyme > 0) {
			for (int i = 0 + increment; i < juryAnonyme + increment; i++) {
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
				anonymousJury.setMotdepasse(passwordGenerator.generatePassword(8));
				anonymousJury.setMail("");
				anonymousJury.setPrenom("");
				anonymousJurys.add(anonymousJury);
			}
			userService.addJurysAnonymes(anonymousJurys, event);
			utilisateurs = juryService.listJurysByEvent(idEvent);

			anonymousJurys.forEach(jury -> utilisateursAnos.add(jury));
			utilisateursAnos.forEach(juryAno -> System.out.println(juryAno.getNom()));

		}
	}
}
