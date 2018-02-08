package com.onek.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.stereotype.Component;

@Component("grille")
public class GrilleBean {

	private List<Critere> criteres = new ArrayList<>();
	private List<Integer> numbers = new ArrayList<>();

	@PostConstruct
	public void postInit() {
		for (int i = 0; i < 6; i++) {
			numbers.add(i);
		}
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

	class Critere {
		private final String categorie;
		private final double coefficient;
		private final String texte;

		private final List<Descripteur> descripteurs;

		Critere(String categorie, double coefficient, String texte) {
			this.categorie = categorie;
			this.coefficient = coefficient;
			this.texte = texte;
			this.descripteurs = new ArrayList<>();
		}

		public void addDescripteur(Descripteur d) {
			descripteurs.add(d);
		}
	}

	class Descripteur {
		private final char niveau;
		private final double poids;
		private final String texte;

		Descripteur(char niveau, double poids, String texte) {
			this.niveau = niveau;
			this.poids = poids;
			this.texte = texte;
		}
	}

	public void onClicAdd() {
		Critere c = new Critere(categorie, coefficient, nom);
		Descripteur d;
		if (poids1 != 0 && texte1 != null) {
			d = new Descripteur('A', poids1, texte1);
			c.addDescripteur(d);
		}
		if (poids2 != 0 && texte2 != null) {
			d = new Descripteur('B', poids2, texte2);
			c.addDescripteur(d);
		}
		if (poids3 != 0 && texte3 != null) {
			d = new Descripteur('C', poids3, texte3);
			c.addDescripteur(d);
		}
		if (poids4 != 0 && texte4 != null) {
			d = new Descripteur('D', poids4, texte4);
			c.addDescripteur(d);
		}
		if (poids5 != 0 && texte5 != null) {
			d = new Descripteur('E', poids5, texte5);
			c.addDescripteur(d);
		}
		criteres.add(c);
		System.out.println(criteres.size() + " " + c.texte + " " + c.coefficient + " "+c.descripteurs.size());
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

	// public void onClicSave() {
	// for (Critere critere : criteres) {
	// com.onek.model.Critere c = new com.onek.model.Critere();
	// c.setCategorie(critere.categorie);
	// c.setCoefficient(critere.coefficient);
	// c.setTexte(critere.texte);
	// for (Descripteur descripteur : critere.descripteurs) {
	// com.onek.model.Descripteur d = new com.onek.model.Descripteur();
	// d.setNiveau(descripteur.niveau);
	// d.setPoids(descripteur.poids);
	// d.setTexte(descripteur.texte);
	// d.setCritere(c);
	// c.addDescripteur(d);
	// }
	// }
	// FacesContext fc = FacesContext.getCurrentInstance();
	// NavigationHandler nh = fc.getApplication().getNavigationHandler();
	// nh.handleNavigation(fc, null, String.format("%s%sfaces-redirect=true",
	// "accueil.xhtml",
	// "accueil.xhtml".contains("?") ? "&" : "?"));
	// }

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
