package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.IncidentApi;
import ayd.proyecto1.fastdelivery.dto.request.NewIncidentDto;
import ayd.proyecto1.fastdelivery.dto.response.IncidentDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.IncidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IncidentController implements IncidentApi {

    private final IncidentService incidentService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createIncident(NewIncidentDto newIncidentDto) {
        log.info("POST incident/create");
        ResponseSuccessfullyDto response = incidentService.createIncident(newIncidentDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentById(Integer id) {
        log.info("GET incident/{}", id);
        ResponseSuccessfullyDto response = incidentService.getIncidentById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getIncidentByIdDeliveryOrder(Integer idDeliveryOrder) {
        log.info("GET incident/deliveryOrder/{}", idDeliveryOrder);
        ResponseSuccessfullyDto response = incidentService.getIncidentByIdDeliveryOrder(idDeliveryOrder);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllIncidents() {
        log.info("GET incident/all");
        ResponseSuccessfullyDto response = incidentService.getAllIncidents();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateIncident(IncidentDto incidentDto) {
        log.info("PUT incident");
        ResponseSuccessfullyDto response = incidentService.updateIncident(incidentDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteIncident(Integer id) {
        log.info("DELETE incident/{}", id);
        ResponseSuccessfullyDto response = incidentService.deleteIncident(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}

