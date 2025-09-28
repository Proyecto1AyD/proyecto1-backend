package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryEvidenceDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryEvidenceDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/deliveryEvidence")
public interface DeliveryEvidenceApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryEvidence(@RequestBody NewDeliveryEvidenceDto newDeliveryEvidenceDto);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryEvidenceById(@PathVariable Integer id);

    @GetMapping("/deliveryOrder/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryEvidenceByDeliveryOrderId(@PathVariable Integer id);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryEvidence();

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateDeliveryEvidence(@RequestBody DeliveryEvidenceDto deliveryEvidenceDto);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryEvidence(@PathVariable Integer id);
}
