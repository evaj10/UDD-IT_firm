package com.master.udd.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotNull
    private Long educationLevelId;

    @NotNull
    private MultipartFile cv;

//    @NotNull
//    private MultipartFile letter;

    @NotNull
    private String ipAddress;
}
