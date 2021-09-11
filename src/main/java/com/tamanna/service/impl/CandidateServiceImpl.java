package com.tamanna.service.impl;

import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.PeriodDTO;
import com.tamanna.entity.Candidate;
import com.tamanna.entity.CandidateTimeSlot;
import com.tamanna.repository.CandidateRepository;
import com.tamanna.repository.CandidateTimeSlotRepository;
import com.tamanna.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository repository;
    private final CandidateTimeSlotRepository timeSlotRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository repository, CandidateTimeSlotRepository timeSlotRepository) {
        this.repository = repository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public List<CandidateDTO> findAllCandidates() {
        return repository.findAllCandidates();
    }

    @Override
    public List<InterviewDTO> getAllScheduledInterviewsByCandidateId(Long id) {
        return repository.getAllScheduledInterviewsByCandidateId(id);
    }

    @Override
    public void save(CandidateDTO candidate) {
        repository.save(new Candidate(candidate.getFirstName(), candidate.getLastName()));
    }

    @Override
    public void saveAvailablePeriod(Long id, PeriodDTO periodDTO) {
        Candidate candidate = repository.getOne(id);

        CandidateTimeSlot timeSlot = CandidateTimeSlot.builder()
                .candidate(candidate)
                .dateFrom(periodDTO.getDateTimeFrom().toLocalDate())
                .dateTo(periodDTO.getDateTimeTo().toLocalDate())
                .timeFrom(periodDTO.getDateTimeFrom().toLocalTime())
                .timeTo(periodDTO.getDateTimeTo().toLocalTime())
                .build();

        timeSlotRepository.save(timeSlot);
    }
}
