package ayd.proyecto1.fastdelivery.controller;


import ayd.proyecto1.fastdelivery.controller.api.DeliveryOrderStatusApi;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import ayd.proyecto1.fastdelivery.service.DeliveryOrderStatusService;
import ayd.proyecto1.fastdelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DeliveryOrderStatusController implements DeliveryOrderStatusApi {

    private final DeliveryOrderStatusService deliveryOrderStatusService;

    private final UserService userService;

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrderStatus() {
        log.info("GET deliveryOrderStatus/all");
        ResponseSuccessfullyDto responseSuccessfullyDto = deliveryOrderStatusService.getAllDeliveryOrderStatus();
        return ResponseEntity.ok(responseSuccessfullyDto);
    }

    @Override
    public ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderStatusById(Integer id) {
        log.info("GET deliveryOrderStatus/{}", id);
        ResponseSuccessfullyDto responseSuccessfullyDto = deliveryOrderStatusService.getDeliveryOrderStatusById(id);
        return ResponseEntity.ok(responseSuccessfullyDto);
    }
}
