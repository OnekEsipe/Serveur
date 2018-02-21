package com.onek.dao;

import java.util.List;

import com.onek.model.Candidat;

public interface CandidatesDao {
	public List<Candidat> findCandidatesByEvent(int idEvent);
	public void addCandidate(Candidat candidat);
	public void addCandidates(List<Candidat> candidates);
	public void supprimerCandidat(int idcandidat);
	public  Candidat findCandidatesById(int idcandidat);
}
