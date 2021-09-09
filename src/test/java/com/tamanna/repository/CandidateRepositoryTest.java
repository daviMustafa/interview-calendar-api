package com.tamanna.repository;

import com.tamanna.AbstractContainerBaseTest;
import com.tamanna.dto.InterviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandidateRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private CandidateRepository repository;

    @Test
    public void should_return_all_interviews_from_given_candidate_id() {
        List<InterviewDTO> interviews = repository.getAllScheduledInterviewsByCandidateId(1L);
        assertEquals(1, interviews.size());

        assertEquals("Debora", interviews.get(0).getInterviewer().getFirstName());
        assertEquals("Davi", interviews.get(0).getCandidate().getFirstName());
        assertEquals("2021-08-25T17:00", interviews.get(0).getStartDateTime().toString());
        assertEquals("2021-08-25T18:00", interviews.get(0).getEndDateTime().toString());
    }
}
