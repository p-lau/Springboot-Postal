package com.plau.postal.usps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plau.postal.usps.api.ServiceStandard;
import com.plau.postal.usps.api.ServiceStandard.EstimatesRequest;
import com.plau.postal.usps.api.ServiceStandard.EstimatesResponse;

@RestController
public class SampleController {
    @Autowired
	private ServiceStandard serviceAPI;

    @GetMapping("/{origin}/{destination}")
    @ResponseBody
    public EstimatesResponse[] sample(@PathVariable String origin, @PathVariable String destination){
        EstimatesRequest req = new EstimatesRequest();
        req.setOriginZIPCode(origin);
        req.setDestinationZIPCode(destination);
        req.setDestinationType("STREET");
        req.setAcceptanceDate("2023-06-24");
        req.setMailClass("PRIORITY_MAIL");

        EstimatesResponse[] res = serviceAPI.estimates(req);
        return res;
    }
}
