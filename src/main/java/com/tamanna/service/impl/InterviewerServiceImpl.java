package com.tamanna.service.impl;

import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.dto.PeriodDTO;
import com.tamanna.entity.Interviewer;
import com.tamanna.entity.InterviewerTimeSlot;
import com.tamanna.repository.InterviewerRepository;
import com.tamanna.repository.InterviewerTimeSlotRepository;
import com.tamanna.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterviewerServiceImpl implements InterviewerService {

    private final InterviewerRepository repository;
    private final InterviewerTimeSlotRepository timeSlotRepository;

    @Autowired
    public InterviewerServiceImpl(InterviewerRepository repository, InterviewerTimeSlotRepository timeSlotRepository) {
        this.repository = repository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public List<InterviewerDTO> findAllInterviewers() {
        return repository.findAllInterviewers();
    }

    @Override
    public List<InterviewDTO> getAllScheduledInterviewsByInterviewerId(Long id) {
        return repository.getAllScheduledInterviewsByInterviewerId(id);
    }

    @Override
    public void save(InterviewerDTO interviewer) {
        repository.save(new Interviewer(interviewer.getFirstName(), interviewer.getLastName()));
    }

    @Override
    public void saveAvailablePeriod(Long id, PeriodDTO periodDTO) {
        Interviewer interviewer = repository.getOne(id);

        InterviewerTimeSlot timeSlot = InterviewerTimeSlot.builder()
                .interviewer(interviewer)
                .dateFrom(periodDTO.getDateTimeFrom().toLocalDate())
                .dateTo(periodDTO.getDateTimeTo().toLocalDate())
                .timeFrom(periodDTO.getDateTimeFrom().toLocalTime())
                .timeTo(periodDTO.getDateTimeTo().toLocalTime())
                .build();

        timeSlotRepository.save(timeSlot);
    }
}
