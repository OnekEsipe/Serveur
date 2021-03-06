package com.onek.managedbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Note;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.service.GrilleService;
import com.onek.utils.Navigation;

/**
 * ManagedBean GrilleBean
 */
@Component("grille")
@Scope("session")
public class GrilleBean {

	@Autowired
	GrilleService grille;

	@Autowired
	EvenementService evenement;
	
	@Autowired
	private EvaluationService evaluation;

	private int idEvent;
	private Evenement event;

	private List<Critere> criteres = new ArrayList<>();
	private List<Integer> numbers = new ArrayList<>();
	private final BigDecimal ref = new BigDecimal(1);

	private int nbDescripteur;

	private String nom;
	private BigDecimal coefficient;
	private String categorie;

	private BigDecimal poids1;
	private BigDecimal poids2;
	private BigDecimal poids3;
	private BigDecimal poids4;
	private BigDecimal poids5;
	private BigDecimal poids6;
	private String texte1;
	private String texte2;
	private String texte3;
	private String texte4;
	private String texte5;
	private String texte6;
	
	private boolean modification;
	private Critere critereModif;

	/**
	 * Initilisation du nombre de niveaux possibles
	 */
	@PostConstruct
	public void postInit() {
		for (int i = 2; i < 7; i++) {
			numbers.add(i);
		}
	}

	/**
	 * Méthode appelée lors d'un GET sur la page grille.xhtml.<br/>
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
			criteres.clear();
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));
			this.event = evenement.findById(idEvent);
			for (Critere critere : event.getCriteres()) {
				criteres.add(critere);
			}
			resetValues();
			this.modification = false;
		}
		criteres.sort(Comparator.comparing(Critere::getCategorie).thenComparing(Critere::getIdcritere));
	}

	/**
	 * Ajout de critères dans la grille
	 */
	public void onClicAdd() {
		Critere c;
		if (modification) {
			c = critereModif;
			for (Descripteur descripteur : c.getDescripteurs()) {
				grille.supprimerDescripteur(descripteur);
			}
		} else {
		    c = new Critere();
		}
		c.setDescripteurs(new ArrayList<>());
		c.setEvenement(event);
		c.setCategorie(categorie);
		c.setCoefficient(coefficient);
		c.setTexte(nom);
		Descripteur d;
		d = new Descripteur();
		d.setNiveau("A");
		d.setPoids(poids1);
		d.setTexte(texte1);
		d.setCritere(c);
		c.addDescripteur(d);
		d = new Descripteur();
		d.setNiveau("B");
		d.setPoids(poids2);
		d.setTexte(texte2);
		d.setCritere(c);
		c.addDescripteur(d);
		if (nbDescripteur > 2) {
			d = new Descripteur();
			d.setNiveau("C");
			d.setPoids(poids3);
			d.setTexte(texte3);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (nbDescripteur > 3) {
			d = new Descripteur();
			d.setNiveau("D");
			d.setPoids(poids4);
			d.setTexte(texte4);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (nbDescripteur > 4) {
			d = new Descripteur();
			d.setNiveau("E");
			d.setPoids(poids5);
			d.setTexte(texte5);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (nbDescripteur > 5) {
			d = new Descripteur();
			d.setNiveau("F");
			d.setPoids(poids6);
			d.setTexte(texte6);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (modification) {
			grille.updateCritere(c);
		} else {
			grille.addCritere(c);
			for (Candidat cand : event.getCandidats()) {
				for (Evaluation eval : evaluation.findByIdCandidate(cand.getIdcandidat())) {
					boolean isPresent = false;
					for (Note not : eval.getNotes()) {
						if (not.getCritere().equals(c)) {
							isPresent = true;
						}
					}
					if (!isPresent) {
						Note note = new Note();
						note.setCommentaire("");
						note.setCritere(c);
						note.setDate(eval.getDatedernieremodif());
						note.setEvaluation(eval);
						note.setNiveau(-1);
						grille.addNote(note);
					}
				}
			}
			criteres.add(c);
		}
		Navigation.redirect("grille.xhtml");
	}

	private void resetValues() {
		categorie = "";
		coefficient = ref;
		nom = "";
		poids1 = ref;
		poids2 = ref;
		poids3 = ref;
		poids4 = ref;
		poids5 = ref;
		poids6 = ref;
		texte1 = "";
		texte2 = "";
		texte3 = "";
		texte4 = "";
		texte5 = "";
		texte6 = "";
		nbDescripteur = 2;
	}

	/**
	 * Suppression d'un critère de la grille et de la liste des critères
	 */
	public void supprimerCritere() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int id = Integer.valueOf(params.get("idcritere"));
		Critere critere = grille.getCritereById(id);
		grille.supprimerCritere(id);
		criteres.remove(critere);
	}

	/**
	 * Modifie un critère en récupérant les descripteurs actuels afin de mettre à jour le critère.
	 * Navgation vers la page addCritere.xhtml
	 */
	public void modifierCritere() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int id = Integer.valueOf(params.get("idcritere"));
		Critere critere = grille.getCritereById(id);
		nom = critere.getTexte();
		coefficient = critere.getCoefficient();
		categorie = critere.getCategorie();
		List<Descripteur> descripteurs = critere.getDescripteurs();
		nbDescripteur = descripteurs.size();
		for (Descripteur descripteur : descripteurs) {
			switch (descripteur.getNiveau()) {
			case "A":
				texte1 = descripteur.getTexte();
				poids1 = descripteur.getPoids();
				break;
			case "B":
				texte2 = descripteur.getTexte();
				poids2 = descripteur.getPoids();
				break;
			case "C":
				texte3 = descripteur.getTexte();
				poids3 = descripteur.getPoids();
				break;
			case "D":
				texte4 = descripteur.getTexte();
				poids4 = descripteur.getPoids();
				break;
			case "E":
				texte5 = descripteur.getTexte();
				poids5 = descripteur.getPoids();
				break;
			case "F":
				texte6 = descripteur.getTexte();
				poids6 = descripteur.getPoids();
				break;
			default :
				break;
			}
		}
		modification = true;
		critereModif = critere;
		Navigation.redirect("addCritere.xhtml");
	}

	/**
	 * Navgation vers la page addCritere.xhtml
	 */
	public void onClicAddCritere() {
		Navigation.redirect("addCritere.xhtml");
	}

	/**
	 * Navgation vers la page grille.xhtml
	 */
	public void onClicReturn() {
		Navigation.redirect("grille.xhtml");
	}

	/**
	 * Getter de la variable nom
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter de la variable nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter de la variable coefficient
	 * @return coefficient 
	 */
	public BigDecimal getCoefficient() {
		return coefficient;
	}

	/**
	 * Setter de la variable coefficient
	 * @param coefficient 
	 */
	public void setCoefficient(BigDecimal coeffecient) {
		this.coefficient = coeffecient;
	}

	/**
	 * Getter de la variable categorie
	 * @return categorie 
	 */
	public String getCategorie() {
		return categorie;
	}

	/**
	 * Setter de la variable categorie
	 * @param categorie 
	 */
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	/**
	 * Getter de la variable poids1
	 * @return poids1 
	 */
	public BigDecimal getPoids1() {
		return poids1;
	}

	/**
	 * Setter de la variable poids1
	 * @param poids1 
	 */
	public void setPoids1(BigDecimal poids1) {
		this.poids1 = poids1;
	}

	/**
	 * Getter de la variable poids2
	 * @return poids2
	 */
	public BigDecimal getPoids2() {
		return poids2;
	}

	/**
	 * Setter de la variable poids2
	 * @param poids2
	 */
	public void setPoids2(BigDecimal poids2) {
		this.poids2 = poids2;
	}

	/**
	 * Getter de la variable poids3
	 * @return poids3
	 */
	public BigDecimal getPoids3() {
		return poids3;
	}

	/**
	 * Setter de la variable poids3
	 * @param poids3
	 */
	public void setPoids3(BigDecimal poids3) {
		this.poids3 = poids3;
	}

	/**
	 * Getter de la variable poids4
	 * @return poids4
	 */
	public BigDecimal getPoids4() {
		return poids4;
	}

	/**
	 * Setter de la variable poids4
	 * @param poids4
	 */
	public void setPoids4(BigDecimal poids4) {
		this.poids4 = poids4;
	}

	/**
	 * Getter de la variable poids5
	 * @return poids5
	 */
	public BigDecimal getPoids5() {
		return poids5;
	}

	/**
	 * Setter de la variable poids5
	 * @param poids5
	 */
	public void setPoids5(BigDecimal poids5) {
		this.poids5 = poids5;
	}

	/**
	 * Getter de la variable poids6
	 * @return poids6
	 */
	public BigDecimal getPoids6() {
		return poids6;
	}

	/**
	 * Setter de la variable poids6
	 * @param poids6
	 */
	public void setPoids6(BigDecimal poids6) {
		this.poids6 = poids6;
	}

	/**
	 * Getter de la variable texte1
	 * @return texte1
	 */
	public String getTexte1() {
		return texte1;
	}

	/**
	 * Setter de la variable texte1
	 * @param texte1
	 */
	public void setTexte1(String texte1) {
		this.texte1 = texte1;
	}

	/**
	 * Getter de la variable texte2
	 * @return texte2
	 */
	public String getTexte2() {
		return texte2;
	}

	/**
	 * Setter de la variable texte2
	 * @param texte2
	 */
	public void setTexte2(String texte2) {
		this.texte2 = texte2;
	}

	/**
	 * Getter de la variable texte3
	 * @return texte3
	 */
	public String getTexte3() {
		return texte3;
	}

	/**
	 * Setter de la variable texte3
	 * @param texte3
	 */
	public void setTexte3(String texte3) {
		this.texte3 = texte3;
	}

	/**
	 * Getter de la variable texte4
	 * @return texte4
	 */
	public String getTexte4() {
		return texte4;
	}

	/**
	 * Setter de la variable texte4
	 * @param texte4
	 */
	public void setTexte4(String texte4) {
		this.texte4 = texte4;
	}

	/**
	 * Getter de la variable texte5
	 * @return texte5
	 */
	public String getTexte5() {
		return texte5;
	}

	/**
	 * Setter de la variable texte5
	 * @param texte5
	 */
	public void setTexte5(String texte5) {
		this.texte5 = texte5;
	}

	/**
	 * Getter de la variable texte6
	 * @return texte6
	 */
	public String getTexte6() {
		return texte6;
	}

	/**
	 * Setter de la variable texte6
	 * @param texte6
	 */
	public void setTexte6(String texte6) {
		this.texte6 = texte6;
	}

	/**
	 * Getter de la variable criteres
	 * @return criteres Liste des critères
	 */
	public List<Critere> getCriteres() {
		return criteres;
	}

	/**
	 * Getter de la variable nbDescripteur
	 * @return nbDescripteur
	 */
	public int getNbDescripteur() {
		return nbDescripteur;
	}

	/**
	 * Getter de la variable numbers
	 * @return numbers Liste
	 */
	public List<Integer> getNumbers() {
		return numbers;
	}

	/**
	 * Setter de la variable setNbDescripteur
	 * @param setNbDescripteur
	 */
	public void setNbDescripteur(int nbDescripteur) {
		this.nbDescripteur = nbDescripteur;
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
	public void setIdEvent(int id) {
		this.idEvent = id;
	}

	/**
	 * Getter de la variable modification
	 * @return modification
	 */
	public boolean isModification() {
		return modification;
	}

	/**
	 * Setter de la variable modification
	 * @param modification
	 */
	public void setModification(boolean modification) {
		this.modification = modification;
	}

}
