package com.tamanna.service.impl;

import com.tamanna.dto.AvailableCandidatePeriodDTO;
import com.tamanna.dto.AvailableInterviewerPeriodDTO;
import com.tamanna.entity.Candidate;
import com.tamanna.entity.Interviewer;
import com.tamanna.repository.CandidateRepository;
import com.tamanna.repository.InterviewerRepository;
import com.tamanna.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class InterviewServiceImpl implements InterviewService {

    private final CandidateRepository candidateRepository;
    private final InterviewerRepository interviewerRepository;

    @Autowired
    public InterviewServiceImpl(CandidateRepository candidateRepository, InterviewerRepository interviewerRepository) {
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
}
