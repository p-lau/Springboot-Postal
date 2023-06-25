package com.plau.postal.usps.api;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.plau.postal.usps.client.HTTPClient;

import lombok.Data;

@Service
public class ServiceStandard {

    @Autowired
    private HTTPClient client;

    private String endpoint = "https://api.usps.com/service-standards/v3";

    public EstimatesResponse[] estimates(EstimatesRequest request){
        String api = endpoint + "/estimates";
        String query = UriComponentsBuilder.fromHttpUrl(api)
        .queryParam("originZIPCode", request.originZIPCode)
        .queryParam("destinationZIPCode", request.destinationZIPCode)
        .queryParam("acceptanceDate", request.acceptanceDate)
        .queryParam("mailClass", request.mailClass)
        .queryParam("destinationType", request.destinationType)
        .queryParam("serviceTypeCodes", request.serviceTypeCodes)
        .toUriString();

        return client.authenticatedHttpRequest(query, HttpMethod.GET, null, EstimatesResponse[].class);
    }

    @Data
    public static class EstimatesRequest {
        private String originZIPCode;
        private String destinationZIPCode;
        private String destinationType;
        private String acceptanceDate;
        private String mailClass;
        private String serviceTypeCodes;
    }
    
    @Data
    public static class EstimatesResponse {
        private String mailClass;
        private String destinationType;
        private Instant acceptanceDateTime;
        private Date effectiveAcceptanceDate;
        private String cutOffTime;
        private String serviceStandard;
        private String serviceStandardMessage;
        private List<Facility> acceptanceLocations;
        private EstimateDelivery delivery;
    }

    @Data
    public static class ClosureTimes {
        private String Monday;
        private String Tuesday;
        private String Wednesday;
        private String Thursday;
        private String Friday;
        private String Saturday;
        private String Sunday;
        private String holidays;
    }

    @Data
    public static class EstimateDelivery {
        private String scheduledDeliveryDateTime;
        private List<Facility> holdForPickupLocation;
    }

    @Data
    public static class Facility {
        private String streetAddress;
        private String streetAddressAbbreviation;
        private String secondaryAddress;
        private String cityAbbreviation;
        private String city;
        private String state;
        private String ZIPCode;
        private String ZIPPlus4;
        private String urbanization;
        private String facilityName;
        private String facilityType;
        private ClosureTimes closes;
    }
}
