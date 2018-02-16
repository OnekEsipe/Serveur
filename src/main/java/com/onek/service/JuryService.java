package com.onek.service;

import java.util.HashMap;
import java.util.List;

import com.onek.model.Candidat;
import com.onek.model.Jury;

public interface JuryService {

	List<Jury> findJuryByIdevent(int idevent);
	HashMap<Jury, List<Candidat>> associatedJurysCandidatesByEvent(List<Jury> jurys, int idevent);

}
