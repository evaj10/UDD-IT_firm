package com.master.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationRequest {

    private String cityName;
    private Double radius;
    private String radiusUnit;

}
