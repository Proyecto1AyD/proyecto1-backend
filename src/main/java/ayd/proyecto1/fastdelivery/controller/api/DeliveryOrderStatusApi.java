package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/deliveryOrderStatus")
public interface DeliveryOrderStatusApi {

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrderStatus(@RequestHeader(value = "authorization") Integer userId);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderStatusById(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);
}