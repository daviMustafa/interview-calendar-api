package com.tamanna.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodDTO implements Serializable {

    private static final long serialVersionUID = 8976470423054412307L;

    @NotNull(message = "dateTimeFrom required.")
    @ApiModelProperty(position = 1)
    private LocalDateTime dateTimeFrom;

    @NotNull(message = "dateTimeTo required.")
    private LocalDateTime dateTimeTo;

}
