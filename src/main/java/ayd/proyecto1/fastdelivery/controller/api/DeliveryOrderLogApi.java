package ayd.proyecto1.fastdelivery.controller.api;


import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/deliveryOrderLog")
public interface DeliveryOrderLogApi {

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrderLogs();

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderLogById(@PathVariable Integer id);

    @GetMapping("/idDeliveryOrder/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderLogByIdDeliveryOrder(@PathVariable Integer id);
}