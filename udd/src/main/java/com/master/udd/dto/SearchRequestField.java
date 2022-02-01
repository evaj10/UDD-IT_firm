package com.master.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestField {

    @NotBlank
    @Pattern(regexp = "applicantName|applicantSurname|applicantEducation|cvContent")
    private String field;

    @NotBlank
    private String query;

    private boolean phrase;

    private boolean mustContain;
}
