package com.onek.service;

import java.util.List;

import com.onek.model.Candidat;

public interface CandidateService {
	public List<Candidat> findCandidatesByEvent(int idEvent);
	public void addCandidate(Candidat candidat);
	public void supprimerCandidat(int idcandidat);

}
