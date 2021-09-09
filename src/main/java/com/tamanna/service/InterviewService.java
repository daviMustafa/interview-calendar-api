package com.tamanna.service;

import com.tamanna.dto.AvailableInterviewerPeriodDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public interface InterviewService {
    List<AvailableInterviewerPeriodDTO> getAvailablePeriodsOfTime(@NotNull Long candidateId, @NotEmpty List<Long> interviewerIds, @NotEmpty List<DayOfWeek> daysOfWeek);
}
