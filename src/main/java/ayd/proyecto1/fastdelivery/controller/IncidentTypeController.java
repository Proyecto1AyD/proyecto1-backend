package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.IncidentTypeApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.IncidentTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IncidentTypeController implements IncidentTypeApi {

    private final IncidentTypeService incidentTypeService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentTypeById(Integer id) {
        log.info("GET incidentType/{}", id);
        ResponseSuccessfullyDto response = incidentTypeService.getIncidentTypeById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllIncidentTypes() {
        log.info("GET incidentType/all");
        ResponseSuccessfullyDto response = incidentTypeService.getAllIncidentTypes();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentTypeByIsCustomer() {
        log.info("GET incidentType/customer");
        ResponseSuccessfullyDto response = incidentTypeService.getIncidentTypeByIsCustomer(true);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentTypeByIsNotCustomer() {
        log.info("GET incidentType/employee");
        ResponseSuccessfullyDto response = incidentTypeService.getIncidentTypeByIsCustomer(false);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}

