package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reports")
public interface ReportsApi {

    @GetMapping("/delivery_orders/{status}")
    ResponseEntity<ResponseSuccessfullyDto> deliveryOrderByStatus(@PathVariable(name = "status") Integer status);



}
