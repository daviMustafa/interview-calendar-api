package com.tamanna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AvailableCandidatePeriodDTO implements Serializable {

    private static final long serialVersionUID = -1398988798636344541L;

    @ApiModelProperty(position = 1)
    private CandidateDTO candidate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(position = 2)
    private LocalDateTime date;

}
