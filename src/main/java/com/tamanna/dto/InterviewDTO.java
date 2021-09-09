package com.tamanna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDTO implements Serializable {

    private static final long serialVersionUID = 6910495163979755704L;

    @ApiModelProperty(readOnly = true)
    public Long id;

    @ApiModelProperty(position = 1)
    private CandidateDTO candidate = new CandidateDTO();

    @ApiModelProperty(position = 2)
    private InterviewerDTO interviewer = new InterviewerDTO();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(position = 3)
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(position = 4)
    private LocalDateTime endDateTime;

    /**
     * {@link com.tamanna.repository.InterviewerRepository#getAllScheduledInterviewsByInterviewerId}
     * {@link com.tamanna.repository.CandidateRepository#getAllScheduledInterviewsByCandidateId}
     */
    public InterviewDTO(Long id, Long candidateId, String candidateFirstName, String candidateLastName, Long interviewerId,
                        String interviewerFirstName, String interviewerLastName, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.candidate.setId(candidateId);
        this.candidate.setFirstName(candidateFirstName);
        this.candidate.setLastName(candidateLastName);
        this.interviewer.setId(interviewerId);
        this.interviewer.setFirstName(interviewerFirstName);
        this.interviewer.setLastName(interviewerLastName);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
