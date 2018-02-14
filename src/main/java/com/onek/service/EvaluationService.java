package com.onek.service;

import java.util.List;

import com.onek.model.Evaluation;

public interface EvaluationService {

	List<Evaluation> findByIdCandidate(Integer idCandidat);
}
