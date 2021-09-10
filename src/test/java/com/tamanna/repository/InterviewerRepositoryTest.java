package com.tamanna.repository;

import com.tamanna.AbstractContainerBaseTest;
import com.tamanna.dto.InterviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterviewerRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private InterviewerRepository repository;

    @Test
    public void should_return_all_interviews_from_given_interviewer_id() {
        List<InterviewDTO> interviews = repository.getAllScheduledInterviewsByInterviewerId(1L);

        assertEquals(2, interviews.size());

        assertEquals("Davi", interviews.get(0).getCandidateFirstName());
        assertEquals("Debora", interviews.get(0).getInterviewerFirstName());
        assertEquals("2021-08-25T17:00", interviews.get(0).getStartDateTime().toString());
        assertEquals("2021-08-25T18:00", interviews.get(0).getEndDateTime().toString());

        assertEquals("Carl", interviews.get(1).getCandidateFirstName());
        assertEquals("Debora", interviews.get(1).getInterviewerFirstName());
        assertEquals("2021-08-26T17:00", interviews.get(1).getStartDateTime().toString());
        assertEquals("2021-08-26T18:00", interviews.get(1).getEndDateTime().toString());
    }
}
