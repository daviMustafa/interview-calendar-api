package com.tamanna.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterviewerDTO implements Serializable {

    private static final long serialVersionUID = 369838076897827121L;

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotBlank(message = "Fist name required.")
    @ApiModelProperty(position = 1)
    private String firstName;

    @NotBlank(message = "Last name required.")
    @ApiModelProperty(position = 2)
    private String lastName;

    public InterviewerDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
