package com.master.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RangeRequest {

    @NotBlank
    @Pattern(regexp = "applicantEducation")
    private String field;

    private Integer lowerBound;

    private Integer upperBound;

    private boolean mustContain;
}
