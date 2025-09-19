package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/employee")
public interface EmployeeApi {


    @PostMapping("/coordinator")
    ResponseEntity<ResponseSuccessfullyDto> createCoordinatorEmployee(@RequestBody NewCoordinatorEmployeeDto newCoordinatorEmployeeDto,
                                                                      @RequestHeader(value = "authorization") Integer userId);


    @PostMapping("/delivery")
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryEmployee(@RequestBody NewDeliveryPersonDto newDeliveryPersonDto,
                                                                      @RequestHeader(value = "authorization") Integer userId);


}
