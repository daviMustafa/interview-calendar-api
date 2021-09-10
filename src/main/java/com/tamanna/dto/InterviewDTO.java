package com.tamanna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalQuery;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class InterviewDTO implements Serializable {

    private static final long serialVersionUID = 6910495163979755704L;

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(position = 1)
    @NotNull(message = "candidateId required")
    private Long candidateId;

    @ApiModelProperty(position = 2, hidden = true)
    private String candidateFirstName;

    @ApiModelProperty(position = 3, hidden = true)
    private String candidateLastName;

    @ApiModelProperty(position = 4)
    @NotNull(message = "interviewerId required")
    private Long interviewerId;

    @ApiModelProperty(position = 5, hidden = true)
    private String interviewerFirstName;

    @ApiModelProperty(position = 6, hidden = true)
    private String interviewerLastName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @ApiModelProperty(position = 7)
    @NotNull
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @ApiModelProperty(position = 8)
    @NotNull
    private LocalDateTime endDateTime;

    /**
     * {@link com.tamanna.repository.InterviewerRepository#getAllScheduledInterviewsByInterviewerId}
     * {@link com.tamanna.repository.CandidateRepository#getAllScheduledInterviewsByCandidateId}
     */
    public InterviewDTO(Long id, Long candidateId, String candidateFirstName, String candidateLastName, Long interviewerId,
                        String interviewerFirstName, String interviewerLastName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.candidateId = candidateId;
        this.candidateFirstName = candidateFirstName;
        this.candidateLastName = candidateLastName;
        this.interviewerId = interviewerId;
        this.interviewerFirstName = interviewerFirstName;
        this.interviewerLastName = interviewerLastName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @AssertFalse(message = "start date or end date is a weekend day.")
    public boolean isWeekend(){
        TemporalQuery<Boolean> weekend = t -> {
            DayOfWeek dow = DayOfWeek.from(t);
            return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
        };

        return startDateTime.query(weekend) || endDateTime.query(weekend);
    }

    @AssertTrue(message = "startDateTime and endDateTime must be within 1 hour range and beginning of any hour until the beginning of the next hour.")
    public boolean isValidTimeSpam(){
        return (startDateTime.getMinute() == 0 && startDateTime.getSecond() == 0)
                && (endDateTime.getMinute() == 0 && endDateTime.getSecond() == 0)
                && Duration.between(startDateTime, endDateTime).toHours() == 1;
    }
}
