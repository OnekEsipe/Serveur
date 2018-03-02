package com.onek.service;

import java.util.List;

import com.onek.model.Candidat;

/**
 * Interface de la classe CandidateServiceImpl. Couche service
 */
public interface CandidateService {

	/**
	 * @see com.onek.dao.CandidatesDao#findCandidatesByEvent(int)
	 */
	public List<Candidat> findCandidatesByEvent(int idEvent);
	
	/**
	 * @see com.onek.dao.CandidatesDao#addCandidate(int)
	 */
	public void addCandidate(Candidat candidat);
	
	/**
	 * @see com.onek.dao.CandidatesDao#addCandidates(java.util.List)
	 */
	public void addCandidates(List<Candidat> candidates);
	
	/**
	 * @see com.onek.dao.CandidatesDao#supprimerCandidat(int)
	 */
	public void supprimerCandidat(int idcandidat);
	
	/**
	 * @see com.onek.dao.CandidatesDao#findCandidatesById(int)
	 */
	public  Candidat findCandidatesById(int idcandidat);
	
}
