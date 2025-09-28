package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewIncidentDto;
import ayd.proyecto1.fastdelivery.dto.response.IncidentDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/incident")
public interface IncidentApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createIncident(@RequestBody NewIncidentDto newIncidentDto);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentById(@PathVariable Integer id);

    @GetMapping("/deliveryOrder/{idDeliveryOrder}")
    ResponseEntity<ResponseSuccessfullyDto> getIncidentByIdDeliveryOrder(@PathVariable Integer idDeliveryOrder);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllIncidents();

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateIncident(@RequestBody IncidentDto incidentDto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteIncident(@PathVariable Integer id);
}

