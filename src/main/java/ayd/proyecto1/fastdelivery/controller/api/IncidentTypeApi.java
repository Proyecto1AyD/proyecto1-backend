package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/incidentType")
public interface IncidentTypeApi {

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentTypeById(@PathVariable Integer id);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllIncidentTypes();

    @GetMapping("/customer")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentTypeByIsCustomer();

    @GetMapping("/employee")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentTypeByIsNotCustomer();
}


