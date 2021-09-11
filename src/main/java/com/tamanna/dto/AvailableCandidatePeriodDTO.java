package com.tamanna.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AvailableCandidatePeriodDTO implements Serializable {

    private static final long serialVersionUID = -1398988798636344541L;

    @ApiModelProperty
    private Long id;

    @ApiModelProperty(position = 1, hidden = true)
    private String firstName;

    @ApiModelProperty(position = 2, hidden = true)
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @ApiModelProperty(position = 2)
    private LocalDateTime date;

}
