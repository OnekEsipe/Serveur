package com.onek.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evenement;
import com.onek.service.GrilleService;

@Component("grille")
public class GrilleBean {
	
	@Autowired
	GrilleService grille;

	private List<Critere> criteres = new ArrayList<>();
	private List<Integer> numbers = new ArrayList<>();
	
	private Evenement event;

	@PostConstruct
	public void postInit() {
		for (int i = 0; i < 6; i++) {
			numbers.add(i);
		}
		event = (Evenement) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("event");
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove("event");
	}

	private int nbDescripteur;

	private String nom;
	private double coefficient;
	private String categorie;

	private double poids1;
	private double poids2;
	private double poids3;
	private double poids4;
	private double poids5;
	private String texte1;
	private String texte2;
	private String texte3;
	private String texte4;
	private String texte5;

	public void onClicAdd() {
		Critere c = new Critere();
		c.setEvenement(event);
		c.setCategorie(categorie);
		c.setCoefficient(coefficient);
		c.setTexte(nom);
		Descripteur d;
		if (poids1 != 0 && texte1 != null) {
			d = new Descripteur();
			d.setNiveau('A');
			d.setPoids(poids1);
			d.setTexte(texte1);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (poids2 != 0 && texte2 != null) {
			d = new Descripteur();
			d.setNiveau('B');
			d.setPoids(poids2);
			d.setTexte(texte2);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (poids3 != 0 && texte3 != null) {
			d = new Descripteur();
			d.setNiveau('C');
			d.setPoids(poids3);
			d.setTexte(texte3);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (poids4 != 0 && texte4 != null) {
			d = new Descripteur();
			d.setNiveau('D');
			d.setPoids(poids4);
			d.setTexte(texte4);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		if (poids5 != 0 && texte5 != null) {
			d = new Descripteur();
			d.setNiveau('E');
			d.setPoids(poids5);
			d.setTexte(texte5);
			d.setCritere(c);
			c.addDescripteur(d);
		}
		criteres.add(c);
		resetValues();
	}

	private void resetValues() {
		categorie = "";
		coefficient = 0;
		nom = "";
		poids1 = 0;
		poids2 = 0;
		poids3 = 0;
		poids4 = 0;
		poids5 = 0;
		texte1 = "";
		texte2 = "";
		texte3 = "";
		texte4 = "";
		texte5 = "";
	}

	public void onClicSave() {
		grille.addCriteres(criteres);
		
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

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coeffecient) {
		this.coefficient = coeffecient;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public double getPoids1() {
		return poids1;
	}

	public void setPoids1(double poids1) {
		this.poids1 = poids1;
	}

	public double getPoids2() {
		return poids2;
	}

	public void setPoids2(double poids2) {
		this.poids2 = poids2;
	}

	public double getPoids3() {
		return poids3;
	}

	public void setPoids3(double poids3) {
		this.poids3 = poids3;
	}

	public double getPoids4() {
		return poids4;
	}

	public void setPoids4(double poids4) {
		this.poids4 = poids4;
	}

	public double getPoids5() {
		return poids5;
	}

	public void setPoids5(double poids5) {
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

	public int getNbDescripteur() {
		return nbDescripteur;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNbDescripteur(int nbDescripteur) {
		this.nbDescripteur = nbDescripteur;
	}

}
