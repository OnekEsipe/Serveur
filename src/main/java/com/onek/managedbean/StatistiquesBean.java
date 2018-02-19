package com.onek.managedbean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.onek.model.Note;
import com.onek.service.EvaluationService;
import com.onek.service.EvenementService;
import com.onek.utils.Navigation;

@Component("statistiques")
public class StatistiquesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EvaluationService evaluation;
	@Autowired
	private EvenementService evenement;

	private int idEvent;
	private Evenement event;

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
		}
	}

	public void buttonResult() {
		buildXlsx();
	}
	
	public void buttonRetour() {
		Navigation.redirect("eventAccueil.xhtml");
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
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
				cell.setCellFormula("'" + candidat.getNom() + " " + candidat.getPrenom() + "'!"
						+ resultCandidats.get(candidat).get(critere.getTexte()).formatAsString()); // ICI
																									// MAP.GET(CANDIDAT).GET(TOTALCRITERE)
				cell.setCellStyle(style);
			}
			cell = row.createCell(colNum++);
			if (resultCandidats.get(candidat).containsKey("total")) {
				cell.setCellFormula("'" + candidat.getNom() + " " + candidat.getPrenom() + "'!"
						+ resultCandidats.get(candidat).get("total").formatAsString());
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
			cell.setCellValue(0);
			cell.setCellStyle(style);
			mapParam.get(critere.getTexte()).put("-", cell.getAddress());
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
			System.out.println("ID Candidat : "+candidat.getIdcandidat());
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			XSSFSheet sheet = workbook.createSheet(candidat.getNom() + " " + candidat.getPrenom());
			System.out.println("Nombre d'évaluation : "+evaluations.size());
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
				System.out.println("Nombre de notes : "+eval.getNotes().size());
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(
						eval.getJury().getUtilisateur().getNom() + " " + eval.getJury().getUtilisateur().getPrenom());
				StringBuilder sb = new StringBuilder();
				cell.setCellStyle(style2);
				sb.append("SUM(");
				for (Note note : eval.getNotes()) {
					System.out.println("Note : niveau = "+note.getNiveau()+" critère = "+note.getCritere().getTexte()+ " commentaire = "+note.getCommentaire());
					if (!mapResultsByCritere.containsKey(note.getCritere().getTexte())) {
						mapResultsByCritere.put(note.getCritere().getTexte(), new ArrayList<>());
					}
					mapResultsByCritere.get(note.getCritere().getTexte()).add(niveaux.get(note.getNiveau()));
					cell = row.createCell(colNum++);
					cell.setCellValue(niveaux.get(note.getNiveau()));
					cell.setCellStyle(style2);
					sb.append("Parametres!" + mapParam.get(note.getCritere().getTexte()).get("coef").formatAsString()
							+ "*Parametres!" + mapParam.get(note.getCritere().getTexte())
									.get(niveaux.get(note.getNiveau())));
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
						sb.append("Parametres!" + mapParam.get(critere.getTexte()).get(niveau))
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

}
