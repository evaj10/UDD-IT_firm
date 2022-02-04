package com.master.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @Valid
    @Size(max = 3)
    private List<SearchRequestField> fields;

    @Valid
    private RangeRequest rangeRequest;

    @Valid
    private GeoLocationRequest geoLocation;

    // 4. KOMBO SVEGA SA AND I OR
    // 5. PhrazeQuery
    // 6. SerbianAnalyzer
    // 7. Highlighter
    // 8. Pretraga po geolokaciji

}
