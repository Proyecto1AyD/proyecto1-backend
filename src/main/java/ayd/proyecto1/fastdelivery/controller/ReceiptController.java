package ayd.proyecto1.fastdelivery.controller;

import ayd.proyecto1.fastdelivery.controller.api.ReceiptApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReceiptController implements ReceiptApi {

    private final ReceiptService receiptService;


    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllReceipt() {
        log.info("GET receipt/all");
        ResponseSuccessfullyDto responseSuccessfullyDto = receiptService.getAllReceipt();
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getReceiptById(Integer receiptId) {
        log.info("GET receipt/{receipt_id}");
        ResponseSuccessfullyDto responseSuccessfullyDto = receiptService.getReceiptById(receiptId);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllReceiptsByDeliveryPerson(Integer deliveryPerosnId) {
        log.info("GET receipt/delivery_person/{deliver_person_id}");
        ResponseSuccessfullyDto responseSuccessfullyDto = receiptService.getByDeliveryPersonId(deliveryPerosnId);
        return new ResponseEntity<>(responseSuccessfullyDto,responseSuccessfullyDto.getCode());
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllReceiptsByAssignment(Integer deliveryOrderAssignmentId) {
        log.info("GET receipt/assignment_order_id");
        ResponseSuccessfullyDto responseSuccessfullyDto = receiptService.getByAssignmentId(deliveryOrderAssignmentId);
        return new ResponseEntity<>(responseSuccessfullyDto, responseSuccessfullyDto.getCode());
    }
}
