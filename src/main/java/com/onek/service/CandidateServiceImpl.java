package com.onek.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onek.dao.CandidatesDao;
import com.onek.model.Candidat;

/**
 * Service CandidateServiceImpl
 */
@Service
public class CandidateServiceImpl implements CandidateService, Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired
	private CandidatesDao candidatedao;
	
	@Override
	public List<Candidat> findCandidatesByEvent(int idEvent) {
		return candidatedao.findCandidatesByEvent(idEvent);
	}

	@Override
	public void addCandidate(Candidat candidat) {
		candidatedao.addCandidate(candidat);
		
	}
	@Override
	public void supprimerCandidat(int idcandidat) {
		candidatedao.supprimerCandidat(idcandidat);
	}

	@Override
	public void addCandidates(List<Candidat> candidates) {
		candidatedao.addCandidates(candidates);	
	}
	@Override
	public  Candidat findCandidatesById(int idcandidat) {
		return candidatedao.findCandidatesById(idcandidat);
	}
}
