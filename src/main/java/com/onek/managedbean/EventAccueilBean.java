package com.onek.managedbean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	private String visibleB = "false";
	private String visibleO = "false";
	private String visibleF = "false";

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

	public String getVisibleF() {
		return visibleF;
	}

	public void setVisibleF(String visibleF) {
		this.visibleF = visibleF;
	}

	public String getVisibleB() {
		return visibleB;
	}

	public void setVisibleB(String visibleB) {
		this.visibleB = visibleB;
	}

	public String getVisibleO() {
		return visibleO;
	}

	public void setVisibleO(String visibleO) {
		this.visibleO = visibleO;
	}

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

			Integer idevent = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEvent");
		
			if(idevent == null) {
				Navigation navigation = new Navigation();
				navigation.redirect("accueil.xhtml");
				return;
			}
			setIdEvent(idevent);
			this.event = evenement.findById(idEvent);
			candidats = eventAccueilservice.listCandidatsByEvent(idEvent);
			utilisateurs = eventAccueilservice.listJurysByEvent(idEvent);

			utilisateursAnos = new ArrayList<>();
			List<Jury> jurys = juryServices.listJurysAnnonymesByEvent(idEvent);
			jurys.forEach(jury -> utilisateursAnos.add(jury.getUtilisateur()));

			this.statut = event.getStatus();
			if (statut.equals("Brouillon")) {

				setVisibleB("true");
				setVisibleO("false");
				setVisibleF("false");
			} else if (statut.equals("Ouvert")) {
				setVisibleB("false");
				setVisibleO("true");
				setVisibleF("false");
			} else {
				setVisibleB("false");
				setVisibleO("false");
				setVisibleF("true");

			}
			System.out.println();
			this.dateStart = event.getDatestart();
			this.dateEnd = event.getDatestop();

			DateFormat dfTime = new SimpleDateFormat("HH:mm");
			String sTimeStart = dfTime.format(event.getDatestart().getTime());
			String sTimeEnd = dfTime.format(event.getDatestop().getTime());
			timeStart = dfTime.parse(sTimeStart);
			timeEnd = dfTime.parse(sTimeEnd);
			FacesContext context = FacesContext.getCurrentInstance();
			String viewId = context.getViewRoot().getViewId();
			ViewHandler handler = context.getApplication().getViewHandler();
			UIViewRoot root = handler.createView(context, viewId);
			root.setViewId(viewId);
			context.setViewRoot(root);
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
		if (event.getStatus().equals("Brouillon")) {
			event.setStatus(statut);
		} else {
			if (event.getStatus().equals("Ouvert") && statut.equals("Fermé")) {
				event.setStatus(statut);
			}
		}
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
	private final Map<String, Map<String, CellAddress>> mapParam = new HashMap<>();
	private final Map<Integer, String> niveaux = new HashMap<>();
	private final Map<Candidat, Map<String, CellAddress>> resultCandidats = new HashMap<>();
	
	private XSSFCellStyle style;
	private XSSFCellStyle style2;
	
	private void init(XSSFWorkbook workbook) {
		niveaux.put(0, "A");
		niveaux.put(1, "B");
		niveaux.put(2, "C");
		niveaux.put(3, "D");
		niveaux.put(4, "E");
		niveaux.put(5, "F");
		niveaux.put(-1, "-");
		style = workbook.createCellStyle();
		style2 = workbook.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
	}

	public void buildXlsx() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		init(workbook);
		List<Critere> criteres = event.getCriteres();
		List<Candidat> candidats = event.getCandidats();
		fillParameterPage(workbook, criteres);
		fillCandidatesPages(workbook, criteres, candidats);
		fillResultsPage(workbook, criteres, candidats);
		autoSizeColumns(workbook);
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.setResponseContentType("application/vnd.ms-excel");
			externalContext.setResponseHeader("Content-Disposition",
					"attachment; filename=\"Export_" + event.getNom() + ".xlsx\"");
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
			cell.setCellStyle(style2);
		}
		cell = row.createCell(colNum++);
		cell.setCellValue("Total");
		cell.setCellStyle(style2);
		for (Candidat candidat : candidats) {
			colNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum++);
			cell.setCellValue(candidat.getNom() + " " + candidat.getPrenom());
			cell.setCellStyle(style2);
			for (Critere critere : criteres) {
				cell = row.createCell(colNum++);
				cell.setCellFormula("'"+candidat.getNom()+" "+candidat.getPrenom()+"'!"+resultCandidats.get(candidat).get(critere.getTexte()).formatAsString()); // ICI MAP.GET(CANDIDAT).GET(TOTALCRITERE)
				cell.setCellStyle(style);
			}
			cell = row.createCell(colNum++);
			if(resultCandidats.get(candidat).containsKey("total")) {
				cell.setCellFormula("'"+candidat.getNom()+" "+candidat.getPrenom()+"'!"+resultCandidats.get(candidat).get("total").formatAsString());
				cell.setCellStyle(style);
			}
		}
	}

	private void fillParameterPage(XSSFWorkbook workbook, List<Critere> criteres) {
		int colNum = 0;
		int rowNum = 0;
		XSSFSheet sheet = workbook.createSheet("Parametres");
		for (Critere critere : criteres) {
			mapParam.put(critere.getTexte(), new HashMap<>());
			Row row = sheet.createRow(rowNum);
			Cell cell = row.createCell(colNum);
			cell.setCellValue(critere.getTexte());
			cell.setCellStyle(style2);
			System.out.println(cell.getStringCellValue() + " --> " + cell.getAddress());
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Coefficient : ");
			cell.setCellStyle(style);
			System.out.println(cell.getStringCellValue() + " --> " + cell.getAddress().formatAsString());
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellValue(critere.getCoefficient().doubleValue());
			cell.setCellStyle(style);
			System.out.println(cell.getNumericCellValue() + " --> " + cell.getAddress().formatAsString());
			mapParam.get(critere.getTexte()).put("coef", cell.getAddress());
			colNum--;
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Descripteur");
			cell.setCellStyle(style2);
			System.out.println(cell.getStringCellValue() + " --> " + cell.getAddress().formatAsString());
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellValue("Poids");
			cell.setCellStyle(style2);
			System.out.println(cell.getStringCellValue() + " --> " + cell.getAddress().formatAsString());
			for (Descripteur descripteur : critere.getDescripteurs()) {
				rowNum++;
				colNum--;
				row = sheet.createRow(rowNum);
				cell = row.createCell(colNum);
				cell.setCellValue(descripteur.getNiveau());
				cell.setCellStyle(style);
				System.out.println(cell.getStringCellValue() + " --> " + cell.getAddress().formatAsString());
				colNum++;
				cell = row.createCell(colNum);
				cell.setCellValue(descripteur.getPoids().doubleValue());
				cell.setCellStyle(style);
				System.out.println(cell.getNumericCellValue() + " --> " + cell.getAddress().formatAsString());
				mapParam.get(critere.getTexte()).put(descripteur.getNiveau(), cell.getAddress());
			}
			rowNum += 2;
			colNum = 0;
		}
	}

	private void fillCandidatesPages(XSSFWorkbook workbook, List<Critere> criteres, List<Candidat> candidats) {
		for (Candidat candidat : candidats) {
			resultCandidats.put(candidat, new HashMap<>());
			int rowNum = 0;
			int colNum = 0;
			Map<String, List<String>> mapResultsByCritere = new HashMap<>();
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			XSSFSheet sheet = workbook.createSheet(candidat.getNom() + " " + candidat.getPrenom());
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum++);
			cell.setCellValue("Jurys");
			cell.setCellStyle(style2);
			for (Critere critere : criteres) {
				cell = row.createCell(colNum++);
				cell.setCellValue("Note " + critere.getTexte());
				cell.setCellStyle(style2);
				cell = row.createCell(colNum++);
				cell.setCellValue("Commentaire " + critere.getTexte());
				cell.setCellStyle(style2);
			}
			cell = row.createCell(colNum++);
			cell.setCellValue("Total");
			cell.setCellStyle(style2);
			for (Evaluation eval : evaluations) {
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(
						eval.getJury().getUtilisateur().getNom() + " " + eval.getJury().getUtilisateur().getPrenom());
				StringBuilder sb = new StringBuilder();
				cell.setCellStyle(style2);
				sb.append("SUM(");
				for (Note note : eval.getNotes()) {
					if (!mapResultsByCritere.containsKey(note.getCritere().getTexte())) {
						mapResultsByCritere.put(note.getCritere().getTexte(), new ArrayList<>());
					}
					mapResultsByCritere.get(note.getCritere().getTexte()).add(niveaux.get(note.getNiveau()));
					cell = row.createCell(colNum++);
					cell.setCellValue(niveaux.get(note.getNiveau()));
					cell.setCellStyle(style2);
					sb.append("Parametres!" + mapParam.get(note.getCritere().getTexte()).get("coef").formatAsString()
							+ "*Parametres!" + mapParam.get(note.getCritere().getTexte())
									.get(niveaux.get(note.getNiveau())).formatAsString());
					cell = row.createCell(colNum++);
					cell.setCellValue(note.getCommentaire());
					cell.setCellStyle(style);
					sb.append("+");
				}
				if (eval.getNotes().isEmpty()) {
					sb.setLength(0);
				} else {
					sb.setLength(sb.length() - 1);
					sb.append(")");
				}
				cell = row.createCell(colNum);
				cell.setCellFormula(sb.toString());
				cell.setCellStyle(style);
				colNum++;
			}
			colNum = 0;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum++);
			cell.setCellValue("Total");
			cell.setCellStyle(style2);
			List<String[]> adresses = new ArrayList<>();
			for (Critere critere : criteres) {
				StringBuilder sb = new StringBuilder();
				sb.append("AVERAGE(");
				if (mapResultsByCritere.containsKey(critere.getTexte())) {
					for (String niveau : mapResultsByCritere.get(critere.getTexte())) {
						sb.append("Parametres!" + mapParam.get(critere.getTexte()).get(niveau).formatAsString())
								.append("+");
					}
					sb.setLength(sb.length() - 1);
					sb.append(")");
					cell = row.createCell(colNum++);
					cell.setCellFormula(sb.toString());
					cell.setCellStyle(style);
					String[] values = new String[2];
					values[0] = critere.getTexte();
					values[1] = cell.getAddress().formatAsString();
					adresses.add(values);
					colNum++;
				} else {
					sb.setLength(0);
					cell = row.createCell(colNum++);
					cell.setCellValue("");
					cell.setCellStyle(style);
					colNum++;
				}
				resultCandidats.get(candidat).put(critere.getTexte(), cell.getAddress());
			}
			cell = row.createCell(colNum++);
			if (!adresses.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("SUM(");
				for (String[] values : adresses) {
					sb.append(values[1]).append("*Parametres!").append(mapParam.get(values[0]).get("coef").formatAsString()).append("+");
				}
				sb.setLength(sb.length()-1);
				sb.append(")");
				cell.setCellFormula(sb.toString());
				cell.setCellStyle(style);
				resultCandidats.get(candidat).put("total", cell.getAddress());
			}
		}
	}

	private void autoSizeColumns(XSSFWorkbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(0);
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}
}