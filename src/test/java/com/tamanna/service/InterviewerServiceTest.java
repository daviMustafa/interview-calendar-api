package com.tamanna.service;

import com.tamanna.AbstractContainerBaseTest;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.PeriodDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterviewerServiceTest  extends AbstractContainerBaseTest {

    @Autowired
    private InterviewerService service;

    @Test
    public void should_return_all_interviews_from_given_interviewer_id() {
        List<InterviewDTO> interviews = service.getAllScheduledInterviewsByInterviewerId(1L);

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

    @Test
    public void should_not_save_interviewer_available_period_given_non_existing_interviewer() {
        PeriodDTO periodDTO = PeriodDTO.builder()
                .dateTimeFrom(LocalDateTime.of(2021, 9, 7, 9, 0, 0))
                .dateTimeTo(LocalDateTime.of(2021, 9, 7, 10, 0, 0))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            service.saveAvailablePeriod(10L, periodDTO);
        });
    }
}
