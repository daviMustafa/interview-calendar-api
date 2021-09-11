package com.tamanna.service;

import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.dto.PeriodDTO;

import java.util.List;

public interface InterviewerService {

    List<InterviewerDTO> findAllInterviewers();

    List<InterviewDTO> getAllScheduledInterviewsByInterviewerId(Long id);

    void save(InterviewerDTO interviewer);

    void saveAvailablePeriod(Long id, PeriodDTO periodDTO);

}
