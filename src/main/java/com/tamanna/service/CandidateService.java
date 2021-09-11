package com.tamanna.service;

import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.PeriodDTO;

import java.util.List;

public interface CandidateService {

    List<CandidateDTO> findAllCandidates();

    List<InterviewDTO> getAllScheduledInterviewsByCandidateId(Long id);

    void save(CandidateDTO candidate);

    void saveAvailablePeriod(Long id, PeriodDTO periodDTO);
}
