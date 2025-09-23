package ayd.proyecto1.fastdelivery.controller.api;

import ayd.proyecto1.fastdelivery.dto.request.NewCoordinatorEmployeeDto;
import ayd.proyecto1.fastdelivery.dto.request.NewDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateCoordinatorDto;
import ayd.proyecto1.fastdelivery.dto.request.UpdateDeliveryPersonDto;
import ayd.proyecto1.fastdelivery.dto.response.ResponseSuccessfullyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee")
public interface EmployeeApi {


    @PostMapping("/coordinator")
    ResponseEntity<ResponseSuccessfullyDto> createCoordinatorEmployee(@RequestBody NewCoordinatorEmployeeDto newCoordinatorEmployeeDto,
                                                                      @RequestHeader(value = "authorization") Integer userId);


    @PostMapping("/delivery")
    ResponseEntity<ResponseSuccessfullyDto> createDeliveryEmployee(@RequestBody NewDeliveryPersonDto newDeliveryPersonDto,
                                                                      @RequestHeader(value = "authorization") Integer userId);

    @PutMapping("/delivery")
    ResponseEntity<ResponseSuccessfullyDto> updateDeliveryPerson(@RequestBody UpdateDeliveryPersonDto updateDeliveryPersonDto,
                                                                 @RequestHeader(value = "authorization") Integer token);

    @PutMapping("/coordinator")
    ResponseEntity<ResponseSuccessfullyDto> updateCoordinator(@RequestBody UpdateCoordinatorDto updateCoordinatorDto,
                                                              @RequestHeader(value = "authorization") Integer token);

    @GetMapping("/coordinator/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getCoordinator(@PathVariable(name ="id") Integer coordinatorId,
                                                           @RequestHeader(value = "authorization") Integer token);

    @GetMapping("/delivery/{id}")
    ResponseEntity<ResponseSuccessfullyDto> getDeliveryPerson(@PathVariable(value = "id") Integer deliveryPersonId,
                                                           @RequestHeader(value = "authorization") Integer token);

    @GetMapping("/delivery/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllDeliveryPerson(@RequestHeader(value = "authorization") Integer token);

    @GetMapping("/coordinator/all")
    ResponseEntity<ResponseSuccessfullyDto> getAllCoordinator(@RequestHeader(value = "authorization") Integer token);

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteDeliveryPerson(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);

    @DeleteMapping("/coordinator/{id}")
    ResponseEntity<ResponseSuccessfullyDto> deleteCoordinator(@PathVariable Integer id, @RequestHeader(value = "authorization") Integer userId);


}
