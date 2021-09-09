package com.tamanna.repository;

import com.tamanna.AbstractContainerBaseTest;
import com.tamanna.entity.Candidate;
import com.tamanna.entity.Interview;
import com.tamanna.entity.Interviewer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDateTime;

public class InterviewRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private InterviewRepository repository;

    @Test
    public void interview_day_must_be_weekday() {
        Interviewer interviewer = interviewerRepository.findById(1L).orElse(null);
        Candidate candidate = candidateRepository.findById(1L).orElse(null);

        LocalDateTime startDateTime = LocalDateTime.of(2021, 9, 5, 17, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 9, 5, 18, 0, 0);

        Interview interview = new Interview(candidate, interviewer, startDateTime, endDateTime);

        Assertions.assertThrows(TransactionSystemException.class, () -> {
            repository.save(interview);
        });
    }

    @Test
    public void interview_hour_must_be_from_beginning_of_any_hour_until_the_beginning_of_the_next_hour() {
        Interviewer interviewer = interviewerRepository.findById(1L).orElse(null);
        Candidate candidate = candidateRepository.findById(1L).orElse(null);

        LocalDateTime startDateTime = LocalDateTime.of(2021, 9, 6, 17, 30, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 9, 6, 18, 0, 0);

        Interview interview = new Interview(candidate, interviewer, startDateTime, endDateTime);

        Assertions.assertThrows(TransactionSystemException.class, () -> {
            repository.save(interview);
        });
    }

    @Test
    public void interview_time_slot_must_be_1_hour_range() {
        Interviewer interviewer = interviewerRepository.findById(1L).orElse(null);
        Candidate candidate = candidateRepository.findById(1L).orElse(null);

        LocalDateTime startDateTime = LocalDateTime.of(2021, 9, 6, 17, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 9, 6, 19, 0, 0);

        Interview interview = new Interview(candidate, interviewer, startDateTime, endDateTime);

        Assertions.assertThrows(TransactionSystemException.class, () -> {
            repository.save(interview);
        });
    }
}
