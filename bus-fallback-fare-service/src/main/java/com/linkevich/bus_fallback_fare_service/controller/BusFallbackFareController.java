package com.linkevich.bus_fallback_fare_service.controller;

import com.linkevich.bus_fallback_fare_service.openapi.api.BusesApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;

@RestController
public class BusFallbackFareController implements BusesApi {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return  Optional.of(new ServletWebRequest(request, response));
    }
}
