package com.onek.managedbean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evenement;
import com.onek.service.EvenementService;
import com.onek.service.GrilleService;
import com.onek.utils.Navigation;

@Component("grille")
public class GrilleBean {

	@Autowired
	GrilleService grille;

	@Autowired
	EvenementService evenement;

	private int idEvent;
	private Evenement event;

	private List<Critere> criteres = new ArrayList<>();
	private List<Critere> newCriteres = new ArrayList<>();
	private List<Integer> numbers = new ArrayList<>();
	private final BigDecimal ref = new BigDecimal(1);

	@PostConstruct
	public void postInit() {
		for (int i = 0; i < 6; i++) {
			numbers.add(i);
		}
	}

	public void before(ComponentSystemEvent e) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			criteres.clear();
			newCriteres.clear();
			Navigation navigation = new Navigation();
			String idEventString = navigation.getURLParameter("id");
			setIdEvent(Integer.parseInt(idEventString));
			this.event = evenement.findById(idEvent);
			for (Critere critere : event.getCriteres()) {
				criteres.add(critere);
			}
		}
	}

	private int nbDescripteur;

	private String nom;
	private BigDecimal coefficient;
	private String categorie;

	private BigDecimal poids1;
	private BigDecimal poids2;
	private BigDecimal poids3;
	private BigDecimal poids4;
	private BigDecimal poids5;
	private String texte1;
	private String texte2;
	private String texte3;
	private String texte4;
	private String texte5;

	public void onClicAdd() {
		Critere c = new Critere();
		c.setDescripteurs(new ArrayList<>());
		c.setEvenement(event);
		c.setCategorie(categorie);
		c.setCoefficient(coefficient);
		c.setTexte(nom);
		Descripteur d;
		if (texte1 != null && !texte1.isEmpty() && poids1 != null && poids1.compareTo(ref) > 0) {
			d = new Descripteur();
			d.setNiveau("A");
			d.setPoids(poids1);
			d.setTexte(texte1);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (texte2 != null && !texte2.isEmpty() && poids2 != null && poids2.compareTo(ref) > 0) {
			d = new Descripteur();
			d.setNiveau("B");
			d.setPoids(poids2);
			d.setTexte(texte2);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (texte3 != null && !texte3.isEmpty() && poids3 != null && poids3.compareTo(ref) > 0) {
			d = new Descripteur();
			d.setNiveau("C");
			d.setPoids(poids3);
			d.setTexte(texte3);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (texte4 != null && !texte4.isEmpty() && poids4 != null && poids4.compareTo(ref) > 0) {
			d = new Descripteur();
			d.setNiveau("D");
			d.setPoids(poids4);
			d.setTexte(texte4);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (texte5 != null && !texte5.isEmpty() && poids5 != null && poids5.compareTo(ref) > 0) {
			d = new Descripteur();
			d.setNiveau("E");
			d.setPoids(poids5);
			d.setTexte(texte5);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		criteres.add(c);
		newCriteres.add(c);
		resetValues();
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
		texte1 = "";
		texte2 = "";
		texte3 = "";
		texte4 = "";
		texte5 = "";
	}

	public void onClicSave() {
		grille.addCriteres(newCriteres);
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove("event");
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nh = fc.getApplication().getNavigationHandler();
		nh.handleNavigation(fc, null,
				String.format("%s%sfaces-redirect=true", "accueil.xhtml", "accueil.xhtml".contains("?") ? "&" : "?"));
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coeffecient) {
		this.coefficient = coeffecient;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public BigDecimal getPoids1() {
		return poids1;
	}

	public void setPoids1(BigDecimal poids1) {
		this.poids1 = poids1;
	}

	public BigDecimal getPoids2() {
		return poids2;
	}

	public void setPoids2(BigDecimal poids2) {
		this.poids2 = poids2;
	}

	public BigDecimal getPoids3() {
		return poids3;
	}

	public void setPoids3(BigDecimal poids3) {
		this.poids3 = poids3;
	}

	public BigDecimal getPoids4() {
		return poids4;
	}

	public void setPoids4(BigDecimal poids4) {
		this.poids4 = poids4;
	}

	public BigDecimal getPoids5() {
		return poids5;
	}

	public void setPoids5(BigDecimal poids5) {
		this.poids5 = poids5;
	}

	public String getTexte1() {
		return texte1;
	}

	public void setTexte1(String texte1) {
		this.texte1 = texte1;
	}

	public String getTexte2() {
		return texte2;
	}

	public void setTexte2(String texte2) {
		this.texte2 = texte2;
	}

	public String getTexte3() {
		return texte3;
	}

	public void setTexte3(String texte3) {
		this.texte3 = texte3;
	}

	public String getTexte4() {
		return texte4;
	}

	public void setTexte4(String texte4) {
		this.texte4 = texte4;
	}

	public String getTexte5() {
		return texte5;
	}

	public void setTexte5(String texte5) {
		this.texte5 = texte5;
	}

	public List<Critere> getCriteres() {
		return criteres;
	}

	public List<Critere> getNewCriteres() {
		return newCriteres;
	}

	public int getNbDescripteur() {
		return nbDescripteur;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNbDescripteur(int nbDescripteur) {
		this.nbDescripteur = nbDescripteur;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int id) {
		this.idEvent = id;
	}

}
