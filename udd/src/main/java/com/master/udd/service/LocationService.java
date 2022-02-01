package com.master.udd.service;

import com.master.udd.dto.LocationApiResponse;
import com.master.udd.exception.InvalidAddressException;
import com.master.udd.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LocationService {

//    LocationIQ API
//    https://us1.locationiq.com/v1/search.php?
//    key=pk.aa973c572140d1aa473aba6828c6d4b4
//    &
//    q=bulevar%20kralja%20petra%20I%2036%2C%20novi%20sad
//    &
//    format=json

    @Value("${api.key}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOCATION_API = "https://us1.locationiq.com/v1/search.php?key=";
    private static final String API_FORMAT = "&format=json";

    public Location getLocationFromAddress(String address) throws InvalidAddressException {
        String queryAddress = "&q=" + URLEncoder.encode(address, StandardCharsets.UTF_8);
        ResponseEntity<LocationApiResponse[]> locationResponse = restTemplate.getForEntity(
                LOCATION_API + API_KEY + queryAddress + API_FORMAT, LocationApiResponse[].class);
        if (locationResponse.getStatusCode().is2xxSuccessful()) {
            LocationApiResponse location = Objects.requireNonNull(locationResponse.getBody())[0];
            return new Location(address, location.getLat(), location.getLon());
        } else {
            throw new InvalidAddressException(address);
        }
    }
}
