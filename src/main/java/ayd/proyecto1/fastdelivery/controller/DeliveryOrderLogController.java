package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.ContractStatusApi;
import ayd.proyecto1.fastdelivery.controller.api.DeliveryOrderLogApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.repository.crud.DeliveryOrderLogCrud;
import ayd.proyecto1.fastdelivery.repository.entities.DeliveryOrderLog;
import ayd.proyecto1.fastdelivery.service.ContractStatusService;
import ayd.proyecto1.fastdelivery.service.DeliveryOrderLogService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DeliveryOrderLogController implements DeliveryOrderLogApi {

    private final DeliveryOrderLogService deliveryOrderLogService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrderLogs() {
        log.info("GET deliveryOrderLog/all");
        ResponseSuccessfullyDto responseSuccessfullyDto = deliveryOrderLogService.getAllDeliveryOrderLogs();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderLogById(Integer id) {
        log.info("GET deliveryOrderLog/{}", id);
        ResponseSuccessfullyDto responseSuccessfullyDto = deliveryOrderLogService.getDeliveryOrderLogById(id);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
