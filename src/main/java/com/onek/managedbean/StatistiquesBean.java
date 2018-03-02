package com.onek.managedbean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
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
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.onek.model.Signature;
import com.onek.model.Utilisateur;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.utils.Navigation;

@Component("statistiques")
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

	public class StatsJury {
		String name;
		double value;
		String notation;
		List<Candidat> candidats;

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

	public class StatsCandidate {
		String name;
		double value;
		String notation;
		List<Utilisateur> jurys;

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
			InitStatByCandidat();
			InitStatByJury();
			this.signedEvent = event.getSigningneeded();
		}
	}

	public void buttonResult() {
		buildXlsx("candidat", false);
	}

	public void buttonResultByJurys() {
		buildXlsx("jury", false);
	}

	public void buttonResultSign() {
		buildXlsx("candidat", true);
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public List<StatsCandidate> getNotesByCandidats() {
		return notesByCandidats;
	}

	public void setNotesByCandidats(List<StatsCandidate> notesByCandidats) {
		this.notesByCandidats = notesByCandidats;
	}

	public List<StatsJury> getNotesByJurys() {
		return notesByJurys;
	}

	public void setNotesByJurys(List<StatsJury> notesByJurys) {
		this.notesByJurys = notesByJurys;
	}

	public double getTotalAvancement() {
		return totalAvancement;
	}

	public void setTotalAvancement(double totalAvancement) {
		this.totalAvancement = totalAvancement;
	}

	public void InitStatByCandidat() {
		double total = 0;
		double totalNoteDone = 0;
		List<Utilisateur> jurys;
		for (Candidat candidat : this.candidats) {
			jurys = new ArrayList<>();
			int totalNotes = 0;
			int nbNoted = 0;
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			for (Evaluation evaluation : evaluations) {
				List<Note> notes = evaluation.getNotes();
				for (Note note : notes) {
					totalNotes++;
					total++;
					if (note.getNiveau() > -1) {
						nbNoted++;
						totalNoteDone++;
					} else {
						if (!jurys.contains(evaluation.getJury().getUtilisateur())) {
							jurys.add(evaluation.getJury().getUtilisateur());
						}
					}
				}
			}
			if (totalNotes == 0) {
				notesByCandidats
						.add(new StatsCandidate(candidat.getNom() + " " + candidat.getPrenom(), 100, 0, 0, jurys));
			} else {
				notesByCandidats.add(new StatsCandidate(candidat.getNom() + " " + candidat.getPrenom(),
						(double) (((double) nbNoted / (double) totalNotes) * 100), nbNoted, totalNotes, jurys));
			}
		}
		if (total == 0) {
			this.setTotalAvancement(100);
			this.setTotalString((int) totalNoteDone + "/" + (int) total);
		} else {
			this.setTotalAvancement(((double) totalNoteDone / (double) total) * 100);
			this.setTotalString((int) totalNoteDone + "/" + (int) total);
		}
	}

	public void InitStatByJury() {
		List<Candidat> candidats;
		for (Jury jury : this.jurys) {
			candidats = new ArrayList<>();
			int totalNotes = 0;
			int nbNoted = 0;
			List<Evaluation> evaluations = evaluation.findByIdJury(jury.getIdjury());
			for (Evaluation evaluation : evaluations) {
				List<Note> notes = evaluation.getNotes();
				for (Note note : notes) {
					totalNotes++;
					if (note.getNiveau() > -1) {
						nbNoted++;
					} else {
						if (!candidats.contains(evaluation.getCandidat())) {
							candidats.add(evaluation.getCandidat());
						}
					}
				}
			}
			Utilisateur user = jury.getUtilisateur();
			if (totalNotes == 0) {
				notesByJurys.add(new StatsJury(user.getNom() + " " + user.getPrenom(), (double) 100, 0, 0, candidats));
			} else {
				notesByJurys.add(new StatsJury(user.getNom() + " " + user.getPrenom(),
						(double) (((double) nbNoted / (double) totalNotes) * 100), nbNoted, totalNotes, candidats));
			}
		}
	}

	// PARTIE EXPORT DE L'EVENEMENT
	private final Map<String, Map<String, CellAddress>> mapParam = new HashMap<>();
	private final Map<Integer, String> niveaux = new HashMap<>();
	private final Map<Candidat, Map<String, CellAddress>> resultCandidats = new HashMap<>();
	private final Map<Jury, Map<String, CellAddress>> resultJurys = new HashMap<>();

	private XSSFCellStyle style;
	private XSSFCellStyle style2;
	private XSSFCellStyle styleImage;

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

	public void buildXlsx(String who, boolean signature) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		init(workbook);
		List<Critere> criteres = event.getCriteres();
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
			} else if (who.equals("candidat")) {
				externalContext.setResponseHeader("Content-Disposition",
						"attachment; filename=\"Export_Candidat_" + event.getNom() + ".xlsx\"");
			}
			workbook.write(externalContext.getResponseOutputStream());
			facesContext.responseComplete();
		} catch (FileNotFoundException e) {
			logger.error(this.getClass().getName(), e);
		} catch (IOException e) {
			logger.error(this.getClass().getName(), e);
		}
	}

	private void fillJurysPages(XSSFWorkbook workbook, List<Critere> criteres) {
		for (Jury jury : jurys) {
			resultJurys.put(jury, new HashMap<>());
			int rowNum = 0;
			int colNum = 0;
			Map<String, List<String>> mapResultsByCritere = new HashMap<>();
			List<Evaluation> evaluations = evaluation.findByIdJury(jury.getIdjury());
			XSSFSheet sheet = workbook
					.createSheet(jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom());
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
			for (Evaluation eval : evaluations) {
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(eval.getCandidat().getNom() + " " + eval.getCandidat().getPrenom());
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
							+ "*Parametres!"
							+ mapParam.get(note.getCritere().getTexte()).get(niveaux.get(note.getNiveau())));
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
						sb.append("Parametres!" + mapParam.get(critere.getTexte()).get(niveau)).append("+");
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
					sb.append(values[1]).append("*Parametres!")
							.append(mapParam.get(values[0]).get("coef").formatAsString()).append("+");
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
				colNum = 0;
				row = sheet.createRow(rowNum++);
				cell = row.createCell(colNum++);
				cell.setCellValue(candidat.getNom() + " " + candidat.getPrenom());
				cell.setCellStyle(style2);
				for (Critere critere : criteres) {
					cell = row.createCell(colNum++);
					cell.setCellFormula("'" + candidat.getNom() + " " + candidat.getPrenom() + "'!"
							+ resultCandidats.get(candidat).get(critere.getTexte()).formatAsString());
					cell.setCellStyle(style);
				}
				cell = row.createCell(colNum++);
				if (resultCandidats.get(candidat).containsKey("total")) {
					cell.setCellFormula("'" + candidat.getNom() + " " + candidat.getPrenom() + "'!"
							+ resultCandidats.get(candidat).get("total").formatAsString());
					cell.setCellStyle(style);
				}
			}
		} else {
			for (Jury jury : jurys) {
				colNum = 0;
				row = sheet.createRow(rowNum++);
				cell = row.createCell(colNum++);
				cell.setCellValue(jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom());
				cell.setCellStyle(style2);
				for (Critere critere : criteres) {
					cell = row.createCell(colNum++);
					cell.setCellFormula("'" + jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom()
							+ "'!" + resultJurys.get(jury).get(critere.getTexte()).formatAsString());
					cell.setCellStyle(style);
				}
				cell = row.createCell(colNum++);
				if (resultJurys.get(jury).containsKey("total")) {
					cell.setCellFormula("'" + jury.getUtilisateur().getNom() + " " + jury.getUtilisateur().getPrenom()
							+ "'!" + resultJurys.get(jury).get("total").formatAsString());
					cell.setCellStyle(style);
				}
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
			rowNum++;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("Coefficient : ");
			cell.setCellStyle(style);
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellValue(critere.getCoefficient().doubleValue());
			cell.setCellStyle(style);
			mapParam.get(critere.getTexte()).put("coef", cell.getAddress());
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
				mapParam.get(critere.getTexte()).put(descripteur.getNiveau(), cell.getAddress());
			}
			rowNum++;
			colNum--;
			row = sheet.createRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue("-");
			cell.setCellStyle(style);
			colNum++;
			cell = row.createCell(colNum);
			cell.setCellValue("");
			cell.setCellStyle(style);
			mapParam.get(critere.getTexte()).put("-", cell.getAddress());
			rowNum += 2;
			colNum = 0;
		}
	}

	private void fillCandidatesPages(XSSFWorkbook workbook, List<Critere> criteres, boolean needSignature) {
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
							+ "*Parametres!"
							+ mapParam.get(note.getCritere().getTexte()).get(niveaux.get(note.getNiveau())));
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
				if (needSignature) {
					for (Signature signature : eval.getSignatures()) {
						cell = row.createCell(colNum);
						cell.setCellValue(signature.getNom());
						cell.setCellStyle(style);
						colNum++;
						cell = row.createCell(colNum);
						
						int imageIDX = workbook.addPicture(signature.getSignature(), Workbook.PICTURE_TYPE_JPEG);
						CreationHelper helper = workbook.getCreationHelper();
						Drawing<?> drawing = sheet.createDrawingPatriarch();
						ClientAnchor anchor = helper.createClientAnchor();
//						anchor.setDx1(0);
//						anchor.setDy1(0);
//						anchor.setDx2(1023);
//						anchor.setDy2(255);
						anchor.setCol1(colNum);
						anchor.setRow1(rowNum-1);
						anchor.setRow2(rowNum-1);
						anchor.setCol2(colNum);
						Picture pict = drawing.createPicture(anchor, imageIDX);
						pict.resize(3, 1);
						sheet.setColumnWidth(colNum, 8000);
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
				if (mapResultsByCritere.containsKey(critere.getTexte())) {
					for (String niveau : mapResultsByCritere.get(critere.getTexte())) {
						sb.append("Parametres!" + mapParam.get(critere.getTexte()).get(niveau)).append("+");
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
					sb.append(values[1]).append("*Parametres!")
							.append(mapParam.get(values[0]).get("coef").formatAsString()).append("+");
				}
				sb.setLength(sb.length() - 1);
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
