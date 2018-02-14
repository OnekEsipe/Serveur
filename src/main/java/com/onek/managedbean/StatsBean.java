package com.onek.managedbean;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.onek.model.Candidat;
import com.onek.model.Critere;
import com.onek.model.Descripteur;
import com.onek.model.Evaluation;
import com.onek.model.Evenement;
import com.onek.model.Note;
import com.onek.service.EvaluationService;

public class StatsBean {
	
	Map<String, Map<String, CellAddress>> mapAddress = new HashMap<>();
	
	@Autowired
	EvaluationService evaluation;
	
	public void buildXlsx(Evenement event) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		List<Critere> criteres = event.getCriteres();
		List<Candidat> candidats = event.getCandidats();
		fillParameterPage(workbook, criteres);
		fillCandidatesPages(workbook, criteres, candidats);
		fillResultsPage(workbook, criteres, candidats);
		try {
            FileOutputStream outputStream = new FileOutputStream("Export_"+event.getNom());
            workbook.write(outputStream);
            workbook.close();
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
			cell.setCellValue(candidat.getNom()+" "+candidat.getPrenom());
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
		XSSFSheet sheet = workbook.createSheet("Paramètres");
		for (Critere critere : criteres) {
			mapAddress.put(critere.getTexte(), new HashMap<>());
			int rowNum = 0;
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(critere.getTexte());
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum);
			cell.setCellValue("Coefficient : ");
			cell = row.createCell(colNum++);
			cell.setCellValue(critere.getCoefficient().doubleValue());
			mapAddress.get(critere.getTexte()).put("coef", cell.getAddress());
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum--);
			cell.setCellValue("Descripteur");
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum--);
			cell.setCellValue("Poids");
			for (Descripteur descripteur : critere.getDescripteurs()) {
				row = sheet.createRow(rowNum++);
				cell = row.createCell(colNum--);
				cell.setCellValue(descripteur.getNiveau());
				cell = row.createCell(colNum++);
				cell.setCellValue(descripteur.getPoids().doubleValue());
				mapAddress.get(critere.getTexte()).put(descripteur.getNiveau(), cell.getAddress());
			}
			cell = row.createCell(colNum++);
		}
	}

	private void fillCandidatesPages(XSSFWorkbook workbook, List<Critere> criteres, List<Candidat> candidats) {
		int rowNum = 0;
		int colNum = 0;
		for (Candidat candidat : candidats) {
			List<Evaluation> evaluations = evaluation.findByIdCandidate(candidat.getIdcandidat());
			XSSFSheet sheet = workbook.createSheet(candidat.getNom()+" "+candidat.getPrenom());
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(candidat.getNom()+" "+candidat.getPrenom());
			for (Critere critere : criteres) {
				cell = row.createCell(colNum++);
				cell.setCellValue("Note "+critere.getTexte());
				cell = row.createCell(colNum++);
				cell.setCellValue("Commentaire "+critere.getTexte());
			}
			for (Evaluation eval : evaluations) {
				row = sheet.createRow(rowNum++);
				colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(eval.getJury().getUtilisateur().getNom()+" "+eval.getJury().getUtilisateur().getPrenom());
				for (Note note : eval.getNotes()) {
					cell = row.createCell(colNum++);
					cell.setCellValue(note.getNiveau());
					cell.setCellValue(note.getCommentaire());
				}
			}
		}
	}
	
	
	
}
