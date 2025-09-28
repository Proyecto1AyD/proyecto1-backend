package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.DeliveryEvidenceApi;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryEvidenceDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryEvidenceDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.DeliveryEvidenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DeliveryEvidenceController implements DeliveryEvidenceApi {

    private final DeliveryEvidenceService deliveryEvidenceService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> createDeliveryEvidence(NewDeliveryEvidenceDto newDeliveryEvidenceDto) {
        log.info("POST deliveryEvidence");
        ResponseSuccessfullyDto response = deliveryEvidenceService.createDeliveryEvidence(newDeliveryEvidenceDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryEvidenceById(Integer id) {
        log.info("GET deliveryEvidence/{}", id);
        ResponseSuccessfullyDto response = deliveryEvidenceService.getDeliveryEvidenceById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryEvidenceByDeliveryOrderId(Integer id) {
        log.info("GET deliveryEvidence/deliveryOrder/{}", id);
        ResponseSuccessfullyDto response = deliveryEvidenceService.getDeliveryEvidenceByDeliveryOrderId(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryEvidence() {
        log.info("GET deliveryEvidence/all");
        ResponseSuccessfullyDto response = deliveryEvidenceService.getAllDeliveryEvidence();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> updateDeliveryEvidence(DeliveryEvidenceDto deliveryEvidenceDto) {
        log.info("PUT deliveryEvidence/{}", deliveryEvidenceDto.getId());
        ResponseSuccessfullyDto response = deliveryEvidenceService.updateDeliveryEvidence(deliveryEvidenceDto);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryEvidence(Integer id) {
        log.info("DELETE deliveryEvidence/{}", id);
        ResponseSuccessfullyDto response = deliveryEvidenceService.deleteDeliveryEvidence(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}

