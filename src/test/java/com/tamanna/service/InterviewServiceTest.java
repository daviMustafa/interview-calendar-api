package com.tamanna.service;

import com.tamanna.AbstractContainerBaseTest;
import com.tamanna.dto.AvailableInterviewerPeriodDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterviewServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private InterviewService service;

    @Test
    public void list_periods_for_interviews_given_candidates_and_interviewer_time_slots(){
        List<AvailableInterviewerPeriodDTO> availableInterviewerPeriodDTOS = service.getAvailablePeriodsOfTime(1L, Arrays.asList(1L, 2L),
                Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));

        assertEquals(8, availableInterviewerPeriodDTOS.size());
    }

    @Test
    public void days_of_week_parameter_cannot_be_empty(){
        assertThrows(ConstraintViolationException.class, () -> {
            service.getAvailablePeriodsOfTime(1L, Arrays.asList(1L, 2L), null);
        });
    }

    @Test
    public void candidate_id_parameter_cannot_be_null(){
        assertThrows(ConstraintViolationException.class, () -> {
            service.getAvailablePeriodsOfTime(null, Arrays.asList(1L, 2L), Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        });
    }

    @Test
    public void interviewer_ids_parameter_cannot_be_empty(){
        assertThrows(ConstraintViolationException.class, () -> {
            service.getAvailablePeriodsOfTime(1L, null, Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        });
    }
}