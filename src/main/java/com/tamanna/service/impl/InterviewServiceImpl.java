package com.tamanna.service.impl;

import com.tamanna.dto.AvailableCandidatePeriodDTO;
import com.tamanna.dto.AvailableInterviewerPeriodDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.entity.Candidate;
import com.tamanna.entity.Interview;
import com.tamanna.entity.Interviewer;
import com.tamanna.repository.CandidateRepository;
import com.tamanna.repository.InterviewRepository;
import com.tamanna.repository.InterviewerRepository;
import com.tamanna.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@Transactional
public class InterviewServiceImpl implements InterviewService {

    private final InterviewRepository repository;
    private final CandidateRepository candidateRepository;
    private final InterviewerRepository interviewerRepository;


    @Autowired
    public InterviewServiceImpl(InterviewRepository repository, CandidateRepository candidateRepository, InterviewerRepository interviewerRepository) {
        this.repository = repository;
        this.candidateRepository = candidateRepository;
        this.interviewerRepository = interviewerRepository;
    }

    @Override
    public List<AvailableInterviewerPeriodDTO> getAvailablePeriodsOfTime(@NotNull Long candidateId, @NotEmpty List<Long> interviewerIds, @NotEmpty List<DayOfWeek> daysOfWeek) {
        Optional<Candidate> candidate = candidateRepository.findCandidateTimeSlots(candidateId);
        List<Interviewer> interviewers = interviewerRepository.findInterviewersTimeSlots(interviewerIds);

        List<AvailableInterviewerPeriodDTO> result = new ArrayList<>();

        if (candidate.isPresent()) {

            TemporalQuery<Boolean> weekDaysFilter = t -> {
                DayOfWeek dow = DayOfWeek.from(t);
                return daysOfWeek.contains(dow);
            };

            if (!CollectionUtils.isEmpty(interviewers)) {
                List<AvailableInterviewerPeriodDTO> interviewerPeriodsList = new ArrayList<>();

                interviewers.stream().map(i -> i.getInterviewerPeriodsList(weekDaysFilter))
                        .forEach(interviewerPeriodsList::addAll);

                List<AvailableCandidatePeriodDTO> candidatePeriodList = candidate.get().getCandidatePeriodsList(weekDaysFilter)
                        .stream().distinct().collect(Collectors.toList());

                result = interviewerPeriodsList.stream().distinct()
                        .filter(inter -> candidatePeriodList.stream().anyMatch(c -> inter.getDate().equals(c.getDate()))).collect(Collectors.toList());

            }
        }
        return result;
    }

    @Override
    public InterviewDTO saveInterview(@Valid @NotNull InterviewDTO interviewDTO) throws Exception {
        List<AvailableInterviewerPeriodDTO> availablePeriodsOfTime = getAvailablePeriodsOfTime(interviewDTO.getCandidateId(),
                Collections.singletonList(interviewDTO.getInterviewerId()),
                Collections.singletonList(interviewDTO.getStartDateTime().getDayOfWeek()));

        boolean dateAvailable = availablePeriodsOfTime.stream().distinct().anyMatch(i -> i.getDate().equals(interviewDTO.getStartDateTime())
                && i.getId().equals(interviewDTO.getInterviewerId()));

        if(dateAvailable){
            Candidate candidate = candidateRepository.getOne(interviewDTO.getCandidateId());
            Interviewer interviewer = interviewerRepository.getOne(interviewDTO.getInterviewerId());

            Interview interview = new Interview(candidate, interviewer, interviewDTO.getStartDateTime(), interviewDTO.getEndDateTime());

            repository.save(interview);

            return InterviewDTO.builder().id(interview.getId())
                    .candidateId(interview.getCandidate().getId())
                    .candidateFirstName(interview.getCandidate().getFirstName())
                    .candidateLastName(interview.getCandidate().getLastName())
                    .interviewerId(interview.getInterviewer().getId())
                    .interviewerFirstName(interview.getInterviewer().getFirstName())
                    .interviewerLastName(interview.getInterviewer().getLastName())
                    .startDateTime(interview.getStartDateTime())
                    .endDateTime(interview.getEndDateTime())
                    .build();

        } else {
            throw new Exception("Interview could not be scheduled since there's no available period of interview for the given interviewer.");
        }
    }
}
