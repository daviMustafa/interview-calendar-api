package com.tamanna.service;

import com.tamanna.AbstractContainerBaseTest;
import com.tamanna.dto.AvailableInterviewerPeriodDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.repository.InterviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterviewServiceTest extends AbstractContainerBaseTest {

    @Autowired
    private InterviewService service;

    @Autowired
    private InterviewRepository repository;

    @Test
    public void list_periods_for_interviews_given_candidates_and_interviewer_time_slots(){
        List<AvailableInterviewerPeriodDTO> availableInterviewerPeriodDTOS = service.getAvailablePeriodsOfTime(1L, Arrays.asList(1L, 2L),
                Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));

        assertEquals(8, availableInterviewerPeriodDTOS.size());
    }

    @Test
    public void create_interview_period_not_available_should_throw_exception() {
        InterviewDTO interviewDTO = InterviewDTO.builder()
                .interviewerId(1L)
                .candidateId(1L)
                .startDateTime(LocalDateTime.of(2021, 9, 26, 9, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 9, 26, 10, 0, 0))
                .build();

        assertThrows(Exception.class, () -> {
            service.saveInterview(interviewDTO);
        });
    }

    @Test
    public void create_interview_when_available() throws Exception {
        InterviewDTO interviewDTO = InterviewDTO.builder()
                .interviewerId(1L)
                .candidateId(1L)
                .startDateTime(LocalDateTime.of(2021, 9, 7, 9, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 9, 7, 10, 0, 0))
                .build();

        InterviewDTO result = service.saveInterview(interviewDTO);
        assertNotNull(result.getId());

        repository.deleteById(result.getId());
        assertEquals(2, repository.findAll().size());
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

    @Test
    public void interview_day_must_be_weekday() {
        InterviewDTO interviewDTO = InterviewDTO.builder()
                .interviewerId(1L)
                .candidateId(1L)
                .startDateTime(LocalDateTime.of(2021, 9, 11, 9, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 9, 11, 10, 0, 0))
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            service.saveInterview(interviewDTO);
        });
    }

    @Test
    public void interview_hour_must_be_from_beginning_of_any_hour_until_the_beginning_of_the_next_hour() {
        InterviewDTO interviewDTO = InterviewDTO.builder()
                .interviewerId(1L)
                .candidateId(1L)
                .startDateTime(LocalDateTime.of(2021, 9, 7, 9, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 9, 7, 10, 30, 0))
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            service.saveInterview(interviewDTO);
        });
    }

    @Test
    public void interview_time_slot_must_be_one_hour_range() {
        InterviewDTO interviewDTO = InterviewDTO.builder()
                .interviewerId(1L)
                .candidateId(1L)
                .startDateTime(LocalDateTime.of(2021, 9, 7, 9, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 9, 7, 11, 0, 0))
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            service.saveInterview(interviewDTO);
        });
    }
}