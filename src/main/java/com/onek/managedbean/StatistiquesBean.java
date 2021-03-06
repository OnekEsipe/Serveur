package com.onek.managedbean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Jury;
import com.onek.model.Note;
import com.onek.model.Signature;
import com.onek.model.Utilisateur;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.utils.Navigation;

/**
 * ManagedBean StatistiquesBean
 */
@Component("statistiques")
@Scope("session")
public class StatistiquesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(StatistiquesBean.class);

	@Autowired
	private EvaluationService evaluation;
	@Autowired
	private EvenementService evenement;

	private int idEvent;
	private Evenement event;

	private double totalAvancement;

	private String totalString;

	private boolean signedEvent;

	private List<StatsCandidate> notesByCandidats;
	private List<StatsJury> notesByJurys;

	private List<StatsCandidate> filteredNotesByCandidats;
	private List<StatsJury> filteredNotesByJurys;

	private List<Candidat> candidats;
	private List<Jury> jurys;

	/**
	 * Classe interne StatsJury. Etablie le lien entre un jury et ses statistiques
	 */
	public class StatsJury {
		String name;
		double value;
		String notation;
		List<Candidat> candidats;

		/**
		 * @param name Nom du jury
		 * @param value Valeur de la statistique
		 * @param nbNoted Nombre de notes réalisées
		 * @param nbNotes Nomre de notes total
		 * @param candidats Liste des candidats
		 */
		public StatsJury(String name, double value, int nbNoted, int nbNotes, List<Candidat> candidats) {
			this.name = name;
			this.value = value;
			this.notation = nbNoted + "/" + nbNotes;
			this.candidats = candidats;
		}

		public String getName() {
			return name;
		}

		public double getValue() {
			return value;
		}

		public String getNotation() {
			return notation;
		}

		public List<Candidat> getCandidats() {
			return candidats;
		}

		public void setCandidats(List<Candidat> candidats) {
			this.candidats = candidats;
		}
	}

	/**
	 * Classe interne StatsCandidate. Etablie le lien entre un candidat et ses statistiques
	 */
	public class StatsCandidate {
		String name;
		double value;
		String notation;
		List<Utilisateur> jurys;

		/**
		 * @param name Nom du candidat
		 * @param value Valeur de la statistique
		 * @param nbNoted Nombre de notes réalisées
		 * @param nbNotes Nomre de notes total
		 * @param candidats Liste des jurys
		 */
		public StatsCandidate(String name, double value, int nbNoted, int nbNotes, List<Utilisateur> jurys) {
			this.name = name;
			this.value = value;
			this.notation = nbNoted + "/" + nbNotes;
			this.jurys = jurys;
		}

		public String getName() {
			return name;
		}

		public double getValue() {
			return value;
		}

		public String getNotation() {
			return notation;
		}

		public List<Utilisateur> getJurys() {
			return jurys;
		}

		public void setJurys(List<Utilisateur> jurys) {
			this.jurys = jurys;
		}
	}

	/**
	 * Méthode appelée lors d'un GET sur la page statistiques.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent e) throws ParseException {
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
			notesByCandidats = new ArrayList<>();
			notesByJurys = new ArrayList<>();
			candidats = event.getCandidats();
			jurys = event.getJurys();
			filteredNotesByCandidats = new ArrayList<>();
			filteredNotesByJurys = new ArrayList<>();
			InitStatByCandidat();
			InitStatByJury();
			this.signedEvent = event.getSigningneeded();
		}
	}

	/**
	 * Construit le fichier excel pour les candidats (sans la signature)
	 * Crée une page pour chacun des candidats. Rempli chaque pages avec les critères et les notes
	 */
	public void buttonResult() {
		buildXlsx("candidat", false);
	}

	/**
	 * Construit le fichier excel pour les jurys (sans signature)
	 * Crée une page pour chacun des jurys. Rempli chaque pages avec les critères et les notes
	 */
	public void buttonResultByJurys() {
		buildXlsx("jury", false);
	}

	/**
	 * Construit le fichier excel pour les candidats (avec la signature)
	 * Crée une page pour chacun des candidats. Rempli chaque pages avec les critères et les notes
	 */
	public void buttonResultSign() {
		buildXlsx("candidat", true);
	}

	/**
	 * Getter de la variable idEvent
	 * @return idEvent Id de l'événement
	 */
	public int getIdEvent() {
		return idEvent;
	}

	/**
	 * Setter de la variable lastName
	 * @param lastName Nom de l'utilisateur
	 */
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	/**
	 * Getter de la variable getNotesByCandidats
	 * @return getNotesByCandidats Liste des notes par candidats
	 */
	public List<StatsCandidate> getNotesByCandidats() {
		return notesByCandidats;
	}

	/**
	 * Setter de la variable getNotesByCandidats
	 * @param getNotesByCandidats Liste des notes par candidats
	 */
	public void setNotesByCandidats(List<StatsCandidate> notesByCandidats) {
		this.notesByCandidats = notesByCandidats;
	}

	/**
	 * Getter de la variable notesByJurys
	 * @return notesByJurys Liste des notes par jurys
	 */
	public List<StatsJury> getNotesByJurys() {
		return notesByJurys;
	}

	/**
	 * Setter de la variable notesByJurys
	 * @param notesByJurys Liste des notes par jurys
	 */
	public void setNotesByJurys(List<StatsJury> notesByJurys) {
		this.notesByJurys = notesByJurys;
	}

	/**
	 * Getter de la variable totalAvancement
	 * @return totalAvancement Avancement total de la notation
	 */
	public double getTotalAvancement() {
		return totalAvancement;
	}

	/**
	 * Setter de la variable totalAvancement
	 * @param totalAvancement Avancement total de la notation
	 */
	public void setTotalAvancement(double totalAvancement) {
		this.totalAvancement = totalAvancement;
	}

	/**
	 * Initialisation des statistiques par candidats
	 */
	public void InitStatByCandidat() {
		int totalEvaluationCompletes = 0;
		int totalEvaluations = 0;
		double total = 0;
		double totalNoteDone = 0;
		List<Utilisateur> jurys;
		for (Candidat candidat : this.candidats) {
			jurys = new ArrayList<>();
			int evaluationCompletes = 0;
			int evaluationsDone = 0;
			int totalNotes = 0;
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			for (Evaluation evaluation : evaluations) {
				totalEvaluations++;
				int nbNoted = 0;
				List<Note> notes = evaluation.getNotes();
				for (Note note : notes) {
					totalNotes++;
					total++;
					if (note.getNiveau() > -1) {
						nbNoted++;
						totalNoteDone++;
						evaluationsDone++;
					} else {
						if (!jurys.contains(evaluation.getJury().getUtilisateur())) {
							jurys.add(evaluation.getJury().getUtilisateur());
						}
					}
				}
				if (nbNoted == notes.size()) {
					evaluationCompletes++;
					totalEvaluationCompletes++;
				}
			}
			if (totalNotes == 0) {
				notesByCandidats
						.add(new StatsCandidate(candidat.getNom() + " " + candidat.getPrenom(), 100, 0, 0, jurys));
			} else {
				notesByCandidats.add(new StatsCandidate(candidat.getNom() + " " + candidat.getPrenom(),
						(double) (((double) evaluationsDone / (double) totalNotes) * 100), evaluationCompletes,
						evaluations.size(), jurys));
			}
		}
		if (total == 0) {
			this.setTotalAvancement(100);
			this.setTotalString((int) totalNoteDone + "/" + (int) total);
		} else {
			this.setTotalAvancement(((double) totalNoteDone / (double) total) * 100);
			this.setTotalString((int) totalEvaluationCompletes + "/" + (int) totalEvaluations);
		}
	}

	/**
	 * Initialisation des statistiques par jurys
	 */
	public void InitStatByJury() {
		List<Candidat> candidats;
		for (Jury jury : this.jurys) {
			candidats = new ArrayList<>();
			int evaluationsDone = 0;
			int evaluationCompletes = 0;
			int totalNotes = 0;
			List<Evaluation> evaluations = evaluation.findByIdJury(jury.getIdjury());
			for (Evaluation evaluation : evaluations) {
				int nbNoted = 0;
				List<Note> notes = evaluation.getNotes();
				for (Note note : notes) {
					totalNotes++;
					if (note.getNiveau() > -1) {
						nbNoted++;
						evaluationsDone++;
					} else {
						if (!candidats.contains(evaluation.getCandidat())) {
							candidats.add(evaluation.getCandidat());
						}
					}
				}
				if (nbNoted == notes.size()) {
					evaluationCompletes++;
				}
			}
			Utilisateur user = jury.getUtilisateur();
			if (totalNotes == 0) {
				notesByJurys.add(new StatsJury(user.getNom() + " " + user.getPrenom(), (double) 100, 0, 0, candidats));
			} else {
				notesByJurys.add(new StatsJury(user.getNom() + " " + user.getPrenom(),
						(double) (((double) evaluationsDone / (double) totalNotes) * 100), evaluationCompletes,
						evaluations.size(), candidats));
			}
		}
	}

	// PARTIE EXPORT DE L'EVENEMENT
	private Map<String, String> mapParam = new HashMap<>();
	private Map<Integer, String> niveaux = new HashMap<>();
	private Map<Candidat, Map<String, CellAddress>> resultCandidats = new HashMap<>();
	private Map<Jury, Map<String, CellAddress>> resultJurys = new HashMap<>();
	private Map<Critere, String> paramRange = new HashMap<>();

	private XSSFCellStyle style;
	private XSSFCellStyle style2;
	private XSSFCellStyle styleSignature;

	private void init(XSSFWorkbook workbook) {
		mapParam = new HashMap<>();
		niveaux = new HashMap<>();
		resultCandidats = new HashMap<>();
		resultJurys = new HashMap<>();
		paramRange = new HashMap<>();
		niveaux.put(0, "A");
		niveaux.put(1, "B");
		niveaux.put(2, "C");
		niveaux.put(3, "D");
		niveaux.put(4, "E");
		niveaux.put(5, "F");
		niveaux.put(-1, "-");
		style = workbook.createCellStyle();
		style2 = workbook.createCellStyle();
		styleSignature = workbook.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		styleSignature.setAlignment(HorizontalAlignment.CENTER);
		styleSignature.setVerticalAlignment(VerticalAlignment.CENTER);
	}

	/**
	 * Construction du fichier excel. Nommage du fichier en fonction du type d'export.
	 * @param who
	 * @param signature True si la signature doit être exporté
	 */
	public void buildXlsx(String who, boolean signature) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		init(workbook);
		List<Critere> criteres = event.getCriteres();
		criteres.sort(Comparator.comparing(Critere::getCategorie).thenComparing(Critere::getIdcritere));
		fillParameterPage(workbook, criteres);
		if (who.equals("candidat")) {
			fillCandidatesPages(workbook, criteres, signature);
		} else {
			fillJurysPages(workbook, criteres);
		}
		fillResultsPage(workbook, criteres, who);
		autoSizeColumns(workbook);
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.setResponseContentType("application/vnd.ms-excel");
			if (who.equals("jury")) {
				externalContext.setResponseHeader("Content-Disposition",
						"attachment; filename=\"Export_Jury_" + event.getNom() + ".xlsx\"");
			} else if (who.equals("candidat") && !signature) {
				externalContext.setResponseHeader("Content-Disposition",
						"attachment; filename=\"Export_Candidat_" + event.getNom() + ".xlsx\"");
			} else if (who.equals("candidat") && signature) {
				externalContext.setResponseHeader("Content-Disposition",
						"attachment; filename=\"Export_Candidat_" + event.getNom() + "_Signature" + ".xlsx\"");
			}
			workbook.write(externalContext.getResponseOutputStream());
			facesContext.responseComplete();
		} catch (FileNotFoundException e) {
			logger.error(this.getClass().getName(), e);
		} catch (IOException e) {
			logger.error(this.getClass().getName(), e);
		}
	}

	private void fillParameterPage(XSSFWorkbook workbook, List<Critere> criteres) {
		int colNum = 0;
		int rowNum = 0;
		XSSFSheet sheet = workbook.createSheet("Parametres");
		for (Critere critere : criteres) {
			Row row = sheet.createRow(rowNum);
			Cell cell = row.createCell(colNum);
			cell.setCellValue(critere.getTexte());
			cell.setCellStyle(style2);
			String range = "Parametres!" + cell.getAddress().formatAsString();
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Coefficient : ");
			cell.setCellStyle(style);
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellValue(critere.getCoefficient().doubleValue());
			cell.setCellStyle(style);
			mapParam.put(critere.getTexte(), cell.getAddress().formatAsString());
			colNum--;
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Descripteur");
			cell.setCellStyle(style2);
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellValue("Poids");
			cell.setCellStyle(style2);
			for (Descripteur descripteur : critere.getDescripteurs()) {
				rowNum++;
				colNum--;
				row = sheet.createRow(rowNum);
				cell = row.createCell(colNum);
				cell.setCellValue(descripteur.getNiveau());
				cell.setCellStyle(style);
				colNum++;
				cell = row.createCell(colNum);
				cell.setCellValue(descripteur.getPoids().doubleValue());
				cell.setCellStyle(style);
			}
			rowNum++;
			colNum--;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("-");
			cell.setCellStyle(style);
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellType(CellType.BLANK);
			cell.setCellStyle(style);
			range = range + ":" + cell.getAddress().formatAsString();
			rowNum += 2;
			colNum = 0;
			paramRange.put(critere, range);
		}
	}

	private void fillCandidatesPages(XSSFWorkbook workbook, List<Critere> criteres, boolean needSignature) {
		for (Candidat candidat : candidats) {
			String fullName = (candidat.getNom().replace("'", " ") + " " + candidat.getPrenom().replace("'", " "));
			if (fullName.length() >= 31) {
				fullName = fullName.substring(0, 30);
			}
			Map<Critere, List<String>> notesByCriteres = new HashMap<>();
			resultCandidats.put(candidat, new HashMap<>());
			int rowNum = 0;
			int colNum = 0;
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			XSSFSheet sheet = workbook.createSheet(fullName);
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
			cell = row.createCell(colNum++);
			cell.setCellValue("Commentaire Général");
			cell.setCellStyle(style2);
			if (needSignature) {
				cell = row.createCell(colNum++);
				cell.setCellValue("Signatures");
				cell.setCellStyle(style2);
			}
			for (Evaluation eval : evaluations) {
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(
						eval.getJury().getUtilisateur().getNom() + " " + eval.getJury().getUtilisateur().getPrenom());
				StringBuilder sb = new StringBuilder();
				cell.setCellStyle(style2);
				sb.append("SUM(");
				List<Note> notes = eval.getNotes();
				notes.sort((a, b) -> {
					int comp1 = a.getCritere().getCategorie().compareTo(b.getCritere().getCategorie());
					if (comp1 != 0) {
						return comp1;
					} else {
						return a.getCritere().getIdcritere() - b.getCritere().getIdcritere();
					}
				});
				for (Note note : notes) {
					if (!notesByCriteres.containsKey(note.getCritere())) {
						notesByCriteres.put(note.getCritere(), new ArrayList<>());
					}
					cell = row.createCell(colNum++);
					cell.setCellValue(niveaux.get(note.getNiveau()));
					cell.setCellStyle(style2);
					notesByCriteres.get(note.getCritere()).add(cell.getAddress().formatAsString());
					sb.append("Parametres!").append(mapParam.get(note.getCritere().getTexte())).append("*VLOOKUP(")
							.append(cell.getAddress().formatAsString()).append(",")
							.append(paramRange.get(note.getCritere())).append(",2,FALSE)");
					cell = row.createCell(colNum++);
					cell.setCellValue(note.getCommentaire());
					cell.setCellStyle(style);
					sb.append("+");
				}
				if (eval.getNotes().isEmpty()) {
					sb.setLength(0);
					cell = row.createCell(colNum);
					cell.setCellValue("");
					cell.setCellStyle(style);
				} else {
					sb.setLength(sb.length() - 1);
					sb.append(")");
					cell = row.createCell(colNum);
					cell.setCellFormula(sb.toString());
					cell.setCellStyle(style);
					colNum++;
				}
				cell = row.createCell(colNum);
				cell.setCellValue(eval.getCommentaire());
				cell.setCellStyle(style2);
				colNum++;
				if (needSignature) {
					for (Signature signature : eval.getSignatures()) {
						cell = row.createCell(colNum);
						cell.setCellValue(signature.getNom());
						cell.setCellStyle(styleSignature);
						sheet.autoSizeColumn(colNum);
						colNum++;
						cell = row.createCell(colNum);

						int imageIDX = workbook.addPicture(signature.getSignature(), Workbook.PICTURE_TYPE_JPEG);
						CreationHelper helper = workbook.getCreationHelper();
						Drawing<?> drawing = sheet.createDrawingPatriarch();
						ClientAnchor anchor = helper.createClientAnchor();
						anchor.setCol1(colNum);
						anchor.setRow1(rowNum - 1);
						anchor.setRow2(rowNum - 1);
						anchor.setCol2(colNum);
						Picture pict = drawing.createPicture(anchor, imageIDX);
						pict.resize(1, 1);
						sheet.setColumnWidth(colNum, 6000);
						row.setHeight((short) 1400);
						colNum++;
					}
				}
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
				if (notesByCriteres.containsKey(critere)) {
					for (String addresse : notesByCriteres.get(critere)) {
						sb.append("VLOOKUP(").append(addresse).append(",").append(paramRange.get(critere))
								.append(",2,FALSE)").append(",");
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
					sb.append(values[1]).append("*Parametres!").append(mapParam.get(values[0])).append("+");
				}
				sb.setLength(sb.length() - 1);
				sb.append(")");
				cell.setCellFormula(sb.toString());
				cell.setCellStyle(style);
				resultCandidats.get(candidat).put("total", cell.getAddress());
			}
		}
	}

	private void fillJurysPages(XSSFWorkbook workbook, List<Critere> criteres) {
		for (Jury jury : jurys) {
			String fullName = (jury.getUtilisateur().getNom().replace("'", " ") + " "
					+ jury.getUtilisateur().getPrenom().replace("'", " "));
			if (fullName.length() >= 31) {
				fullName = fullName.substring(0, 30);
			}
			resultJurys.put(jury, new HashMap<>());
			int rowNum = 0;
			int colNum = 0;
			Map<Critere, List<String>> notesByCriteres = new HashMap<>();
			List<Evaluation> evaluations = evaluation.findByIdJury(jury.getIdjury());
			XSSFSheet sheet = workbook.createSheet(fullName);
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum++);
			cell.setCellValue("Candidats");
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
			cell = row.createCell(colNum++);
			cell.setCellValue("Commentaire Général");
			cell.setCellStyle(style2);
			for (Evaluation eval : evaluations) {
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(eval.getCandidat().getNom() + " " + eval.getCandidat().getPrenom());
				StringBuilder sb = new StringBuilder();
				cell.setCellStyle(style2);
				sb.append("SUM(");
				List<Note> notes = eval.getNotes();
				notes.sort((a, b) -> {
					int comp1 = a.getCritere().getCategorie().compareTo(b.getCritere().getCategorie());
					if (comp1 != 0) {
						return comp1;
					} else {
						return a.getCritere().getIdcritere() - b.getCritere().getIdcritere();
					}
				});
				for (Note note : notes) {
					if (!notesByCriteres.containsKey(note.getCritere())) {
						notesByCriteres.put(note.getCritere(), new ArrayList<>());
					}
					cell = row.createCell(colNum++);
					cell.setCellValue(niveaux.get(note.getNiveau()));
					cell.setCellStyle(style2);
					notesByCriteres.get(note.getCritere()).add(cell.getAddress().formatAsString());
					sb.append("Parametres!").append(mapParam.get(note.getCritere().getTexte())).append("*VLOOKUP(")
							.append(cell.getAddress().formatAsString()).append(",")
							.append(paramRange.get(note.getCritere())).append(",2,FALSE)");
					cell = row.createCell(colNum++);
					cell.setCellValue(note.getCommentaire());
					cell.setCellStyle(style);
					sb.append("+");
				}
				if (eval.getNotes().isEmpty()) {
					sb.setLength(0);
					cell = row.createCell(colNum);
					cell.setCellValue("");
					cell.setCellStyle(style);
					colNum++;
				} else {
					sb.setLength(sb.length() - 1);
					sb.append(")");
					cell = row.createCell(colNum);
					cell.setCellFormula(sb.toString());
					cell.setCellStyle(style);
					colNum++;
				}
				cell = row.createCell(colNum);
				cell.setCellValue(eval.getCommentaire());
				cell.setCellStyle(style2);
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
				if (notesByCriteres.containsKey(critere)) {
					for (String addresse : notesByCriteres.get(critere)) {
						sb.append("VLOOKUP(").append(addresse).append(",").append(paramRange.get(critere))
								.append(",2,FALSE)").append(",");
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
				resultJurys.get(jury).put(critere.getTexte(), cell.getAddress());
			}
			cell = row.createCell(colNum++);
			if (!adresses.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				sb.append("SUM(");
				for (String[] values : adresses) {
					sb.append(values[1]).append("*Parametres!").append(mapParam.get(values[0])).append("+");
				}
				sb.setLength(sb.length() - 1);
				sb.append(")");
				cell.setCellFormula(sb.toString());
				cell.setCellStyle(style);
				resultJurys.get(jury).put("total", cell.getAddress());
			}
		}
	}

	private void fillResultsPage(XSSFWorkbook workbook, List<Critere> criteres, String who) {
		int rowNum = 0;
		int colNum = 0;
		XSSFSheet sheet = workbook.createSheet("Résultats globaux");
		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(colNum++);
		if (who.equals("candidat")) {
			cell.setCellValue("Candidats");
		} else {
			cell.setCellValue("Jurys");
		}
		for (Critere critere : criteres) {
			cell = row.createCell(colNum++);
			cell.setCellValue(critere.getTexte());
			cell.setCellStyle(style2);
		}
		cell = row.createCell(colNum++);
		cell.setCellValue("Total");
		cell.setCellStyle(style2);
		if (who.equals("candidat")) {
			for (Candidat candidat : candidats) {
				String fullName = (candidat.getNom().replace("'", " ") + " " + candidat.getPrenom().replace("'", " "));
				if (fullName.length() >= 31) {
					fullName = fullName.substring(0, 30);
				}
				colNum = 0;
				row = sheet.createRow(rowNum++);
				cell = row.createCell(colNum++);
				cell.setCellValue(candidat.getNom() + " " + candidat.getPrenom());
				cell.setCellStyle(style2);
				for (Critere critere : criteres) {
					cell = row.createCell(colNum++);
					cell.setCellFormula("'" + fullName + "'!"
							+ resultCandidats.get(candidat).get(critere.getTexte()).formatAsString());
					cell.setCellStyle(style);
				}
				cell = row.createCell(colNum++);
				if (resultCandidats.get(candidat).containsKey("total")) {
					cell.setCellFormula(
							"'" + fullName + "'!" + resultCandidats.get(candidat).get("total").formatAsString());
					cell.setCellStyle(style);
				}
			}
		} else {
			for (Jury jury : jurys) {
				String fullName = (jury.getUtilisateur().getNom().replace("'", " ") + " "
						+ jury.getUtilisateur().getPrenom().replace("'", " "));
				if (fullName.length() >= 31) {
					fullName = fullName.substring(0, 30);
				}
				colNum = 0;
				row = sheet.createRow(rowNum++);
				cell = row.createCell(colNum++);
				cell.setCellValue(jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom());
				cell.setCellStyle(style2);
				for (Critere critere : criteres) {
					cell = row.createCell(colNum++);
					cell.setCellFormula(
							"'" + fullName + "'!" + resultJurys.get(jury).get(critere.getTexte()).formatAsString());
					cell.setCellStyle(style);
				}
				cell = row.createCell(colNum++);
				if (resultJurys.get(jury).containsKey("total")) {
					cell.setCellFormula("'" + fullName + "'!" + resultJurys.get(jury).get("total").formatAsString());
					cell.setCellStyle(style);
				}
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

	public String getTotalString() {
		return totalString;
	}

	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}

	public List<StatsJury> getFilteredNotesByJurys() {
		return filteredNotesByJurys;
	}

	public void setFilteredNotesByJurys(List<StatsJury> filteredNotesByJurys) {
		this.filteredNotesByJurys = filteredNotesByJurys;
	}

	public List<StatsCandidate> getFilteredNotesByCandidats() {
		return filteredNotesByCandidats;
	}

	public void setFilteredNotesByCandidats(List<StatsCandidate> filteredNotesByCandidats) {
		this.filteredNotesByCandidats = filteredNotesByCandidats;
	}

	public boolean isSignedEvent() {
		return signedEvent;
	}

	public void setSignedEvent(boolean signedEvent) {
		this.signedEvent = signedEvent;
	}

}
