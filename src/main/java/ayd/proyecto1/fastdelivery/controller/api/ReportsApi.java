package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@RequestMapping("/reports")
public interface ReportsApi {

    @GetMapping("/delivery_orders/{status}")
    ResponseEntity<ResponseSuccessfullyDto> deliveryOrderByStatus(@PathVariable(name = "status") Integer status);

    @GetMapping("/commission/delivery_person/{delivery_person_id}/{init_date}/{finish_date}")
    ResponseEntity<ResponseSuccessfullyDto> getByAssignmentIdAndDate(@PathVariable(name = "delivery_person_id") Integer deliveryId,
                                                                     @PathVariable(name = "init_date") Date initDate,
                                                                     @PathVariable(name = "finish_date") Date finishDate);



}
