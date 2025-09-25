package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.DeliveryOrderDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/deliveryOrder")
public interface DeliveryOrderApi {

    @PostMapping
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryOrder(@RequestBody NewDeliveryOrderDto newDeliveryOrderDto);

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderById(@PathVariable Integer id);

    @GetMapping("/business/{idBusiness}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryOrderByIdBusiness(@PathVariable Integer idBusiness);

    @GetMapping("/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryOrder();

    @PutMapping
    ResponseEntity<ResponseSuccessfullyDto> updateDeliveryOrder(@RequestBody DeliveryOrderDto deliveryOrderDto);

    @DeleteMapping("{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryOrder(@PathVariable Integer id);
}
