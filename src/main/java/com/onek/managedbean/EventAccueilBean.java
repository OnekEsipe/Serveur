package com.onek.managedbean;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Utilisateur;
import com.onek.service.AddJuryService;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.service.EventAccueilService;
import com.onek.service.UserService;
import com.onek.utils.Navigation;
import com.onek.utils.PasswordGenerator;

@Component("eventAccueil")
public class EventAccueilBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventAccueilService eventAccueilservice;

	@Autowired
	EvenementService evenement;

	@Autowired
	private UserService userService;

	@Autowired
	private AddJuryService juryServices;

	@Autowired
	EvaluationService evaluation;

	private final Navigation navigation = new Navigation();

	private int idEvent;
	private Evenement event;

	private String statut;
	private Date dateStart;
	private Date dateEnd;
	private Date timeStart;
	private Date timeEnd;
	private String message;
	private int juryAnonyme;

	private List<Candidat> filteredcandidats;
	private Candidat selectedcandidat;
	private List<Candidat> candidats;
	private Candidat candidat;

	private List<Utilisateur> utilisateurs;
	private List<Utilisateur> utilisateursAnos;

	private Utilisateur utilisateur;
	private List<Utilisateur> filteredutilisateurs;
	private Utilisateur selectedutilisateur;

	private PasswordGenerator passwordGenerator;

	public List<Utilisateur> getFilteredutilisateurs() {
		return filteredutilisateurs;
	}

	public void setFilteredutilisateurs(List<Utilisateur> filteredutilisateurs) {
		this.filteredutilisateurs = filteredutilisateurs;
	}

	public Utilisateur getSelectedutilisateur() {
		return selectedutilisateur;
	}

	public void setSelectedutilisateur(Utilisateur selectedutilisateur) {
		this.selectedutilisateur = selectedutilisateur;
	}

	public List<Candidat> getFilteredcandidats() {
		return filteredcandidats;
	}

	public void setFilteredcandidats(List<Candidat> filteredcandidats) {
		this.filteredcandidats = filteredcandidats;
	}

	public Candidat getSelectedcandidat() {
		return selectedcandidat;
	}

	public void setSelectedcandidat(Candidat selectedcandidat) {
		this.selectedcandidat = selectedcandidat;
	}

	public int getJuryAnonyme() {
		return juryAnonyme;
	}

	public void setJuryAnonyme(int juryAnonyme) {
		this.juryAnonyme = juryAnonyme;
	}

	public Evenement getEvent() {
		return event;
	}

	public void setEvent(Evenement event) {
		this.event = event;
	}

	public void before(ComponentSystemEvent e) throws ParseException {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			setIdEvent((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent"));
			this.event = evenement.findById(idEvent);
			candidats = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);

			utilisateursAnos = new ArrayList<>();
			List<Jury> jurys = juryServices.listJurysAnnonymesByEvent(idEvent);
			jurys.forEach(jury -> utilisateursAnos.add(jury.getUtilisateur()));

			this.statut = event.getStatus();
			this.dateStart = event.getDatestart();
			this.dateEnd = event.getDatestop();

			DateFormat dfTime = new SimpleDateFormat("HH:mm");
			String sTimeStart = dfTime.format(event.getDatestart().getTime());
			String sTimeEnd = dfTime.format(event.getDatestop().getTime());
			timeStart = dfTime.parse(sTimeStart);
			timeEnd = dfTime.parse(sTimeEnd);
		}
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

	public List<Candidat> getCandidats() {
		return candidats;
	}

	public void setCandidats(List<Candidat> candidats) {
		this.candidats = candidats;
	}

	public Candidat getCandidat() {
		return candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public List<Utilisateur> getUtilisateursAnos() {
		return utilisateursAnos;
	}

	public void setUtilisateursAnos(List<Utilisateur> utilisateursAnos) {
		this.utilisateursAnos = utilisateursAnos;
	}

	public void addJuryAnonymeButton() {
		passwordGenerator = new PasswordGenerator();
		List<Utilisateur> anonymousJurys = new ArrayList<>();
		Utilisateur anonymousJury;
		int increment = juryServices.findAnonymousByIdEvent(idEvent).size();
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
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);

			// On met à jour la liste des jurys anonymes
			anonymousJurys.forEach(jury -> utilisateursAnos.add(jury));
			utilisateursAnos.forEach(juryAno -> System.out.println(juryAno.getNom()));

		}
	}

	public void eventUpdateButton() {
		event.setDatestart(new Date(dateStart.getTime() + timeStart.getTime()));
		event.setDatestop(new Date(dateEnd.getTime() + timeEnd.getTime()));
		event.setStatus(statut);
		eventAccueilservice.editEvenement(event);

	}

	public void supprimerCandidat() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int idcandidat = Integer.valueOf(params.get("idcandidat"));

		eventAccueilservice.supprimerCandidat(idcandidat);
		candidats = eventAccueilservice.listCandidatsByEvent(1);

	}

	public void supprimerUtilisateur() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		int iduser = Integer.valueOf(params.get("iduser"));

		eventAccueilservice.supprimerUtilisateur(iduser);
		utilisateurs = eventAccueilservice.listJurysByEvent(1);
	}

	public void buttonGrille() {
		navigation.redirect("grille.xhtml");
	}

	public void buttonAttribution() {
		navigation.redirect("attributionJuryCandidat.xhtml");
	}

	public void buttonAddJury() {
		navigation.redirect("addJury.xhtml");
	}

	public void buttonAddCandidat() {
		navigation.redirect("addCandidates.xhtml");
	}

	public void buttonResult() {
		buildXlsx();
	}
	
	// PARTIE EXPORT DE L'EVENEMENT

	Map<String, Map<String, CellAddress>> mapAddress = new HashMap<>();
	Map<Integer, String> niveaux = new HashMap<>();
	
	private void init() {
		niveaux.put(0, "A");
		niveaux.put(1, "B");
		niveaux.put(2, "C");
		niveaux.put(3, "D");
		niveaux.put(4, "E");
		niveaux.put(5, "F");
	}

	public void buildXlsx() {
		init();
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<Critere> criteres = event.getCriteres();
		List<Candidat> candidats = event.getCandidats();
		fillParameterPage(workbook, criteres);
		fillCandidatesPages(workbook, criteres, candidats);
		fillResultsPage(workbook, criteres, candidats);
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    externalContext.setResponseContentType("application/vnd.ms-excel");
		    externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Export_"+event.getNom()+".xlsx\"");
		    workbook.write(externalContext.getResponseOutputStream());
		    facesContext.responseComplete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fillResultsPage(XSSFWorkbook workbook, List<Critere> criteres, List<Candidat> candidats) {
		int rowNum = 0;
		int colNum = 0;
		XSSFSheet sheet = workbook.createSheet("Résultats globaux");
		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(colNum++);
		cell.setCellValue("Candidats");
		for (Critere critere : criteres) {
			cell = row.createCell(colNum++);
			cell.setCellValue(critere.getTexte());
		}
		for (Candidat candidat : candidats) {
			colNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum++);
			cell.setCellValue(candidat.getNom() + " " + candidat.getPrenom());
			for (Critere critere : criteres) {
				cell = row.createCell(colNum++);
				cell.setCellValue(""); // ICI MAP.GET(CANDIDAT).GET(TOTALCRITERE)
			}
			cell = row.createCell(colNum++);
			cell.setCellValue(""); // ICI MAP.GET(CANDIDAT).GET(TOTAL)
		}
	}

	private void fillParameterPage(XSSFWorkbook workbook, List<Critere> criteres) {
		int colNum = 0;
		XSSFSheet sheet = workbook.createSheet("Parametres");
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		for (Critere critere : criteres) {
			mapAddress.put(critere.getTexte(), new HashMap<>());
			int rowNum = 0;
			Row row = sheet.createRow(rowNum);
			Cell cell = row.createCell(colNum);
			cell.setCellValue(critere.getTexte());
			System.out.println(cell.getStringCellValue() +" --> "+ cell.getAddress());
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Coefficient : ");
			System.out.println(cell.getStringCellValue() +" --> "+ cell.getAddress().formatAsString());
			colNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue(critere.getCoefficient().doubleValue());
			System.out.println(cell.getNumericCellValue() +" --> "+ cell.getAddress().formatAsString());
			mapAddress.get(critere.getTexte()).put("coef", cell.getAddress());
			colNum--;
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Descripteur");
			System.out.println(cell.getStringCellValue() +" --> "+ cell.getAddress().formatAsString());
			colNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Poids");
			System.out.println(cell.getStringCellValue() +" --> "+ cell.getAddress().formatAsString());
			for (Descripteur descripteur : critere.getDescripteurs()) {
				rowNum++;
				colNum--;
				row = sheet.createRow(rowNum);
				cell = row.createCell(colNum);
				cell.setCellValue(descripteur.getNiveau());
				System.out.println(cell.getStringCellValue() +" --> "+ cell.getAddress().formatAsString());
				colNum++;
				row = sheet.createRow(rowNum);
				cell = row.createCell(colNum);
				cell.setCellValue(descripteur.getPoids().doubleValue());
				System.out.println(cell.getNumericCellValue() +" --> "+ cell.getAddress().formatAsString());
				mapAddress.get(critere.getTexte()).put(descripteur.getNiveau(), cell.getAddress());
			}
			colNum += 2;
		}
	}

	private void fillCandidatesPages(XSSFWorkbook workbook, List<Critere> criteres, List<Candidat> candidats) {
		int nbcriteres = 0;
		int nbrows = 0;
		for (Candidat candidat : candidats) {
			int rowNum = 0;
			int colNum = 0;
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			XSSFSheet sheet = workbook.createSheet(candidat.getNom() + " " + candidat.getPrenom());
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(candidat.getNom() + " " + candidat.getPrenom());
			for (Critere critere : criteres) {
				nbcriteres++;
				cell = row.createCell(colNum++);
				cell.setCellValue("Note " + critere.getTexte());
				cell = row.createCell(colNum++);
				cell.setCellValue("Commentaire " + critere.getTexte());
			}
			for (Evaluation eval : evaluations) {
				nbrows++;
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(
						eval.getJury().getUtilisateur().getNom() + " " + eval.getJury().getUtilisateur().getPrenom());
				for (Note note : eval.getNotes()) {
					cell = row.createCell(colNum++);
					cell.setCellValue(niveaux.get(note.getNiveau()));
					cell = row.createCell(colNum++);
					cell.setCellValue(note.getCommentaire());
				}
				cell = row.createCell(colNum++);
				cell.setCellValue(""); // INSERT HERE FORMULA NOTE JURY POUR LE CANDIDAT
			}
		}
	}

}