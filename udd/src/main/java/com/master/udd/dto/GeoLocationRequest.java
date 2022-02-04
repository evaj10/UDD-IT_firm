package com.master.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationRequest {

    @NotBlank
    private String cityName;

    @Min(1)
    private Double radius;

    @NotBlank
    private String radiusUnit;

}
